package askyu.gestio.dominio.evento

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jdk.jfr.BooleanFlag

@Entity
data class Tag(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTag:Int?,
    @field:NotBlank var nome:String?,
    var cor:String?,
    @field: ManyToOne var tipoTag: TipoTag?,
    var ativo: Boolean? = true
){
    constructor() : this(null, null, null, null)
}