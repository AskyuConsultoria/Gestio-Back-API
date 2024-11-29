package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface ItemPedidoRepository: JpaRepository<ItemPedido, Int>, IRepositorio {
    fun findByUsuarioIdAndClienteIdAndAtivoTrue(usuarioId: Int, clienteId: Int): List<ItemPedido>
    fun findByUsuarioIdAndAtivoTrueOrderByIdDesc(usuarioId: Int): List<ItemPedido>

    fun findByUsuarioIdAndId(usuarioId: Int, itemPedidoId: Int): ItemPedido
    fun existsByUsuarioIdAndId(usuarioId: Int, itemPedido: Int): Boolean

}