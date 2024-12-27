package com.sd.lib.compose.tab.container

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * 当选中状态变化时，如何显示隐藏Tab，默认实现[DefaultTabDisplay]
 */
typealias TabDisplay = @Composable (content: @Composable () -> Unit, selected: Boolean) -> Unit

internal val DefaultTabDisplay: TabDisplay = { content: @Composable () -> Unit, selected: Boolean ->
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