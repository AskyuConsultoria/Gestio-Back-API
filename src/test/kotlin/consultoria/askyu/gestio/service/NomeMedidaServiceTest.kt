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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException
import java.util.*

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
        mapper = mock(ModelMapper::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        clienteRepository = mock(ClienteRepository::class.java)
        clienteService = ClienteService(mapper, clienteRepository, usuarioRepository, usuarioService)
        pecaRepository = mock(PecaRepository::class.java)
        pecaService = PecaService(pecaRepository, usuarioRepository, usuarioService, mapper)
        nomeMedidaRepository = mock(NomeMedidaRepository::class.java)
        nomeMedidaService = NomeMedidaService(nomeMedidaRepository, pecaService, usuarioService, mapper)
    }

    @DisplayName("Deve retornar uma exceção com código 404 caso o nome de medida não existir")
    @Test
    fun existenceValidationReturnsResponseNotFound() {
        val usuarioId = 1
        val pecaId = 1
        val nomeMedidaId = 1

        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            nomeMedidaService.validarSeNomeMedidaExiste(usuarioId, pecaId, nomeMedidaId)
        }

        assertEquals(404, excecao.statusCode.value())
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

        val novoNomeMedida = NomeMedidaCadastroRequest( "L. Cintura", 2, 2)
        val nomeMedidaMapeado = NomeMedida( 1,"L. Cintura", beforePeca, beforeUsuario, true)
        val esperado = NomeMedida( 1,"L. Cintura", afterPeca, afterUsuario, true)


        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(afterUsuario))
        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.findById(anyInt())).thenReturn(Optional.of(afterPeca))
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(true)
        `when`(mapper.map(novoNomeMedida, NomeMedida::class.java)).thenReturn(nomeMedidaMapeado)
        `when`(nomeMedidaRepository.save(nomeMedidaMapeado)).thenAnswer {
            invocation ->
            val nomeMedida = invocation.getArgument(0, NomeMedida::class.java)
            nomeMedida
        }

        val resultado = nomeMedidaService.postByUsuarioIdAndPecaId(1, 1, novoNomeMedida)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)
        assertEquals(esperado.ativo, resultado.ativo)
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
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndAtivoIsTrue(usuarioId, pecaId)).thenReturn(listaEsperada)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            nomeMedidaService.getAllByUsuarioIdAndPecaId(usuarioId, pecaId)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @DisplayName("getallByUsuarioIdAndPecaId deve retornar uma lista de nome de medida")
    @Test
    fun getallByUsuarioIdAndPecaIdExpectListOfNomeMedida(){
        val usuarioId = 1
        val clienteId = 1
        val pecaId = 1

        val peca = Peca(1)
        val usuario = Usuario(1)

        val listaEsperada = listOf<NomeMedida>(
            NomeMedida( 1,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 2,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 3,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 4,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 5,"L. Cintura", peca, usuario, ativo = true),
        )

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndAtivoIsTrue(usuarioId, pecaId)).thenReturn(listaEsperada)

       val resultado = nomeMedidaService.getAllByUsuarioIdAndPecaId(usuarioId, pecaId)

        assertInstanceOf(NomeMedida::class.java, resultado[0])
        assertEquals(listaEsperada.size, resultado.size)
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
            nomeMedidaRepository.getByUsuarioIdAndPecaIdAndNomeContainsIgnoreCaseAndAtivoIsTrue(
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

    @DisplayName("getAllByUsuarioIdAndPecaIdAndNomeContains deve retornar uma lista de nome de medida")
    @Test
    fun getAllByUsuarioIdAndPecaIdAndNomeContainsExpectListOfNomeMedida(){
        val usuarioId = 1
        val clienteId = 1
        val pecaId = 1

        val peca = Peca(1)
        val usuario = Usuario(1)
        val nome = "calca"

        val listaEsperada = listOf<NomeMedida>(
            NomeMedida( 1,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 2,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 3,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 4,"L. Cintura", peca, usuario, ativo = true),
            NomeMedida( 5,"L. Cintura", peca, usuario, ativo = true),
        )

        `when`(usuarioRepository.existsById(usuarioId)).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(usuarioId, pecaId)).thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndNomeContainsIgnoreCaseAndAtivoIsTrue(usuarioId, pecaId, nome)).thenReturn(listaEsperada)

        val resultado = nomeMedidaService.getAllByUsuarioIdAndPecaIdAndNomeContains(usuarioId, pecaId, nome)

        assertInstanceOf(NomeMedida::class.java, resultado[0])
        assertEquals(listaEsperada.size, resultado.size)
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
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(usuarioId, pecaId, nomeMedidaId))
            .thenReturn(nomeMedida)

        val resultado = nomeMedidaService.getByUsuarioIdAndPecaIdAndId(usuarioId, pecaId, nomeMedidaId)

        assertEquals(nomeMedida.usuario!!.id, resultado.usuario!!.id)
        assertEquals(nomeMedida.peca!!.id, resultado.peca!!.id)
        assertEquals(nomeMedida.id, resultado.id)
    }

    @DisplayName("putByUsuarioIdAndPecaIdAndId deve retonar o objeto de saída com os parâmetros do endpoint")
    @Test
    fun putByUsuarioIdAndPecaIdAndIdExpectEqualIO(){
        val beforePeca = Peca(2)
        val beforeUsuario = Usuario(2)
        val afterPeca = Peca(1)
        val afterUsuario = Usuario(1)

        val nomeMedidaAtualizado = NomeMedidaCadastroRequest( "L. Cintura", 2, 2)
        val nomeMedidaMapeado = NomeMedida( 2,"L. Cintura", beforePeca, beforeUsuario)
        val esperado = NomeMedida( 1,"L. Cintura", afterPeca, afterUsuario)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(anyInt(), anyInt(), anyInt()))
            .thenReturn(true)
        `when`(nomeMedidaService.mapper.map(nomeMedidaAtualizado, NomeMedida::class.java)).thenReturn(nomeMedidaMapeado)
        `when`(nomeMedidaRepository.save(nomeMedidaMapeado)).thenAnswer {
            invocation ->
            val nomeMedida = invocation.getArgument(0, NomeMedida::class.java)
            nomeMedida
        }

        val resultado = nomeMedidaService.putByUsuarioIdAndPecaIdAndId(1, 1, 1, nomeMedidaAtualizado)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)
        assertEquals(esperado.id, resultado.id)
    }

    @DisplayName("deleteByUsuarioIdAndPecaIdAndId deve excluir a linha associadas ao parâmetro do endpoint")
    @Test
    fun deleteByUsuarioIdAndPecaIdAndIdExpectEqualIO(){
        val beforePeca = Peca(2)
        val beforeUsuario = Usuario(2)

        val afterPeca = Peca(1)
        val afterUsuario = Usuario(1)

        val nomeMedidaASerDeletado = NomeMedida( 1,"L. Cintura", beforePeca, beforeUsuario)
        val esperado = NomeMedida( 1,"L. Cintura", afterPeca, afterUsuario, false)

        `when`(usuarioRepository.existsById(anyInt())).thenReturn(true)
        `when`(pecaRepository.existsByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(true)
        `when`(nomeMedidaRepository.existsByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(anyInt(), anyInt(), anyInt()))
            .thenReturn(true)
        `when`(nomeMedidaRepository.getByUsuarioIdAndPecaIdAndIdAndAtivoIsTrue(anyInt(), anyInt(), anyInt()))
            .thenReturn(nomeMedidaASerDeletado)
        `when`(nomeMedidaRepository.save(nomeMedidaASerDeletado)).thenAnswer {
            invocation ->
            val nomeMedida = invocation.getArgument(0, NomeMedida::class.java)
            nomeMedida
        }

        val resultado = nomeMedidaService.deleteByUsuarioIdAndPecaIdAndId(1, 1, 1)

        assertEquals(esperado.usuario!!.id, resultado.usuario!!.id)
        assertEquals(esperado.peca!!.id, resultado.peca!!.id)
        assertEquals(esperado.id, resultado.id)
        assertEquals(esperado.ativo, resultado.ativo)
    }


}