package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.AgendamentoViewTotalEtapa
import consultoria.askyu.gestio.interfaces.ViewServico
import consultoria.askyu.gestio.repository.AgendamentoViewTotalEtapaRepository
import org.springframework.stereotype.Service

@Service
class AgendamentoViewTotalEtapaServico (
    val agendamentoViewTotalEtapaRepository: AgendamentoViewTotalEtapaRepository,
    val usuarioService: UsuarioService
): ViewServico(agendamentoViewTotalEtapaRepository, usuarioService){

    override fun visualizar(usuarioId: Int): List<AgendamentoViewTotalEtapa>{
        usuarioService.existenceValidation(usuarioId)
        return agendamentoViewTotalEtapaRepository.findByUsuarioIdAndAtivoTrue(usuarioId)
    }
}