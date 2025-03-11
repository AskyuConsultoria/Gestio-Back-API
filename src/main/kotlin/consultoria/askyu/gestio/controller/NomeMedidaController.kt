package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.NomeMedida
import consultoria.askyu.gestio.dtos.NomeMedidaCadastroRequest
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.NomeMedidaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nomes-medidas")
class NomeMedidaController(
    var nomeMedidaService: NomeMedidaService
): Controlador(nomeMedidaService)
 {

     @Operation(summary = "Cadastra um nome de medida com base no Id do usuário e da peça.",
         description = "Realiza um cadastro de um nome de medida de roupa com base no Id de usuário e da peça.")
     @ApiResponses(
         value = [
             ApiResponse(responseCode = "201", description = "Cadastro feito com sucesso!"),
             ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
         ],
     )

     @PostMapping("{usuarioId}/{pecaId}")
     fun postByUsuarioIdAndPecaId(
         @Valid
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @RequestBody novoNomeMedida: NomeMedidaCadastroRequest
     ): ResponseEntity<NomeMedida>{
         val nomeMedida = nomeMedidaService.postByUsuarioIdAndPecaId(usuarioId, pecaId, novoNomeMedida)
         return ResponseEntity.status(201).body(nomeMedida)
     }


     @Operation(summary = "Busca todos os nomes de medidas pelo Id do usuário e da peça.",
         description = "Retorna todas os nomes de medidas pelo Id do usuário e da peça.")
     @ApiResponses(
         value = [
             ApiResponse(responseCode = "200", description = "Nomes de medidas buscadas com sucesso!"),
             ApiResponse(responseCode = "204", description = "Nenhum nome de medida cadastrada no sistema."),
             ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
         ],
     )

    @GetMapping("{usuarioId}/{pecaId}")
    fun getAllByUsuarioIdAndPecaId(
        @PathVariable usuarioId: Int,
        @PathVariable pecaId: Int
    ): ResponseEntity<List<NomeMedida>> {
        val listaNomeMedida = nomeMedidaService.getAllByUsuarioIdAndPecaId(usuarioId, pecaId)
        return ResponseEntity.status(200).body(listaNomeMedida)
    }

     @Operation(summary = "Busca todos os nomes de medidas pelo Id do usuário e da peça e pelo nome.",
         description = "Retorna todas os nomes de medidas pelo Id do usuário e da peça e pelo nome da medida.")
     @ApiResponses(
         value = [
             ApiResponse(responseCode = "200", description = "Nomes de medidas buscadas com sucesso!"),
             ApiResponse(responseCode = "204", description = "Nenhum nome de medida cadastrada no sistema."),
             ApiResponse(responseCode = "404", description = "Nenhum nome de medida foi encontrada com este nome.", content = [Content(schema = Schema())])
         ],
     )

     @GetMapping("{usuarioId}/{pecaId}/filtro-nome")
     fun getAllByUsuarioIdAndPecaIdAndNome(
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @RequestParam nome: String,
     ): ResponseEntity<List<NomeMedida>>{
         val listaNomeMedida = nomeMedidaService.getAllByUsuarioIdAndPecaIdAndNomeContains(usuarioId, pecaId, nome)
         return ResponseEntity.status(200).body(listaNomeMedida)
     }

     @Operation(summary = "Busca um nome de medida pelo Id do usuário, da peça e da própria medida.",
         description = "Retorna um nome de medida pelo Id do usuário, da peça e da própria medida.")
     @ApiResponses(
         value = [
             ApiResponse(responseCode = "200", description = "Nome de medida econtrada com sucesso!"),
             ApiResponse(responseCode = "404", description = "Nome de medida não foi encontrado.", content = [Content(schema = Schema())])
         ],
     )

     @GetMapping("{usuarioId}/{pecaId}/{nomeMedidaId}")
         fun getByUsuarioIdAndPecaIdAndId(
             @PathVariable usuarioId: Int,
             @PathVariable pecaId: Int,
             @PathVariable nomeMedidaId: Int,
         ): ResponseEntity<NomeMedida>{
             val nomeMedida = nomeMedidaService.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)
             return ResponseEntity.status(200).body(nomeMedida)
         }


     @Operation(summary = "Atualiza um nome de medida pelo Id do usuário, da peça e da própria medida.",
         description = "Atualiza um nome de medidas pelo Id do usuário, da peça e da própria medida.")
     @ApiResponses(
         value = [
             ApiResponse(responseCode = "200", description = "Nome de medida atulizado com sucesso!"),
             ApiResponse(responseCode = "404", description = "Nome de medida não foi encontrado.", content = [Content(schema = Schema())])
         ],
     )

     @PutMapping("{usuarioId}/{pecaId}/{nomeMedidaId}")
     fun putByUsuarioIdAndPecaIdAndId(
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @PathVariable nomeMedidaId: Int,
         @RequestBody nomeMedidaAtualizada: NomeMedidaCadastroRequest
     ): ResponseEntity<NomeMedida>{
         val nomeMedida = nomeMedidaService.putByUsuarioIdAndPecaIdAndId(
             usuarioId, pecaId, nomeMedidaId, nomeMedidaAtualizada
         )
         return ResponseEntity.status(200).body(nomeMedida)
     }

     @Operation(summary = "Desativa um nome de medida pelo Id do usuário, da peça e da própria medida.",
         description = "Realiza a exclusão lógica de um nome de medida pelo Id do usuário, da peça e da própria medida.")
     @ApiResponses(
         value = [
             ApiResponse(responseCode = "200", description = "Nome de medida desativado com sucesso!"),
             ApiResponse(responseCode = "404", description = "Nome de medida não foi encontrado.", content = [Content(schema = Schema())])
         ],
     )

     @DeleteMapping("{usuarioId}/{pecaId}/{nomeMedidaId}")
     fun deleteByUsuarioIdAndPecaIdAndId(
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @PathVariable nomeMedidaId: Int,
     ): ResponseEntity<Void>{
         nomeMedidaService.deleteByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)
         return ResponseEntity.status(200).build()
     }
}