package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.ClienteAtualizarDTO
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteResponse
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*

class ClienteServiceTest {

    lateinit var repository: ClienteRepository
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var usuarioService: UsuarioService
    lateinit var service: ClienteService
    lateinit var mapper: ModelMapper

    val restTemplate = RestTemplate()

    val expectedUser = Usuario(id = 1, senha = "12345678", usuario = "tester")
    val expectedDto = ClienteCadastroDTO(nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = null, usuario = 1)
    val expectedDtoResponsavel = ClienteCadastroDTO(nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = 2, usuario = 1)
    val expectedResponsavel = Cliente(id = 2, nome = "teste2", sobrenome = "teste2", dtNasc = LocalDate.now(), email = "teste2@teste.com", responsavel = null, usuario = expectedUser)
    val expectedResponsavel2 = Cliente(id = 2, nome = "teste3", sobrenome = "teste3", dtNasc = LocalDate.now(), email = "teste3@teste.com", responsavel = null, usuario = expectedUser)
    val expected = Cliente(id = 1, nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = null, usuario = expectedUser)
    val expectedAtualizarDTO = ClienteAtualizarDTO(id = 1, nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = 2)
    val expectedAtualizarDTO2 = ClienteAtualizarDTO(id = 2, nome = "teste3", sobrenome = "teste3", dtNasc = LocalDate.now(), email = "teste3@teste.com", responsavel = null)
    val expectedDisabled = Cliente(id = 1, nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = null, usuario = expectedUser, ativo = false)
    val expectedcResponsavel = Cliente(id = 1, nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = expectedResponsavel, usuario = expectedUser)
    val expectedResponse = ClienteResponse(id = 1, nome = "teste", sobrenome = "teste", dtNasc = LocalDate.now(), email = "teste@teste.com", responsavel = null)
    val expectedList = listOf(expected, expected)
    val expectedListResponse = listOf(expectedResponse, expectedResponse)
    val id = 1

    @BeforeEach
    fun iniciar() {
        mapper = mock(ModelMapper::class.java)
        repository = mock(ClienteRepository::class.java)
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
        service = ClienteService(mapper, repository, usuarioRepository, usuarioService)
    }


    @Test
    fun `Lista estar vazia`() {
        val list = mutableListOf<Endereco>()
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
    fun `Existe um cliente com esse id`() {
        `when`(repository.existsById(id))
            .thenReturn(true)
        val validar = service.idValidation(id)
        assertEquals(validar, true)
    }

    @Test
    fun `Não existe um cliente com esse id`() {
        `when`(repository.existsById(id))
            .thenReturn(false)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.idValidation(id)
        }
        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Existe um usuario com esse id`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        val validar = service.usuarioValidation(id)
        assertEquals(validar, true)
    }

    @Test
    fun `Não existe um usuario com esse id`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(false)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.idValidation(id)
        }
        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Teste cadastrando um cliente com um usuario que existe`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        `when`(service.mapper.map(expectedDto, Cliente::class.java))
            .thenReturn(expected)
        `when`(usuarioRepository.findById(id))
            .thenReturn(Optional.of(expectedUser))
        `when`(repository.save(expected))
            .thenReturn(expected)

         val teste = service.cadastrar(expectedDto)

        assertEquals(teste, expected)
    }

    @Test
    fun `Teste cadastrando um cliente com um usuario que existe com responsável`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        `when`(service.mapper.map(expectedDtoResponsavel, Cliente::class.java))
            .thenReturn(expectedcResponsavel)
        `when`(usuarioRepository.findById(id))
            .thenReturn(Optional.of(expectedUser))
        `when`(repository.existsById(2))
            .thenReturn(true)
        `when`(repository.findById(2))
            .thenReturn(Optional.of(expectedResponsavel))
        `when`(repository.save(expectedcResponsavel))
            .thenReturn(expectedcResponsavel)

        val teste = service.cadastrar(expectedDtoResponsavel)

        assertEquals(teste, expectedcResponsavel)
    }

    @Test
    fun `Teste cadastrando um cliente com um usuario que existe mas responsalvel não`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(true)
        `when`(service.mapper.map(expectedDtoResponsavel, Cliente::class.java))
            .thenReturn(expectedcResponsavel)
        `when`(usuarioRepository.findById(id))
            .thenReturn(Optional.of(expectedUser))
        `when`(repository.save(expectedResponsavel))
            .thenReturn(expectedResponsavel)
        `when`(repository.existsById(id))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.cadastrar(expectedDtoResponsavel)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Teste cadastrando cliente com um usuario que não existe`() {
        `when`(usuarioRepository.existsById(id))
            .thenReturn(false)
        `when`(service.mapper.map(expectedDto, Cliente::class.java))
            .thenReturn(expected)


        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.cadastrar(expectedDto)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Buscar um cliente que existe`() {
        `when`(repository.existsById(id))
            .thenReturn(true)
        `when`(repository.findById(id))
            .thenReturn(Optional.of(expected))
        `when`(repository.save(expected))
            .thenReturn(expected)
        `when`(service.mapper.map(expected, ClienteResponse::class.java))
            .thenReturn(expectedResponse)
        val teste = service.buscarUmCliente(id)
        assertEquals(teste, expectedResponse)
    }

    @Test
    fun `Buscar um cliente que não existe`() {
        `when`(repository.existsById(id))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.buscarUmCliente(id)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Buscar todos os clientes`() {

        `when`(repository.findByUsuarioId(id))
            .thenReturn(expectedList)
        `when`(mapper.map(expected, ClienteResponse::class.java))
            .thenReturn(expectedResponse)
        val teste = service.buscarClientes(id)
        assertEquals(teste, expectedListResponse)
    }

    @Test
    fun `Buscar todos os clientes deu errado`() {

        `when`(repository.findByUsuarioId(id))
            .thenReturn(listOf())

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.buscarClientes(id)
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @Test
    fun `desativarClientePorId deu certo`() {
        `when`(repository.existsById(id))
            .thenReturn(true)
        `when`(repository.findById(id))
            .thenReturn(Optional.of(expected))
        `when`(repository.save(expectedDisabled))
            .thenReturn(expectedDisabled)
        val teste = service.desativarClientePorId(id)
        assertEquals(teste, expectedDisabled)
    }

    @Test
    fun `desativarClientePorId não deu certo`() {
        `when`(repository.existsById(id))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.desativarClientePorId(id)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `atualizarCliente deu certo adicionar responsavel`() {
        `when`(repository.existsById(id))
            .thenReturn(true)
        `when`(repository.findById(id))
            .thenReturn(Optional.of(expected))
        `when`(repository.existsById(2))
            .thenReturn(true)
        `when`(repository.findById(2))
            .thenReturn(Optional.of(expectedResponsavel))
        `when`(repository.save(expectedcResponsavel))
            .thenReturn(expectedcResponsavel)
        val teste = service.atualizarCliente(expectedAtualizarDTO)
        assertEquals(teste, expectedcResponsavel)
    }

    @Test
    fun `atualizarCliente deu errado adicionar responsavel que não existe`() {
        `when`(repository.existsById(id))
            .thenReturn(true)
        `when`(repository.findById(id))
            .thenReturn(Optional.of(expected))
        `when`(repository.existsById(2))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.atualizarCliente(expectedAtualizarDTO)
        }

        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `atualizarCliente deu certo sem adicionar responsavel`() {
        `when`(repository.existsById(2))
            .thenReturn(true)
        `when`(repository.findById(2))
            .thenReturn(Optional.of(expectedResponsavel))
        `when`(repository.save(expectedResponsavel2))
            .thenReturn(expectedResponsavel2)

        val teste = service.atualizarCliente(expectedAtualizarDTO2)
        assertEquals(teste, expectedResponsavel2)
    }

    @Test
    fun `atualizarCliente deu errado pois usuario não existe`() {
        `when`(repository.existsById(2))
            .thenReturn(false)

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.atualizarCliente(expectedAtualizarDTO2)
        }

        assertEquals(404, excecao.statusCode.value())
    }
}