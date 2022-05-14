package domain

import network.*

class SetBaseURL constructor(private val networkConnector: NetworkConnector = NetworkConnectorImplementation) {

    fun set(url: String) = networkConnector.setBaseUrl(url)
}