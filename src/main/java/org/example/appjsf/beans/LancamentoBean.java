package org.example.appjsf.beans;

import org.example.appjsf.dao.DaoGeneric;
import org.example.appjsf.entities.Lancamento;
import org.example.appjsf.entities.Pessoa;
import org.example.appjsf.services.LancamentoService;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class LancamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Lancamento lancamento = new Lancamento();
    private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
    private List<Lancamento> lancamentos = new ArrayList<>();

    private LancamentoService lancamentoService = new LancamentoService();

    public String salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        lancamento.setUsuario(pessoaUser);
        lancamentoService.salvar(lancamento);

        carregarLancamentos();

        return "";
    }

    @PostConstruct
    public void carregarLancamentos() {
        lancamentos = lancamentoService.carregarLancamento();
    }

    public String novo() {
        lancamento = new Lancamento();
        return "";
    }

    public String remover() {
        lancamentoService.deletar(lancamento);
        lancamento = new Lancamento();
        carregarLancamentos();
        return "";
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public DaoGeneric<Lancamento> getDaoGeneric() {
        return daoGeneric;
    }

    public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
        this.daoGeneric = daoGeneric;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public LancamentoService getLancamentoService() {
        return lancamentoService;
    }

    public void setLancamentoService(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }
}
