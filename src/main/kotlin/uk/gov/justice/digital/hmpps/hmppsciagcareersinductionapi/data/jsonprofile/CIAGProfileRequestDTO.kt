package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.HopingToGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.ReasonToNotGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.EducationAndQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PrisonWorkAndEducation
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.SkillsAndInterests
import java.time.LocalDateTime

data class CIAGProfileRequestDTO(

  @Schema val offenderId: String,

  @Schema var createdBy: String,

  @Schema var createdDateTime: LocalDateTime,

  @Schema var modifiedBy: String,

  @Schema var desireToWork: Boolean,

  @Schema var modifiedDateTime: LocalDateTime,

  @Schema var hopingToGetWork: HopingToGetWork,

  @Schema var reasonToNotGetWorkOther: String?,

  @Schema var abilityToWorkOther: String?,

  @Schema var abilityToWork: MutableSet<AbilityToWorkImpactedBy>?,

  @Schema var reasonToNotGetWork: MutableSet<ReasonToNotGetWork>?,

  @Schema var workExperience: PreviousWork?,

  @Schema var skillsAndInterests: SkillsAndInterests?,

  @Schema var qualificationsAndTraining: EducationAndQualification?,

  @Schema var inPrisonInterests: PrisonWorkAndEducation?,

  @Schema var schemaVersion: String?,

)
