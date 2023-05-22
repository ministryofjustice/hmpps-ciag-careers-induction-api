package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import java.time.LocalDateTime

data class ActionsRequired(
  val modifiedBy: String,
  val modifiedDateTime: LocalDateTime,

  val actions: List<Action>,
)
