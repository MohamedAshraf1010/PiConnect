package domain

class Update constructor(private val api: String) {

    fun <T> run(headers: Map<String, String>, queries: Map<String, String>, body: Any?): T {
        var response = ""
        return "" as T
    }
}