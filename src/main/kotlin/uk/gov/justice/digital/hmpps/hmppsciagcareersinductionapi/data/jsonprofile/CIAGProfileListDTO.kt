package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema

data class CIAGProfileListDTO(

  @Schema(description = "This is the ID of the inmate ", name = "offenderId", pattern = "^[A-Z]\\d{4}[A-Z]{2}\$", required = true)
  val ciagProfileList: List<CIAGProfileDTO>?,
)
