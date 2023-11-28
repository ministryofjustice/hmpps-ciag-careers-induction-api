package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.ErrorResponse
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileListDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileOffenderIdListRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.CIAGProfileService
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.validation.CIAGValidationService

@Validated
@RestController
@RequestMapping("/ciag/induction", produces = [MediaType.APPLICATION_JSON_VALUE])
class CIAGResourceController(
  private val ciagProfileService: CIAGProfileService,
  private val ciagValidationService: CIAGValidationService,
) {
  @PreAuthorize("hasAnyRole('ROLE_EDUCATION_WORK_PLAN_EDITOR','ROLE_EDUCATION_WORK_PLAN_VIEWER')")
  @GetMapping("/{offenderId}")
  @Operation(
    summary = "Fetch the CIAG profile for the offender",
    description = "Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "CIAG profile is returned",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = CIAGProfileDTO::class),
          ),
        ],
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Incorrect permissions to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))],
      ),
      ApiResponse(
        responseCode = "400",
        description = "Invalid Parameters have been passed",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "404",
        description = "Resource not found",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun getCIAGProfileForOffenderId(
    @Valid
    @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    )
    @PathVariable
    offenderId: String,
  ): CIAGProfileDTO? {
    return ciagProfileService.getCIAGProfileForOffender(offenderId)
  }

  @PreAuthorize("hasAnyRole('ROLE_EDUCATION_WORK_PLAN_EDITOR','ROLE_EDUCATION_WORK_PLAN_VIEWER')")
  @PostMapping("/list")
  @Operation(
    summary = "Fetch the CIAG profile for the given offender ids",
    description = "Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "CIAG profile list is returned",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = CIAGProfileListDTO::class),
          ),
        ],
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Incorrect permissions to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))],
      ),
      ApiResponse(
        responseCode = "400",
        description = "Invalid Parameters have been passed",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun getAllCIAGProfileForGivenOffenderIds(
    @Valid @RequestBody
    offenderIdList: CIAGProfileOffenderIdListRequestDTO,
  ): CIAGProfileListDTO {
    return ciagProfileService.getAllCIAGProfileForGivenOffenderIds(offenderIdList.offenderIds)
  }

  @PreAuthorize("hasAnyRole('ROLE_EDUCATION_WORK_PLAN_EDITOR')")
  @DeleteMapping("/{offenderId}")
  @Operation(
    summary = "Delete CIAG Profile",
    description = "Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "Successfully deleted the ,CIAG profile",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = CIAGProfileDTO::class),
          ),
        ],
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Incorrect permissions to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))],
      ),
      ApiResponse(
        responseCode = "400",
        description = "Invalid Parameters have been passed",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "404",
        description = "Resource not found",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun deleteCIAGProfileForOffenderId(
    @Valid
    @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    )
    @PathVariable
    offenderId: String,
  ) {
    return ciagProfileService.deleteCIAGProfile(offenderId)
  }

  @PreAuthorize("hasAnyRole('ROLE_EDUCATION_WORK_PLAN_EDITOR')")
  @PostMapping("/{offenderId}")
  @Operation(
    summary = "Create the CIAG profile for an offender",
    description = "Called once to initially create the previousWork. Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "CIAG profile created",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = CIAGProfileDTO::class),
          ),
        ],
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Incorrect permissions to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "400",
        description = "Invalid Parameters have been passed",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "404",
        description = "Resource not found",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun createOffenderProfile(
    @Valid
    @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    )
    @PathVariable
    offenderId: String,
    @Validated @RequestBody
    requestDTO: CIAGProfileRequestDTO,
  ): CIAGProfileDTO? {
    ciagValidationService.validateInput(requestDTO)
    return ciagProfileService.createOrUpdateCIAGInductionForOffender(requestDTO)?.let {
      CIAGProfileDTO(
        it,
      )
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_EDUCATION_WORK_PLAN_EDITOR')")
  @PutMapping("/{offenderId}")
  @Operation(
    summary = "Update the CIAG profile for an offender",
    description = "Called once to initially create the previousWork. Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "CIAG profile update",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = CIAGProfileDTO::class),
          ),
        ],
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Incorrect permissions to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "400",
        description = "Invalid Parameters have been passed",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "404",
        description = "Resource not found",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun updateOffenderProfile(
    @Valid
    @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    )
    @PathVariable
    offenderId: String,
    @Valid @RequestBody
    requestDTO: CIAGProfileRequestDTO,
  ): CIAGProfileDTO? {
    return ciagProfileService.createOrUpdateCIAGInductionForOffender(requestDTO)?.let {
      CIAGProfileDTO(
        it,
      )
    }
  }
}
