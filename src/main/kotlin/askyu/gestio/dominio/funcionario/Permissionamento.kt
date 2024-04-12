package askyu.gestio.dominio.funcionario

import jakarta.persistence.*

@Entity
data class Permissionamento(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTipoAcesso:Int?,

    @field:ManyToOne
    var fkNivelAcesso: NivelAcesso?,

    @field:ManyToOne
    var fkTipoAcesso: TipoAcesso?,

    var permitido:Boolean?
){
    constructor() : this(null, null, null, null)
}
