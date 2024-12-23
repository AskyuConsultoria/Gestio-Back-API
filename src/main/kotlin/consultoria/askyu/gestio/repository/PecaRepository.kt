package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PecaRepository:JpaRepository<Peca, Int>, IRepositorio {
    fun findByUsuarioIdAndAtivoTrue(id: Int): List<Peca>
    fun findByUsuarioIdAndNomeContainsIgnoreCase(id: Int, nome: String): List<Peca>
    fun findByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Optional<Peca>
    fun existsByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Boolean
}