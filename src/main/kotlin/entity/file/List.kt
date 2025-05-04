package com.manjiuqi.mcsmsdk.entity.file

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FileList(
    val status: Long,
    val data: Data,
    val time: Long
) {
    @Serializable
    data class Data(
        val items: List<Item>,
        val page: Long,
        val pageSize: Long,
        val total: Long,
        val absolutePath: String
    )

    @Serializable
    data class Item(
        val name: String,
        val size: Long,
        val time: String,
        val mode: Long,
        val type: Long
    )
}

@Serializable
data class FileListQuery(
    val daemonId: String,
    val uuid: String,
    val target: String,
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int
)