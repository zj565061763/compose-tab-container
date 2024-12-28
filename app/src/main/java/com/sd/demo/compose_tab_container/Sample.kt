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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sd.demo.compose_tab_container.ui.theme.AppTheme
import com.sd.lib.compose.tab.container.TabContainer

class Sample : ComponentActivity() {
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
  Live,
  Video,
  Me,
}

@Composable
private fun Content() {
  // 当前选中的Tab
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
  modifier: Modifier = Modifier,
  selectedTab: TabType,
) {
  TabContainer(
    modifier = modifier.fillMaxSize(),
    selectedTab = selectedTab,
  ) {
    // 设置tab内容
    Tab(TabType.Home) {
      TabContent(TabType.Home)
    }

    // 设置tab内容
    Tab(TabType.Live) {
      TabContent(TabType.Live)
    }

    // 设置tab内容，eager = true，提前加载
    Tab(TabType.Video, eager = true) {
      TabContent(TabType.Video)
    }

    // 设置tab内容，自定义display，选中的时候才添加可组合项
    Tab(
      tab = TabType.Me,
      display = { content, selected -> if (selected) content() },
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
  NavigationBar {
    for (tab in TabType.entries) {
      NavigationBarItem(
        selected = selectedTab == tab,
        onClick = { onClickTab(tab) },
        icon = { Text(text = tab.name) },
      )
    }
  }
}

@Preview
@Composable
private fun Preview() {
  Content()
}