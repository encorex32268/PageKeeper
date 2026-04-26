@file:OptIn(ExperimentalUuidApi::class)

package com.lihan.pagekeeper.core.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.lihan.pagekeeper.core.domain.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import okio.IOException
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

import android.net.Uri
import com.lihan.pagekeeper.core.presentation.util.getFileNameFromContentUri

class DefaultFileManager(
    private val context: Context
): FileManager{

    override suspend fun saveBook(uri: Uri): String {
        return withContext(Dispatchers.IO) {
            try {
                val fileName = context.getFileNameFromContentUri(uri) ?: "${Uuid.random()}.fb2"
                val file = File(context.filesDir, "books/$fileName")
                file.parentFile?.mkdirs()

                context.contentResolver.openInputStream(uri)?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }

    override suspend fun saveBitmapToDevice(
        byteArray: ByteArray
    ): String {
        if (byteArray.isEmpty()) return ""
        return withContext(Dispatchers.IO){
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                ?: return@withContext ""

            val file = File(context.filesDir , "books/images/${Uuid.random()}.webp")
            file.parentFile?.mkdirs()
            file.outputStream().use { outputStream ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 80, outputStream)
                } else {
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 80, outputStream)
                }
            }

            file.absolutePath
        }
    }

    override suspend fun removeFiles(paths: List<String>) = withContext(Dispatchers.IO) {
        paths.forEach { path ->
            try {
                if (path.isBlank()) return@forEach
                val file = File(path)
                if (file.exists()){
                    file.delete()
                }
            }catch (e: Exception){
                ensureActive()
                e.printStackTrace()
            }
        }
    }
}