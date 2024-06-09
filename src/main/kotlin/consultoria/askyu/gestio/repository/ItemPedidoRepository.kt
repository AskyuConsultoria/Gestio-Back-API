package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ItemPedido
import org.springframework.data.jpa.repository.JpaRepository

interface ItemPedidoRepository: JpaRepository<ItemPedido, Int> {
    fun findByUsuarioIdAndClienteId(usuarioId: Int, clienteId: Int): List<ItemPedido>
    fun findByUsuarioId(usuarioId: Int): List<ItemPedido>

    fun findByUsuarioIdAndId(usuarioId: Int, itemPedidoId: Int): ItemPedido
    fun existsByUsuarioIdAndId(usuarioId: Int, itemPedido: Int): Boolean

}