package consultoria.askyu.gestio

import askyu.gestio.dto.TecidoCadastroRequest
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException

@Service
class TecidoService(
    var tecidoRepository: TecidoRepository,
    val mapper: ModelMapper = ModelMapper()
){


    fun salvar(@Valid tecido: TecidoCadastroRequest){
        val dto = mapper.map(tecido, Tecido::class.java)
        tecidoRepository.save(dto)
    }

    fun listar(): List<Tecido> {
      val listaTecido = tecidoRepository.findAll()
        validarSeListaEVazia(listaTecido)
        return listaTecido
    }

    fun listarPorNome(nome: String): List<Tecido> {
        val listaTecido = tecidoRepository.findByNome(nome)
        validarSeListaEVazia(listaTecido)
        return listaTecido
    }

    fun buscarTecidoPorId(id: Int): Tecido {
        validarSeExistePorId(id)
        val tecido = tecidoRepository.findById(id).get()
        return tecido
    }

    fun desativar(@RequestParam id: Int){
       validarSeExistePorId(id)
        val tecido = tecidoRepository.findById(id).get()
        tecido.ativo = false
        tecidoRepository.save(tecido)
    }


    fun validarSeListaEVazia(listaTecido: List<*>) {
        if (listaTecido.isEmpty()) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "A lista de teciods encontra-se vazia."
            )
        }
    }

    fun validarSeExistePorId(id: Int){
        if(!tecidoRepository.existsById(id)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "O tecido acessado não existe no sistema."
            )
        }
    }

}