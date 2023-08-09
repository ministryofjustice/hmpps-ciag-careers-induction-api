package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import javax.persistence.Embeddable

@Embeddable
data class WorkExperience(
  var typeOfWorkExperience: WorkType?,
  var otherWork: String?,

  var role: String?,
  var details: String?,
)
