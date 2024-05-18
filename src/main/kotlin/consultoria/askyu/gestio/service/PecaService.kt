package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dtos.PecaCadastroRequest
import consultoria.askyu.gestio.repository.PecaRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PecaService(
    var pecaRepository: PecaRepository,
    var usuarioService: UsuarioService,
    var mapper: ModelMapper = ModelMapper()
) {

    fun getAllByUsuarioId(id: Int): List<Peca>{
        validarSeUsuarioExiste(id)
        var listaPeca = pecaRepository.findByUsuarioId(id)
        validarSeListaEstaVazia(listaPeca)
        return listaPeca
    }

    fun getByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Peca{
        validarSeUsuarioExiste(usuarioId)
        validarSeAPecaExiste(usuarioId, pecaId)
        val peca = pecaRepository.findByUsuarioIdAndId(usuarioId, pecaId).get()
        return peca
    }

    fun getByUsuarioIdAndNome(id: Int, nome: String): List<Peca>{
        validarSeUsuarioExiste(id)
        var listaPeca = pecaRepository.findByUsuarioIdAndNomeIgnoreCase(id, nome)
        validarSeListaEstaVazia(listaPeca)
        return listaPeca
    }

    fun postByUsuarioId(id: Int, novaPeca: PecaCadastroRequest): Peca{
        validarSeUsuarioExiste(id)
        novaPeca.usuario = usuarioService.repository.findById(id).get()
        val peca = mapper.map(novaPeca, Peca::class.java)
        peca.ativo = true
        return pecaRepository.save(peca)
    }

    fun putByUsuarioId(usuarioId: Int, pecaId: Int, pecaAtualizada: Peca): Peca{
        validarSeUsuarioExiste(usuarioId)
        validarSeAPecaExiste(usuarioId, pecaId)
        pecaAtualizada.usuario = usuarioService.repository.findById(usuarioId).get()
        pecaAtualizada.id = pecaId
        return pecaRepository.save(pecaAtualizada)
    }

    fun deleteByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Peca{
        validarSeUsuarioExiste(usuarioId)
        validarSeAPecaExiste(usuarioId, pecaId)
        val peca = pecaRepository.findByUsuarioIdAndId(usuarioId, pecaId).get()
        peca.ativo = false
        return pecaRepository.save(peca)
    }

    fun validarSeListaEstaVazia(lista: List<*>): List<*>{
        if(lista.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Peça está vazia."
            )
        }
        return lista
    }

    fun validarSeUsuarioExiste(id: Int){
        usuarioService.existenceValidation(id)
    }

    fun validarSeAPecaExiste(usuarioId: Int, pecaId: Int){
        if(pecaRepository.countByUsuarioIdAndId(usuarioId, pecaId) == 0){
           throw ResponseStatusException(
               HttpStatusCode.valueOf(404), "Peça não foi encontrada."
           )
        }
    }
}