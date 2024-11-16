package com.myapp.usecase.check

import spock.lang.Specification

import static com.myapp.usecase.check.MaintenanceFrequency.*
import static com.myapp.usecase.check.MaintenanceScore.*

class CheckCarResultTest extends Specification {

  def "should create CheckCarResult using numberOfAccidents"() {
    when:
    def vin = "1234";
    def result = new CheckCarResult(vin, numberOfAccidents, null)

    then:
    result.getVin() == vin
    result.getAccidentFree() == expectedResult
    result.getMaintenanceScore() == null

    where:
    numberOfAccidents || expectedResult
    null              || null
    0                 || true
    1                 || false
  }

  def "should create CheckCarResult using maintenanceFrequency"() {
    when:
    def vin = "1234";
    def result = new CheckCarResult(vin, null, maintenanceFrequency)

    then:
    result.getVin() == vin
    result.getMaintenanceScore() == expectedResult
    result.getAccidentFree() == null

    where:
    maintenanceFrequency || expectedResult
    null                 || null
    LOW                  || POOR
    VERY_LOW             || POOR
    MEDIUM               || AVERAGE
    HIGH                 || GOOD
  }
}