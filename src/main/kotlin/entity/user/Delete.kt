package com.manjiuqi.mcsmsdk.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDelete(
    val status: Int,
    val data: String,
    val time: Long
)