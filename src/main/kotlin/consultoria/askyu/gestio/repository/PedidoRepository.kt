package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Pedido
import org.springframework.data.jpa.repository.JpaRepository

interface PedidoRepository: JpaRepository<Pedido, Int> {
    fun findByUsuarioId(usuarioId: Int): List<Pedido>
}