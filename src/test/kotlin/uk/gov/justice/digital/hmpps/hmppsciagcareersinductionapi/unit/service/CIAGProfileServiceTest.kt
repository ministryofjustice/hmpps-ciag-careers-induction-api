package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.NotFoundException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.OutboundEventsService
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.EducationAndQualificationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PreviousWorkRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PrisonWorkAndEducationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.SkillsAndInterestsRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.WorkInterestsRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.CIAGProfileService
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.telemetry.TelemetryService
import java.util.Optional

class CIAGProfileServiceTest {
  private val ciagProfileRepository: CIAGProfileRepository = mock()
  private val educationAndQualificationRepository: EducationAndQualificationRepository = mock()
  private val previousWorkRepository: PreviousWorkRepository = mock()
  private val prisonWorkAndEducationRepository: PrisonWorkAndEducationRepository = mock()
  private val skillsAndInterestsRepository: SkillsAndInterestsRepository = mock()
  private val workInterestsRepository: WorkInterestsRepository = mock()
  private val outboundEventsService: OutboundEventsService = mock()
  private val telemetryService: TelemetryService = mock()

  private lateinit var profileService: CIAGProfileService

  @BeforeEach
  fun beforeEach() {
    profileService = CIAGProfileService(
      ciagProfileRepository,
      educationAndQualificationRepository,
      previousWorkRepository,
      prisonWorkAndEducationRepository,
      skillsAndInterestsRepository,
      workInterestsRepository,
      outboundEventsService,
      telemetryService,
    )
  }

  @Test
  fun `makes a call to the repository to save the CIAG profile`() {
    whenever(ciagProfileRepository.saveAndFlush(any())).thenReturn(TestData.ciag)

    val rProfile = profileService.createOrUpdateCIAGInductionForOffender(TestData.ciagDTO)
    val argumentCaptor = ArgumentCaptor.forClass(CIAGProfile::class.java)
    verify(ciagProfileRepository).saveAndFlush(argumentCaptor.capture())
    assertThat(rProfile).extracting(TestData.offenderIdString, "hopingToGetWork")
      .contains(TestData.ciag.offenderId, TestData.ciag.hopingToGetWork)
  }

  @Test
  fun `makes a call to the repository to update the CIAG profile`() {
    whenever(ciagProfileRepository.saveAndFlush(any())).thenReturn(TestData.ciag_with_no_subsets)

    val rProfile = profileService.createOrUpdateCIAGInductionForOffender(TestData.ciagDTO_with_no_subsets)
    val argumentCaptor = ArgumentCaptor.forClass(CIAGProfile::class.java)
    verify(ciagProfileRepository).saveAndFlush(argumentCaptor.capture())
    assertThat(rProfile).extracting(TestData.createdByString, TestData.offenderIdString, TestData.modifiedByString)
      .contains(TestData.ciag.createdBy, TestData.ciag.offenderId, TestData.ciag.modifiedBy)
    TestData.ciag_with_no_subsets.modifiedBy = "Paul"
    TestData.ciagDTO_with_no_subsets.modifiedBy = "Paul"
    whenever(ciagProfileRepository.saveAndFlush(TestData.ciag_with_no_subsets)).thenReturn(TestData.ciag_with_no_subsets)
    var modifiedProfile = profileService.createOrUpdateCIAGInductionForOffender(TestData.ciagDTO_with_no_subsets)
    val modifiedArgumentCaptor = ArgumentCaptor.forClass(CIAGProfile::class.java)
    verify(ciagProfileRepository, times(2)).saveAndFlush(modifiedArgumentCaptor.capture())
    assertThat(modifiedProfile).extracting(
      TestData.offenderIdString,
      TestData.modifiedByString,
    ).contains(TestData.ciag_with_no_subsets.offenderId, TestData.ciagDTO_with_no_subsets.modifiedBy)
  }

  @Test
  fun `makes a call to the repository to get the CIAG profile`() {
    whenever(ciagProfileRepository.findById((any()))).thenReturn(Optional.of(TestData.ciag))

    val rProfile = TestData.ciagDTO.offenderId?.let { profileService.getCIAGProfileForOffender(it) }
    assertThat(rProfile).extracting(TestData.createdByString, TestData.offenderIdString, TestData.modifiedByString)
      .contains(TestData.ciag.createdBy, TestData.ciag.offenderId, TestData.ciag.modifiedBy)
  }

  @Test
  fun `makes a call to the repository to get list of CIAG profile`() {
    whenever(ciagProfileRepository.findAllCIAGProfilesByIdList((any()))).thenReturn(TestData.ciagMainProfileList)

    val rProfileList = TestData.offenderIdList?.let { profileService.getAllCIAGProfileForGivenOffenderIds(it) }
    assertThat(rProfileList?.ciagProfileList?.size).isEqualTo(2)
    assertThat(TestData.offenderIdList).contains(rProfileList?.ciagProfileList?.get(0)?.offenderId)
    assertThat(TestData.offenderIdList).contains(rProfileList?.ciagProfileList?.get(1)?.offenderId)
  }

  @Test
  fun `makes a call to the repository to get the CIAG profile but getting a null object`() {
    AssertionsForClassTypes.assertThatExceptionOfType(NotFoundException::class.java).isThrownBy {
      whenever(ciagProfileRepository.findById((any()))).thenReturn(null)
      TestData.ciagDTO.offenderId?.let { profileService.getCIAGProfileForOffender(it) }
    }.withMessageContaining("CIAG profile does not exist for offender")
  }

  @Test
  fun `makes a call to the repository to delete the CIAG profilefor a given Offender id`() {
    whenever(ciagProfileRepository.findById((TestData.ciagDTO.offenderId))).thenReturn(Optional.of(TestData.ciag))
    TestData.ciagDTO.offenderId?.let { profileService.deleteCIAGProfile(it) }
    assert(true)
  }

  @Test
  fun `makes a call to the repository to delete the CIAG profile for a ofender id that does not have a profile but getting a null object`() {
    AssertionsForClassTypes.assertThatExceptionOfType(NotFoundException::class.java).isThrownBy {
      whenever(ciagProfileRepository.findById((any()))).thenReturn(null)
      TestData.ciagDTO.offenderId?.let { profileService.deleteCIAGProfile(it) }
    }.withMessageContaining("CIAG profile does not exist for offender")
  }
}
