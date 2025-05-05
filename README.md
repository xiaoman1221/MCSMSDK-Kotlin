# MCSMSDK-Kotlin
## 本项目主要是对MCSMANAGER的API部分封装，使其更加方便调用

**目前项目尚处于开发中，BUG不可避免**
## 使用方法

```kotlin
repositories {
    mavenCentral()
    mavenLocal()
    maven("https://m2.1v.fit/releases")
    maven("https://repo.maven.rtast.cn/releases")
}
```

```kotlin
import com.manjiuqi.mcsmsdk.MCSM

    val client = MCSM(
        "http://192.168.43.57",
        23333,
        "114514"
    )
      client.daemonList()?.forEach {
        println(client.mkdir("1919810", it.uuid, "/denom"))
    }
```
