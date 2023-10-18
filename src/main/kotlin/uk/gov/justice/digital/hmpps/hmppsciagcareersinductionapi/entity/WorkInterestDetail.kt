package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import javax.persistence.Embeddable

@Embeddable
data class WorkInterestDetail(
  @Schema(description = "This is the interest of the inmate, being detailed.", name = "workInterest", required = true)
  var workInterest: WorkType,
  @Schema(description = "This is the role of the inmate for the given work interest.", name = "role", required = true)
  var role: String,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as WorkInterestDetail

    if (workInterest != other.workInterest) return false
    if (role != other.role) return false

    return true
  }

  override fun hashCode(): Int {
    var result = workInterest?.hashCode() ?: 0
    result = 31 * result + (role?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "WorkInterestDetail(workInterest=$workInterest, role=$role)"
  }
}
