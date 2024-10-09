package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.repository.EnderecoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.modelmapper.ModelMapper
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import java.util.*

class EnderecoServicoTest {


    lateinit var repository: EnderecoRepository
    lateinit var service: EnderecoService
    lateinit var usuarioService: UsuarioService
    lateinit var clienteService: ClienteService
    lateinit var mapper: ModelMapper

    val restTemplate = RestTemplate()

    val expected = Endereco(id = 1, bairro = "Vila Darli", cep = "03262-080", localidade = "São Paulo", logradouro = "Rua Doutor Arnaldo Barbosa", uf = "SP")

    val id = 1
    val cep = "03262080"

    @BeforeEach
    fun iniciar() {
        repository = mock(EnderecoRepository::class.java)
        mapper = mock(ModelMapper::class.java)
        service = consultoria.askyu.gestio.service.EnderecoService(repository, usuarioService, clienteService, mapper)
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
    fun `Existe um cep assim`() {
        `when`(repository.existsById(id))
            .thenReturn(true)
        val validar = service.existenceValidation(id)
        assertEquals(validar, true)
    }

    @Test
    fun `Não existe um cep assim`() {
        `when`(repository.existsById(id))
            .thenReturn(false)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.existenceValidation(id)
        }
        assertEquals(404, excecao.statusCode.value())
    }

    @Test
    fun `Esse cep é unico`() {
        `when`(repository.countByCep(cep))
            .thenReturn(0)
        assertDoesNotThrow {
            service.validarCepExiste(cep)
        }

    }

    @Test
    fun `Esse cep não é unico`() {
        `when`(repository.countByCep(cep))
            .thenReturn(1)
        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.validarCepExiste(cep)
        }
        assertEquals(409, excecao.statusCode.value())
    }

    @Test
    fun `Achou todos os enderecos`() {
        `when`(repository.findAll())
            .thenReturn(listOf(expected,expected,expected,expected))

        val teste = service.listar()

        assertDoesNotThrow {
            service.listValidation(teste)
        }
    }

    @Test
    fun `Não achou nenhum endereco`() {
        `when`(repository.findAll())
            .thenReturn(listOf<Endereco>())

        val excecao = assertThrows(ResponseStatusException::class.java) {
            service.listar()
        }

        assertEquals(204, excecao.statusCode.value())
    }

    @Test
    fun `buscar encontrou um endereco com base no cep`() {
        `when`(repository.findByCep(cep))
            .thenReturn(Optional.of(expected))
        val teste = service.buscar(cep)
        assertEquals(expected, teste.body)
    }

    @Test
    fun `não encontrou nenhum usuario com base no cep`() {
        `when`(repository.findByCep(cep))
            .thenReturn(Optional.empty())
        val teste = service.buscar(cep)
        assertEquals(404, teste.statusCode.value())
    }
}