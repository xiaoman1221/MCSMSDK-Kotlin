package com.manjiuqi.mcsmsdk.entity.daemon

import kotlinx.serialization.Serializable

@Serializable
data class DaemonDelete(
    val status: Int,
    val data: Boolean,
    val time: Long
)
