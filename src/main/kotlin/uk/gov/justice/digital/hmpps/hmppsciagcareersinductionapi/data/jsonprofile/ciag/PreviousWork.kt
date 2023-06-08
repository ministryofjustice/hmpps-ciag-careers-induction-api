package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType

data class PreviousWork(
  var workList: List<WorkType>?,
  var otherIntrests: String?,
)
