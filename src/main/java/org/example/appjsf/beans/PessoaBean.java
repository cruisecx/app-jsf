package org.example.appjsf.beans;

import org.example.appjsf.entities.Pessoa;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class PessoaBean implements Serializable {

    private Pessoa pessoa;

    private boolean show = false;

    public void salvar() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Só pra contrariar");
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
