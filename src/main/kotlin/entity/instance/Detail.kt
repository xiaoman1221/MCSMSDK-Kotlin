package com.manjiuqi.mcsmsdk.entity.instance

import com.manjiuqi.mcsmsdk.entity.InstanceDetail
import kotlinx.serialization.Serializable


@Serializable
data class InstancesDetail(
    val status: Long,
    val data: List<InstanceDetail>,
    val time: Long
)
