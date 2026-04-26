@file:OptIn(ExperimentalUuidApi::class)

package com.lihan.pagekeeper.core.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.lihan.pagekeeper.core.domain.FileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultFileManager(
    private val context: Context
): FileManager{

    override suspend fun saveBitmapToDevice(
        byteArray: ByteArray
    ): String {
        if (byteArray.isEmpty()) return ""
        return withContext(Dispatchers.IO){
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                ?: return@withContext ""

            val file = File(context.filesDir , "covers/${Uuid.random()}.webp")
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

    override suspend fun removeBitmap(path: String) {
        val file = File(path)
        if (file.exists()){
            file.delete()
        }
    }
}