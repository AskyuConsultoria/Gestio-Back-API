package askyu.gestio.dominio.ficha

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
data class Modelo(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idModelo:Int?,
    @field:NotBlank var nome:String?,
    @field:NotBlank var descricao:String?
){
    constructor() : this(null, null, null)
}
