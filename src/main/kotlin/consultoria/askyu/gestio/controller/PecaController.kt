package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dtos.PecaCadastroRequest
import consultoria.askyu.gestio.service.PecaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pecas")
class PecaController(
    var pecaService: PecaService,
) {

    @PostMapping("/{id}")
    fun postByUsuarioId(@Valid @PathVariable id: Int, @RequestBody novaPeca: PecaCadastroRequest): ResponseEntity<Peca>{
     val peca  = pecaService.postByUsuarioId(id, novaPeca)
        return ResponseEntity.status(201).body(peca)
    }

    @GetMapping("/{id}")
    fun getAllByUsuarioId(@PathVariable id: Int): ResponseEntity<List<Peca>>{
       val listaPeca = pecaService.getAllByUsuarioId(id)
        return ResponseEntity.status(200).body(listaPeca)
    }

    @GetMapping("/{usuarioId}/{pecaId}")
    fun getByUsuarioIdAndId(@PathVariable usuarioId: Int, @PathVariable pecaId: Int): ResponseEntity<Peca>{
        val peca = pecaService.getByUsuarioIdAndId(usuarioId, pecaId)
        return ResponseEntity.status(200).body(peca)
    }

    @GetMapping("/{id}/filter-nome")
    fun getByUsuarioIdAndNome(@PathVariable id: Int, @RequestParam nome: String): ResponseEntity<List<Peca>>{
        val listaPeca = pecaService.getByUsuarioIdAndNome(id, nome)
        return ResponseEntity.status(200).body(listaPeca)
    }

    @PutMapping("/{usuarioId}/{pecaId}")
    fun putByUsuarioIdAndId(
        @PathVariable usuarioId: Int,
        @PathVariable pecaId: Int,
        @RequestBody pecaAtualizada: Peca
    ): ResponseEntity<Peca>{
        val peca = pecaService.putByUsuarioId(usuarioId, pecaId, pecaAtualizada)
        return ResponseEntity.status(200).body(peca)
    }

    @DeleteMapping("/{usuarioId}/{pecaId}")
    fun deleteByUsuarioIdAndId(@PathVariable usuarioId: Int, @PathVariable pecaId: Int): ResponseEntity<Void>{
        pecaService.deleteByUsuarioIdAndId(usuarioId, pecaId)
        return ResponseEntity.status(204).build()
    }




}