package piconnect

enum class HttpMethod(val method: String) {
    GET("GET"), POST("POST"), UPDATE("UPDATE"), DELETE("DELETE"), PATCH("PATCH")
}