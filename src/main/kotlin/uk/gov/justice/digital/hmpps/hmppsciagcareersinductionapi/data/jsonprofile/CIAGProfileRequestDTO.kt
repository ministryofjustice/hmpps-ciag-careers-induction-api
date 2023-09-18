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

  @Schema(description = "This is the ID of the inmate ", name = "offenderId", pattern = "^[A-Z]\\d{4}[A-Z]{2}\$", required = true)
  val offenderId: String,

  @Schema(description = "This is the person who creates the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record creation ", name = "createdBy", required = true)
  var createdBy: String,

  @Schema(description = "This is the creation date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record creation ", name = "createdDateTime", required = true)
  var createdDateTime: LocalDateTime,

  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String,

  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime,

  @Schema(description = "Whether the inmate wants to work or not", name = "desireToWork", required = true)
  var desireToWork: Boolean,

  @Schema(description = "Whether the inmate hopes to get work", name = "hopingToGetWork", required = true)
  var hopingToGetWork: HopingToGetWork,

  @Schema(description = "This is the reason that is given when the inmate do not want to work .This field is mandatory when  \"reasonToNotGetWork\" has a Value set to \"OTHER\" ", name = "reasonToNotGetWorkOther", required = false)
  var reasonToNotGetWorkOther: String?,

  @Schema(description = "This is the factor affecting work which is peculiar to this inmate  .This field is mandatory when  \"abilityToWork\" has a Value set to \"OTHER\" ", name = "abilityToWorkOther", required = false)
  var abilityToWorkOther: String?,

  @Schema(description = "This is the factors affecting work to this inmate .", name = "abilityToWork", required = false)
  var abilityToWork: MutableSet<AbilityToWorkImpactedBy>?,

  @Schema(description = "This is the reasons for the inmate not to get work.", name = "reasonToNotGetWork", required = false)
  var reasonToNotGetWork: MutableSet<ReasonToNotGetWork>?,

  @Schema(description = "This is the previous experience of the inmate.", name = "workExperience", required = false)
  var workExperience: PreviousWork?,

  @Schema(description = "This is the  skills and interests of the inmate.", name = "skillsAndInterests", required = false)
  var skillsAndInterests: SkillsAndInterests?,

  @Schema(description = "This is the qualification and training achived in the prison by the inmate.", name = "qualificationsAndTraining", required = false)
  var qualificationsAndTraining: EducationAndQualification?,

  @Schema(description = "This is the work and training the inmate.wants to receive in prison", name = "inPrisonInterests", required = false)
  var inPrisonInterests: PrisonWorkAndEducation?,

  @Schema(description = "This is the schema version used", name = "schemaVersion", required = false)
  var schemaVersion: String?,

)
