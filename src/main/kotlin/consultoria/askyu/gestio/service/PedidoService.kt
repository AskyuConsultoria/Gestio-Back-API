package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.PedidoCadastroDTO
import consultoria.askyu.gestio.dtos.PedidoResponseDTO
import consultoria.askyu.gestio.interfaces.Servico
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
): Servico(repository, mapper) {
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
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "A Pedido não existe.")
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

    fun validateExistence(pedidoId: Int){
        if(!repository.existsById(pedidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Pedido não foi encontrado!"
            )
        }
    }

    fun activeValidation(idUsuario: Int, idPedido: Int){
       val pedido = repository.findByUsuarioIdAndId(idUsuario, idPedido)
        if(!pedido.ativo)
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Pedido não foi encontrado")
    }

    fun cadastrar(pedido: PedidoCadastroDTO): Pedido {
        usuarioValidation(pedido.usuario!!)
        idClienteValidation(pedido.cliente!!)
        idEtapaValidation(pedido.etapa!!)
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

    fun buscar(idUsuario: Int): List<PedidoResponseDTO>{
        val lista = repository.findByUsuarioIdAndAtivoTrue(idUsuario)
        val listaDto = mutableListOf<PedidoResponseDTO>()

        listValidation(lista)

        lista.map {
            listaDto+= mapper.map(it, PedidoResponseDTO::class.java)
        }

        return listaDto
    }

    fun buscarUm(idUsuario: Int, idPedido: Int): PedidoResponseDTO {
        usuarioValidation(idUsuario)
        validateExistence(idPedido)
        activeValidation(idUsuario, idPedido)

        val pedido = repository.findByUsuarioIdAndIdAndAtivoTrue(idUsuario, idPedido)


        val response = mapper.map(pedido, PedidoResponseDTO::class.java)

        return response
    }

    fun atualizar(idUsuario: Int, idPedido: Int, pedidoAtualizado: Pedido): Pedido {
        usuarioValidation(pedidoAtualizado.usuario!!.id!!)
        idClienteValidation(pedidoAtualizado.usuario!!.id!!)
        idEtapaValidation(pedidoAtualizado.usuario!!.id!!)
        idAgendamentoValidation(pedidoAtualizado.agendamento!!.id!!)
        idItemPedidoValidation(pedidoAtualizado.itemPedido!!.id!!)
        validateExistence(idPedido)

        pedidoAtualizado.etapa = etapaRepository.findById(pedidoAtualizado.etapa!!.id!!).get()
        pedidoAtualizado.usuario = usuarioRepository.findById(pedidoAtualizado.usuario!!.id!!).get()
        pedidoAtualizado.cliente = clienteRepository.findById(pedidoAtualizado.cliente!!.id!!).get()
        pedidoAtualizado.itemPedido = itemPedidoRepository.findById(pedidoAtualizado.itemPedido!!.id!!).get()
        pedidoAtualizado.agendamento = agendamentoRepository.findById(pedidoAtualizado.agendamento!!.id!!).get()
        return repository.save(pedidoAtualizado)
    }

    fun excluir(idUsuario: Int, idPedido: Int): Pedido {
        usuarioValidation(idUsuario)
        validateExistence(idPedido)

        val pedido = repository.findByUsuarioIdAndIdAndAtivoTrue(idUsuario, idPedido)

        pedido!!.ativo = false
        return repository.save(pedido)
    }
}