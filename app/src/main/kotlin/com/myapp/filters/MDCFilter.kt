package com.myapp.filters;

import io.micronaut.context.propagation.slf4j.MdcPropagationContext
import io.micronaut.core.propagation.PropagatedContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.annotation.Filter.MATCH_ALL_PATTERN
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import org.reactivestreams.Publisher
import org.slf4j.MDC
import java.util.*

@Filter(MATCH_ALL_PATTERN)
class MDCFilter : HttpServerFilter {
  companion object {
    const val REQUEST_ID: String = "requestId"
  }

  override fun doFilter(request: HttpRequest<*>?, chain: ServerFilterChain?): Publisher<MutableHttpResponse<*>> {
    try {
      MDC.put(REQUEST_ID, UUID.randomUUID().toString())
      PropagatedContext.get().plus(MdcPropagationContext()).propagate().use { _ ->
        return chain!!.proceed(request)
      }
    } finally {
      MDC.remove(REQUEST_ID)
    }
  }
}