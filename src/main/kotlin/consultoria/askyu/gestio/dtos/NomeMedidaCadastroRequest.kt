package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank

data class NomeMedidaCadastroRequest(
    @NotBlank
    var nome: String? = null,

    var peca: Peca? = null,

    var usuario: Usuario? = null
) {

}