package org.example.appjsf.repositories;

import org.example.appjsf.entities.Pessoa;

import javax.faces.model.SelectItem;
import java.util.List;

public interface IDaoPessoa {
    Pessoa consultarUsuario(String login, String senha);

    List<SelectItem> listaEstados();
}
