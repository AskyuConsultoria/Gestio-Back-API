package askyu.gestio.dominio.evento

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
data class TipoTag(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTipo:Int?,
    @field:NotBlank var nome:String?,
    @field:NotBlank var descricao:String?
){
    constructor() : this(null, null, null)
}
