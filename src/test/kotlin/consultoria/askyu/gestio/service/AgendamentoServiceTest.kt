package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.repository.AgendamentoRepository
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.EtapaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class AgendamentoServicoTest {
    lateinit var mapper: ModelMapper
    lateinit var agendamentoRepository: AgendamentoRepository 
    lateinit var clienteRepository: ClienteRepository
    lateinit var etapaRepository: EtapaRepository
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var agendamentoService: AgendamentoService
    
    @BeforeEach
    fun iniciar(){
        mapper = mock(ModelMapper::class.java)
        clienteRepository = mock(ClienteRepository::class.java)
        agendamentoRepository = mock(AgendamentoRepository::class.java)
        etapaRepository = mock(EtapaRepository::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        agendamentoService = AgendamentoService(mapper, agendamentoRepository, clienteRepository, etapaRepository, usuarioRepository)
    }

    val afterUsuario = Usuario(1)

    val listaAgendamento = mutableListOf<Agendamento>(
        Agendamento(1), Agendamento(2), Agendamento(3), Agendamento(4)
    )

    val agendamento = Agendamento(1)

    @DisplayName("Deve retornar uma exceção com código 404 caso a peça não existir.")
    @Test
    fun validateExistenceExpectNotFound(){
        `when`(agendamentoRepository.existsById(anyInt())).thenReturn(false)

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            agendamentoService.validateExistence(1)
        }

        Assertions.assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("listValidation deve retornar uma exceção com código 204 caso a lista esteja vazia")
    @Test
    fun listValidationExpectNoContent(){
        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            agendamentoService.listValidation(listOf<Etapa>())
        }

        Assertions.assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscar deve retornar uma exceção com código 204 caso a lista esteja vazia.")
    @Test
    fun buscarExpectNoContent(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.findByUsuarioIdAndAtivoTrue(anyInt())).thenReturn(listOf())

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java){
            agendamentoService.buscar(1)
        }

        Assertions.assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscarUm deve retornar uma exceção com código 404 caso o Id da etapa não existir")
    @Test
    fun buscarUmExpectNoContent(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.existsById(anyInt())).thenReturn(false)

        val excecao = Assertions.assertThrows(ResponseStatusException::class.java) {
            agendamentoService.buscarUm(1, 1)
        }

        Assertions.assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("buscar não deve retornar uma exceção quando a lista estiver preenchida.")
    @Test
    fun buscarExpectDoesNotThrow(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.findByUsuarioIdAndAtivoTrue(anyInt())).thenReturn(listaAgendamento)

        Assertions.assertDoesNotThrow {
            agendamentoService.buscar(1)
        }

    }

    @DisplayName("buscarUm não deve retornar uma exceção quando possuir um objeto válido")
    @Test
    fun buscarUmExpectDoesNotThrow(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.findByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(agendamento)
        `when`(
            agendamentoRepository.findByUsuarioIdAndIdAndAtivoTrue(
                anyInt(),
                anyInt()
            )
        ).thenReturn(agendamento)

        Assertions.assertDoesNotThrow {
            agendamentoService.buscarUm(1, 1)
        }
    }

    @DisplayName("excluir deve atualizar o campo 'ativo' para 'false'")
    @Test
    fun excluirExpectActiveFalse(){
        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(agendamentoRepository.existsById(anyInt())).thenReturn(true)
        `when`(
            agendamentoRepository.findByUsuarioIdAndIdAndAtivoTrue(
                anyInt(),
                anyInt()
            )
        ).thenReturn(agendamento)
        `when`(agendamentoRepository.save(agendamento)).thenReturn(agendamento)

        val resultado = agendamentoService.excluir(1, 1)

        Assertions.assertEquals(false, resultado.ativo)
    }
    
}