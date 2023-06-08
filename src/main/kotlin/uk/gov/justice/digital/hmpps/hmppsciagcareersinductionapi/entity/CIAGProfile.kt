package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.ciag.AchievedFunctionalLevel
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.ciag.PreviousWorkDetail
import java.time.LocalDateTime
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "ciag_profile")
@TypeDefs(
  TypeDef(name = "json", typeClass = JsonType::class),
)
data class CIAGProfile(
  @Id
  val offenderId: String,

  var bookingId: Long,

  @CreatedBy
  var createdBy: String,

  @CreatedDate
  var createdDateTime: LocalDateTime,

  @LastModifiedBy
  var modifiedBy: String,

  @Column(name = "DESIRE_TO_WORK")
  var desireToWork: Boolean,

  @LastModifiedDate
  var modifiedDateTime: LocalDateTime,

  @ElementCollection
  @CollectionTable(name = "Functional")
  @Column(name = "Previous_Work_Detail")
  var previousWorkDetail: List<PreviousWorkDetail>,

  @ElementCollection
  @CollectionTable(name = "Functional")
  @Column(name = "Achieved_Functionals")
  var achievedFunctionalLevel: List<AchievedFunctionalLevel>,
)
