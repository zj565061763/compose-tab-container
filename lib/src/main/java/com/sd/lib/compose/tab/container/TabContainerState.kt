package com.sd.lib.compose.tab.container

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal class TabContainerState {
  private var _display: TabDisplay? = null
  private var _selectedTab by mutableStateOf<Any?>(null)

  fun setTabDisplay(display: TabDisplay?) {
    _display = display
  }

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

    var load by remember(tab) { mutableStateOf(false) }
    if (eager || selected) {
      load = true
    }

    if (load) {
      val finalDisplay = display ?: _display
      if (finalDisplay != null) {
        finalDisplay(content, selected)
      } else {
        DefaultTabDisplay(
          selected = selected,
          content = content,
        )
      }
    }
  }
}