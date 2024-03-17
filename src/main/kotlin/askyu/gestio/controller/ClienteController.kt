package askyu.gestio.controller

import askyu.gestio.Cliente
import askyu.gestio.Patch.DadosAdicionais
import askyu.gestio.Patch.NovoNome
import askyu.gestio.Tag
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/cliente")
class ClienteController {

    var sistema = mutableListOf<Cliente>()

    @GetMapping
    fun getList():ResponseEntity<List<Cliente>>{
        if(sistema.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "Não existem tags cadastradas")
        }
        return ResponseEntity.status(200).body(sistema)
    }
    @GetMapping("/{i}")
    fun getListId(@PathVariable i:Int):ResponseEntity<Cliente>{
        if(existeClient(i)) {
            return ResponseEntity.status(200).body(sistema[i])
        }
        return ResponseEntity.status(204).build()
    }

    @PostMapping
    fun postCliente(@RequestBody cliente: Cliente): ResponseEntity<String> {
        var i = 0
        sistema.forEach {
            if(cliente.nome == it.nome){
                return updateClient(cliente, i)
            }
            i++
        }
        if(cliente.nome.isNotEmpty()){
            sistema.add(cliente)
            return ResponseEntity.status(201).body("Cliente ${cliente.nome} criado com sucesso!")
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(400), "Faltam dados nesse cliente")
    }

    fun updateClient(cliente: Cliente, i:Int): ResponseEntity<String> {
        if(cliente.atualizar){
            sistema[i] = cliente
            return ResponseEntity.status(200).body("Cliente atualizado com sucesso!")
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse cliente ja existe!")

    }

    @PutMapping("/{i}")
    fun putInClient(@RequestBody DadosAdicionais: DadosAdicionais, @PathVariable i:Int):ResponseEntity<Cliente>{
        var putIn = sistema[i]
        putIn.CEP = DadosAdicionais.CEP
        return ResponseEntity.status(200).body(sistema[i])
    }


    @PatchMapping("/{i}")
    fun patchInClient(@RequestBody novoNome: NovoNome, @PathVariable i:Int):ResponseEntity<String>{
        if(existeClient(i)) {
            sistema[i].nome = novoNome.nome
            return ResponseEntity.status(200).body("Nome do Cliente atualizado para ${sistema[i].nome}")
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(400), "Cliente não encontrado!")
    }

    @DeleteMapping("/{i}")
    fun DeleteClient(@PathVariable i:Int):ResponseEntity<String>{
        if(existeClient(i)){
            sistema.removeAt(i)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    fun existeClient(i:Int):Boolean{
        return i >= 0 && i < sistema.size
    }
}