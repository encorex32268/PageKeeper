package com.lihan.pagekeeper.core.domain


interface FileManager {
    suspend fun saveBitmapToDevice(byteArray: ByteArray): String
    suspend fun saveBook(uri: android.net.Uri): String
    suspend fun removeFiles(paths: List<String>)
}