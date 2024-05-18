package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Peca
import jakarta.validation.constraints.NotBlank

data class NomeMedidaCadastroRequest(
    @NotBlank
    var nome: String? = null,

    var peca: Peca? = null,
) {

}