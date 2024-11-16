package com.myapp.exceptions;

open class InvalidArgumentException(code: String, message: String?) : DomainException(code, message)
