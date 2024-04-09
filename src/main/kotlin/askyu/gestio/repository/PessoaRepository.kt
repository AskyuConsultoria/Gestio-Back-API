package askyu.gestio.repository

import askyu.gestio.dominio.pessoa.Pessoa
import org.springframework.data.jpa.repository.JpaRepository

interface PessoaRepository : JpaRepository<Pessoa, Int> {

}