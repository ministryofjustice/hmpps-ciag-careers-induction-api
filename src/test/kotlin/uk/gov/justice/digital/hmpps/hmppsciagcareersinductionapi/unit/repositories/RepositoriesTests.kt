package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.repositories

import com.fasterxml.jackson.core.type.TypeReference
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Skills
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.EducationAndQualificationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PreviousWorkRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PrisonWorkAndEducationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.SkillsAndInterestsRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.WorkInterestsRepository

@DataJpaTest
@ActiveProfiles("test")
class RepositoriesTests @Autowired constructor(
  val ciagRepository: CIAGProfileRepository,
  val educationAndQualificationRepository: EducationAndQualificationRepository,
  val previousWorkRepository: PreviousWorkRepository,
  val prisonWorkAndEducationRepository: PrisonWorkAndEducationRepository,
  val skillsAndInterestsRepository: SkillsAndInterestsRepository,
  val workInterestsRepository: WorkInterestsRepository,
) {
  @Mock
  private val securityContext: SecurityContext? = null

  @Mock
  private val authentication: Authentication? = null

  @BeforeEach
  fun beforeClass() {
    whenever(authentication?.principal).thenReturn("test_id")
    whenever(securityContext?.authentication).thenReturn(authentication)
    SecurityContextHolder.setContext(securityContext)
    ciagRepository.deleteAll()
  }

  @Test
  fun `When CIAG Profile is created with education and qualification`() {
    var initialProfile = getCIAGProfile()
    assertThat(initialProfile.qualificationsAndTraining?.id).isNull()
    skillsAndInterestsRepository.save(initialProfile.skillsAndInterests)
    educationAndQualificationRepository.save(initialProfile.qualificationsAndTraining)
    prisonWorkAndEducationRepository.save(initialProfile.inPrisonInterests)
    workInterestsRepository.save(initialProfile.workExperience?.workInterests)
    previousWorkRepository.save(initialProfile.workExperience)
    val savedCIAGProfile = ciagRepository.saveAndFlush(initialProfile)

    val found = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(found?.get()?.qualificationsAndTraining?.id).isNotNull()
  }

  @Test
  fun `When CIAG Profile is created with skills`() {
    var initialProfile = getCIAGProfile()
    assertThat(initialProfile.skillsAndInterests?.id).isNull()
    skillsAndInterestsRepository.save(initialProfile.skillsAndInterests)
    educationAndQualificationRepository.save(initialProfile.qualificationsAndTraining)
    prisonWorkAndEducationRepository.save(initialProfile.inPrisonInterests)
    workInterestsRepository.save(initialProfile.workExperience?.workInterests)
    previousWorkRepository.save(initialProfile.workExperience)
    val savedCIAGProfile = ciagRepository.saveAndFlush(initialProfile)

    val found = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(found?.get()?.skillsAndInterests?.id).isNotNull()
    found?.get()?.skillsAndInterests = null

    ciagRepository.saveAndFlush(found?.get())

    val foundWithoutSkills = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(foundWithoutSkills?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(foundWithoutSkills?.get()?.skillsAndInterests).isNull()
  }

  @Test
  fun `When CIAG Profile is created with skills and skills updated`() {
    var initialProfile = getCIAGProfile()
    assertThat(TestData.ciagSecond.skillsAndInterests?.id).isNull()
    skillsAndInterestsRepository.save(initialProfile.skillsAndInterests)
    educationAndQualificationRepository.save(initialProfile.qualificationsAndTraining)
    prisonWorkAndEducationRepository.save(initialProfile.inPrisonInterests)
    workInterestsRepository.save(initialProfile.workExperience?.workInterests)
    previousWorkRepository.save(initialProfile.workExperience)
    val savedCIAGProfile = ciagRepository.saveAndFlush(initialProfile)

    val found = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(found?.get()?.skillsAndInterests?.id).isNotNull()
    assertThat(found?.get()?.skillsAndInterests?.skills?.size).isEqualTo(8)
    found?.get()?.skillsAndInterests?.skills?.add(Skills.POSITIVE_ATTITUDE)
    skillsAndInterestsRepository.save(found?.get()?.skillsAndInterests)
    val updatedCIAGProfile = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(updatedCIAGProfile?.get()?.skillsAndInterests?.skills?.size).isEqualTo(9)
  }

  @Test
  fun `When CIAG Profile is created with skills and then updated with skills removed`() {
    var initialProfile = getCIAGProfile()
    assertThat(TestData.ciagSecond.skillsAndInterests?.id).isNull()
    skillsAndInterestsRepository.save(initialProfile.skillsAndInterests)
    educationAndQualificationRepository.save(initialProfile.qualificationsAndTraining)
    prisonWorkAndEducationRepository.save(initialProfile.inPrisonInterests)
    workInterestsRepository.save(initialProfile.workExperience?.workInterests)
    previousWorkRepository.save(initialProfile.workExperience)
    val savedCIAGProfile = ciagRepository.saveAndFlush(initialProfile)

    val found = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(found?.get()?.skillsAndInterests?.id).isNotNull()
    found?.get()?.skillsAndInterests = null

    ciagRepository.saveAndFlush(found?.get())

    val foundWithoutSkills = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(foundWithoutSkills?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(foundWithoutSkills?.get()?.skillsAndInterests).isNull()
  }

  @Test
  fun `When CIAG Profile is created with previous work`() {
    var initialProfile = getCIAGProfile()
    assertThat(initialProfile.workExperience?.id).isNull()
    skillsAndInterestsRepository.save(initialProfile.skillsAndInterests)
    educationAndQualificationRepository.save(initialProfile.qualificationsAndTraining)
    prisonWorkAndEducationRepository.save(initialProfile.inPrisonInterests)
    workInterestsRepository.save(initialProfile.workExperience?.workInterests)
    previousWorkRepository.save(initialProfile.workExperience)

    val savedCIAGProfile = ciagRepository.saveAndFlush(initialProfile)

    val found = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(initialProfile.offenderId)

    assertThat(found?.get()?.workExperience?.id).isNotNull()
  }

  @Test
  fun `When CIAG Profile is created with prison work and education`() {
    var initialProfile = getCIAGProfile()
    assertThat(initialProfile.inPrisonInterests?.id).isNull()
    skillsAndInterestsRepository.save(initialProfile.skillsAndInterests)
    educationAndQualificationRepository.save(initialProfile.qualificationsAndTraining)
    prisonWorkAndEducationRepository.save(initialProfile.inPrisonInterests)
    workInterestsRepository.save(initialProfile.workExperience?.workInterests)
    previousWorkRepository.save(initialProfile.workExperience)
    val savedCIAGProfile = ciagRepository.saveAndFlush(initialProfile)

    val found = ciagRepository.findById(initialProfile.offenderId!!)
    assertThat(found?.get()?.offenderId).isEqualTo(initialProfile.offenderId)
    ciagRepository.saveAndFlush(initialProfile)
    assertThat(found?.get()?.inPrisonInterests?.id).isNotNull()
  }

  fun getCIAGProfile(): CIAGProfile {
    val ciagProfile = CapturedSpringConfigValues.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_unitTest_Full_CIAG_profile,
      object : TypeReference<CIAGProfile>() {},
    )
    return ciagProfile
  }
}
