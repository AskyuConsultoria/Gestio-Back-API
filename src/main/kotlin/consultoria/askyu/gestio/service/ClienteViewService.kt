package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.ClienteView
import consultoria.askyu.gestio.repository.ClienteViewRepository
import org.springframework.stereotype.Service

@Service
class ClienteViewService(
    val usuarioService: UsuarioService,
    val clienteService: ClienteService,
    val clienteViewRepository: ClienteViewRepository
) {

    fun visualizar(id: Int): ClienteView{
        clienteService.idValidation(id)

        var clienteDado = clienteViewRepository.findById(id).get()
        return clienteDado
    }
}