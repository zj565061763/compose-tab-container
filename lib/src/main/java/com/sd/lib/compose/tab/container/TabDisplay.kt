package com.sd.lib.compose.tab.container

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.graphicsLayer

/**
 * 当选中状态变化时，如何显示隐藏Tab，默认实现[DefaultTabDisplay]
 */
typealias TabDisplay = @Composable (content: @Composable () -> Unit, selected: Boolean) -> Unit

@Composable
internal fun DefaultTabDisplay(
  selected: Boolean,
  content: @Composable () -> Unit,
) {
  val modifier = if (selected) Modifier else {
    Modifier
      .graphicsLayer {
        scaleX = 0f
        scaleY = 0f
      }
      .focusProperties {
        canFocus = false
      }
  }
  Box(modifier) {
    content()
  }
}