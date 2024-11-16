package com.myapp.usecase.check;

import com.myapp.exceptions.EntityNotFoundException;
import com.myapp.exceptions.InvalidArgumentException;
import com.myapp.exceptions.RestRepositoryException;

class CheckCarExceptions {
  companion object {
    fun vinNotFound(): EntityNotFoundException {
      val code = ErrorCodes.VIN_NOT_FOUND;

      return EntityNotFoundException(code.name, code.message);
    }

    fun invalidArgument(): InvalidArgumentException {
      val code = ErrorCodes.INVALID_ARGUMENT;

      return InvalidArgumentException(code.name, code.message);
    }

    fun restRepositoryException(): RestRepositoryException {
      val code = ErrorCodes.SERVICE_UNAVAILABLE;

      return RestRepositoryException(code.name, code.message);
    }
  }

  enum class ErrorCodes(val message: String) {
    VIN_NOT_FOUND("Vin not found"),
    INVALID_ARGUMENT("Invalid argument"),
    SERVICE_UNAVAILABLE("Service unavailable");
  }
}
