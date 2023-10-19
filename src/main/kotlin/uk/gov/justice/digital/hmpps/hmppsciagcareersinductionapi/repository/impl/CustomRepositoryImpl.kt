package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.impl

import org.springframework.data.repository.query.Param
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CustomRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CustomRepositoryImpl : CustomRepository {
  @PersistenceContext
  private val entityManager: EntityManager? = null

  override fun findCIAGProfileByEntityGraph(offenderId: String): CIAGProfile? {
    val entityGraph = entityManager?.createEntityGraph(CIAGProfile::class.java)
    entityGraph?.addAttributeNodes("abilityToWork", "reasonToNotGetWork", "workExperience", "skillsAndInterests", "qualificationsAndTraining", "inPrisonInterests")
    val properties = mutableMapOf<String, Any>()

    properties["javax.persistence.fetchgraph"] = entityGraph!!
    return entityManager?.find(CIAGProfile::class.java, offenderId, properties)
  }

  override fun findAllCIAGProfilesByIdList(@Param("offenderIdList") offenderIdList: List<String>): MutableList<CIAGProfileDTO>? {
    val query = entityManager?.createNamedQuery("CIAGProfile.findInductionsByIdList_Named", CIAGProfileDTO::class.java)
    query?.setParameter("offenderIdList", offenderIdList)
    return query?.getResultList()
  }
}
