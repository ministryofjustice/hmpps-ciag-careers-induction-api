package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.validation

import jakarta.validation.Valid
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Service
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.OtherQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PersonalInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonTraining
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.ReasonToNotGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Skills
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO

@Service
@Validated
class CIAGValidationService {

  var emptyField: String = "Value cannot be empty"
  var invalidField: String = "Field cannot be populated"

  fun validateInput(@Valid ciagProfileRequestDTO: CIAGProfileRequestDTO) {
    val result = BeanPropertyBindingResult(ciagProfileRequestDTO, "ciagProfileRequestDTO")
    if (ciagProfileRequestDTO.reasonToNotGetWork?.contains(ReasonToNotGetWork.OTHER) == true && ciagProfileRequestDTO.reasonToNotGetWorkOther == null) {
      val fieldError = FieldError("ciagProfileRequestDTO.reasonToNotGetWorkOther", "ciagProfileRequestDTO.reasonToNotGetWorkOther", null, false, null, null, emptyField)
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.reasonToNotGetWork?.contains(ReasonToNotGetWork.OTHER) == false && ciagProfileRequestDTO.reasonToNotGetWorkOther?.isNotEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.reasonToNotGetWorkOther", "ciagProfileRequestDTO.reasonToNotGetWorkOther", ciagProfileRequestDTO.reasonToNotGetWorkOther, false, null, null, invalidField)
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.abilityToWork?.contains(AbilityToWorkImpactedBy.OTHER) == true && ciagProfileRequestDTO.abilityToWorkOther == null) {
      val fieldError = FieldError("ciagProfileRequestDTO.abilityToWorkOther", "ciagProfileRequestDTO.abilityToWorkOther", null, false, null, null, emptyField)
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.abilityToWork?.contains(AbilityToWorkImpactedBy.OTHER) == false && ciagProfileRequestDTO.abilityToWorkOther?.isNotEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.abilityToWorkOther", "ciagProfileRequestDTO.abilityToWorkOther", ciagProfileRequestDTO.abilityToWorkOther, false, null, null, invalidField)
      result.addError(fieldError)
    }

    validatePreviousExperience(ciagProfileRequestDTO, result)
    validateWorkInterests(ciagProfileRequestDTO, result)
    validatePrisonWorkAndEducation(ciagProfileRequestDTO, result)
    validateSkillAndInterests(ciagProfileRequestDTO, result)
    validateQualificationsAndTraining(ciagProfileRequestDTO, result)
    if (result.hasFieldErrors()) {
      throw MethodArgumentNotValidException(
        MethodParameter(
          this.javaClass.getDeclaredMethod("validateInput", CIAGProfileRequestDTO::class.java),
          0,
        ),
        result,
      )
    }
  }
  fun validatePrisonWorkAndEducation(@Valid ciagProfileRequestDTO: CIAGProfileRequestDTO, result: BeanPropertyBindingResult) {
    if (ciagProfileRequestDTO.inPrisonInterests?.inPrisonEducation?.contains(PrisonTraining.OTHER) == true && ciagProfileRequestDTO.inPrisonInterests?.inPrisonEducationOther == null) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonEducationOther",
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonEducationOther",
        ciagProfileRequestDTO.inPrisonInterests?.inPrisonEducationOther,
        false,
        null,
        null,
        emptyField,
      )
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.inPrisonInterests?.inPrisonEducation?.contains(PrisonTraining.OTHER) == false && ciagProfileRequestDTO.inPrisonInterests?.inPrisonEducationOther?.isNotEmpty() == true) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonEducationOther",
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonEducationOther",
        ciagProfileRequestDTO.inPrisonInterests?.inPrisonEducationOther,
        false,
        null,
        null,
        invalidField,
      )
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.inPrisonInterests?.inPrisonWork?.contains(PrisonWork.OTHER) == true && ciagProfileRequestDTO.inPrisonInterests?.inPrisonWorkOther == null) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonWorkOther",
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonWorkOther",
        ciagProfileRequestDTO.inPrisonInterests?.inPrisonWorkOther,
        false,
        null,
        null,
        emptyField,
      )
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.inPrisonInterests?.inPrisonWork?.contains(PrisonWork.OTHER) == false && ciagProfileRequestDTO.inPrisonInterests?.inPrisonWorkOther?.isNotEmpty() == true) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonWorkOther",
        "ciagProfileRequestDTO.inPrisonInterests.inPrisonWorkOther",
        ciagProfileRequestDTO.inPrisonInterests?.inPrisonWorkOther,
        false,
        null,
        null,
        invalidField,
      )
      result.addError(fieldError)
    }
  }

  fun validateSkillAndInterests(@Valid ciagProfileRequestDTO: CIAGProfileRequestDTO, result: BeanPropertyBindingResult) {
    if (ciagProfileRequestDTO.skillsAndInterests?.skills?.contains(Skills.OTHER) == true && ciagProfileRequestDTO.skillsAndInterests?.skillsOther == null) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.skillsAndInterests.skillsOther",
        "ciagProfileRequestDTO.skillsAndInterests.skillsOther",
        ciagProfileRequestDTO.skillsAndInterests?.skillsOther,
        false,
        null,
        null,
        emptyField,
      )
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.skillsAndInterests?.skills?.contains(Skills.OTHER) == false && ciagProfileRequestDTO.skillsAndInterests?.skillsOther?.isNotEmpty() == true) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.skillsAndInterests.skillsOther",
        "ciagProfileRequestDTO.skillsAndInterests.skillsOther",
        ciagProfileRequestDTO.skillsAndInterests?.skillsOther,
        false,
        null,
        null,
        invalidField,
      )
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.skillsAndInterests?.personalInterests?.contains(PersonalInterests.OTHER) == true && ciagProfileRequestDTO.skillsAndInterests?.personalInterestsOther == null) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.skillsAndInterests.personalInterestsOther",
        "ciagProfileRequestDTO.skillsAndInterests.personalInterestsOther",
        ciagProfileRequestDTO.skillsAndInterests?.personalInterestsOther,
        false,
        null,
        null,
        emptyField,
      )
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.skillsAndInterests?.personalInterests?.contains(PersonalInterests.OTHER) == false && ciagProfileRequestDTO.skillsAndInterests?.personalInterestsOther?.isNotEmpty() == true) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.skillsAndInterests.personalInterestsOther",
        "ciagProfileRequestDTO.skillsAndInterests.personalInterestsOther",
        ciagProfileRequestDTO.skillsAndInterests?.personalInterestsOther,
        false,
        null,
        null,
        invalidField,
      )
      result.addError(fieldError)
    }
  }
  fun validateQualificationsAndTraining(@Valid ciagProfileRequestDTO: CIAGProfileRequestDTO, result: BeanPropertyBindingResult) {
    if (ciagProfileRequestDTO.qualificationsAndTraining?.additionalTraining?.contains(OtherQualification.OTHER) == true && ciagProfileRequestDTO.qualificationsAndTraining?.additionalTrainingOther == null) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.qualificationsAndTraining.additionalTrainingOther",
        "ciagProfileRequestDTO.qualificationsAndTraining.additionalTrainingOther",
        ciagProfileRequestDTO.qualificationsAndTraining?.additionalTrainingOther,
        false,
        null,
        null,
        emptyField,
      )
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.qualificationsAndTraining?.additionalTraining?.contains(OtherQualification.OTHER) == false && ciagProfileRequestDTO.qualificationsAndTraining?.additionalTrainingOther?.isNotEmpty() == true) {
      val fieldError = FieldError(
        "ciagProfileRequestDTO.qualificationsAndTraining.additionalTrainingOther",
        "ciagProfileRequestDTO.qualificationsAndTraining.additionalTrainingOther",
        ciagProfileRequestDTO.qualificationsAndTraining?.additionalTrainingOther,
        false,
        null,
        null,
        invalidField,
      )
      result.addError(fieldError)
    }
  }
  fun validatePreviousExperience(ciagProfileRequestDTO: CIAGProfileRequestDTO, result: BeanPropertyBindingResult) {
    if (ciagProfileRequestDTO.workExperience?.typeOfWorkExperience?.isEmpty() == true || ciagProfileRequestDTO.workExperience?.workExperience?.isEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.workExperience", "ciagProfileRequestDTO.workExperience.workExperience", ciagProfileRequestDTO.workExperience?.workExperience, false, null, null, invalidField)
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.workExperience?.typeOfWorkExperience?.contains(WorkType.OTHER) == true && ciagProfileRequestDTO.workExperience?.typeOfWorkExperienceOther.isNullOrEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.typeOfWorkExperienceOther", "ciagProfileRequestDTO.workExperience.typeOfWorkExperienceOther", ciagProfileRequestDTO.workExperience?.typeOfWorkExperienceOther, false, null, null, emptyField)
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.workExperience?.typeOfWorkExperience?.contains(WorkType.OTHER) == false && ciagProfileRequestDTO.workExperience?.typeOfWorkExperienceOther.isNullOrEmpty() == false) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.typeOfWorkExperienceOther", "ciagProfileRequestDTO.workExperience.typeOfWorkExperienceOther", ciagProfileRequestDTO.workExperience?.typeOfWorkExperienceOther, false, null, null, invalidField)
      result.addError(fieldError)
    }
  }
  fun validateWorkInterests(ciagProfileRequestDTO: CIAGProfileRequestDTO, result: BeanPropertyBindingResult) {
    if (ciagProfileRequestDTO.workExperience?.workInterests != null && ciagProfileRequestDTO.workExperience?.workInterests?.workInterests.isNullOrEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.workInterests.workInterests", "ciagProfileRequestDTO.workExperience.workInterests.workInterests", ciagProfileRequestDTO.workExperience?.workInterests?.workInterests, false, null, null, emptyField)
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.workExperience?.workInterests?.workInterests.isNullOrEmpty() == false && ciagProfileRequestDTO.workExperience?.workInterests?.particularJobInterests.isNullOrEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.workInterests.particularJobInterests", "ciagProfileRequestDTO.workExperience.workInterests.particularJobInterests", ciagProfileRequestDTO.workExperience?.workInterests?.particularJobInterests, false, null, null, emptyField)
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.workExperience?.workInterests?.workInterests.isNullOrEmpty() == true && ciagProfileRequestDTO.workExperience?.workInterests?.particularJobInterests?.isEmpty() == false) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.workInterests.particularJobInterests", "ciagProfileRequestDTO.workExperience.workInterests.particularJobInterests", ciagProfileRequestDTO.workExperience?.workInterests?.particularJobInterests, false, null, null, invalidField)
      result.addError(fieldError)
    }
    if (ciagProfileRequestDTO.workExperience?.workInterests?.workInterests?.contains(WorkType.OTHER) == true && ciagProfileRequestDTO.workExperience?.workInterests?.workInterestsOther.isNullOrEmpty() == true) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther", "ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther", ciagProfileRequestDTO.workExperience?.workInterests?.workInterestsOther, false, null, null, emptyField)
      result.addError(fieldError)
    }

    if (ciagProfileRequestDTO.workExperience?.workInterests?.workInterests?.contains(WorkType.OTHER) == false && ciagProfileRequestDTO.workExperience?.workInterests?.workInterestsOther.isNullOrEmpty() == false) {
      val fieldError = FieldError("ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther", "ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther", ciagProfileRequestDTO.workExperience?.workInterests?.workInterestsOther, false, null, null, invalidField)
      result.addError(fieldError)
    }
  }
}
