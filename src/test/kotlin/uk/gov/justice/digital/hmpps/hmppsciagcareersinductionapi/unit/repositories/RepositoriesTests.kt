package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.EducationAndQualificationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PreviousWorkRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PrisonWorkAndEducationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.SkillsAndInterestsRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.WorkInterestsRepository

@DataJpaTest
@ActiveProfiles("test")
class RepositoriesTests @Autowired constructor(
  val entityManager: TestEntityManager,
  val ciagRepository: CIAGProfileRepository,
  val workInterestsRepository: WorkInterestsRepository,
  val skillsAndInterestsRepository: SkillsAndInterestsRepository,
  val prisonWorkAndEducationRepository: PrisonWorkAndEducationRepository,
  val previousWorkRepository: PreviousWorkRepository,
  val educationAndQualificationRepository: EducationAndQualificationRepository,
) {

  @Test
  fun `When CIAG Profile is created with education and qualification`() {
    assertThat(TestData.ciagEducation.qualificationsAndTraining?.id).isNull()
    val savedCIAGProfile = ciagRepository.saveAndFlush(TestData.ciagEducation)

    val found = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(TestData.ciagEducation.offenderId)

    assertThat(found?.get()?.qualificationsAndTraining?.id).isNotNull()
  }

  @Test
  fun `When CIAG Profile is created with previous work`() {
    assertThat(TestData.ciagPreviousWork.workExperience?.id).isNull()
//    workInterestsRepository.saveAndFlush(TestData.ciagPreviousWork.workExperience?.workInterests)
    val savedCIAGProfile = ciagRepository.saveAndFlush(TestData.ciagPreviousWork)

    val found = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(TestData.ciagPreviousWork.offenderId)

    assertThat(found?.get()?.workExperience?.id).isNotNull()
  }

  @Test
  fun `When CIAG Profile is created with prison work and education`() {
    assertThat(TestData.ciagPrisonWork.inPrisonInterests?.id).isNull()
    val savedCIAGProfile = ciagRepository.saveAndFlush(TestData.ciagPrisonWork)

    val found = ciagRepository.findById(TestData.ciagPrisonWork.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(TestData.ciagPrisonWork.offenderId)

    assertThat(found?.get()?.inPrisonInterests?.id).isNotNull()
  }
/*
  @Test
  fun `When create then return CIAG`() {
    ciagRepository.saveAndFlush(TestData.ciag)

    entityManager.flush()

    val found = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found).isEqualTo(TestData.ciag)
  }

  @Test
  fun `When update type 1 then return CIAG`() {
    ciagRepository.saveAndFlush(TestData.ciag)

    val found = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found).isEqualTo(TestData.ciag)

    ciagRepository.saveAndFlush(found)
    val foundUpdated = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found).isEqualTo(foundUpdated)
  }

  @Test
  fun `When update type 2 then return CIAG`() {
    ciagRepository.saveAndFlush(TestData.ciag)

    val found = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found).isEqualTo(TestData.ciag)

    val foundUpdated = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found).isEqualTo(foundUpdated)
  }

  @Test
  fun `When delete then return null`() {
    ciagRepository.saveAndFlush(TestData.ciag)

    val found = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(found).isEqualTo(TestData.ciag)

    ciagRepository.delete(found)
    val foundUpdated = ciagRepository.findById(TestData.ciag.offenderId!!)
    assertThat(foundUpdated).isNull()
  }*/
}
