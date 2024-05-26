package consultoria.askyu.gestio.service
import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.PecaCadastroRequest
import consultoria.askyu.gestio.repository.PecaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException
import java.util.*

class PecaServiceTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService
    lateinit var mapper: ModelMapper

    @BeforeEach
    fun iniciar(){
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        pecaRepository = mock(PecaRepository::class.java)
        mapper = mock(ModelMapper::class.java)
        pecaService = PecaService(pecaRepository, usuarioService, mapper)
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

    @DisplayName("getAllByUsuarioId deve retornar uma exceção com código 204 caso a lista esteja vazia.")
    @Test
    fun getAllByUsuarioIdExpectNoContent(){
        val usuarioId = 1
        val listaEsperada = listOf<Peca>()

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.findByUsuarioId(usuarioId)).thenReturn(listaEsperada)

        val excecao = assertThrows(ResponseStatusException::class.java){
            pecaService.getAllByUsuarioId(usuarioId)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("getByUsuarioIdAndId deve retornar um objeto de peça")
    @Test
    fun getByUsuarioIdExpectPeca(){
        val usuarioId = 1
        val pecaId = 1

        val usuario = Usuario(usuarioId)
        val peca =
            Peca(
                1,
                "calça",
                "uma calça com medidas padrozinadas",
                usuario,
                ativo = true
            )

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(pecaRepository.findByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(Optional.of(peca))

        val resultado = pecaService.getByUsuarioIdAndId(usuarioId, pecaId)

        assertInstanceOf(Peca::class.java, resultado)
    }

    @DisplayName("getByUsuarioIdAndNome deve retornar uma exceção com código 204 quando a lista estiver vazia.")
    @Test
    fun getByUsuarioIdAndNomeExpectNoContent(){
        val usuarioId = 1
        val pecaId = 1
        val nome = "calca"

        val listaEsperada = listOf<Peca>()

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.findByUsuarioIdAndNomeContainsIgnoreCase(usuarioId, nome)).thenReturn(listaEsperada)

        val excecao = assertThrows(ResponseStatusException::class.java){
            pecaService.getByUsuarioIdAndNome(usuarioId, nome)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("getByUsuarioIdAndNome deve retornar uma lista de peças")
    @Test
    fun getByUsuarioIdAndNomeExpectListOfPeca(){
        val usuarioId = 1
        val pecaId = 1

        val nome = "terno"
        val usuario = Usuario(usuarioId)
        val listaEsperada = listOf<Peca>(
            Peca(pecaId, "terno", "uma terno padrão", usuario, ativo = true)
        )


        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.findByUsuarioIdAndNomeContainsIgnoreCase(usuarioId, nome)).thenReturn(listaEsperada)

        val resultado = pecaService.getByUsuarioIdAndNome(usuarioId, nome)

        assertInstanceOf(Peca::class.java, resultado[0])
        assertEquals(listaEsperada.size, resultado.size)
    }

    @DisplayName("postByUsuarioId deve retornar o objeto de saída com os valores dos parâmetros.")
    @Test
    fun postByUsuarioId(){
        val usuarioId = 1

        val beforeUsuario = Usuario(2)
        val afterUsuario = Usuario(1)

        val novaPeca = PecaCadastroRequest(
             "terno", "um terno padrão", beforeUsuario
        )

        val pecaMapeada = Peca(
            1, "terno", "um terno padrão", beforeUsuario)

        val esperado = Peca(
            1, "terno", "um terno padrão", afterUsuario)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pecaService.mapper.map(novaPeca, Peca::class.java)).thenReturn(pecaMapeada)
        `when`(pecaRepository.save(any(Peca::class.java))).thenAnswer {
            invocation ->
            val peca = invocation.getArgument(0, Peca::class.java)
            peca
        }

        val resultado = pecaService.postByUsuarioId(1, novaPeca)
        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(true, resultado.ativo)
}

}
