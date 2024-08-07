---
comments: true
---

# 插件是如何工作的

插件是Allay的重要组成部分之一。在编写第一个插件之前，有必要先了解插件的加载以及运行流程。

## 组成部分

`PluginSource`: 插件源。提供插件文件的位置（本地位置，网络位置）。
用户可以注册自己的插件源，系统在加载插件时会遍历所有插件源。

`PluginLoader(Factory)`: 插件加载器（工厂）。对于每个插件文件，系统会查找适合其的加载器
（这通过遍历已注册的插件加载器工厂并依次调用`PluginLoaderFactory.canLoad(path)`实现）。
若找到，将为其创建一个加载器实例。用户可以注册自己的插件加载器工厂以支持不同语言/不同格式的插件。

`PluginDescriptor`: 插件描述符。包含一个插件的各种信息，例如其作者，API版本，入口，依赖等。

`PluginManager`: 插件管理器，包含加载/运行插件等一系列逻辑。

`PluginContainer`: 插件的运行容器。插件加载后会创建一个运行容器，包含此插件的逻辑主体，描述符，加载器以及数据文件夹。

`Plugin`: 插件的逻辑主体。系统通过此对象与一个插件交互，通常来说其至少包含插件被加载时，被启用时，被关闭时将要执行的逻辑

## 加载流程

服务器启动时，插件管理器先遍历所有插件源并查找每个插件文件对应的加载器。

接下来，管理器将为每个插件加载描述符，并检查回环依赖。

一切完成后，调用插件的`onLoad()`方法。注意此时不代表服务器已经启动完成，不应该做任何与游戏内容有关的操作，包括但不限于：

- 注册监听器
- 注册命令
- 注册定时任务
- 与世界交互
- ...

启用插件时，会调用插件的`onEnable()`方法。对应地，关闭插件时会调用插件的`onDisable()`方法。