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
  var typeOfWorkExperience: MutableSet<WorkType>?,

  @Column(name = "WORK_EXPERIENCE_OTHER")
  var typeOfWorkExperienceOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "WORK_EXPERIENCE_DETAIL", joinColumns = [JoinColumn(name = "PREVIOUS_WORK_ID")])
  @Column(name = "WORK_EXPERIENCE_DETAIL")
  var workExperience: MutableSet<WorkExperience>?,

  @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "WORK_INTERESTS_ID")
  var workInterests: WorkInterests?,

  @OneToOne(mappedBy = "workExperience",fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JsonIgnore
  var profile: CIAGProfile?,
)
