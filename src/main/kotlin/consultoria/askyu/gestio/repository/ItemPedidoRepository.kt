package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ItemPedido
import org.springframework.data.jpa.repository.JpaRepository

interface ItemPedidoRepository: JpaRepository<ItemPedido, Int> {
}