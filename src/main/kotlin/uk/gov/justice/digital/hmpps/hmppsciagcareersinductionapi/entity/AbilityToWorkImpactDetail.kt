package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import javax.persistence.Embeddable

@Embeddable
data class AbilityToWorkImpactDetail(
  var functionalAssessment: AbilityToWorkImpactedBy?,
  var otherAbilityToWorkImpactedBy: String?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AbilityToWorkImpactDetail

    if (functionalAssessment != other.functionalAssessment) return false
    if (otherAbilityToWorkImpactedBy != other.otherAbilityToWorkImpactedBy) return false

    return true
  }

  override fun hashCode(): Int {
    var result = functionalAssessment?.hashCode() ?: 0
    result = 31 * result + (otherAbilityToWorkImpactedBy?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "AbilityToWorkImpactDetail(functionalAssessment=$functionalAssessment, otherAbilityToWorkImpactedBy=$otherAbilityToWorkImpactedBy)"
  }
}
