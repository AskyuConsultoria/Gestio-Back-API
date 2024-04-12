package askyu.gestio.dominio.funcionario

import askyu.gestio.dominio.pessoa.Pessoa
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Funcionario(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idFuncionario: Int?,
    @field:ManyToOne
    var fkPessoa: Pessoa?,
    @field:NotBlank @field:ManyToOne var fkNivelAcesso: NivelAcesso?,
    @field:NotBlank var usuario: String?,
    @field:NotBlank var senha: String?,
) {
    constructor() : this(null, null, null,null, null)
}