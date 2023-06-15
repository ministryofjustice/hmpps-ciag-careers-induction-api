package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
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
  @CollectionTable(name = "Functional", joinColumns = [ JoinColumn(name = "offender_Id")])
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
  )
  var goals: MutableSet<Goals>,

  var schemaVersion: String,
)
