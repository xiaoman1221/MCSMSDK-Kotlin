package com.manjiuqi.mcsmsdk.entity.docker

import kotlinx.serialization.Serializable

@Serializable
data class DockerNetwork(
    val status: Int,
    val data: DockerNetworkList,
    val time: Long
)