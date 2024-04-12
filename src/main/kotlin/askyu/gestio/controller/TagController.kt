package askyu.gestio.controller

import askyu.gestio.dominio.evento.Tag
import askyu.gestio.dominio.evento.TipoTag
import askyu.gestio.repository.TagRepository
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
    lateinit var repository: TagRepository

    @PostMapping
    fun cadastrar(@RequestBody tag: Tag, idTipo: Int){
        tag.fkTipo = idTipo
        repository.save(tag)
    }

    @GetMapping
    fun listarTags(): ResponseEntity<List<Tag>>{
       val listaTags = repository.findAll()
       return validarListaNaoEVaziaERetornarRespostaOuException(listaTags)
    }
    
    @GetMapping("/nome")
    fun listarTagsPorNome(@RequestParam nome: String): ResponseEntity<List<Tag>>{
        val listaTags = repository.findByNome(nome)
        return validarListaNaoEVaziaERetornarRespostaOuException(listaTags)
    }

    @GetMapping("/idTipo")
    fun buscarTagPorTipo(@RequestParam idTipo: Int): ResponseEntity<Tag> {
       val tag = validarSeTagExisteERetornarTagOuNulo(idTipo).get()
        return ResponseEntity.status(200).body(tag)
    }

    @PutMapping("/idTipo")
    fun atualizarTag(
        @RequestParam idTipo: Int,
        @RequestBody tag: Tag,
        nome: String,
        cor: String
    ): ResponseEntity<Tag>{
        val tag = validarSeTagExisteERetornarTagOuNulo(idTipo).get()
        tag.nome = nome
        tag.cor = cor
        return ResponseEntity.status(200).body(tag)
    }

//    @DeleteMapping("/idTipo")
//    fun desativarTag(@RequestParam idTipo: Int, @RequestBody tag: Tag): ResponseEntity<Void>{
//        val tag = validarSeTagExisteERetornarTagOuNulo(idTipo).get()
//        tag.tipo = false
//        return ResponseEntity.status(204).build()
//    }
    // Validar adição de campo "ativo"

    fun validarSeTagExisteERetornarTagOuNulo(idTipo: Int): Optional<Tag> {
       val tag = repository.findById(idTipo)
        if(tag.isPresent) return tag
        else throw ResponseStatusException(
            HttpStatusCode.valueOf(404), "Tag não existe no sistema."
        )

    }
    
    fun validarListaNaoEVaziaERetornarRespostaOuException(listaTags: List<Tag>): ResponseEntity<List<Tag>> {
        if(listaTags.isNotEmpty()){
            return ResponseEntity.status(201).body(listaTags)
        }
        throw ResponseStatusException(
            HttpStatusCode.valueOf(204), "A lista de tags encontra-se vazia."
        )
    }
}