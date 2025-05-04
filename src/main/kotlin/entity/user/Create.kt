package com.manjiuqi.mcsmsdk.entity.user

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


@Serializable
data class UserCreate(
    val status: Int,
    @SerializedName("data")
    val data: JsonElement,
    val time: Long,
)

@Serializable
data class UserCreateQuery(
    val username: String,
    val password: String,
    val permission: Int
)
