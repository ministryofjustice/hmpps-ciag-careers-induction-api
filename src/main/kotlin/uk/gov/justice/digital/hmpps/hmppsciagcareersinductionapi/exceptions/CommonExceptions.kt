package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.exceptions

import org.slf4j.LoggerFactory
import java.util.function.Supplier

class ExceptionHandler {

  companion object {
    private val log = LoggerFactory.getLogger(this::class.java)
  }
}

class AlreadyExistsException(offenderId: String) :
  Exception("CIAG profile already exists for offender $offenderId")

class NotFoundException(var offenderId: String) : Exception("CIAG profile does not exist for offender $offenderId"), Supplier<Throwable> {
  override fun get(): Throwable {
    throw NotFoundException(offenderId)
  }
}

class InvalidStateException(var offenderId: String) : Exception(",CIAG profile is in an invalid state for  $offenderId"), Supplier<Throwable> {
  override fun get(): Throwable {
    throw InvalidStateException(offenderId)
  }
}
