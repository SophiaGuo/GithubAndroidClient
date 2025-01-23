# GithubAndroidClient

GithubAndroidClient 是使用 Kotlin 实现的 Github 原生客户端。

- 基于 MVVM 设计模式，使用协程处理并发请求
- 网络请求使用 Retrofit 网络请求库
- 声明式 UI，使用多个 JetPack 组件，如 ViewModel、LiveData、Navigator、TabLayout + ViewPager 等，UI 层使用 ViewBinding 进行绑定
- 使用expresso测试框架



## 如何使用

#### APK

打包好的 apk 为  [app-debug.apk](app/build/outputs/apk/debug/app-debug.apk) 

路径位于 GithubAndroidClient/app/build/outputs/apk/debug/app-debug.apk

（时间关系，应该使用签名打包 release 包）



#### 申请个人登录令牌

由于 Github 限制，需要使用个人登录令牌进行登录。

申请个人登录令牌步骤如下，官网文档：

https://docs.github.com/zh/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens

1. 网页端登录到 GitHub 账户（可使用本 app 首页）
2. 前往 Settings -> Developer settings -> Personal access tokens
3. 点击 Generate new token
4. 选择需要的权限，然后生成令牌
5. 复制生成的令牌



#### 拉取代码

把项目 clone 下来

```
git clone git@github.com:SophiaGuo/GithubAndroidClient.git
```

编译后运行，需要登录时使用第一步生成的个人登录令牌进行登录

（更好解决方法，注册为 github 第三方 app 使用账号密码登录，时间限制只使用个人登录令牌）



## 功能

底部菜单栏使用 BottomNavigationView 组件，分别是首页，搜索页，和我的页，均为 Fragment。

**首页**：浏览 github 首页 trending***（要求点）***

**搜索页**：使用 TabLayout + ViewPager 生成 tab，可根据不同编程语言筛选仓库***（要求点）***

顶部搜索栏可搜索用户/搜索仓库，点击按钮进行切换***（额外点）***

点击具体仓库可查看具体仓库内容***（要求点）***

**我的页**：显示个人账号页，可查看仓库，关注者，关注的人，设置***（要求点）***

<img src="https://upload-images.jianshu.io/upload_images/30388762-acf96f8933b8d94c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-6818094c5cfdef22.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-04bbdc8a4f375e21.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" />



搜索

<img src="https://upload-images.jianshu.io/upload_images/30388762-bf04258bc5a01bb7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-5c81fc7f8c3d1545.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" />



#### 登录模块

在我的页提供登录模块，可进入我的仓库/关注者/关注的人页，若未登录，会跳转登录页，并提供退出登录，清理缓存功能***（额外点）***，登录状态会保存在本地，下次启动 app 依然保留***（要求点）***

<img src="https://upload-images.jianshu.io/upload_images/30388762-914abee5c3545733.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-5b3c6f2fd90a29e2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-373dc84dca9315f5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" />



#### 状态错误处理

当网络错误、没有数据时，会显示对应的错误提示***（要求点）***

<img src="https://upload-images.jianshu.io/upload_images/30388762-1c304aa3e6d928ce.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-ec3b743510e1cca3.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" />



## **适配**

- 支持 minSdk >= 29设备***（要求点）***
- 支持横竖屏切换***（要求点）***
- 权限控制，启动时在 MainActivity 请求权限***（额外点）***

```
private val requestPermissionLauncher =
    registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean,  ->
        if (isGranted) {
            Log.i(tag, "Permission Granted")
        } else {
            Log.i(tag, "Permission Denied")
            Toast.makeText(this, R.string.error_deny_permission, Toast.LENGTH_SHORT).show()
        }
    }
```

- 支持中英文语言切换，可随系统语言设置切换中英文***（额外点）***
- 支持暗黑模式，可在系统设置暗黑模式体验***（额外点）***

<img src="https://upload-images.jianshu.io/upload_images/30388762-7485f00f2333c768.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-f34a05e02f8ae023.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-127d1284ac1e8fc6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" />



## 框架

项目总体框架如下，对应代码中 com.sophia.githubandroidclient 下 package 名，为从上到下调用，模块之间解耦。

![UML](https://upload-images.jianshu.io/upload_images/30388762-190b0bd21b9d9dcb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1024)

Base 层提供 view、viewmodel 基类，封装通用函数，抽离的 view组件，工具类，接口等

Data 层包括数据类型定义

Data Process 数据处理层是对 Data 层数据的处理

View 层为 Activity、Fragment 等，以及对应绑定的 adapter



#### MVVM 框架

项目基于 MVVM 设计模式，结构图如下

<img src="https://upload-images.jianshu.io/upload_images/30388762-7a43bcd73a8bedd8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/551"/>

View 包括 Activity 和 Fragment，处理用户输入以及输出界面

Model 定义数据结构

View 和 Model 使用双向数据绑定到 ViewModel 进行数据处理，数据来源有本地存储数据，以及基于 Retrofit 从网络拉取的数据，通过Repository 进行接口封装后，ViewModel 进行处理



#### 设计模式

使用了单例模式，模板模式，责任链模式等。



#### 基类

项目封装多个基类

BaseActivity/BaseFragment：基于 Activity 和 Fragment 封装生命周期，懒加载处理等

BaseVmActivity/BaseVmFragment：封装 ViewModel，协程，可直接继承使用

BaseLoadMoreAdapter：封装加载更多组件



#### 网络请求

使用 Retrofit 定义接口，方便快捷



## **测试**



## **性能优化**

#### 问题

测试过程中发现冷启动特别慢，特别是搜索页花费约10s才能加载出来。



#### 分析

使用 Android Studio 自带的 Profiler 分析工具，录制冷启动到打开搜索页过程（这里临时把首页改到搜索页方便测试），并分析 trace 文件。

<img src="https://upload-images.jianshu.io/upload_images/30388762-0a66cad0be71b7d5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1200"/>

查看火山图和 Top-down 图分析

![trace](https://upload-images.jianshu.io/upload_images/30388762-13d26ccf25f27e13.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1200)

发现 SearchResultRepository.search 函数花费约 5s。

该函数是执行搜索结果显示搜索第一个 viewpage kotlin 语言的搜索结果，运行在协程中。该函数为耗时函数，不应该卡住 UI 显示。



#### 优化

该函数在 fragment onResume() 中执行懒加载策略，修改为子线程执行

<img src="https://upload-images.jianshu.io/upload_images/30388762-2200fcf827a0ef30.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/662" style="zoom: 50%;" /><img src="https://upload-images.jianshu.io/upload_images/30388762-ee97c945872fb343.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/676" style="zoom: 50%;" />



#### 结果

启动不再卡住 UI，而是先展示 UI 和 loading 图标，等加载完成再显示搜索结果。冷启动搜索页减少约一半时间（时间和设备限制只能在自己的测试机上测试得到的数据）

<img src="https://upload-images.jianshu.io/upload_images/30388762-efa5b8a2b7ab9dc6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" style="zoom:25%;" />



#### 可优化

可以增加 SplashActivity 启动页，预加载耗时数据