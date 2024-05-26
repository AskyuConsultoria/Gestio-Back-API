package consultoria.askyu.gestio

import askyu.gestio.dto.TecidoCadastroRequest
import consultoria.askyu.gestio.service.UsuarioService
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TecidoService(
    var usuarioService: UsuarioService,
    var tecidoRepository: TecidoRepository,
    val mapper: ModelMapper = ModelMapper()
){


    fun salvar(usuarioId: Int, @Valid novoTecido: TecidoCadastroRequest){
        usuarioService.existenceValidation(usuarioId)
        val tecido  = mapper.map(novoTecido, Tecido::class.java)
        tecido.usuario!!.id = usuarioId
        tecidoRepository.save(tecido)
    }

    fun listar(usuarioId: Int, tecidoId: Int): List<Tecido> {
      val listaTecido = tecidoRepository.findByUsuarioId(usuarioId)
        validarSeListaEVazia(listaTecido)
        return listaTecido
    }

    fun listarPorNome(usuarioId:Int,  nome: String): List<Tecido> {
        val listaTecido = tecidoRepository.findByUsuarioIdAndNomeContainsIgnoreCase(usuarioId,nome)
        validarSeListaEVazia(listaTecido)
        return listaTecido
    }

    fun buscarTecidoPorId(usuarioId: Int, tecidoId: Int): Tecido {
        existenceValidation(usuarioId, tecidoId)
        val tecido = tecidoRepository.findByUsuarioIdAndId(usuarioId, tecidoId)
        return tecido
    }

    fun desativar(usuarioId: Int, tecidoId: Int){
        existenceValidation(usuarioId, tecidoId)
        val tecido = tecidoRepository.findByUsuarioIdAndId(usuarioId, tecidoId)
        tecido.usuario!!.id = usuarioId
        tecido.id = tecidoId
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

    fun existenceValidation(usuarioId: Int, tecidoId: Int){
        if(!tecidoRepository.existsByUsuarioIdAndId(usuarioId, tecidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "O tecido acessado não existe no sistema."
            )
        }
    }

}