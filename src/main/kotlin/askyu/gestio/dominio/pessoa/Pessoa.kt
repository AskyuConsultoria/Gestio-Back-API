package askyu.gestio.dominio.pessoa

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

@Entity
data class Pessoa(

    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idCliente:Int?,
    @field:NotBlank var nome:String?,
    @field:NotBlank var sobrenome: String?,
    @field:NotBlank var dtNasc:LocalDate?,
    @field:NotBlank var email:String?,

    @field:ManyToOne
    var fkResponsavel: Pessoa?,

    @field:NotBlank var ativo:Boolean?
)