package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder

/**
 * This class is used as a way to capture the Spring Jackson mapper in a way that
 * data classes etc may use it as they cannot have it auto injected
 */
@Configuration
class CapturedSpringConfigValues {
  companion object {
    val principal by lazy {
      SecurityContextHolder.getContext().authentication.principal as DpsPrincipal
    }
    var OBJECT_MAPPER: ObjectMapper = this.configObjectMapper()
    fun getDPSPrincipal(): DpsPrincipal {
      return SecurityContextHolder.getContext().authentication.principal as DpsPrincipal
    }
    fun configObjectMapper(): ObjectMapper {
      val mapper = ObjectMapper()
      mapper.registerModule(JavaTimeModule())
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      return mapper.registerKotlinModule()
    }
  }
}
