package com.lihan.pagekeeper.library.di

import com.lihan.pagekeeper.library.presentation.LibraryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val libraryModule = module {
    viewModelOf(::LibraryViewModel)
}