package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Cliente(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var nome: String? = null,
    var sobrenome: String? = null,
    var dt_nasc: LocalDate? = null,
    var email: String? = null,
    var responsavel: Int? = null,
    @field:ManyToOne
    var usuario: Usuario? = null,
    var ativo: Boolean? = true
){
    constructor(
        paramNome: String,
        paramSobrenome: String,
        paramDtNasc: LocalDate,
        paramEmail: String,
        paramUsuario: Usuario
    ): this(nome = paramNome, sobrenome = paramSobrenome, dt_nasc = paramDtNasc, email = paramEmail, usuario = paramUsuario)
}
