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
 /*   var ciagProfileSaved = ciagProfileRepository.findById(ciagProfileDTO.offenderId)
    if (ciagProfileSaved != null && ciagProfileSaved.isPresent()) {
      ciagProfileSaved.

      ciagProfileRepository.flush()
    } else {*/
      ciagProfileRepository.saveAndFlush(ciagProfile)
//    }/**/


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
    var ciagProfile = ciagProfileRepository.findById(offenderId)
    if (ciagProfile == null || ciagProfile.isEmpty()) {
      throw NotFoundException(offenderId)
    }
    return ciagProfile.let { CIAGProfileDTO(it.get()) }
  }

  fun deleteCIAGProfile(
    offenderId: String,
  ) {
    var ciagProfile = ciagProfileRepository.findById(offenderId)
    if (ciagProfile == null || ciagProfile.isEmpty()) {
      throw NotFoundException(offenderId)
    } else {
    }
    return ciagProfileRepository.deleteById(offenderId)
  }
}
