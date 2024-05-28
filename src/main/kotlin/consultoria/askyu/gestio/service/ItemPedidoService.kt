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

    fun cadastrar(
        usuarioId: Int,
        clienteId: Int,
        pecaId: Int,
        novoItemPedido: ItemPedidoCadastroRequest
    ): ItemPedido{
        usuarioService.existenceValidation(usuarioId)
        clienteService.validateExistence(usuarioId, clienteId)
        pecaService.validarSeAPecaExiste(usuarioId, pecaId)
        val itemPedido = mapper.map(novoItemPedido, ItemPedido::class.java)
        itemPedido.usuario!!.id = usuarioId
        itemPedido.cliente!!.id = clienteId
        itemPedido.peca!!.id = pecaId
        itemPedido.ativo = true
        return itemPedidoRepository.save(itemPedido)
    }

    fun getByUsuarioId(usuarioId: Int): List<ItemPedido>{
        usuarioService.existenceValidation(usuarioId)
        val listaItemPedido = itemPedidoRepository.findByUsuarioId(usuarioId)
        validarSeListaEstaVazia(listaItemPedido)
        return listaItemPedido
    }

    fun getByUsuarioIdAndClientId(usuarioId: Int, clienteId: Int): List<ItemPedido>{
        usuarioService.existenceValidation(usuarioId)
        clienteService.validateExistence(usuarioId, clienteId)
        val listaItemPedido = itemPedidoRepository.findByUsuarioIdAndClienteId(usuarioId, clienteId)
        validarSeListaEstaVazia(listaItemPedido)
        return listaItemPedido
    }

    fun deleteByUsuarioIdAndId(usuarioId: Int, itemPedidoId: Int): ItemPedido{
        validateExistence(usuarioId, itemPedidoId)
        var itemPedido = itemPedidoRepository.findByUsuarioIdAndId(
            usuarioId, itemPedidoId
        )
        itemPedido.usuario!!.id = usuarioId
        itemPedido.id = itemPedidoId
        itemPedido.ativo = false
        return itemPedidoRepository.save(itemPedido)
    }
    fun validarSeListaEstaVazia(lista: List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Fichas está vazia."
            )
        }
    }

    fun validateExistence(usuarioId: Int, itemPedidoId: Int){
        if(!itemPedidoRepository.existsByUsuarioIdAndId(usuarioId, itemPedidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Ficha não foi encontrada!"
            )
        }
    }


}