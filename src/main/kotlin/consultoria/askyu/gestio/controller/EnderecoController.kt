package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dtos.CepCadastroDTO
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.EnderecoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/enderecos")
class EnderecoController(val service: EnderecoService
): Controlador(service) {

    @Operation(summary = "Buscar todas os endereços",
        description = "Retorna todas os endereços cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo endereços cadastradas"),
            ApiResponse(responseCode = "204", description = "Não há endereços cadastradas", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping
    fun getList(): ResponseEntity<List<Endereco>> {
        val lista = service.listar()
        return ResponseEntity.status(200).body(lista)
    }


    @Operation(summary = "Atualiza um endereço específico.",
        description = "Atualiza um endereço especificado pelo id do usuário e do endereço.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço Atualizado!"),
            ApiResponse(responseCode = "404", description = "Usuário ou endereço não foram encontrados", content = [Content(schema = Schema())]),
        ],
    )

    @PutMapping("/{usuarioId}/{enderecoId}")
    fun atualizar(
        @PathVariable usuarioId: Int,
        @PathVariable enderecoId: Int,
        @RequestBody enderecoAtualizado: Endereco,
    ): ResponseEntity<Endereco> {
        val endereco = service.atualizar(usuarioId, enderecoId, enderecoAtualizado)
        return ResponseEntity.status(200).body(endereco)
    }


    @Operation(summary = "Buscar todas os endereços pelo id do usuário e do cliente",
        description = "Retorna todas os endereços cadastrados pelo id do usuário e do cliente.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo endereços cadastradas do cliente"),
            ApiResponse(responseCode = "204", description = "O cliente não possui clientes cadastrados", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário ou cliente não existem", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/{usuarioId}/{clienteId}")
    fun listarPorCliente(@PathVariable usuarioId: Int, @PathVariable clienteId: Int): ResponseEntity<List<Endereco>> {
        val lista = service.listarPorCliente(usuarioId, clienteId)
        return ResponseEntity.status(200).body(lista)
    }

    @Operation(summary = "Buscaa um endereço com base em seu indentificador único.",
        description = "Buscar um endereço com base em seu id.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo endereço cadastrado."),
            ApiResponse(responseCode = "404", description = "Endereço não encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/buscar-um/{usuarioId}/{enderecoId}")
    fun buscarPorId(@PathVariable usuarioId: Int, @PathVariable enderecoId: Int): ResponseEntity<Endereco> {
        val endereco = service.buscarPorId(usuarioId, enderecoId)
        return ResponseEntity.status(200).body(endereco)
    }


    @Operation(summary = "Cadastrar endereço",
        description = "Cadastra uma endereço no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Endereço cadastrada com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse cep não existe", content = [Content(schema = Schema())])
        ],
    )

    @PostMapping("/por-cep")
    fun cadastrarPorCEP(@Valid @RequestParam cep:CepCadastroDTO):ResponseEntity<Endereco> {
        val salvo = service.cadastrarCEP(cep.cep)
        return ResponseEntity.status(201).body(salvo)
    }


    @Operation(summary = "Cadastrar endereço",
        description = "Cadastra uma endereço no sistema com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não existe", content = [Content(schema = Schema())])
        ],
    )

    @PostMapping("/{usuarioId}")
    fun cadastrar(@PathVariable usuarioId: Int, @RequestBody novoEndereco: Endereco):ResponseEntity<Endereco> {
        val endereco = service.cadastrar(usuarioId, novoEndereco)
        return ResponseEntity.status(201).body(endereco)
    }

    @Operation(summary = "Obtem um endereço",
        description = "Obtem um endereco que ja existe, buscado pelo cep.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse endereco não está cadastrado", content = [Content(schema = Schema())])
        ],
    )

    @GetMapping("/cep")
    fun getEndereco(@RequestParam cep:String):ResponseEntity<Endereco> {
        return service.buscar(cep)
    }

    @Operation(summary = "Desativa um endereço.",
        description = "Desativa um endereço com base no Id do usuário e do Endereço.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Endereço não foi encontrado.", content = [Content(schema = Schema())])
        ],
    )

    @DeleteMapping("{usuarioId}/{enderecoId}")
    fun desativarEndereco(@PathVariable usuarioId: Int, @PathVariable enderecoId: Int): ResponseEntity<Endereco> {
        val endereco = service.desativar(usuarioId, enderecoId)
        return ResponseEntity.status(200).body(endereco)
    }

}