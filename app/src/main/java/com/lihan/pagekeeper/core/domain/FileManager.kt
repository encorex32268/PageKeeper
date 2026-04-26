package com.lihan.pagekeeper.core.domain


interface FileManager {
    suspend fun saveBitmapToDevice(byteArray: ByteArray): String
    suspend fun removeBitmap(paths: List<String>)
}