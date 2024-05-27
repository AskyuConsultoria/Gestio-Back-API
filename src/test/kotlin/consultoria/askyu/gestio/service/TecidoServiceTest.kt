package consultoria.askyu.gestio.service

import askyu.gestio.dto.TecidoCadastroRequest
import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.TecidoRepository
import consultoria.askyu.gestio.TecidoService
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class TecidoServiceTest{

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var tecidoRepository:TecidoRepository
    lateinit var tecidoService: TecidoService
    lateinit var mapper: ModelMapper

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        tecidoRepository = mock(TecidoRepository::class.java)
        mapper = mock(ModelMapper::class.java)
        tecidoService = TecidoService(usuarioService, tecidoRepository, mapper)
    }

    @DisplayName("Deve retornar uma exceção com código 404 se o tecido não existir")
    @Test
    fun existenceValidationNotFound(){
        val usuarioId = 1
        val tecidoId = 1

        `when`(tecidoRepository.existsByUsuarioIdAndId(usuarioId, tecidoId)).thenReturn(false)

      val excecao = assertThrows(ResponseStatusException::class.java){
          tecidoService.existenceValidation(usuarioId, tecidoId)
      }

        assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("salvar deve possuir no objeto de saída os valores dos parâmetros do endpoint.")
    @Test
    fun salvarExpectEqualIO(){
        val beforeUsuario = Usuario(2)
        val afterUsuario = Usuario(1)

        val novoTecido = TecidoCadastroRequest(2, "Cetim", beforeUsuario)
        val tecidoMapeado = Tecido(2, "Cetim", beforeUsuario)
        val esperado = Tecido(1, "Cetim", afterUsuario)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(tecidoService.mapper.map(novoTecido, Tecido::class.java)).thenReturn(tecidoMapeado)
        `when`(tecidoRepository.save(tecidoMapeado)).thenReturn(tecidoMapeado)

        val resultado = tecidoService.salvar(1, 1, novoTecido)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.id, resultado.id)

    }

    @DisplayName("atualizar deve possuir no objeto de saída os valores dos parâmetros do endpoint.")
    @Test
    fun atualizarExpectEqualIO(){
        val beforeUsuario = Usuario(2)
        val afterUsuario = Usuario(1)

        val novoTecido = TecidoCadastroRequest(2, "Cetim", beforeUsuario)
        val tecidoMapeado = Tecido(2, "Cetim", beforeUsuario)
        val esperado = Tecido(1, "Cetim", afterUsuario)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(tecidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(tecidoService.mapper.map(novoTecido, Tecido::class.java)).thenReturn(tecidoMapeado)
        `when`(tecidoRepository.save(tecidoMapeado)).thenReturn(tecidoMapeado)

        val resultado = tecidoService.atualizar(1, 1, novoTecido)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.id, resultado.id)
    }

    @DisplayName("listar deve retornar uma exceção com código 204 quando a lista estiver vazia")
    @Test
    fun listarExpectNoContent(){
        val listaEsperada = listOf<Tecido>()

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)

        val excecao = assertThrows(ResponseStatusException::class.java){
            tecidoService.listar(1)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("listarPorNome deve retornar uma exceção com código 204 quando a lista estiver vazia")
    @Test
    fun listarPorNomeExpectNoContent(){
        val listaEsperada = listOf<Tecido>()

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)

        val excecao = assertThrows(ResponseStatusException::class.java){
            tecidoService.listarPorNome(1, "Cetim")
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("buscarPorTecidoId deve retornar um tecido.")
    @Test
    fun buscarPorTecidoIdExpectTecido(){
        val usuario = Usuario(1)
        val esperado = Tecido(1, "Cetim", usuario)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(tecidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(tecidoRepository.findByUsuarioIdAndId(1, 1)).thenReturn(esperado)

        val resultado = tecidoService.buscarTecidoPorId(1, 1)

        assertInstanceOf(Tecido::class.java, resultado)
    }

    @DisplayName("desativar deve possuir no objeto de saída os valores dos parâmetros do endpoint.")
    @Test
    fun desativarExpectEqualIO(){
        val beforeUsuario = Usuario(2)
        val afterUsuario = Usuario(1)

        val tecidoASerDeletado = Tecido(2, "Cetim", beforeUsuario)
        val esperado = Tecido(1, "Cetim", afterUsuario, false)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(tecidoRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(tecidoRepository.findByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(tecidoASerDeletado)
        `when`(tecidoRepository.save(tecidoASerDeletado)).thenReturn(tecidoASerDeletado)

        val resultado = tecidoService.desativar(1, 1)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.id, resultado.id)
        assertEquals(esperado.ativo, resultado.ativo)
    }

    @DisplayName("validarSeListaEVazia deve retornar uma exceção com código 204 quando receber uma lista vazia.")
    @Test
    fun validarSeListaEVaziaExpectNoContent(){
        val listaEsperada = listOf<Tecido>()

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)

        val excecao = assertThrows(ResponseStatusException::class.java){
            tecidoService.validarSeListaEVazia(listaEsperada)
        }

        assertEquals(204, excecao.statusCode.value())
    }
}