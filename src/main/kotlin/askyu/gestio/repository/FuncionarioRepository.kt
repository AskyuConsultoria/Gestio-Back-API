package askyu.gestio.repository

import askyu.gestio.dominio.evento.Tag
import askyu.gestio.dominio.funcionario.Funcionario
import org.springframework.data.jpa.repository.JpaRepository

interface FuncionarioRepository : JpaRepository<Funcionario, Int> {
}