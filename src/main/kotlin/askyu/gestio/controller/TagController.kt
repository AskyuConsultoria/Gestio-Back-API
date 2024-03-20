package askyu.gestio.controller

import askyu.gestio.Patch.DadosAdicionaisTag
import askyu.gestio.Patch.NovoNome
import askyu.gestio.Tag
import askyu.gestio.Usuario
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/tag")
class TagController {

    var sistema = mutableListOf<Tag>()

    @GetMapping
    fun getList():ResponseEntity<List<Tag>>{
        if(sistema.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "Não existem tags cadastradas")
        }
        return ResponseEntity.status(200).body(sistema)
    }
    @GetMapping("/{i}")
    fun getListId(@PathVariable i:Int):ResponseEntity<Tag>{
        if(existeTag(i)) {
            return ResponseEntity.status(200).body(sistema[i])
        }
        return ResponseEntity.status(204).build()
    }

    @PostMapping
    fun postTag(@RequestBody Tag: Tag): ResponseEntity<String> {
        var i = 0
        sistema.forEach {
            if(Tag.nome == it.nome){
                return updateTag(Tag, i)
            }
            i++
        }
        if(Tag.nome.isNotEmpty()){
            sistema.add(Tag)
            return ResponseEntity.status(201).body("Tag ${Tag.nome} criado com sucesso!")
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(400), "Faltam dados nesse Tag")
    }

    fun updateTag(Tag: Tag, i:Int): ResponseEntity<String> {
        if(Tag.atualizar){
            sistema[i] = Tag
            return ResponseEntity.status(200).body("Tag atualizado com sucesso!")
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse Tag ja existe!")

    }

    @PutMapping("/{i}")
    fun putInTag(@RequestBody prioridadeTag: DadosAdicionaisTag, @PathVariable i:Int):ResponseEntity<Tag>{
        if(existeTag(i)) {
            sistema[i].prioridade = prioridadeTag.prioridade
            return ResponseEntity.status(200).body(sistema[i])
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Tag não encontrado!")
    }


    @PatchMapping("/{i}")
    fun patchInTag(@RequestBody novoNome: NovoNome, @PathVariable i:Int):ResponseEntity<String>{
        if(existeTag(i)) {
            sistema[i].nome = novoNome.nome
            return ResponseEntity.status(200).body("Nome do Tag atualizado para ${sistema[i].nome}")
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Tag não encontrado!")
    }

    @DeleteMapping("/{i}")
    fun DeleteTag(@PathVariable i:Int):ResponseEntity<String>{
        if(existeTag(i)){
            sistema.removeAt(i)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    fun existeTag(i:Int):Boolean{
        return i >= 0 && i < sistema.size
    }
}