package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedFunctionalLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedTrainjng
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.Goals
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWorkDetail
import java.time.LocalDateTime

data class CIAGProfileRequestDTO(
  @Schema(description = "The Offender Id", example = "OFFEN1") val offenderId: String,
  @Schema var createdBy: String,
  @Schema var createdDateTime: LocalDateTime,
  @Schema var modifiedBy: String,
  @Schema var desireToWork: Boolean,
  @Schema var modifiedDateTime: LocalDateTime,
  @Schema var previousWorkDetail: MutableSet<PreviousWorkDetail>,
  @Schema var achievedFunctionalLevel: MutableSet<AchievedFunctionalLevel>,
  @Schema var achievedQualification: MutableSet<AchievedQualification>,
  @Schema var achievedTrainjng: MutableSet<AchievedTrainjng>,
  @Schema var previousWork: MutableSet<PreviousWork>,
  @Schema var goals: MutableSet<Goals>,
  @Schema var schemaVersion: String,

)
