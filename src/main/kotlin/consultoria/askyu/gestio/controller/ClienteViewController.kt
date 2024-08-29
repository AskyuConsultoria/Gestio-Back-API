package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ClienteView
import consultoria.askyu.gestio.interfaces.ViewControlador
import consultoria.askyu.gestio.service.ClienteViewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cliente-view")
class ClienteViewController(
    val clienteViewService: ClienteViewService
):ViewControlador(clienteViewService) {

        @CrossOrigin(
            origins = ["http://localhost:3333"],
            methods = [RequestMethod.GET],
            allowCredentials = "true"
        )
        @GetMapping("/{id}")
        fun visualizar(@PathVariable id: Int): ResponseEntity<ClienteView> {
            val clienteDado = clienteViewService.visualizar(id)
            return ResponseEntity.status(200).body(clienteDado)
        }
}