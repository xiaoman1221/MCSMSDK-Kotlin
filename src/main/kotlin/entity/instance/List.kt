package com.manjiuqi.mcsmsdk.entity.instance

import com.manjiuqi.mcsmsdk.entity.InstanceDetail
import kotlinx.serialization.Serializable

// 实例列表
@Serializable
data class InstancesList(
    val status: Int,
    val data: Data,
    val allTags: List<String>?,
    val time: Long
) {
    @Serializable
    data class Data(
        val maxPage: Int,
        val pageSize: Int,
        val data: List<InstanceDetail>
    )
}
