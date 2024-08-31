package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.AgendamentoLog
import consultoria.askyu.gestio.repository.AgendamentoLogRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class AgendamentoLogService(
    val mapper: ModelMapper = ModelMapper(),
    val repository: AgendamentoLogRepository,
    val agendamentoService: AgendamentoService,
    val usuarioService: UsuarioService
) {

    fun buscarPorIdAgendamento(usuarioId: Int, agendamentoId: Int): List<AgendamentoLog>{
        usuarioService.existenceValidation(usuarioId)
        agendamentoService.validateExistence(agendamentoId)

       val listaAgendamentoLog = repository.findByUsuarioIdAndAgendamentoId(usuarioId, agendamentoId)
        usuarioService.listValidation(listaAgendamentoLog)
       return listaAgendamentoLog
    }
}