package askyu.gestio.dominio.ficha

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank

@Entity
data class FichaTecnica(
    @field:Id @field:NotBlank @ManyToOne var fkFicha: Ficha?,
    @field:NotBlank @ManyToOne var fkMedida: MetricaMedida?,
    @field:NotBlank var valorMedida:String?

)
