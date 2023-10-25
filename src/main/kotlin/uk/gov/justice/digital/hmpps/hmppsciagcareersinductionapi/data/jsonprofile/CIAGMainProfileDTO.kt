package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.HopingToGetWork
import java.time.LocalDateTime

data class CIAGMainProfileDTO(

  @Schema(description = "This is the ID of the inmate ", name = "offenderId", pattern = "^[A-Z]\\d{4}[A-Z]{2}\$", required = true)
  val offenderId: String,

  @Schema(description = "This is the person who creates the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record creation ", name = "createdBy", required = true)
  var createdBy: String,

  @Schema(description = "This is the creation date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record creation ", name = "createdDateTime", required = true)
  var createdDateTime: LocalDateTime,

  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String,

  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime,

  @Schema(description = "Whether the inmate wants to work or not", name = "desireToWork", required = true)
  var desireToWork: Boolean,

  @Schema(description = "Whether the inmate hopes to get work", name = "hopingToGetWork", required = true)
  var hopingToGetWork: HopingToGetWork,

) {
  constructor(
    offenderId: String,
    createdDateTime: LocalDateTime,
    createdBy: String,

    modifiedBy: String,

    modifiedDateTime: LocalDateTime,
    desireToWork: Boolean,
    hopingToGetWork: Int,
  ) : this(
    offenderId = offenderId!!,
    createdBy = createdBy!!,
    createdDateTime = createdDateTime!!,
    modifiedBy = modifiedBy!!,
    desireToWork = desireToWork!!,
    modifiedDateTime = modifiedDateTime!!,
    hopingToGetWork = HopingToGetWork.fromInt(hopingToGetWork)!!,
  ) {
  }
}
