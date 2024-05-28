package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate
import java.util.*

@Entity
//versão simples do cliente apenas para teste
data class Cliente(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    var nome: String,

    var sobrenome: String,

    var dtNasc: LocalDate,

    @ManyToOne
    var usuario: Usuario?=null,

    var ativo: Boolean = true

)
