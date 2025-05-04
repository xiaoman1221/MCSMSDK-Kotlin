package com.manjiuqi.mcsmsdk.entity.instance

import com.manjiuqi.mcsmsdk.entity.InstanceConfig
import kotlinx.serialization.Serializable


@Serializable
data class InstancesCreate(
    val status: Int,
    val data: Data,
    val time: Long,
) {
    @Serializable
    data class Data(
        val instanceUuid: String,
        val config: InstanceConfig,
    )
}
