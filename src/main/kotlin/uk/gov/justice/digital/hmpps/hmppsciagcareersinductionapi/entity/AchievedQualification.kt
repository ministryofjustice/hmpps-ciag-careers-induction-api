package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel
import javax.persistence.Embeddable

@Embeddable
data class AchievedQualification(
  var subject: String?,
  var grade: String?,
  var level: QualificationLevel?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AchievedQualification

    if (subject != other.subject) return false
    if (grade != other.grade) return false
    if (level != other.level) return false

    return true
  }

  override fun hashCode(): Int {
    var result = subject?.hashCode() ?: 0
    result = 31 * result + (grade?.hashCode() ?: 0)
    result = 31 * result + (level?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "AchievedQualification(subject=$subject, grade=$grade, level=$level)"
  }
}
