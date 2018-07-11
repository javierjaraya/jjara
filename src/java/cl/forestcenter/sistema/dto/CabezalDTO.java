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
public class CabezalDTO {
    public Integer idCabezal;
    public String patente;
    public String modelo;
    public String numeroChasis;
    public int año;
    public double horometro;
    public Date fechaRegistro;
    public Integer estado;
    public String descripcionEstado;

    public CabezalDTO() {
    }

    public Integer getIdCabezal() {
        return idCabezal;
    }

    public void setIdCabezal(Integer idCabezal) {
        this.idCabezal = idCabezal;
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

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getHorometro() {
        return horometro;
    }

    public void setHorometro(double horometro) {
        this.horometro = horometro;
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
