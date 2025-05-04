package com.manjiuqi.mcsmsdk.entity

import kotlinx.serialization.Serializable

@Serializable
data class InstanceDetail(
    val instanceUUID: String? = null,
    val started: Long,
    val status: Long,
    val config: InstanceConfig,
    val info: Info,
    val space: Long? = null,
    val processInfo: ProcessInfo? = null
)

@Serializable
data class InstanceConfig(
    val nickname: String,
    val startCommand: String? = "bash",
    val stopCommand: String? = "^C",
    val cwd: String? = null,
    val ie: String? = "utf8",
    val oe: String? = "utf8",
    val createDatetime: Long? = null,
    val lastDatetime: Long? = null,
    val type: String? = "universal",
    val tag: List<String>? = null,
    val endTime: Long? = null,
    val fileCode: String? = "utf8",
    val processType: String? = "universal",
    val updateCommand: String? = null,
    val crlf: Int,
    val category: Long,
    val enableRcon: Boolean? = null,
    val rconPassword: String? = null,
    val rconPort: Int? = null,
    val rconIP: String? = null,
    val actionCommandList: List<String>? = null,
    val terminalOption: TerminalOption,
    val eventTask: EventTask,
    val docker: Docker? = null,
    val pingConfig: PingConfig? = null,
    val extraServiceConfig: ExtraServiceConfig? = null,
)

@Serializable
data class Docker(
    val containerName: String,
    val image: String,
    val ports: List<Int>? = null,
    val extraVolumes: List<String>? = null,
    val memory: Long,
    val networkMode: String,
    val networkAliases: List<String>? = null,
    val cpusetCpus: String,
    val cpuUsage: Long,
    val maxSpace: Long,
    val io: Long,
    val network: Long,
    val workingDir: String,
    val env: List<String>? = null,
    val changeWorkdir: Boolean
)

@Serializable
data class EventTask(
    val autoStart: Boolean,
    val autoRestart: Boolean,
    val ignore: Boolean
)

@Serializable
data class ExtraServiceConfig(
    val openFrpTunnelID: String? = null,
    val openFrpToken: String? = null,
    val isOpenFrp: Boolean? = null
)

@Serializable
data class PingConfig(
    val ip: String,
    val port: Int,
    val type: Int
)

@Serializable
data class TerminalOption(
    val haveColor: Boolean = true,
    val pty: Boolean = true,
    val ptyWindowCol: Int,
    val ptyWindowRow: Int
)

@Serializable
data class Info(
    val mcPingOnline: Boolean,
    val currentPlayers: Long,
    val maxPlayers: Long,
    val version: String,
    val fileLock: Long,
    val playersChart: List<String>? = null,
    val openFrpStatus: Boolean,
    val latency: Long
)

@Serializable
data class ProcessInfo(
    val cpu: Double,
    val memory: Long,
    val ctime: Long,
    val elapsed: Long,
    val timestamp: Long,
    val pid: Long,
    val ppid: Long
)