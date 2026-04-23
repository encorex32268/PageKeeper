package com.lihan.pagekeeper.core.domain


interface FileManager {
    suspend fun saveBitmapToDevice(byteArray: ByteArray): String
}