package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.TelefoneAtualizarDTO
import consultoria.askyu.gestio.dtos.TelefoneCadastroDTO
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.TelefoneRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TelefoneService (

    val mapper: ModelMapper = ModelMapper(),
    val telefoneRepository: TelefoneRepository,
    val clienteRepository: ClienteRepository,
    val tipoTelefoneRepository: TelefoneRepository

) {

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun telefoneValidation(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int){
        if(!telefoneRepository.existsByUsuarioIdAndClienteIdAndTipoTelefoneIdAndId(usuarioId, clienteId, tipoTelefoneId, telefoneId)){

            throw ResponseStatusException(HttpStatusCode.valueOf(404), "O Telefone não existe.")
        }

    }

    fun deletarTelefone(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int): Telefone {
        telefoneValidation(usuarioId, clienteId, tipoTelefoneId, telefoneId)

        val telefone = telefoneRepository.findByUsuarioIdAndId(usuarioId, tipoTelefoneId)

        telefone.usuario!!.id
        telefone.cliente!!.id
        telefone.tipoTelefone!!.id
        telefone.ativo = false

        return telefoneRepository.save(telefone)
    }
}