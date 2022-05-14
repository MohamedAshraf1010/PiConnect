package domain

import network.*
import piconnect.HttpMethod

class Delete constructor(private val api: String) {

    private val networkConnector: NetworkConnector = NetworkConnectorImplementation

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, body: Any?, responseType: Class<T>): T = networkConnector.rest(api = api, httpMethod = HttpMethod.DELETE, headers = headers, queries = queries, body = body, type = responseType)
}