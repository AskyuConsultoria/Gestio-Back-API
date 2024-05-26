package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dtos.EnderecoRequest
import consultoria.askyu.gestio.repository.EnderecoRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

class EnderecoService(
    val repository: EnderecoRepository,
    val mapper: ModelMapper = ModelMapper()
)
{

    fun getLista(): List<Endereco>{
        val lista = repository.findEndereco()
        validarLista(lista)

        val dtos = lista.map {
            mapper.map(it, Endereco::class.java)
        }
        return dtos
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun buscarPorCep(cep: String): Optional<Endereco>{
        return repository.findByCep(cep)
    }

    fun contagemCep(cep: String): Int{
        return repository.countByCep(cep)
    }

    fun salvarEndereco(endereco: Endereco){
        repository.save(endereco)
    }
}