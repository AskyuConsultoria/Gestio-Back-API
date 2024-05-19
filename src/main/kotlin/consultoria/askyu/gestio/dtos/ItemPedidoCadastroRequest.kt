package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank

data class ItemPedidoCadastroRequest(
    var observacao: String? = null,
    @field:NotBlank
    var cliente: Cliente? = null,
    @field:NotBlank
    var usuario: Usuario? = null,
    @field:NotBlank
    var peca: Peca? = null,
) {
}