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

@Entity
@Table(name = "ciag_profile")
data class CIAGProfile(
  @Id
  val offenderId: String,
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
  var otherReasonToNotGetWork: String?,

  @Column(name = "OTHER_ABILITY_TO_WORK_IMPACT")
  var otherAbilityTOWorkImpact: String?,

  @ElementCollection
  @CollectionTable(name = "ABILITY_TO_WORK_IMPACT", joinColumns = [JoinColumn(name = "OFFENDER_ID")])
  @Column(name = "WORK_IMPACT_")
  var abilityToWorkImpactDetail: MutableSet<AbilityToWorkImpactedBy>?,

  @ElementCollection
  @CollectionTable(name = "REASON_NOT_TO_GET_WORK", joinColumns = [JoinColumn(name = "OFFENDER_ID")])
  @Column(name = "REASON")
  var reasonToNotGetWork: MutableSet<ReasonToNotGetWork>?,

  @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "PREVIOUS_WORK_ID")
  var previousWorkDetails: PreviousWork?,

  @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "WORK_INTERESTS_ID")
  var workInterests: WorkInterests?,

  @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "SKILLS_AND_INTERESTS_ID")
  var skillsAndInterests: SkillsAndInterests?,

  @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "EDUCATION_AND_QUALIFICATION_ID")
  var educationAndQualification: EducationAndQualification?,

  @OneToOne(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "PRISON_WORK_AND_EDUCATION_ID")
  var prisonWorkAndEducation: PrisonWorkAndEducation?,

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
    desireToWork = ciagProfileRequestDTO.desireToWork!!,
    modifiedDateTime = ciagProfileRequestDTO.modifiedDateTime!!,
    hopingToGetWork = ciagProfileRequestDTO.hopingToGetWork!!,
    otherReasonToNotGetWork = ciagProfileRequestDTO.otherReasonToNotGetWork,
    otherAbilityTOWorkImpact = ciagProfileRequestDTO.otherAbilityTOWorkImpact,
    abilityToWorkImpactDetail = ciagProfileRequestDTO.abilityToWorkImpactDetail,
    reasonToNotGetWork = ciagProfileRequestDTO.reasonToNotGetWork,
    previousWorkDetails = ciagProfileRequestDTO.previousWorkDetails,
    workInterests = ciagProfileRequestDTO.workInterests,
    skillsAndInterests = ciagProfileRequestDTO.skillsAndInterests,
    educationAndQualification = ciagProfileRequestDTO.educationAndQualification,
    prisonWorkAndEducation = ciagProfileRequestDTO.prisonWorkAndEducation,

    schemaVersion = ciagProfileRequestDTO.schemaVersion,
  ) {
    prisonWorkAndEducation?.modifiedBy = modifiedBy
    prisonWorkAndEducation?.modifiedDateTime = modifiedDateTime
    educationAndQualification?.modifiedBy = modifiedBy
    educationAndQualification?.modifiedDateTime = modifiedDateTime
    skillsAndInterests?.modifiedBy = modifiedBy
    skillsAndInterests?.modifiedDateTime = modifiedDateTime
    workInterests?.modifiedBy = modifiedBy
    workInterests?.modifiedDateTime = modifiedDateTime
    previousWorkDetails?.modifiedBy = modifiedBy
    previousWorkDetails?.modifiedDateTime = modifiedDateTime
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as CIAGProfile

    if (offenderId != other.offenderId) return false
    if (createdBy != other.createdBy) return false
    if (createdDateTime != other.createdDateTime) return false
    if (modifiedBy != other.modifiedBy) return false
    if (desireToWork != other.desireToWork) return false
    if (modifiedDateTime != other.modifiedDateTime) return false
    if (hopingToGetWork != other.hopingToGetWork) return false
    if (otherReasonToNotGetWork != other.otherReasonToNotGetWork) return false
    if (otherAbilityTOWorkImpact != other.otherAbilityTOWorkImpact) return false
    if (abilityToWorkImpactDetail != other.abilityToWorkImpactDetail) return false
    if (reasonToNotGetWork != other.reasonToNotGetWork) return false
    if (previousWorkDetails != other.previousWorkDetails) return false
    if (workInterests != other.workInterests) return false
    if (skillsAndInterests != other.skillsAndInterests) return false
    if (educationAndQualification != other.educationAndQualification) return false
    if (prisonWorkAndEducation != other.prisonWorkAndEducation) return false
    if (schemaVersion != other.schemaVersion) return false

    return true
  }

  override fun hashCode(): Int {
    var result = offenderId.hashCode()
    result = 31 * result + createdBy.hashCode()
    result = 31 * result + createdDateTime.hashCode()
    result = 31 * result + modifiedBy.hashCode()
    result = 31 * result + desireToWork.hashCode()
    result = 31 * result + modifiedDateTime.hashCode()
    result = 31 * result + hopingToGetWork.hashCode()
    result = 31 * result + (otherReasonToNotGetWork?.hashCode() ?: 0)
    result = 31 * result + (otherAbilityTOWorkImpact?.hashCode() ?: 0)
    result = 31 * result + abilityToWorkImpactDetail.hashCode()
    result = 31 * result + reasonToNotGetWork.hashCode()
    result = 31 * result + previousWorkDetails.hashCode()
    result = 31 * result + workInterests.hashCode()
    result = 31 * result + skillsAndInterests.hashCode()
    result = 31 * result + educationAndQualification.hashCode()
    result = 31 * result + prisonWorkAndEducation.hashCode()
    result = 31 * result + schemaVersion.hashCode()
    return result
  }

  override fun toString(): String {
    return "CIAGProfile(offenderId='$offenderId', createdBy='$createdBy', createdDateTime=$createdDateTime, modifiedBy='$modifiedBy', desireToWork=$desireToWork, modifiedDateTime=$modifiedDateTime, hopingToGetWork=$hopingToGetWork, otherReasonToNotGetWork=$otherReasonToNotGetWork, otherAbilityTOWorkImpact=$otherAbilityTOWorkImpact, abilityToWorkImpactDetail=$abilityToWorkImpactDetail, reasonToNotGetWork=$reasonToNotGetWork, previousWorkDetails=$previousWorkDetails, workInterests=$workInterests, skillsAndInterests=$skillsAndInterests, educationAndQualification=$educationAndQualification, prisonWorkAndEducation=$prisonWorkAndEducation, schemaVersion='$schemaVersion')"
  }
}
