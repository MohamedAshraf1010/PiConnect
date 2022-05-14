package domain

import network.*
import piconnect.HttpMethod

class Patch constructor(private val api: String) {

    private val networkConnector: NetworkConnector = NetworkConnectorImplementation

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, body: Any?, responseType: Class<T>): T = networkConnector.connect(api = api, httpMethod = HttpMethod.POST, headers = headers.plus(Pair("X-HTTP-Method-Override", "PATCH")), queries = queries, body = body, type = responseType)
}