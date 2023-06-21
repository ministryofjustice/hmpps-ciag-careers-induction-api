package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "ciag_profile")
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
  @CollectionTable(name = "Work_Detail")
  @Column(name = "Previous_Work_Detail")
  var previousWorkDetail: MutableSet<PreviousWorkDetail>,

  @ElementCollection
  @CollectionTable(name = "Functional", joinColumns = [JoinColumn(name = "offender_Id")])
  @Column(name = "Achieved_Functionals")
  var achievedFunctionalLevel: MutableSet<AchievedFunctionalLevel>,

  @ElementCollection
  @CollectionTable(name = "Qualification", joinColumns = [JoinColumn(name = "offender_Id")])
  @Column(name = "Achieved_Qualification")
  var achievedQualification: MutableSet<AchievedQualification>,

  @ElementCollection
  @CollectionTable(name = "Training", joinColumns = [JoinColumn(name = "offender_Id")])
  @Column(name = "Achieved_Trainjng")
  var achievedTrainjng: MutableSet<AchievedTrainjng>,

  @ElementCollection
  @CollectionTable(name = "PreviousWork", joinColumns = [JoinColumn(name = "offender_Id")])
  @Column(name = "Previous_Work")
  var previousWork: MutableSet<PreviousWork>,

  @OneToMany(
    mappedBy = "profile",
    cascade = [CascadeType.ALL],
    orphanRemoval = true,
    fetch = FetchType.EAGER,
  )
  var goals: MutableSet<Goals>,

  var schemaVersion: String,

) {
  constructor(
    ciagProfileRequestDTO: CIAGProfileRequestDTO,
  ) : this(
    offenderId = ciagProfileRequestDTO.offenderId,
    bookingId = ciagProfileRequestDTO.bookingId,
    createdBy = ciagProfileRequestDTO.createdBy,
    createdDateTime = LocalDateTime.now(),
    modifiedBy = ciagProfileRequestDTO.modifiedBy,
    desireToWork = ciagProfileRequestDTO.desireToWork,
    modifiedDateTime = ciagProfileRequestDTO.modifiedDateTime,
    previousWorkDetail = ciagProfileRequestDTO.previousWorkDetail,
    achievedFunctionalLevel = ciagProfileRequestDTO.achievedFunctionalLevel,
    achievedQualification = ciagProfileRequestDTO.achievedQualification,
    achievedTrainjng = ciagProfileRequestDTO.achievedTrainjng,
    previousWork = ciagProfileRequestDTO.previousWork,
    goals = ciagProfileRequestDTO.goals,
    schemaVersion = ciagProfileRequestDTO.schemaVersion,
  )

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as CIAGProfile

    if (offenderId != other.offenderId) return false
    if (bookingId != other.bookingId) return false
    if (createdBy != other.createdBy) return false
    if (createdDateTime != other.createdDateTime) return false
    if (modifiedBy != other.modifiedBy) return false
    if (desireToWork != other.desireToWork) return false
    if (modifiedDateTime != other.modifiedDateTime) return false
    if (previousWorkDetail != other.previousWorkDetail) return false
    if (achievedFunctionalLevel != other.achievedFunctionalLevel) return false
    if (achievedQualification != other.achievedQualification) return false
    if (achievedTrainjng != other.achievedTrainjng) return false
    if (previousWork != other.previousWork) return false
    if (goals != other.goals) return false
    if (schemaVersion != other.schemaVersion) return false

    return true
  }

  override fun hashCode(): Int {
    var result = offenderId.hashCode()
    result = 31 * result + bookingId.hashCode()
    result = 31 * result + createdBy.hashCode()
    result = 31 * result + createdDateTime.hashCode()
    result = 31 * result + modifiedBy.hashCode()
    result = 31 * result + desireToWork.hashCode()
    result = 31 * result + modifiedDateTime.hashCode()
    result = 31 * result + previousWorkDetail.hashCode()
    result = 31 * result + achievedFunctionalLevel.hashCode()
    result = 31 * result + achievedQualification.hashCode()
    result = 31 * result + achievedTrainjng.hashCode()
    result = 31 * result + previousWork.hashCode()
    result = 31 * result + goals.hashCode()
    result = 31 * result + schemaVersion.hashCode()
    return result
  }
}
