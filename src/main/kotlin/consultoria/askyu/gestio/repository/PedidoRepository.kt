package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.ClienteRelatorioResponse
import consultoria.askyu.gestio.dtos.PedidoRelatorioPorMesResponse
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure

interface PedidoRepository: JpaRepository<Pedido, Int>, IRepositorio {
    fun findByUsuarioId(usuarioId: Int): List<Pedido>

    fun findByUsuarioIdAndId(usuarioId: Int, pedidoId: Int): Pedido

    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<Pedido>

    fun findByUsuarioIdAndIdAndAtivoTrue(usuarioId: Int, pedidoId: Int): Pedido?

    fun findByUsuarioIdAndAgendamentoIdAndAtivoTrue(usuarioId: Int, agendamentoId: Int): List<Pedido>
    fun findByUsuarioIdAndAgendamentoIdAndAtivoIs(usuarioId: Int, agendamentoId: Int, ativo: Boolean): ArrayList<Pedido>

    @Procedure(name = "getComparacaoClientes")
    fun buscarRelatorioPedido(usuarioId: Int): ClienteRelatorioResponse

    @Procedure(name = "listar_pedidos_ultimos_12_meses")
    fun buscarPorMesRelatorioPedido(usuarioId: Int, ano:Int): PedidoRelatorioPorMesResponse
}