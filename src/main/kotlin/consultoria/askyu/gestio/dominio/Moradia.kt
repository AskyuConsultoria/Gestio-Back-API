package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Moradia(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,

    @field:ManyToOne
    var endereco: Endereco?,

    @field: ManyToOne
    var usuario: Usuario?,

    @field:ManyToOne
    var cliente:Cliente,

    var complemento: String,

    var numero:Int?,

    var ativo:Boolean?
)
