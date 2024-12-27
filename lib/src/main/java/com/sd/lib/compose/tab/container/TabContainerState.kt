package com.sd.lib.compose.tab.container

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal class TabContainerState {
  private var _selectedTab by mutableStateOf<Any?>(null)
  private val _activeTabs = mutableStateMapOf<Any, TabState>()

  fun selectTab(tab: Any) {
    _selectedTab = tab
  }

  @Composable
  fun Tab(
    tab: Any,
    eager: Boolean,
    display: TabDisplay,
    content: @Composable () -> Unit,
  ) {
    val state = remember(tab) { TabState() }.apply {
      this.display.value = display
      this.content.value = content
    }

    SideEffect {
      _activeTabs[tab]?.update(state)
    }

    if (eager || tab == _selectedTab) {
      LaunchedEffect(tab, state) {
        _activeTabs[tab] = state
      }
    }

    DisposableEffect(tab) {
      onDispose {
        _activeTabs.remove(tab)
      }
    }
  }

  @Composable
  fun Content() {
    for ((tab, state) in _activeTabs) {
      key(tab) {
        val content = state.content.value
        val display = state.display.value
        display(content, tab == _selectedTab)
      }
    }
  }
}

@Stable
private class TabState(
  val display: MutableState<TabDisplay> = mutableStateOf(DefaultTabDisplay),
  val content: MutableState<@Composable () -> Unit> = mutableStateOf({}),
) {
  fun update(state: TabState) {
    this.display.value = state.display.value
    this.content.value = state.content.value
  }
}