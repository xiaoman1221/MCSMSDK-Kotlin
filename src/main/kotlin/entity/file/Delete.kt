package com.manjiuqi.mcsmsdk.entity.file

import kotlinx.serialization.Serializable

@Serializable
data class FileDelete(
    val targets: List<String>,
)
