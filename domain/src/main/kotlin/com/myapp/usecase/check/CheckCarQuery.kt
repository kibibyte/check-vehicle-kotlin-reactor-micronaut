package com.myapp.usecase.check;

import com.myapp.exceptions.InvalidArgumentException
import org.apache.commons.lang3.StringUtils.isBlank

data class CheckCarQuery(val vin: String, val featuresToCheck: List<CheckCarFeature>) {
  init {
    if (isBlank(vin)) {
      throw InvalidArgumentException("INVALID_VIN", "Vin cannot be empty");
    }
    if (featuresToCheck.isEmpty()) {
      throw InvalidArgumentException("EMPTY_FEATURES", "Features cannot be empty");
    }
  }

  fun isCheckFeature(feature: CheckCarFeature): Boolean {
    return featuresToCheck.contains(feature);
  }
}
