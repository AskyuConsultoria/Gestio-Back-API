package askyu.gestio.repository

import askyu.gestio.dominio.pessoa.Endereco
import org.springframework.data.jpa.repository.JpaRepository

interface EnderecoRepository : JpaRepository<Endereco, Int> {
}