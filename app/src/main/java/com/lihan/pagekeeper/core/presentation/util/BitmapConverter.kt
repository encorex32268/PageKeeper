package com.lihan.pagekeeper.core.presentation.util

import android.graphics.Bitmap
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

object BitmapConverter {

    suspend fun toByteArray(bitmap: Bitmap?): ByteArray {
        if (bitmap == null){
            return byteArrayOf()
        }
        return  withContext(Dispatchers.IO){
            val outputStream = ByteArrayOutputStream()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 80, outputStream)
            } else {
                bitmap.compress(Bitmap.CompressFormat.WEBP,80,outputStream)
            }
            outputStream.toByteArray()
        }
    }
}