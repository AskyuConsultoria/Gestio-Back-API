package askyu.gestio.dominio.pessoa

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Telefone(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTelefone:Int?,
    @field:NotBlank var numero:String?,

    @field:NotBlank @field:ManyToOne
    var fkTipo: TipoTelefone?,
    @field:NotBlank @field:ManyToOne
    var fkDono: Pessoa?,


    @field:NotBlank var ativo:Boolean?
){
    constructor() : this(null, null, null,null, null)
}