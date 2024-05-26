package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.ItemPedidoCadastroRequest
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.ItemPedidoRepository
import consultoria.askyu.gestio.repository.PecaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class ItemPedidoServiceTest {

    lateinit var itemPedidoRepository: ItemPedidoRepository
    lateinit var itemPedidoService: ItemPedidoService
    lateinit var clienteRepository: ClienteRepository
    lateinit var clienteService: ClienteService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var mapper: ModelMapper

    @BeforeEach
    fun iniciar(){
        itemPedidoRepository = mock(ItemPedidoRepository::class.java)
        clienteRepository = mock(ClienteRepository::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        pecaRepository = mock(PecaRepository::class.java)
        clienteService = ClienteService(clienteRepository, usuarioService)
        pecaService = PecaService(pecaRepository, usuarioService)
        mapper = mock(ModelMapper::class.java)
        itemPedidoService = ItemPedidoService(itemPedidoRepository, usuarioService, clienteService, pecaService, mapper)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o item de pedido não seja encontrado.")
    @Test
    fun existenceValidationExpectNotFound(){
        val usuarioId = 1
        val clienteId = 1



        Mockito.`when`(itemPedidoRepository.existsByUsuarioIdAndId(usuarioId, clienteId)).thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            itemPedidoService.validateExistence(usuarioId, clienteId)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("cadastrar deve retornar o objeto de saída contendo os valores dos parâmetros do endpoint.")
    @Test
    fun cadastrarExpectEqualIO(){
        val beforeCliente: Cliente = Cliente(2)
        val beforeUsuario: Usuario = Usuario(2)
        val beforePeca: Peca = Peca(2)

        val afterCliente = Cliente(1)
        val afterUsuario = Usuario(1)
        val afterPeca = Peca(1)

        val novoItemPedido =
            ItemPedidoCadastroRequest( "Mangas curtas", beforeCliente, beforeUsuario, beforePeca)

        val esperado = ItemPedido(1, "Mangas curtas", afterCliente, afterUsuario, afterPeca, true)

        `when`(usuarioRepository.existsById(1)).thenReturn(true)
        `when`(clienteRepository.existsByUsuarioIdAndId(1, 1)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(1, 1)).thenReturn(true)
        `when`(itemPedidoRepository.save(esperado)).thenReturn(esperado)
        `when`(mapper.map(novoItemPedido, ItemPedido::class.java)).thenReturn(esperado)

        val resultado =
            itemPedidoService.cadastrar(1, 1, 1, novoItemPedido)

        assertEquals(novoItemPedido.usuario!!.id, resultado.usuario!!.id)
        assertEquals(novoItemPedido.cliente!!.id, resultado.cliente!!.id)
        assertEquals(novoItemPedido.peca!!.id, resultado.peca!!.id)

    }

    @DisplayName("cadastrar deve retornar uma exceção se o objeto de saída não estiver com os valores dos parâmetros do endpoint.")
    @Test
    fun cadastrarExpectNotEqualIO(){
        val beforeCliente: Cliente = Cliente(2)
        val beforeUsuario: Usuario = Usuario(2)
        val beforePeca: Peca = Peca(2)

        val afterCliente = Cliente(1)
        val afterUsuario = Usuario(1)
        val afterPeca = Peca(1)

        val novoItemPedido =
            ItemPedidoCadastroRequest( "Mangas curtas", beforeCliente, beforeUsuario, beforePeca)

        val itemPedido = ItemPedido(1, "Mangas curtas", afterCliente, afterUsuario, afterPeca, ativo = true)

        `when`(itemPedidoRepository.save(itemPedido)).thenReturn(itemPedido)

        val excecao = assertThrows(ResponseStatusException::class.java){
            itemPedidoService.validarCadastro(novoItemPedido, itemPedido)
        }

        assertEquals(501, excecao.statusCode.value())
    }
    @DisplayName("findByUsuarioId deve retornar uma exceção com código 204 se não houver conteúdo na lista")
    @Test
    fun findByUsuarioIdExpectNoContent(){
        val usuarioId = 1
        val listaItemPedido: MutableList<ItemPedido> = mutableListOf()
        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)

        `when`(itemPedidoRepository.findByUsuarioId(usuarioId)).thenReturn(listaItemPedido)

        val exececao = assertThrows(ResponseStatusException::class.java){
            itemPedidoService.getByUsuarioId(usuarioId)
        }

        assertEquals(204, exececao.statusCode.value())
    }

    @DisplayName("findByUsuarioIdAndClienteId deve retornar uma exceção com código 204 se não houver conteúdo na lista")
    @Test
    fun findByUsuarioIdAndClienteIdExpectNoContent(){
        val usuarioId = 1
        val clienteId = 1

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(clienteRepository.existsByUsuarioIdAndId(usuarioId, clienteId)).thenReturn(true)

        val listaItemPedido: MutableList<ItemPedido> = mutableListOf()

        `when`(itemPedidoRepository.findByUsuarioIdAndClienteId(usuarioId, clienteId)).thenReturn(listaItemPedido)

        val excecao = assertThrows(ResponseStatusException::class.java){
            itemPedidoService.getByUsuarioIdAndClientId(usuarioId, clienteId)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("deleteByUsuarioIdAndItemPedidoId deve excluir uma linha equivalente os códigos de identificação passados no endpoint.")
    @Test
    fun deleteByUsuarioIdAndItemPedidoIdExpectEqualIO(){
        val usuarioId = 2
        val itemPedidoId = 2
        val cliente = Cliente(2)
        val usuario = Usuario(2)
        val peca = Peca(2)

        `when`(itemPedidoRepository.existsByUsuarioIdAndId(1, 1))
            .thenReturn(true)

        val esperado = ItemPedido(2, "Mangas curtas", cliente, usuario, peca, ativo = true)


        `when`(itemPedidoRepository.findByUsuarioIdAndId(1, 1))
            .thenReturn(esperado)

        `when`(itemPedidoRepository.save(esperado)).thenReturn(esperado)

        val resultado = itemPedidoService.deleteByUsuarioIdAndId(1, 1)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.id, resultado.id)
        assertEquals(esperado.ativo, false)

    }


}