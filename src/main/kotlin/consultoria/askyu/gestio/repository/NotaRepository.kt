package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Nota
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotaRepository: JpaRepository<Nota, Int> {

    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<Nota>
    fun findByUsuarioIdAndClienteIdAndAtivoTrue(usuarioId: Int, clienteId: Int): List<Nota>
    fun findByUsuarioIdAndTituloContainsIgnoreCaseAndAtivoTrue(usuarioId: Int, nome: String): List<Nota>
    fun findByUsuarioIdAndIdAndAtivoTrue(usuarioId: Int, notaId: Int): Nota
    fun existsByUsuarioIdAndId(usuarioId: Int, clienteId: Int): Boolean
}