package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Complemento(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,

//    @field:ManyToOne
//    var fkPessoa: Pessoa?,

    @field:ManyToOne
    var endereco_id: Endereco?,

    @field:NotBlank var numero:Int?,
    var complemento: String?,
    @field:NotBlank var ativo:Boolean?
)
