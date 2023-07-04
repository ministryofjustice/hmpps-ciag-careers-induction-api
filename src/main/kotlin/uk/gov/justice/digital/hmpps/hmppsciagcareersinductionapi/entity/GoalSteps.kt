package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.TimePeriod
import javax.persistence.Embeddable

@Embeddable
data class GoalSteps(
  var step: String?,
  var timeperiod: TimePeriod?,

)
