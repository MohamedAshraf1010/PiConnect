package domain

import network.*
import piconnect.HttpMethod

class Post constructor(private val api: String) {

    private val networkConnector: NetworkConnector = NetworkConnectorImplementation

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, body: Any?, responseType: Class<T>): T = networkConnector.connect(api = api, httpMethod = HttpMethod.POST, headers = headers, queries = queries, body = body, type = responseType)
}