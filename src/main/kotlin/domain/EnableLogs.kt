package domain

import network.NetworkConnector
import network.NetworkConnectorImplementation

class EnableLogs  constructor(private val networkConnector: NetworkConnector = NetworkConnectorImplementation) {

    fun set(enabled: Boolean) = networkConnector.enableLogs(enabled)
}