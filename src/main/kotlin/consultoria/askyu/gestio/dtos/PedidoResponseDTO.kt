package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.*

data class PedidoResponseDTO(
    var id:Int,
    var itemPedido: ItemPedido,
    var agendamento: Agendamento,
    var usuario: Usuario,
    var etapa: Etapa,
    var cliente: Cliente,
)
