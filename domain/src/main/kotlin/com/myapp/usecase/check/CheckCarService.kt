package com.myapp.usecase.check;

import com.myapp.usecase.check.CheckCarFeature.ACCIDENT_FREE
import com.myapp.usecase.check.CheckCarFeature.MAINTENANCE
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.util.*

@Singleton
class CheckCarService(private val checkCarRepository: CheckCarRepository) {
  companion object {
    private val log = LoggerFactory.getLogger(CheckCarService::class.java)
  }

  fun check(checkCarQuery: CheckCarQuery): Mono<CheckCarResult> {
    val vin = checkCarQuery.vin

    val findNumberOfAccidents: Mono<Optional<Int>> = checkCarRepository
      .findNumberOfAccidents(vin)
      .doOnSubscribe { _ -> log.info("Find number of accidents requested") }

    val findMaintenanceFrequency: Mono<Optional<MaintenanceFrequency>> = checkCarRepository
      .findMaintenanceFrequency(vin)
      .doOnSubscribe { _ -> log.info("Find maintenance frequency requested") };

    val findAllFeatures: Mono<Tuple2<Optional<Int>, Optional<MaintenanceFrequency>>> = Mono.zip(
      if (checkCarQuery.isCheckFeature(ACCIDENT_FREE)) findNumberOfAccidents else Mono.just(Optional.empty()),
      if (checkCarQuery.isCheckFeature(MAINTENANCE)) findMaintenanceFrequency else Mono.just(Optional.empty())
    )

    return findAllFeatures.map { tuple ->
      CheckCarResult(vin,
        tuple.t1.orElse(null),
        tuple.t2.orElse(null))
    }
  }
}
