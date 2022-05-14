package domain

import network.*
import piconnect.HttpMethod

class Post constructor(private val api: String) {

    private val networkConnector: NetworkConnector = NetworkConnectorImplementation

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, body: Any? = null, formParams: Map<String, String> = mapOf(), isForm: Boolean = false, responseType: Class<T>): T {
        return if (isForm) networkConnector.form(api = api, httpMethod = HttpMethod.POST, headers = headers, queries = queries, formParams = formParams, type = responseType)
        else networkConnector.rest(api = api, httpMethod = HttpMethod.POST, headers = headers, queries = queries, body = body, type = responseType)
    }
}