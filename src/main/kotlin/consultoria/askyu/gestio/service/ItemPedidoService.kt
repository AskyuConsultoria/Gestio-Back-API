package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dtos.ItemPedidoCadastroRequest
import consultoria.askyu.gestio.repository.ItemPedidoRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

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

    fun getByUsuarioId(usuarioId: Int): List<ItemPedido>{
        usuarioService.existenceValidation(usuarioId)
        val listaItemPedido = itemPedidoRepository.findByUsuarioId(usuarioId)
        validarSeListaEstaVazia(listaItemPedido)
        return listaItemPedido
    }

    fun deleteByUsuarioIdAndId(usuarioId: Int, itemPedidoId: Int){
        validateExistence(usuarioId, itemPedidoId)
        var itemPedido = itemPedidoRepository.findByUsuarioIdAndId(
            usuarioId, itemPedidoId
        )
        itemPedido.usuario!!.id = usuarioId
        itemPedido.id = itemPedidoId
        itemPedido.ativo = false
        itemPedidoRepository.save(itemPedido)
    }
    fun validarSeListaEstaVazia(lista: List<*>): List<*>{
        if(lista.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Fichas está vazia."
            )
        }
        return lista
    }

    fun validateExistence(usuarioId: Int, itemPedidoId: Int){
        if(!itemPedidoRepository.existsByUsuarioIdAndId(usuarioId, itemPedidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Ficha não foi encontrada!"
            )
        }
    }
}