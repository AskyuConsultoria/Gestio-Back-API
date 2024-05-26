package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.NomeMedida
import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.NomeMedidaCadastroRequest
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.NomeMedidaRepository
import consultoria.askyu.gestio.repository.PecaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException

class NomeMedidaServiceTest {
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var clienteRepository: ClienteRepository
    lateinit var clienteService: ClienteService
    lateinit var pecaRepository: PecaRepository
    lateinit var pecaService: PecaService
    lateinit var nomeMedidaRepository: NomeMedidaRepository
    lateinit var nomeMedidaService: NomeMedidaService
    lateinit var mapper: ModelMapper

    @BeforeEach
    fun iniciar() {
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteService(clienteRepository, usuarioService)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioService)
        nomeMedidaRepository = mock(NomeMedidaRepository::class.java)
        mapper = mock(ModelMapper::class.java)
        nomeMedidaService = NomeMedidaService(nomeMedidaRepository, pecaService, usuarioService, mapper)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o nome de medida não existir")
    @Test
    fun existenceValidationReturnsResponseNotFound() {
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            nomeMedidaService.validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @DisplayName("getallByUsuarioIdAndPecaId deve retornar uma exceção com código 204 caso a lista esteja vazia.")
    @Test
    fun getallByUsuarioIdAndPecaIdExpectNoContent() {
        val usuarioId = 1
        val clienteId = 1
        val pecaId = 1

        val listaEsperada = listOf<NomeMedida>()

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaId(usuarioId, pecaId)).thenReturn(listaEsperada)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            nomeMedidaService.getAllByUsuarioIdAndPecaId(usuarioId, pecaId)
        }

        assertEquals(204, excecao.statusCode.value())
    }



    @DisplayName("getAllByUsuarioIdAndPecaIdAndNomeContains deve retornar uma exeção com código 204 caso a lista esteja vazia.")
    @Test
    fun getAllByUsuarioIdAndPecaIdAndNomeContainsExpectNoContent() {
        val usuarioId = 1
        val pecaId = 1
        val nome = "Altura"

        val listaEsperada = listOf<NomeMedida>()

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(
            nomeMedidaRepository.getByUsuarioIdAndPecaIdAndNomeContainsIgnoreCase(
                usuarioId,
                pecaId,
                nome
            )
        ).thenReturn(listaEsperada)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            nomeMedidaService.getAllByUsuarioIdAndPecaIdAndNomeContains(usuarioId, pecaId, nome)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("getByUsuarioIdAndPecaIdAndId deve retornar o objeto de saída com os parâmetros do endpoint.")
    @Test
    fun getByUsuarioIdAndPecaIdAndIdExpectEqualIO() {
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        val peca = Peca(1)
        val usuario = Usuario(1)

        val nomeMedida = NomeMedida(1, "L. Cintura", peca, usuario, true)

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(nomeMedida)

        val resultado = nomeMedidaService.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)

        assertEquals(nomeMedida.usuario!!.id, resultado.usuario!!.id)
        assertEquals(nomeMedida.peca!!.id, resultado.peca!!.id)
        assertEquals(nomeMedida.id, resultado.id)
    }

    @DisplayName("postByUsuarioIdAndPecaId deve retonar o objeto de saída com os parâmetros do endpoint")
    @Test
    fun postByUsuarioIdAndPecaIdExpectEqualIO(){
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        val beforePeca = Peca(2)
        val beforeUsuario = Usuario(2)
        val afterPeca = Peca(1)
        val afterUsuario = Usuario(1)

        val novoNomeMedida = NomeMedidaCadastroRequest( "L. Cintura", beforePeca, beforeUsuario)
        val nomeMedida = NomeMedida( 1,"L. Cintura", afterPeca, afterUsuario, true)

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(true)
        `when`(mapper.map(novoNomeMedida, NomeMedida::class.java)).thenReturn(nomeMedida)
        `when`(nomeMedidaRepository.save(nomeMedida)).thenReturn(nomeMedida)

        val resultado = nomeMedidaService.postByUsuarioIdAndPecaId(usuarioId, pecaId, novoNomeMedida)

        assertEquals(nomeMedida.usuario!!.id, resultado.usuario!!.id)
        assertEquals(nomeMedida.peca!!.id, resultado.peca!!.id)
        assertEquals(nomeMedida.id, resultado.id)
        assertEquals(nomeMedida.ativo, resultado.ativo)
    }

    @DisplayName("putByUsuarioIdAndPecaIdAndId deve retonar o objeto de saída com os parâmetros do endpoint")
    @Test
    fun putByUsuarioIdAndPecaIdAndIdExpectEqualIO(){
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        val beforePeca = Peca(2)
        val beforeUsuario = Usuario(2)
        val afterPeca = Peca(1)
        val afterUsuario = Usuario(1)

        val nomeMedidaAtualizado = NomeMedidaCadastroRequest( "L. Cintura", beforePeca, beforeUsuario)
        val nomeMedida = NomeMedida( 1,"L. Cintura", afterPeca, afterUsuario, true)

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(true)
        `when`(mapper.map(nomeMedidaAtualizado, NomeMedida::class.java)).thenReturn(nomeMedida)
        `when`(nomeMedidaRepository.save(nomeMedida)).thenReturn(nomeMedida)

        val resultado = nomeMedidaService.putByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId, nomeMedidaAtualizado)

        assertEquals(nomeMedida.usuario!!.id, resultado.usuario!!.id)
        assertEquals(nomeMedida.peca!!.id, resultado.peca!!.id)
        assertEquals(nomeMedida.id, resultado.id)
    }

    @DisplayName("deleteByUsuarioIdAndPecaIdAndId deve excluir a linha associadas ao parâmetro do endpoint")
    @Test
    fun deleteByUsuarioIdAndPecaIdAndId(){
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        val peca = Peca(1)
        val usuario = Usuario(1)

        val nomeMedida = NomeMedida( 1,"L. Cintura", peca, usuario, ativo = true)


        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(nomeMedida)
        `when`(nomeMedidaRepository.save(nomeMedida)).thenReturn(nomeMedida)

        val resultado = nomeMedidaService.deleteByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)

        assertEquals(nomeMedida.usuario!!.id, resultado.usuario!!.id)
        assertEquals(nomeMedida.peca!!.id, resultado.peca!!.id)
        assertEquals(nomeMedida.id, resultado.id)
        assertEquals(nomeMedida.ativo, false)

    }


}