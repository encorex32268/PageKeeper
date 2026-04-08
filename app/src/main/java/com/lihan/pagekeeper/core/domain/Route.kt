package com.lihan.pagekeeper.core.domain

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object Library: Route

    @Serializable
    data object Favorites: Route

    @Serializable
    data object Finished: Route

}