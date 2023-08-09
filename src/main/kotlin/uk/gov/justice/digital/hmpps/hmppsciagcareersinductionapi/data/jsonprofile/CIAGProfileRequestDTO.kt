package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.HopingToGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.ReasonToNotGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.EducationAndQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PrisonWorkAndEducation
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.SkillsAndInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.WorkInterests
import java.time.LocalDateTime

data class CIAGProfileRequestDTO(

  @Schema var offenderId: String?,

  @Schema var createdBy: String?,

  @Schema var createdDateTime: LocalDateTime?,

  @Schema var modifiedBy: String?,

  @Schema var desireToWork: Boolean?,

  @Schema var modifiedDateTime: LocalDateTime?,

  @Schema var hopingToGetWork: HopingToGetWork?,

  @Schema var otherReasonToNotGetWork: String?,

  @Schema var otherAbilityTOWorkImpact: String?,

  @Schema var abilityToWorkImpactDetail: MutableSet<AbilityToWorkImpactedBy>?,

  @Schema var reasonToNotGetWork: MutableSet<ReasonToNotGetWork>?,

  @Schema var previousWorkDetails: PreviousWork?,

  @Schema var workInterests: WorkInterests?,

  @Schema var skillsAndInterests: SkillsAndInterests?,

  @Schema var educationAndQualification: EducationAndQualification?,

  @Schema var prisonWorkAndEducation: PrisonWorkAndEducation?,
  @Schema var schemaVersion: String?,

) {
  constructor(profileEntity: CIAGProfile) : this(
    offenderId = profileEntity.offenderId,
    createdBy = profileEntity.createdBy,
    createdDateTime = profileEntity.createdDateTime,
    modifiedBy = profileEntity.modifiedBy,
    desireToWork = profileEntity.desireToWork,
    modifiedDateTime = profileEntity.modifiedDateTime,
    hopingToGetWork = profileEntity.hopingToGetWork,
    otherReasonToNotGetWork = profileEntity.otherReasonToNotGetWork,
    otherAbilityTOWorkImpact = profileEntity.otherAbilityTOWorkImpact,
    abilityToWorkImpactDetail = profileEntity.abilityToWorkImpactDetail,
    reasonToNotGetWork = profileEntity.reasonToNotGetWork,
    previousWorkDetails = profileEntity.previousWorkDetails,
    workInterests = profileEntity.workInterests,
    skillsAndInterests = profileEntity.skillsAndInterests,
    educationAndQualification = profileEntity.educationAndQualification,
    prisonWorkAndEducation = profileEntity.prisonWorkAndEducation,
    schemaVersion = profileEntity.schemaVersion,
  )
}
