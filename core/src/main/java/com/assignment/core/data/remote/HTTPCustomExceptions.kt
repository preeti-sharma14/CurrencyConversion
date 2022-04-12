package com.assignment.core.data.remote

import java.io.IOException

class HTTPNotFoundException constructor(message: String) : IOException(message) {
    constructor() : this("No data found")
}
class HTTPBadRequest constructor(message:String):IOException(message){
    constructor():this("Bad Request")
}
class ServerNotAvailableException(message: String):IOException(message)
class NetworkException(throwable: Throwable):IOException(throwable.message,throwable)