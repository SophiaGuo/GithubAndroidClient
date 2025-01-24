# GithubAndroidClient

中文版请见[这里](https://github.com/SophiaGuo/GithubAndroidClient/blob/main/README_ZH.md)。



GithubAndroidClient is a native Github client application using Kotlin.

- Based on the MVVM design pattern, use coroutines to handle concurrent requests
- Using the Retrofit network request library for network requests
- Declarative UI, using multiple JetPack components such as VNet, LiveData, Navigator, TabLayout+ViewPager, etc. The UI layer is bound using ViewBinding
- Using the Expresso testing framework



## How to use

#### APK

The packaged apk is  [app-debug.apk](app/debug/app-debug.apk) 

The path is located at  GithubAndroidClient/app/debug/app-debug.apk

(Due to insufficient time, signature should be used to package release package)



#### Apply for a personal access token

Due to Github restrictions, a personal access token is required for login.
The steps to apply for a personal login token are as follows, according to the official website document:
https://docs.github.com/zh/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens

1. Log in to your GitHub account on the web page (you can use the homepage of this app)
2. Go to Settings ->Developer settings ->Personal access tokens
3. Click on Generate new token
4. Select the required permissions and generate a token
5. Copy the generated token



#### Pull code

Clone the project

```
git clone git@github.com:SophiaGuo/GithubAndroidClient.git
```

Configure in local. properties
```
USER_ACCESS_TOKEN="XXXXXX"
```
Fill in with personal access token, compile and run.
When logging in, use the personal login token generated in the first step, or click to log in with a test account.
(A better solution is to register as a third-party app on GitHub and log in with an account and password, with a time limit of only using personal login tokens)



## Feature

The bottom menu bar uses the BottomNavigationView component, which includes the Homepage, Search page, and My Page, all of which are fragments.

**Home**：Browse GitHub homepage trending ***(Requirements)***

**Search page**：Use TabLayout and ViewPager to generate tabs and filter repositories based on different programming languages ***(Requirements)***

The top search bar can search for users/repositories, click the button to switch  ***(Extra Point)***

Click on the specific warehouse to view its contents ***(Requirements)***

**My**：Display personal account page, view personal repositories, followers, followers, settings ***(Requirements)***

<img src="https://upload-images.jianshu.io/upload_images/30388762-acf96f8933b8d94c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%"/> <img src="https://upload-images.jianshu.io/upload_images/30388762-6818094c5cfdef22.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-04bbdc8a4f375e21.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%"/>



Search

<img src="https://upload-images.jianshu.io/upload_images/30388762-bf04258bc5a01bb7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-5c81fc7f8c3d1545.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" />



#### Login module

Provide a login module on my page, which allows access to my repository/followers/followers page. If not logged in, it will redirect to the login page and provide features such as logging out and clearing cache ***(Extra Point)*** *. The login status will be saved locally and will be retained the next time the app is launched* **(Requirements)**

<img src="https://upload-images.jianshu.io/upload_images/30388762-914abee5c3545733.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-5b3c6f2fd90a29e2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-373dc84dca9315f5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" />



#### Handle Error Status

When there is a network error or no data available, the corresponding error message will be displayed. ***(Requirements)***

<img src="https://upload-images.jianshu.io/upload_images/30388762-1c304aa3e6d928ce.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-ec3b743510e1cca3.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" />

## **Adaption**

- Support minSdk>=29 devices **(Requirements)**
- Support both portrait and landscape modes **(Requirements)**
- Request permission at MainActivity when startup  ***(Extra Point)***

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

- Support language change. You can change language to Chinese or English in system settings  ***(Extra Point)***
- Support dark mode. You can change to dark mode in system settings  ***(Extra Point)***

<img src="https://upload-images.jianshu.io/upload_images/30388762-7485f00f2333c768.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-f34a05e02f8ae023.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" /> <img src="https://upload-images.jianshu.io/upload_images/30388762-127d1284ac1e8fc6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" />



## Framework

The overall framework of the project is as follows, corresponding to the package name under com. sophia. githubandroidclient in the code, which is called from top to bottom and decoupled between modules.

![UML](https://upload-images.jianshu.io/upload_images/30388762-190b0bd21b9d9dcb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1024)

The Base layer provides view and viewmodel base classes, encapsulates generic functions, detached view components, utility classes, interfaces, etc
The Data layer includes data type definitions
The Data Process layer is the processing of data in the Data layer
The View layer consists of Activities, Fragments, and corresponding bound adapters



#### MVVM Framework

The project is based on the MVVM design pattern, and the structural diagram is as follows.

<img src="https://upload-images.jianshu.io/upload_images/30388762-7a43bcd73a8bedd8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/551"/>

View includes Activity and Fragment, handling user input and output interfaces
Model defines data structure
View and Model use bidirectional data binding to VNet for data processing. The data sources include locally stored data and data pulled from the network based on Retrofit. After being encapsulated through the Repository interface, the ViewModel process the data



#### Design Pattern

Single instance mode, template mode, responsibility chain mode, etc. were used.



#### Base Class

Project encapsulates multiple base classes
BaseActivity/BaseFragment： Encapsulate lifecycle based on Activity and Fragment, lazy loading processing, etc
BaseVmActivity/BaseVmFragment： Encapsulate VNet, coroutines, can be directly inherited and used
BaseLoadMoreAdapter： Encapsulate and load more components



#### Network

Using Retrofit to define interfaces for convenience.



## **Testing**

The Android UI testing uses the Expresso testing framework, with the code path located at GithubAndroidClient/app/src/androidTest/java/com/Sophia/Githubandroidclient. It is used to test UI display, jumping, etc. In this project, it is used to test navigator jumping, fragment switching, activity jumping, etc.

Unit testing uses JUnit, with the code path located at GithubAndroidClient/app/src/test/java/com/Sophia/gitubandroidclient, to test Kotlin logic. In this project, it is used to test whether the data pulled by repository using Retrofit is valid.

Due to time constraints, the testing code is incomplete and for reference only.



## Performance Optimization

#### Problem

During the testing process, it was found that cold start is particularly slow, especially when the search page takes about 10 seconds to load.



#### Analysis

Use the profiler analysis tool that comes with Android Studio to record the process from cold start to opening the search page (temporarily changing the homepage to the search page for testing purposes), and analyze the trace file.

<img src="https://upload-images.jianshu.io/upload_images/30388762-0a66cad0be71b7d5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1200"/>

Check volcano and the top-down chart to analysis.

![trace](https://upload-images.jianshu.io/upload_images/30388762-13d26ccf25f27e13.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1200)

The SearchResultRepository.search function was found to take approximately 5 seconds.
This function displays the search results for the first viewport in Kotlin language and runs in the coroutine. This function is a time-consuming function and should not get stuck in UI display.



#### Optimization

This function executes lazy loading strategy in fragment onResume() and is modified to be executed by child threads

<img src="https://upload-images.jianshu.io/upload_images/30388762-2200fcf827a0ef30.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/662" width="50%" /><img src="https://upload-images.jianshu.io/upload_images/30388762-ee97c945872fb343.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/676" width="50%" />

####

#### Result

Starting no longer freezes the UI, but displays the UI and loading icon first, and then displays the search results after loading is complete. Cold start search page reduces time by about half (time and device limitations can only be tested on one's own testing machine)

<img src="https://upload-images.jianshu.io/upload_images/30388762-efa5b8a2b7ab9dc6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080" width="25%" />

#### To be optimized

Add SplashActivity launch page, preloading time consumption data.