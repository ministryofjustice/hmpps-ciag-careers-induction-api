package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.CascadeType
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
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import java.time.LocalDateTime

@Entity
@Table(name = "PREVIOUS_WORK")
data class PreviousWork(
  @Column(name = "HAS_WORKED_BEFORE", nullable = false)
  var hasWorkedBefore: Boolean,

  @LastModifiedBy
  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String?,

  @LastModifiedDate
  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime?,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_EXPERIENCE", joinColumns = [JoinColumn(name = "PREVIOUS_WORK_ID")])
  @Column(name = "WORK_EXPERIENCE")
  @Size(min = 1)
  @Schema(description = "This is the work experience list of the inmate.", name = "typeOfWorkExperience", required = false)
  var typeOfWorkExperience: MutableSet<WorkType>?,

  @Column(name = "WORK_EXPERIENCE_OTHER")
  @Schema(description = "This is the work experience which is peculiar to this inmate  .This field is mandatory when  \"typeOfWorkExperience\" has a Value set to \"OTHER\" ", name = "typeOfWorkExperienceOther", required = false)
  var typeOfWorkExperienceOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_EXPERIENCE_DETAIL", joinColumns = [JoinColumn(name = "PREVIOUS_WORK_ID")])
  @Column(name = "WORK_EXPERIENCE_DETAIL")
  @Size(min = 1)
  @Schema(description = "This is the list of work experience details of the inmate.", name = "workExperience", required = true)
  var workExperience: MutableSet<WorkExperience>?,

  @OneToOne(cascade = [CascadeType.REMOVE], orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "WORK_INTERESTS_ID")
  @Schema(description = "This is the work interests of the inmate.", name = "workInterests", required = true)
  var workInterests: WorkInterests?,

) {

  override fun toString(): String {
    return "PreviousWork(hasWorkedBefore=$hasWorkedBefore, modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, typeOfWorkExperience=$typeOfWorkExperience, typeOfWorkExperienceOther=$typeOfWorkExperienceOther, workExperience=$workExperience, workInterests=$workInterests)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PreviousWork

    if (hasWorkedBefore != other.hasWorkedBefore) return false
    if (id != other.id) return false
    if (typeOfWorkExperience != other.typeOfWorkExperience) return false
    if (typeOfWorkExperienceOther != other.typeOfWorkExperienceOther) return false
    if (workExperience != other.workExperience) return false
    if (workInterests != other.workInterests) return false

    return true
  }

  override fun hashCode(): Int {
    var result = hasWorkedBefore.hashCode()
    result = 31 * result + (id?.hashCode() ?: 0)
    result = 31 * result + (typeOfWorkExperience?.hashCode() ?: 0)
    result = 31 * result + (typeOfWorkExperienceOther?.hashCode() ?: 0)
    result = 31 * result + (workExperience?.hashCode() ?: 0)
    result = 31 * result + (workInterests?.hashCode() ?: 0)
    return result
  }

  @PrePersist
  fun prePersist() {
    this.modifiedBy = CapturedSpringConfigValues.getDPSPrincipal().name
    this.modifiedDateTime = LocalDateTime.now()
  }

  @PreUpdate
  fun preUpdate() {
    this.modifiedBy = CapturedSpringConfigValues.getDPSPrincipal().name
    this.modifiedDateTime = LocalDateTime.now()
  }
}
