package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileListDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.NotFoundException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.EventType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.OutboundEventsService
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.EducationAndQualificationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PreviousWorkRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.PrisonWorkAndEducationRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.SkillsAndInterestsRepository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.WorkInterestsRepository

@Service
class CIAGProfileService(
  private val ciagProfileRepository: CIAGProfileRepository,
  private val educationAndQualificationRepository: EducationAndQualificationRepository,
  private val previousWorkRepository: PreviousWorkRepository,
  private val prisonWorkAndEducationRepository: PrisonWorkAndEducationRepository,
  private val skillsAndInterestsRepository: SkillsAndInterestsRepository,
  private val workInterestsRepository: WorkInterestsRepository,
  private val outboundEventsService: OutboundEventsService,
) {

  fun createOrUpdateCIAGInductionForOffender(
    ciagProfileDTO: CIAGProfileRequestDTO,

  ): CIAGProfile? {
    var ciagProfile = CIAGProfile(ciagProfileDTO)
    var eventType: EventType = EventType.CIAG_INDUCTION_CREATED
    if (ciagProfileRepository.existsById(ciagProfile.offenderId)) {
      eventType = EventType.CIAG_INDUCTION_UPDATED
    }
    ciagProfile.skillsAndInterests?.let { skillsAndInterestsRepository.save(it) }
    ciagProfile.qualificationsAndTraining?.let { educationAndQualificationRepository.save(it) }
    ciagProfile.inPrisonInterests?.let { prisonWorkAndEducationRepository.save(it) }
    ciagProfile.workExperience?.workInterests?.let { workInterestsRepository.save(it) }
    ciagProfile.workExperience?.let { previousWorkRepository.save(it) }
    ciagProfile = ciagProfileRepository.saveAndFlush(ciagProfile)
    outboundEventsService.createAndPublishEventMessage(ciagProfile, eventType)
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
  fun getAllCIAGProfileForGivenOffenderIds(
    offenderIdList: List<String>,
  ): CIAGProfileListDTO {
    var ciagProfileMutableList = ciagProfileRepository.findAllCIAGProfilesByIdList(offenderIdList)
    var ciagProfileDTOList = CIAGProfileListDTO(ciagProfileMutableList)

    return ciagProfileDTOList
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
