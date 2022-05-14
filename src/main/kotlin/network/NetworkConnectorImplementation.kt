package network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import piconnect.HttpMethod
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
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

    override fun <T> connect(api: String, httpMethod: HttpMethod, headers: Map<String, String>, queries: Map<String, String>, body: Any?): T {
        try {
            val urlQueries = prepareQueries(queries)
            val url = URL("$baseUrl/$api$urlQueries")
            log("URL", url.toString())
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = httpMethod.method
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")
            headers.forEach { (key, value) -> connection.setRequestProperty(key, value) }
            log("Headers", connection.requestProperties.toString())
            val payload = body.let { gson.toJson(body) } ?: ""
            log("Body", payload)
            val writer = BufferedWriter(OutputStreamWriter(connection.outputStream))
            writer.write(payload)
            writer.flush()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val out = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                out.append(line)
                line = reader.readLine()
            }
            val response = out.toString()
            log("Response", response)
            val collectionType = object : TypeToken<T>() {}.type
            return gson.fromJson(response, collectionType)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    private fun prepareQueries(queries: Map<String, String>): String {
        var urlQueries = ""
        if (queries.isNotEmpty()) {
            queries.forEach { (key, value) ->
                urlQueries = if (urlQueries.isEmpty()) "?$urlQueries$key=$value" else "$urlQueries&&$key=$value"
            }
        }
        return urlQueries
    }

    private fun log(key: String, value: String) = println("PiConnect: $key --> $value")
}