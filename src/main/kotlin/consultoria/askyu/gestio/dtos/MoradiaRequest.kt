package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import kotlin.math.max
import kotlin.math.min

data class MoradiaRequest(

    @field: NotBlank
    var complemento: String,

    @field: NotBlank
    var numero: Int,

    @field: NotBlank
    var ativo: Boolean
)
