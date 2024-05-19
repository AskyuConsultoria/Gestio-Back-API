package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dtos.ItemPedidoCadastroRequest
import consultoria.askyu.gestio.repository.ItemPedidoRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class ItemPedidoService(
    var itemPedidoRepository: ItemPedidoRepository,
    var usuarioService: UsuarioService,
    var clienteService: ClienteService,
    var pecaService: PecaService,
    var mapper: ModelMapper = ModelMapper()
) {

    fun postByUsuarioIdAndClienteIdAndPecaId(
        usuarioId: Int,
        clienteId: Int,
        pecaId: Int,
        novoItemPedido: ItemPedidoCadastroRequest
    ): ItemPedido{
        usuarioService.existenceValidation(usuarioId)
        clienteService.existenceValidation(usuarioId, clienteId)
        pecaService.validarSeAPecaExiste(usuarioId, pecaId)
        novoItemPedido.usuario!!.id = usuarioId
        novoItemPedido.cliente!!.id = clienteId
        novoItemPedido.peca!!.id = pecaId
        val itemPedido = mapper.map(novoItemPedido, ItemPedido::class.java)
        return itemPedidoRepository.save(itemPedido)
    }
}