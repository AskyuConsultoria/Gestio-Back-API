package askyu.gestio.controller

import askyu.gestio.dominio.evento.Tag
import askyu.gestio.dominio.evento.TipoTag
import askyu.gestio.repository.TagTipoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/tipo-tag")

class TipoTagController {
    @Autowired
    lateinit var repository: TagTipoRepository

    @PostMapping()
    fun cadastrar(@RequestBody tipoTag: TipoTag): ResponseEntity<TipoTag>{
        repository.save(tipoTag)
        return ResponseEntity.status(201).body(tipoTag)
    }

    @GetMapping()
    fun listar(): ResponseEntity<List<TipoTag>> {
        val listaTipoTag = repository.findAll()
        return validarListaNaoEVaziaERetornarRespostaOuException(listaTipoTag)
    }

    @PutMapping()
    fun atualizar(@RequestParam idTipoTag: Int, @RequestBody novoTipoTag: TipoTag): ResponseEntity<TipoTag>{
        val tipoTag = repository.findById(idTipoTag).get()
        tipoTag.nome = novoTipoTag.nome
        tipoTag.descricao = novoTipoTag.descricao
        return ResponseEntity.status(201).body(tipoTag)
    }

    @DeleteMapping()
    fun desativarPorId(@RequestParam idTipoTag: Int): ResponseEntity<Void>{
        val tipoTag: TipoTag = repository.findById(idTipoTag).get()
        tipoTag.ativo = false
        repository.save(tipoTag)
        return ResponseEntity.status(204).build()
    }

    fun validarListaNaoEVaziaERetornarRespostaOuException(listaTags: List<TipoTag>): ResponseEntity<List<TipoTag>> {
        if(listaTags.isNotEmpty()){
            return ResponseEntity.status(200).body(listaTags)
        }
        throw ResponseStatusException(
            HttpStatusCode.valueOf(204), "A lista de tags encontra-se vazia."
        )
    }
}