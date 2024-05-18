package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Peca
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PecaRepository:JpaRepository<Peca, Int> {
    fun findByUsuarioId(id: Int): List<Peca>
    fun findByUsuarioIdAndNomeIgnoreCase(id: Int, nome: String): List<Peca>
    fun findByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Optional<Peca>
    fun countByUsuarioIdAndId(usuarioId: Int, pecaId: Int): Int
}