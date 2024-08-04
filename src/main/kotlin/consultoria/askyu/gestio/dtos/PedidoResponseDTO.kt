package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.*

data class PedidoResponseDTO(
    var id:Int? = null,
    var itemPedido: ItemPedido? = null,
    var agendamento: Agendamento? = null,
    var usuario: Usuario? = null,
    var etapa: Etapa? = null,
    var cliente: Cliente? = null,
)
