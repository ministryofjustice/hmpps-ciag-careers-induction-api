package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessment
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessmentLevel
import java.time.LocalDateTime

data class AchievedFunctionalLevel(
  var functionalAssessment: FunctionalAssessment?,
  var functionalAssessmentLevel: FunctionalAssessmentLevel?,
  var createdDateTime: LocalDateTime ?,
)
