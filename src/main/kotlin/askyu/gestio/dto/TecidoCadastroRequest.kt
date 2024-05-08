package askyu.gestio.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jdk.jfr.BooleanFlag

data class TecidoCadastroRequest(
    @field:NotBlank @field:Size(max = 20)
    var id: Int?,
    var nome:String?,
    @field:BooleanFlag
    var ativo: Boolean? = true
)