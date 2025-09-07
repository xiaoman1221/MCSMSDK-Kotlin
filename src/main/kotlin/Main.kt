package com.manjiuqi.mcsmsdk

import cn.rtast.rob.common.http.Http
import cn.rtast.rutil.string.toJson
import com.manjiuqi.mcsmsdk.entity.Dashboard
import com.manjiuqi.mcsmsdk.entity.InstanceConfig
import com.manjiuqi.mcsmsdk.entity.daemon.DaemonCreate
import com.manjiuqi.mcsmsdk.entity.daemon.DaemonCreateQuery
import com.manjiuqi.mcsmsdk.entity.daemon.DaemonDelete
import com.manjiuqi.mcsmsdk.entity.docker.*
import com.manjiuqi.mcsmsdk.entity.file.*
import com.manjiuqi.mcsmsdk.entity.instance.InstanceUpdate
import com.manjiuqi.mcsmsdk.entity.instance.InstancesCreate
import com.manjiuqi.mcsmsdk.entity.instance.InstancesDelete
import com.manjiuqi.mcsmsdk.entity.instance.InstancesList
import com.manjiuqi.mcsmsdk.entity.user.*

class MCSM(
    val host: String,
    val port: Int,
    val apikey: String,
) {
    /**
     * 构建查询 URI
     */
    fun query(q: String, p: String? = ""): String {
        try {
            val url = "${host}:${port}${q}?apikey=${apikey}${p}"
            return url
        } catch (_: Exception) {
            return "URI构建失败请联系管理"
        }
    }

    /**
     *  获取 仪表盘概览数据
     */
    fun overview(): Dashboard? {
        try {
            val data = query("/api/overview").let { Http.get<Dashboard>(it) }
            return data
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * 获取 用户 列表
     */
    fun userList(page: Int = 1, pageSite: Int = 10, role: Int? = null): Search? {
        try {
            if (role == null) {
                val data = query(
                    "/api/auth/search",
                    "&userName=&page=$page&page_size=$pageSite&role="
                ).let { Http.get<Search>(it) }
                return data
            }
            val data = query(
                "/api/auth/search",
                "&userName=&page=$page&page_size=$pageSite&role=$role"
            ).let { Http.get<Search>(it) }
            return data
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * 创建 用户
     */
    fun createUser(user: String, pass: String, permission: Int = 1): UserCreate? {
        try {
            val data = query("/api/auth").let {
                val res = UserCreateQuery(user, pass, permission).toJson()
                Http.post<UserCreate>(
                    it,
                    res
                )
            }
            return data
        } catch (_: Exception) {
            println(query("/api/auth"))
            println(UserCreateQuery(user, pass, permission).toJson())
            return null
        }
    }

    /**
     * 删除用户
     */
    fun deleteUser(user: String): Boolean {
        try {
            val user = searchUser(user)
            if (user != null && user.status == 200 && user.data.total == 1) {
                val data = user.data.data?.get(0)?.uuid
                val resp = query("/api/auth").let { Http.delete<UserDelete>(it, "[\"$data\"]") }
                return resp.status == 200
            } else {
                return false
            }
        } catch (w: Exception) {
            println(w.toString())
            return false
        }
    }

    /**
     * 搜索用户
     */
    fun searchUser(userName: String, page: Int = 1, pageSite: Int = 10, role: Int? = null): Search? {
        try {
            if (role == null) {
                val data = query(
                    "/api/auth/search",
                    "&userName=$userName&page=$page&page_size=$pageSite&role="
                ).let { Http.get<Search>(it) }
                return data
            }
            val data = query(
                "/api/auth/search",
                "&userName=$userName&page=$page&page_size=$pageSite&role=$role"
            ).let { Http.get<Search>(it) }
            return data
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * 更新 用户
     */
    fun updateUser(uuid: String, userName: String? = null, password: String? = null, role: Int? = null): UserUpdate? {
        val user = userList()
        if (user != null && user.status == 200 && user.data.total == 1 && user.data.data != null) {
            user.data.data.forEach { data ->
                if (data.uuid == uuid) {
                    val userUpdateQuery = userName?.let {
                        UserUpdateQuery(
                            uuid,
                            config = UserUpdateQuery.Config(uuid, userName, role),
                        )
                    }
                    try {
                        val data = query("/api/auth/update").let {
                            userUpdateQuery?.let { it1 -> Http.put<UserUpdate>(it, it1.toJson()) }
                        }
                        return data
                    } catch (_: Exception) {
                        return null
                    }
                }
            }

        }
        return null
    }

    /**
     * 获取 指定节点的实例列表
     */
    fun instancesListByDaemonId(daemonId: String): InstancesList? {
        try {
            val data = query(
                "/api/service/remote_service_instances",
                "&daemonId=${daemonId}&page=1&page_size=10&status=&instance_name="
            ).let { Http.get<InstancesList>(it) }
            return data
        } catch (e: Exception) {
            println(e.toString())
            return null
        }
    }

    /**
     * 获取 所有节点的所有列表
     */
    fun instancesList(): List<InstancesList>? {
        try {
            val uuids = mutableListOf<String>()
            val data = mutableListOf<InstancesList>()
            overview()?.data?.remote?.forEach {
                uuids.add(it.uuid)
            }
            uuids.forEach {
                val tmp = query(
                    "/api/service/remote_service_instances",
                    "&daemonId=${it}&page=1&page_size=10&status=&instance_name="
                ).let { it -> Http.get<InstancesList>(it) }
                tmp.let { element -> data.add(element) }
            }
            return data
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * 创建 实例
     */
    fun createInstance(daemonId: String, instanceConfig: InstanceConfig): InstancesCreate {
        val data = query("/api/instance", "&daemonId=${daemonId}").let {
            Http.post<InstancesCreate>(
                it,
                instanceConfig.toJson()
            )
        }
        return data
    }

    /**
     * 更新 实例
     */
    fun updateInstance(daemonId: String, uuid: String, instanceConfig: InstanceConfig): InstanceUpdate {
        val data = query("/api/instance", "&uuid=${uuid}&daemonId=${daemonId}").let {
            Http.put<InstanceUpdate>(
                it,
                instanceConfig.toJson()
            )
        }
        return data
    }

    /**
     * 删除 实例
     */
    fun deleteInstance(daemonId: String, uuids: List<String>, deleteFile: Boolean = false): String {
        val res = InstancesDelete(uuids, deleteFile).toJson()
        val data = query("/api/instance", "&daemonId=${daemonId}").let { Http.delete(it, res) }
        return data
    }

    /**
     * 启动 实例
     */
    fun startInstance(uuid: String, daemonId: String): Boolean {
        val data = query("/api/protected_instance/open", "&uuid=${uuid}&daemonId=${daemonId}").let {
            Http.get<InstanceUpdate>(it)
        }
        return data.status == 200
    }

    /**
     * 停止 实例
     */
    fun stopInstance(uuid: String, daemonId: String): Boolean {
        val data = query("/api/protected_instance/stop", "&uuid=${uuid}&daemonId=${daemonId}").let {
            Http.get<InstanceUpdate>(it)
        }
        return data.status == 200
    }

    /**
     *  重启 实例
     */
    fun restartInstance(uuid: String, daemonId: String): Boolean {
        val data = query(
            "/api/protected_instance/restart",
            "&uuid=${uuid}&daemonId=${daemonId}"
        ).let { Http.get<InstanceUpdate>(it) }
        return data.status == 200
    }

    /**
     *  强制结束 实例
     */
    fun killInstance(uuid: String, daemonId: String): Boolean {
        val data = query("/api/protected_instance/kill", "&uuid=${uuid}&daemonId=${daemonId}").let {
            Http.get<InstanceUpdate>(it)
        }
        return data.status == 200
    }

    /**
     * 获取后端节点列表
     */
    fun daemonList(): List<Dashboard.Remote>? {
        val overflow = overview()
        if (overflow != null) {
            return overflow.data?.remote
        }
        return null
    }

    /**
     * 添加 后端节点
     */
    fun addDaemon(ip: String, port: Int, prefix: String = "", remarks: String = "", apikey: String): DaemonCreate {
        val res = DaemonCreateQuery(ip, port, prefix, remarks, apikey).toJson()
        val data = query("api/service/remote_service").let { Http.post<DaemonCreate>(it, res) }
        return data
    }

    /**
     * 删除 后端节点
     */
    fun deleteDaemon(uuid: String): Boolean {
        val data = query("/api/service/remote_service").let { Http.delete<DaemonDelete>(it, "{uuid: $uuid}") }
        return data.data
    }

    /**
     * 链接 后端节点
     */
    fun connectDaemon(uuid: String): Boolean {
        val data = query("/api/service/link_remote_service", "&uuid=${uuid}").let { Http.get<DaemonDelete>(it) }
        return data.data
    }

    /**
     * 更新 后端节点
     */
    fun updateDaemon(
        uuid: String,
        ip: String,
        port: Int,
        prefix: String = "",
        remarks: String = "",
        apikey: String
    ): Boolean {
        val res = DaemonCreateQuery(ip, port, prefix, remarks, apikey).toJson()
        val data = query("/api/service/remote_service", "&uuid=${uuid}").let { Http.put<DaemonDelete>(it, res) }
        return data.data
    }

    /**
     * 获取 文件列表
     */
    fun fileList(daemonId: String, uuid: String, target: String = "/", page: Int, pageSite: Int): FileList {
        val res = FileListQuery(daemonId, uuid, target, page, pageSite).toJson()
        val data = query("/api/files/list", res).let { Http.get<FileList>(it) }
        return data
    }

    /**
     * 获取 文件内容
     */
    fun fileContent(uuid: String, daemonId: String, target: String): FileContent {
        val data = query("/api/files/", "&daemonId=${daemonId}&uuid=${uuid}").let {
            Http.put<FileContent>(
                it,
                "{\"target\":$target\"}"
            )
        }
        return data
    }

    /**
     * 更新 文件
     */
    fun updateFileContent(uuid: String, daemonId: String, fileContent: String, target: String = "/"): Boolean {
        val res = UpdateFileContent(target, fileContent).toJson()
        val data = query("/api/files/", "&uuid=${uuid}&daemonId=${daemonId}").let { Http.put<DaemonDelete>(it, res) }
        return data.data
    }

    /**
     * 下载 文件
     */
    fun downloadFile(uuid: String, daemonId: String, target: String): String? {
        try {
            val data = query(
                "/api/files/download",
                "&file_name=$target&daemonId=$daemonId&uuid=$uuid"
            ).let { Http.get<FilePassword>(it) }
            return if (data.status == 200) {
                return if (data.data.addr == "localhost:24444") {
                    "$host:24444/download/${data.data.password}${target}"
                } else {
                    "$host/download/${data.data.password}${target}"
                }
            } else {
                null
            }
        } catch (e: Exception) {
            println(e)
            return null
        }
    }

    /**
     * 上传 文件
     */
    fun uploadFile(uuid: String, daemonId: String, target: String): String? {
        try {
            val data = query(
                "/api/files/upload",
                "&upload_dir=$target&daemonId=$daemonId&uuid=$uuid"
            ).let { Http.get<FilePassword>(it) }
            return if (data.status == 200) {
                return if (data.data.addr == "localhost:24444") {
                    "$host:24444/upload/${data.data.password}"
                } else {
                    "$host/upload/${data.data.password}"
                }
            } else {
                null
            }
        } catch (e: Exception) {
            println(e)
            return null
        }
    }

    /**
     * 删除 文件
     */
    fun deleteFile(uuid: String, daemonId: String, targets: List<String>): Boolean {
        try {
            val res = FileDelete(targets).toJson()
            val data = query("/api/files", "&daemonId=$daemonId&uuid=$uuid").let { Http.delete<DaemonDelete>(it, res) }
            return data.status == 200
        } catch (e: Exception) {
            println(e)
            return false
        }
    }

    /**
     * 创建 空文件
     */
    fun touchFile(uuid: String, daemonId: String, target: String): Boolean {
        try {
            val data = query("/api/files/touch", "&daemonId=$daemonId&uuid=$uuid").let {
                Http.post<DaemonDelete>(
                    it,
                    "{\"target\": \"${target}\"}"
                )
            }
            return data.status == 200
        } catch (e: Exception) {
            println(e)
            return false
        }
    }

    /**
     * 创建 文件夹
     */
    fun mkdir(uuid: String, daemonId: String, target: String): Boolean {
        try {
            val data = query("/api/files/mkdir", "&daemonId=$daemonId&uuid=$uuid").let {
                Http.post<DaemonDelete>(
                    it,
                    "{\"target\": \"${target}\"}"
                )
            }
            return data.status == 200
        } catch (e: Exception) {
            println(e)
            return false
        }
    }

    /**
     * 文件/文件夹 重命名/移动
     */
    fun mv(uuid: String, daemonId: String, targets: MoveFile): Boolean {
        try {
            val res = targets.toJson()
             val data = query("/api/files/move","&daemonId=$daemonId&uuid=$uuid").let { Http.put<DaemonDelete>(it,res) }
            return data.status == 200
        } catch (e: Exception) {
            println(e)
            return false
        }
    }

    /**
     * Docker 镜像列表
     */
    fun getDockerImages(daemonId: String): DockerImages {
        return query("/api/environment/image", "&daemonId=${daemonId}").let { Http.get<DockerImages>(it) }
    }

    /**
     * Docker 容器列表
     */
    fun getDockerContainers(daemonId: String): DockerContainers {
        return query("/api/environment/containers", "&daemonId=${daemonId}").let { Http.get<DockerContainers>(it) }
    }

    /**
     * Docker 网络接口列表
     */
    fun getDockerNetworks(daemonId: String): DockerNetwork {
        return query("/api/environment/network", "&daemonId=${daemonId}").let { Http.get<DockerNetwork>(it) }
    }

    /**
     * Docker 新增镜像
     */
    fun addDockerImage(daemonId: String, dockerfile: String? = null, name: String, tag: String): Boolean {
        val res = DockerCreateImage(name, tag, dockerfile)
        val data = query("/api/environment/image", "&daemonId=${daemonId}").let { Http.post<DaemonDelete>(it) }
        return if (data.status == 200) {
            data.data
        } else {
            false
        }
    }

    /**
     * Docker 获取构建进度
     */
    fun getDockerProgress(daemonId: String): Progress {
        return query("/api/environment/progress", "&daemonId=${daemonId}").let { Http.get<Progress>(it) }
    }
}

