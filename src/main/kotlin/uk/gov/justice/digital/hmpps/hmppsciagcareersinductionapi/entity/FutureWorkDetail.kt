package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import javax.persistence.Embeddable

@Embeddable
data class FutureWorkDetail(
  var workTypeJob: WorkType?,
  var otherJob: String?,
  var jobTitle: String?,
)
