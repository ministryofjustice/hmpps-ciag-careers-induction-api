package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Training

data class AchievedTrainjng(
  var training: List<Training>?,
  var customTraining: String?,
)
