package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
@Entity
@Table(name = "profile_goals")
data class Goals constructor(

  var goal: String?,

  @ElementCollection
  @CollectionTable(name = "Goal_Steps", joinColumns = [JoinColumn(name = "goal_id")])
  @Column(name = "Goal_Step")
  var steps: MutableSet<GoalSteps>?,

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn(name = "profile_id")
  val profile: CIAGProfile?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Goals

    if (goal != other.goal) return false
    if (steps != other.steps) return false

    return true
  }

  override fun hashCode(): Int {
    var result = goal?.hashCode() ?: 0
    result = 31 * result + (steps?.hashCode() ?: 0)
    return result
  }
}
