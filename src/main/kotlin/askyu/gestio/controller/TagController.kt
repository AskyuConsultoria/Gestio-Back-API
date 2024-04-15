package askyu.gestio.controller

import askyu.gestio.dominio.evento.Tag
import askyu.gestio.dominio.evento.TipoTag
import askyu.gestio.repository.TagRepository
import askyu.gestio.repository.TagTipoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@RestController
@RequestMapping("/tag")
class TagController {

    @Autowired
    lateinit var repositoryTag: TagRepository

    @Autowired
    lateinit var repositoryTipoTag: TagTipoRepository
    @PostMapping
    fun cadastrar(@RequestBody tag: Tag, @RequestParam idTipoTag: Int): ResponseEntity<Tag>{
        tag.tipoTag = repositoryTipoTag.findById(idTipoTag).get()
        repositoryTag.save(tag)
        return ResponseEntity.status(201).body(tag)
    }

    @GetMapping
    fun listarTags(): ResponseEntity<List<Tag>>{
       val listaTags = repositoryTag.findAll()
       return validarListaNaoEVaziaERetornarRespostaOuException(listaTags)
    }
    
    @GetMapping("/nome")
    fun listarTagsPorNome(@RequestParam nome: String): ResponseEntity<List<Tag>>{
        val listaTags = repositoryTag.findByNome(nome)
        return validarListaNaoEVaziaERetornarRespostaOuException(listaTags)
    }

    @GetMapping("/idTipoTag")
    fun buscarTagPorTipo(@RequestParam idTipoTag: Int): ResponseEntity<Tag> {
       val tag = repositoryTag.findById(idTipoTag).get()
        return ResponseEntity.status(200).body(tag)
    }

    @PutMapping()
    fun atualizarTag(
        @RequestParam idTag: Int,
        @RequestBody novaTag: Tag,
    ): ResponseEntity<Tag>{
        val tag = repositoryTag.findById(idTag).get()
        tag.nome = novaTag.nome
        tag.cor = novaTag.cor
        tag.ativo = novaTag.ativo
        repositoryTag.save(tag)
        return ResponseEntity.status(200).body(tag)
    }

    @DeleteMapping()
    fun desativarTag(@RequestParam idTag: Int): ResponseEntity<Void>{
        val tag = repositoryTag.findById(idTag).get()
        tag.ativo = false
        repositoryTag.save(tag)
        return ResponseEntity.status(204).build()
    }


    
    fun validarListaNaoEVaziaERetornarRespostaOuException(listaTags: List<Tag>): ResponseEntity<List<Tag>> {
        if(listaTags.isNotEmpty()){
            return ResponseEntity.status(200).body(listaTags)
        }
        throw ResponseStatusException(
            HttpStatusCode.valueOf(204), "A lista de tags encontra-se vazia."
        )
    }
}