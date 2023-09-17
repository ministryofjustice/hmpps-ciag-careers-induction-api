package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.AbilityToWorkImpactedBy
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.EducationLevels
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.HopingToGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.OtherQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PersonalInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonTraining
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.QualificationLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.ReasonToNotGetWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Skills
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.AchievedQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.EducationAndQualification
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.PrisonWorkAndEducation
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.SkillsAndInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.WorkExperience
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.WorkInterestDetail
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.WorkInterests
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.CIAGProfileService
import java.io.File
import java.lang.Boolean.FALSE
import java.time.LocalDateTime

class TestData {
  companion object {
    val createInvalidTypeOfWorkExperienceOther = File("src/test/resources/testdata/CreateProfile_InvalidTypeOfWorkExperienceOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createTypeOfWorkExperienceOther = File("src/test/resources/testdata/CreateProfile_EmptyTypeOfWorkExperienceOther.json").inputStream().readBytes().toString(Charsets.UTF_8)

    val createInvalidAdditionalTrainingOther = File("src/test/resources/testdata/CreateProfile_InvalidAdditionalTrainingOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createAdditionalTrainingOther = File("src/test/resources/testdata/CreateProfile_EmptyAdditionalTrainingOther.json").inputStream().readBytes().toString(Charsets.UTF_8)

    val createInvalidInPrisonEducationOther = File("src/test/resources/testdata/CreateProfile_InvalidInPrisonEducationOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInPrisonEducationOther = File("src/test/resources/testdata/CreateProfile_EmptyInPrisonEducationOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInvalidInPrisonWorkOther = File("src/test/resources/testdata/CreateProfile_InvalidInPrisonWorkOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInPrisonWorkOther = File("src/test/resources/testdata/CreateProfile_EmptyInPrisonWorkOther.json").inputStream().readBytes().toString(Charsets.UTF_8)

    val createInvalidPersonalInterestsOther = File("src/test/resources/testdata/CreateProfile_InvalidPersonalIntrestsOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createPersonalInterestsOther = File("src/test/resources/testdata/CreateProfile_EmptyPersonalInterestsOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInvalidSkillsOther = File("src/test/resources/testdata/CreateProfile_InvalidSkillsOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createSkillsOther = File("src/test/resources/testdata/CreateProfile_EmptySkillsOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createEmptyParticularJobInterests = File("src/test/resources/testdata/CreateProfile_EmptyParticularJobInterests.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInvalidParticularJobInterests = File("src/test/resources/testdata/CreateProfile_InvalidParticularJobInterests.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInvalidWorkInterestsOther = File("src/test/resources/testdata/CreateProfile_InvalidWorkInterestsOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createEmptyWorkInterestsOther = File("src/test/resources/testdata/CreateProfile_EmptyWorkInterestsOther.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInvalidAbilityToWork = File("src/test/resources/testdata/CreateProfile_InvalidAbilityToWork.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createEmptyAbilityToWork = File("src/test/resources/testdata/CreateProfile_EmptyAbilityToWork.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createProfileJsonRequest = File("src/test/resources/testdata/CreateProfile_correct.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createEmptyReasonToNotGetWork = File("src/test/resources/testdata/CreateProfile_EmptyReasonToNotGetWork.json").inputStream().readBytes().toString(Charsets.UTF_8)
    val createInvalidReasonToNotGetWork = File("src/test/resources/testdata/CreateProfile_InvalidReasonToNotGetWork.json").inputStream().readBytes().toString(Charsets.UTF_8)

    private lateinit var profileService: CIAGProfileService
    val createdByString = "createdBy"
    val offenderIdString = "offenderId"
    val modifiedByString = "modifiedBy"
    var hopingToGetWork = HopingToGetWork.NOT_SURE
    var abilityToWorkImpactDetailList = mutableSetOf(AbilityToWorkImpactedBy.HEALTH_ISSUES)
    var reasonToNotGetWork = mutableSetOf(ReasonToNotGetWork.FULL_TIME_CARER)
    var previousWorkDetail = WorkExperience(WorkType.BEAUTY, null, "jobtitle", "Respon")
    var previousWorkDetailSet = mutableSetOf(previousWorkDetail)
    var workInterests = WorkInterests(
      "Sacintha",
      LocalDateTime.now(),
      null,
      mutableSetOf(WorkType.BEAUTY),
      null,
      mutableSetOf(
        WorkInterestDetail(WorkType.BEAUTY, "tired"),
      ),
      null,
    )

    var previousWork = PreviousWork(FALSE, "Sacintha", LocalDateTime.now(), null, mutableSetOf(WorkType.BEAUTY), null, previousWorkDetailSet, workInterests, null)
    var skillsAndInterests = SkillsAndInterests(
      "Sacintha",
      LocalDateTime.now(),
      null,
      mutableSetOf(Skills.COMMUNICATION),
      null,
      mutableSetOf(PersonalInterests.COMMUNITY),
      null,
      null,
    )
    var acchievedQualification = AchievedQualification("Subject", "grade 2", QualificationLevel.LEVEL_1)

    var acchievedQualificationSet = mutableSetOf(acchievedQualification)
    var educationAndQualification = EducationAndQualification(
      "Sacintha",
      LocalDateTime.now(),
      null,
      EducationLevels.FURTHER_EDUCATION_COLLEGE,
      acchievedQualificationSet,
      mutableSetOf(OtherQualification.CSCS_CARD),
      null,
      null,
    )
    var prisonWorkAndEducation = PrisonWorkAndEducation(
      "Sacintha",
      LocalDateTime.now(),
      null,
      mutableSetOf(PrisonWork.PRISON_LAUNDRY),
      null,
      mutableSetOf(PrisonTraining.BARBERING_AND_HAIRDRESSING),
      null,
      null,
    )

    val ciagDTO = CIAGProfileRequestDTO(
      "A1234AB",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(), HopingToGetWork.NOT_SURE, null, null, abilityToWorkImpactDetailList, reasonToNotGetWork, previousWork, skillsAndInterests, educationAndQualification, prisonWorkAndEducation, "1.1",

    )

    val ciag = CIAGProfile(
      "A1234AB",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(), HopingToGetWork.NOT_SURE, null, null, abilityToWorkImpactDetailList, reasonToNotGetWork, previousWork, skillsAndInterests, educationAndQualification, prisonWorkAndEducation, "1.1",

    )

    val ciagEducation = CIAGProfile(
      "A1234AB",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(), HopingToGetWork.NOT_SURE, null, null, abilityToWorkImpactDetailList, reasonToNotGetWork, null, null, educationAndQualification, null, "1.1",

    )

    val ciagPreviousWork = CIAGProfile(
      "A1234AB",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(), HopingToGetWork.NOT_SURE, null, null, abilityToWorkImpactDetailList, reasonToNotGetWork, previousWork, null, null, null, "1.1",

    )

    val ciagPrisonWork = CIAGProfile(
      "A1234AB",
      "sacintha",
      LocalDateTime.now(),
      "sacintha",
      true,
      LocalDateTime.now(), HopingToGetWork.NOT_SURE, null, null, abilityToWorkImpactDetailList, reasonToNotGetWork, null, null, null, prisonWorkAndEducation, "1.1",

    )
  }
}
