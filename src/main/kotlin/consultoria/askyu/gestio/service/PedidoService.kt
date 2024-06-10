package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.PedidoCadastroDTO
import consultoria.askyu.gestio.dtos.PedidoResponseDTO
import consultoria.askyu.gestio.repository.*
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PedidoService(
    val mapper: ModelMapper = ModelMapper(),
    val repository : PedidoRepository,
    val clienteRepository : ClienteRepository,
    val usuarioRepository: UsuarioRepository,
    val etapaRepository: EtapaRepository,
    val itemPedidoRepository : ItemPedidoRepository,
    val agendamentoRepository : AgendamentoRepository
) {
    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun idClienteValidation(id:Int): Boolean{
        if(clienteRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O cliente não existe.")
    }

    fun idEtapaValidation(id:Int): Boolean{
        if(etapaRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "A etapa não existe.")
    }

    fun usuarioValidation(id:Int): Boolean{
        if(usuarioRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O usuario não existe.")
    }
    fun idAgendamentoValidation(id:Int): Boolean{
        if(agendamentoRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O Agendamento não existe.")
    }
    fun idItemPedidoValidation(id:Int): Boolean{
        if(itemPedidoRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O Item Pedido não existe.")
    }

    fun idValidation(id:Int): Boolean{
        if(repository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O pedido não existe.")
    }

    fun cadastrar(pedido: PedidoCadastroDTO): Pedido {
        usuarioValidation(pedido.usuario!!)
        idClienteValidation(pedido.usuario!!)
        idEtapaValidation(pedido.usuario!!)
        idAgendamentoValidation(pedido.agendamento!!)
        idItemPedidoValidation(pedido.itemPedido!!)

        val novoPedido = mapper.map(pedido, Pedido::class.java)

        novoPedido.etapa = etapaRepository.findById(pedido.etapa!!).get()
        novoPedido.usuario = usuarioRepository.findById(pedido.usuario!!).get()
        novoPedido.cliente = clienteRepository.findById(pedido.cliente!!).get()
        novoPedido.itemPedido = itemPedidoRepository.findById(pedido.itemPedido!!).get()
        novoPedido.agendamento = agendamentoRepository.findById(pedido.agendamento!!).get()

        return repository.save(novoPedido)
    }

    fun buscarUm(idCliente: Int): PedidoResponseDTO {
        idValidation(idCliente)
        val cliente = repository.findById(idCliente).get()

        val response = mapper.map(cliente, PedidoResponseDTO::class.java)

        return response
    }

    fun buscar(idUsuario: Int): List<PedidoResponseDTO>{
        //
        val lista = repository.findByUsuarioId(idUsuario)
        val listaDto = mutableListOf<PedidoResponseDTO>()

        listValidation(lista)

        lista.map {
            listaDto+= mapper.map(it, PedidoResponseDTO::class.java)
        }

        return listaDto
    }

}