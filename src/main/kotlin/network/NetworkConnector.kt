package network

import piconnect.HttpMethod

interface NetworkConnector {

    fun setBaseUrl(url: String)

    fun enableLogs(enable: Boolean)

    fun <T> connect(api: String = "", httpMethod: HttpMethod = HttpMethod.GET, headers: Map<String, String> = mapOf(), queries: Map<String, String> = mapOf(), body: Any? = null): T
}