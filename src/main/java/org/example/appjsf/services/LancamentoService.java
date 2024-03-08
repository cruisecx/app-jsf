package org.example.appjsf.services;

import org.example.appjsf.dao.DaoGeneric;
import org.example.appjsf.entities.Lancamento;
import org.example.appjsf.entities.Pessoa;
import org.example.appjsf.repositories.IDaoLancamentoImpl;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;

public class LancamentoService {
    private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
    private IDaoLancamentoImpl daoLancamentoImpl = new IDaoLancamentoImpl();

    public void salvar(Lancamento lancamento) {
        daoGeneric.merge(lancamento);
    }

    public void deletar(Lancamento lancamento) {
        daoGeneric.deletePorId(lancamento);
    }

    public List<Lancamento> carregarLancamento() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
        return daoLancamentoImpl.consultar(pessoaUser.getId());
    }

    public List<Lancamento> getListEntityLimit10(Class<Lancamento> class1) {
        return daoGeneric.getListEntityLimit10(class1);
    }
}
