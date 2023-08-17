package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.EducationLevels
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.OtherQualification
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "EDUCATION_QUALIFICATION")
data class EducationAndQualification(
  @LastModifiedBy
  var modifiedBy: String,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @Column(name = "EDUCATION_LEVEL")
  var educationLevel: EducationLevels?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "ACHIEVED_QUALIFICATION", joinColumns = [JoinColumn(name = "work_interests_id")])
  @Column(name = "QUALIFICATION")
  var qualifications: MutableSet<AchievedQualification>?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "EXTRA_QUALIFICATION", joinColumns = [JoinColumn(name = "work_interests_id")])
  @Column(name = "QUALIFICATION")
  var additionalTraining: MutableSet<OtherQualification>?,

  @Column(name = "OTHER_QUALIFICATION")
  var additionalTrainingOther: String?,

  @OneToOne(mappedBy = "qualificationsAndTraining")
  @JsonIgnore
  var profile: CIAGProfile?,
) {

  override fun toString(): String {
    return "EducationAndQualification(modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, educationLevel=$educationLevel, qualifications=$qualifications, additionalTraining=$additionalTraining, additionalTrainingOther=$additionalTrainingOther, profile=$profile)"
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
}
