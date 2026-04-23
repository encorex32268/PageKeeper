package com.lihan.pagekeeper.core.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Xml
import com.lihan.pagekeeper.core.presentation.util.model.EpubMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.util.zip.ZipInputStream

class DefaultEpubMetadataParser(
    private val context: Context
): EpubMetadataParser {

    override suspend fun parseEpubFile(uri: Uri): EpubMetadata {

        return withContext(Dispatchers.IO){

            var title = ""
            var author = ""
            var coverBitmap: Bitmap? = null

            context.contentResolver.openInputStream(uri)?.use { fis ->
                val zis = ZipInputStream(fis)
                var entry = zis.nextEntry
                while (entry != null) {
                    println("Entry: $entry")

                    val isContentOpfFile = entry.name.endsWith("content.opf") || entry.name.endsWith(".opf")

                    if (isContentOpfFile) {
                        val parser = Xml.newPullParser()
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                        parser.setInput(zis, "UTF-8")

                        var eventType = parser.eventType
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_TAG) {
                                when (parser.name) {
                                    "dc:title" -> title = parser.nextText()
                                    "dc:creator" -> author = parser.nextText()
                                }
                            }
                            if (title.isNotEmpty() && author.isNotEmpty()) break
                            eventType = parser.next()
                        }

                    }

                    val isCoverImageFile = entry.name.contains("cover", ignoreCase = true) &&
                            (
                                    entry.name.endsWith(".jpg") ||
                                            entry.name.endsWith(".png") ||
                                            entry.name.endsWith(".jpeg")
                                    )

                    if (isCoverImageFile) {
                        coverBitmap = BitmapFactory.decodeStream(zis)
                    }

                    zis.closeEntry()
                    entry = zis.nextEntry
                }
            }
            EpubMetadata(
                title = title,
                author = author,
                cover = coverBitmap
            )
        }

    }
}