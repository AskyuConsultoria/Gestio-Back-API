package consultoria.askyu.gestio.service
import consultoria.askyu.gestio.repository.PecaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.web.server.ResponseStatusException
import java.util.*

class PecaServiceTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioService)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso a peça não existir.")
    @Test
    fun existenceValidationReturnsResponseNotFound(){
        val usuarioId = 1
        val clienteId = 1


        Mockito.`when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, clienteId)).thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            pecaService.validarSeAPecaExiste(usuarioId, clienteId)
        }

        assertEquals(404, excecao.statusCode.value())
    }

}