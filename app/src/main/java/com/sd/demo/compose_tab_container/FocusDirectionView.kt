package com.sd.demo.compose_tab_container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FocusDirectionView(
  modifier: Modifier = Modifier,
) {
  val manager = LocalFocusManager.current
  Box(modifier = modifier.size(160.dp)) {
    // Up
    ItemView(
      modifier = Modifier.align(Alignment.TopCenter),
      imageVector = Icons.Filled.KeyboardArrowUp,
      onClick = { manager.moveFocus(FocusDirection.Up) },
    )

    // Down
    ItemView(
      modifier = Modifier.align(Alignment.BottomCenter),
      imageVector = Icons.Filled.KeyboardArrowDown,
      onClick = { manager.moveFocus(FocusDirection.Down) },
    )

    // Left
    ItemView(
      modifier = Modifier.align(Alignment.CenterStart),
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
      onClick = { manager.moveFocus(FocusDirection.Left) },
    )

    // Right
    ItemView(
      modifier = Modifier.align(Alignment.CenterEnd),
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      onClick = { manager.moveFocus(FocusDirection.Right) },
    )
  }
}

@Composable
private fun ItemView(
  modifier: Modifier = Modifier,
  imageVector: ImageVector,
  onClick: () -> Unit,
) {
  IconButton(
    modifier = modifier,
    onClick = onClick,
  ) {
    Icon(
      imageVector = imageVector,
      contentDescription = null,
    )
  }
}

@Preview
@Composable
private fun Preview() {
  FocusDirectionView()
}