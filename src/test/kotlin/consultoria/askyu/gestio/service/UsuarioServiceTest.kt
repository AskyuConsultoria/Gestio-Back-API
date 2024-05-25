package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.web.server.ResponseStatusException

class UsuarioServiceTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService

    @BeforeEach
    fun iniciar(){
        usuarioRepository = Mockito.mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o usuário não seja encontrado.")
    @Test
    fun existenceValidationReturnsResponseNotFound(){
        val clienteId = 1

        Mockito.`when`(usuarioRepository.existsById(clienteId)).thenReturn(false)

        val resultado = usuarioRepository.existsById(clienteId)

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            usuarioService.existenceValidation(clienteId)
        }

        Assertions.assertEquals(404, excecao.statusCode.value())
    }
}