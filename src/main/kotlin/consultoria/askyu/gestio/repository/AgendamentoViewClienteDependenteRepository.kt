package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.AgendamentoViewClienteDependente
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AgendamentoViewClienteDependenteRepository: JpaRepository<AgendamentoViewClienteDependente, Int>, IRepositorio {
    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<AgendamentoViewClienteDependente>

    fun findByUsuarioIdAndResponsavelIdAndNomeContainsIgnoreCaseAndAtivoTrueAndClienteativoTrue(usuarioId: Int, responsavelId: Int, nome: String): List<AgendamentoViewClienteDependente>
}