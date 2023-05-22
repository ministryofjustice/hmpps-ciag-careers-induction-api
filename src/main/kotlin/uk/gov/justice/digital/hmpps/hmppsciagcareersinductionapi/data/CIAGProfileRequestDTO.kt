package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.Profile
import javax.validation.Valid

data class CIAGProfileRequestDTO(

  @Schema(description = "Booking Id", example = "12345678")
  val bookingId: Long,

  @Schema(description = "Work readiness profile JSON data", example = "{...}")
  @Valid
  val profileData: Profile,
)
