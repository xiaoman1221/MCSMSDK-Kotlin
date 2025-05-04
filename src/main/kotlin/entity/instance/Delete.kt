package com.manjiuqi.mcsmsdk.entity.instance

import kotlinx.serialization.Serializable


@Serializable
data class InstancesDelete(
    val uuids: List<String>,
    val deleteFile: Boolean
)
