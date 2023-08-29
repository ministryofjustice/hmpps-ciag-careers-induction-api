package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.NotFoundException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import java.time.LocalDateTime

@Service
class CIAGProfileService(
  private val ciagProfileRepository: CIAGProfileRepository,
) {
  fun createOrUpdateCIAGInductionForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): CIAGProfile? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)
    var ciagProfileSavedOptional = ciagProfileRepository.findById(ciagProfileDTO.offenderId)
    ciagProfile.createdDateTime = LocalDateTime.now()
    ciagProfile.createdBy = ciagProfileDTO.modifiedBy
    if (ciagProfileSavedOptional != null && ciagProfileSavedOptional.isPresent()) {
      ciagProfile = updateCIAGInductionForOffender(ciagProfileDTO)!!
    } else {
      ciagProfile = ciagProfileRepository.saveAndFlush(ciagProfile)
    }

    return ciagProfile
  }

  fun updateCIAGInductionForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): CIAGProfile? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)
    var ciagProfileSavedOptional = ciagProfileRepository.findById(ciagProfileDTO.offenderId)
    ciagProfile.createdDateTime = LocalDateTime.now()
    ciagProfile.createdBy = ciagProfileDTO.modifiedBy
    if (ciagProfileSavedOptional != null && ciagProfileSavedOptional.isPresent()) {
      if (!ciagProfileSavedOptional.get().equals(ciagProfile)) {
        var ciagProfileSaved = ciagProfileSavedOptional.get()
        ciagProfile.modifiedDateTime = LocalDateTime.now()
        ciagProfile.modifiedBy = ciagProfileDTO.modifiedBy
        ciagProfile.createdDateTime = ciagProfileSaved.modifiedDateTime
        ciagProfile.createdBy = ciagProfileSaved.createdBy

        if (ciagProfileSaved.inPrisonInterests?.equals(ciagProfile.inPrisonInterests) == false) {
          ciagProfile.inPrisonInterests?.modifiedBy = ciagProfileSaved.modifiedBy
          ciagProfile.inPrisonInterests?.modifiedDateTime = LocalDateTime.now()
        }

        if (ciagProfileSaved.workExperience?.equals(ciagProfile.workExperience) == false) {
          ciagProfile.workExperience?.modifiedBy = ciagProfileSaved.modifiedBy
          ciagProfile.workExperience?.modifiedDateTime = LocalDateTime.now()
        }

        if (ciagProfileSaved.qualificationsAndTraining?.equals(ciagProfile.qualificationsAndTraining) == false) {
          ciagProfile.qualificationsAndTraining?.modifiedBy = ciagProfileSaved.modifiedBy
          ciagProfile.qualificationsAndTraining?.modifiedDateTime = LocalDateTime.now()
        }
        if (ciagProfileSaved.skillsAndInterests?.equals(ciagProfile.skillsAndInterests) == false) {
          ciagProfile.skillsAndInterests?.modifiedBy = ciagProfileSaved.modifiedBy
          ciagProfile.skillsAndInterests?.modifiedDateTime = LocalDateTime.now()
        }
      }
    } else {
      throw NotFoundException(ciagProfileDTO.offenderId)
    }
    ciagProfile = ciagProfileRepository.saveAndFlush(ciagProfile)

    return ciagProfile
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
