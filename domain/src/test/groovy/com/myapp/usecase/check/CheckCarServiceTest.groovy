package com.myapp.usecase.check

import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import static com.myapp.usecase.check.CheckCarFeature.ACCIDENT_FREE
import static com.myapp.usecase.check.CheckCarFeature.MAINTENANCE
import static com.myapp.usecase.check.MaintenanceFrequency.HIGH

class CheckCarServiceTest extends Specification {

  private final CheckCarRepository checkCarRepository = Mock()
  private final CheckCarService checkCarService = new CheckCarService(checkCarRepository)

  def "should check car with success"() {
    when:
    def vin = "1234"
    def checkCarQuery = new CheckCarQuery(vin, featuresToCheck)
    checkCarRepository.findNumberOfAccidents(vin) >> Mono.just(Optional.of(0))
    checkCarRepository.findMaintenanceFrequency(vin) >> Mono.just(Optional.of(HIGH))
    Mono<CheckCarResult> checkCarResult = checkCarService.check(checkCarQuery)

    then:
    StepVerifier.create(checkCarResult)
        .expectNext(expectedResult)
        .verifyComplete()

    where:
    featuresToCheck              || expectedResult
    [ACCIDENT_FREE, MAINTENANCE] || new CheckCarResult("1234", 0, HIGH)
    [ACCIDENT_FREE]              || CheckCarResult.of("1234", 0)
    [MAINTENANCE]                || CheckCarResult.of("1234", HIGH)
  }
}