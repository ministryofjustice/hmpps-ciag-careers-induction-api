package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import jakarta.validation.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.AuthorizationServiceException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions.NotFoundException
import java.util.*
import kotlin.collections.ArrayList

@RestControllerAdvice
class ControllerAdvice {
  companion object {
    private val log = LoggerFactory.getLogger(this::class.java)
  }

  @ExceptionHandler(AccessDeniedException::class)
  fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorResponse> {
    log.info("Access denied exception: {}", e.message)
    return ResponseEntity
      .status(HttpStatus.FORBIDDEN)
      .body(
        ErrorResponse(
          status = HttpStatus.FORBIDDEN.value(),
          userMessage = "Authentication problem. Check token and roles - ${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  @ExceptionHandler(AuthorizationServiceException::class)
  fun handleAuthorizationServiceException(e: AccessDeniedException): ResponseEntity<ErrorResponse> {
    log.info("Auth service exception: {}", e.message)
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(
        ErrorResponse(
          status = HttpStatus.UNAUTHORIZED.value(),
          userMessage = "Authentication problem. Check token and roles - ${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  @ExceptionHandler(RestClientResponseException::class)
  fun handleRestClientException(e: RestClientResponseException): ResponseEntity<ErrorResponse> {
    log.error("RestClientResponseException: ${e.message}", e)
    return ResponseEntity
      .status(e.rawStatusCode)
      .body(
        ErrorResponse(
          status = e.rawStatusCode,
          userMessage = "Rest client exception ${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  @ExceptionHandler(RestClientException::class)
  fun handleRestClientException(e: RestClientException): ResponseEntity<ErrorResponse> {
    log.error("RestClientException: ${e.message}", e)
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(
        ErrorResponse(
          status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
          userMessage = "Rest client exception ${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  @ExceptionHandler(ValidationException::class)
  fun handleValidationException(e: ValidationException): ResponseEntity<ErrorResponse> {
    log.info("Validation exception: ${e.message}", e)
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse(
          status = HttpStatus.BAD_REQUEST.value(),
          userMessage = "Validation failure: ${e.message?.let { e.message!!.substring(it.indexOf(":") + 2) }}",
          developerMessage = e.message?.let { e.message!!.substring(it.indexOf(":") + 2) },
        ),
      )
  }

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
    log.info("Validation exception: ${e.message}", e)
    var messages: ArrayList<String> = ArrayList()
    if (0 < e.fieldErrorCount) {
      for (error in e.fieldErrors) {
        buildMessageText(error)?.let { messages.add(it) }
      }
    } else {
      val error = e.globalError
      java.util.List.of(error.defaultMessage)
    }
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse(
          status = HttpStatus.BAD_REQUEST.value(),
          userMessage = messages.joinToString(System.lineSeparator()),
          developerMessage = messages.joinToString(System.lineSeparator()),
        ),
      )
  }

  @ExceptionHandler(value = [MissingKotlinParameterException::class])
  fun handleMissingKotlinParameter(exception: MissingKotlinParameterException): ResponseEntity<ErrorResponse> {
    val fieldName = exception.path.joinToString(separator = ".") { it.fieldName }
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse(
          status = HttpStatus.BAD_REQUEST.value(),
          userMessage = "Validation failure: $fieldName",
          developerMessage = "Missing $fieldName",
        ),
      )
  }

  @ExceptionHandler(HttpMessageNotReadableException::class)
  fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
    log.info("Validation exception: ${e.message}", e)
    val genericMessage = "Unacceptable JSON " + e.message
    var errorDetails: String? = genericMessage
    if (e.cause is MissingKotlinParameterException) {
      val mkpx: MissingKotlinParameterException = e.cause as MissingKotlinParameterException
      val fieldName = mkpx.path.joinToString(separator = ".") { it.fieldName }
      errorDetails = "Missing $fieldName"
    }
    if (e.cause is InvalidFormatException) {
      val ifx: InvalidFormatException = e.cause as InvalidFormatException
      if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
        errorDetails = java.lang.String.format(
          "Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
          ifx.getValue(),
          ifx.getPath().get(ifx.getPath().size - 1).getFieldName(),
          Arrays.toString(ifx.getTargetType().getEnumConstants()),
        )
      }
    }
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse(
          status = HttpStatus.BAD_REQUEST.value(),
          userMessage = "Validation failure: $errorDetails",
          developerMessage = errorDetails,
        ),
      )
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException::class)
  fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
    log.info("Validation exception: ${e.message}", e)
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse(
          status = HttpStatus.BAD_REQUEST.value(),
          userMessage = "Validation failure: ${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  @ExceptionHandler(NotFoundException::class)
  fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
    log.info("NotFoundException: ${e.message}", e)
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(
        ErrorResponse(
          status = HttpStatus.NOT_FOUND.value(),
          userMessage = "${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  @ExceptionHandler(java.lang.Exception::class)
  fun handleException(e: java.lang.Exception): ResponseEntity<ErrorResponse?>? {
    log.error("Unexpected exception: ${e.message}", e)
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(
        ErrorResponse(
          status = HttpStatus.INTERNAL_SERVER_ERROR,
          userMessage = "Unexpected error: ${e.message}",
          developerMessage = e.message,
        ),
      )
  }

  fun buildMessageText(error: FieldError): String? {
    return if (null != error.defaultMessage) {
      "Field: %s, Value: %s,  Error: %s".format(error.field, error.rejectedValue, error.defaultMessage)
    } else {
      "Invalid value for '%s'.".format(error.field)
    }
  }
}

data class ErrorResponse(
  val status: Int,
  val errorCode: String? = null,
  val userMessage: String? = null,
  val developerMessage: String? = null,
  val moreInfo: String? = null,
) {
  constructor(
    status: HttpStatus,
    errorCode: String? = null,
    userMessage: String? = null,
    developerMessage: String? = null,
    moreInfo: String? = null,
  ) :
    this(status.value(), errorCode, userMessage, developerMessage, moreInfo)
}
