package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Empresa
import consultoria.askyu.gestio.dtos.EmailDTO
import consultoria.askyu.gestio.dtos.EmpresaCadastroRequest
import consultoria.askyu.gestio.dtos.TelefoneDTO
import consultoria.askyu.gestio.repository.EmpresaRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class EmpresaService(
    val repository: EmpresaRepository,
    val mapper: ModelMapper = ModelMapper()
) {
    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun validadeUnico(empresa: Empresa){
        if(repository.countByNomeEmpresaAndNomeFantasiaAndCnpjAndAtivo(empresa.nomeEmpresa,empresa.nomeFantasia,empresa.cnpj) >= 1){
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "Essa cadastro ja existe no sistema!")
        }
    }

    fun existenceValidation(id:Int){
        if(!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Empresa não encontrada!")
        }
    }

    fun findAll():List<Empresa>{
        val empresas = repository.findAll()
        listValidation(empresas)
        return empresas
    }

    fun cadastrarEmpresa(empresaRequest: EmpresaCadastroRequest):Empresa{
        var empresa = mapper.map(empresaRequest, Empresa::class.java)
        validadeUnico(empresa)
        return repository.save(empresa)
    }

    fun desativarEmpresa(id:Int){
        existenceValidation(id)
        var empresa = repository.findById(id).get()
        empresa.ativo = false
        repository.save(empresa)
    }

    fun attTelefone(id:Int, novoTelefone:TelefoneDTO):Empresa{
        existenceValidation(id)
        var empresa = repository.findById(id).get()
        if(empresa.telefone == novoTelefone.telefone){
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse ja é o telefone cadastrado nessa empresa!")
        }
        empresa.telefone = novoTelefone.telefone
        return repository.save(empresa)
    }

    fun attEmail(id:Int, novoEmail:EmailDTO):Empresa{
        existenceValidation(id)
        var empresa = repository.findById(id).get()
        if(empresa.email == novoEmail.email){
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse ja é o email cadastrado nessa empresa!")
        }
        empresa.email = novoEmail.email
        return repository.save(empresa)
    }
}