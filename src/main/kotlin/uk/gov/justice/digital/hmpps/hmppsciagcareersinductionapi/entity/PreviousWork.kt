package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import javax.persistence.Embeddable

@Embeddable
data class PreviousWork(
  var workList: WorkType?,
  var otherIntrests: String?,
)
