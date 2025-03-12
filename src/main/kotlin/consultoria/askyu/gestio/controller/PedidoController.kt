package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.PedidoCadastroDTO
import consultoria.askyu.gestio.dtos.PedidoRelatorioResponse
import consultoria.askyu.gestio.dtos.PedidoResponseDTO
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.PedidoRelatorioService
import consultoria.askyu.gestio.service.PedidoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedido")
class PedidoController(
    val service: PedidoService,
    val pedidoRelatorioService: PedidoRelatorioService
): Controlador(service) {
    @Operation(summary = "Cadastro de Pedido",
        description = "Cadastra um pedido no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível criar esse pedido.", content = [Content(schema = Schema())]),
        ],
    )

    @PostMapping
    fun cadastro(@Valid @RequestBody novoPedido: PedidoCadastroDTO): ResponseEntity<Pedido> {
        val service = service.cadastrar(novoPedido)
        return ResponseEntity.status(200).body(service)

    }

    @Operation(summary = "Busca um pedido pelo id do usuário e do próprio pedido",
        description = "Busca um único pedido pelo id do usuário")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Pedido encontrado com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhum pedido foi encontrado."),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/{idUsuario}/{idPedido}")
    fun buscarUm(
        @PathVariable idUsuario: Int,
        @PathVariable idPedido: Int
    ): ResponseEntity<PedidoResponseDTO>{
        val pedido = service.buscarUm(idUsuario, idPedido)
        return ResponseEntity.status(200).body(pedido)
    }

    @Operation(summary = "Lista todos os pedidos pelo id do usuário",
        description = "Busca todos os pedidos id do usuário e do próprio pedido.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Pedido encontrado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou pedido não encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/{idUsuario}")
    fun buscar(@PathVariable idUsuario: Int): ResponseEntity<List<PedidoResponseDTO>>{
        val pedido = service.buscar(idUsuario)
        return ResponseEntity.status(200).body(pedido)
    }

    @Operation(summary = "Lista todos os pedidos pelo id do agendamento",
        description = "Busca todos os pedidos id do usuário e de um agendamento.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso!"),
            ApiResponse(responseCode = "204", description = "Este agendamento ainda não possui nenhum pedido."),
            ApiResponse(responseCode = "404", description = "Usuário ou agendamento não encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("buscar-por-agendamento/{usuarioId}/{agendamentoId}")
    fun buscarPorAgendamentoId(
        @PathVariable usuarioId: Int,
        @PathVariable agendamentoId: Int,
    ): ResponseEntity<List<Pedido>>{
        val listaPedido = service.buscarPorAgendamentoId(usuarioId, agendamentoId)
        return ResponseEntity.status(200).body(listaPedido)
    }

    @Operation(summary = "Lista todos os pedidos cancelados pelo id do agendamento",
        description = "Busca todos os pedidos cancelados id do usuário e de um agendamento.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso!"),
            ApiResponse(responseCode = "204", description = "Este agendamento ainda não possui nenhum pedido."),
            ApiResponse(responseCode = "404", description = "Usuário ou agendamento não encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/buscar-por-agendamento-cancelado/{usuarioId}/{agendamentoId}")
    fun buscarPedidosCanceladosPorAgendamentoId(
        @PathVariable usuarioId: Int,
        @PathVariable agendamentoId: Int,
    ): ResponseEntity<ArrayList<Pedido>>{
        val arrayPedido = service.buscarPedidosCancelados(usuarioId, agendamentoId)
        return ResponseEntity.status(200).body(arrayPedido)
    }

    @Operation(summary = "Retorna a quantidade de pedidos no mês e no mês passado no mesmo período",
        description = "Realiza operações dentro do banco de dados através de uma View, calulca a quantidade de períodos do primeiro dia do mês até o dia atual. Esse cálculo também é feito com o mês anterior, e por fim ela retorna a quantidade de pedidos no mês atual até o dia de hoje e a quantidade de pedidos no mês passado no mesmo dia.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou pedido não encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/relatorio-kpi/{usuarioId}")
    fun buscarRelatorioPedido(@PathVariable usuarioId: Int, @RequestParam anoEscolhido: Int, @RequestParam mesEscolhido: Int, @RequestParam periodo: Int): ResponseEntity<PedidoRelatorioResponse> {
        val relatorioPedido = pedidoRelatorioService.getComparacaoPedidos(usuarioId, anoEscolhido, mesEscolhido, periodo)
        return ResponseEntity.status(200).body(relatorioPedido)
    }

    @Operation(summary = "Atualiza uma etapa pelo id do usuário e pelo próprio id da etapa",
        description = "Atualiza uma única etapa com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Etapas encontradas com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )

    @PutMapping("/{idUsuario}/{idPedido}")
    fun atualizar(
        @PathVariable idUsuario: Int,
        @PathVariable idPedido: Int,
        @RequestBody pedidoAtualizado: Pedido
    ): ResponseEntity<Pedido>{
        val pedido = service.atualizar(idUsuario, idPedido, pedidoAtualizado)
        return ResponseEntity.status(200).body(pedido)
    }

    @Operation(summary = "Exclui logicamente uma pedido pelo id do usuário e pelo próprio id da etapa",
        description = "Exclui logicamente uma única etapa com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pedidos encontradas com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )

    @DeleteMapping("/{idUsuario}/{idPedido}")
    fun excluir(
        @PathVariable idUsuario: Int,
        @PathVariable idPedido: Int,
    ): ResponseEntity<Pedido>{
        val pedido = service.excluir(idUsuario, idPedido)
        return ResponseEntity.status(200).body(pedido)
    }


    @Operation(summary = "Exclui logicamente todos os pedidos com base em um agendamento",
        description = "Exclui logicamente todos os pedidos com base no id do usuário e de um agendamento.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pedidos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhum pedido cadastrado no agendamento."),
            ApiResponse(responseCode = "404", description = "Usuário ou Agendamento não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )

    @DeleteMapping("/excluir-por-agendamento/{idUsuario}/{idAgendamento}")
    fun excluirPorAgendamento(
        @PathVariable idUsuario: Int,
        @PathVariable idAgendamento: Int,
    ): ResponseEntity<Array<Pedido>>{
        val arrayPedido  = service.excluirPorAgendamento(idUsuario, idAgendamento)
        return ResponseEntity.status(200).body(arrayPedido)
    }


    @Operation(summary = "Reativa logicamente todos os pedidos com base em um agendamento",
        description = "Reativa logicamente todos os pedidos com base no id do usuário e de um agendamento.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pedidos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhum pedido cadastrado no agendamento."),
            ApiResponse(responseCode = "404", description = "Usuário ou Agendamento não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )

    @PatchMapping("/reativar-por-agendamento/{idUsuario}/{idAgendamento}")
    fun reativarPorAgendamento(
        @PathVariable idUsuario: Int,
        @PathVariable idAgendamento: Int,
    ): ResponseEntity<Array<Pedido>>{
        val arrayPedido = service.reativarPorAgendamento(idUsuario, idAgendamento)
        return ResponseEntity.status(200).body(arrayPedido)
    }



}