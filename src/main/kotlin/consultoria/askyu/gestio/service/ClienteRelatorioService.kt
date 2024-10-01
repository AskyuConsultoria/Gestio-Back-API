package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dtos.ClienteRelatorioResponse
import consultoria.askyu.gestio.interfaces.RelatorioServico
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.StoredProcedureQuery
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class ClienteRelatorioService(
    @PersistenceContext private val entityManager: EntityManager
):RelatorioServico(entityManager){
        @Transactional
        fun getComparacaoClientes(usuarioId: Int, anoEscolhido: Int, mesEscolhido: Int, periodo: Int): ClienteRelatorioResponse {
        val query: StoredProcedureQuery = entityManager
            .createStoredProcedureQuery("getComparacaoClientes")
            .registerStoredProcedureParameter("usuario_id_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("ano_escolhido", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("mes_escolhido", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("periodo", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("quantidade_cliente_passado", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .registerStoredProcedureParameter("quantidade_cliente_atual", Int::class.java, jakarta.persistence.ParameterMode.OUT)
            .setParameter("usuario_id_input", usuarioId)
            .setParameter("ano_escolhido", anoEscolhido)
            .setParameter("mes_escolhido", mesEscolhido)
            .setParameter("periodo", periodo)


        query.execute()

        val quantidadeClientePassado = query.getOutputParameterValue("quantidade_cliente_passado") as Int
        val quantidadeClienteAtual = query.getOutputParameterValue("quantidade_cliente_atual") as Int

        return ClienteRelatorioResponse(quantidadeClientePassado, quantidadeClienteAtual)

        }
}