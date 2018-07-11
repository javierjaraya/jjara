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
public class TipoArbolDTO {
    public Integer idTipoArbol;
    public String nombre;

    public TipoArbolDTO() {
    }

    public TipoArbolDTO(Integer idTipoArbol, String nombre) {
        this.idTipoArbol = idTipoArbol;
        this.nombre = nombre;
    }

    public Integer getIdTipoArbol() {
        return idTipoArbol;
    }

    public void setIdTipoArbol(Integer idTipoArbol) {
        this.idTipoArbol = idTipoArbol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        
}
