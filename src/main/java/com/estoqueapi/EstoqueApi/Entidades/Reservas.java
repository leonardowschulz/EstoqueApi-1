package com.estoqueapi.EstoqueApi.Entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservas {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long idReserva;
    private boolean finalizada = false;
    private float quantidadeReserva;
    private Date dataPrevista;
    private String ordem;

    @ManyToOne
    private Usuarios usuario;

    @ManyToOne
    private Itens item;

    public long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(long idReserva) {
        this.idReserva = idReserva;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public float getQuantidadeReserva() {
        return quantidadeReserva;
    }

    public void setQuantidadeReserva(float quantidadeReserva) {
        this.quantidadeReserva = quantidadeReserva;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Itens getItem() {
        return item;
    }

    public void setItem(Itens item) {
        this.item = item;
    }
}
