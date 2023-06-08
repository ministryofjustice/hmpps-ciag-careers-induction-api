package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FutureIntrests

data class FutureIntrestsDetail(
  var definedIntrests: List<FutureIntrests>?,
  var otherIntrests: String?,
)
