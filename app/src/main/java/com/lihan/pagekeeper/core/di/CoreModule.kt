package com.lihan.pagekeeper.core.di

import androidx.room.Room
import com.lihan.pagekeeper.core.data.local.BookDao
import com.lihan.pagekeeper.core.data.local.PageKeeperDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {

    single{
        Room.databaseBuilder(
            context = androidContext(),
            klass = PageKeeperDatabase::class.java,
            name = "pageKeeper.db"
        ).build()
    }

    single {
        get<PageKeeperDatabase>().bookDao
    }.bind<BookDao>()

}