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

enum class TabDisplay {
    Default,
    New,
}

interface TabContainerScope {
    fun tab(
        key: Any,
        display: TabDisplay = TabDisplay.Default,
        content: @Composable () -> Unit,
    )
}

private class TabContainerImpl : TabContainerScope {
    private val _keyHolder: MutableSet<Any> = hashSetOf()
    private val _tabHolder: MutableMap<Any, TabInfo> = hashMapOf()
    private val _activeKeyHolder: MutableMap<Any, String> = mutableStateMapOf()

    private var _configMode = false

    fun startConfig() {
        if (!_configMode) {
            _configMode = true
        }
    }

    fun stopConfig() {
        if (_configMode) {
            _configMode = false
            check(_keyHolder.isNotEmpty()) { "You should config tab in TabContainer apply block." }
            _tabHolder.iterator().removeIf { !_keyHolder.contains(it.key) }
            _activeKeyHolder.iterator().removeIf { !_keyHolder.contains(it.key) }
            _keyHolder.clear()
        }
    }

    override fun tab(
        key: Any,
        display: TabDisplay,
        content: @Composable () -> Unit,
    ) {
        check(_configMode) { "This should be called in TabContainer apply block." }
        _keyHolder.add(key)

        val info = _tabHolder[key]
        if (info == null) {
            _tabHolder[key] = TabInfo(
                contentState = mutableStateOf(content),
                displayState = mutableStateOf(display),
            )
        } else {
            info.contentState.value = content
            info.displayState.value = display
        }
    }

    @Composable
    fun Content(key: Any) {
        LaunchedEffect(key) {
            if (_tabHolder.containsKey(key)) {
                _activeKeyHolder[key] = ""
            }
        }

        for (item in _activeKeyHolder.keys) {
            _tabHolder[item]?.let { info ->
                when (info.displayState.value) {
                    TabDisplay.Default -> {
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
                            info.contentState.value.invoke()
                        }
                    }

                    TabDisplay.New -> {
                        if (item == key) {
                            info.contentState.value.invoke()
                        }
                    }
                }
            }
        }
    }
}

private class TabInfo(
    val contentState: MutableState<@Composable () -> Unit>,
    val displayState: MutableState<TabDisplay>,
)

private inline fun <T> MutableIterator<T>.removeIf(predicate: (T) -> Boolean) {
    while (hasNext()) {
        if (predicate(next())) {
            remove()
        }
    }
}