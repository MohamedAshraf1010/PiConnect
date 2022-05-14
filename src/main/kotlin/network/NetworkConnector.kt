package network

import piconnect.HttpMethod

interface NetworkConnector {

    fun setBaseUrl(url: String)

    fun enableLogs(enable: Boolean)

    fun <T> rest(api: String = "", httpMethod: HttpMethod = HttpMethod.GET, headers: Map<String, String> = mapOf(), queries: Map<String, String> = mapOf(), body: Any? = null, type: Class<T>): T

    fun <T> form(api: String = "", httpMethod: HttpMethod = HttpMethod.POST, headers: Map<String, String> = mapOf(), queries: Map<String, String> = mapOf(), formParams: Map<String, String> = mapOf(), type: Class<T>): T
}