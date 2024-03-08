package org.example.appjsf.beans;

import org.example.appjsf.entities.Pessoa;
import org.example.appjsf.enuns.EnumSexo;
import org.example.appjsf.services.PessoaService;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ViewScoped
@Named
public class PessoaBean implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Pessoa pessoa = new Pessoa();

    private List<Pessoa> pessoas = new ArrayList<>();

    private PessoaService pessoaService = new PessoaService();

    public String salvar() {
        pessoaService.salvar(pessoa);
        return "";
    }

    public String remover() {
        pessoaService.delete(pessoa);
        return "";
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<EnumSexo> getOptsEnumSexo() {
        return Arrays.asList(EnumSexo.MASCULINO, EnumSexo.FEMININO);
    }

}
