package consultoria.askyu.gestio

import consultoria.askyu.gestio.dtos.TecidoCadastroRequest
import consultoria.askyu.gestio.interfaces.Controlador
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tecidos")
class TecidoController(
    var tecidoService: TecidoService
): Controlador(tecidoService) {

    @Operation(summary = "Cadastra um tecido.",
        description = "Realiza um cadastro de um tecido.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Cadastro feito com sucesso!"),
            ApiResponse(responseCode = "400", description = "Cadastro não foi concluido com sucesso!"),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping("/{usuarioId}/{tecidoId}")
    fun cadastrar(
        @PathVariable usuarioId: Int,
        @PathVariable tecidoId: Int,
        @RequestBody novoTecido: TecidoCadastroRequest
    ): ResponseEntity<Tecido>{
        val tecido = tecidoService.salvar(usuarioId, tecidoId, novoTecido)
        return ResponseEntity.status(201).body(tecido)
    }

    @Operation(summary = "Busca todos os tecidos.",
        description = "Retorna todos os tecidos cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo tecidos cadastrados!"),
            ApiResponse(responseCode = "204", description = "Não há tecidos cadastrados.")
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun listar(@PathVariable usuarioId: Int): ResponseEntity<List<Tecido>>{
       val listaTecido = tecidoService.listar(usuarioId)
       return ResponseEntity.status(200).body(listaTecido)
    }


    @Operation(summary = "Busca tecidos por um nome.",
        description = "Retorna uma lista de tecidos identificado pelo seu nome.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo tecidos pelo nome!"),
            ApiResponse(responseCode = "204", description = "Nenhum tecido foi encotrado com este nome.")
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("{usuarioId}/filtro-nome")
    fun listarPorNome(@PathVariable usuarioId: Int, @RequestParam nome: String): ResponseEntity<List<Tecido>>{
        val listaTecido = tecidoService.listarPorNome(usuarioId, nome)
        return ResponseEntity.status(200).body(listaTecido)
    }


    @Operation(summary = "Busca um tecido por um código de indentificação.",
        description = "Retorna um tecido por um código de indentificação.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo tecido por código de identificação!"),
            ApiResponse(responseCode = "404", description = "Nenhum tecido foi encontrado pelo código de identificação.")
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )

    @GetMapping("{usuarioId}/{tecidoId}")
    fun buscarTecidoPorId(@PathVariable usuarioId: Int, @PathVariable tecidoId: Int): ResponseEntity<Tecido> {
        val tecido = tecidoService.buscarTecidoPorId(usuarioId, tecidoId)
        return ResponseEntity.status(200).body(tecido)
    }

    @Operation(summary = "Atualiza um tecido por um código de indentificação.",
        description = "Atualiza um tecido com base no seu código de identificação.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tecido atualizado com Êxito!"),
            ApiResponse(responseCode = "404", description = "Nenhum tecido foi encontrado pelo código de identificação.")
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.PUT],
        allowCredentials = "true"
    )
    @PutMapping("{usuarioId}/{tecidoId}")
    fun atualizar(
        @PathVariable usuarioId: Int,
        @PathVariable tecidoId: Int,
        @RequestBody @Valid tecidoAtualizado: Tecido,
    ): ResponseEntity<Tecido>{
        val tecido = tecidoService.atualizar(usuarioId, tecidoId, tecidoAtualizado)
        return ResponseEntity.status(200).body(tecido)
    }

    @Operation(summary = "Desativa um tecido com base no seu código de identificação.",
        description = "Realiza uma exclusão lógica do tecido com base no seu código de identificação.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tecido atualizado com Êxito!"),
            ApiResponse(responseCode = "204", description = "Nenhum tecido foi encontrado pelo código de identificação.")
        ],
    )

    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.DELETE],
        allowCredentials = "true"
    )

    @DeleteMapping("/{usuarioId}/{tecidoId}")
    fun desativar(@PathVariable usuarioId: Int, @PathVariable tecidoId: Int): ResponseEntity<Void>{
        tecidoService.desativar(usuarioId, tecidoId)
        return ResponseEntity.status(204).build()
    }


}