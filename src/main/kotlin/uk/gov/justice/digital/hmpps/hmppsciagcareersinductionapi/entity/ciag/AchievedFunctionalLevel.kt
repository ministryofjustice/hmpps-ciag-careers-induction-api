package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessment
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessmentLevel
import java.time.LocalDateTime
import javax.persistence.Embeddable

@Embeddable
data class AchievedFunctionalLevel(
  var functionalAssessment: FunctionalAssessment?,
  var functionalAssessmentLevel: FunctionalAssessmentLevel?,
  var achievedDate: LocalDateTime ?,
)
