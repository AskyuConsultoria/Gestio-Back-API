package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class ClienteAtualizarDTO(

    @field:NotBlank
    var id:Int,

    var nome:String? = null,

    var sobrenome:String? = null,

    var dtNasc: LocalDate? = null,

    var email:String? = null,

    var responsavel: Int? = null,

    )
