package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.NomeMedida
import consultoria.askyu.gestio.dtos.NomeMedidaCadastroRequest
import consultoria.askyu.gestio.service.NomeMedidaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nomes-medidas")
class NomeMedidaController(
    var nomeMedidaService: NomeMedidaService
)
 {
     @PostMapping("{usuarioId}/{pecaId}")
     fun postByUsuarioIdAndPecaId(
         @Valid
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @RequestBody novoNomeMedida: NomeMedidaCadastroRequest
     ): ResponseEntity<NomeMedida>{
         val nomeMedida = nomeMedidaService.postByUsuarioIdAndPecaId(usuarioId, pecaId, novoNomeMedida)
         return ResponseEntity.status(200).body(nomeMedida)
     }

    @GetMapping("{usuarioId}/{pecaId}")
    fun getAllByUsuarioIdAndPecaId(
        @PathVariable usuarioId: Int,
        @PathVariable pecaId: Int
    ): ResponseEntity<List<NomeMedida>> {
        val listaNomeMedida = nomeMedidaService.getAllByUsuarioIdAndPecaId(usuarioId, pecaId)
        return ResponseEntity.status(200).body(listaNomeMedida)
    }

     @GetMapping("{usuarioId}/{pecaId}/filter-nome")
     fun getAllByUsuarioIdAndPecaIdAndNome(
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @RequestParam nome: String,
     ): ResponseEntity<List<NomeMedida>>{
         val listaNomeMedida = nomeMedidaService.getAllByUsuarioIdAndPecaIdAndNome(usuarioId, pecaId, nome)
         return ResponseEntity.status(200).body(listaNomeMedida)
     }


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

     @DeleteMapping("{usuarioId}/{pecaId}/{nomeMedidaId}")
     fun deleteByUsuarioIdAndPecaIdAndId(
         @PathVariable usuarioId: Int,
         @PathVariable pecaId: Int,
         @PathVariable nomeMedidaId: Int,
     ): ResponseEntity<Void>{
         nomeMedidaService.deleteByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)
         return ResponseEntity.status(204).build()
     }
}