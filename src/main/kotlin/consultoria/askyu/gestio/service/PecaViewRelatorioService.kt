package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dtos.PecaRelatorioPorAnoResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.StoredProcedureQuery
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PecaViewRelatorioService(
    @PersistenceContext private val entityManager: EntityManager
) {

    @Transactional
    fun getPecasVendidasPorUsuarioEAno(usuarioId: Int, ano: Int): List<PecaRelatorioPorAnoResponse> {
        val query: StoredProcedureQuery = entityManager
            .createStoredProcedureQuery("contar_pecas_vendidas_por_usuario_ano")
            .registerStoredProcedureParameter("usuario_id_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .registerStoredProcedureParameter("ano_input", Int::class.java, jakarta.persistence.ParameterMode.IN)
            .setParameter("usuario_id_input", usuarioId)
            .setParameter("ano_input", ano)

        val results = query.resultList

        return results.map {
            val row = it as Array<*>
            PecaRelatorioPorAnoResponse(
                nomePeca = row[0] as String,
                qtdVendida = (row[1] as Number).toInt()
            )
        }
    }
}