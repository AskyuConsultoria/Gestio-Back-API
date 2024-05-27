package askyu.gestio.dto

import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank

data class TecidoCadastroRequest(
    var id: Int?,
    @field:NotBlank
    var nome:String?,
    var usuario: Usuario?
)