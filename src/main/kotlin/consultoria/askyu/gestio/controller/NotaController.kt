package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Nota
import consultoria.askyu.gestio.service.NotaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notas")
class NotaController(
    val service: NotaService
) {


    @Operation(summary = "Realiza o cadastro de uma nota com base em um usuário.",
        description = "Realiza o cadastro de uma nota com base em um usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Cadastro da nota realizado com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @PostMapping("/{usuarioId}")
    fun cadastrar(@PathVariable usuarioId: Int, @RequestBody novaNota: Nota): ResponseEntity<Nota>{
        val nota = service.cadastrar(usuarioId, novaNota)
        return ResponseEntity.status(201).body(nota)
    }



    @Operation(summary = "Busca todas as notas com base em um usuário.",
        description = "Busca todas as notas com base em um usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Notas encontradas sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma nota encontrada."),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/{usuarioId}")
    fun buscar(@PathVariable usuarioId: Int): ResponseEntity<List<Nota>>{
        val listaNota = service.buscar(usuarioId)
        return ResponseEntity.status(200).body(listaNota)
    }




    @Operation(summary = "Busca todas as notas com base em um usuário e um cliente.",
        description = "Busca todas as notas com base em um usuário e um cliente.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Notas encontradas sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma nota encontrada."),
            ApiResponse(responseCode = "404", description = "Usuário ou cliente não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/por-cliente/{usuarioId}/{clienteId}")
    fun buscarPorCliente(@PathVariable usuarioId: Int, @PathVariable clienteId: Int): ResponseEntity<List<Nota>>{
        val listaNota = service.buscarPorCliente(usuarioId, clienteId)
        return ResponseEntity.status(200).body(listaNota)
    }



    @Operation(summary = "Busca todas as notas com base em um usuário e o titulo da nota.",
        description = "Busca todas as notas com base em um usuário e o titulo da nota.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Notas encontradas sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma nota encontrada."),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/por-titulo/{usuarioId}")
    fun buscarPorTitulo(@PathVariable usuarioId: Int, @RequestParam titulo: String): ResponseEntity<List<Nota>>{
        val listaNota = service.buscarPorTitulo(usuarioId, titulo)
        return  ResponseEntity.status(200).body(listaNota)
    }



    @Operation(summary = "Busca uma nota específica com base em um usuário e no id da própria nota.",
        description = "Busca uma nota específica com base em um usuário e no id da própria nota.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Notas encontradas sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma nota encontrada."),
            ApiResponse(responseCode = "404", description = "Usuário ou nota não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/{usuarioId}/{notaId}")
    fun buscarPorId(@PathVariable usuarioId: Int, @PathVariable notaId: Int): ResponseEntity<Nota>{
        val nota = service.buscarPorId(usuarioId, notaId)
        return ResponseEntity.status(200).body(nota)
    }



    @Operation(summary = "Atualiza uma nota específica com base em um usuário e no id da própria nota.",
        description = "Atualiza uma nota específica com base em um usuário e no id da própria nota.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Nota atualizada com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou nota não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @PutMapping("/{usuarioId}/{notaId}")
    fun atualizar(
        @PathVariable usuarioId: Int,
        @PathVariable notaId: Int,
        @RequestBody notaAtualizada: Nota,
    ): ResponseEntity<Nota>{
        val nota = service.atualizar(usuarioId, notaId, notaAtualizada)
        return ResponseEntity.status(200).body(nota)
    }


    @Operation(summary = "Desativa uma nota específica com base em um usuário e no id da própria nota.",
        description = "Desativa uma nota específica com base em um usuário e no id da própria nota.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Nota atualizada com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou nota não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @DeleteMapping("/{usuarioId}/{notaId}")
    fun desativar(@PathVariable usuarioId: Int, @PathVariable notaId: Int): ResponseEntity<Nota>{
        val nota = service.desativar(usuarioId, notaId)
        return ResponseEntity.status(200).body(nota)
    }







}