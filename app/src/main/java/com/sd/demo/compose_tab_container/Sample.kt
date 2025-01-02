package com.sd.demo.compose_tab_container

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
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
private fun Content(
  modifier: Modifier = Modifier,
) {
  // 当前选中的Tab
  var selectedTab by remember { mutableStateOf(TabType.Home) }

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Tabs(
      modifier = Modifier.weight(1f),
      selectedTab = selectedTab,
    )

    NavigationView(
      selectedTab = selectedTab,
      onClickTab = { selectedTab = it },
    )

    FocusDirectionView()
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
    // 设置Tab内容
    Tab(TabType.Home) {
      TabContent(TabType.Home)
    }

    // 设置Tab内容
    Tab(TabType.Live) {
      TabContent(TabType.Live)
    }

    // 设置Tab内容，eager = true，提前加载
    Tab(TabType.Video, eager = true) {
      TabContent(TabType.Video)
    }

    // 设置Tab内容，自定义display
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

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    FocusableGrid()
    Text(
      text = tabType.name,
      modifier = Modifier.padding(top = 24.dp),
    )
  }
}

@Composable
private fun NavigationView(
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