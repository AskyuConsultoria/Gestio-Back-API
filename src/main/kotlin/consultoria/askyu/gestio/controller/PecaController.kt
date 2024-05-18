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

    @GetMapping("/{id}/filter-nome")
    fun getByUsuarioIdAndNome(@PathVariable id: Int, @RequestParam nome: String): ResponseEntity<List<Peca>>{
        val listaPeca = pecaService.getByUsuarioIdAndNome(id, nome)
        return ResponseEntity.status(200).body(listaPeca)
    }




}