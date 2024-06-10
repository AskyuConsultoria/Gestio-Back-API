package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.ClienteRelatorioResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure

interface PedidoRepository: JpaRepository<Pedido, Int> {
    fun findByUsuarioId(usuarioId: Int): List<Pedido>


    @Procedure(name = "getComparacaoClientes")
    fun buscarRelatorioPedido(usuarioId: Int): ClienteRelatorioResponse
}