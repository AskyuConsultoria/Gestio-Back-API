package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dominio.Foto
import consultoria.askyu.gestio.dtos.EtapaCadastroDTO
import consultoria.askyu.gestio.dtos.FotoCadastroDTO
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.FotoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

class FotoController(
    val service: FotoService
): Controlador(service) {

    @PostMapping
    fun cadastro(@Valid @RequestBody novaFoto: FotoCadastroDTO): ResponseEntity<Foto> {
        val service = service.cadastrar(novaFoto)
        return ResponseEntity.status(200).body(service)
    }

    @GetMapping("/{usuarioId}")
    fun buscar(@PathVariable usuarioId: Int): ResponseEntity<List<Foto>>{
        val listaFoto = service.buscar(usuarioId)
        return ResponseEntity.status(200).body(listaFoto)
    }

    @GetMapping("/{usuarioId}/{fotoId}")
    fun buscarUm(@PathVariable usuarioId: Int ,
                 @PathVariable fotoId:Int): ResponseEntity<Foto> {
        val foto = service.buscarUm(usuarioId,fotoId)
        return ResponseEntity.status(200).body(foto)
    }

}