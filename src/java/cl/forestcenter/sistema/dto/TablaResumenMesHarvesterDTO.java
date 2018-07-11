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
public class TablaResumenMesHarvesterDTO {
    public Integer mes;
    public String mesAño;
    public double produccion;
    public double horasMaquina;
    public double arbolesXHora;
    public double productividad;
    public double tiempoMuerto;

    public TablaResumenMesHarvesterDTO() {
    }

    public TablaResumenMesHarvesterDTO(Integer mes,String mesAño, double produccion, double horasMaquina, double arbolesXHora, double productividad, double tiempoMuerto) {
        this.mes = mes;
        this.mesAño = mesAño;
        this.produccion = produccion;
        this.horasMaquina = horasMaquina;
        this.arbolesXHora = arbolesXHora;
        this.productividad = productividad;
        this.tiempoMuerto = tiempoMuerto;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getMesAño() {
        return mesAño;
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

    public double getArbolesXHora() {
        return arbolesXHora;
    }

    public void setArbolesXHora(double arbolesXHora) {
        this.arbolesXHora = arbolesXHora;
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
