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
public class PredioDTO {
    public Integer idPredio;
    public String nombre;
    public Integer idArea;
    public String area;
    public Integer idZona;
    public String zona;
    public double superficie;
    public Integer estado;
    public String descripcionEstado;

    public PredioDTO() {
    }

    public PredioDTO(Integer idPredio, String nombre, Integer idArea, String area, Integer idZona, String zona, double superficie) {
        this.idPredio = idPredio;
        this.nombre = nombre;
        this.idArea = idArea;
        this.area = area;
        this.idZona = idZona;
        this.zona = zona;
        this.superficie = superficie;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }        

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
        this.descripcionEstado = estado == 0 ? "Inactivo" : "Activo";
    }
}
