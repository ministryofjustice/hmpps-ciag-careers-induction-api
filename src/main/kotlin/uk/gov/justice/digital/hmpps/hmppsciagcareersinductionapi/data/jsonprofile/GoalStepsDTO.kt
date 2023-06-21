package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.TimePeriod
import javax.persistence.*

data class GoalStepsDTO(
  var id: Long? = null,
  var step: String?,
  var timeperiod: TimePeriod?,
)
