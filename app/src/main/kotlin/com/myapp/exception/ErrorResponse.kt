package com.myapp.exception;

import com.myapp.exceptions.DomainException
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorResponse(val code: String, val message: String?) {
  companion object {
    fun of(exception: DomainException): ErrorResponse {
      return ErrorResponse(exception.code, exception.message)
    }

    fun of(code: String, message: String?): ErrorResponse {
      return ErrorResponse(code, message);
    }
  }
}
