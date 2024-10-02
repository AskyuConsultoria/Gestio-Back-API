package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.UsuarioCadastroDTO
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(val service:UsuarioService): Controlador(service) {


    @Operation(summary = "Cadastrar um Usuario",
        description = "Cadastra um Usuario no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            ApiResponse(responseCode = "400", description = "Houve algum dado errado no cadastro!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse usuário já esta cadastrado!", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping
    fun cadastrarUsuario(@Valid @RequestBody novoUsuario:UsuarioCadastroDTO):ResponseEntity<Usuario>{
        val usuario = service.cadastrar(novoUsuario)
        return ResponseEntity.status(201).body(usuario)
    }

    @Operation(summary = "Realiza o Login do Usuário",
        description = "Realiza o Login do Usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dado errado no login", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse usuario ja esta logado.", content = [Content(schema = Schema())]),
        ],
    )
        @CrossOrigin(
            origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
            methods = [RequestMethod.GET],
            allowCredentials = "true",
        )
        @GetMapping("/login")
    fun logar(@RequestParam usuario:String, @RequestParam senha:String):ResponseEntity<Usuario>{
        val user = service.logar(usuario, senha)
        return ResponseEntity.status(200).body(user)
    }

    @Operation(summary = "Buscar todas os Usuários",
        description = "Retorna todos os Usuarios cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo Usuarios cadastrados!"),
            ApiResponse(responseCode = "204", description = "Não há Usuarios cadastrados!", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping
    fun listarUsuarios(): ResponseEntity<List<Usuario>> {
        val lista = service.findAll()
        return ResponseEntity.status(200).body(lista)
    }

    @Operation(summary = "Realiza o Logoff do Usuário",
        description = "Realiza o Logoff do Usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Logoff realizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "409", description = "Esse usuario não esta logado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping("/login/deslogar")
    fun deslogar(@RequestParam usuario:String):ResponseEntity<Usuario>{
        val user = service.deslogar(usuario)
        return ResponseEntity.status(200).body(user)
    }

    @Operation(summary = "Exibe os dados do Usuario",
        description = "Exibe todos os dados do Usuario.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo dados do Usuario"),
            ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/info/{id}")
    fun getInfo(@RequestParam id:Int):ResponseEntity<Usuario>{
        val info = service.obterInfo(id)
        return ResponseEntity.status(200).body(info)
    }

    @Operation(summary = "Remover usuário",
        description = "Desativa um usuário do sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário removido com sucesso!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado!", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.DELETE],
        allowCredentials = "true"
    )
    @DeleteMapping("/{id}")
    fun deletarUsuario(@PathVariable id:Int):ResponseEntity<String>{
        service.desativarUsuario(id)
        return ResponseEntity.status(200).body("Usuário removido com sucesso!")
    }
}