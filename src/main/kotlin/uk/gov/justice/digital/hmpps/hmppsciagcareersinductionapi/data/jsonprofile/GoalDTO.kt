package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.GoalSteps

data class GoalDTO(
  var goal: String?,
  var steps: MutableSet<GoalSteps>?,
)
