package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Foto
import consultoria.askyu.gestio.dtos.FotoCadastroDTO
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.FotoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/fotos")
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

    @GetMapping(value = ["/dado-arquivo/{usuarioId}/{fotoId}"], produces = ["image/jpg", "image/png"] )
    fun buscarDadoArquivo(@PathVariable usuarioId: Int, @PathVariable fotoId: Int): ResponseEntity<ByteArray>{
        val dadoArquivo = service.buscarDadoArquivo(usuarioId, fotoId)
        return ResponseEntity.status(200).body(dadoArquivo)
    }

    @GetMapping("/{usuarioId}/{fotoId}")
    fun buscarUm(@PathVariable usuarioId: Int ,
                 @PathVariable fotoId:Int): ResponseEntity<Foto> {
        val foto = service.buscarUm(usuarioId,fotoId)
        return ResponseEntity.status(200).body(foto)
    }



    @PutMapping("/{idUsuario}/{idFoto}")
    fun atualizar(@PathVariable idUsuario: Int, @PathVariable idFoto: Int, @RequestBody fotoAtualizada: Foto): ResponseEntity<Foto>{
        val foto = service.atualizar(idUsuario, idFoto, fotoAtualizada)
        return ResponseEntity.status(200).body(foto)
    }

    @PatchMapping(value = ["/{usuarioId}/{fotoId}"], consumes = ["image/jpg", "image/png"])
    fun atualizarImagemFoto(
        @PathVariable usuarioId: Int,
        @PathVariable fotoId: Int,
        @RequestBody dadoArquivo: ByteArray
    ): ResponseEntity<Foto>{
        val fotoAtualizada = service.atualizarFoto(usuarioId, fotoId, dadoArquivo)
        return ResponseEntity.status(200).body(fotoAtualizada)
    }



    @DeleteMapping("/{idUsuario}/{idFoto}")
    fun excluir(
        @PathVariable idUsuario: Int,
        @PathVariable idFoto: Int,
    ): ResponseEntity<Void>{
        val foto = service.excluir(idUsuario, idFoto)
        return ResponseEntity.status(200).build()
    }





}