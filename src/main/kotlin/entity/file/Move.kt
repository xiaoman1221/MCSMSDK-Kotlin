package com.manjiuqi.mcsmsdk.entity.file

import kotlinx.serialization.Serializable

@Serializable
data class MoveFile(
    val targets: List<List<String>>,
)