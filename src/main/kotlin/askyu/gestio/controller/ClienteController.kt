//package askyu.gestio.controller
//
//
//import askyu.gestio.dominio.funcionario.Funcionario
//import askyu.gestio.repository.FuncionarioRepository
//import askyu.gestio.repository.PessoaRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/cliente")
//class ClienteController {
//
//    @Autowired
//    lateinit var repository: FuncionarioRepository
//
//    @GetMapping
//    fun getList(): ResponseEntity<List<Funcionario>> {
//        return ResponseEntity.status(200).body(repository.findAll())
//    }
////    @GetMapping("/{i}")
////    fun getListId(@PathVariable i:Int):ResponseEntity<Pessoa>{
////        if(existeClient(i)) {
////            return ResponseEntity.status(200).body(sistema[i])
////        }
////        return ResponseEntity.status(204).build()
////    }
////
////    @PostMapping
////    fun postCliente(@RequestBody pessoa: Pessoa, i:Int = 0): ResponseEntity<String> {
////        sistema.forEach {
////            if(pessoa.nome == it.nome){
////                return updateClient(pessoa, 1, true)
////            }
////        }
////        if(pessoa.nome.isNotEmpty()){
////            sistema.add(pessoa)
////            return ResponseEntity.status(201).body("Cliente ${pessoa.nome} criado com sucesso!")
////        }
////        throw ResponseStatusException(HttpStatusCode.valueOf(400), "Faltam dados nesse cliente")
////    }
////
////    fun updateClient(pessoa: Pessoa, i:Int, t:Boolean): ResponseEntity<String> {
////        if(t){
////            sistema[i] = pessoa
////            return ResponseEntity.status(200).body("Cliente atualizado com sucesso!")
////        }
////        throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse cliente ja existe!")
////
////    }
////
////    @PutMapping("/{i}")
////    fun putInClient(@RequestBody cep: DadosAdicionais, @PathVariable i:Int):ResponseEntity<Pessoa>{
////        sistema[i].cep = cep.cep
////        return ResponseEntity.status(200).body(sistema[i])
////    }
////
////
////    @PatchMapping("/{i}")
////    fun patchInClient(@RequestBody novoNome: NovoNome, @PathVariable i:Int):ResponseEntity<String>{
////        if(existeClient(i)) {
////            sistema[i].nome = novoNome.nome
////            return ResponseEntity.status(200).body("Nome do Cliente atualizado para ${sistema[i].nome}")
////        }
////        throw ResponseStatusException(HttpStatusCode.valueOf(400), "Cliente não encontrado!")
////    }
////
////    @DeleteMapping("/{i}")
////    fun DeleteClient(@PathVariable i:Int):ResponseEntity<String>{
////        if(existeClient(i)){
////            sistema.removeAt(i)
////            return ResponseEntity.status(200).build()
////        }
////        return ResponseEntity.status(404).build()
////    }
////
////    fun existeClient(i:Int):Boolean{
////        return i >= 0 && i < sistema.size
////    }
//}