package com.sd.lib.compose.tab.container

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TabContainer(
  modifier: Modifier = Modifier,
  selectedTab: Any,
  display: TabDisplay? = null,
  content: @Composable TabContainerScope.() -> Unit,
) {
  val state = remember { TabContainerState() }
  state.setTabDisplay(display)

  LaunchedEffect(state, selectedTab) {
    state.selectTab(selectedTab)
  }

  Box(modifier = modifier) {
    remember(state) { TabContainerScope(state) }.content()
  }
}

class TabContainerScope internal constructor(
  private val state: TabContainerState,
) {
  @Composable
  fun Tab(
    tab: Any,
    eager: Boolean = false,
    display: TabDisplay? = null,
    content: @Composable () -> Unit,
  ) {
    state.Tab(
      tab = tab,
      eager = eager,
      display = display,
      content = content,
    )
  }
}