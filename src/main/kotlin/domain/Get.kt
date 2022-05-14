package domain

import network.*

class Get constructor(private val api: String) {

    private val networkConnector: NetworkConnector = NetworkConnectorImplementation

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, responseType: Class<T>): T = networkConnector.connect(api = api, headers = headers, queries = queries, type = responseType)
}