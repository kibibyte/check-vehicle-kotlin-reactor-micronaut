package com.myapp.exception;

import com.myapp.exceptions.DomainException;
import com.myapp.exceptions.EntityNotFoundException;
import com.myapp.exceptions.InvalidArgumentException;
import com.myapp.exceptions.RestRepositoryException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
class DomainExceptionHandler : ExceptionHandler<DomainException, HttpResponse<ErrorResponse>> {

  @Override
  override fun handle(request: HttpRequest<*>?, e: DomainException): HttpResponse<ErrorResponse> {
    if (e is EntityNotFoundException) {
      return HttpResponse.notFound(ErrorResponse.of(e.code, e.message));
    }
    if (e is InvalidArgumentException) {
      return HttpResponse.badRequest(ErrorResponse.of(e.code, e.message));
    }
    if (e is RestRepositoryException) {
      return HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE);
    }

    return HttpResponse.serverError(ErrorResponse.of(e.code, e.message));
  }
}