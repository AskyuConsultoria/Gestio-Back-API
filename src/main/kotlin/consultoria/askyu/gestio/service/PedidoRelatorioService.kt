package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dtos.PedidoRelatorioResponse
import consultoria.askyu.gestio.interfaces.RelatorioServico
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.StoredProcedureQuery
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PedidoRelatorioService(
    @PersistenceContext private val entityManager: EntityManager
): RelatorioServico(entityManager) {
    @Transactional
    fun getComparacaoPedidos(usuarioId: Int): PedidoRelatorioResponse {
        val query: StoredProcedureQuery = entityManager
            .createStoredProcedureQuery("getComparacaoPedidos")
            .registerStoredProcedureParameter("usuario_id_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("quantidade_pedido_mes_passado", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .registerStoredProcedureParameter("quantidade_pedido_mes_atual", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .setParameter("usuario_id_input", usuarioId)

        query.execute()

        val quantidadePedidoMesPassado = query.getOutputParameterValue("quantidade_pedido_mes_passado") as Int
        val quantidadePedidoMesAtual = query.getOutputParameterValue("quantidade_pedido_mes_atual") as Int

        return PedidoRelatorioResponse(quantidadePedidoMesPassado, quantidadePedidoMesAtual)

    }
}