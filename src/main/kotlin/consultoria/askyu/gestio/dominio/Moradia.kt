package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Moradia(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,

//    @field:ManyToOne
//    var fkPessoa: Pessoa?,

    @field:ManyToOne
    var endereco: Endereco?,

    var numero:Int?,
    var complemento: String?,
    var ativo:Boolean?
)
