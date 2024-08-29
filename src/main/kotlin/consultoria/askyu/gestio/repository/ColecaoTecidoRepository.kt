package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ColecaoTecido
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface ColecaoTecidoRepository: JpaRepository<ColecaoTecido, Int>, IRepositorio {

    fun findByUsuarioId(usuarioId: Int): List<ColecaoTecido>
    fun existsByUsuarioIdAndId(usuarioId: Int, colecaoTecidoId: Int): Boolean
    fun findByUsuarioIdAndItemPedidoId(usuarioId: Int, itemPedidoId: Int): List<ColecaoTecido>
    fun findByUsuarioIdAndId(usuarioId: Int, colecaoTecidoId: Int): ColecaoTecido
    fun deleteByUsuarioIdAndId(usuarioId: Int, colecaoTecidoId: Int): Void
}