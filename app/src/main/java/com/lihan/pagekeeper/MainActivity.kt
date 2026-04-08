@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lihan.pagekeeper.core.domain.Route
import com.lihan.pagekeeper.core.presentation.navigation.AdaptiveLayout
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration.Companion.fromWindowSizeClass
import com.lihan.pagekeeper.library.presentation.LibraryScreenRoot
import com.lihan.pagekeeper.library.presentation.tablet.LibraryTabletScreen
import com.lihan.pagekeeper.library.presentation.tablet.LibraryTabletState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PageKeeperTheme {
                val navController = rememberNavController()


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
                    content = {
                        NavHost(
                            navController = navController,
                            startDestination = Route.Library,
                        ) {
                            composable<Route.Library> {
                                if(currentDeviceConfiguration.isMobile){
                                    LibraryScreenRoot(
                                        menuClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        },
                                        navigateToSearch = {
                                            navController.navigate(Route.Search)
                                        }
                                    )
                                }else{
                                    LibraryTabletScreen(
                                        state = LibraryTabletState(),
                                        onAction = {}
                                    )
                                }
                            }
                            composable<Route.Favorites> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Favorites"
                                    )
                                }
                            }

                            composable<Route.Finished> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Finished"
                                    )
                                }
                            }

                            composable<Route.Search> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Search"
                                    )
                                }
                            }
                        }
                    }
                )

            }
        }
    }
}
