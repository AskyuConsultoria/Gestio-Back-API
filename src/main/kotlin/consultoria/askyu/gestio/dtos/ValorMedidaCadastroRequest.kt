package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.*
import jakarta.validation.constraints.NotBlank

data class ValorMedidaCadastroRequest(

    var valor: Float? = null,

    @field:NotBlank
    var nomeMedida: NomeMedida? = null,

    @field:NotBlank
    var itemPedido: ItemPedido? = null,

    @field:NotBlank
    var cliente: Cliente? = null,

    @field:NotBlank
    var usuario: Usuario? = null,

    @field:NotBlank
    var peca: Peca? = null
) {

}