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
public class TipoFaenaDTO {
    public Integer idTipoFaena;
    public String nombre;

    public TipoFaenaDTO() {
    }

    public TipoFaenaDTO(Integer idTipoFaena, String nombre) {
        this.idTipoFaena = idTipoFaena;
        this.nombre = nombre;
    }

    public Integer getIdTipoFaena() {
        return idTipoFaena;
    }

    public void setIdTipoFaena(Integer idTipoFaena) {
        this.idTipoFaena = idTipoFaena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
