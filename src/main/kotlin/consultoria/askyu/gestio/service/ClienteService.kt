package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.repository.ClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteService (val repository: ClienteRepository){

    fun salvar(cliente: Cliente){
        repository.save(cliente)
    }
}