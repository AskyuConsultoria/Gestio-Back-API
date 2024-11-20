package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dtos.PedidoRelatorioPorMesResponse
import consultoria.askyu.gestio.interfaces.RelatorioServico
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.StoredProcedureQuery
import jakarta.transaction.Transactional
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    fun exportar(usuarioId: Int): FileSystemResource {

        val file = File("csv-pedidos.csv")
        val fileWriter = FileWriter(file)
        val output = Formatter(fileWriter)
        val ano = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")).toInt() - 1
        val list = getComparacaoPedidos(usuarioId, ano)

        list.map {
            output.format("%s;%d\n",
                it.mes,
                it.qtdPedido
            )
        }

        output.close()
        fileWriter.close()

        val resource = FileSystemResource(file)

        return resource
    }
}

