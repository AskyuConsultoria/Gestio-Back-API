package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.springframework.web.server.ResponseStatusException

class ClienteServiceTest {

    lateinit var clienteRepository: ClienteRepository
    lateinit var clienteService: ClienteService
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteService(clienteRepository, usuarioService)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o usuário não seja encontrado.")
    @Test
    fun existenceValidationReturnsResponseNotFound(){
        val usuarioId = 1
        val clienteId = 1


        `when`(clienteRepository.existsByUsuarioIdAndId(usuarioId, clienteId)).thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            clienteService.validateExistence(usuarioId, clienteId)
        }

        assertEquals(404, excecao.statusCode.value())
    }
}