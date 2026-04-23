package com.lihan.pagekeeper.core.presentation.navigation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.domain.Route
import com.lihan.pagekeeper.core.presentation.ImportBook
import com.lihan.pagekeeper.core.presentation.Menu
import com.lihan.pagekeeper.core.presentation.MenuExpand
import com.lihan.pagekeeper.core.presentation.design_system.buttons.PKButton
import com.lihan.pagekeeper.core.presentation.ui.theme.BGActive
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.Primary
import com.lihan.pagekeeper.core.presentation.ui.theme.navigation_Large
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration.Companion.fromWindowSizeClass
import com.lihan.pagekeeper.library.presentation.LibraryAction
import kotlinx.coroutines.launch

@Composable
fun AdaptiveLayout(
    currentDeviceConfiguration: DeviceConfiguration,
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
    onUpsertBook: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    val filePick = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri ->
        if (uri != null){
            onUpsertBook(uri)
        }
    }


    when (currentDeviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ModalNavigationDrawer(
                modifier = modifier,
                drawerState = drawerState,
                drawerContent = {
                    Column(
                        modifier = Modifier
                            .width(260.dp)
                            .fillMaxHeight()
                            .background(BGMain, RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)),
                    ){
                        DrawerContent(
                            selected = selected,
                            onDrawerItemClick = { index,route ->
                                selected = index
                                navController.navigate(route)
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            onMenuCloseClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            onImportBookClick = {
                                filePick.launch("application/epub+zip")
                            }
                        )
                    }
                },
                content = content
            )
        }

        else -> {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .captionBarPadding()
            ) {
                Box(
                    modifier = Modifier
                        .animateContentSize()
                ){
                    if (!drawerState.isOpen) {
                        NavigationRail(
                            header = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Menu,
                                        contentDescription = "Open",
                                        tint = Icons
                                    )
                                }
                                IconButton(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Primary, RoundedCornerShape(16.dp)),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Primary
                                    ),
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = ImportBook,
                                        contentDescription = null,
                                        tint = BGMain
                                    )
                                }
                            },
                            content = {
                                Spacer(Modifier.height(60.dp))
                                NavigationRailContent(
                                    selected = selected,
                                    onDrawerItemClick = {
                                        selected = it
                                    }
                                )
                            }
                        )

                    } else {
                        PermanentDrawerSheet(Modifier.width(240.dp)) {
                            DrawerContent(
                                selected = selected,
                                onDrawerItemClick = { index,route ->
                                    selected = index
                                    navController.navigate(route)
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                onMenuCloseClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                onImportBookClick = {
                                    filePick.launch("application/xml")
                                }
                            )
                        }
                    }

                }
                content()
            }
        }
    }

}

@Composable
private fun DrawerContent(
    selected: Int,
    onImportBookClick: () -> Unit,
    onMenuCloseClick: () -> Unit,
    onDrawerItemClick: (Int, Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 60.dp)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(bottom = 60.dp)
        ){
            IconButton(
                onClick = onMenuCloseClick,
            ) {
                Icon(
                    imageVector = MenuExpand,
                    contentDescription = "Close",
                    tint = Icons
                )
            }
            Spacer(Modifier.height(4.dp))
            PKButton(
                text = stringResource(R.string.import_book),
                onClick = onImportBookClick,
                leadingIcon = {
                    Icon(
                        imageVector = ImportBook,
                        contentDescription = null
                    )
                }
            )
        }
        Column(
            modifier = Modifier.width(180.dp)
        ) {
            destinations.forEachIndexed { index, destination ->
                val isSelected = selected == index
                NavigationDrawerItem(
                    modifier = Modifier.fillMaxWidth().padding(end = 25.dp),
                    label = {
                        Text(
                            text = destination.name,
                            style = MaterialTheme.typography.navigation_Large,
                            color = Icons
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        onDrawerItemClick(selected,destination.route)
                    },
                    icon = {
                        Icon(
                            imageVector = if (isSelected){
                                destination.selectedIcon
                            }else{
                                destination.unSelectedIcon
                            },
                            contentDescription = destination.name,
                            tint = Icons
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = BGActive,
                        unselectedContainerColor = Color.Transparent,
                    )
                )
            }

        }
    }
}

@Composable
private fun NavigationRailContent(
    selected: Int,
    onDrawerItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        destinations.forEachIndexed { index, destination ->
            val isSelected = selected == index
            NavigationRailItem(
                label = {
                    Text(
                        text = destination.name,
                        style = MaterialTheme.typography.navigation_Large,
                        color = Icons
                    )
                },
                selected = isSelected,
                onClick = {
                    onDrawerItemClick(selected)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected){
                            destination.selectedIcon
                        }else{
                            destination.unSelectedIcon
                        },
                        contentDescription = destination.name
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Icons,
                    selectedTextColor = Icons,
                    indicatorColor = BGActive,
                    unselectedIconColor = Icons,
                    unselectedTextColor = Icons
                )
            )
        }

    }
}


@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun AdaptiveScaffoldTabletPreview() {
    PageKeeperTheme {
        AdaptiveLayout(
            currentDeviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            content = {

            },
            onUpsertBook = {}
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun AdaptiveScaffoldPreview() {
    PageKeeperTheme {
        AdaptiveLayout(
            currentDeviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            content = {

            },
            onUpsertBook = {}
        )
    }
}



