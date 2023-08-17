package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonTraining
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonWork
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
@Table(name = "PRISON_WORK_EDUCATION")
data class PrisonWorkAndEducation(
  @LastModifiedBy
  var modifiedBy: String,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PRISON_WORK", joinColumns = [JoinColumn(name = "PRISON_WORK_EDUCATION_ID")])
  @Column(name = "WORK")
  var inPrisonWork: MutableSet<PrisonWork>?,
  @Column(name = "OTHER_PRISON_WORK")
  var inPrisonWorkOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PRISON_EDUCATION", joinColumns = [JoinColumn(name = "PRISON_WORK_EDUCATION_ID")])
  @Column(name = "EDUCATION")
  var inPrisonEducation: MutableSet<PrisonTraining>?,
  @Column(name = "OTHER_PRISON_EDUCATION")
  var inPrisonEducationOther: String?,
  @OneToOne(mappedBy="inPrisonInterests")
  @JsonIgnore
  var profile: CIAGProfile?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PrisonWorkAndEducation

    if (id != other.id) return false
    if (inPrisonWork != other.inPrisonWork) return false
    if (inPrisonWorkOther != other.inPrisonWorkOther) return false
    if (inPrisonEducation != other.inPrisonEducation) return false
    if (inPrisonEducationOther != other.inPrisonEducationOther) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + (inPrisonWork?.hashCode() ?: 0)
    result = 31 * result + (inPrisonWorkOther?.hashCode() ?: 0)
    result = 31 * result + (inPrisonEducation?.hashCode() ?: 0)
    result = 31 * result + (inPrisonEducationOther?.hashCode() ?: 0)
    return result
  }

}
