package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

data class CIAGProfileOffenderIdListRequestDTO(
  @Schema(description = "This is the ID list of the inmates ", name = "offenderIds", pattern = "^[A-Z]\\d{4}[A-Z]{2}\$", required = true)
  @Size(min = 1)
  val offenderIds: List<String>,
)
