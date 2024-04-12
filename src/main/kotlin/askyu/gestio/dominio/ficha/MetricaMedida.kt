package askyu.gestio.dominio.ficha

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
data class MetricaMedida(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idMetrica:Int?,
    @field:NotBlank var local:String?,
    @field:NotBlank var unidadeMedida:String?
){
    constructor() : this(null, null, null)
}
