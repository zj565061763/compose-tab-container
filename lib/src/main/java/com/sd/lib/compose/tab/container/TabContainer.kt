package com.sd.lib.compose.tab.container

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun TabContainer(
    key: Any,
    modifier: Modifier = Modifier,
    apply: TabContainerScope.() -> Unit,
) {
    val container = remember {
        TabContainerImpl()
    }.apply {
        apply()
    }

    Box(modifier = modifier) {
        container.Content(key)
    }
}

interface TabContainerScope {
    fun tab(
        key: Any,
        content: @Composable () -> Unit,
    )
}

private class TabContainerImpl : TabContainerScope {
    private val _contentHolder: MutableMap<Any, MutableState<@Composable () -> Unit>> = hashMapOf()
    private val _activeKeyHolder: MutableMap<Any, String> = mutableStateMapOf()

    override fun tab(
        key: Any,
        content: @Composable () -> Unit,
    ) {
        val state = _contentHolder[key]
        if (state == null) {
            _contentHolder[key] = mutableStateOf(content)
        } else {
            state.value = content
        }
    }

    @Composable
    fun Content(key: Any) {
        LaunchedEffect(key) {
            if (_contentHolder.containsKey(key)) {
                _activeKeyHolder[key] = ""
            }
        }

        for (item in _activeKeyHolder.keys) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        if (item == key) {
                            this.scaleX = 1f
                        } else {
                            this.scaleX = 0f
                        }
                    }
            ) {
                _contentHolder[item]?.value?.invoke()
            }
        }
    }
}