package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Funcionario
import consultoria.askyu.gestio.dtos.FuncionarioDTO
import consultoria.askyu.gestio.service.FuncionarioService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/funcionario")
class FuncionarioController(val service:FuncionarioService) {

    @PostMapping
    fun cadastrarFuncionario(@Valid @RequestBody novoFuncionario:FuncionarioDTO):ResponseEntity<Funcionario>{
        var funcionario = service.cadastrar(novoFuncionario)
        return ResponseEntity.status(201).body(funcionario)
    }

    @PostMapping("/login")
    fun logar(@RequestParam usuario:String, @RequestParam senha:String):ResponseEntity<Funcionario>{
        val funcionario = service.logar(usuario, senha)
        return ResponseEntity.status(200).body(funcionario)
    }

    @GetMapping
    fun listarFuncionarios(): ResponseEntity<List<Funcionario>> {
        val lista = service.findAll()
        return ResponseEntity.status(200).body(lista)
    }

    @PostMapping("/login/deslogar")
    fun deslogar(@RequestParam usuario:String):ResponseEntity<Funcionario>{
        val funcionario = service.deslogar(usuario)
        return ResponseEntity.status(200).body(funcionario)
    }

    @GetMapping("/info/{id}")
    fun getInfo(@RequestParam id:Int):ResponseEntity<Funcionario>{
        val info = service.obterInfo(id)
        return ResponseEntity.status(200).body(info)
    }

    @DeleteMapping("/{id}")
    fun deletarFuncionario(@PathVariable id:Int):ResponseEntity<String>{
        service.desativarFuncionario(id)
        return ResponseEntity.status(200).body("Funcionario removida com sucesso!")
    }
}