package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel
import javax.persistence.Embeddable

@Embeddable
data class AchievedQualification(
  var subject: String?,
  var grade: String?,
  var level: QualificationLevel?,
)
