package com.myapp.usecase.check;

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Serdeable
data class CheckCarRequest(
  @NotBlank
  val vin: String,
  @NotNull
  @Size(min = 1, max = 2)
  val features: List<CheckCarFeature>
)