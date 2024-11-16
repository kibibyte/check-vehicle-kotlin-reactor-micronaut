package com.myapp.usecase.check;

import reactor.core.publisher.Mono
import java.util.*

interface CheckCarRepository {

  fun findNumberOfAccidents(vin: String): Mono<Optional<Int>>

  fun findMaintenanceFrequency(vin: String): Mono<Optional<MaintenanceFrequency>>
}
