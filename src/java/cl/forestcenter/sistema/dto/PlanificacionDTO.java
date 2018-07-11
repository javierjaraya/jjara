/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

import java.util.Date;
import java.util.List;



/**
 *
 * @author Javier J
 */
public class PlanificacionDTO {
    public Integer idPlanificacion;
    public Date fechaInicio;
    public Date fechaTermino;
    public Integer estado;
    public String descripcionEstado;
    public Integer idFaena;
    public String descripcionFaena;
    
    public List<DetallePlanificacionDTO> detalle;

    public PlanificacionDTO() {
    }

    public Integer getIdPlanificacion() {
        return idPlanificacion;
    }

    public void setIdPlanificacion(Integer idPlanificacion) {
        this.idPlanificacion = idPlanificacion;
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
        this.descripcionEstado = estado == 1 ? "Activo" : "Inactivo";
    }

    public Integer getIdFaena() {
        return idFaena;
    }

    public void setIdFaena(Integer idFaena) {
        this.idFaena = idFaena;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public List<DetallePlanificacionDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetallePlanificacionDTO> detalle) {
        this.detalle = detalle;
    }
    public String getDescripcionFaena() {
        return descripcionFaena;
    }

    public void setDescripcionFaena(String descripcionFaena) {
        this.descripcionFaena = descripcionFaena;
    }
}
