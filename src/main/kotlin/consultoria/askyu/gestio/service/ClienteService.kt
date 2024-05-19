package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteCadastroRequest
import consultoria.askyu.gestio.repository.ClienteRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ClienteService(
    var clienteRepository: ClienteRepository,
    var usuarioService: UsuarioService,
    var mapper: ModelMapper = ModelMapper()
) {

    fun postByUsuarioId(usuarioId: Int, novoCliente: ClienteCadastroRequest): Cliente{
        usuarioService.existenceValidation(usuarioId)
        novoCliente.usuario!!.id = usuarioId
        var cliente = mapper.map(novoCliente, Cliente::class.java)
        return clienteRepository.save(cliente)
    }

    fun existenceValidation(usuarioId: Int, clienteId: Int){
        if(!clienteRepository.existsByUsuarioIdAndId(usuarioId, clienteId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Cliente não foi encontrado."
            )
        }
    }
}