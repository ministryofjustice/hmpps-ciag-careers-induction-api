package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema

data class CIAGProfileListDTO(

  @Schema(description = "This is the List of inductions for inmates ", name = "ciagProfileList", required = true)
  val ciagProfileList: List<CIAGMainProfileDTO>?,
)
