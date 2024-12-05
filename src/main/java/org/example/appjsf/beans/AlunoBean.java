package org.example.appjsf.beans;

import org.example.appjsf.dao.DaoGeneric;
import org.example.appjsf.entities.Aluno;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class AlunoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Aluno aluno = new Aluno();
    private List<Aluno> alunos = new ArrayList<>();

    private DaoGeneric<Aluno> daoGeneric = new DaoGeneric<Aluno>();

    public String salvar() {
        daoGeneric.merge(aluno);
        carregarAlunos();
        return "";
    }

    @PostConstruct
    public void carregarAlunos() {
        alunos = daoGeneric.getListEntity(Aluno.class);
    }

    public void novo() {
        System.out.println("novo");
        setAluno(new Aluno());
    }

    public void remover() {
        daoGeneric.deletePorId(aluno);
        setAluno(new Aluno());
        carregarAlunos();
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

}
