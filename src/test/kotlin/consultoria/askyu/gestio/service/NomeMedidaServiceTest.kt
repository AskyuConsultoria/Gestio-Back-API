package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.repository.NomeMedidaRepository
import consultoria.askyu.gestio.repository.PecaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.web.server.ResponseStatusException

class NomeMedidaServiceTest{
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService
    lateinit var nomeMedidaRepository: NomeMedidaRepository
    lateinit var nomeMedidaService: NomeMedidaService

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioService)
        nomeMedidaRepository = mock(NomeMedidaRepository::class.java)
        nomeMedidaService = NomeMedidaService(nomeMedidaRepository, pecaService, usuarioService)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o nome de medida não existir")
    @Test
    fun existenceValidationReturnsResponseNotFound(){
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(false)

       val excecao = assertThrows(ResponseStatusException::class.java){
           nomeMedidaService.validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
       }

        assertEquals(404, excecao.statusCode.value())
    }
}