package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PersonalInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Skills
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
@Table(name = "SKILLS_AND_INTERESTS")
data class SkillsAndInterests(
  @LastModifiedBy
  var modifiedBy: String,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "SKILLS_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "SKILLS")
  var skills: MutableSet<Skills>?,
  @Column(name = "OTHER_SKILL")
  var skillOTHER: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERSONAL_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "PERSONAL_WORK_INTEREST")
  var personalInterests: MutableSet<PersonalInterests>?,

  @Column(name = "OTHER_PERSONAL_INTRESTS")
  var personalInterestsOther: String?,
  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn(name = "OFFENDER_ID")
  @JsonIgnore
  val profile: CIAGProfile?,
)
