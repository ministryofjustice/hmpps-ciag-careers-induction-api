package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedFunctionalLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedTrainjng
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.Goals
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWorkDetail
import java.time.LocalDateTime

data class CIAGProfileRequestDTO(
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

)
