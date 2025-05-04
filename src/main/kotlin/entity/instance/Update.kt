package com.manjiuqi.mcsmsdk.entity.instance

import kotlinx.serialization.Serializable

@Serializable
data class InstanceUpdate(
    val status: Int,
    val data: Data,
    val time: Long,
) {
    @Serializable
    data class Data(
        val instanceUuid: String
    )
}
