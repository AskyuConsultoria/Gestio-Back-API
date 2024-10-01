package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewServico
import consultoria.askyu.gestio.repository.TecidoViewRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class TecidoViewService(
    val tecidoViewRepository: TecidoViewRepository,
    val usuarioService: UsuarioService
): ViewServico(tecidoViewRepository, usuarioService) {

    fun visualizar(usuarioId: Int, dataInicio: LocalDateTime): List<TecidoGraficoView>{
        usuarioService.existenceValidation(usuarioId)
        return tecidoViewRepository.findByUsuarioIdAndDataInicioGreaterThan(usuarioId, dataInicio)
    }
}