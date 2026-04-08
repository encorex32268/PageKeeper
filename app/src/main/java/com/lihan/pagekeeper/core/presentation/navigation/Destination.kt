package com.lihan.pagekeeper.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.domain.Route
import com.lihan.pagekeeper.core.presentation.Flag
import com.lihan.pagekeeper.core.presentation.FlagFill
import com.lihan.pagekeeper.core.presentation.Library
import com.lihan.pagekeeper.core.presentation.LibraryFill
import com.lihan.pagekeeper.core.presentation.Star
import com.lihan.pagekeeper.core.presentation.StarFill

data class Destination(
    val route: Route,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val name: String
)

val destinations: List<Destination>
    @Composable get() = listOf(
        Destination(Route.Library , selectedIcon = LibraryFill , unSelectedIcon = Library , name = stringResource(R.string.library)),
        Destination(Route.Favorites , selectedIcon = StarFill, unSelectedIcon = Star, name = stringResource(R.string.favorite)),
        Destination(Route.Finished , selectedIcon = FlagFill, unSelectedIcon = Flag, name = stringResource(R.string.finished)),
    )
