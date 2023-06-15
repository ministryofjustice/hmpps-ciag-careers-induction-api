package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import java.time.LocalDateTime

data class Profile(
  var status: ProfileStatus,
  var statusChange: Boolean?,
  var statusChangeDate: LocalDateTime?,
  var statusChangeType: StatusChange?,
  var supportDeclined_history: MutableList<SupportDeclined>?,
  var supportAccepted_history: MutableList<SupportAccepted>?,
  var supportDeclined: SupportDeclined?,
  var supportAccepted: SupportAccepted?,
)