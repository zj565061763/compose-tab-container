package com.sd.lib.compose.tab.container

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TabContainer(
  modifier: Modifier = Modifier,
  selectedKey: Any,
  content: @Composable TabContainerScope.() -> Unit,
) {
  val state = remember { TabContainerState() }
  remember(state) { TabContainerScope(state) }.content()
  state.selectKey(selectedKey)
  Box(modifier = modifier) {
    state.Content()
  }
}

class TabContainerScope internal constructor(
  private val state: TabContainerState,
) {
  @Composable
  fun Tab(
    key: Any,
    eager: Boolean = false,
    display: TabDisplay = DefaultTabDisplay,
    content: @Composable () -> Unit,
  ) {
    state.Tab(
      key = key,
      eager = eager,
      display = display,
      content = content,
    )
  }
}