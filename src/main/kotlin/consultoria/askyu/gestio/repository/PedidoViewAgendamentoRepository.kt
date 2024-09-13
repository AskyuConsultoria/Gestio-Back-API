package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.PedidoViewAgendamentoPeca
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoViewAgendamentoPecaRepository: JpaRepository<PedidoViewAgendamentoPeca, Int> {

    fun findByUsuarioIdAndPecanomeContainsIgnoreCaseAndAtivoTrue(usuarioId: Int, nome: String): List<PedidoViewAgendamentoPeca>

    fun findByUsuarioIdAndTecidonomeContainsIgnoreCaseAndAtivoTrue(usuarioId: Int, nome: String): List<PedidoViewAgendamentoPeca>
}