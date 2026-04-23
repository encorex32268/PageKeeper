package com.lihan.pagekeeper.core.di

import androidx.room.Room
import com.lihan.pagekeeper.core.data.DefaultFileManager
import com.lihan.pagekeeper.core.data.PageKeeperBookRepository
import com.lihan.pagekeeper.core.data.local.BookDao
import com.lihan.pagekeeper.core.data.local.PageKeeperDatabase
import com.lihan.pagekeeper.core.domain.BookRepository
import com.lihan.pagekeeper.core.domain.FileManager
import com.lihan.pagekeeper.core.presentation.util.DefaultEpubMetadataParser
import com.lihan.pagekeeper.core.presentation.util.EpubMetadataParser
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
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

    singleOf(::PageKeeperBookRepository).bind<BookRepository>()
    singleOf(::DefaultFileManager).bind<FileManager>()
    singleOf(::DefaultEpubMetadataParser).bind<EpubMetadataParser>()

}