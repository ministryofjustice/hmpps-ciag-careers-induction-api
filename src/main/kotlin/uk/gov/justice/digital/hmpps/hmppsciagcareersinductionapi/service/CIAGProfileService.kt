package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.AlreadyExistsException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.NotFoundException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository

@Service
class CIAGProfileService(
  private val ciagProfileRepository: CIAGProfileRepository,
) {
  fun createOrUpdateCIAGProfileForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): CIAGProfile? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)

    ciagProfileRepository.saveAndFlush(ciagProfile)

    return ciagProfile
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
  fun deleteCIAGProfileForOffenderByOffenderId(
    offenderId: String,

  ): Boolean? {
    if (!ciagProfileRepository.existsById(offenderId)) {
      throw NotFoundException(offenderId)
    }
    ciagProfileRepository.deleteById(offenderId)

    return true
  }

  fun getCIAGProfileForOffender(
    offenderId: String,

  ): CIAGProfileDTO? {
    var ciagProfile = ciagProfileRepository.findByOffenderId(offenderId)
    if (ciagProfile == null) {
      throw NotFoundException(offenderId)
    }
    return ciagProfile.let { CIAGProfileDTO(it) }
  }

  fun deleteCIAGProfile(
    offenderId: String,
  ): CIAGProfileDTO? {
    var ciagProfile = ciagProfileRepository.findByOffenderId(offenderId)

    return ciagProfile?.let { CIAGProfileDTO(it) }
  }
}
