package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.UsuarioCadastroDTO
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.server.ResponseStatusException
import java.util.*

class UsuarioServiceTest {

    lateinit var usuarioRepository: UsuarioRepository
    lateinit var service: UsuarioService
    lateinit var mapper: ModelMapper

    val expected = Usuario(id = 1, usuario = "Hungareio", senha = "12345678")
    val expectedDto = UsuarioCadastroDTO(usuario = "Hungareio", senha = "12345678")
    val expectedDisabled = Usuario(id = 1, usuario = "Hungareio", senha = "12345678", ativo = false)
    val expectedLogged = Usuario(id = 1, usuario = "Hungareio", senha = "12345678", autenticado = true)

    val id = 1

    @BeforeEach
    fun iniciar() {
        usuarioRepository = mock(UsuarioRepository::class.java)
        mapper = mock(ModelMapper::class.java)
        service = UsuarioService(usuarioRepository, mapper)
    }


    @Test
    fun `Lista estar vazia`() {
        val list = mutableListOf<Usuario>()
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.listValidation(list)
        }
        assertEquals(204, excecao.statusCode.value())
    }

    @Test
    fun `Lista está com coisa`() {
        val list = listOf(expected)

        assertDoesNotThrow {
            service.listValidation(list)
        }
    }

    @Test
    fun `Existe um cadastro assim`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        val validar = service.existenceValidation(id)
        assertEquals(validar, true)
    }

    @Test
    fun `Não existe um cadastro assim`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(false)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.existenceValidation(id)
        }
        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Esse item é unico`() {
        `when`(usuarioRepository.countByUsuario(expected.usuario))
            .thenReturn(0)
        val teste = service.uniqueValidation(expected)
        assertEquals(teste, true)
    }

    @Test
    fun `Esse item não é unico`() {
        `when`(usuarioRepository.countByUsuario(expected.usuario))
            .thenReturn(1)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.uniqueValidation(expected)
        }
        assertEquals(409, excecao.statusCode.value())
    }

    @Test
    fun `Teste cadastrando um usuário`() {

        `when`(service.mapper.map(expectedDto, Usuario::class.java))
            .thenReturn(expected)
        `when`(usuarioRepository.save(expected))
            .thenReturn(expected)

        val novoUsuario = service.cadastrar(expectedDto)

        assertEquals(novoUsuario, expected)
    }

    @Test
    fun `Teste cadastrando usuario que ja existe`() {
        `when`(usuarioRepository.countByUsuario(expected.usuario))
            .thenReturn(1)

        `when`(service.mapper.map(expectedDto, Usuario::class.java))
            .thenReturn(expected)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.cadastrar(expectedDto)
        }

        assertEquals(409, excecao.statusCode.value())
    }

    @Test
    fun `Desativando um usuário`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        `when`(usuarioRepository.findById(id))
            .thenReturn(Optional.of(expected))
        `when`(usuarioRepository.save(expectedDisabled))
            .thenReturn(expectedDisabled)

        val desativado = service.desativarUsuario(id)

        assertEquals(desativado, expectedDisabled)
    }

    @Test
    fun `Falhando em desativando um usuário pois não existe`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.desativarUsuario(id)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Logado com sucesso`() {
        `when`(usuarioRepository.countByUsuarioAndSenhaAndAtivoIsTrue(expected.usuario, expected.senha))
            .thenReturn(1)
        `when`(usuarioRepository.findByUsuarioAndSenha(expected.usuario, expected.senha))
            .thenReturn(expected)
        `when`(usuarioRepository.save(expectedLogged))
            .thenReturn(expectedLogged)

        val teste = service.logar(expected.usuario,expected.senha)

        assertEquals(expectedLogged, teste)
    }

    @Test
    fun `Falha no login pois o usuario esta desativado`() {
        `when`(usuarioRepository.countByUsuarioAndSenhaAndAtivoIsTrue(expected.usuario, expected.senha))
            .thenReturn(0)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.logar(expected.usuario,expected.senha)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Falha no login pois o usuario esta logado`() {
        `when`(usuarioRepository.countByUsuarioAndSenhaAndAtivoIsTrue(expected.usuario, expected.senha))
            .thenReturn(1)
        `when`(usuarioRepository.findByUsuarioAndSenha(expected.usuario, expected.senha))
            .thenReturn(expectedLogged)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.logar(expected.usuario,expected.senha)
        }

        assertEquals(409, excecao.statusCode.value())
    }

    @Test
    fun `Deslogado com sucesso`() {
        `when`(usuarioRepository.findByUsuario(expectedLogged.usuario))
            .thenReturn(expectedLogged)
        `when`(usuarioRepository.save(expected))
            .thenReturn(expected)

        val teste = service.deslogar(expected.usuario)

        assertEquals(expected, teste)
    }

    @Test
    fun `Falha no login pois o usuario não esta logado`() {
        `when`(usuarioRepository.findByUsuario(expected.usuario))
            .thenReturn(expected)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.deslogar(expected.usuario)
        }

        assertEquals(400, excecao.statusCode.value())
    }

    @Test
    fun `Obteve as informações do usuário`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        `when`(usuarioRepository.findById(id))
            .thenReturn(Optional.of(expected))
        val teste = service.obterInfo(id)
        assertEquals(expected, teste)
    }

    @Test
    fun `Não encontrou o usuario no obterInformações`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.obterInfo(id)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Achou todos os usuário`() {
        `when`(usuarioRepository.findAll())
            .thenReturn(listOf(expected,expected,expected,expected))

        val teste = service.findAll()

        assertDoesNotThrow {
            service.listValidation(teste)
        }
    }

    @Test
    fun `Não achou nenhum usuario`() {
        `when`(usuarioRepository.findAll())
            .thenReturn(listOf<Usuario>())

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.findAll()
        }

        assertEquals(204, excecao.statusCode.value())
    }
}