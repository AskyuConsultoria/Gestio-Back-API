package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.repository.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
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

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteService(clienteRepository, usuarioService)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioService)
        itemPedidoRepository = mock(ItemPedidoRepository::class.java)
        itemPedidoService = ItemPedidoService(itemPedidoRepository, usuarioService, clienteService, pecaService)
        nomeMedidaRepository = mock(NomeMedidaRepository::class.java)
        nomeMedidaService = NomeMedidaService(nomeMedidaRepository, pecaService, usuarioService)
        valorMedidaRepository = mock(ValorMedidaRepository::class.java)
        valorMedidaService = ValorMedidaService(usuarioService, clienteService, pecaService, nomeMedidaService, itemPedidoService, valorMedidaRepository)
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


}