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
    fun getComparacaoPedidos(usuarioId: Int, anoEscolhido: Int, mesEscolhido: Int, periodo: Int): PedidoRelatorioResponse {
        val query: StoredProcedureQuery = entityManager
            .createStoredProcedureQuery("getComparacaoPedidos")
            .registerStoredProcedureParameter("usuario_id_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("ano_escolhido", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("mes_escolhido", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("periodo", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("quantidade_pedido_passado", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .registerStoredProcedureParameter("quantidade_pedido_atual", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .setParameter("usuario_id_input", usuarioId)
            .setParameter("ano_escolhido", anoEscolhido)
            .setParameter("mes_escolhido", mesEscolhido)
            .setParameter("periodo", periodo)

        query.execute()

        val quantidadePedidoPassado = query.getOutputParameterValue("quantidade_pedido_passado") as Int
        val quantidadePedidoAtual = query.getOutputParameterValue("quantidade_pedido_atual") as Int

        return PedidoRelatorioResponse(quantidadePedidoPassado, quantidadePedidoAtual)

    }
}