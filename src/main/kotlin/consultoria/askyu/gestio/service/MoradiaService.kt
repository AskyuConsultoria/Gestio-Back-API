package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.repository.MoradiaRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class MoradiaService (
    val repository: MoradiaRepository,
    val mapper: ModelMapper = ModelMapper()
) {
    fun getLista(): List<Moradia>{
        val lista = repository.findMoradia()
        validarLista(lista)

        val dtos = lista.map {
            mapper.map(it, Moradia::class.java)
        }
        return dtos
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun buscarPorCliente(cliente: Cliente): Optional<Moradia> {
        return repository.findByCliente(cliente)
    }

    fun buscarPorEndereco(endereco: Endereco): Optional<Moradia> {
        return repository.findByEndereco(endereco)
    }

    fun buscarPorUsuario(usuario: Usuario): Optional<Moradia> {
        return repository.findByUsuario(usuario)
    }

    fun salvar(moradia:Moradia){
        repository.save(moradia)
    }

    fun excluirPorId(id: Int){
    repository.deleteById(id)
    }


}
