package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.PedidoResponseDTO
import consultoria.askyu.gestio.repository.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class PedidoServiceTest{
    lateinit var mapper: ModelMapper
    lateinit var clienteRepository : ClienteRepository
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var itemPedidoRepository : ItemPedidoRepository
    lateinit var etapaRepository: EtapaRepository
    lateinit var agendamentoRepository : AgendamentoRepository
    lateinit var pedidoRepository : PedidoRepository
    lateinit var pedidoService: PedidoService
    @BeforeEach
    fun iniciar(){
        mapper = mock(ModelMapper::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        clienteRepository = mock(ClienteRepository::class.java)
        etapaRepository = mock(EtapaRepository::class.java)
        itemPedidoRepository = mock(ItemPedidoRepository::class.java)
        agendamentoRepository = mock(AgendamentoRepository::class.java)
        pedidoRepository = mock(PedidoRepository::class.java)
        pedidoService = PedidoService(mapper, pedidoRepository, clienteRepository, usuarioRepository, etapaRepository, itemPedidoRepository, agendamentoRepository)
    }
    
    val afterUsuario = Usuario(1)
    
    val listaPedido = mutableListOf<Pedido>(
        Pedido(1), Pedido(2), Pedido(3), Pedido(4)
    ) 
    
    val pedido = Pedido(1)

    val pedidoResponse = PedidoResponseDTO(1)

    @DisplayName("Deve retornar uma exceção com código 404 caso a peça não existir.")
    @Test
    fun validateExistenceExpectNotFound(){
        `when`(pedidoRepository.existsById(anyInt())).thenReturn(false)

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            pedidoService.validateExistence(1)
        }

        Assertions.assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("listValidation deve retornar uma exceção com código 204 caso a lista esteja vazia")
    @Test
    fun listValidationExpectNoContent(){
        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            pedidoService.listValidation(listOf<Etapa>())
        }

        Assertions.assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscar deve retornar uma exceção com código 204 caso a lista esteja vazia.")
    @Test
    fun buscarExpectNoContent(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.findByUsuarioIdAndAtivoTrue(anyInt())).thenReturn(listOf())

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java){
            pedidoService.buscar(1)
        }

        Assertions.assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscarUm deve retornar uma exceção com código 404 caso o Id da etapa não existir")
    @Test
    fun buscarUmExpectNoContent(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.existsById(anyInt())).thenReturn(false)

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            pedidoService.buscarUm(1, 1)
        }

        Assertions.assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("buscar não deve retornar uma exceção quando a lista estiver preenchida.")
    @Test
    fun buscarExpectDoesNotThrow(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.findByUsuarioIdAndAtivoTrue(anyInt())).thenReturn(listaPedido)

        Assertions.assertDoesNotThrow {
            pedidoService.buscar(1)
        }

    }

    @DisplayName("buscarUm não deve retornar uma exceção quando possuir um objeto válido")
    @Test
    fun buscarUmExpectDoesNotThrow(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.findByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(pedido)
        `when`(pedidoRepository.findByUsuarioIdAndIdAndAtivoTrue(anyInt(), anyInt())).thenReturn(pedido)
        `when`(pedidoService.mapper.map(pedido, PedidoResponseDTO::class.java)).thenReturn(pedidoResponse)

        Assertions.assertDoesNotThrow {
            pedidoService.buscarUm(1, 1)
        }
    }

    @DisplayName("excluir deve atualizar o campo 'ativo' para 'false'")
    @Test
    fun excluirExpectActiveFalse(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.existsById(anyInt())).thenReturn(true)
        `when`(pedidoRepository.findByUsuarioIdAndIdAndAtivoTrue(anyInt(), anyInt())).thenReturn(pedido)
        `when`(pedidoRepository.save(pedido)).thenReturn(pedido)

        val resultado = pedidoService.excluir(1, 1)

        Assertions.assertEquals(false, resultado.ativo)
    }
}