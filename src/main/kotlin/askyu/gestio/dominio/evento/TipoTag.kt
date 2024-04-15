package askyu.gestio.dominio.evento

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Null

@Entity
data class TipoTag(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTipoTag :Int?,
    @field:NotBlank var nome:String?,
    @field:NotBlank var descricao:String?,
    var ativo: Boolean? = true
){
    constructor() : this(null, null, null, null)
}
