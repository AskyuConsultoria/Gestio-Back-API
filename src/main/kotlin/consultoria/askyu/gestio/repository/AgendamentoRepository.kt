package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Agendamento
import org.springframework.data.jpa.repository.JpaRepository

interface AgendamentoRepository: JpaRepository<Agendamento, Int> {
    fun findByUsuarioId(usuarioId: Int): List<Agendamento>
}