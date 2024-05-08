package askyu.gestio.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jdk.jfr.BooleanFlag

data class TecidoCadastroRequest(
    var id: Int?,
    @field:NotBlank
    var nome:String?,
    @field:BooleanFlag
    var ativo: Boolean? = true
)