package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
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
@Table(name = "WORK_INTERESTS")
data class WorkInterests(
  @LastModifiedBy
  var modifiedBy: String,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ElementCollection
  @CollectionTable(name = "WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_ID")])
  @Column(name = "WORK_INTERESTS")
  var workInterests: MutableSet<WorkType>?,
  @Column(name = "OTHER_WORK_INTEREST")
  var otherWorkInterests: String?,

  @ElementCollection
  @CollectionTable(name = "PARTICULAR_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_ID")])
  @Column(name = "PARTICULAR_WORK_INTEREST")
  var particularWorkList: MutableSet<PreviousWorkDetail>?,

  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn(name = "OFFENDER_ID")
  val profile: CIAGProfile?,
)