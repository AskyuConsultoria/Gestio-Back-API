package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ValorMedida
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface ValorMedidaRepository: JpaRepository<ValorMedida, Int>, IRepositorio {
    fun findByUsuarioIdAndItemPedidoId(usuarioId: Int, itemPedidoId: Int): List<ValorMedida>

//    @Query(value = "select vm.item_pedido_id, vm.valor, nm.nome from valor_medida vm join nome_medida nm on vm.nome_medida_id = nm.id where vm.usuario_id = ?1 and vm.item_pedido_id = ?2")
//     fun buscarSimples(@Param("usuarioId") usuarioId: Int, @Param("itemPedidoId") itemPedidoId: Int): List<ValorMedida>

    fun existsByUsuarioIdAndNomeMedidaIdAndItemPedidoId(
        usuarioId: Int,
        nomeMedidaId: Int,
        itemPedidoId: Int,
    ): Boolean

    fun existsByUsuarioIdAndItemPedidoIdAndId(
        usuarioId: Int,
        itemPedidoId: Int,
        valorMedidaId: Int
    ): Boolean

    fun findByUsuarioIdAndAndId(usuarioId: Int, valorMedidaId: Int): ValorMedida
}