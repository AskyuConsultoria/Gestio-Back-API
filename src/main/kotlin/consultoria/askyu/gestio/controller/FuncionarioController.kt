package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Funcionario
import consultoria.askyu.gestio.dtos.FuncionarioDTO
import consultoria.askyu.gestio.service.FuncionarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/funcionario")
class FuncionarioController(val service:FuncionarioService) {


    @Operation(summary = "Cadastrar um funcionario",
        description = "Cadastra um funcionario no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Funcionario cadastrado"),
            ApiResponse(responseCode = "400", description = "Houve algum dado errado no cadastro!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse funcionario ja esta cadastrado!", content = [Content(schema = Schema())]),
        ],
    )
    @PostMapping
    fun cadastrarFuncionario(@Valid @RequestBody novoFuncionario:FuncionarioDTO):ResponseEntity<Funcionario>{
        var funcionario = service.cadastrar(novoFuncionario)
        return ResponseEntity.status(201).body(funcionario)
    }

    @Operation(summary = "Realiza o Login do funcionário",
        description = "Realiza o Login do funcionário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dado errado no login", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse usuario ja esta logado.", content = [Content(schema = Schema())]),
        ],
    )
    @PostMapping("/login")
    fun logar(@RequestParam usuario:String, @RequestParam senha:String):ResponseEntity<Funcionario>{
        val funcionario = service.logar(usuario, senha)
        return ResponseEntity.status(200).body(funcionario)
    }

    @Operation(summary = "Buscar todas os funcionários",
        description = "Retorna todos os funcionarios cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo funcionarios cadastrados!"),
            ApiResponse(responseCode = "204", description = "Não há funcionarios cadastrados!", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping
    fun listarFuncionarios(): ResponseEntity<List<Funcionario>> {
        val lista = service.findAll()
        return ResponseEntity.status(200).body(lista)
    }

    @Operation(summary = "Realiza o Logoff do funcionário",
        description = "Realiza o Logoff do funcionário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Logoff realizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse usuario não esta logado.", content = [Content(schema = Schema())]),
        ],
    )
    @PostMapping("/login/deslogar")
    fun deslogar(@RequestParam usuario:String):ResponseEntity<Funcionario>{
        val funcionario = service.deslogar(usuario)
        return ResponseEntity.status(200).body(funcionario)
    }

    @Operation(summary = "Exibe os dados do funcionario",
        description = "Exibe todos os dados do funcionario.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo dados do funcionario"),
            ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/info/{id}")
    fun getInfo(@RequestParam id:Int):ResponseEntity<Funcionario>{
        val info = service.obterInfo(id)
        return ResponseEntity.status(200).body(info)
    }

    @Operation(summary = "Remover funcionario",
        description = "Desativa um funcionario do sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Funcionário removido com sucesso!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Funcionário não encontrado!", content = [Content(schema = Schema())]),
        ],
    )
    @DeleteMapping("/{id}")
    fun deletarFuncionario(@PathVariable id:Int):ResponseEntity<String>{
        service.desativarFuncionario(id)
        return ResponseEntity.status(200).body("Funcionario removido com sucesso!")
    }
}