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
        startConfig()
        apply()
        stopConfig()
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
    private val _keyHolder: MutableSet<Any> = hashSetOf()
    private val _contentHolder: MutableMap<Any, MutableState<@Composable () -> Unit>> = hashMapOf()
    private val _activeKeyHolder: MutableMap<Any, String> = mutableStateMapOf()

    private var _configMode = false

    fun startConfig() {
        if (!_configMode) {
            _configMode = true
            _keyHolder.clear()
        }
    }

    fun stopConfig() {
        if (_configMode) {
            check(_keyHolder.isNotEmpty()) { "You should config tab in TabContainer apply block." }
            _configMode = false

            _contentHolder.iterator().run {
                while (hasNext()) {
                    val item = next()
                    if (!_keyHolder.contains(item.key)) {
                        remove()
                    }
                }
            }

            _keyHolder.clear()
        }
    }

    override fun tab(
        key: Any,
        content: @Composable () -> Unit,
    ) {
        check(_configMode) { "This should be called in TabContainer apply block." }
        _keyHolder.add(key)

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
            _contentHolder[item]?.let { content ->
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
                    content.value.invoke()
                }
            }
        }
    }
}