package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository

@DataJpaTest
@ActiveProfiles("test")
class RepositoriesTests @Autowired constructor(
  val ciagRepository: CIAGProfileRepository,
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
}
