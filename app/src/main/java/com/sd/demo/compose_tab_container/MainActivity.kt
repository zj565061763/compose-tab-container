package com.sd.demo.compose_tab_container

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.demo.compose_tab_container.ui.theme.AppTheme
import com.sd.lib.compose.tab.container.TabContainer
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Content()
            }
        }
    }
}

@Composable
private fun Content() {

    val tabs = remember { listOf("A", "B") }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
        ) {
            tabs.forEachIndexed { index, item ->
                val selected = index == selectedTabIndex
                Tab(
                    modifier = Modifier.height(50.dp),
                    selected = selected,
                    onClick = { selectedTabIndex = index },
                ) {
                    Text(text = item)
                }
            }
        }

        TabContainer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            key = tabs[selectedTabIndex],
        ) {
            tab("A") {
                TabContent(tag = "I am A")
            }
            tab("B") {
                TabContent(tag = "I am B")
            }
        }
    }
}

@Composable
private fun TabContent(
    modifier: Modifier = Modifier,
    tag: String,
) {
    var count by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = tag)
        Text(text = count.toString())
    }
}