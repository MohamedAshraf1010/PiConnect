package piconnect

import domain.*

class PiConnect private constructor(baseUrl: String, logsEnabled: Boolean) {

    init {
        SetBaseURL().set(baseUrl)
        EnableLogs().set(logsEnabled)
    }

    fun <T> rest(api: String = "", httpMethod: HttpMethod = HttpMethod.GET, headers: Map<String, String> = mapOf(), queries: Map<String, String> = mapOf(), body: Any? = null): T {
        return when (httpMethod) {
            HttpMethod.GET -> Get(api).run(headers, queries)
            HttpMethod.POST -> Post(api).run(headers, queries, body)
            HttpMethod.UPDATE -> Update(api).run(headers, queries, body)
            HttpMethod.DELETE -> Delete(api).run(headers, queries, body)
            HttpMethod.PATCH -> Patch(api).run(headers, queries, body)
        }
    }

    companion object {
        class Builder {
            private var baseUrl = ""
            private var logsEnabled = false

            fun baseUrl(baseUrl: String) {
                this.baseUrl = baseUrl
            }

            fun enableLogs(enable: Boolean) {
                this.logsEnabled = enable
            }

            fun build(): PiConnect = PiConnect(baseUrl, logsEnabled)
        }
    }
}