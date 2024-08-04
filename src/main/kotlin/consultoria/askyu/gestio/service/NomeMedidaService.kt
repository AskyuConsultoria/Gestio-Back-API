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
        val nomeMedida = mapper.map(novoNomeMedida, NomeMedida::class.java)
        validarSePecaExiste(usuarioId, pecaId)
        nomeMedida.usuario = usuarioService.findById(usuarioId)
        nomeMedida.peca = pecaService.findById(pecaId)
        nomeMedida.ativo = true
        return nomeMedidaRepository.save(nomeMedida)
    }
    fun getAllByUsuarioIdAndPecaId(usuarioId: Int, pecaId: Int): List<NomeMedida>{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        var listaNomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndAtivoIsTrue(usuarioId, pecaId)
        validarSeListaEstaVazia(listaNomeMedida)
        return listaNomeMedida
    }

    fun getAllByUsuarioIdAndPecaIdAndNomeContains(usuarioId: Int, pecaId: Int, nome: String): List<NomeMedida>{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        var listaNomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndNomeContainsIgnoreCaseAndAtivoIsTrue(usuarioId, pecaId, nome)
        validarSeListaEstaVazia(listaNomeMedida)
        return listaNomeMedida
    }

    fun getByUsuarioIdAndPecaIdAndId(usuarioId: Int, pecaId: Int, nomeMedidaId: Int): NomeMedida{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        var nomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId)
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
        val nomeMedida = mapper.map(nomeMedidaAtualizada, NomeMedida::class.java)
        nomeMedida.usuario!!.id = usuarioId
        nomeMedida.peca!!.id = pecaId
        nomeMedida.id = nomeMedidaId
        return nomeMedidaRepository.save(nomeMedida)
    }

    fun deleteByUsuarioIdAndPecaIdAndId(
        usuarioId: Int,
        pecaId: Int,
        nomeMedidaId: Int
    ): NomeMedida{
        usuarioService.existenceValidation(usuarioId)
        validarSePecaExiste(usuarioId, pecaId)
        validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        val nomeMedida = nomeMedidaRepository.getByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId)
        nomeMedida.usuario!!.id = usuarioId
        nomeMedida.peca!!.id = pecaId
        nomeMedida.id = nomeMedidaId
        nomeMedida.ativo = false
       return nomeMedidaRepository.save(nomeMedida)
    }

    fun validarSePecaExiste(usuarioId: Int, pecaId: Int){
        pecaService.validarSeAPecaExiste(usuarioId, pecaId)
    }

    fun validarSeNomeMedidaExiste(usuarioId: Int, pecaId: Int, nomeMedidaId: Int){
        if(!nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "O nome de medida não foi encontrado."
            )
        }

    }

    fun validarSeListaEstaVazia(lista: List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Peça está vazia."
            )
        }
    }
}