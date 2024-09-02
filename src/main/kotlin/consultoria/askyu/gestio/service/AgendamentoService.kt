package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dtos.AgendamentoCadastroDTO
import consultoria.askyu.gestio.interfaces.Servico
import consultoria.askyu.gestio.repository.*
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class AgendamentoService(
    val mapper: ModelMapper = ModelMapper(),
    val repository : AgendamentoRepository,
    val clienteRepository: ClienteRepository,
    val etapaRepository: EtapaRepository,
    val usuarioRepository: UsuarioRepository,
    val enderecoRepository: EnderecoRepository
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
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "A etapa não existe.")
    }

    fun idAgendamentoValidation(id:Int): Boolean{
        if(repository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O Agendamento não existe.")
    }

    fun idEnderecoValidation(id:Int): Boolean{
        if(enderecoRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O endereço não existe.")
    }


    fun idUsuarioValidation(id:Int): Boolean{
        if(usuarioRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O usuário não existe.")
    }

    fun validateExistence(pedidoId: Int){
        if(!repository.existsById(pedidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Pedido não foi encontrado!"
            )
        }
    }
    fun activeValidation(idUsuario: Int, idAgendamento: Int){
        val agendamento = repository.findByUsuarioIdAndId(idUsuario, idAgendamento)
        if(!agendamento.ativo)
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Agendamento não foi encontrado")
    }

    fun cadastrar(agendamento: AgendamentoCadastroDTO): Agendamento {
        idUsuarioValidation(agendamento.usuario!!)
        idClienteValidation(agendamento.cliente!!)
        idEtapaValidation(agendamento.etapa!!)

        val novoAgendamento = mapper.map(agendamento, Agendamento::class.java)

        novoAgendamento.usuario = usuarioRepository.findById(agendamento.usuario!!).get()
        novoAgendamento.cliente = clienteRepository.findById(agendamento.cliente!!).get()
        novoAgendamento.etapa = etapaRepository.findById(agendamento.etapa!!).get()
        novoAgendamento.endereco = enderecoRepository.findById(agendamento.endereco!!).get()
        return repository.save(novoAgendamento)
    }

    fun buscar(idUsuario: Int): List<Agendamento>{
        idUsuarioValidation(idUsuario)

        val listaAgendamento = repository.findByUsuarioIdAndAtivoTrue(idUsuario)
        listValidation(listaAgendamento)

        return listaAgendamento
    }

    fun buscarUm(idUsuario: Int, idAgendamento: Int): Agendamento {
        idUsuarioValidation(idUsuario)
        validateExistence(idAgendamento)
        activeValidation(idUsuario, idAgendamento)

        val agendamento = repository.findByUsuarioIdAndId(idUsuario, idAgendamento)
        return agendamento
    }

    fun buscarPorEtapaAtivo(usuarioId: Int, etapaId: Int): List<Agendamento>{
        idUsuarioValidation(usuarioId)
        idEtapaValidation(etapaId)

        val listaAgendamento = repository.findByUsuarioIdAndAtivoTrueAndEtapaId(usuarioId, etapaId)
        listValidation(listaAgendamento)
        return listaAgendamento
    }

    fun buscarPorEtapaInativo(usuarioId: Int, etapaId: Int): List<Agendamento>{
        idUsuarioValidation(usuarioId)
        idEtapaValidation(etapaId)

        val listaAgendamento = repository.findByUsuarioIdAndAtivoFalseAndEtapaId(usuarioId, etapaId)
        listValidation(listaAgendamento)
        return listaAgendamento
    }


    fun buscarPorIntervaloDeTempo(idUsuario: Int, dataInicio: LocalDateTime, dataFim: LocalDateTime): List<Agendamento>{
        idUsuarioValidation(idUsuario)

        val listaAgendamento = repository.findByUsuarioIdAndDataInicioBetweenAndAtivoTrue(idUsuario, dataInicio, dataFim)

        listValidation(listaAgendamento)
        return listaAgendamento
    }

    fun buscarUltimos7Agendamentos(idUsuario: Int): List<Agendamento>{
        idUsuarioValidation(idUsuario)

        val listaAgendamento = repository.findTop7ByUsuarioIdAndAtivoTrueOrderByDataInicioDesc(idUsuario)
        listValidation(listaAgendamento)
        return listaAgendamento
    }


    fun atualizar(idUsuario: Int, idAgendamento: Int, agendamentoAtualizado: Agendamento): Agendamento {
        idUsuarioValidation(agendamentoAtualizado.usuario!!.id!!)
        idClienteValidation(agendamentoAtualizado.cliente!!.id!!)
        idEtapaValidation(agendamentoAtualizado.etapa!!.id!!)

        agendamentoAtualizado.id = idAgendamento
        agendamentoAtualizado.usuario =
            usuarioRepository.findById(agendamentoAtualizado.usuario!!.id!!).get()
        agendamentoAtualizado.cliente =
            clienteRepository.findById(agendamentoAtualizado.cliente!!.id!!).get()
        agendamentoAtualizado.etapa
            etapaRepository.findById(agendamentoAtualizado.etapa!!.id!!)
        agendamentoAtualizado.endereco =
            enderecoRepository.findById(agendamentoAtualizado.endereco!!.id!!).get()

        return repository.save(agendamentoAtualizado)
    }

    fun atualizarEndereco(idUsuario: Int, idAgendamento: Int, idEndereco: Int): Agendamento{
        idUsuarioValidation(idUsuario)
        idAgendamentoValidation(idAgendamento)
        idEnderecoValidation(idEndereco)
        var agendamento = repository.findByUsuarioIdAndId(idUsuario, idAgendamento)
        agendamento.endereco = enderecoRepository.findById(idEndereco).get()
        return repository.save(agendamento)
    }


    fun excluir(idUsuario: Int, idAgendamento: Int): Agendamento {
        idUsuarioValidation(idUsuario)
        validateExistence(idAgendamento)

        val agendamento = repository.findByUsuarioIdAndIdAndAtivoTrue(idUsuario, idAgendamento)

        agendamento.ativo = false
        return repository.save(agendamento)
    }
}