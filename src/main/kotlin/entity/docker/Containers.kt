package com.manjiuqi.mcsmsdk.entity.docker

import kotlinx.serialization.Serializable

@Serializable
data class DockerContainers(
    val status: Int,
    val data: DockerImageList,
    val time: Long
)
