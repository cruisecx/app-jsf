package org.example.appjsf.services;

import org.example.appjsf.dao.DaoGeneric;
import org.example.appjsf.entities.Pessoa;
import org.example.appjsf.repositories.IDaoPessoa;
import org.example.appjsf.repositories.IDaoPessoaImpl;

import java.util.List;

public class PessoaService {
    private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();

    private IDaoPessoa daoPessoa = new IDaoPessoaImpl();

    public void salvar(Pessoa pessoa) {
        daoGeneric.salvar(pessoa);
    }

    public void delete(Pessoa pessoa) {
        daoGeneric.deletePorId(pessoa);
    }

    public List<Pessoa> getListEntityLimit10(Class<Pessoa> class1) {
        return daoGeneric.getListEntityLimit10(class1);
    }

    public Pessoa consultarUsuario(String login, String senha) {
        return daoPessoa.consultarUsuario(login, senha);
    }
}
