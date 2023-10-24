package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel
import javax.persistence.Embeddable

@Embeddable
data class AchievedQualification(
  @Schema(description = "This is the subject the inmate has chosen.", name = "subject", required = false)
  var subject: String?,
  @Schema(description = "This is the grade on the subject the inmate has chosen.", name = "grade", required = false)
  var grade: String?,
  @Schema(description = "This is the level of  the subject the inmate has chosen.", name = "level", required = false)
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
