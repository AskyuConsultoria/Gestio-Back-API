package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.ValorMedida
import consultoria.askyu.gestio.dtos.ValorMedidaCadastroRequest
import consultoria.askyu.gestio.repository.ValorMedidaRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ValorMedidaService(
    var usuarioService: UsuarioService,
    var clienteService: ClienteService,
    var pecaService: PecaService,
    var nomeMedidaService: NomeMedidaService,
    var itemPedidoService: ItemPedidoService,
    var valorMedidaRepository: ValorMedidaRepository,
    var mapper: ModelMapper = ModelMapper()
) {

    fun postByIds(
        usuarioId: Int,
        clienteId: Int,
        pecaId: Int,
        nomeMedidaId: Int,
        itemPedidoId: Int,
        novoValorMedida: ValorMedidaCadastroRequest
    ): ValorMedida {
        usuarioService.existenceValidation(usuarioId)
        clienteService.validateExistence(usuarioId, clienteId)
        pecaService.validarSeAPecaExiste(usuarioId, pecaId)
        nomeMedidaService.validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        itemPedidoService.validateExistence(usuarioId, itemPedidoId)
        val valorMedida = mapper.map(novoValorMedida, ValorMedida::class.java)
        validarSeValorEstaRegistrado(usuarioId, nomeMedidaId, itemPedidoId)
        valorMedida.usuario!!.id = usuarioId
        valorMedida.cliente!!.id = clienteId
        valorMedida.peca!!.id = pecaId
        valorMedida.nomeMedida!!.id = nomeMedidaId
        valorMedida.itemPedido!!.id = itemPedidoId
        return valorMedidaRepository.save(valorMedida)
    }

    fun getByUsuarioIdAndItemPedidoId(usuarioId: Int, itemPedidoId: Int): List<ValorMedida> {
        usuarioService.existenceValidation(usuarioId)
        itemPedidoService.validateExistence(usuarioId, itemPedidoId)
        val listaValorMedida = valorMedidaRepository.findByUsuarioIdAndItemPedidoId(
            usuarioId, itemPedidoId
        )
        validarSeListaEstaVazia(listaValorMedida)
        return listaValorMedida
    }

//    fun getSimples(usuarioId: Int, itemPedidoId: Int): List<ValorMedida>{
//        usuarioService.existenceValidation(usuarioId)
//        itemPedidoService.validateExistence(usuarioId, itemPedidoId)
//        val listaValorMedida = valorMedidaRepository.buscarSimples(
//            usuarioId, itemPedidoId
//        )
//        validarSeListaEstaVazia(listaValorMedida)
//        return listaValorMedida
//    }

    fun putByUsuarioIdAndItemPedidoIdAndId(
        usuarioId: Int,
        itemPedidoId: Int,
        valorMedidaId: Int,
        valorMedidaAtualizado: ValorMedidaCadastroRequest
    ): ValorMedida {
        usuarioService.existenceValidation(usuarioId)
        itemPedidoService.validateExistence(usuarioId, itemPedidoId)
        validarSeValorExiste(usuarioId, itemPedidoId, valorMedidaId)
        valorMedidaAtualizado.usuario!!.id = usuarioId
        valorMedidaAtualizado.itemPedido!!.id = itemPedidoId
        val valorMedida = mapper.map(valorMedidaAtualizado, ValorMedida::class.java)
        valorMedida.id = valorMedidaId
        valorMedidaRepository.save(valorMedida)
        return valorMedida
    }

    fun validarSeValorExiste(
        usuarioId: Int,
        itemPedidoId: Int,
        valorMedidaId: Int
    ) {
        val valorNaoExiste =
            !valorMedidaRepository.existsByUsuarioIdAndItemPedidoIdAndId(
                usuarioId, itemPedidoId, valorMedidaId
            )

        if (valorNaoExiste) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Valor de medida não existe."
            )
        }
    }

    fun validarSeValorEstaRegistrado(usuarioId: Int, nomeMedidaId: Int, itemPedidoId: Int) {
        val existeValorRegistrado =
            valorMedidaRepository.existsByUsuarioIdAndNomeMedidaIdAndItemPedidoId(
                usuarioId,
                nomeMedidaId,
                itemPedidoId
            )

        if (existeValorRegistrado) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Não se pode ter mais de um valor cadastrado em nome de medida numa mesma ficha."
            )
        }
    }

    fun validarSeListaEstaVazia(lista: List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "Lista de Valores de medidas está vazia."
            )
        }
    }

}