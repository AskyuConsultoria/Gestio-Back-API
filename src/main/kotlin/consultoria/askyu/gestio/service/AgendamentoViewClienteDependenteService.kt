package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.AgendamentoViewClienteDependente
import consultoria.askyu.gestio.repository.AgendamentoViewClienteDependenteRepository
import org.springframework.stereotype.Service

@Service
class AgendamentoViewClienteDependenteService(
    val repository: AgendamentoViewClienteDependenteRepository,
    val usuarioService: UsuarioService
) {

    fun visualizar(usuarioId: Int): List<AgendamentoViewClienteDependente> {
        usuarioService.existenceValidation(usuarioId)
        val listaAgendamento = repository.findByUsuarioIdAndAtivoTrue(usuarioId)
        usuarioService.listValidation(listaAgendamento)
        return listaAgendamento
    }

    fun visualizarPorClienteNome(usuarioId: Int, responsavelId: Int, nome: String): List<AgendamentoViewClienteDependente>{
        usuarioService.existenceValidation(usuarioId)
        val listaAgendamento =
            repository.
            findByUsuarioIdAndResponsavelIdAndNomeContainsIgnoreCaseAndAtivoTrueAndClienteativoTrue(
                usuarioId, responsavelId, nome
            )
        usuarioService.listValidation(listaAgendamento)
        return listaAgendamento
    }
}