package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.NomeMedida
import consultoria.askyu.gestio.dtos.NomeMedidaCadastroRequest
import consultoria.askyu.gestio.repository.NomeMedidaRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class NomeMedidaService(
    var nomeMedidaRepository: NomeMedidaRepository,
    var pecaService: PecaService,
    var usuarioService: UsuarioService,
    var mapper: ModelMapper = ModelMapper()
) {

    fun postByUsuarioIdAndPecaId(usuarioId: Int, pecaId: Int, novoNomeMedida: NomeMedidaCadastroRequest): NomeMedida{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        novoNomeMedida.usuario!!.id = usuarioId
        novoNomeMedida.peca!!.id = pecaId
        val nomeMedida = mapper.map(novoNomeMedida, NomeMedida::class.java)
        nomeMedida.ativo = true
        return nomeMedidaRepository.save(nomeMedida)
    }
    fun getAllByUsuarioIdAndPecaId(usuarioId: Int, pecaId: Int): List<NomeMedida>{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        var listaNomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaId(usuarioId, pecaId)
        validarSeListaEstaVazia(listaNomeMedida)
        return listaNomeMedida
    }

    fun getAllByUsuarioIdAndPecaIdAndNomeContains(usuarioId: Int, pecaId: Int, nome: String): List<NomeMedida>{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        var listaNomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndNomeContainsIgnoreCase(usuarioId, pecaId, nome)
        validarSeListaEstaVazia(listaNomeMedida)
        return listaNomeMedida
    }

    fun getByUsuarioIdAndPecaIdAndId(usuarioId: Int, pecaId: Int, nomeMedidaId: Int): NomeMedida{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        var nomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)
        return nomeMedida
    }

    fun putByUsuarioIdAndPecaIdAndId(
        usuarioId: Int,
        pecaId: Int,
        nomeMedidaId: Int,
        nomeMedidaAtualizada: NomeMedidaCadastroRequest
    ): NomeMedida{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        nomeMedidaAtualizada.usuario!!.id = usuarioId
        nomeMedidaAtualizada.peca!!.id = pecaId
        val nomeMedida = mapper.map(nomeMedidaAtualizada, NomeMedida::class.java)
        nomeMedida.id = nomeMedidaId
        return nomeMedidaRepository.save(nomeMedida)
    }

    fun deleteByUsuarioIdAndPecaIdAndId(
        usuarioId: Int,
        pecaId: Int,
        nomeMedidaId: Int
    ){
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        var nomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)
        nomeMedida.ativo = false
        nomeMedidaRepository.save(nomeMedida)
    }

    fun validarSePecaExiste(usuarioId: Int, pecaId: Int){
        pecaService.validarSeAPecaExiste(usuarioId, pecaId)
    }

    fun validarSeNomeMedidaExiste(usuarioId: Int, pecaId: Int, nomeMedidaId: Int){
        if(!nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Nenhuma medida com este nome foi encontrada."
            )
        }

    }

    fun validarSeListaEstaVazia(lista: List<*>): List<*>{
        if(lista.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Peça está vazia."
            )
        }
        return lista
    }
}