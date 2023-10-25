package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

open class MappedSuperclass(
  @LastModifiedBy
  open var modifiedBy: String,

  @LastModifiedDate
  open var modifiedDateTime: LocalDateTime,
)
