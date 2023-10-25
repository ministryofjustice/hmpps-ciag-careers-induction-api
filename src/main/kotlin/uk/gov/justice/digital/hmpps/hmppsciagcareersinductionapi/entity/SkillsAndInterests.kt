package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PersonalInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Skills
import java.time.LocalDateTime
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.validation.constraints.Size

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
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "SKILLS_WORK_INTERESTS", joinColumns = [JoinColumn(name = "WORK_INTERESTS_ID")])
  @Column(name = "SKILLS")
  @Size(min = 1)
  @Schema(description = "This is the skill list of the inmate.", name = "skills", required = false)
  var skills: MutableSet<Skills>?,
  @Column(name = "OTHER_SKILL")
  @Schema(description = "This is the skill which is peculiar to this inmate  .This field is mandatory when  \"skills\" has a Value set to \"OTHER\" ", name = "skillsOther", required = false)
  var skillsOther: String?,

  @ElementCollection(fetch = FetchType.LAZY)
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

  @PrePersist
  fun prePersist() {
    this.modifiedBy = CapturedSpringConfigValues.principal.toString()
    this.modifiedDateTime = LocalDateTime.now()
  }

  @PreUpdate
  fun preUpdate() {
    this.modifiedBy = CapturedSpringConfigValues.principal.toString()
    this.modifiedDateTime = LocalDateTime.now()
  }
}
