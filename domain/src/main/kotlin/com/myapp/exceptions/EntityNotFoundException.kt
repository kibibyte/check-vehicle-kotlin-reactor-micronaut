package com.myapp.exceptions;

class EntityNotFoundException(code: String, message: String?) : DomainException(code, message);
