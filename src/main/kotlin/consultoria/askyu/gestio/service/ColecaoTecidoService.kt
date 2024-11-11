package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.TecidoService
import consultoria.askyu.gestio.dominio.ColecaoTecido
import consultoria.askyu.gestio.dtos.ColecaoTecidoCadastroRequest
import consultoria.askyu.gestio.interfaces.Servico
import consultoria.askyu.gestio.repository.ColecaoTecidoRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class ColecaoTecidoService(
    var usuarioService: UsuarioService,
    var clienteService: ClienteService,
    var pecaService: PecaService,
    var itemPedidoService: ItemPedidoService,
    var tecidoService: TecidoService,
    var colecaoTecidoRepository: ColecaoTecidoRepository,
    val mapper: ModelMapper = ModelMapper()

): Servico(colecaoTecidoRepository, mapper) {
    fun cadastrar(
        usuarioId: Int,
        itemPedidoId: Int,
        tecidoId: Int,
        novoColecaoTecido: ColecaoTecidoCadastroRequest
    ): ColecaoTecido {
        usuarioService.existenceValidation(usuarioId)
        itemPedidoService.validateExistence(usuarioId, itemPedidoId)
        tecidoService.existenceValidation(usuarioId, tecidoId)
        val itemPedido = itemPedidoService.buscarUm(usuarioId, itemPedidoId)
        clienteService.validateExistence(usuarioId, itemPedido.cliente!!.id!!)
        pecaService.validarSeAPecaExiste(usuarioId, itemPedido.peca!!.id!!)

       novoColecaoTecido.peca = pecaService.getByUsuarioIdAndId(usuarioId, itemPedido.peca!!.id!!)
       novoColecaoTecido.cliente = clienteService.buscarUmCliente(itemPedido.cliente!!.id!!)

        val colecaoTecido = mapper.map(novoColecaoTecido, ColecaoTecido::class.java)
        colecaoTecido.usuario!!.id = usuarioId
        colecaoTecido.itemPedido!!.id = itemPedidoId
        colecaoTecido.tecido!!.id = tecidoId

        return colecaoTecidoRepository.save(colecaoTecido)
    }

    fun buscarPorFicha(
        usuarioId: Int,
        itemPedidoId: Int
    ): List<ColecaoTecido>{
        val listaColecaoTecido =
            colecaoTecidoRepository.findByUsuarioIdAndItemPedidoId(usuarioId, itemPedidoId)
        validarSeListaEstaVazia(listaColecaoTecido)
        return listaColecaoTecido
    }

    fun excluir(
        usuarioId: Int,
        colecaoTecidoId: Int
    ){
        existenceValidation(usuarioId, colecaoTecidoId)
        colecaoTecidoRepository.deleteById(colecaoTecidoId)
    }


    fun validarSeListaEstaVazia(listaColecaoTecido: List<*>){
        if(listaColecaoTecido.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de coleção tecidos está vazia."
            )
        }
    }

    fun existenceValidation(usuarioId: Int, colecaoTecidoId: Int){
        if(!colecaoTecidoRepository.existsByUsuarioIdAndId(usuarioId, colecaoTecidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Coleção de Tecido não foi encontrada."
            )
        }
    }
}