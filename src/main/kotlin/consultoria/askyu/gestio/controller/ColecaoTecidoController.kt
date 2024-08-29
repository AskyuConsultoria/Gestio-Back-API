package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ColecaoTecido
import consultoria.askyu.gestio.dtos.ColecaoTecidoCadastroRequest
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.ColecaoTecidoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/colecoes-tecidos")
class ColecaoTecidoController(
    val colecaoTecidoService: ColecaoTecidoService
): Controlador(colecaoTecidoService) {


    @Operation(summary = "Cadasta uma coleção de tecido dentro de uma ficha.",
        description = "Realiza um cadastro de uma coleção de tecido com base nos códigos de identificação do usuário, cliente, peça, ficha (o item do pedido) e o tecido.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Cadastro feito com sucesso!"),
            ApiResponse(responseCode = "404", description = "Um dos códigos de identificação utilizados não foram encontrados dentro da base de dados.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping("/{usuarioId}/{clienteId}/{pecaId}/{itemPedidoId}/{tecidoId}")
    fun cadastrar(
        @PathVariable usuarioId: Int,
        @PathVariable clienteId: Int,
        @PathVariable pecaId: Int,
        @PathVariable itemPedidoId: Int,
        @PathVariable tecidoId: Int,
        @RequestBody novaColecaoTecido: ColecaoTecidoCadastroRequest
    ): ResponseEntity<ColecaoTecido>{
        val colecaoTecido = colecaoTecidoService.cadastrar(
            usuarioId, clienteId, pecaId, itemPedidoId, tecidoId, novaColecaoTecido
        )
        return ResponseEntity.status(201).body(colecaoTecido)
    }

    @Operation(summary = "Busca todas as coleções de tecidos com base no código da ficha.",
        description = "Lista todas as coleções de tecidos com base no código de identificação do usuário e da ficha.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Cadastro feito com sucesso!"),
            ApiResponse(responseCode = "204", description = "Lista de coleção de tecidos está vazia", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Um dos códigos de identificação utilizados não foram encontrados dentro da base de dados.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}/{itemPedidoId}")
    fun buscarPorFicha(
        @PathVariable usuarioId: Int,
        @PathVariable itemPedidoId: Int,
    ): ResponseEntity<List<ColecaoTecido>>{
        val listaColecaoTecidos =
            colecaoTecidoService.buscarPorFicha(usuarioId, itemPedidoId)
        return ResponseEntity.status(200).body(listaColecaoTecidos)
    }


    @Operation(summary = "Exclui uma coleção tecido com base no código de identificação daquela coleção.",
        description = "Realiza uma exclusão genuína da coleção de tecido dentro do banco de dados através do código de identificação do usuário e da coleção de tecido.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Coleção de tecidos excluída com sucess0", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário ou Coleção de tecido não existem.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.DELETE],
        allowCredentials = "true"
    )
    @DeleteMapping("/{usuarioId}/{colecaoTecidoId}")
    fun excluir(
        @PathVariable usuarioId: Int,
        @PathVariable colecaoTecidoId: Int
    ): ResponseEntity<Void>{
        colecaoTecidoService.excluir(usuarioId, colecaoTecidoId)
        return ResponseEntity.status(204).build()
    }


}