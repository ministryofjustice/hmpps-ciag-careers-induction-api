package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "profile_goals")
data class Goals(
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  var id: Long? = null,
  var goal: String?,

  @ManyToOne(fetch = FetchType.LAZY)
  var profile: CIAGProfile?,
  @OneToMany(
    mappedBy = "goals",
    cascade = [CascadeType.ALL],
    orphanRemoval = true,
  )
  var steps: MutableSet<GoalSteps>?,
)
