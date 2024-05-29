package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.MoradiaResponse
import consultoria.askyu.gestio.repository.MoradiaRepository
import org.modelmapper.ModelMapper
import org.modelmapper.TypeToken
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
    fun getLista(): List<MoradiaResponse>{
        val lista = repository.findMoradia()
        validarLista(lista)

        val dtos = lista.map {
            mapper.map(it, MoradiaResponse::class.java)
        }
        return dtos
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun buscarPorCliente(cliente: Cliente): List<MoradiaResponse> {
        val lista = repository.findAll()
        validarLista(lista)

        //pega a lista toda e converte em uma lista de produto simples response
        val listaDtos: List<MoradiaResponse> = mapper.map(
            lista,
            object: TypeToken<List<MoradiaResponse>>() {}.type
        )
        return listaDtos
    }

    fun buscarPorEndereco(endereco: Endereco): List<MoradiaResponse>  {
        val lista = repository.findAll()
        validarLista(lista)

        //pega a lista toda e converte em uma lista de produto simples response
        val listaDtos: List<MoradiaResponse> = mapper.map(
            lista,
            object: TypeToken<List<MoradiaResponse>>() {}.type
        )
        return listaDtos
    }

    fun buscarPorUsuario(usuario: Usuario): List<MoradiaResponse>  {
        val lista = repository.findAll()
        validarLista(lista)

        //pega a lista toda e converte em uma lista de produto simples response
        val listaDtos: List<MoradiaResponse> = mapper.map(
            lista,
            object: TypeToken<List<MoradiaResponse>>() {}.type
        )
        return listaDtos
    }

    fun salvar(moradia:Moradia){
        if (!repository.existsById(moradia.id!!)) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404))
        }

       repository.save(moradia)
    }

    fun excluirPorId(id: Int){
        val lista = repository.findById(id)
        if(lista.isEmpty){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204))
        }

        repository.deleteById(id)
    }


}
