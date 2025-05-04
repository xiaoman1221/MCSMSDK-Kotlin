package com.manjiuqi.mcsmsdk.entity.user

import com.manjiuqi.mcsmsdk.entity.InstanceDetail
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdate(
    val status: Int,
    val data: Boolean,
    val time: Long
)

@Serializable
data class UserUpdateQuery(
    val uuid: String,
    val config: Config
) {
    @Serializable
    data class Config(
        val uuid: String,
        val userName: String? = null,
        val permission: Int? = null,
        val loginTime: String? = null,
        val registerTime: String? = null,
        val instanceDetail: List<InstanceDetail>? = null,
        val apiKey: String? = null,
        val isInit: Boolean? = null,
        val secret: String? = null,
        val open2FA: Boolean? = null
    )
}
