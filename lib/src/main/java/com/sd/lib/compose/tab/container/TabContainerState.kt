package com.sd.lib.compose.tab.container

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal class TabContainerState {
  private var _selectedTab by mutableStateOf<Any?>(null)

  fun selectTab(tab: Any) {
    _selectedTab = tab
  }

  @Composable
  fun Tab(
    tab: Any,
    eager: Boolean,
    display: TabDisplay?,
    content: @Composable () -> Unit,
  ) {
    val selected by remember(tab) { derivedStateOf { tab == _selectedTab } }

    var load by remember { mutableStateOf(false) }
    if (eager || selected) {
      load = true
    }

    if (load) {
      if (display != null) {
        display(content, selected)
      } else {
        DefaultTabDisplay(
          selected = selected,
          content = content,
        )
      }
    }
  }
}