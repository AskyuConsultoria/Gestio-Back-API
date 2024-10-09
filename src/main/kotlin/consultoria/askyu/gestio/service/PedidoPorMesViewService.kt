package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dtos.PedidoRelatorioPorMesResponse
import consultoria.askyu.gestio.interfaces.RelatorioServico
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.StoredProcedureQuery
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PedidoPorMesRelatorioService(
    @PersistenceContext private val entityManager: EntityManager
) : RelatorioServico(entityManager) {

    @Transactional
    fun getComparacaoPedidos(usuarioId: Int, ano:Int): List<PedidoRelatorioPorMesResponse> {
        val query: StoredProcedureQuery = entityManager
            .createStoredProcedureQuery("listar_pedidos_ultimos_12_meses")
            .registerStoredProcedureParameter("usuario_id_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("ano_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .setParameter("usuario_id_input", usuarioId)
            .setParameter("ano_input", ano)


        query.execute()

        val relatorio = mutableListOf<PedidoRelatorioPorMesResponse>()

        val resultList = query.resultList

        for (result in resultList) {
            val mes = (result as Array<*>)[0] as String
            val qtdPedido = (result[1] as Long).toInt()
            relatorio.add(PedidoRelatorioPorMesResponse(mes, qtdPedido))
        }

        return relatorio
    }
}

