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
import org.mockito.Mockito.*
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
        mapper = mock(ModelMapper::class.java)
        itemPedidoRepository = mock(ItemPedidoRepository::class.java)
        clienteRepository = mock(ClienteRepository::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        pecaRepository = mock(PecaRepository::class.java)
        clienteService = ClienteService(mapper, clienteRepository, usuarioRepository, usuarioService)
        pecaService = PecaService(pecaRepository, usuarioRepository, usuarioService)
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

        val itemPedidoMapeado = ItemPedido(2, "Mangas curtas", beforeCliente, beforeUsuario, beforePeca, true)

        val esperado = ItemPedido(1, "Mangas curtas", afterCliente, afterUsuario, afterPeca, true)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(clienteRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(itemPedidoService.mapper.map(novoItemPedido, ItemPedido::class.java)).thenReturn(itemPedidoMapeado)
        `when`(itemPedidoRepository.save(itemPedidoMapeado)).thenReturn(itemPedidoMapeado)


        val resultado =
            itemPedidoService.cadastrar(1, 1, 1, novoItemPedido)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.cliente!!.id, resultado.cliente!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)
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
        val beforeCliente = Cliente(2)
        val beforeUsuario = Usuario(2)
        val beforePeca = Peca(2)

        val afterCliente = Cliente(1)
        val afterUsuario = Usuario(1)
        val afterPeca = Peca(1)


        val itemPedidoASerDeletado = ItemPedido(2, "Mangas curtas", beforeCliente, beforeUsuario, beforePeca)
        val esperado = ItemPedido(1, "Mangas curtas", afterCliente, afterUsuario, afterPeca, ativo = false)

        `when`(itemPedidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt()))
            .thenReturn(true)

        `when`(itemPedidoRepository.findByUsuarioIdAndId(anyInt(), anyInt()))
            .thenReturn(itemPedidoASerDeletado)

        `when`(itemPedidoRepository.save(itemPedidoASerDeletado)).thenAnswer{
            invocation ->
            val itemPedido = invocation.getArgument(0, ItemPedido::class.java)
            itemPedido
        }

        val resultado = itemPedidoService.deleteByUsuarioIdAndId(1, 1)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.id, resultado.id)
        assertEquals(esperado.ativo, resultado.ativo)

    }


}