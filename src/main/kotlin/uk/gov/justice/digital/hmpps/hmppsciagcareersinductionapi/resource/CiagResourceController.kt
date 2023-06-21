package uk.gov.justice.digital.hmpps.ciag.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.ErrorResponse
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.service.CIAGProfileService
import javax.validation.Valid
import javax.validation.constraints.Pattern

@Validated
@RestController
@RequestMapping("/ciag", produces = [MediaType.APPLICATION_JSON_VALUE])
class CiagResourceController(
  private val ciagProfileService: CIAGProfileService,
) {

  //  @PreAuthorize("hasAnyRole('CIAG_EDIT_VIEW','CIAG_EDIT')")
  @GetMapping("/{offenderId}")
  @Operation(
    summary = "Fetch the test message",
    description = "Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "test Message",
        content = [
          Content(
            mediaType = "application/json",
            schema = Schema(implementation = String::class),
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
    ],
  )
  fun getCIAGProfileForOffenderId(
    @Valid @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    ) @PathVariable offenderId: String,
  ): CIAGProfileDTO? {
    return ciagProfileService.getCIAGProfileDTO(offenderId)
  }

  //  @PreAuthorize("hasRole('CIAG_EDIT')")
  @PostMapping("/{offenderId}")
  @Operation(
    summary = "Create the CIAG profile for an offender",
    description = "Called once to initially create the profile. Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
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
    ],
  )
  fun createOffenderProfile(
    @Valid @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    ) @PathVariable offenderId: String,
    @Valid @RequestBody requestDTO: CIAGProfileRequestDTO,
    @AuthenticationPrincipal oauth2User: String,
  ): CIAGProfileDTO? {
    requestDTO.modifiedBy = oauth2User
    return ciagProfileService.createCIAGProfileForOffender(requestDTO)?.let {
      CIAGProfileDTO(

        it,
      )
    }
  }

  @PutMapping("/{offenderId}")
  @Operation(
    summary = "Create the CIAG profile for an offender",
    description = "Called once to initially create the profile. Currently requires role <b>ROLE_VIEW_PRISONER_DATA</b>",
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
    ],
  )
  fun updateOffenderProfile(
    @Valid @Pattern(
      regexp = "^[A-Z]\\d{4}[A-Z]{2}\$",
      message = "Invalid Offender Id",
    ) @PathVariable offenderId: String,
    @Valid @RequestBody requestDTO: CIAGProfileRequestDTO,
    @AuthenticationPrincipal oauth2User: String,
  ): CIAGProfileDTO? {
    requestDTO.modifiedBy = oauth2User
    return ciagProfileService.createCIAGProfileForOffender(requestDTO)?.let {
      CIAGProfileDTO(
        it,
      )
    }
  }
}
