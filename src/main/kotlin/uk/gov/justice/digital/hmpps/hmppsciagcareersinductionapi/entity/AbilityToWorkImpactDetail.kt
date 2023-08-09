package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import javax.persistence.Embeddable

@Embeddable
data class AbilityToWorkImpactDetail(
  var functionalAssessment: AbilityToWorkImpactedBy?,
  var otherAbilityToWorkImpactedBy: String?,
)
