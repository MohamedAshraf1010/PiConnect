package piconnect

import com.google.gson.annotations.SerializedName

fun main() {
    val network = PiConnect.Builder().baseUrl("https://reqres.in/api").enableLogs(true).build()
    val response = network.rest("users/2", httpMethod = HttpMethod.GET, responseType = Response::class.java)
    println(response.user.email)
}

data class Response (
    @SerializedName("data") var user: User,
    @SerializedName("reply") var reply: ArrayList<User>,
)

class User {
    @SerializedName("id") var id: String = ""
    @SerializedName("email") var email: String = ""
    @SerializedName("name") var name: String = ""
    @SerializedName("first_name") var first_name: String = ""
    @SerializedName("last_name") var last_name: String = ""
    @SerializedName("avatar") var avatar: String = ""
    @SerializedName("job") var job: String = ""
}