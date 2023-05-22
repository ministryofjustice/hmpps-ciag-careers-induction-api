package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import java.time.LocalDateTime

data class WorkExperience(
  val modifiedBy: String,
  val modifiedDateTime: LocalDateTime,

  val previousWorkOrVolunteering: String,
  val qualificationsAndTraining: List<QualificationsAndTraining>,
  val qualificationsAndTrainingOther: String,
)
