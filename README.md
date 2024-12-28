[![](https://jitpack.io/v/zj565061763/compose-tab-container.svg)](https://jitpack.io/#zj565061763/compose-tab-container)

# Sample

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
    modifier = modifier.fillMaxSize(),
    selectedTab = selectedTab,
  ) {
    // 设置Tab内容
    Tab(TabType.Home) {
      TabContent(TabType.Home)
    }

    // 设置Tab内容
    Tab(TabType.Live) {
      TabContent(TabType.Live)
    }

    // 设置Tab内容，eager = true，提前加载
    Tab(TabType.Video, eager = true) {
      TabContent(TabType.Video)
    }

    // 设置Tab内容，自定义display
    Tab(
      tab = TabType.Me,
      display = { content, selected -> if (selected) content() },
    ) {
      TabContent(TabType.Me)
    }
  }
}

@Composable
private fun TabContent(tabType: TabType) {
  // Tab内容
}
```