package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessment
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FunctionalAssessmentLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.TimePeriod
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Training
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedFunctionalLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedTrainjng
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.GoalSteps
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.Goals
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWorkDetail
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import java.time.LocalDateTime

@DataJpaTest
@ActiveProfiles("test")
class RepositoriesTests @Autowired constructor(
  val entityManager: TestEntityManager,
  val ciagRepository: CIAGProfileRepository,
) {

  @Test
  fun `When create then return CIAG`() {
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

    val ciag = CIAGProfile(
      "offen1", 123, "sacintha", LocalDateTime.now(),
      "sacintha", true, LocalDateTime.now(), previousWorkDetailSet, acchievedFuntionalLevelSet, acchievedQualificationSet, achievedTrainjngSet, previousWorkSet, goalSet, "1.1",
    )
    entityManager.persist(ciag)

    entityManager.flush()
    ciag.goals?.addAll(goalSet)
    entityManager.merge(ciag)
    entityManager.flush()
    val found = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(found).isEqualTo(ciag)
  }

  @Test
  fun `When update type 1 then return CIAG`() {
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

    val ciag = CIAGProfile(
      "offen1", 123, "sacintha", LocalDateTime.now(),
      "sacintha", true, LocalDateTime.now(), previousWorkDetailSet, acchievedFuntionalLevelSet, acchievedQualificationSet, achievedTrainjngSet, previousWorkSet, goalSet, "1.1",
    )
    entityManager.persist(ciag)

    entityManager.flush()
    ciag.goals?.addAll(goalSet)
    entityManager.merge(ciag)
    entityManager.flush()
    val found = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(found).isEqualTo(ciag)

    goalStep = GoalSteps("step2", TimePeriod.THREE_TO_SIX)

    goalstepSet = mutableSetOf(goalStep)
    goals = Goals("goal2", goalstepSet, null, null)

    goalSet = mutableSetOf(goals)
    found?.goals?.addAll(goalSet)
    entityManager.merge(found)
    val foundUpdated = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(found).isEqualTo(foundUpdated)
  }

  @Test
  fun `When update type 2 then return CIAG`() {
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

    val ciag = CIAGProfile(
      "offen1", 123, "sacintha", LocalDateTime.now(),
      "sacintha", true, LocalDateTime.now(), previousWorkDetailSet, acchievedFuntionalLevelSet, acchievedQualificationSet, achievedTrainjngSet, previousWorkSet, goalSet, "1.1",
    )
    entityManager.persist(ciag)

    entityManager.flush()
    ciag.goals?.addAll(goalSet)
    entityManager.merge(ciag)
    entityManager.flush()
    val found = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(found).isEqualTo(ciag)

    goalStep = GoalSteps("step2", TimePeriod.THREE_TO_SIX)

    goalstepSet = mutableSetOf(goalStep)
    goals = Goals("goal2", goalstepSet, null, null)

    goalSet = mutableSetOf(goals)
    found?.goals?.clear()
    entityManager.merge(found)
    val foundUpdated = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(found).isEqualTo(foundUpdated)
  }

  @Test
  fun `When delete then return null`() {
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

    val ciag = CIAGProfile(
      "offen1", 123, "sacintha", LocalDateTime.now(),
      "sacintha", true, LocalDateTime.now(), previousWorkDetailSet, acchievedFuntionalLevelSet, acchievedQualificationSet, achievedTrainjngSet, previousWorkSet, goalSet, "1.1",
    )
    entityManager.persist(ciag)

    entityManager.flush()
    ciag.goals?.addAll(goalSet)
    entityManager.merge(ciag)
    entityManager.flush()
    val found = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(found).isEqualTo(ciag)

    goalStep = GoalSteps("step2", TimePeriod.THREE_TO_SIX)

    goalstepSet = mutableSetOf(goalStep)
    goals = Goals("goal2", goalstepSet, null, null)

    goalSet = mutableSetOf(goals)
    found?.goals?.clear()
    entityManager.remove(found)
    val foundUpdated = ciagRepository.findByOffenderId(ciag.offenderId!!)
    assertThat(foundUpdated).isNull()
  }
}
