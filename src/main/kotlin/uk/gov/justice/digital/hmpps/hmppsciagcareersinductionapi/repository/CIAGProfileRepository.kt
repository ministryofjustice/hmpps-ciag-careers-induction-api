package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile

@Repository
interface CIAGProfileRepository : JpaRepository<CIAGProfile, String> {

//  fun findById(offenderId: String): CIAGProfile?
}
