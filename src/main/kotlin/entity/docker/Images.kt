package com.manjiuqi.mcsmsdk.entity.docker

import kotlinx.serialization.Serializable

@Serializable
data class DockerImages(
    val status: Long,
    val data: List<DockerImageList>,
    val time: Long
)
