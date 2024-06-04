package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Moradia(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?= null,

    @field:ManyToOne
    var endereco: Endereco?= null,

    @field: ManyToOne
    var usuario: Usuario?= null,

    @field:ManyToOne
    var cliente:Cliente?= null,

    var complemento: String?= null,

    var numero:Int?,

    var ativo:Boolean?
)
