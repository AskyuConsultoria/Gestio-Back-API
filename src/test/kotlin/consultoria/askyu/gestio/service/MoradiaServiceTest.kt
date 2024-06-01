package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.MoradiaResponse
import consultoria.askyu.gestio.repository.MoradiaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDate

class MoradiaServiceTest {
    lateinit var repository: MoradiaRepository
    lateinit var service: MoradiaService


    @BeforeEach
    fun iniciar() {
        repository = mock(MoradiaRepository::class.java)
        service = MoradiaService(repository)
    }

    @DisplayName("Se houver moradias, irá retorna-las")
    @Test
    fun getLista() {
        val listaEsperada = listOf(
            Moradia(
                id = 1,
                endereco = Endereco(
                    id = 1,
                    cep = "06333-350",
                    logradouro = "Penapolis",
                    bairro = "Santa Brígida",
                    localidade = "Carapicuíba",
                    uf = "SP"
                ),
                usuario = Usuario(
                    id = 1,
                    usuario = "gabriella",
                    senha = "12345678",
                    autenticado = false,
                    ativo = true
                ),
                cliente = Cliente(
                    id = 1,
                    nome = "Sarah",
                    sobrenome = "Oliveira",
                    dtNasc = LocalDate.of(2004, 8, 27),
                    email = "sarah.oliveira@sptech.school",
                    responsavel = null,
                    usuario = 1,
                    ativo = true
                ),
                complemento = "apt2",
                numero = 702,
                ativo = true
            )
        )

        Mockito.`when`(repository.findMoradia())
            .thenReturn(listaEsperada)

        val resultado = service.getLista()

        assertEquals(listaEsperada.size, resultado.size)

        resultado.forEachIndexed { index, moradiaResponse ->
            val moradia = listaEsperada[index]
            validarMoradia(moradia, moradiaResponse)
        }
    }

    fun validarMoradia(moradia: Moradia, moradiaResponse: MoradiaResponse) {
        assertEquals(moradia.id, moradiaResponse.id)
        assertEquals(moradia.endereco?.id, moradiaResponse.endereco?.id)
        assertEquals(moradia.usuario?.id, moradiaResponse.usuario?.id)
        assertEquals(moradia.cliente.id, moradiaResponse.cliente?.id)
        assertEquals(moradia.complemento, moradiaResponse.complemento)
        assertEquals(moradia.numero, moradiaResponse.numero)
        assertEquals(moradia.ativo, moradiaResponse.ativo)
    }

    @DisplayName("se houver aquele cliente, retorna o item")
    @Test
    fun getClienteId(){
        val idCliente= 1
        service.buscarPorCliente(idCliente)
    }

    @DisplayName("se houver aquele endereco, retorna o item")
    @Test
    fun getEnderecoId(){
        val idEndereco= 1
        service.buscarPorEndereco(idEndereco)
    }

    @DisplayName("se houver aquele usuario, retorna o item")
    @Test
    fun getUsuarioId(){
        val idUsuario= 1
        service.buscarPorUsuario(idUsuario)
    }


    @DisplayName("se estiver valido, salva a moradia")
    @Test
    fun saveMoradia(){
        val novaMoradia =
            Moradia(
                id = 1,
                endereco = Endereco(
                    id = 1,
                    cep = "06333-350",
                    logradouro = "Penapolis",
                    bairro = "Santa Brígida",
                    localidade = "Carapicuíba",
                    uf = "SP"
                ),
                usuario = Usuario(
                    id = 1,
                    usuario = "gabriella",
                    senha = "12345678",
                    autenticado = false,
                    ativo = true
                ),
                cliente = Cliente(
                    id = 1,
                    nome = "Sarah",
                    sobrenome = "Oliveira",
                    dtNasc = LocalDate.of(2004, 8, 27),
                    email = "sarah.oliveira@sptech.school",
                    responsavel = null,
                    usuario = 1,
                    ativo = true
                ),
                complemento = "apt2",
                numero = 702,
                ativo = true
            )


        Mockito.`when`(repository.save(novaMoradia))
            .thenReturn(novaMoradia)

    }

    @DisplayName("se id existir, exclui uma moradia")
    @Test
    fun deleteMoradia(){
        saveMoradia()
        val idMoradia= 1
        repository.deleteById(idMoradia)
    }
}


