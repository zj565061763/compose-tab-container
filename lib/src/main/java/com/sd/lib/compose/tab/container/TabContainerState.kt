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
  private var _selectedKey by mutableStateOf<Any?>(null)
  private val _activeTabs = mutableStateMapOf<Any, TabState>()

  fun selectKey(key: Any) {
    _selectedKey = key
  }

  @Composable
  fun Tab(
    key: Any,
    eager: Boolean,
    display: TabDisplay,
    content: @Composable () -> Unit,
  ) {
    val state = remember(key) { TabState() }.apply {
      this.display.value = display
      this.content.value = content
    }

    SideEffect {
      _activeTabs[key]?.update(state)
    }

    if (eager || key == _selectedKey) {
      LaunchedEffect(key, state) {
        _activeTabs[key] = state
      }
    }

    DisposableEffect(key) {
      onDispose {
        _activeTabs.remove(key)
      }
    }
  }

  @Composable
  fun Content() {
    for ((key, state) in _activeTabs) {
      key(key) {
        val content = state.content.value
        val display = state.display.value
        display(content, key == _selectedKey)
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