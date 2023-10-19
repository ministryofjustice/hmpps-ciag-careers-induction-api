package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common

enum class HopingToGetWork {
  YES,
  NO,
  NOT_SURE,
  ;
  companion object {
    fun fromInt(value: Int) = HopingToGetWork.values().first { it.ordinal == value }
  }
}
