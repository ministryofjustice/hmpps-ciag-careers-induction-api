package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.ciag
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import javax.persistence.Embeddable

@Embeddable
data class PreviousWorkDetail(
  var workTypeJob: WorkType?,
  var otherJob: String?,
  var jobTitle: String?,
  var tasksAndResponsibilities: String?,
)
