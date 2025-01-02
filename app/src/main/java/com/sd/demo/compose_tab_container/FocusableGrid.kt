package com.sd.demo.compose_tab_container

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FocusableGrid(
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
  ) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      FocusableBox()
      FocusableBox()
      FocusableBox()
    }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      FocusableBox()
      FocusableBox()
      FocusableBox()
    }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      FocusableBox()
      FocusableBox()
      FocusableBox()
    }
  }
}

@Composable
private fun FocusableBox(
  modifier: Modifier = Modifier,
) {
  var color by remember { mutableStateOf(Black) }
  Box(
    modifier = modifier
      .size(48.dp)
      .border(2.dp, color)
      .onFocusChanged { color = if (it.isFocused) Green else Black }
      .focusable()
  )
}

@Preview
@Composable
private fun Preview() {
  FocusableGrid()
}