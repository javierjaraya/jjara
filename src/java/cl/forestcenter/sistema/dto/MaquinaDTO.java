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
public class MaquinaDTO {
    public Integer idMaquina;
    public String codigoMaquina;
    public String codigoForestal;
    public String patente;
    public String modelo;
    public String numeroChasis;
    public String numeroMotor;
    public Integer año;
    public double horometro;
    public Integer idTipoMaquina;
    public String tipoMaquina;
    public Integer idCabezal;
    public String cabezal;
    public Date fechaRegistro;
    public Integer estado;
    public String descripcionEstado;

    public MaquinaDTO() {
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }
    
    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public String getCodigoForestal() {
        return codigoForestal;
    }

    public void setCodigoForestal(String codigoForestal) {
        this.codigoForestal = codigoForestal;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public double getHorometro() {
        return horometro;
    }

    public void setHorometro(double horometro) {
        this.horometro = horometro;
    }

    public Integer getIdTipoMaquina() {
        return idTipoMaquina;
    }

    public void setIdTipoMaquina(Integer idTipoMaquina) {
        this.idTipoMaquina = idTipoMaquina;
    }

    public String getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(String tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }
    
    public Integer getIdCabezal() {
        return idCabezal;
    }

    public void setIdCabezal(Integer idCabezal) {
        this.idCabezal = idCabezal;
    }

    public String getCabezal() {
        return cabezal;
    }

    public void setCabezal(String cabezal) {
        this.cabezal = cabezal;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
   
    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
        this.descripcionEstado = estado == 1 ? "Activo" : "Inactivo";
    }            
}
