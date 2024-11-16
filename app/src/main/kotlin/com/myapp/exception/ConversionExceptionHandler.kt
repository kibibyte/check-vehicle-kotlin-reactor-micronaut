package com.myapp.exception;

import io.micronaut.context.annotation.Replaces
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ConversionErrorHandler
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Replaces(ConversionErrorHandler::class)
class ConversionExceptionHandler : ExceptionHandler<ConversionErrorException, HttpResponse<ErrorResponse>> {

  override fun handle(request: HttpRequest<*>?, exception: ConversionErrorException?): HttpResponse<ErrorResponse> {
    return HttpResponse.badRequest(ErrorResponse.of("INVALID_INPUT", "Invalid input"));
  }
}