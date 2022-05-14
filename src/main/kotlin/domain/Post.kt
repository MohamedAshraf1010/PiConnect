package domain

import network.*

class Post constructor(private val api: String) {

    private val networkConnector: NetworkConnector = NetworkConnectorImplementation

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, body: Any?): T {
        var response = ""
        return "" as T
    }
}