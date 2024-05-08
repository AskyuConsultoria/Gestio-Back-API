package consultoria.askyu.gestio

import askyu.gestio.dto.TecidoCadastroRequest
import jakarta.validation.Valid
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tecidos")
class TecidoController(
    var tecidoRepository: TecidoRepository,
    var tecidoService: TecidoService
) {

    @Operation(summary = "Cadastra um tecido.",
        description = "Realiza um cadastro de um tecido.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cadastro feito com sucesso!"),
        ],
    )
    @PostMapping
    fun cadastrar(@RequestBody @Valid tecido: TecidoCadastroRequest): ResponseEntity<TecidoCadastroRequest>{
        tecidoService.salvar(tecido)
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
    @GetMapping
    fun listar(): ResponseEntity<List<Tecido>>{
       val listaTecido = tecidoService.listar()
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
    @GetMapping("/nome")
    fun listarPorNome(@RequestParam nome: String): ResponseEntity<List<Tecido>>{
        val listaTecido = tecidoService.listarPorNome(nome)
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
    @GetMapping("/{id}")
    fun buscarTecidoPorId(@PathVariable id: Int): ResponseEntity<Tecido> {
        val tecido = tecidoService.buscarTecidoPorId(id)
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
    @PutMapping()
    fun atualizar(
        @RequestParam id: Int,
        @RequestBody @Valid tecido: TecidoCadastroRequest,
    ): ResponseEntity<TecidoCadastroRequest>{
        tecido.id = id
        tecidoService.salvar(tecido)
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

    @DeleteMapping()
    fun desativar(@RequestParam id: Int): ResponseEntity<Void>{
        tecidoService.desativar(id)
        return ResponseEntity.status(200).build()
    }


}