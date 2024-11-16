package com.myapp.exceptions

open class DomainException(val code: String, message: String?) : RuntimeException(message)