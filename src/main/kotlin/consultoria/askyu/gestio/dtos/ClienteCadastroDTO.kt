package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class ClienteCadastroDTO(
    @field:NotBlank
    var nome:String,

    @field:NotBlank
    var sobrenome:String,

    var dtNasc: LocalDate? = null,

    @field:NotBlank
    var email:String,

    var responsavel: Int? = null,

    @NotBlank
    var usuario:Int,

)
