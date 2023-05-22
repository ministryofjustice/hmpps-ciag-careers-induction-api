package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

data class Action(
  val todoItem: ActionTodo,
  val status: ActionStatus,
  val id: List<IDocs>,
)
