package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.HopingToGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.ReasonToNotGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "CIAG_PROFILE")
data class CIAGProfile(
  @Id
  val offenderId: String,
  @Column(name = "PRISON_ID")
  var prisonId: String?,

  @Column(name = "PRISON_NAME")
  var prisonName: String?,
  @CreatedBy
  var createdBy: String,

  @CreatedDate
  var createdDateTime: LocalDateTime,

  @LastModifiedBy
  var modifiedBy: String,

  @Column(name = "DESIRE_TO_WORK")
  var desireToWork: Boolean,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,

  @Column(name = "HOPING_TO_GET_WORK")
  var hopingToGetWork: HopingToGetWork,

  @Column(name = "REASON_TO_NOT_GET_WORK")
  var reasonToNotGetWorkOther: String?,

  @Column(name = "OTHER_ABILITY_TO_WORK_IMPACT")
  var abilityToWorkOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "ABILITY_TO_WORK_IMPACT", joinColumns = [JoinColumn(name = "OFFENDER_ID")])
  @Column(name = "WORK_IMPACT")
  @Size(min = 1)
  var abilityToWork: MutableSet<AbilityToWorkImpactedBy>?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "REASON_TO_NOT_WORK", joinColumns = [JoinColumn(name = "OFFENDER_ID")])
  @Column(name = "REASON")
  @Size(min = 1)
  var reasonToNotGetWork: MutableSet<ReasonToNotGetWork>?,

  @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "PREVIOUS_WORK_ID")
  var workExperience: PreviousWork?,

  @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "SKILLS_AND_INTERESTS_ID")
  var skillsAndInterests: SkillsAndInterests?,

  @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "EDUCATION_AND_QUALIFICATION_ID")
  var qualificationsAndTraining: EducationAndQualification?,

  @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "PRISON_WORK_AND_EDUCATION_ID")
  var inPrisonInterests: PrisonWorkAndEducation?,

  @Column(name = "SCHEMA_VERSION")
  var schemaVersion: String?,

) {
  constructor(
    ciagProfileRequestDTO: CIAGProfileRequestDTO,
  ) : this(
    offenderId = ciagProfileRequestDTO.offenderId!!,
    createdBy = ciagProfileRequestDTO.createdBy!!,
    createdDateTime = ciagProfileRequestDTO.createdDateTime!!,
    modifiedBy = ciagProfileRequestDTO.modifiedBy!!,
    prisonId = ciagProfileRequestDTO.prisonId,
    prisonName = ciagProfileRequestDTO.prisonName,
    desireToWork = ciagProfileRequestDTO.desireToWork!!,
    modifiedDateTime = ciagProfileRequestDTO.modifiedDateTime!!,
    hopingToGetWork = ciagProfileRequestDTO.hopingToGetWork!!,
    reasonToNotGetWorkOther = ciagProfileRequestDTO.reasonToNotGetWorkOther,
    abilityToWorkOther = ciagProfileRequestDTO.abilityToWorkOther,
    abilityToWork = ciagProfileRequestDTO.abilityToWork,
    reasonToNotGetWork = ciagProfileRequestDTO.reasonToNotGetWork,
    workExperience = ciagProfileRequestDTO.workExperience,
    skillsAndInterests = ciagProfileRequestDTO.skillsAndInterests,
    qualificationsAndTraining = ciagProfileRequestDTO.qualificationsAndTraining,
    inPrisonInterests = ciagProfileRequestDTO.inPrisonInterests,

    schemaVersion = ciagProfileRequestDTO.schemaVersion,
  )

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as CIAGProfile

    if (offenderId != other.offenderId) return false
    if (desireToWork != other.desireToWork) return false
    if (hopingToGetWork != other.hopingToGetWork) return false
    if (reasonToNotGetWorkOther != other.reasonToNotGetWorkOther) return false
    if (abilityToWorkOther != other.abilityToWorkOther) return false
    if (abilityToWork != other.abilityToWork) return false
    if (reasonToNotGetWork != other.reasonToNotGetWork) return false
    if (workExperience != other.workExperience) return false
    if (skillsAndInterests != other.skillsAndInterests) return false
    if (qualificationsAndTraining != other.qualificationsAndTraining) return false
    if (inPrisonInterests != other.inPrisonInterests) return false
    if (schemaVersion != other.schemaVersion) return false
    if (prisonId != other.prisonId) return false
    if (prisonName != other.prisonName) return false

    return true
  }

  override fun hashCode(): Int {
    var result = offenderId.hashCode()
    result = 31 * result + desireToWork.hashCode()
    result = 31 * result + hopingToGetWork.hashCode()
    result = 31 * result + (reasonToNotGetWorkOther?.hashCode() ?: 0)
    result = 31 * result + (abilityToWorkOther?.hashCode() ?: 0)
    result = 31 * result + abilityToWork.hashCode()
    result = 31 * result + reasonToNotGetWork.hashCode()
    result = 31 * result + workExperience.hashCode()
    result = 31 * result + skillsAndInterests.hashCode()
    result = 31 * result + qualificationsAndTraining.hashCode()
    result = 31 * result + inPrisonInterests.hashCode()
    result = 31 * result + schemaVersion.hashCode()
    result = 31 * result + prisonId.hashCode()
    result = 31 * result + prisonName.hashCode()
    return result
  }

  override fun toString(): String {
    return "CIAGProfile(offenderId='$offenderId', createdBy='$createdBy', createdDateTime=$createdDateTime, modifiedBy='$modifiedBy', desireToWork=$desireToWork, modifiedDateTime=$modifiedDateTime, hopingToGetWork=$hopingToGetWork, reasonToNotGetWorkOther=$reasonToNotGetWorkOther, abilityToWorkOther=$abilityToWorkOther, abilityToWork=$abilityToWork, reasonToNotGetWork=$reasonToNotGetWork, prisonName=$prisonName, prisonId=$prisonId, workExperience=$workExperience,  skillsAndInterests=$skillsAndInterests, qualificationsAndTraining=$qualificationsAndTraining, inPrisonInterests=$inPrisonInterests, schemaVersion='$schemaVersion')"
  }
}
