package com.assignment.currencyConversion.main.data.network.remote

import java.io.IOException

class HTTPNotFoundException constructor(message:String):IOException(message){
    constructor():this("No data found")
}
class HTTPBadRequest constructor(message:String):IOException(message) {
    constructor() : this("Bad Request")
}
class AuthorizationException(message: String):IOException(message)