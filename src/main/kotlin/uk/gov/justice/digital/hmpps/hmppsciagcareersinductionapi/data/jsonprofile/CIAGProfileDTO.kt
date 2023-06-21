package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedFunctionalLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedTrainjng
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.Goals
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWorkDetail
import java.time.LocalDateTime

data class CIAGProfileDTO(
  @Schema(description = "Offender Id", example = "ABC12345")
  val offenderId: String,

  var bookingId: Long,

  var createdBy: String,

  var createdDateTime: LocalDateTime,

  var modifiedBy: String,

  var desireToWork: Boolean,

  var modifiedDateTime: LocalDateTime,

  var previousWorkDetail: MutableSet<PreviousWorkDetail>,

  var achievedFunctionalLevel: MutableSet<AchievedFunctionalLevel>,

  var achievedQualification: MutableSet<AchievedQualification>,

  var achievedTrainjng: MutableSet<AchievedTrainjng>,

  var previousWork: MutableSet<PreviousWork>,

  var goals: MutableSet<Goals>,

  var schemaVersion: String,

) {
  constructor(profileEntity: CIAGProfile) : this(
    offenderId = profileEntity.offenderId,
    bookingId = profileEntity.bookingId,
    createdBy = profileEntity.createdBy,
    createdDateTime = profileEntity.createdDateTime,
    modifiedBy = profileEntity.modifiedBy,
    desireToWork = profileEntity.desireToWork,
    modifiedDateTime = profileEntity.modifiedDateTime,
    previousWorkDetail = profileEntity.previousWorkDetail,
    achievedFunctionalLevel = profileEntity.achievedFunctionalLevel,
    achievedQualification = profileEntity.achievedQualification,
    achievedTrainjng = profileEntity.achievedTrainjng,
    previousWork = profileEntity.previousWork,
    goals = profileEntity.goals,
    schemaVersion = profileEntity.schemaVersion,
  )
}
