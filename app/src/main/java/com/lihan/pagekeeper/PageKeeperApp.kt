package com.lihan.pagekeeper

import android.app.Application
import com.lihan.pagekeeper.core.di.coreModule
import com.lihan.pagekeeper.library.di.libraryModule
import com.lihan.pagekeeper.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PageKeeperApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PageKeeperApp)
            androidLogger(Level.DEBUG)
            modules(
                coreModule,
                libraryModule,
                searchModule
            )
        }
    }
}