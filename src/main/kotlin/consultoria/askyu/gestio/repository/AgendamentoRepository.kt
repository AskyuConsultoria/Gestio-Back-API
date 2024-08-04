package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Agendamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
interface AgendamentoRepository: JpaRepository<Agendamento, Int> {
    fun findByUsuarioId(usuarioId: Int): List<Agendamento>

    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<Agendamento>

    fun findByUsuarioIdAndId(usuarioId: Int, agendamentoId: Int): Agendamento

    fun findByUsuarioIdAndIdAndAtivoTrue(usuarioId: Int, agendamentoId: Int): Agendamento

    fun findByUsuarioIdAndDataInicioBetweenAndAtivoTrue(usuarioId: Int, dataInicio: LocalDateTime, dataFim: LocalDateTime): List<Agendamento>

    fun findTop7ByUsuarioIdAndAtivoTrueOrderByDataInicioDesc(usuarioId: Int): List<Agendamento>

    fun findByUsuarioIdAndAtivoTrueAndEtapaId(usuarioId: Int, etapaId: Int): List<Agendamento>

    fun findByUsuarioIdAndEtapaIdAndAtivoFalse(usuarioId: Int, etapaId: Int): List<Agendamento>


}