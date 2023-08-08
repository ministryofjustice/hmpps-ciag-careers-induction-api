package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
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
  @ElementCollection
  @CollectionTable(name = "PRISON_WORK", joinColumns = [JoinColumn(name = "PRISON_WORK_EDUCATION_ID")])
  @Column(name = "WORK")
  var prisonWork: MutableSet<PrisonWork>?,
  @Column(name = "OTHER_PRISON_WORK")
  var otherPrisonWork: String?,

  @ElementCollection
  @CollectionTable(name = "PRISON_EDUCATION", joinColumns = [JoinColumn(name = "PRISON_WORK_EDUCATION_ID")])
  @Column(name = "EDUCATION")
  var prisonEducation: MutableSet<PrisonTraining>?,
  @Column(name = "OTHER_PRISON_EDUCATION")
  var otherPrisonEducation: String?,
  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn(name = "OFFENDER_ID")
  val profile: CIAGProfile?,
)
