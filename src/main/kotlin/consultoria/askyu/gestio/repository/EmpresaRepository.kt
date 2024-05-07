package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Empresa
import org.springframework.data.jpa.repository.JpaRepository

interface EmpresaRepository : JpaRepository<Empresa, Int> {

    fun countByNomeEmpresaAndNomeFantasiaAndCnpjAndAtivo(nomeEmpresa:String, nomeFantasia:String, cnpj: String, ativo:Boolean = true):Int
}