package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.TecidoRepository
import consultoria.askyu.gestio.TecidoService
import consultoria.askyu.gestio.dominio.*
import consultoria.askyu.gestio.dtos.ColecaoTecidoCadastroRequest
import consultoria.askyu.gestio.repository.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class ColecaoTecidoServiceTest{
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var clienteRepository: ClienteRepository
    lateinit var clienteService: ClienteService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService
    lateinit var itemPedidoRepository: ItemPedidoRepository
    lateinit var itemPedidoService: ItemPedidoService
    lateinit var tecidoRepository: TecidoRepository
    lateinit var tecidoService: TecidoService
    lateinit var colecaoTecidoRepository: ColecaoTecidoRepository
    lateinit var colecaoTecidoService: ColecaoTecidoService
    lateinit var mapper: ModelMapper

    @BeforeEach
    fun iniciar(){
        mapper = mock(ModelMapper::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteService(mapper, clienteRepository, usuarioRepository, usuarioService)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioService)
        itemPedidoRepository = mock(ItemPedidoRepository::class.java)
        itemPedidoService = ItemPedidoService(itemPedidoRepository, usuarioService, clienteService, pecaService)
        tecidoRepository = mock(TecidoRepository::class.java)
        tecidoService = TecidoService(usuarioService, tecidoRepository)
        colecaoTecidoRepository = mock(ColecaoTecidoRepository::class.java)
        colecaoTecidoService = ColecaoTecidoService(usuarioService, clienteService, pecaService, itemPedidoService, tecidoService, colecaoTecidoRepository, mapper)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso a coleção de tecido não existir")
    @Test
    fun existenceValidationNotFound(){
        val usuarioId = 1
        val colecaoTecidoId = 1

        `when`(colecaoTecidoRepository.existsByUsuarioIdAndId(usuarioId, colecaoTecidoId)).thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            colecaoTecidoService.existenceValidation(usuarioId, colecaoTecidoId)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("ValidarSeListaEstaVazia deve retornar uma exceção com código 204 quando capturar uma lista vazia.")
    @Test
    fun ValidarSeListaEstaVaziaNoContent(){

        val listaEsperada = listOf<ColecaoTecido>()

        `when`(colecaoTecidoRepository.findByUsuarioIdAndItemPedidoId(1, 1))
            .thenReturn(listaEsperada)

       val excecao = assertThrows(ResponseStatusException::class.java){
            colecaoTecidoService.validarSeListaEstaVazia(listaEsperada)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("cadastrar deve retornar o objeto de saída contendo os valores dos parâmetros do endpoint.")
    @Test
    fun cadastrarExpectEqualIO(){
        val beforeTecido = Tecido(2)
        val beforeItemPedido = ItemPedido(2)
        val beforeCliente = Cliente(2)
        val beforeUsuario = Usuario(2)
        val beforePeca = Peca(2)

        val afterTecido = Tecido(1)
        val afterItemPedido = ItemPedido(1)
        val afterCliente = Cliente(1)
        val afterUsuario = Usuario(1)
        val afterPeca = Peca(1)

        val novaColecaoTecido =
            ColecaoTecidoCadastroRequest(
                beforeTecido,
                beforeItemPedido,
                beforeCliente,
                beforeUsuario,
                beforePeca
            )

        val colecaoTecidoMapeado = ColecaoTecido(
            1,
            beforeTecido,
            beforeItemPedido,
            beforeCliente,
            beforeUsuario,
            beforePeca
        )

        val esperado = ColecaoTecido(
            1,
            afterTecido,
            afterItemPedido,
            afterCliente,
            afterUsuario,
            afterPeca
        )

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(clienteRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(itemPedidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(tecidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)

        `when`(colecaoTecidoService.mapper.map(novaColecaoTecido, ColecaoTecido::class.java))
            .thenReturn(colecaoTecidoMapeado)
        `when`(colecaoTecidoRepository.save(colecaoTecidoMapeado)).thenReturn(colecaoTecidoMapeado)

        val resultado =
            colecaoTecidoService.cadastrar(1, 1, 1, 1, 1, novaColecaoTecido)

        assertEquals(esperado.tecido!!.id, resultado.tecido!!.id)
        assertEquals(esperado.itemPedido!!.id, resultado.itemPedido!!.id)
        assertEquals(esperado.cliente!!.id, resultado.cliente!!.id)
        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)
    }

    @DisplayName("buscarPorFicha deve retornar uma exceção com código 204 quando a lista estiver vazia.")
    @Test
    fun buscarPorFichaExpectNoContent(){
        val listaEsperada = listOf<ColecaoTecido>()

        `when`(colecaoTecidoRepository.findByUsuarioIdAndItemPedidoId(1, 1))
            .thenReturn(listaEsperada)

        val excecao = assertThrows(ResponseStatusException::class.java){
            colecaoTecidoService.buscarPorFicha(1, 1)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscarPorFicha deve retornar uma lista de ColecaoTecido")
    @Test
    fun buscarPorFichaExpectListOfColecaoTecido(){
        val tecido = Tecido(2)
        val itemPedido = ItemPedido(2)
        val cliente = Cliente(2)
        val usuario = Usuario(2)
        val peca = Peca(2)

        val esperado = listOf<ColecaoTecido>(
            ColecaoTecido(1, tecido, itemPedido, cliente, usuario, peca),
            ColecaoTecido(2, tecido, itemPedido, cliente, usuario, peca),
            ColecaoTecido(3, tecido, itemPedido, cliente, usuario, peca),
            ColecaoTecido(4, tecido, itemPedido, cliente, usuario, peca)
        )


        `when`(colecaoTecidoRepository.findByUsuarioIdAndItemPedidoId(anyInt(), anyInt()))
            .thenReturn(esperado)


        val resultado = colecaoTecidoService.buscarPorFicha(1, 1)

        assertInstanceOf(ColecaoTecido::class.java, resultado[0])
        assertEquals(esperado.size, resultado.size)
    }




}