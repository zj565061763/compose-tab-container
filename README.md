# About

实现`Jetpack Compose`中`Tab`
切换状态管理功能，详细介绍地址点击：[这里](https://juejin.cn/post/7327562275107536911)

# Gradle

[![](https://jitpack.io/v/zj565061763/compose-tab-container.svg)](https://jitpack.io/#zj565061763/compose-tab-container)

# Demo

```kotlin
// Tab类型
private enum class TabType {
    Home,
    Video,
    Me,
}

@Composable
private fun Tabs(
    modifier: Modifier = Modifier,
    selectedTab: TabType,
) {
    // Tab容器
    TabContainer(
        // 当前选中的Tab类型
        selectedKey = selectedTab,
        modifier = modifier.fillMaxSize(),
    ) {
        // 设置Tab类型对应的UI
        tab(TabType.Home) {
            TabContent(TabType.Home)
        }

        tab(TabType.Video) {
            TabContent(TabType.Video)
        }

        tab(
            key = TabType.Me,
            display = { content, selected ->
                // 自定义display，选中的时候才添加可组合项
                if (selected) content()
            },
        ) {
            TabContent(TabType.Me)
        }
    }
}
```