package com.manjiuqi.mcsmsdk.entity.daemon

import kotlinx.serialization.Serializable

@Serializable
data class DaemonCreate(
    val status: Int,
    val data: String,
    val time: Long
)

@Serializable
data class DaemonCreateQuery(
    val ip: String,
    val port: Int,
    val prefix: String,
    val apikey: String,
    val remarks: String
)