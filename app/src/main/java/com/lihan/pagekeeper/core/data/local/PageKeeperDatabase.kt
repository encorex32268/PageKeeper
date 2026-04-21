package com.lihan.pagekeeper.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PageKeeperDatabase: RoomDatabase() {
    abstract val bookDao: BookDao
}