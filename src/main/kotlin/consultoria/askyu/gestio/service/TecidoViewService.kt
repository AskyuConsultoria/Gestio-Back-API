package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.TecidoRepository
import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewServico
import consultoria.askyu.gestio.repository.TecidoViewRepository
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.util.*


@Service
class TecidoViewService(
    val tecidoViewRepository: TecidoViewRepository,
    val usuarioService: UsuarioService,
): ViewServico(tecidoViewRepository, usuarioService) {

    fun visualizar(usuarioId: Int, dataInicio: LocalDateTime): List<TecidoGraficoView>{
        usuarioService.existenceValidation(usuarioId)
        return tecidoViewRepository.findByUsuarioIdAndDataInicioGreaterThan(usuarioId, dataInicio)
    }

    fun exportar(usuarioId: Int): FileSystemResource {

        val file = File("csv-tecido.csv")
        val fileWriter = FileWriter(file)
        val output = Formatter(fileWriter)

        val list = tecidoViewRepository.findByUsuarioId(usuarioId)

        list.map {
            output.format("%s;%d\n",
                it.nome,
                it.qtdTecidos
            )
        }

        output.close()
        fileWriter.close()

        val resource = FileSystemResource(file)

        return resource
    }

}