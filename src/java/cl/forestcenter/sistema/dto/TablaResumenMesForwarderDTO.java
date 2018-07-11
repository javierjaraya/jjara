/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.forestcenter.sistema.dto;

/**
 *
 * @author Javier-PC
 */
public class TablaResumenMesForwarderDTO {
    public Integer mes;
    public String mesAño;
    public double produccion;
    public double horasMaquina;
    public double ciclosXHora;
    public double productividad;
    public double tiempoMuerto;

    public TablaResumenMesForwarderDTO() {
    }

    public TablaResumenMesForwarderDTO(Integer mes,String mesAño, double produccion, double horasMaquina, double ciclosXHora, double productividad, double tiempoMuerto) {
        this.mes = mes;
        this.mesAño = mesAño;
        this.produccion = produccion;
        this.horasMaquina = horasMaquina;
        this.ciclosXHora = ciclosXHora;
        this.productividad = productividad;
        this.tiempoMuerto = tiempoMuerto;
    }
    
    public String getMesAño() {
        return mesAño;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setMesAño(String mesAño) {
        this.mesAño = mesAño;
    }

    public double getProduccion() {
        return produccion;
    }

    public void setProduccion(double produccion) {
        this.produccion = produccion;
    }

    public double getHorasMaquina() {
        return horasMaquina;
    }

    public void setHorasMaquina(double horasMaquina) {
        this.horasMaquina = horasMaquina;
    }

    public double getCiclosXHora() {
        return ciclosXHora;
    }

    public void setCiclosXHora(double ciclosXHora) {
        this.ciclosXHora = ciclosXHora;
    }

    public double getProductividad() {
        return productividad;
    }

    public void setProductividad(double productividad) {
        this.productividad = productividad;
    }

    public double getTiempoMuerto() {
        return tiempoMuerto;
    }

    public void setTiempoMuerto(double tiempoMuerto) {
        this.tiempoMuerto = tiempoMuerto;
    }
}
