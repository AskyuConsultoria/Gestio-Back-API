//package askyu.gestio.controller
//
//import askyu.gestio.Patch.NovoNome
//import askyu.gestio.dominio.funcionario.Funcionario
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.DeleteMapping
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PatchMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.PutMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping("/pessoa")
//class PessoaController {
//
//    var sistema = mutableListOf<Funcionario>()
//
//    @GetMapping
//    fun getList():ResponseEntity<List<Funcionario>>{
//        return ResponseEntity.status(200).body(sistema)
//    }
//    @GetMapping("/{i}")
//    fun getListId(@PathVariable i:Int):ResponseEntity<Funcionario>{
//        if(existeUsuario(i)) {
//            return ResponseEntity.status(200).body(sistema[i])
//        }
//        return ResponseEntity.status(204).build()
//    }
//    @PostMapping
//    fun postUser(@RequestBody funcionario: Funcionario):ResponseEntity<Funcionario>{
//        sistema.add(funcionario)
//        return ResponseEntity.status(201).body(funcionario)
//    }
//
//    @PutMapping("/{i}")
//    fun putInUser(@RequestBody funcionario: Funcionario, @PathVariable i:Int):ResponseEntity<Funcionario>{
//        sistema[i] = funcionario
//        return ResponseEntity.status(200).body(sistema[i])
//    }
//
//
//    @PatchMapping("/{i}")
//    fun patchInUser(@RequestBody novoNome: NovoNome, @PathVariable i:Int):ResponseEntity<Funcionario>{
//        sistema[i].nome = novoNome.nome
//        return ResponseEntity.status(200).body(sistema[i])
//    }
//    @DeleteMapping("/{i}")
//    fun DeleteUser(@PathVariable i:Int):ResponseEntity<Any>{
//        if(existeUsuario(i)){
//            return ResponseEntity.status(200).build()
//        }
//        return ResponseEntity.status(404).build()
//    }
//
//    fun existeUsuario(i:Int):Boolean{
//        return i >= 0 && i < sistema.size
//    }
//}