package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ValorMedida
import org.springframework.data.jpa.repository.JpaRepository

interface ValorMedidaRepository: JpaRepository<ValorMedida, Int> {
    fun findByUsuarioIdAndItemPedidoId(usuarioId: Int, itemPedidoId: Int): List<ValorMedida>

    fun existsByUsuarioIdAndNomeMedidaIdAndItemPedidoId(
        usuarioId: Int,
        nomeMedidaId: Int,
        itemPedidoId: Int,
    ): Boolean
}