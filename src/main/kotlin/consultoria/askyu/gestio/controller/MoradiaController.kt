package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Moradia
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/moradia")
class MoradiaController (val service: MoradiaService) {

    @GetMapping("{idUsuario}")
    fun getMoradia(@PathVariable idUsuario: Int): ResponseEntity<List<MoradiaResponse>>{
        val moradia= service.getLista(idUsuario)
        return ResponseEntity.status(200).body(moradia)
    }

    @GetMapping("/{idCliente}/{idUsuario}/")
    fun buscarMoradiaCliente(@PathVariable idUsuario: Int, @PathVariable idCliente: Int): ResponseEntity<List<Moradia>>{
        val moradia= service.buscarPorCliente(idUsuario,idCliente)
        return ResponseEntity.status(200).body(moradia)
    }

    @GetMapping("/{idUsuario}/{idCliente}/{idEndereco}")
    fun buscarMoradiaEndereco(@PathVariable idUsuario: Int,@PathVariable idCliente: Int ,@PathVariable idEndereco: Int)
    : ResponseEntity<List<Moradia>>{
        val moradia= service.buscarPorEndereco(idUsuario, idCliente, idEndereco)
        return ResponseEntity.status(200).body(moradia)
    }

    @PostMapping("/{idUsuario}/{idCliente}/{idEndereco}")
    fun cadastrarMoradia(@PathVariable idUsuario: Int, @PathVariable idCliente: Int, @PathVariable idEndereco: Int,
                         @RequestBody moradia:Moradia):ResponseEntity<Moradia>{
        val moradiaCadastrada= service.salvar(idUsuario, idCliente, idEndereco, moradia)
        return ResponseEntity.status(201).body(moradiaCadastrada)
    }

    @PatchMapping("/{idUsuario}/{idCliente}/{idEndereco}/{id}")
    fun atualizarMoradia(@PathVariable idUsuario: Int, @PathVariable idCliente: Int, @PathVariable idEndereco: Int,
                         @PathVariable id:Int, @RequestBody novaMoradia: Moradia): ResponseEntity<Moradia>{
        novaMoradia.id= id
        service.salvar(idUsuario,idCliente, idEndereco ,novaMoradia)
        return ResponseEntity.status(200).body(novaMoradia)
    }

    @DeleteMapping("/{id}/{idUsuario}/{idCliente}/{idEndereco}")
    fun deletarMoradia(@PathVariable idUsuario: Int, @PathVariable idCliente: Int, @PathVariable idEndereco: Int,
                       @PathVariable id:Int): ResponseEntity<Void>{
        service.excluirPorId(id,idUsuario,idCliente, idEndereco)
        return ResponseEntity.status(200).build()
    }
}