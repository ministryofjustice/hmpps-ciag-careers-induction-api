package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.TimePeriod
import javax.persistence.*

@Embeddable
data class GoalSteps(
/*  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  var id: Long? = null,*/
  var step: String?,
  var timeperiod: TimePeriod?,
 /* @ManyToOne(fetch = FetchType.EAGER, cascade = [javax.persistence.CascadeType.ALL])
  @JoinColumn(name = "goal_id")
  var goals: Goals?,*/
)
