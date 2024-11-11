package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario

data class ColecaoTecidoCadastroRequest(
    var usuario: Usuario? = null,
    val itemPedido: ItemPedido? = null,
    var tecido: Tecido? = null,
    var cliente: Cliente? = null,
    var peca: Peca? = null
    ) {

}