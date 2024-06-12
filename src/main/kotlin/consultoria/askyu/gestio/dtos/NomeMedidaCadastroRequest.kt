package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank

data class NomeMedidaCadastroRequest(
    @NotBlank
    var nome: String? = null,
    @NotBlank
    var peca: Int? = null,
    @NotBlank
    var usuario: Int? = null
) {

}