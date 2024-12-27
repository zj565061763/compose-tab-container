package com.sd.lib.compose.tab.container

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * tab容器，根据选中的[selectedKey]展示对应的tab
 */
@Composable
fun TabContainer(
  modifier: Modifier = Modifier,
  selectedKey: Any,
  apply: TabContainerScope.() -> Unit,
) {
  val container = remember {
    TabContainerImpl()
  }.apply {
    startConfig()
    apply()
    stopConfig()
  }

  Box(modifier = modifier) {
    container.Content(selectedKey)
  }
}

interface TabContainerScope {
  /**
   * 设置tab内容
   * @param key tab对应的key
   * @param display [TabDisplay]
   * @param eager 是否提前加载tab
   * @param content tab内容
   */
  fun tab(
    key: Any,
    display: TabDisplay = DefaultDisplay,
    eager: Boolean = false,
    content: @Composable () -> Unit,
  )
}

/**
 * 当选中状态变化时，如何显示隐藏tab，默认实现[DefaultDisplay]
 */
typealias TabDisplay = @Composable (content: @Composable () -> Unit, selected: Boolean) -> Unit

private val DefaultDisplay: TabDisplay = { content: @Composable () -> Unit, selected: Boolean ->
  Box(
    modifier = Modifier.graphicsLayer {
      val scale = if (selected) 1f else 0f
      this.scaleX = scale
      this.scaleY = scale
    }
  ) {
    content()
  }
}

private class TabContainerImpl : TabContainerScope {
  private val _store: MutableMap<Any, TabInfo> = mutableMapOf()
  private val _activeTabs: MutableMap<Any, TabState> = mutableStateMapOf()

  private var _configState = ConfigState.None

  fun startConfig() {
    if (_configState == ConfigState.None) {
      _configState = ConfigState.Config
    }
  }

  fun stopConfig() {
    if (_configState == ConfigState.Config) {
      _configState = ConfigState.ConfigFinish
    }
  }

  override fun tab(
    key: Any,
    display: TabDisplay,
    eager: Boolean,
    content: @Composable () -> Unit,
  ) {
    if (_configState == ConfigState.Config) {
      val info = _store[key]
      if (info == null) {
        _store[key] = TabInfo(display, content)
      } else {
        info.display = display
        info.content = content
      }

      if (eager) {
        activeTab(key)
      }
    }
  }

  private fun activeTab(key: Any) {
    if (_activeTabs[key] == null) {
      val info = checkNotNull(_store[key]) { "Key $key was not found." }
      _activeTabs[key] = TabState(
        display = mutableStateOf(info.display),
        content = mutableStateOf(info.content),
      )
    }
  }

  @Composable
  fun Content(selectedKey: Any) {
    SideEffect {
      if (_configState == ConfigState.ConfigFinish) {
        _configState = ConfigState.None
        for ((key, state) in _activeTabs) {
          val info = checkNotNull(_store[key])
          state.update(info)
        }
      }
    }

    LaunchedEffect(selectedKey) {
      activeTab(selectedKey)
    }

    for ((key, state) in _activeTabs) {
      key(key) {
        val display = state.display.value
        display(state.content.value, key == selectedKey)
      }
    }
  }

  enum class ConfigState {
    None,
    Config,
    ConfigFinish,
  }
}

private class TabInfo(
  var display: TabDisplay,
  var content: @Composable () -> Unit,
)

private class TabState(
  val display: MutableState<TabDisplay>,
  val content: MutableState<@Composable () -> Unit>,
) {
  fun update(info: TabInfo) {
    display.value = info.display
    content.value = info.content
  }
}