package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dtos.PecaCadastroRequest
import consultoria.askyu.gestio.repository.PecaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrDefault

@Service
class PecaService(
    var pecaRepository: PecaRepository,
    var usuarioRepository: UsuarioRepository,
    var usuarioService: UsuarioService,
    var mapper: ModelMapper = ModelMapper()
) {

    fun getAllByUsuarioId(id: Int): List<Peca>{
        usuarioService.existenceValidation(id)
        var listaPeca = pecaRepository.findByUsuarioId(id)
        validarSeListaEstaVazia(listaPeca)
        return listaPeca
    }

    fun getByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Peca{
        usuarioService.existenceValidation(usuarioId)
        validarSeAPecaExiste(usuarioId, pecaId)
        val peca = pecaRepository.findByUsuarioIdAndId(usuarioId, pecaId).get()
        return peca
    }

    fun getByUsuarioIdAndNome(id: Int, nome: String): List<Peca>{
        usuarioService.existenceValidation(id)
        var listaPeca = pecaRepository.findByUsuarioIdAndNomeContainsIgnoreCase(id, nome)
        validarSeListaEstaVazia(listaPeca)
        return listaPeca
    }

    fun postByUsuarioId(id: Int, novaPeca: PecaCadastroRequest): Peca{
        usuarioService.existenceValidation(id)
        val peca = mapper.map(novaPeca, Peca::class.java)
        peca.usuario = usuarioRepository.findById(id).get()
        peca.ativo = true
        return pecaRepository.save(peca)
    }

    fun putByUsuarioId(usuarioId: Int, pecaId: Int, pecaAtualizada: PecaCadastroRequest): Peca{
        usuarioService.existenceValidation(usuarioId)
        validarSeAPecaExiste(usuarioId, pecaId)
        val peca = mapper.map(pecaAtualizada, Peca::class.java)
        peca.usuario = usuarioRepository.findById(usuarioId).getOrDefault(null)
        peca.id = pecaId
        return pecaRepository.save(peca)
    }

    fun deleteByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Peca{
        usuarioService.existenceValidation(usuarioId)
        validarSeAPecaExiste(usuarioId, pecaId)
        val peca = pecaRepository.findByUsuarioIdAndId(usuarioId, pecaId).get()
        peca.usuario!!.id = usuarioId
        peca.id = pecaId
        peca.ativo = false
        return pecaRepository.save(peca)
    }

    fun validarSeListaEstaVazia(lista: List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Peça está vazia."
            )
        }
    }

    fun validarSeAPecaExiste(usuarioId: Int, pecaId: Int){
        if(!pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)){
           throw ResponseStatusException(
               HttpStatusCode.valueOf(404), "Peça não foi encontrada."
           )
        }
    }

    fun findById(id:Int):Peca{
        val peca = pecaRepository.findById(id).get()
        return peca
    }
}