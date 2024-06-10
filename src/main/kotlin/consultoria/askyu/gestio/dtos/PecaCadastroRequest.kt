package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank

data class PecaCadastroRequest(
    @field:NotBlank
    var nome: String? = null,

    @field:NotBlank
    var descricao: String? = null,

    @field:NotBlank
    var usuario: Int? = null
)