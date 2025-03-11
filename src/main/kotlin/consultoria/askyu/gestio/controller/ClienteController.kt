package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteAtualizarDTO
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteRelatorioResponse
import consultoria.askyu.gestio.dtos.ClienteResponse
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.ClienteRelatorioService
import consultoria.askyu.gestio.service.ClienteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
@RequestMapping("/clientes")
class ClienteController (
    val service: ClienteService,
    val clienteRelatorioService: ClienteRelatorioService
): Controlador(service){
    @Operation(summary = "Cadastro de Cliente",
        description = "Cadastra um cliente no sistema do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível cadastrar esse cliente.", content = [Content(schema = Schema())]),
        ],
    )

    @PostMapping
        fun cadastro(@Valid @RequestBody novoCliente: ClienteCadastroDTO): ResponseEntity<Cliente>{
        val service = service.cadastrar(novoCliente)

        return ResponseEntity.status(200).body(service)
    }

    @Operation(
        summary = "Cadastro de Cliente ou mais por txt",
        description = "Cadastra um ou mais clientes no sistema a partir de um arquivo txt."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "Erro ao processar o arquivo.", content = [Content(schema = Schema())]),
        ]
    )

    @PostMapping("/txt", consumes = ["multipart/form-data"])
    fun cadastroTxt(@RequestParam("file") novoCliente: MultipartFile): ResponseEntity<List<Cliente>> {
        if (novoCliente.isEmpty) {
            return ResponseEntity.badRequest().build()
        }
        val tempFile = File.createTempFile("clientes-", ".txt")

        novoCliente.transferTo(tempFile)

        val clientes = service.cadastrarTxt(tempFile)

        return ResponseEntity.status(200).body(clientes)
    }


    @Operation(summary = "Pesquisar um cliente",
        description = "Busca um cliente do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo cliente."),
            ApiResponse(responseCode = "204", description = "Não foi possível exibir esse cliente.", content = [Content(schema = Schema())]),
        ],
    )


    @GetMapping("/{idCliente}/buscarUm")
    fun getUmCliente (@PathVariable idCliente: Int): ResponseEntity<Cliente>{
        val cliente = service.buscarUmCliente(idCliente)

        return ResponseEntity.status(200).body(cliente)
    }

    @Operation(summary = "Pesquisar todos os clientes",
        description = "Busca todos os clientes do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo lista de clientes."),
            ApiResponse(responseCode = "204", description = "Não foi possível exibir essa lista de cliente.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/{idUsuario}")
    fun getTodosOsClientes (@PathVariable idUsuario: Int): ResponseEntity<List<ClienteResponse>>{
        val listaCliente = service.buscarClientes(idUsuario)

        return ResponseEntity.status(200).body(listaCliente)
    }

    @Operation(summary = "Pesquisar todos os clientes pelo id do Usuário e pelo nome do cliente",
        description = "Busca todos os clientes com base no usuário e no nome do cliente.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo lista de clientes."),
            ApiResponse(responseCode = "204", description = "Não foi possível exibir essa lista de cliente.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/{idUsuario}/filtro-nome")
    fun buscarPorClientesPorNome (@PathVariable idUsuario: Int, @RequestParam nome: String): ResponseEntity<List<Cliente>>{
        val listaCliente = service.buscarClientesPorNome(idUsuario, nome)
        return ResponseEntity.status(200).body(listaCliente)
    }


    @GetMapping("/por-responsavel/{usuarioId}/{responsavelId}")
    fun buscarClientesPorResponsavel(@PathVariable usuarioId: Int, @PathVariable responsavelId: Int): ResponseEntity<List<Cliente>> {
        val listaCliente = service.buscarClientesPorResponsavel(usuarioId, responsavelId)
        return ResponseEntity.status(200).body(listaCliente)
    }




    @GetMapping("relatorio-kpi/{usuarioId}")
    fun buscarRelatorioPedido(@PathVariable usuarioId: Int, @RequestParam anoEscolhido: Int, @RequestParam mesEscolhido: Int, @RequestParam periodo: Int): ResponseEntity<ClienteRelatorioResponse> {
        val relatorioPedido = clienteRelatorioService.getComparacaoClientes(usuarioId, anoEscolhido, mesEscolhido, periodo)
        return ResponseEntity.status(200).body(relatorioPedido)
    }

    @Operation(summary = "Desativar cliente",
        description = "Desativar (visualmente será excluir) um cliente no sistema do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso"),
            ApiResponse(responseCode = "404", description = "Não foi possível desativar esse cliente.", content = [Content(schema = Schema())]),
        ],
    )

    @DeleteMapping("/{usuarioId}/{clienteId}")
    fun desativarCliente(@PathVariable usuarioId: Int, @PathVariable clienteId: Int ): ResponseEntity<Cliente> {
        val cliente = service.desativarClientePorId(usuarioId, clienteId)
        return ResponseEntity.status(200).body(cliente)
    }

    @Operation(summary = "Atualizar Cliente",
        description = "Atualiza os campos de um cliente no sistema do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível atualizar esse cliente.", content = [Content(schema = Schema())]),
        ],
    )

    @PutMapping("/{idCliente}")
    fun atualizarCliente(@Valid @PathVariable idCliente: Int, @RequestBody dados: ClienteAtualizarDTO): ResponseEntity<Cliente>{
        val clienteAtualizado = service.atualizarCliente(idCliente, dados)

        return ResponseEntity.status(200).body(clienteAtualizado)
    }


    @PatchMapping("/{usuarioId}/{clienteId}/{responsavelId}")
    fun atualizarResponsavel(
        @PathVariable usuarioId: Int,
        @PathVariable clienteId: Int,
        @PathVariable responsavelId: Int,
    ): ResponseEntity<Cliente>{
        val cliente = service.atualizarResponsavel(usuarioId, clienteId, responsavelId)
        return ResponseEntity.status(200).body(cliente)
    }


    @PatchMapping("/{usuarioId}/{clienteId}")
    fun retirarResponsavel(
        @PathVariable usuarioId: Int,
        @PathVariable clienteId: Int,
    ): ResponseEntity<Cliente>{
        val cliente = service.retirarResponsavel(usuarioId, clienteId)
        return ResponseEntity.status(200).body(cliente)
    }

}


