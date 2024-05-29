package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import java.util.*

data class ClienteAtualizarDTO(

    @field:NotBlank
    var id:Int,

    var nome:String,

    var sobrenome:String,

    var dtNasc: Date,

    var email:String,

    var responsavel: Int?,

    )
