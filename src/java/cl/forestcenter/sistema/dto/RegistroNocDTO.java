/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

import java.sql.Date;

/**
 *
 * @author Javier-PC
 */
public class RegistroNocDTO {

    public Integer idRegistroNoc;
    public Date fechaRegistro;
    public Integer idFaena;
    public String faena;
    public Integer idMaquina;
    public String maquina;
    public Integer idOperador;
    public String operador;
    public Integer idPredio;
    public String predio;
    public double m3;
    public String observacion;

    public RegistroNocDTO() {
    }

    public Integer getIdRegistroNoc() {
        return idRegistroNoc;
    }

    public void setIdRegistroNoc(Integer idRegistroNoc) {
        this.idRegistroNoc = idRegistroNoc;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdFaena() {
        return idFaena;
    }

    public void setIdFaena(Integer idFaena) {
        this.idFaena = idFaena;
    }

    public String getFaena() {
        return faena;
    }

    public void setFaena(String faena) {
        this.faena = faena;
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public double getM3() {
        return m3;
    }

    public void setM3(double m3) {
        this.m3 = m3;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    
}
