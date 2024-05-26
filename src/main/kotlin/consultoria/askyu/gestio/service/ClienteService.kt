package consultoria.askyu.gestio.service


import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.repository.ClienteRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

class ClienteService(
    val repository: ClienteRepository,
    val mapper: ModelMapper= ModelMapper()
) {

    fun getLista(): List<Cliente>{
        val lista = repository.findCliente()
        validarLista(lista)

        val dtos = lista.map {
            mapper.map(it, Cliente::class.java)
        }
        return dtos
    }

    fun listarNomeCliente(nomeCliente: String): List<Cliente>{
        val lista= repository.findByNome(nomeCliente)
        validarLista(lista)

        val dtos= lista.map {
            mapper.map(it, Cliente:: class.java)
        }

        return dtos
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun salvar(cliente: Cliente){
        repository.save(cliente)
    }

    fun validarIdCliente(id:Int) {
        if (!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(404))
        }
    }

    fun buscarPorId(id:Int): Optional<Cliente>{
        val lista= repository.findById(id)

        val dtos= lista.map{
            mapper.map(it, Cliente::class.java)
        }

        return dtos
    }

    fun desativar(@RequestParam id: Int){
        validarIdCliente(id)
        val cliente = repository.findById(id).get()
        cliente.ativo = false
        repository.save(cliente)
    }

}