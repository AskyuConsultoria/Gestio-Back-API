package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import java.util.*

data class ClienteCadastroDTO(
    @field:NotBlank
    var nome:String,

    @field:NotBlank
    var sobrenome:String,

    @field:NotBlank
    var dtNasc: Date,

    @field:NotBlank
    var email:String,

    var responsavel: Int?,

    @NotBlank
    var usuario:Int,

)