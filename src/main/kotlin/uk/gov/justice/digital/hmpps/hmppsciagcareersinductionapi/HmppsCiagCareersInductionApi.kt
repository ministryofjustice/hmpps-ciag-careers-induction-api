package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication()
@ConfigurationPropertiesScan
class HmppsCiagCareersInductionApi

fun main(args: Array<String>) {
  runApplication<HmppsCiagCareersInductionApi>(*args)
}
