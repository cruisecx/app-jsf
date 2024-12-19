package org.example.appjsf.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "servidor")
public class Servidor implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String nome;
    private String matricula;
    private String uf;
    private boolean efetivo;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getMatricula(){return matricula;}
    public void setMatricula(String matricula){this.matricula = matricula;}

    public String getUf(){return uf;}
    public void setUf(String uf){this.uf = uf;}

    public boolean getEfetivo(){return efetivo;}
    public void setEfetivo(boolean efetivo){this.efetivo = efetivo;}

}
