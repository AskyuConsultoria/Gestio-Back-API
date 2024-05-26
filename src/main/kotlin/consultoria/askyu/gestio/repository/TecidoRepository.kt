package consultoria.askyu.gestio

import org.springframework.data.jpa.repository.JpaRepository

interface TecidoRepository: JpaRepository<Tecido, Int> {

    fun existsByUsuarioIdAndId(usuarioId: Int, tecidoId: Int): Boolean
    fun findByUsuarioId(usuarioId: Int): List<Tecido>

    fun findByUsuarioIdAndId(usuarioId: Int, tecidoId: Int): Tecido

    fun findByUsuarioIdAndNomeContainsIgnoreCase(usuarioId: Int, nome: String): List<Tecido>

}