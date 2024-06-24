package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.EtapaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class EtapaServiceTest {
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var clienteRepository: ClienteRepository
    lateinit var etapaRepository: EtapaRepository
    lateinit var etapaService: EtapaService

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        clienteRepository = mock(ClienteRepository::class.java)
        etapaRepository = mock(EtapaRepository::class.java)
        etapaService = EtapaService(ModelMapper(), etapaRepository, usuarioRepository)
    }

    val afterUsuario = Usuario(1)

    val listaEtapa = mutableListOf(
        Etapa(1, "Entrevista", "Etapa inicial de negociação", false, afterUsuario, true),
        Etapa(2, "Entrevista", "Etapa inicial de negociação", false, afterUsuario, true),
        Etapa(3, "Entrevista", "Etapa inicial de negociação", false, afterUsuario, true),
        Etapa(4, "Entrevista", "Etapa inicial de negociação", false, afterUsuario, true),
        Etapa(5, "Entrevista", "Etapa inicial de negociação", false, afterUsuario, true)
    )

    val etapa: Etapa = Etapa(1, "Entrevista", "Etapa inicial de negociação", false, afterUsuario, true)

    @DisplayName("Deve retornar uma exceção com código 404 caso a peça não existir.")
    @Test
    fun validateExistenceExpectNotFound(){
        `when`(etapaRepository.existsById(anyInt())).thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            etapaService.validateExistence(1)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("listValidation deve retornar uma exceção com código 204 caso a lista esteja vazia")
    @Test
    fun listValidationExpectNoContent(){
        val excecao = assertThrows(ResponseStatusException::class.java){
            etapaService.listValidation(listOf<Etapa>())
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscar deve retornar uma exceção com código 204 caso a lista esteja vazia.")
    @Test
    fun buscarExpectNoContent(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.findByUsuarioIdAndAtivoTrue(anyInt())).thenReturn(listOf())

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java){
            etapaService.buscar(1)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscarUm deve retornar uma exceção com código 404 caso o Id da etapa não existir")
    @Test
    fun buscarUmExpectNoContent(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.existsById(anyInt())).thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java){
            etapaService.buscarUm(1, 1)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("buscar não deve retornar uma exceção quando a lista estiver preenchida.")
    @Test
    fun buscarExpectDoesNotThrow(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.findByUsuarioIdAndAtivoTrue(anyInt())).thenReturn(listaEtapa)

        assertDoesNotThrow {
            etapaService.buscar(1)
        }

    }

    @DisplayName("buscarUm não deve retornar uma exceção quando possuir um objeto válido")
    @Test
    fun buscarUmExpectDoesNotThrow(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.findByUsuarioIdAndIdAndAtivoTrue(anyInt(), anyInt())).thenReturn(etapa)

        assertDoesNotThrow {
            etapaService.buscarUm(1, 1)
        }
    }

    @DisplayName("excluir deve atualizar o campo 'ativo' para 'false'")
    @Test
    fun excluirExpectActiveFalse(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.existsById(anyInt())).thenReturn(true)
        `when`(etapaRepository.findByUsuarioIdAndIdAndAtivoTrue(anyInt(), anyInt())).thenReturn(etapa)
        `when`(etapaRepository.save(etapa)).thenReturn(etapa)

        val resultado = etapaService.excluir(1, 1)

        assertEquals(false, resultado.ativo)
    }
}
