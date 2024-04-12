package askyu.gestio.dominio.pessoa

import askyu.gestio.dominio.pessoa.Endereco
import askyu.gestio.dominio.pessoa.Pessoa
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Complemento(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idComplemento:Int?,

    @field:ManyToOne
    var fkPessoa: Pessoa?,

    @field:ManyToOne
    var fkEndereco: Endereco?,

    @field:NotBlank var numero:Int?,
    var complemento: String?,
    @field:NotBlank var ativo:Boolean?
){
    constructor() : this(null, null, null,null, null, null)
}
