
plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
    id("maven-publish")
}

group = "com.manjiuqi.mcsmsdk"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.maven.rtast.cn/releases")
    maven("https://repo.maven.rtast.cn/snapshots")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("cn.rtast:rtast-util-string:0.0.1")
    implementation("cn.rtast.rob:ronebot-common-http:3.0.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

publishing {
    publications {
        create<MavenPublication>("mcsmsdk") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
    repositories{
        mavenLocal()
    }
}