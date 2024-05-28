package consultoria.askyu.gestio.Service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.service.ClienteService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ClienteServiceTest{
    lateinit var repository: ClienteRepository
    lateinit var service: ClienteService

    @BeforeEach
    fun iniciar(){
    repository= mock(ClienteRepository::class.java)
    service= ClienteService(repository)
    }

    @DisplayName("Se houver produto na tabela, o service deve retornar os mesmos elementos")
    @Test
    fun verificarLista() {

        val listaEsperada = listOf(
            Cliente(id= 1, nome= "Davi", sobrenome="Lee", dtNasc = LocalDate.of(2004, 5, 10), usuario= null, ativo= true ),
            Cliente(id = 2, nome= "Alice", sobrenome= "Senes", dtNasc = LocalDate.of(2003, 3, 20), usuario = null, ativo = true),
        )
        Mockito.`when`(repository.findCliente()).thenReturn(listaEsperada)

        val resultado = service.getLista()

        assertEquals(listaEsperada.size, resultado.size)

        assertEquals(listaEsperada, resultado)
    }

}
