package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGMainProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile

interface CustomRepository {
  fun findCIAGProfileByEntityGraph(offenderId: String): CIAGProfile?
  fun findAllCIAGProfilesByIdList(offenderIdList: List<String>): MutableList<CIAGMainProfileDTO>?
}
