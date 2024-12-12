package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewServico
import consultoria.askyu.gestio.repository.PedidoViewRepository
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.util.*

@Service
class PedidoViewService(
    val usuarioService: UsuarioService,
    val pedidoViewRepository: PedidoViewRepository
): ViewServico(pedidoViewRepository, usuarioService) {
     fun visualizar(usuarioId: Int, dataInicio: LocalDateTime): List<PedidoGraficoView> {
        usuarioService.existenceValidation(usuarioId)
        return pedidoViewRepository.findByUsuarioIdAndDataInicioEquals(usuarioId, dataInicio.monthValue)
    }

    fun exportar(usuarioId: Int): FileSystemResource {

        val file = File("csv-clientes.csv")
        val fileWriter = FileWriter(file)
        val output = Formatter(fileWriter)

        val list = pedidoViewRepository.findByUsuarioId(usuarioId)

        list.map {
            output.format("%s;%s;%d\n",
                it.nome,
                it.sobrenome,
                it.qtdPedidos
            )
        }

        output.close()
        fileWriter.close()

        val resource = FileSystemResource(file)

        return resource
    }

}