package com.myapp.usecase.check;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
data class CheckCarResponse(
  val requestId: String,
  val vin: String,
  val accidentFree: Boolean?,
  val maintenanceScore: MaintenanceScore?
)