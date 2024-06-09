package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.AgendamentoCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteResponse
import consultoria.askyu.gestio.repository.AgendamentoRepository
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.EtapaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AgendamentoService(
    val mapper: ModelMapper = ModelMapper(),
    val repository : AgendamentoRepository,
    val clienteRepository: ClienteRepository,
    val etapaRepository: EtapaRepository,
    val usuarioRepository: UsuarioRepository
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
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O cliente não existe.")
    }

    fun idUsuarioValidation(id:Int): Boolean{
        if(usuarioRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O cliente não existe.")
    }

    fun idValidation(id:Int): Boolean{
        if(repository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O cliente não existe.")
    }

    fun cadastrar(agendamento: AgendamentoCadastroDTO): Agendamento {
        idUsuarioValidation(agendamento.usuario)
        idClienteValidation(agendamento.cliente)
        idEtapaValidation(agendamento.etapa)

        val novoAgendamento = mapper.map(agendamento, Agendamento::class.java)

        novoAgendamento.usuario = usuarioRepository.findById(agendamento.usuario).get()
        novoAgendamento.cliente = clienteRepository.findById(agendamento.cliente).get()
        novoAgendamento.etapa = etapaRepository.findById(agendamento.etapa).get()

        return repository.save(novoAgendamento)
    }

    fun buscarUm(idCliente: Int): ClienteResponse {
        idValidation(idCliente)
        val cliente = repository.findById(idCliente).get()

        val clienteResponse = mapper.map(cliente, ClienteResponse::class.java)

        return clienteResponse
    }

    fun buscar(idUsuario: Int): List<ClienteResponse>{
        //
        val listaClientes = repository.findByUsuarioId(idUsuario)
        val listaDto = mutableListOf<ClienteResponse>()

        listValidation(listaClientes)

        listaClientes.map {
            listaDto+= mapper.map(it, ClienteResponse::class.java)
        }

        return listaDto
    }
}