package askyu.gestio.dominio.ficha

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Tecido(
    @field:Id
    var id:Int? = null,
    var nome:String? = null,
    var ativo: Boolean? = true
){
    constructor() : this(null, null, true)
}