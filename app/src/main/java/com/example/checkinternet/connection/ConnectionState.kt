package com.example.checkinternet.connection

sealed class ConnectionState{
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
