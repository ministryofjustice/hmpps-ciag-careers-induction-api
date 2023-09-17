package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
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
import javax.validation.constraints.Size

@Entity
@Table(name = "PREVIOUS_WORK")
data class PreviousWork(
  @Column(name = "HAS_WORKED_BEFORE", nullable = false)
  var hasWorkedBefore: Boolean,
  @LastModifiedBy
  var modifiedBy: String,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_EXPERIENCE", joinColumns = [JoinColumn(name = "PREVIOUS_WORK_ID")])
  @Column(name = "WORK_EXPERIENCE")
  @Size(min = 1)
  var typeOfWorkExperience: MutableSet<WorkType>,

  @Column(name = "WORK_EXPERIENCE_OTHER")
  var typeOfWorkExperienceOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_EXPERIENCE_DETAIL", joinColumns = [JoinColumn(name = "PREVIOUS_WORK_ID")])
  @Column(name = "WORK_EXPERIENCE_DETAIL")
  @Size(min = 1)
  var workExperience: MutableSet<WorkExperience>,

  @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "WORK_INTERESTS_ID")
  var workInterests: WorkInterests?,

  @OneToOne(mappedBy = "workExperience")
  @JsonIgnore
  var profile: CIAGProfile?,
) {

  override fun toString(): String {
    return "PreviousWork(hasWorkedBefore=$hasWorkedBefore, modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, typeOfWorkExperience=$typeOfWorkExperience, typeOfWorkExperienceOther=$typeOfWorkExperienceOther, workExperience=$workExperience, workInterests=$workInterests, profile=$profile)"
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
}
