package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.MoradiaResponse
import consultoria.askyu.gestio.service.MoradiaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/moradia")
class MoradiaController (val service: MoradiaService) {
    @GetMapping
    fun getMoradia(): ResponseEntity<List<MoradiaResponse>>{
        val moradia= service.getLista()
        return ResponseEntity.status(200).body(moradia)
    }

    @GetMapping("/buscar-cliente/{cliente}")
    fun buscarMoradiaCliente(@PathVariable cliente: Cliente): ResponseEntity<List<MoradiaResponse>>{
        val moradia= service.buscarPorCliente(cliente)
        return ResponseEntity.status(200).body(moradia)
    }

    @GetMapping("/buscar-endereco/{endereco}")
    fun buscarMoradiaEndereco(@PathVariable endereco: Endereco): ResponseEntity<List<MoradiaResponse>>{
        val moradia= service.buscarPorEndereco(endereco)
        return ResponseEntity.status(200).body(moradia)
    }

    @GetMapping("/buscar-usuario/{usuario}")
    fun buscarMoradiaUsuario(@PathVariable usuario: Usuario): ResponseEntity<List<MoradiaResponse>>{
        val moradia= service.buscarPorUsuario(usuario)
        return ResponseEntity.status(200).body(moradia)
    }

    @PostMapping
    fun cadastrarMoradia(@RequestBody moradia:Moradia):ResponseEntity<Void>{
        service.salvar(moradia)
        return ResponseEntity.status(201).build()
    }

    @PatchMapping("{id}")
    fun atualizarMoradia(@PathVariable id:Int, @RequestBody novaMoradia: Moradia): ResponseEntity<Void>{
        novaMoradia.id= id
        service.salvar(novaMoradia)
        return ResponseEntity.status(200).build()
    }

    @DeleteMapping("/{id}")
    fun deletarMoradia(@PathVariable id:Int): ResponseEntity<Void>{
        service.excluirPorId(id)
        return ResponseEntity.status(200).build()
    }
}