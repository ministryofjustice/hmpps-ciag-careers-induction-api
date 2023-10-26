package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonTraining
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.PrisonWork
import java.time.LocalDateTime
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "PRISON_WORK_EDUCATION")
data class PrisonWorkAndEducation(

  @LastModifiedBy
  @Schema(description = "This is the person who modifies the Induction.Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedBy", required = true)
  var modifiedBy: String?,

  @LastModifiedDate
  @Schema(description = "This is the modified date and time of Induction record .Even though it is passed from front end it wil be automatically set to the right value at the time of record modification ", name = "modifiedDateTime", required = true)
  var modifiedDateTime: LocalDateTime?,

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  val id: Long?,
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PRISON_WORK", joinColumns = [JoinColumn(name = "PRISON_WORK_EDUCATION_ID")])
  @Column(name = "WORK")
  @Size(min = 1)
  @Schema(description = "This is the prison work list of the inmate.", name = "inPrisonWork", required = false)
  var inPrisonWork: MutableSet<PrisonWork>?,
  @Column(name = "OTHER_PRISON_WORK")
  @Schema(description = "This is the prison work which is peculiar to this inmate  .This field is mandatory when  \"inPrisonWork\" has a Value set to \"OTHER\" ", name = "inPrisonWorkOther", required = false)
  var inPrisonWorkOther: String?,

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PRISON_EDUCATION", joinColumns = [JoinColumn(name = "PRISON_WORK_EDUCATION_ID")])
  @Column(name = "EDUCATION")
  @Size(min = 1)
  @Schema(description = "This is the prison education list of the inmate.", name = "inPrisonEducation", required = false)
  var inPrisonEducation: MutableSet<PrisonTraining>?,
  @Column(name = "OTHER_PRISON_EDUCATION")
  @Schema(description = "This is the prison education which is peculiar to this inmate  .This field is mandatory when  \"inPrisonEducation\" has a Value set to \"OTHER\" ", name = "inPrisonEducationOther", required = false)
  var inPrisonEducationOther: String?,

) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PrisonWorkAndEducation

    if (id != other.id) return false
    if (inPrisonWork != other.inPrisonWork) return false
    if (inPrisonWorkOther != other.inPrisonWorkOther) return false
    if (inPrisonEducation != other.inPrisonEducation) return false
    if (inPrisonEducationOther != other.inPrisonEducationOther) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + (inPrisonWork?.hashCode() ?: 0)
    result = 31 * result + (inPrisonWorkOther?.hashCode() ?: 0)
    result = 31 * result + (inPrisonEducation?.hashCode() ?: 0)
    result = 31 * result + (inPrisonEducationOther?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "PrisonWorkAndEducation(modifiedBy='$modifiedBy', modifiedDateTime=$modifiedDateTime, id=$id, inPrisonWork=$inPrisonWork, inPrisonWorkOther=$inPrisonWorkOther, inPrisonEducation=$inPrisonEducation, inPrisonEducationOther=$inPrisonEducationOther)"
  }

  @PrePersist
  fun prePersist() {
    this.modifiedBy = CapturedSpringConfigValues.principal.name
    this.modifiedDateTime = LocalDateTime.now()
  }

  @PreUpdate
  fun preUpdate() {
    this.modifiedBy = CapturedSpringConfigValues.principal.name
    this.modifiedDateTime = LocalDateTime.now()
  }
}
