package askyu.gestio.controller

import askyu.gestio.Patch.NovoNome
import askyu.gestio.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UsuarioController {

    var sistema = mutableListOf<Usuario>()

    @GetMapping
    fun getList():ResponseEntity<List<Usuario>>{
        return ResponseEntity.status(200).body(sistema)
    }
    @GetMapping("/{i}")
    fun getListId(@PathVariable i:Int):ResponseEntity<Usuario>{
        if(existeUsuario(i)) {
            return ResponseEntity.status(200).body(sistema[i])
        }
        return ResponseEntity.status(204).build()
    }
    @PostMapping
    fun postUser(@RequestBody usuario: Usuario):ResponseEntity<Usuario>{
        sistema.add(usuario)
        return ResponseEntity.status(201).body(usuario)
    }

    @PutMapping("/{i}")
    fun putInUser(@RequestBody usuario: Usuario, @PathVariable i:Int):ResponseEntity<Usuario>{
        sistema[i] = usuario
        return ResponseEntity.status(200).body(sistema[i])
    }


    @PatchMapping("/{i}")
    fun patchInUser(@RequestBody novoNome: NovoNome, @PathVariable i:Int):ResponseEntity<Usuario>{
        sistema[i].nome = novoNome.nome
        return ResponseEntity.status(200).body(sistema[i])
    }
    @DeleteMapping("/{i}")
    fun DeleteUser(@PathVariable i:Int):ResponseEntity<Any>{
        if(existeUsuario(i)){
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    fun existeUsuario(i:Int):Boolean{
        return i >= 0 && i < sistema.size
    }
}