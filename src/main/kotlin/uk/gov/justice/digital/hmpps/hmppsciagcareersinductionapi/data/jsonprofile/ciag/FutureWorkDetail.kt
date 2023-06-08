package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType

data class FutureWorkDetail(
  var workTypeJob: WorkType?,
  var otherJob: String?,
  var jobTitle: String?,
)
