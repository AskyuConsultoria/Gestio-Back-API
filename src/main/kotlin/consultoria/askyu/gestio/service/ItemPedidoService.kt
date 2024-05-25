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
        novoItemPedido.usuario!!.id = usuarioId
        novoItemPedido.cliente!!.id = clienteId
        novoItemPedido.peca!!.id = pecaId
        val itemPedido = mapper.map(novoItemPedido, ItemPedido::class.java)
        validarCadastro(novoItemPedido, itemPedido)
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
    fun validarCadastro(novoItemPedido: ItemPedidoCadastroRequest, itemPedido: ItemPedido){
        val notEqualIO =
            novoItemPedido.usuario!!.id != itemPedido.usuario!!.id
                    || novoItemPedido.cliente!!.id != itemPedido.cliente!!.id
                    || novoItemPedido.peca!!.id != itemPedido.peca!!.id

        if(notEqualIO){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(501), "O objeto de saída deve possuir os parâmetros do endpoint"
            )
        }
    }
}