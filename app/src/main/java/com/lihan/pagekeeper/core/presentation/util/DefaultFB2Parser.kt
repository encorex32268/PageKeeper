package com.lihan.pagekeeper.core.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Xml
import com.lihan.pagekeeper.core.presentation.util.model.EpubMetadata
import com.lihan.pagekeeper.core.presentation.util.model.FB2Metadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.util.zip.ZipInputStream

class DefaultFB2Parser(
    private val context: Context
): FB2FileParser {

    companion object{
        private const val MIME_TYPE = "fb2"
        private const val XLINK_NS = "http://www.w3.org/1999/xlink"
    }

    override suspend fun parse(uri: Uri): FB2Metadata? {
        val mimeType = context.contentResolver.getType(uri)
        val extension = uri.path?.substringAfterLast('.', "")

        if (mimeType != "application/x-fictionbook+xml" && extension?.lowercase() != MIME_TYPE) {
            return null
        }

        return withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val parser = Xml.newPullParser()
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
                    parser.setInput(inputStream, "UTF-8")

                    var title = ""
                    var firstName = ""
                    var middleName = ""
                    var lastName = ""
                    var targetCoverId = ""
                    var coverBitmap: Bitmap? = null

                    var eventType = parser.eventType
                    var inTitleInfo = false

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        val tagName = parser.name

                        when (eventType) {
                            XmlPullParser.START_TAG -> {
                                when (tagName) {
                                    "title-info" -> inTitleInfo = true
                                    "book-title" -> if (inTitleInfo) title = parser.nextText()
                                    "first-name" -> if (inTitleInfo) firstName = parser.nextText()
                                    "middle-name" -> if (inTitleInfo) middleName = parser.nextText()
                                    "last-name" -> if (inTitleInfo) lastName = parser.nextText()
                                    "image" -> {
                                        if (inTitleInfo) {
                                            // 1. 先抓封面 ID
                                            targetCoverId = parser.getAttributeValue(XLINK_NS, "href")?.removePrefix("#") ?: ""
                                        }
                                    }
                                    "binary" -> {
                                        val id = parser.getAttributeValue(null, "id")
                                        if (id == targetCoverId && targetCoverId.isNotEmpty()) {
                                            val base64String = parser.nextText()
                                            coverBitmap = decodeBase64ToBitmap(base64String)                                        }
                                    }
                                }
                            }
                            XmlPullParser.END_TAG -> {
                                if (tagName == "title-info") inTitleInfo = false
                            }
                        }
                        eventType = parser.next()
                    }

                    FB2Metadata(
                        title = title,
                        author = listOf(firstName, middleName, lastName).filter { it.isNotBlank() }.joinToString(" "),
                        cover = coverBitmap // 建議 Metadata 類改為存 Bitmap 或 ByteArray
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val cleanedString = base64Str.replace("\n", "").replace("\r", "").trim()
            val imageBytes = Base64.decode(cleanedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: Exception) {
            null
        }
    }


}

