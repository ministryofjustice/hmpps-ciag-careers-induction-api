package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@ConfigurationPropertiesBinding
class LocalDatePropertyConverter : Converter<String, LocalDate> {
  override fun convert(source: String): LocalDate? {
    return if (source == "") null else LocalDate.parse(source)
  }
}
