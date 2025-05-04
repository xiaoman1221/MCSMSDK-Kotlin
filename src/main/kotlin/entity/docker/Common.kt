package com.manjiuqi.mcsmsdk.entity.docker

import kotlinx.serialization.Serializable


@Serializable
data class DockerContainerList(
    val id: String,
    val names: List<String>,
    val image: String,
    val imageID: String,
    val command: String,
    val created: Long,
    val state: String,
    val status: String,
    val ports: List<Port>,
    val labels: Labels,
    val sizeRw: Long,
    val sizeRootFS: Long,
    val hostConfig: HostConfig,
    val networkSettings: NetworkSettings,
    val mounts: List<Mount>
) {
    @Serializable
    data class HostConfig(
        val networkMode: String
    )

    @Serializable
    data class Labels(
        val comExampleVendor: String? = null,
        val comExampleLicense: String? = null,
        val comExampleVersion: String? = null
    )

    @Serializable
    data class Mount(
        val name: String,
        val source: String,
        val destination: String,
        val driver: String,
        val mode: String,
        val rw: Boolean,
        val propagation: String
    )

    @Serializable
    data class NetworkSettings(
        val networks: Networks
    )

    @Serializable
    data class Networks(
        val bridge: Bridge
    )

    @Serializable
    data class Bridge(
        val networkID: String,
        val endpointID: String,
        val gateway: String,
        val ipAddress: String,
        val ipPrefixLen: Long,
        val iPv6Gateway: String,
        val globalIPv6Address: String,
        val globalIPv6PrefixLen: Long,
        val macAddress: String
    )

    @Serializable
    data class Port(
        val privatePort: Long,
        val publicPort: Long,
        val type: String
    )
}

@Serializable
data class DockerImageList(
    val containers: Long,
    val created: Long,
    val id: String,
    val labels: MutableMap<String, String>? = null,
    val parentID: String,
    val repoDigests: List<String>,
    val repoTags: List<String>,
    val sharedSize: Long,
    val size: Long
)

@Serializable
data class DockerNetworkList(
    val name: String,
    val id: String,
    val created: String,
    val scope: String,
    val driver: String,
    val enableIPv6: Boolean,
    val internal: Boolean,
    val attachable: Boolean,
    val ingress: Boolean,
    val ipam: IPAM,
    val options: Options,
    val containers: Containers? = null
) {
    @Serializable
    class Containers()

    @Serializable
    data class IPAM(
        val driver: String,
        val config: List<Config>
    )

    @Serializable
    data class Config(
        val subnet: String
    )

    @Serializable
    data class Options(
        val comDockerNetworkBridgeDefaultBridge: String? = null,
        val comDockerNetworkBridgeEnableIcc: String? = null,
        val comDockerNetworkBridgeEnableIPMasquerade: String? = null,
        val comDockerNetworkBridgeHostBindingIpv4: String? = null,
        val comDockerNetworkBridgeName: String? = null,
        val comDockerNetworkDriverMTU: String? = null
    )
}