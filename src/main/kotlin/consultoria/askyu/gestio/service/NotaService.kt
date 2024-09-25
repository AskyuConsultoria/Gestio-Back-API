package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Nota
import consultoria.askyu.gestio.repository.NotaRepository
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class NotaService(
    var repository: NotaRepository,
    var usuarioService: UsuarioService,
    var clienteService: ClienteService
) {

    fun validarSeANotaExiste(usuarioId: Int, notaId: Int){
        if(!repository.existsByUsuarioIdAndId(usuarioId, notaId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Nota não foi encontrada."
            )
        }
    }


    fun cadastrar(usuarioId: Int, novaNota: Nota): Nota {
        usuarioService.existenceValidation(usuarioId)
        novaNota.usuario!!.id = usuarioId
        return repository.save(novaNota)
    }

    fun buscar(usuarioId: Int): List<Nota>{
        usuarioService.existenceValidation(usuarioId)
        val listaNota = repository.findByUsuarioIdAndAtivoTrue(usuarioId)
        return listaNota
    }

    fun buscarPorCliente(usuarioId: Int, clienteId: Int): List<Nota>{
        usuarioService.existenceValidation(usuarioId)
        clienteService.validateExistence(usuarioId, clienteId)
        val listaNota = repository.findByUsuarioIdAndClienteIdAndAtivoTrue(usuarioId, clienteId)
        usuarioService.listValidation(listaNota)
        return listaNota
    }

    fun buscarPorTitulo(usuarioId: Int, titulo: String): List<Nota>{
        usuarioService.existenceValidation(usuarioId)
        val listaNota = repository.findByUsuarioIdAndTituloContainsIgnoreCaseAndAtivoTrue(usuarioId, titulo)
        usuarioService.listValidation(listaNota)
        return listaNota
    }

    fun buscarPorId(usuarioId: Int, notaId: Int): Nota{
        usuarioService.existenceValidation(usuarioId)
        val nota = repository.findByUsuarioIdAndIdAndAtivoTrue(usuarioId, notaId)
        return nota
    }

    fun atualizar(usuarioId: Int, notaId: Int, novaAtualizada: Nota): Nota{
        usuarioService.existenceValidation(usuarioId)
        validarSeANotaExiste(usuarioId, notaId)

        novaAtualizada.usuario!!.id = usuarioId
        novaAtualizada.id = notaId
        return repository.save(novaAtualizada)
    }

    fun desativar(usuarioId: Int, notaId: Int): Nota{
        usuarioService.existenceValidation(usuarioId)
        validarSeANotaExiste(usuarioId, notaId)
        val nota = repository.findByUsuarioIdAndIdAndAtivoTrue(usuarioId, notaId)
        nota.id = notaId
        nota.ativo = false
        return repository.save(nota)
    }

}