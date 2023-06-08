package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.ciag

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "ciag_profile")
data class Goals(
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var goalId: Long,
  var goal: String?,

  var profile: CIAGProfile?,
  @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
  @JoinColumn(name = "post_id")
  var steps: List<GoalSteps>?,
)
