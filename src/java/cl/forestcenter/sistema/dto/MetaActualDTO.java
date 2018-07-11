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
public class MetaActualDTO {
    public Integer idPlanificacion;
    public Integer idFaena;
    public String descripcionFaena;
    public Integer numeroActa;
    public Date fechaInicio;
    public Date fechaTermino;
    public Integer totalDias;
    public Integer diasTranscurridos;
    public Integer idTipoArbol;
    public String nombreTipoArbol;
    public double metrosCubicos;
    public Integer idPredio;
    public String nombrePredio;

    public MetaActualDTO() {
    }

    public Integer getIdPlanificacion() {
        return idPlanificacion;
    }

    public void setIdPlanificacion(Integer idPlanificacion) {
        this.idPlanificacion = idPlanificacion;
    }

    public Integer getIdFaena() {
        return idFaena;
    }

    public void setIdFaena(Integer idFaena) {
        this.idFaena = idFaena;
    }

    public String getDescripcionFaena() {
        return descripcionFaena;
    }

    public void setDescripcionFaena(String descripcionFaena) {
        this.descripcionFaena = descripcionFaena;
    }

    public Integer getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(Integer numeroActa) {
        this.numeroActa = numeroActa;
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

    public Integer getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(Integer totalDias) {
        this.totalDias = totalDias;
    }

    public Integer getDiasTranscurridos() {
        return diasTranscurridos;
    }

    public void setDiasTranscurridos(Integer diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    public Integer getIdTipoArbol() {
        return idTipoArbol;
    }

    public void setIdTipoArbol(Integer idTipoArbol) {
        this.idTipoArbol = idTipoArbol;
    }

    public String getNombreTipoArbol() {
        return nombreTipoArbol;
    }

    public void setNombreTipoArbol(String nombreTipoArbol) {
        this.nombreTipoArbol = nombreTipoArbol;
    }

    public double getMetrosCubicos() {
        return metrosCubicos;
    }

    public void setMetrosCubicos(double metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public String getNombrePredio() {
        return nombrePredio;
    }

    public void setNombrePredio(String nombrePredio) {
        this.nombrePredio = nombrePredio;
    }
    
    
}
