package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    FuncionarioRepository funcionarioRepository;
    public List<Funcionario>mostrarTodosFuncionarios(){
return funcionarioRepository.findAll();
    }

    //criar serviço para buscar um funcionario pelo id
    public Funcionario mostarUmFuncionarioPeloId(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow();
    }

    //criar serviço para buscar um funcionario pelo email
    public Funcionario mostarUmFuncionarioPeloEmail(String email){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

    public Funcionario mostarUmFuncionarioPeloNome(String nome){
        Optional<Funcionario> funcionario = funcionarioRepository.findByNome(nome);
        return funcionario.orElseThrow();
    }
    //criar um serviço para cadastrar um novo funcionario
    public Funcionario cadastrarFuncionario (Funcionario funcionario){
        //só por precaução nós vamos colocar o id do funcionario como nulo
        funcionario.setIdFuncionario(null);
        return funcionarioRepository.save(funcionario);

    }

    public void deletarFuncionario (Integer idFuncionario){
        funcionarioRepository.deleteById(idFuncionario);
    }

    public void  editarFuncionario (Funcionario funcionario){
        funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario = mostarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }
}
