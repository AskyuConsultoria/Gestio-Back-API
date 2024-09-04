package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class TipoTelefoneDTO (
    val id: Int? = null,
    @field:NotBlank
    val nome: String,
    @field:NotNull
    val digitos: Int,
    @field:NotNull
    val usuario: Int
) {


}