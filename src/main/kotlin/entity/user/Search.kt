package com.manjiuqi.mcsmsdk.entity.user

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// 获取 用户 列表
@Serializable
data class Search(
    val status: Int,
    val data: Data,
    val time: Long,
) {
    @Serializable
    data class Data(
        val page: Int,
        val pageSize: Int,
        val maxPage: Int,
        val total: Int,
        val data: List<UserData>? = null,

        )

    @Serializable
    data class UserData(
        val uuid: String,
        val userName: String,
        val passWord: String,
        val passWordType: String,
        val salt: String?,
        val permission: Int,
        val registerTime: String,
        val loginTime: String,
        val instances: List<Instance>,
        val apiKey: String,
        val isInit: Boolean,
        val secret: String?,
        val open2FA: Boolean
    )

    @Serializable
    data class Instance(
        val instanceUuid: String,
        val daemonId: String,
    )
}

// 用户列表 查询参数
data class SearchQuery(
    val userName: String? = null,
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    val role: String? = null,
//    1 = 用户 ，10 = 管理员 ， -1 = 封禁用户
)