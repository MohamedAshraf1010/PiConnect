package network

import com.google.gson.Gson
import com.google.gson.JsonParseException
import piconnect.HttpMethod
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object NetworkConnectorImplementation: NetworkConnector {

    private val gson = Gson()
    private var baseUrl = ""
    private var logsEnabled = false

    override fun setBaseUrl(url: String) {
        baseUrl = url
    }

    override fun enableLogs(enable: Boolean) {
        logsEnabled = enable
    }

    override fun <T> rest(api: String, httpMethod: HttpMethod, headers: Map<String, String>, queries: Map<String, String>, body: Any?, type: Class<T>): T {
        try {
            val connection = creatConnection(api, httpMethod, headers, queries)
            connection.setRequestProperty("Content-Type", "application/json")
            log("Headers", connection.requestProperties.toString())
            body?.let {
                val payload = gson.toJson(body)
                log("Body", payload)
                val writer = BufferedWriter(OutputStreamWriter(connection.outputStream))
                writer.write(payload)
                writer.flush()
            }
            val response = connect(connection)
            return handleResponse(response, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun <T> form(api: String, httpMethod: HttpMethod, headers: Map<String, String>, queries: Map<String, String>, formParams: Map<String, String>, type: Class<T>): T {
        try {
            val connection = creatConnection(api, httpMethod, headers, queries)
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            log("Headers", connection.requestProperties.toString())
            var parameters = ""
            formParams.forEach { (key, value) -> parameters = if (parameters.isEmpty()) "$parameters$key=$value" else "$parameters&$key=$value" }
            log("Form Params", parameters)
            val writer = DataOutputStream(connection.outputStream)
            writer.write(parameters.toByteArray())
            writer.flush()
            val response = connect(connection)
            return handleResponse(response, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    private fun creatConnection(api: String, httpMethod: HttpMethod, headers: Map<String, String>, queries: Map<String, String>): HttpsURLConnection {
        val urlQueries = prepareQueries(queries)
        val url = URL("$baseUrl/$api$urlQueries")
        log("URL", url.toString())
        log("Method", httpMethod.method)
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = httpMethod.method
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("charset", "utf-8")
        headers.forEach { (key, value) -> connection.setRequestProperty(key, value) }
        return connection
    }

    private fun connect(connection: HttpsURLConnection): String {
        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val out = StringBuilder()
        var line = reader.readLine()
        while (line != null) {
            out.append(line)
            line = reader.readLine()
        }
        connection.disconnect()
        val response = out.toString()
        log("Response", response)
        return response
    }

    private fun <T> handleResponse(response: String, type: Class<T>): T {
        return try {
            gson.fromJson(response, type)
        } catch (ex: JsonParseException) {
            if (type == String::class.java) return response as T
            else throw ex
        }
    }

    private fun prepareQueries(queries: Map<String, String>): String {
        var urlQueries = ""
        if (queries.isNotEmpty()) {
            queries.forEach { (key, value) ->
                urlQueries = if (urlQueries.isEmpty()) "?$urlQueries$key=$value" else "$urlQueries&$key=$value"
            }
        }
        return urlQueries
    }

    private fun log(key: String, value: String) {
        if (logsEnabled) println("PiConnect: $key --> $value")
    }
}