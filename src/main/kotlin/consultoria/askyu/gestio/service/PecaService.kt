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
        pecaRepository.save(peca)
        return peca
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
}