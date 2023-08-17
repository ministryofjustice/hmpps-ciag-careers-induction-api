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

@Entity
@Table(name = "CURRENT_WORK_INTERESTS")
data class WorkInterests(
  @LastModifiedBy
  var modifiedBy: String,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "WORK_INTERESTS")
  var workInterests: MutableSet<WorkType>?,
  @Column(name = "OTHER_WORK_INTEREST")
  var workInterestsOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PARTICULAR_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "PARTICULAR_WORK_INTEREST")
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
