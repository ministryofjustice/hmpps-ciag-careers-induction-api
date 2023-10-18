package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import javax.persistence.Embeddable

@Embeddable
data class WorkExperience(
  @Schema(description = "This is the experience of the inmate, being detailed.", name = "typeOfWorkExperience", required = true)
  var typeOfWorkExperience: WorkType,

  @Schema(description = "This is the experience of the inmate, being detailed, which is not listed in typeOfWorkExperience Enum", name = "otherWork", required = false)
  var otherWork: String?,

  @Schema(description = "This is the role  of the inmate, in the work ,being detailed", name = "role", required = false)
  var role: String?,

  @Schema(description = "This is the detail of the work ", name = "details", required = false)
  var details: String?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as WorkExperience

    if (typeOfWorkExperience != other.typeOfWorkExperience) return false
    if (otherWork != other.otherWork) return false
    if (role != other.role) return false
    if (details != other.details) return false

    return true
  }

  override fun hashCode(): Int {
    var result = typeOfWorkExperience?.hashCode() ?: 0
    result = 31 * result + (otherWork?.hashCode() ?: 0)
    result = 31 * result + (role?.hashCode() ?: 0)
    result = 31 * result + (details?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "WorkExperience(typeOfWorkExperience=$typeOfWorkExperience, otherWork=$otherWork, role=$role, details=$details)"
  }
}
