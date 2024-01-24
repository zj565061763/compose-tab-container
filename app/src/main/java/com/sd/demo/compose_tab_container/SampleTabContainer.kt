package com.sd.demo.compose_tab_container

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.demo.compose_tab_container.ui.theme.AppTheme
import com.sd.lib.compose.tab.container.TabContainer

class SampleTabContainer : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Content()
            }
        }
    }
}

private enum class TabType {
    Home,
    Video,
    Me,
}

@Composable
private fun Content() {
    /** 当前选中的Tab */
    var selectedTab by remember { mutableStateOf(TabType.Home) }

    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(
            selectedTab = selectedTab,
            modifier = Modifier.weight(1f),
        )
        BottomNavigation(
            selectedTab = selectedTab,
            onClickTab = { selectedTab = it },
        )
    }
}

@Composable
private fun Tabs(
    selectedTab: TabType,
    modifier: Modifier = Modifier,
) {
    TabContainer(
        selectedKey = selectedTab,
        modifier = modifier.fillMaxSize(),
    ) {
        tab(TabType.Home) {
            TabContent(TabType.Home)
        }
        tab(TabType.Video) {
            TabContent(TabType.Video)
        }
        tab(
            key = TabType.Me,
            display = { content, selected ->
                // 自定义display，每次都添加和移除可组合项
                if (selected) content()
            },
        ) {
            TabContent(TabType.Me)
        }
    }
}

@Composable
private fun TabContent(
    tabType: TabType,
    modifier: Modifier = Modifier,
) {
    DisposableEffect(tabType) {
        logMsg { "tab:${tabType.name}" }
        onDispose { logMsg { "tab:${tabType.name} onDispose" } }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = tabType.name)
    }
}

/**
 * 底部导航
 */
@Composable
private fun BottomNavigation(
    selectedTab: TabType,
    onClickTab: (TabType) -> Unit,
) {
    val tabs = remember { TabType.entries.toList() }
    NavigationBar {
        for (tab in tabs) {
            key(tab) {
                NavigationBarItem(
                    selected = selectedTab == tab,
                    onClick = { onClickTab(tab) },
                    icon = { Text(text = tab.name) },
                )
            }
        }
    }
}