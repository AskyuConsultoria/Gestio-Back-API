package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dtos.AgendamentoCadastroDTO
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.AgendamentoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/agendamento")
class AgendamentoController(
    val service: AgendamentoService
): Controlador(service) {

    @Operation(summary = "Cadastro de Agendamento",
        description = "Cadastra um agendamento no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Agendamento cadastrado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível criar esse agendamento.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping
    fun cadastro(@Valid @RequestBody novoAgendamento: AgendamentoCadastroDTO): ResponseEntity<Agendamento> {
        val service = service.cadastrar(novoAgendamento)
        return ResponseEntity.status(200).body(service)
    }

    @Operation(summary = "Listagem de agendamentos pelo id do usuário",
        description = "Busca todas os agendamentos com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{idUsuario}")
    fun buscar(@PathVariable idUsuario: Int): ResponseEntity<List<Agendamento>>{
        val listaAgendamento = service.buscar(idUsuario)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @Operation(summary = "Listagem de agendamentos pelo id do usuário e da etapa.",
        description = "Busca todas os agendamentos com base no id do usuário e no id da etapa, buscando apenas os agendamentos ativos dentro do banco de dados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário ou etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/etapa-ativo/{usuarioId}/{etapaId}")
    fun buscarPorEtapaAtivo(@PathVariable usuarioId: Int, @PathVariable etapaId:Int): ResponseEntity<List<Agendamento>>{
        val listaAgendamento = service.buscarPorEtapaAtivo(usuarioId, etapaId)
        return ResponseEntity.status(200).body(listaAgendamento)
    }


    @Operation(summary = "Listagem de agendamentos pelo id do usuário e da etapa.",
        description = "Busca todas os agendamentos com base no id do usuário e no id da etapa, buscando apenas os agendamentos inativos dentro do banco de dados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário ou etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/etapa-inativo/{usuarioId}/{etapaId}")
    fun buscarPorEtapaInativo(@PathVariable usuarioId: Int, @PathVariable etapaId:Int): ResponseEntity<List<Agendamento>>{
        val listaAgendamento = service.buscarPorEtapaInativo(usuarioId, etapaId)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @Operation(summary = "Listagem de agendamentos pelo id do usuário e da etapa.",
        description = "Busca todas os agendamentos com base no id do usuário e no id da etapa, podendo ser filtrado os agendamentos inativos ou ativos via parâmetro.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário ou etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/etapa-filtro-nome/{usuarioId}/{etapaId}")
    fun buscarPorEtapaENome(
        @PathVariable usuarioId: Int,
        @PathVariable etapaId: Int,
        @RequestParam nome: String,
        @RequestParam ativo: Boolean,
    ): ResponseEntity<List<Agendamento>>{
        val listaAgendamento = service.buscarPorEtapaENome(usuarioId, etapaId, nome, ativo)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @Operation(summary = "Busca um agendamento pelo id do usuário",
        description = "Busca um único agendamento com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou agendamento não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{idUsuario}/{idAgendamento}")
    fun buscarUm(@PathVariable idUsuario: Int, @PathVariable idAgendamento: Int): ResponseEntity<Agendamento>{
        val agendamento = service.buscarUm(idUsuario, idAgendamento)
        return ResponseEntity.status(200).body(agendamento)
    }

    @Operation(summary = "Listagem de agendamentos pelo id do usuário e um intervalo de tempo",
        description = "Busca todas os agendamentos com base no id do usuário e em duas datas diferentes, sendo que a data utilizada é sempre a de ínicio. Dessa forma este endpoint captura os agendamentos dentre uma faixa de período onde os agendamentos tiveram ínicio.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{idUsuario}/intervalo-tempo")
    fun buscarPorIntervaloDeTempo(
        @PathVariable idUsuario: Int,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dataInicio: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dataFim: LocalDateTime,
    ): ResponseEntity<List<Agendamento>> {
        val listaAgendamento =
            service.buscarPorIntervaloDeTempo(idUsuario, dataInicio, dataFim)
        return ResponseEntity.status(200).body(listaAgendamento)
    }


    @Operation(summary = "Listagem dos últimos 7 agendamentos feitos pelo usuário com base no id do usuário",
        description = "Busca os últimos 7 agendamentos feito pelo usuário utilizando de seu Id.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{idUsuario}/ultimos")
    fun buscarUltimos7Pedidos(
        @PathVariable idUsuario: Int,
    ): ResponseEntity<List<Agendamento>> {
        val listaAgendamento =
            service.buscarUltimos7Agendamentos(idUsuario)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @Operation(summary = "Listagem dos agendamentos com base no id do usuário e no nome dos clientes",
        description = "Busca os agendamentos com base no id do usuário e no nome dos clientes.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/filtro-cliente-nome/{idUsuario}")
    fun buscarPorClienteNome(
        @PathVariable idUsuario: Int,
        @RequestParam nome: String,
        @RequestParam ativo: Boolean
    ): ResponseEntity<List<Agendamento>> {
        val listaAgendamento =
            service.buscarPorClienteNome(idUsuario, nome, ativo)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @Operation(summary = "Listagem dos agendamentos com base no id do usuário e no email dos clientes",
        description = "Busca os agendamentos com base no id do usuário e no email dos clientes.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/filtro-cliente-email/{idUsuario}")
    fun buscarPorClienteEmail(
        @PathVariable idUsuario: Int,
        @RequestParam email: String,
    ): ResponseEntity<List<Agendamento>> {
        val listaAgendamento =
            service.buscarPorClienteEmail(idUsuario, email)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @Operation(summary = "Atualiza um agendamento pelo id do usuário e pelo próprio id do próprio agendamento",
        description = "Atualiza um único agendamento com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou agendamento não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.PUT],
        allowCredentials = "true"
    )
    @PutMapping("/{idUsuario}/{idAgendamento}")
    fun atualizar(
        @PathVariable idUsuario: Int,
        @PathVariable idAgendamento: Int,
        @RequestBody agendamentoAtualizado: Agendamento
    ): ResponseEntity<Agendamento>{
        val agendamento = service.atualizar(idUsuario, idAgendamento, agendamentoAtualizado)
        return ResponseEntity.status(200).body(agendamento)
    }


    @Operation(summary = "Atualiza o endereço do agendamento pelo id do endereço",
        description = "Atualiza o endereço do agendamento através do id do usuário, agendamento e endereço.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou agendamento não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.PATCH],
        allowCredentials = "true"
    )
    @PatchMapping("atualizar-endereco/{idUsuario}/{idAgendamento}/{idEndereco}")
    fun atualizarEndereco(
        @PathVariable idUsuario: Int,
        @PathVariable idAgendamento: Int,
        @PathVariable idEndereco: Int
    ): ResponseEntity<Agendamento>{
        val agendamento = service.atualizarEndereco(idUsuario, idAgendamento, idEndereco)
        return ResponseEntity.status(200).body(agendamento)
    }

    @Operation(summary = "Atualiza o telefone do agendamento pelo id do telefone",
        description = "Atualiza o endereço do agendamento através do id do usuário, agendamento e telefone.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou agendamento não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.PATCH],
        allowCredentials = "true"
    )
    @PatchMapping("/atualizar-telefone/{idUsuario}/{idAgendamento}/{idTelefone}")
    fun atualizarTelefone(
        @PathVariable idUsuario: Int,
        @PathVariable idAgendamento: Int,
        @PathVariable idTelefone: Int
    ): ResponseEntity<Agendamento>{
        val agendamento = service.atualizarTelefone(idUsuario, idAgendamento, idTelefone)
        return ResponseEntity.status(200).body(agendamento)
    }



    @Operation(summary = "Exclui logicamente um agendamento pelo id do usuário e pelo próprio id do agendamento",
        description = "Exclui logicamente um único agendamento com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento excluído com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Agendamento não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
  
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.PUT],
        allowCredentials = "true"
    )
    @DeleteMapping("/{idUsuario}/{idAgendamento}")
    fun excluir(
        @PathVariable idUsuario: Int,
        @PathVariable idAgendamento: Int,
    ): ResponseEntity<Agendamento>{
        val agendamento = service.excluir(idUsuario, idAgendamento)
        return ResponseEntity.status(200).body(agendamento)
    }

}