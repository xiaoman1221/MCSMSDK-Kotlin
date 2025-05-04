package com.manjiuqi.mcsmsdk.entity.docker

import kotlinx.serialization.Serializable

@Serializable
data class Progress(
    val status: Int,
    val data: MutableMap<String, Int>,
    val time: Long,
)