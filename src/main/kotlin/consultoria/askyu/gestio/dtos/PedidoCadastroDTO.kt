package consultoria.askyu.gestio.dtos

data class PedidoCadastroDTO(
    var itemPedido: Int? = null,
    var agendamento: Int? = null,
    var usuario: Int? = null,
    var etapa: Int? = null,
    var cliente: Int? = null,
)
