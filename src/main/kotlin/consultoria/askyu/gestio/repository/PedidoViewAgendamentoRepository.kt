package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.PedidoViewAgendamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoViewAgendamentoRepository: JpaRepository<PedidoViewAgendamento, Int> {

    fun findByUsuarioIdAndPecanomeContainsIgnoreCaseAndAtivoTrue(usuarioId: Int, nome: String): List<PedidoViewAgendamento>

    fun findByUsuarioIdAndTecidonomeContainsIgnoreCaseAndAtivoTrue(usuarioId: Int, nome: String): List<PedidoViewAgendamento>
}