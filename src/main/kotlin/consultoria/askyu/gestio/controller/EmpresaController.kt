package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Empresa
import consultoria.askyu.gestio.dtos.EmailDTO
import consultoria.askyu.gestio.dtos.EmpresaCadastroRequest
import consultoria.askyu.gestio.dtos.TelefoneDTO
import consultoria.askyu.gestio.service.EmpresaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/empresa")
class EmpresaController(val service: EmpresaService) {

    @Operation(summary = "Buscar todas as empresas",
        description = "Retorna todas as empresas cadastradas.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo empresas cadastradas!"),
            ApiResponse(responseCode = "204", description = "Não há empresas cadastradas!", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping
    fun listarEmpresas(): ResponseEntity<List<Empresa>> {
        var empresas = service.findAll()
        return ResponseEntity.status(200).body(empresas)
    }

    @Operation(summary = "Cadastrar empresa",
        description = "Cadastra uma empresa no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso!"),
            ApiResponse(responseCode = "400", description = "Houve algum dado errado no cadastro!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Essa empresa ja esta cadastrada!", content = [Content(schema = Schema())]),
        ],
    )
    @PostMapping
    fun cadastrarEmpresa(@Valid @RequestBody empresaRequest: EmpresaCadastroRequest): ResponseEntity<Empresa> {
        val empresa = service.cadastrarEmpresa(empresaRequest)
        return ResponseEntity.status(201).body(empresa)
    }

    @Operation(summary = "Remover empresa",
        description = "Desativa uma empresa no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Empresa removida com sucesso!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada!", content = [Content(schema = Schema())]),
        ],
    )
    @DeleteMapping("/{id}")
    fun desativarEmpresa(@PathVariable id: Int): ResponseEntity<String> {
        service.desativarEmpresa(id)
        return ResponseEntity.status(200).body("Empresa removida com sucesso!")
    }

    @Operation(summary = "Atualizar telefone da empresa",
        description = "Atualiza o telefone de contato de uma empresa.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Telefone alterado com sucesso"),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse ja é o telefone cadastrado nessa empresa!", content = [Content(schema = Schema())]),
        ],
    )
    @PatchMapping("/telefone/{id}")
    fun atualizarTelefone(@PathVariable id: Int, @Valid @RequestBody novoTelefone:TelefoneDTO): ResponseEntity<Empresa> {
        var empresa = service.attTelefone(id, novoTelefone)
        return ResponseEntity.status(200).body(empresa)
    }

    @Operation(summary = "Atualizar email da empresa",
        description = "Atualiza o email de contato de uma empresa.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Email alterado com sucesso"),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse ja é o Email cadastrado nessa empresa!", content = [Content(schema = Schema())]),
        ],
    )
    @PatchMapping("/email/{id}")
    fun atualizarEmail(@PathVariable id: Int, @Valid @RequestBody novoEmail:EmailDTO): ResponseEntity<Empresa> {
            var empresa = service.attEmail(id, novoEmail)
            return ResponseEntity.status(200).body(empresa)
    }

//    HttpStatusCode.valueOf(409), "Esse login ja existe!")
//    HttpStatusCode.valueOf(403), "Este usuário tem permissão para autentificar!")
}