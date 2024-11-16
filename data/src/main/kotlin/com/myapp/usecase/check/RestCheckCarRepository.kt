package com.myapp.usecase.check;

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import reactor.util.retry.RetryBackoffSpec
import java.time.Duration
import java.util.*

@Singleton
class RestCheckCarRepository(
  private val insuranceClient: InsuranceClient,
  private val maintenanceClient: MaintenanceClient
) : CheckCarRepository {

  companion object {
    private val log = LoggerFactory.getLogger(RestCheckCarRepository::class.java)
    const val RETRY_MAX_ATTEMPTS: Long = 2;
    const val RETRY_DELAY_SEC: Long = 2;
  }

  override fun findNumberOfAccidents(vin: String): Mono<Optional<Int>> {
    return insuranceClient.getReport(vin)
      .map { Optional.of(it.getClaims()) }
      .retryWhen(getRetrySpec())
      .onErrorResume { e -> onErrorResume(e, "Cannot get number of accidents") }
  }

  override fun findMaintenanceFrequency(vin: String): Mono<Optional<MaintenanceFrequency>> {
    return maintenanceClient.getReport(vin)
      .map { Optional.of(it.maintenanceFrequency) }
      .retryWhen(getRetrySpec())
      .onErrorResume { e -> onErrorResume(e, "Cannot get maintenance frequency") };
  }

  private fun <T> onErrorResume(e: Throwable, message: String): Mono<Optional<T & Any>> {
    if (is404Error(e)) {
      return Mono.just(Optional.empty())
    }

    log.error(message, e)
    return Mono.error(CheckCarExceptions.restRepositoryException());
  }

  private fun getRetrySpec(): RetryBackoffSpec {
    return Retry
      .backoff(RETRY_MAX_ATTEMPTS, Duration.ofSeconds(RETRY_DELAY_SEC))
      .filter { e -> !is404Error(e) };
  }

  private fun is404Error(e: Throwable): Boolean {
    if (e is HttpClientResponseException) {
      return e.status.equals(HttpStatus.NOT_FOUND);
    }
    return false;
  }
}
