package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.TimePeriod

data class GoalSteps(
  var step: String?,
  var timeperiod: TimePeriod?,
)
