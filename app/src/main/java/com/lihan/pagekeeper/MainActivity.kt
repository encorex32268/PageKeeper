@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lihan.pagekeeper.core.domain.Route
import com.lihan.pagekeeper.core.presentation.navigation.AdaptiveLayout
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration.Companion.fromWindowSizeClass
import com.lihan.pagekeeper.favorites.presentation.FavoritesScreen
import com.lihan.pagekeeper.finished.presentation.FinishedScreen
import com.lihan.pagekeeper.library.presentation.LibraryAction
import com.lihan.pagekeeper.library.presentation.LibraryAdaptiveScreenRoot
import com.lihan.pagekeeper.library.presentation.LibraryViewModel
import com.lihan.pagekeeper.search.presentation.SearchScreenRoot
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PageKeeperTheme {
                val navController = rememberNavController()
                val viewModel: LibraryViewModel = koinViewModel()
                val libraryState by viewModel.state.collectAsStateWithLifecycle()
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
                val currentDeviceConfiguration = fromWindowSizeClass(windowSizeClass)


                AdaptiveLayout(
                    currentDeviceConfiguration = currentDeviceConfiguration,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    drawerState = drawerState,
                    onUpsertBook = {
                        viewModel.onAction(LibraryAction.UpsertBook(it))
                    },
                    content = {
                        NavHost(
                            navController = navController,
                            startDestination = Route.Library,
                        ) {
                            composable<Route.Library> {
                                LibraryAdaptiveScreenRoot(
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    onSearchClick = {
                                        navController.navigate(Route.Search)
                                    },
                                    viewModel = viewModel
                                )
                            }
                            composable<Route.Favorites> {
                                FavoritesScreen(
                                    state = libraryState,
                                    onSearchClick = {
                                        navController.navigate(Route.Search)
                                    },
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                )
                            }

                            composable<Route.Finished> {
                                FinishedScreen(
                                    state = libraryState,
                                    onSearchClick = {
                                        navController.navigate(Route.Search)
                                    },
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                )
                            }

                            composable<Route.Search> {
                                SearchScreenRoot(
                                    onBack = {
                                        navController.navigateUp()
                                    },
                                    onNavigateToDetail = {}
                                )
                            }
                        }
                    }
                )

            }
        }
    }
}
