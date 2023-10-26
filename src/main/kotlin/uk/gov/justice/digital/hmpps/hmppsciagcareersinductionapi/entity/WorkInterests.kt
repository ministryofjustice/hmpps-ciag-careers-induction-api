package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import java.time.LocalDateTime
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "CURRENT_WORK_INTERESTS")
data class WorkInterests(
  @LastModifiedBy
  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String?,

  @LastModifiedDate
  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime?,
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
    return "WorkInterests(modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, workInterests=$workInterests, workInterestsOther=$workInterestsOther, particularJobInterests=$particularJobInterests)"
  }

  @PrePersist
  fun prePersist() {
    this.modifiedBy = CapturedSpringConfigValues.principal.name
    this.modifiedDateTime = LocalDateTime.now()
  }

  @PreUpdate
  fun preUpdate() {
    this.modifiedBy = CapturedSpringConfigValues.principal.name
    this.modifiedDateTime = LocalDateTime.now()
  }
}
