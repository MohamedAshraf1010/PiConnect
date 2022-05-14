package piconnect

import domain.*

class PiConnect private constructor(baseUrl: String, logsEnabled: Boolean) {

    init {
        SetBaseURL().set(baseUrl)
        EnableLogs().set(logsEnabled)
    }

    fun <T> rest(api: String = "", httpMethod: HttpMethod = HttpMethod.GET, headers: Map<String, String> = mapOf(), queries: Map<String, String> = mapOf(), body: Any? = null, responseType: Class<T>): T {
        return when (httpMethod) {
            HttpMethod.GET -> Get(api).run(headers = headers, queries = queries, responseType = responseType)
            HttpMethod.POST -> Post(api).run(headers = headers, queries = queries, body = body, responseType = responseType)
            HttpMethod.PUT -> Put(api).run(headers = headers, queries = queries, body = body, responseType = responseType)
            HttpMethod.DELETE -> Delete(api).run(headers = headers, queries = queries, body = body, responseType = responseType)
            HttpMethod.PATCH -> Patch(api).run(headers = headers, queries = queries, body = body, responseType = responseType)
        }
    }

    fun <T> form(api: String = "", headers: Map<String, String> = mapOf(), queries: Map<String, String> = mapOf(), formParams: Map<String, String> = mapOf(), responseType: Class<T>): T {
        return Post(api).run(headers = headers, queries = queries, formParams = formParams, isForm = true, responseType = responseType)
    }

    class Builder {
        private var baseUrl = ""
        private var logsEnabled = false

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun enableLogs(enable: Boolean): Builder {
            this.logsEnabled = enable
            return this
        }

        fun build(): PiConnect = PiConnect(baseUrl, logsEnabled)
    }
}