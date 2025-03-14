package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ValorMedida
import consultoria.askyu.gestio.dtos.ValorMedidaCadastroRequest
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.ValorMedidaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/valores-medidas")
class ValorMedidaController(
    var valorMedidaService: ValorMedidaService
): Controlador(valorMedidaService) {

    @Operation(summary = "Cadasta uma valor de medida com base em um nome de medida.",
        description = "Realiza um cadastro de uma valor de medida com base nos códigos de identificação do usuário, cliente, peça, nome de medida e ficha.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Cadastro feito com sucesso!"),
            ApiResponse(responseCode = "404", description = "Um dos códigos de identificação utilizados não foram encontrados dentro da base de dados.", content = [Content(schema = Schema())])
        ],
    )

    @PostMapping("/{usuarioId}/{clienteId}/{pecaId}/{nomeMedidaId}/{itemPedidoId}")
    fun cadastrar(
       @PathVariable usuarioId: Int,
       @PathVariable clienteId: Int,
       @PathVariable pecaId: Int,
       @PathVariable nomeMedidaId: Int,
       @PathVariable itemPedidoId: Int,
       @RequestBody novoValorMedida: ValorMedidaCadastroRequest
    ): ResponseEntity<ValorMedida>{
        val valorMedida = valorMedidaService.postByIds(
            usuarioId, clienteId, pecaId, nomeMedidaId, itemPedidoId, novoValorMedida
        )
        return ResponseEntity.status(201).body(valorMedida)
    }

    @Operation(summary = "Busca todas as coleções de tecidos com base no código da ficha.",
        description = "Lista todas as coleções de tecidos com base no código de identificação do usuário e da ficha.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Busca feito com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma valor de medida foi encontrado."),
            ApiResponse(responseCode = "404", description = "Um dos códigos de identificação utilizados não foram encontrados dentro da base de dados.", content = [Content(schema = Schema())])
        ],
    )

    @GetMapping("/{usuarioId}/{itemPedidoId}")
    fun buscarPorItemPedido(
        @PathVariable usuarioId: Int,
        @PathVariable itemPedidoId: Int
    ): ResponseEntity<List<ValorMedida>>{
        val listaValorMedida = valorMedidaService.getByUsuarioIdAndItemPedidoId(
            usuarioId, itemPedidoId
        )
        return ResponseEntity.status(200).body(listaValorMedida)
    }


    @Operation(summary = "Busca uma valor de medida específico.",
        description = "Buscar um valor de medida específico pelo Id do usuário e do valor de medida.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Busca feito com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma valor de medida foi encontrado."),
            ApiResponse(responseCode = "404", description = "Um dos códigos de identificação utilizados não foram encontrados dentro da base de dados.", content = [Content(schema = Schema())])
        ],
    )

    @GetMapping("/{usuarioId}/{valorMedidaId}/buscar-um")
    fun buscarUm(
        @PathVariable usuarioId: Int,
        @PathVariable valorMedidaId: Int,
        @RequestParam valorMedidaAtualizado: Float,
    ): ResponseEntity<ValorMedida>{
        val valorMedida = valorMedidaService.buscarUm(usuarioId, valorMedidaId, valorMedidaAtualizado)
        return ResponseEntity.status(200).body(valorMedida)
    }

//    @GetMapping("/simples/{usuarioId}/{itemPedidoId}")
//    fun buscarSimples(
//        @PathVariable usuarioId: Int,
//        @PathVariable itemPedidoId: Int
//    ): ResponseEntity<List<ValorMedida>>{
//        val listaValorMedida = valorMedidaService.getSimples(
//            usuarioId, itemPedidoId
//        )
//        return ResponseEntity.status(200).body(listaValorMedida)
//    }


    @Operation(summary = "Atualiza um valor de medida código da ficha e do valor de medida..",
        description = "Realiza uma atualização do valor de medida com base no código de identificação do usuário, ficha (o item pedido) e do próprio valor de medida.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Atualização feita com sucesso!"),
            ApiResponse(responseCode = "404", description = "Um dos códigos de identificação utilizados não foram encontrados dentro da base de dados.", content = [Content(schema = Schema())])
        ],
    )

    @PutMapping("/{usuarioId}/{valorMedidaId}")
    fun atualizar(
        @Valid
        @PathVariable usuarioId: Int,
        @PathVariable valorMedidaId: Int,
        @RequestParam valorMedidaAtualizado: Float
    ): ResponseEntity<ValorMedida>{
        val valorMedida = valorMedidaService.putByUsuarioIdAndAndId(
            usuarioId, valorMedidaId, valorMedidaAtualizado
        )
        return ResponseEntity.status(200).body(valorMedida)
    }

}