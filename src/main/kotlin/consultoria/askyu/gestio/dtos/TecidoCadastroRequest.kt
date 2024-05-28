package askyu.gestio.dto

import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank

data class TecidoCadastroRequest(
    var id: Int? = null,
    @field:NotBlank
    var nome:String? = null,
    var usuario: Usuario? = null
)