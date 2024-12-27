[![](https://jitpack.io/v/zj565061763/compose-tab-container.svg)](https://jitpack.io/#zj565061763/compose-tab-container)

# About

实现`Jetpack Compose`中`Tab`切换管理功能，详细介绍地址点击：[这里](https://juejin.cn/post/7327562275107536911)

# Demo

```kotlin
// Tab类型
private enum class TabType {
  Home,
  Live,
  Video,
  Me,
}

@Composable
private fun Tabs(
  modifier: Modifier = Modifier,
  selectedTab: TabType,
) {
  TabContainer(
    selectedKey = selectedTab,
    modifier = modifier.fillMaxSize(),
  ) {
    // 设置tab内容
    tab(TabType.Home) {
      TabContent(TabType.Home)
    }

    // 设置tab内容
    tab(TabType.Live) {
      TabContent(TabType.Live)
    }

    // 设置tab内容，eager = true，提前加载
    tab(TabType.Video, eager = true) {
      TabContent(TabType.Video)
    }

    // 设置tab内容，自定义display，选中的时候才添加可组合项
    tab(
      key = TabType.Me,
      display = { content, selected -> if (selected) content() },
    ) {
      TabContent(TabType.Me)
    }
  }
}
```