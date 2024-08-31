package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.AgendamentoLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AgendamentoLogRepository: JpaRepository<AgendamentoLog, Int > {

    fun findByUsuarioIdAndAgendamentoId(usuarioId: Int, agendamentoId: Int): List<AgendamentoLog>
}