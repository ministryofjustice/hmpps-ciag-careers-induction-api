package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PersonalInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Skills
import java.time.LocalDateTime

@Entity
@Table(name = "SKILLS_AND_INTERESTS")
data class SkillsAndInterests(
  @LastModifiedBy
  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String,

  @LastModifiedDate
  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime,
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "SKILLS_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "SKILLS")
  @Size(min = 1)
  @Schema(description = "This is the skill list of the inmate.", name = "skills", required = false)
  var skills: MutableSet<Skills>?,
  @Column(name = "OTHER_SKILL")
  @Schema(description = "This is the skill which is peculiar to this inmate  .This field is mandatory when  \"skills\" has a Value set to \"OTHER\" ", name = "skillsOther", required = false)
  var skillsOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PERSONAL_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "PERSONAL_WORK_INTEREST")
  @Size(min = 1)
  @Schema(description = "This is the interests list of the inmate.", name = "personalInterests", required = false)
  var personalInterests: MutableSet<PersonalInterests>?,

  @Column(name = "OTHER_PERSONAL_INTRESTS")
  @Schema(description = "This is the work interest which is peculiar to this inmate  .This field is mandatory when  \"personalInterests\" has a Value set to \"OTHER\" ", name = "personalInterestsOther", required = false)
  var personalInterestsOther: String?,

) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as SkillsAndInterests

    if (id != other.id) return false
    if (skills != other.skills) return false
    if (skillsOther != other.skillsOther) return false
    if (personalInterests != other.personalInterests) return false
    if (personalInterestsOther != other.personalInterestsOther) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + (skills?.hashCode() ?: 0)
    result = 31 * result + (skillsOther?.hashCode() ?: 0)
    result = 31 * result + (personalInterests?.hashCode() ?: 0)
    result = 31 * result + (personalInterestsOther?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "SkillsAndInterests(modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, skills=$skills, skillsOther=$skillsOther, personalInterests=$personalInterests, personalInterestsOther=$personalInterestsOther)"
  }
}
