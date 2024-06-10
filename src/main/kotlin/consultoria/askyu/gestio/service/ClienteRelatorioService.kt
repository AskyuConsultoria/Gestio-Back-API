package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dtos.ClienteRelatorioResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.StoredProcedureQuery
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class ClienteRelatorioService(
    @PersistenceContext private val entityManager: EntityManager
){
        @Transactional
        fun getComparacaoClientes(usuarioId: Int): ClienteRelatorioResponse {
        val query: StoredProcedureQuery = entityManager
            .createStoredProcedureQuery("getComparacaoClientes")
            .registerStoredProcedureParameter("usuario_id_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("quantidade_cliente_mes_passado", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .registerStoredProcedureParameter("quantidade_cliente_mes_atual", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .setParameter("usuario_id_input", usuarioId)

        query.execute()

        val quantidadeClienteMesPassado = query.getOutputParameterValue("quantidade_cliente_mes_passado") as Int
        val quantidadeClienteMesAtual = query.getOutputParameterValue("quantidade_cliente_mes_atual") as Int

        return ClienteRelatorioResponse(quantidadeClienteMesPassado, quantidadeClienteMesAtual)

        }
}