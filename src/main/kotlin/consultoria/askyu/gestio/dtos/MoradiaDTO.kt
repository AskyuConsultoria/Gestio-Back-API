package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MoradiaDTO(
    @field:Size(min= 1, max= 5)
    var numero:Int?,

    @field:Size(min= 1, max= 6)
    var complemento: String,

    @field:NotBlank
    var ativo:Boolean?
)
