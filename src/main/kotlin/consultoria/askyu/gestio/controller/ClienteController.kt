package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.service.ClienteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cliente")
class ClienteController (val service:ClienteService){

    @PostMapping
    fun cadastro(@RequestBody cliente: Cliente): ResponseEntity<Cliente>{
    service.salvar(cliente)
    return ResponseEntity.status(200).body(cliente)
    }
}