package com.sd.lib.compose.tab.container

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

internal class TabContainerState {
  private var _selectedTab by mutableStateOf<Any?>(null)
  private val _activeTabs = mutableStateMapOf<Any, MutableState<TabState>>()

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
    SideEffect {
      _activeTabs[tab]?.value = TabState(display, content)
    }

    if (eager || tab == _selectedTab) {
      LaunchedEffect(tab) {
        val tabState = TabState(display, content)
        _activeTabs[tab] = mutableStateOf(tabState)
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
        TabContent(
          tab = tab,
          state = state.value,
        )
      }
    }
  }

  @Composable
  private fun TabContent(tab: Any, state: TabState) {
    val content = state.content
    val display = state.display
    display(content, tab == _selectedTab)
  }

  @Immutable
  private class TabState(
    val display: TabDisplay,
    val content: @Composable () -> Unit,
  )
}