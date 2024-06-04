package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.MoradiaResponse
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.EnderecoRepository
import consultoria.askyu.gestio.repository.MoradiaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import java.time.LocalDate

class MoradiaServiceTest {
    lateinit var repository: MoradiaRepository
    lateinit var mapper: ModelMapper
    lateinit var usuario: UsuarioRepository
    lateinit var cliente:ClienteRepository
    lateinit var endereco: EnderecoRepository
    lateinit var service: MoradiaService


    @BeforeEach
    fun iniciar() {
        repository = mock(MoradiaRepository::class.java)
        cliente = mock(ClienteRepository::class.java)
        endereco = mock(EnderecoRepository::class.java)
        usuario= mock(UsuarioRepository::class.java)
        mapper= mock(ModelMapper::class.java)
        service = MoradiaService(repository, usuario, cliente, endereco, mapper)
    }

    @DisplayName("se o usuario existir, retorna")
    @Test
    fun validationUsuario(){
        val usuarioTeste= Usuario(id= 1, usuario="gabriella", senha= "12345678", autenticado = true, ativo = true)
        `when`(usuario.existsById(1))
            .thenReturn(true)
        service.validarUsuario(usuarioTeste.id)

    }

    @DisplayName("Se houver moradias, irá retorna-las")
    @Test
    fun getLista() {
        val listaEsperada = mockarMoradia()
        `when`(usuario.existsById(anyInt())).thenReturn(true)

        `when`(repository.findByUsuarioId(1))
            .thenReturn(listaEsperada)

        val resultado = service.getLista(1)

        assertEquals(listaEsperada!!.size, resultado.size)
    }

    @DisplayName("se houver aquele cliente, retorna o item")
    @Test
    fun getClienteId(){
        val listaDesejada= mockarMoradia()
        val listaMapeada= listaDesejada!!.map{service.mapper.map(it, MoradiaResponse::class.java)}

        `when`(usuario.existsById(anyInt())).thenReturn(true)
        `when`(cliente.existsByUsuarioIdAndId(anyInt(),anyInt())).thenReturn(true)
        `when`(listaDesejada!!.map{service.mapper.map(it, MoradiaResponse::class.java)}).thenReturn(listaMapeada)
        `when`(repository.findByUsuarioIdAndClienteId(anyInt(), anyInt())).thenReturn(listaDesejada)

        val resultado=  service.buscarPorCliente(1,1)


      assertEquals(listaMapeada!!.size, resultado.size)

    }

    @DisplayName("se houver aquele endereco, retorna o item")
    @Test
    fun getEnderecoId(){
        val listaDesejada= mockarMoradia()
        val listaMapeada= listaDesejada!!.map{service.mapper.map(it, MoradiaResponse::class.java)}

        `when`(usuario.existsById(anyInt())).thenReturn(true)
        `when`(cliente.existsByUsuarioIdAndId(anyInt(),anyInt())).thenReturn(true)
        `when`(endereco.existsByUsuarioIdAndClienteIdAndId(anyInt(),anyInt(), anyInt())).thenReturn(true)
        `when`(listaDesejada!!.map{service.mapper.map(it, MoradiaResponse::class.java)}).thenReturn(listaMapeada)
        `when`(repository.findByUsuarioIdAndClienteId(anyInt(), anyInt())).thenReturn(listaDesejada)

        val resultado=  service.buscarPorCliente(1,1)


        assertEquals(listaMapeada!!.size, resultado.size)

    }


    @DisplayName("se estiver valido, salva a moradia")
    @Test
    fun saveMoradia(){
        val novaMoradia= mockarMoradia()!![0]
        `when`(usuario.existsById(anyInt())).thenReturn(true)
        `when`(cliente.existsByUsuarioIdAndId(anyInt(),anyInt())).thenReturn(true)
        `when`(endereco.existsByUsuarioIdAndClienteIdAndId(anyInt(),anyInt(), anyInt())).thenReturn(true)
        `when`(repository.save(novaMoradia)).thenReturn(novaMoradia)
        service.salvar(1,1,1, novaMoradia)
    }

    @DisplayName("se id existir, exclui uma moradia")
    @Test
    fun deleteMoradia(){
        val moradia= null

        `when`(usuario.existsById(anyInt())).thenReturn(true)
        `when`(cliente.existsByUsuarioIdAndId(anyInt(),anyInt())).thenReturn(true)
        `when`(endereco.existsByUsuarioIdAndClienteIdAndId(anyInt(), anyInt(),anyInt())).thenReturn(true)
        `when`(repository.existsByUsuarioIdAndId(anyInt(),anyInt())).thenReturn(true)
        `when`(repository.findByUsuarioIdAndId(anyInt(), anyInt())).thenReturn(moradia)

        val respostaEsperada= false
        val resultado= service.excluirPorId(1,1,1,1)

        assertEquals(respostaEsperada,resultado)
    }
//

    fun mockarMoradia(): List<Moradia>?{
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

        return listaEsperada
    }
}


