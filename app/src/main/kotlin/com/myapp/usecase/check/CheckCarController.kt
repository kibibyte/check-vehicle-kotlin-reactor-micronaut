package com.myapp.usecase.check;

import com.myapp.filters.MDCFilter
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import jakarta.validation.Valid
import org.slf4j.MDC
import reactor.core.publisher.Mono

@Controller
open class CheckCarController(var checkCarService: CheckCarService) {

  @Post(value = "/check", consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
  open fun check(@Valid @Body request: CheckCarRequest): Mono<CheckCarResponse> {
    val checkCarQuery = CheckCarQuery(request.vin, request.features);

    return checkCarService.check(checkCarQuery)
      .map(this::toCheckCarResponse);
  }

  fun toCheckCarResponse(result: CheckCarResult): CheckCarResponse {
    return CheckCarResponse(
      requestId = MDC.get(MDCFilter.REQUEST_ID),
      vin = result.vin,
      accidentFree = result.accidentFree,
      maintenanceScore = result.maintenanceScore
    )
  }
}