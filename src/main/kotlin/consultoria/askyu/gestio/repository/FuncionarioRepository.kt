package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Funcionario
import org.springframework.data.jpa.repository.JpaRepository

interface FuncionarioRepository: JpaRepository<Funcionario, Int> {

    fun countByUsuarioAndEmpresaId(usuario: String, empresaId:Int): Int

    fun findByUsuarioAndSenha(usuario: String, senha: String): Funcionario

    fun findByUsuario(usuario: String): Funcionario
}