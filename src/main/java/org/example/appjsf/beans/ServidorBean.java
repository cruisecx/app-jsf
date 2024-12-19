package org.example.appjsf.beans;

import org.example.appjsf.dao.DaoGeneric;
import org.example.appjsf.entities.Servidor;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ServidorBean implements Serializable {

    private Servidor servidor = new Servidor();
    private List<Servidor> servidores = new ArrayList();

    private List<Servidor> servidoresFiltrados = new ArrayList();
    private String searchTerm;
    private Servidor servidorSelecionado;

    private DaoGeneric<Servidor> daoGeneric = new DaoGeneric<>();

    public ServidorBean() {
        servidoresFiltrados = new ArrayList<>(servidores);
    }

    @PostConstruct
    public void carregarServidores() {
        this.filterRecords();
    }

    public String salvar() {
        daoGeneric.merge(servidor);
        novo();
        carregarServidores();
        return "";
    }

    public void novo() {
        System.out.println("novo");
        setServidor(new Servidor());
    }

    public void remover() {
        daoGeneric.deletePorId(servidor);
        setServidor(new Servidor());
        carregarServidores();
    }

    public String toUpperCase(String word) {
        String result = word.toUpperCase();
        return result;
    }

    public void filterRecords() {
       try {
           if (searchTerm != null) {
               String searchTermLC = searchTerm.trim().toLowerCase();
               // alteramos essa linha
               setServidores(new ArrayList<>(daoGeneric.getListEntity(Servidor.class)));

               servidoresFiltrados = servidores.stream()
                       .filter(s -> s.getNome().toLowerCase().contains(searchTermLC))
                       .collect(Collectors.toList());

               setServidores(servidoresFiltrados);
           } else {
               setServidores(new ArrayList<>(daoGeneric.getListEntity(Servidor.class)));
           }

       } catch (Exception e) {
           System.out.println("Erro: " + e.getMessage());
       }
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public List<Servidor> getServidores() {
        return servidores;
    }

    public void setServidores(List<Servidor> servidores) {
        this.servidores = servidores;
    }

    public Servidor getServidorSelecionado() {
        return servidorSelecionado;
    }

    public void setServidorSelecionado(Servidor servidorSelecionado) {
        this.servidorSelecionado = servidorSelecionado;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Servidor> getServidoresFiltrados() {
        return servidoresFiltrados;
    }

    public void setServidoresFiltrados(List<Servidor> servidoresFiltrados) {
        this.servidoresFiltrados = servidoresFiltrados;
    }
}
