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

  @ElementCollection
  @CollectionTable(name = "ACHIEVED_QUALIFICATION", joinColumns = [JoinColumn(name = "work_interests_id")])
  @Column(name = "QUALIFICATION")
  var qualifications: MutableSet<AchievedQualification>?,

  @ElementCollection
  @CollectionTable(name = "EXTRA_QUALIFICATION", joinColumns = [JoinColumn(name = "work_interests_id")])
  @Column(name = "QUALIFICATION")
  var additionalTraining: MutableSet<OtherQualification>?,

  @Column(name = "OTHER_QUALIFICATION")
  var additionalTrainingOther: String?,

  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn(name = "offender_id")
  @JsonIgnore
  var profile: CIAGProfile?,
)
