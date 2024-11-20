package consultoria.askyu.gestio

import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface TecidoRepository: JpaRepository<Tecido, Int>, IRepositorio {

    fun existsByUsuarioIdAndId(usuarioId: Int, tecidoId: Int): Boolean

    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<Tecido>

    fun findByUsuarioIdAndId(usuarioId: Int, tecidoId: Int): Tecido

    fun findByUsuarioIdAndNomeContainsIgnoreCase(usuarioId: Int, nome: String): List<Tecido>

    fun findByUsuarioIdAndAtivo(usuarioId: Int, ativo:Boolean): List<Tecido>

}