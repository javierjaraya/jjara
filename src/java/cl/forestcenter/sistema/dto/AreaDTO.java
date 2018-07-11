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
public class AreaDTO {
    public Integer idArea;
    public String nombre;

    public AreaDTO() {
    }

    public AreaDTO(Integer idArea, String nombre) {
        this.idArea = idArea;
        this.nombre = nombre;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
