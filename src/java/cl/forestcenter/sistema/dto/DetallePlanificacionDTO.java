
package cl.forestcenter.sistema.dto;

import java.sql.Date;

/**
 *
 * @author Javier J
 */
public class DetallePlanificacionDTO {
    public Integer idDetallePlanificacion;
    public Integer numeroActa;
    public Date fechaInicioDetalle;
    public Date fechaTerminoDetalle;
    public Integer idTipoArbol;
    public String tipoArbol;
    public Double metrosCubicos;
    public Integer estadoDetalle;
    public String descripcionEstado;
    public Integer idPredio;
    public String nombrePredio;
    public Integer idPlanificacionFaena;
    

    public DetallePlanificacionDTO() {
    }

    public Integer getIdDetallePlanificacion() {
        return idDetallePlanificacion;
    }

    public void setIdDetallePlanificacion(Integer idDetallePlanificacion) {
        this.idDetallePlanificacion = idDetallePlanificacion;
    }

    public Integer getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(Integer numeroActa) {
        this.numeroActa = numeroActa;
    }

    public Date getFechaInicioDetalle() {
        return fechaInicioDetalle;
    }

    public void setFechaInicioDetalle(Date fechaInicioDetalle) {
        this.fechaInicioDetalle = fechaInicioDetalle;
    }

    public Date getFechaTerminoDetalle() {
        return fechaTerminoDetalle;
    }

    public void setFechaTerminoDetalle(Date fechaTerminoDetalle) {
        this.fechaTerminoDetalle = fechaTerminoDetalle;
    }

    public Integer getIdTipoArbol() {
        return idTipoArbol;
    }

    public void setIdTipoArbol(Integer idTipoArbol) {
        this.idTipoArbol = idTipoArbol;
    }

    public String getTipoArbol() {
        return tipoArbol;
    }

    public void setTipoArbol(String tipoArbol) {
        this.tipoArbol = tipoArbol;
    }

    public Double getMetrosCubicos() {
        return metrosCubicos;
    }

    public void setMetrosCubicos(Double metrosCubicos) {
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

    public Integer getIdPlanificacionFaena() {
        return idPlanificacionFaena;
    }

    public void setIdPlanificacionFaena(Integer idPlanificacionFaena) {
        this.idPlanificacionFaena = idPlanificacionFaena;
    }

    
    public Integer getEstadoDetalle() {
        return estadoDetalle;
    }

    public void setEstadoDetalle(Integer estado) {
        this.estadoDetalle = estado;
        this.descripcionEstado = estado == 1 ? "Activo" : "Inactivo";
    }
}
