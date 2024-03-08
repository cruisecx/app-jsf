package org.example.appjsf.repositories;

import org.example.appjsf.entities.Lancamento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface IDaoLancamento extends Serializable {
    List<Lancamento> consultar(Long codUser);

    List<Lancamento> consultarLimit10(Long codUser);

    List<Lancamento> relatorioLancamento (String numNome, Date dataIni, Date dataFim);
}
