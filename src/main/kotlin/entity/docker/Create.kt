package com.manjiuqi.mcsmsdk.entity.docker

import kotlinx.serialization.Serializable

@Serializable
data class DockerCreateImage(
    val name: String,
    val tag: String,
    val dockerfile: String? = null,
)
