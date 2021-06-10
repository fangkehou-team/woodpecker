# WoodPecker

** 一个Java Crash / ANR 监测库 **

** a Java Crash / ANR monitor **

## 中文 (You can find the English version below)

### 介绍

WoodPecker (其实叫“啄木鸟”也可以呢) 是用于监测Android 程序 Java Crash 和 ANR 并进行处理并汇报的库，最初是为了给自己写的垃圾Crash上报的能力，不过本着菜鸟互帮互助的心理还是把自己写的垃圾开源了，请各位dalao不要喷我ㄟ( ▔, ▔ )ㄏ

### 使用方法

由于暂时还没有上传到jitpack或者maven central，所以暂时还是只能下载源码然后调用（我稍后会处理好jitpack，如果有时间的话去搞一搞maven central，不过硬说很麻烦，所以不要抱太大希望吧）

如果你已经引入了这个项目，那么你可以通过如下的一段代码去初始化然后进行工作 (被[]包裹的代码并不是必须的，但是大部分应用在初始化的时候都需要对这些参数进行调整)：

```java

CrashBuilder.from(getApplicationContext())[.setSleeptime(50L)][.setAnrtime(1000L)][.setProcesser(new DemoProcesser())].build().init();

```

解释：

CrashBuilder.from(Context mcontext) WoodPeckerBuilder初始化，需要传入Context （可以是application，activity，service等等，**不过大部分应用在这里都会传application的Context吧。。。。。**）

setSleeptime(Long sleeptime) WoodPecker在监测ANR是需要开启一个独立线程不断获取主线程堆栈，该数值是每获取一次堆栈休眠的时间，默认为50ms

setAnrtime(Long anrtime) WoodPecker将在一个进程运行超过这个时间之后判定程序出现ANR，默认为1s

setProcesser(ProcesserBase processer) 当出现Java Crash或者ANR之后，WoodPecker将会使用该processer相应的函数处理错误（需要自己实现一个ProcesserBase），默认为WoodPecker自带的ProcesserDemo

## English (你瞅啥呢，中文在上面啊)
