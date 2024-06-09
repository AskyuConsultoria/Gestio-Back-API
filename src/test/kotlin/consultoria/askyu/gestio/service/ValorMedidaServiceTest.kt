package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.*
import consultoria.askyu.gestio.dtos.ValorMedidaCadastroRequest
import consultoria.askyu.gestio.repository.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException


class ValorMedidaServiceTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var clienteRepository: ClienteRepository
    lateinit var clienteService: ClienteService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService
    lateinit var itemPedidoRepository: ItemPedidoRepository
    lateinit var itemPedidoService: ItemPedidoService
    lateinit var nomeMedidaRepository: NomeMedidaRepository
    lateinit var nomeMedidaService: NomeMedidaService
    lateinit var valorMedidaRepository: ValorMedidaRepository
    lateinit var valorMedidaService: ValorMedidaService
    lateinit var mapper: ModelMapper

    @BeforeEach
    fun iniciar() {
        mapper = mock(ModelMapper::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteService(mapper, clienteRepository, usuarioRepository, usuarioService)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioService)
        itemPedidoRepository = mock(ItemPedidoRepository::class.java)
        itemPedidoService = ItemPedidoService(itemPedidoRepository, usuarioService, clienteService, pecaService)
        nomeMedidaRepository = mock(NomeMedidaRepository::class.java)
        nomeMedidaService = NomeMedidaService(nomeMedidaRepository, pecaService, usuarioService)
        valorMedidaRepository = mock(ValorMedidaRepository::class.java)
        valorMedidaService = ValorMedidaService(
            usuarioService,
            clienteService,
            pecaService,
            nomeMedidaService,
            itemPedidoService,
            valorMedidaRepository,
            mapper
        )

    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o valor de medida não existir")
    @Test
    fun existenceValidationReturnsResponseNotFound() {
        val usuarioId = 1
        val itemPedidoId = 1
        val nomeMedidaId = 1

        `when`(valorMedidaRepository.existsByUsuarioIdAndItemPedidoIdAndId(usuarioId, itemPedidoId, nomeMedidaId))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            valorMedidaService.validarSeValorExiste(usuarioId, itemPedidoId, nomeMedidaId)
        }

        assertEquals(404, excecao.statusCode.value())
    }


    @DisplayName("postByIds deve retornar um objeto de saída com os valores dos parâmetros do endpoint.")
    @Test
    fun postByIdsExpectEqualIO(){
        val beforeNomeMedida = NomeMedida(2)
        val beforeItemPedido = ItemPedido(2)
        val beforeCliente = Cliente(2)
        val beforeUsuario = Usuario(2)
        val beforePeca = Peca(2)

        val afterNomeMedida = NomeMedida(1)
        val afterItemPedido = ItemPedido(1)
        val afterCliente = Cliente(1)
        val afterUsuario = Usuario(1)
        val afterPeca = Peca(1)

        val novoValorMedida = ValorMedidaCadastroRequest(
            20.2.toFloat(),
            beforeNomeMedida,
            beforeItemPedido,
            beforeCliente,
            beforeUsuario,
            beforePeca
        )

        val valorMedidaMapeado = ValorMedida(1, 20.2.toFloat(), beforeNomeMedida, beforeItemPedido, beforeCliente, beforeUsuario, beforePeca)
        val esperado =
            ValorMedida(1, 20.2.toFloat(), afterNomeMedida, afterItemPedido, afterCliente, afterUsuario, afterPeca)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(clienteRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(anyInt(), anyInt(), anyInt())).thenReturn(true)
        `when`(itemPedidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(valorMedidaRepository.existsByUsuarioIdAndNomeMedidaIdAndItemPedidoId(anyInt(), anyInt(), anyInt()))
            .thenReturn(false)
        `when`(valorMedidaService.mapper.map(novoValorMedida, ValorMedida::class.java)).thenReturn(valorMedidaMapeado)
        `when`(valorMedidaRepository.save(valorMedidaMapeado)).thenReturn(valorMedidaMapeado)

        val resultado = valorMedidaService.postByIds(1, 1, 1, 1, 1, novoValorMedida)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.cliente!!.id, resultado.cliente!!.id)
        assertEquals(esperado.itemPedido!!.id, resultado.itemPedido!!.id)
        assertEquals(esperado.nomeMedida!!.id, resultado.nomeMedida!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)
    }

    @DisplayName("putByUsuarioIdAndItemPedidoIdAndId deve retornar atualizar o objeto de saída com os parâmetros do endpoint.")
    @Test
    fun putByUsuarioIdAndItemPedidoIdAndIdExpectEqualIO() {
        val usuarioId = 1
        val itemPedidoId = 1
        val valorMedidaId = 1

        val beforeNomeMedida = NomeMedida(2)
        val beforeItemPedido = ItemPedido(2)
        val beforeCliente = Cliente(2)
        val beforeUsuario = Usuario(2)
        val beforePeca = Peca(2)

        val afterNomeMedida = NomeMedida(1)
        val afterItemPedido = ItemPedido(1)
        val afterCliente = Cliente(1)
        val afterUsuario = Usuario(1)
        val afterPeca = Peca(1)

        val valorMedidaAtualizado = ValorMedidaCadastroRequest(
            20.2.toFloat(),
            beforeNomeMedida,
            beforeItemPedido,
            beforeCliente,
            beforeUsuario,
            beforePeca
        )

        val esperado =
            ValorMedida(1, 20.2.toFloat(), afterNomeMedida, afterItemPedido, afterCliente, afterUsuario, afterPeca)

        `when`(usuarioRepository.existsById(1)).thenReturn(true)
        `when`(clienteRepository.existsByUsuarioIdAndId(1, 1)).thenReturn(true)
        `when`(itemPedidoRepository.existsByUsuarioIdAndId(1, 1)).thenReturn(true)
        `when`(valorMedidaRepository.existsByUsuarioIdAndItemPedidoIdAndId(1, 1, valorMedidaId))
            .thenReturn(true)
        `when`(mapper.map(valorMedidaAtualizado, ValorMedida::class.java)).thenReturn(esperado)
        `when`(valorMedidaRepository.save(esperado)).thenReturn(esperado)

        val resultado = valorMedidaService.putByUsuarioIdAndItemPedidoIdAndId(1, 1, 1, valorMedidaAtualizado)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.cliente!!.id, resultado.cliente!!.id)
        assertEquals(esperado.itemPedido!!.id, resultado.itemPedido!!.id)
        assertEquals(esperado.nomeMedida!!.id, resultado.nomeMedida!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)

    }

    @DisplayName("validarSeValorEstaRegistrado deve retornar uma exceção com código 400 caso o valor de medida tente ser cadastrado mais de uma vez com o mesmo nome de medida na mesma ficha.")
    @Test
    fun validarSeValorEstaRegistradoExpectBadRequest() {
        val usuarioId = 1
        val nomeMedidaId = 1
        val itemPedidoId = 1

        val nomeMedida = NomeMedida(1)
        val itemPedido = ItemPedido(1)
        val cliente = Cliente(1)
        val usuario = Usuario(1)
        val peca = Peca(1)



        `when`(
            valorMedidaRepository.existsByUsuarioIdAndNomeMedidaIdAndItemPedidoId(
                usuarioId,
                nomeMedidaId,
                itemPedidoId
            )
        )
            .thenReturn(true)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            valorMedidaService.validarSeValorEstaRegistrado(usuarioId, nomeMedidaId, itemPedidoId)
        }

        assertEquals(400, excecao.statusCode.value())
    }

    @DisplayName("validarSeListaEstaVazia deve retornar uma exceção com código 204 se a lista estiver vazia.")
    @Test
    fun validarSeListaEstaVaziaExpectNoContent() {
        val usuarioId = 1
        val nomeMedidaId = 1
        val itemPedidoId = 1

        val lista = mutableListOf<ValorMedida>()

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(itemPedidoRepository.existsByUsuarioIdAndId(usuarioId, itemPedidoId))
            .thenReturn(true)
        `when`(valorMedidaRepository.findByUsuarioIdAndItemPedidoId(usuarioId, itemPedidoId))
            .thenReturn(lista)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            valorMedidaService.getByUsuarioIdAndItemPedidoId(usuarioId, itemPedidoId)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("getByUsuarioIdAndItemPedidoId deve retornar o mesmo tamanho de lista que está presente no banco de dados.")
    @Test
    fun getByUsuarioIdAndItemPedidoIdExpectSameSize(){
        val nomeMedida = NomeMedida(2)
        val itemPedido = ItemPedido(2)
        val cliente = Cliente(2)
        val usuario = Usuario(2)
        val peca = Peca(2)

        val listaEsperada = listOf<ValorMedida>(
            ValorMedida(1, 20.2.toFloat(), nomeMedida, itemPedido, cliente, usuario, peca),
            ValorMedida(2, 20.2.toFloat(), nomeMedida, itemPedido, cliente, usuario, peca),
            ValorMedida(3, 20.2.toFloat(), nomeMedida, itemPedido, cliente, usuario, peca),
            ValorMedida(4, 20.2.toFloat(), nomeMedida, itemPedido, cliente, usuario, peca),
            ValorMedida(5, 20.2.toFloat(), nomeMedida, itemPedido, cliente, usuario, peca)
        )

        `when`(usuarioRepository.existsById(1)).thenReturn(true)
        `when`(itemPedidoRepository.existsByUsuarioIdAndId(1,1)).thenReturn(true)
         `when`(valorMedidaRepository.findByUsuarioIdAndItemPedidoId(1,1))
             .thenReturn(listaEsperada)

        val resultado = valorMedidaService.getByUsuarioIdAndItemPedidoId(1,1)

        assertEquals(listaEsperada.size, resultado.size)
    }

}