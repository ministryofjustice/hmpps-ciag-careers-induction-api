package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
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
  @ElementCollection
  @CollectionTable(name = "PREVIOUS_WORK_DETAIL", joinColumns = [JoinColumn(name = "WORK_ID")])
  @Column(name = "WORK_DETAILS")
  var workList: MutableSet<PreviousWorkDetail>?,

  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn(name = "OFFENDER_ID")
  val profile: CIAGProfile?,
)
