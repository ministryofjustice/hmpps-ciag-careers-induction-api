package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel

data class AchievedQualification(
  var subject: String?,
  var grade: String?,
  var level: QualificationLevel?,
)
