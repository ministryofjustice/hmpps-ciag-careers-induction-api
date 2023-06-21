package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.AlreadyExistsException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository

@Service
class CIAGProfileService(
  private val ciagProfileRepository: CIAGProfileRepository,
) {
  fun createCIAGProfileForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): CIAGProfile? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)

    ciagProfileRepository.saveAndFlush(ciagProfile)

    return ciagProfile
  }

  fun updateCIAGProfileForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): CIAGProfileDTO? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)
    if (ciagProfileRepository.existsById(ciagProfile.offenderId)) {
      throw AlreadyExistsException(ciagProfile.offenderId)
    }
    ciagProfileRepository.saveAndFlush(ciagProfile)

    return CIAGProfileDTO(ciagProfile)
  }

  fun deleteCIAGProfileForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): Boolean? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)
    if (ciagProfileRepository.existsById(ciagProfile.offenderId)) {
      throw AlreadyExistsException(ciagProfile.offenderId)
    }
    ciagProfileRepository.delete(ciagProfile)

    return true
  }

  fun getCIAGProfileForOffender(
    offenderId: String,

  ): CIAGProfileDTO? {
    var ciagProfile = ciagProfileRepository.findByOffenderId(offenderId)

    return ciagProfile?.let { CIAGProfileDTO(it) }
  }

  fun getCIAGProfileDTO(
    offenderId: String,

  ): CIAGProfileDTO? {
    var ciagProfile = ciagProfileRepository.findByOffenderId(offenderId)

    return ciagProfile?.let { CIAGProfileDTO(it) }
  }
}
