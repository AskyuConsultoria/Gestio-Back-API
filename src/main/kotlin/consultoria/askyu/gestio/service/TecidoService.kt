package consultoria.askyu.gestio

import consultoria.askyu.gestio.service.UsuarioService
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
    fun salvar(usuarioId: Int, tecidoId: Int, novoTecido: Tecido): Tecido{
        usuarioService.existenceValidation(usuarioId)
        novoTecido.usuario!!.id = usuarioId
        novoTecido.id = tecidoId
        return tecidoRepository.save(novoTecido)
    }

    fun atualizar(usuarioId: Int, tecidoId: Int, tecidoAtualizado: Tecido): Tecido{
        usuarioService.existenceValidation(usuarioId)
        existenceValidation(usuarioId, tecidoId)
        tecidoAtualizado.usuario!!.id = usuarioId
        tecidoAtualizado.id = tecidoId
        return tecidoRepository.save(tecidoAtualizado)
    }

    fun listar(usuarioId: Int): List<Tecido> {
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
        usuarioService.existenceValidation(usuarioId)
        existenceValidation(usuarioId, tecidoId)
        val tecido = tecidoRepository.findByUsuarioIdAndId(usuarioId, tecidoId)
        return tecido
    }

    fun desativar(usuarioId: Int, tecidoId: Int): Tecido{
        existenceValidation(usuarioId, tecidoId)
        val tecido = tecidoRepository.findByUsuarioIdAndId(usuarioId, tecidoId)
        tecido.usuario!!.id = usuarioId
        tecido.id = tecidoId
        tecido.ativo = false
        return tecidoRepository.save(tecido)
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