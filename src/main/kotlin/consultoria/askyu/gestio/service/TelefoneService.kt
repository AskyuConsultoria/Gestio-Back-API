package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dtos.TelefoneDTO
import consultoria.askyu.gestio.repository.TelefoneRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TelefoneService (

    val mapper: ModelMapper = ModelMapper(),
    val telefoneRepository: TelefoneRepository,
    val usuarioService: UsuarioService,
    val clienteService: ClienteService,
    val tipoTelefoneService: TipoTelefoneService

) {

    fun listar(usuarioId: Int): List<Telefone> {
        usuarioService.existenceValidation(usuarioId)
        val listaTelefone = telefoneRepository.findByUsuarioId(usuarioId)
        listValidation(listaTelefone)
        return listaTelefone
    }

    fun listarPorNumero(usuarioId: Int, numero: String): List<Telefone> {
        usuarioService.existenceValidation(usuarioId)
        val listaTelefone = telefoneRepository.findByUsuarioIdAndNumeroContainsIgnoreCase(usuarioId, numero)
        listValidation(listaTelefone)
        return listaTelefone
    }

    fun salvarTelefone(
        usuarioId: Int,
        clienteId: Int,
        tipoTelefoneId: Int,
        dadoAtualizado: TelefoneDTO
    ): Telefone {
        usuarioService.existenceValidation(usuarioId)
        clienteService.idValidation(clienteId)
        tipoTelefoneService.validarTipoTelefone(usuarioId, tipoTelefoneId)

        val telefone = mapper.map(dadoAtualizado, Telefone::class.java)

        telefone.usuario!!.id = usuarioId
        telefone.cliente!!.id = clienteId
        telefone.tipoTelefone!!.id = tipoTelefoneId
        return telefoneRepository.save(telefone)
    }

    fun atualizarTelefone(
        usuarioId: Int,
        clienteId: Int,
        tipoTelefoneId: Int,
        telefoneId: Int,
        dadoAtualizado: TelefoneDTO
    ): Telefone{
        usuarioService.existenceValidation(usuarioId)
        clienteService.idValidation(clienteId)
        tipoTelefoneService.validarTipoTelefone(usuarioId, tipoTelefoneId)
        telefoneValidation(usuarioId, clienteId, tipoTelefoneId, telefoneId)

        val telefone = mapper.map(dadoAtualizado, Telefone::class.java)
        telefone.usuario!!.id = usuarioId
        telefone.cliente!!.id = clienteId
        telefone.tipoTelefone!!.id = telefoneId
        telefone.id = telefoneId
        return telefoneRepository.save(telefone)
    }

    fun deletarTelefone(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int): Telefone {
        usuarioService.existenceValidation(usuarioId)
        clienteService.idValidation(clienteId)
        tipoTelefoneService.validarTipoTelefone(usuarioId, tipoTelefoneId)
        telefoneValidation(usuarioId, clienteId, tipoTelefoneId, telefoneId)

        val telefone = telefoneRepository.findByUsuarioIdAndId(usuarioId, tipoTelefoneId)

        telefone.usuario!!.id = usuarioId
        telefone.cliente!!.id = clienteId
        telefone.tipoTelefone!!.id = tipoTelefoneId
        telefone.ativo = false

        return telefoneRepository.save(telefone)
    }

    fun telefoneValidation(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int){
        if(!telefoneRepository.existsByUsuarioIdAndClienteIdAndTipoTelefoneIdAndId(usuarioId, clienteId, tipoTelefoneId, telefoneId)){

            throw ResponseStatusException(HttpStatusCode.valueOf(404), "O Telefone não foi encontrado.")
        }

    }

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }


}