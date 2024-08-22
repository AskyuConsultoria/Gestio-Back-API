package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.AgendamentoViewTotalEtapa
import consultoria.askyu.gestio.repository.AgendamentoViewTotalEtapaRepository
import org.springframework.stereotype.Service

@Service
class AgendamentoViewTotalEtapaService (
    val agendamentoViewTotalEtapaRepository: AgendamentoViewTotalEtapaRepository,
    val usuarioService: UsuarioService
) {

    fun visualizar(usuarioId: Int): List<AgendamentoViewTotalEtapa>{
        usuarioService.existenceValidation(usuarioId)
        return agendamentoViewTotalEtapaRepository.findByUsuarioIdAndAtivoTrue(usuarioId)
    }
}