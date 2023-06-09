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
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.CIAGProfileService

class CIAGProfileServiceTest {
  private val ciagProfileRepository: CIAGProfileRepository = mock()
  private lateinit var profileService: CIAGProfileService

  @BeforeEach
  fun beforeEach() {
    profileService = CIAGProfileService(ciagProfileRepository)
  }

  @Test
  fun `makes a call to the repository to save the CIAG profile`() {
    whenever(ciagProfileRepository.saveAndFlush(any())).thenReturn(TestData.ciag)

    val rProfile = profileService.createOrUpdateCIAGProfileForOffender(TestData.ciagDTO)
    val argumentCaptor = ArgumentCaptor.forClass(CIAGProfile::class.java)
    verify(ciagProfileRepository).saveAndFlush(argumentCaptor.capture())
    assertThat(rProfile).extracting(TestData.createdByString, TestData.offenderIdString)
      .contains(TestData.ciag.createdBy, TestData.ciag.offenderId)
  }

  @Test
  fun `makes a call to the repository to update the CIAG profile`() {
    whenever(ciagProfileRepository.saveAndFlush(TestData.ciag)).thenReturn(TestData.ciag)

    val rProfile = profileService.createOrUpdateCIAGProfileForOffender(TestData.ciagDTO)
    val argumentCaptor = ArgumentCaptor.forClass(CIAGProfile::class.java)
    verify(ciagProfileRepository).saveAndFlush(argumentCaptor.capture())
    assertThat(rProfile).extracting(TestData.createdByString, TestData.offenderIdString, TestData.modifiedByString)
      .contains(TestData.ciag.createdBy, TestData.ciag.offenderId, TestData.ciag.modifiedBy)
    TestData.ciag.modifiedBy = "Paul"
    TestData.ciagDTO.modifiedBy = "Paul"
    whenever(ciagProfileRepository.saveAndFlush(TestData.ciag)).thenReturn(TestData.ciag)
    var modifiedProfile = profileService.createOrUpdateCIAGProfileForOffender(TestData.ciagDTO)
    val modifiedArgumentCaptor = ArgumentCaptor.forClass(CIAGProfile::class.java)
    verify(ciagProfileRepository, times(2)).saveAndFlush(modifiedArgumentCaptor.capture())
    assertThat(modifiedProfile).extracting(
      TestData.createdByString,
      TestData.offenderIdString,
      TestData.modifiedByString,
    ).contains(TestData.ciag.createdBy, TestData.ciag.offenderId, TestData.ciag.modifiedBy)
  }

  @Test
  fun `makes a call to the repository to get the CIAG profile`() {
    whenever(ciagProfileRepository.findByOffenderId((any()))).thenReturn(TestData.ciag)

    val rProfile = profileService.getCIAGProfileForOffender(TestData.ciagDTO.offenderId)
    assertThat(rProfile).extracting(TestData.createdByString, TestData.offenderIdString, TestData.modifiedByString)
      .contains(TestData.ciag.createdBy, TestData.ciag.offenderId, TestData.ciag.modifiedBy)
  }

  @Test
  fun `makes a call to the repository to get the CIAG profile but getting a null object`() {
    AssertionsForClassTypes.assertThatExceptionOfType(NotFoundException::class.java).isThrownBy {
      whenever(ciagProfileRepository.findByOffenderId((any()))).thenReturn(null)
      profileService.getCIAGProfileForOffender(TestData.ciagDTO.offenderId)
    }.withMessageContaining("CIAG profile does not exist for offender")
  }

  @Test
  fun `makes a call to the repository to delete the CIAG profilefor a given Offender id`() {
    whenever(ciagProfileRepository.findByOffenderId((any()))).thenReturn(null)

    val rProfile = profileService.deleteCIAGProfile(TestData.ciagDTO.offenderId)
    assertThat(rProfile).isNull()
  }
}
