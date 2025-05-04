package com.manjiuqi.mcsmsdk.entity.file

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FileContent(
    val status: Int,
    val data: String,
    val time: Long,
)

@Serializable
data class UpdateFileContent(
    val target: String,
    val text: String
)

@Serializable
data class DownloadFileQuery(
    val uuid: String,
    val daemonId: String,
    @SerializedName("file_name")
    val fileName: String,
)

@Serializable
data class FilePassword(
    val status: Int,
    val data: DownloadFileData,
    val time: Long,
) {
    @Serializable
    data class DownloadFileData(
        val password: String,
        val addr: String,
    )
}