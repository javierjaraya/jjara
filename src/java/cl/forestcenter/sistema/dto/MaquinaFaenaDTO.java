/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

import java.sql.Date;

/**
 *
 * @author Javier J
 */
public class MaquinaFaenaDTO {
    public Integer idMaquinaFaena;
    public String codigoMaquina;
    public MaquinaDTO maquina;
    public Integer idFaena;
    public FaenaDTO faena;
    public Date fechaInicio;
    public Date fechaTermino;
    public Integer estado;
    public String descripcionEstado;

    public MaquinaFaenaDTO() {
    }

    public Integer getIdMaquinaFaena() {
        return idMaquinaFaena;
    }

    public void setIdMaquinaFaena(Integer idMaquinaFaena) {
        this.idMaquinaFaena = idMaquinaFaena;
    }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public MaquinaDTO getMaquina() {
        return maquina;
    }

    public void setMaquina(MaquinaDTO maquina) {
        this.maquina = maquina;
    }

    public Integer getIdFaena() {
        return idFaena;
    }

    public void setIdFaena(Integer idFaena) {
        this.idFaena = idFaena;
    }

    public FaenaDTO getFaena() {
        return faena;
    }

    public void setFaena(FaenaDTO faena) {
        this.faena = faena;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }
    
    
}
