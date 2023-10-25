package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.EducationLevels
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.OtherQualification
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

@Entity
@Table(name = "EDUCATION_QUALIFICATION")
data class EducationAndQualification(
  @LastModifiedBy
  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String,

  @LastModifiedDate
  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @Column(name = "EDUCATION_LEVEL")
  @Schema(description = "This is the Highest education level of the inmate.", name = "educationLevel", required = false)
  var educationLevel: EducationLevels?,

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "ACHIEVED_QUALIFICATION", joinColumns = [JoinColumn(name = "work_interests_id")])
  @Column(name = "QUALIFICATION")
  @Schema(description = "This is the qualification list of the inmate.", name = "qualifications", required = false)
  var qualifications: MutableSet<AchievedQualification>?,

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "EXTRA_QUALIFICATION", joinColumns = [JoinColumn(name = "work_interests_id")])
  @Column(name = "QUALIFICATION")
  @Schema(description = "This is the additional training list of the inmate.", name = "additionalTraining", required = false)
  var additionalTraining: MutableSet<OtherQualification>?,

  @Column(name = "OTHER_QUALIFICATION")
  @Schema(description = "This is the additional which is peculiar to this inmate  .This field is mandatory when  \"additionalTraining\" has a Value set to \"OTHER\" ", name = "additionalTrainingOther", required = false)
  var additionalTrainingOther: String?,

) {

  override fun toString(): String {
    return "EducationAndQualification(modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, educationLevel=$educationLevel, qualifications=$qualifications, additionalTraining=$additionalTraining, additionalTrainingOther=$additionalTrainingOther)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as EducationAndQualification

    if (id != other.id) return false
    if (educationLevel != other.educationLevel) return false
    if (qualifications != other.qualifications) return false
    if (additionalTraining != other.additionalTraining) return false
    if (additionalTrainingOther != other.additionalTrainingOther) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + (educationLevel?.hashCode() ?: 0)
    result = 31 * result + (qualifications?.hashCode() ?: 0)
    result = 31 * result + (additionalTraining?.hashCode() ?: 0)
    result = 31 * result + (additionalTrainingOther?.hashCode() ?: 0)
    return result
  }

  @PrePersist
  fun prePersist() {
    this.modifiedBy = CapturedSpringConfigValues.principal.toString()
    this.modifiedDateTime = LocalDateTime.now()
  }

  @PreUpdate
  fun preUpdate() {
    this.modifiedBy = CapturedSpringConfigValues.principal.toString()
    this.modifiedDateTime = LocalDateTime.now()
  }
}
