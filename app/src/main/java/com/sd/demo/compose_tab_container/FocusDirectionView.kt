package com.sd.demo.compose_tab_container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FocusDirectionView(
  modifier: Modifier = Modifier,
) {
  val manager = LocalFocusManager.current
  Box(
    modifier = modifier
      .size(140.dp)
      .padding(10.dp)
  ) {
    Button(
      modifier = Modifier
        .size(40.dp)
        .align(Alignment.TopCenter),
      contentPadding = PaddingValues(0.dp),
      onClick = { manager.moveFocus(FocusDirection.Up) }
    ) {
      Text(text = "上")
    }

    Button(
      modifier = Modifier
        .size(40.dp)
        .align(Alignment.BottomCenter),
      contentPadding = PaddingValues(0.dp),
      onClick = { manager.moveFocus(FocusDirection.Down) }
    ) {
      Text(text = "下")
    }

    Button(
      modifier = Modifier
        .size(40.dp)
        .align(Alignment.CenterStart),
      contentPadding = PaddingValues(0.dp),
      onClick = { manager.moveFocus(FocusDirection.Left) }
    ) {
      Text(text = "左")
    }

    Button(
      modifier = Modifier
        .size(40.dp)
        .align(Alignment.CenterEnd),
      contentPadding = PaddingValues(0.dp),
      onClick = { manager.moveFocus(FocusDirection.Right) }
    ) {
      Text(text = "右")
    }
  }
}

@Preview
@Composable
private fun Preview() {
  FocusDirectionView()
}