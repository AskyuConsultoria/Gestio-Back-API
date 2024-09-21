package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
interface AgendamentoRepository: JpaRepository<Agendamento, Int>, IRepositorio {
    fun findByUsuarioId(usuarioId: Int): List<Agendamento>

    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<Agendamento>

    fun findByUsuarioIdAndId(usuarioId: Int, agendamentoId: Int): Agendamento

    fun findByUsuarioIdAndIdAndAtivoTrue(usuarioId: Int, agendamentoId: Int): Agendamento

    fun findByUsuarioIdAndDataInicioBetweenAndAtivoTrue(usuarioId: Int, dataInicio: LocalDateTime, dataFim: LocalDateTime): List<Agendamento>

    fun findTop7ByUsuarioIdAndAtivoTrueOrderByDataInicioDesc(usuarioId: Int): List<Agendamento>

    fun findByUsuarioIdAndAtivoTrueAndEtapaId(usuarioId: Int, etapaId: Int): List<Agendamento>

    fun findByUsuarioIdAndAtivoFalseAndEtapaId(usuarioId: Int, etapaId: Int): List<Agendamento>

    fun findByUsuarioIdAndClienteNomeContainsIgnoreCaseAndAtivoIs(usuarioId: Int, nome: String, ativo: Boolean): List<Agendamento>

    fun findByUsuarioIdAndAndClienteEmailContainsIgnoreCaseAndAtivoTrue(usuarioId: Int, email: String): List<Agendamento>


}