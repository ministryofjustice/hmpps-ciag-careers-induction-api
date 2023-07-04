package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessment
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessmentLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.TimePeriod
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Training
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedFunctionalLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedTrainjng
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.GoalSteps
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.Goals
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWorkDetail
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.CIAGProfileService
import java.time.LocalDateTime

class TestData {
  companion object {
    private lateinit var profileService: CIAGProfileService
    val createdByString = "createdBy"
    val offenderIdString = "offenderId"
    val modifiedByString = "modifiedBy"
    var previousWorkDetail = PreviousWorkDetail(WorkType.ANIMAL_CARE_AND_FARMING, null, "jobtitle", "Respon")
    var previousWorkDetailSet = mutableSetOf(previousWorkDetail)
    var acchievedFuntionalLevel = AchievedFunctionalLevel(
      FunctionalAssessment.DIGITAL_LITERACY,
      FunctionalAssessmentLevel.LEVEL_1,
      LocalDateTime.now(),
    )
    var acchievedFuntionalLevelSet = mutableSetOf(acchievedFuntionalLevel)
    var acchievedQualification = AchievedQualification("Subject", "grade 2", QualificationLevel.LEVEL_1, true)
    var acchievedQualificationSet = mutableSetOf(acchievedQualification)
    var achievedTraining = AchievedTrainjng(Training.CSCS_CARD, null)
    var achievedTrainjngSet = mutableSetOf(achievedTraining)
    var previousWork = PreviousWork(WorkType.ANIMAL_CARE_AND_FARMING, null)
    var previousWorkSet = mutableSetOf(previousWork)
    var goalStep = GoalSteps("step1", TimePeriod.SIX_TO_TWELEVE)

    var goalstepSet = mutableSetOf(goalStep)
    var goals = Goals("goal1", goalstepSet, null, null)

    var goalSet = mutableSetOf(goals)

    val ciagDTO = CIAGProfileRequestDTO(
      "offen1",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(),
      previousWorkDetailSet,
      acchievedFuntionalLevelSet,
      acchievedQualificationSet,
      achievedTrainjngSet,
      previousWorkSet,
      goalSet,
      "1.1",
    )

    val ciag = CIAGProfile(
      "offen1",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(),
      previousWorkDetailSet,
      acchievedFuntionalLevelSet,
      acchievedQualificationSet,
      achievedTrainjngSet,
      previousWorkSet,
      goalSet,
      "1.1",
    )
  }
}
