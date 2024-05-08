package askyu.gestio.controller

import askyu.gestio.dominio.ficha.Tecido
import askyu.gestio.dto.TecidoCadastroRequest
import askyu.gestio.repository.TecidoRepository
import askyu.gestio.service.TecidoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatusCode
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
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/tecidos")
class TecidoController(
    var tecidoRepository: TecidoRepository,
    var tecidoService: TecidoService
) {
    
    @PostMapping
    fun cadastrar(@RequestBody @Valid tecido: TecidoCadastroRequest): ResponseEntity<TecidoCadastroRequest>{
        tecidoService.salvar(tecido)
        return ResponseEntity.status(201).body(tecido)
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Tecido>>{
       val listaTecido = tecidoService.listar()
       return ResponseEntity.status(200).body(listaTecido)
    }
    
    @GetMapping("/nome")
    fun listarPorNome(@RequestParam nome: String): ResponseEntity<List<Tecido>>{
        val listaTecido = tecidoService.listarPorNome(nome)
        return ResponseEntity.status(200).body(listaTecido)
    }

    @GetMapping("/{id}")
    fun buscarTecidoPorId(@PathVariable id: Int): ResponseEntity<Tecido> {
        val tecido = tecidoService.buscarTecidoPorId(id)
        return ResponseEntity.status(200).body(tecido)
    }

    @PutMapping()
    fun atualizar(
        @RequestParam id: Int,
        @RequestBody @Valid tecido: TecidoCadastroRequest,
    ): ResponseEntity<TecidoCadastroRequest>{
        tecido.id = id
        tecidoService.salvar(tecido)
        return ResponseEntity.status(200).body(tecido)
    }

    @DeleteMapping()
    fun desativar(@RequestParam id: Int): ResponseEntity<Void>{
        tecidoService.desativar(id)
        return ResponseEntity.status(200).build()
    }


}