package com.manjiuqi.mcsmsdk.entity

import kotlinx.serialization.Serializable

// 仪表盘 API 数据类
@Serializable
data class Dashboard(
    val status: Int?,
    val data: Data? = null,
    val time: Double
) {
    @Serializable
    data class Data(
        val version: String,
        val specifiedDaemonVersion: String,
        val process: Process,
        val record: Record,
        val system: System,
        val chart: Chart,
        val remoteCount: RemoteCount,
        val remote: List<Remote>,
    )

    @Serializable
    data class Process(
        val cpu: Float,
        val memory: Float,
        val cwd: String
    )

    @Serializable
    data class Record(
        val logined: Int,
        val illegalAccess: Int,
        val banips: Int,
        val loginFailed: Int
    )

    @Serializable
    data class System(
        val user: User,
        val time: Double,
        val totalmem: Double,
        val freemem: Double,
        val type: String,
        val version: String,
        val node: String,
        val hostname: String,
        val loadavg: List<Float>,
        val platform: String,
        val release: String,
        val uptime: Double,
        val cpu: Double
    ) {
        @Serializable
        data class User(
            val uid: Int,
            val gid: Int,
            val username: String,
            val homedir: String,
            val shell: String
        )
    }

    @Serializable
    data class Chart(
        val system: List<ChartSystem>,
        val request: List<Request>,
    ) {
        @Serializable
        data class ChartSystem(
            val cpu: Float,
            val mem: Float,
        )

        @Serializable
        data class Request(
            val value: Int,
            val totalInstance: Int,
            val runningInstance: Int
        )
    }

    @Serializable
    data class RemoteCount(
        val available: Int,
        val total: Int
    )

    @Serializable
    data class Remote(
        val version: String,
        val process: Process,
        val instance: Instance,
        val system: RemoteSystem,
        val cpuMemChart: List<CPUMemChart>,
        val uuid: String,
        val ip: String,
        val port: Int,
        val prefix: String?,
        val available: Boolean,
        val remarks: String
    )

    @Serializable
    data class Instance(
        val running: Int,
        val total: Int,
    )

    @Serializable
    data class RemoteSystem(
        val type: String,
        val hostname: String,
        val platform: String,
        val release: String,
        val uptime: Double,
        val cwd: String,
        val loadavg: List<Float>,
        val freemem: Double,
        val cpuUsage: Float,
        val memUsage: Double,
        val processCpu: Double,
        val processMem: Double,
    )

    @Serializable
    data class CPUMemChart(
        val cpu: Float,
        val mem: Float,
    )
}
