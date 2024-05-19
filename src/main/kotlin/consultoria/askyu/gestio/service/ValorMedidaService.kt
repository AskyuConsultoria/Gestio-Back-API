package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.ValorMedida
import consultoria.askyu.gestio.dtos.ValorMedidaCadastroRequest
import consultoria.askyu.gestio.repository.ValorMedidaRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

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
    ): ValorMedida{
        itemPedidoService.validateExistence(usuarioId, itemPedidoId)
        nomeMedidaService.validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        pecaService.validarSeAPecaExiste(usuarioId, pecaId)
        clienteService.existenceValidation(usuarioId, clienteId)
        usuarioService.existenceValidation(usuarioId)
        novoValorMedida.nomeMedida!!.id = nomeMedidaId
        novoValorMedida.itemPedido!!.id = itemPedidoId
        novoValorMedida.cliente!!.id = clienteId
        novoValorMedida.peca!!.id = pecaId
        val valorMedida = mapper.map(novoValorMedida, ValorMedida::class.java)
        return valorMedidaRepository.save(valorMedida)
    }
}