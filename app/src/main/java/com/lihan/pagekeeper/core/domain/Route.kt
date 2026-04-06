package com.lihan.pagekeeper.core.domain

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object Library: Route
}