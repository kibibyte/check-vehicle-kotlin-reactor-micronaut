package com.myapp.usecase.check;

import com.myapp.usecase.check.MaintenanceFrequency.*
import com.myapp.usecase.check.MaintenanceScore.*


data class CheckCarResult(val vin: String, val numberOfAccidents: Int?, val maintenanceFrequency: MaintenanceFrequency?) {
  val accidentFree: Boolean? = numberOfAccidents?.equals(0)
  val maintenanceScore: MaintenanceScore? = maintenanceFrequency?.let { this.mapToMaintenanceScore(it) }

  companion object {
    @JvmStatic
    fun of(vin: String, numberOfAccidents: Int): CheckCarResult {
      return CheckCarResult(vin, numberOfAccidents, null);
    }

    @JvmStatic
    fun of(vin: String, maintenanceFrequency: MaintenanceFrequency): CheckCarResult {
      return CheckCarResult(vin, null, maintenanceFrequency);
    }
  }

  private fun mapToMaintenanceScore(maintenanceFrequency: MaintenanceFrequency): MaintenanceScore {
    if (maintenanceFrequency==LOW || maintenanceFrequency==VERY_LOW) {
      return POOR;
    }
    if (maintenanceFrequency==MEDIUM) {
      return AVERAGE;
    }

    return GOOD;
  }
}
