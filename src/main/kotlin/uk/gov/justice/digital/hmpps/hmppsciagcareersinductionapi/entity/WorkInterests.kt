package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import java.time.LocalDateTime

@Entity
@Table(name = "CURRENT_WORK_INTERESTS")
data class WorkInterests(
  @LastModifiedBy
  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String,

  @LastModifiedDate
  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "WORK_INTERESTS")
  @Size(min = 1)
  @Schema(description = "This is the list of interests of the inmate.", name = "workInterests", required = true)
  var workInterests: MutableSet<WorkType>,
  @Column(name = "OTHER_WORK_INTEREST")
  @Schema(description = "This is the work interest which is peculiar to this inmate  .This field is mandatory when  \"workInterests\" has a Value set to \"OTHER\" ", name = "workInterestsOther", required = false)
  var workInterestsOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PARTICULAR_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "PARTICULAR_WORK_INTEREST")
  @Schema(description = "This is the list of detailed interests of the inmate.", name = "particularJobInterests")
  var particularJobInterests: MutableSet<WorkInterestDetail>?,

  @OneToOne(mappedBy = "workInterests")
  @JsonIgnore
  var previousWork: PreviousWork?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as WorkInterests

    if (id != other.id) return false
    if (workInterests != other.workInterests) return false
    if (workInterestsOther != other.workInterestsOther) return false
    if (particularJobInterests != other.particularJobInterests) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + (workInterests?.hashCode() ?: 0)
    result = 31 * result + (workInterestsOther?.hashCode() ?: 0)
    result = 31 * result + (particularJobInterests?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "WorkInterests(modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, workInterests=$workInterests, workInterestsOther=$workInterestsOther, particularJobInterests=$particularJobInterests, previousWork=$previousWork)"
  }
}
