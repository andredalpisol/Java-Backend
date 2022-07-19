package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Services.Execption.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Execption.EntityNotFoundException;
import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    public List<Funcionario>mostrarTodosFuncionarios(){
return funcionarioRepository.findAll();
    }

    //criar serviço para buscar um funcionario pelo id
    public Funcionario mostarUmFuncionarioPeloId(Integer idFuncionario) throws EntityNotFoundException {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Erro, funcionario de ID " + idFuncionario + " não existe")
        );
    }

    public List<Funcionario> mostrarFuncionarioPeloCargo (Integer idCargo){
        Optional<Cargo> cargoFuncionarios = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargoFuncionarios);
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
    public Funcionario cadastrarFuncionario (Funcionario funcionario) throws DataIntegrityViolationException {
        try {
        //só por precaução nós vamos colocar o id do funcionario como nulo
        funcionario.setIdFuncionario(null);
        funcionario.setCargo(null);
        return funcionarioRepository.save(funcionario);
        }

        catch (Exception e){
            throw new DataIntegrityViolationException("Erro ao cadastrar o funcionario");
        }

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
