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
public class TipoMaquinaDTO {
    public Integer idTipoMaquina;
    public String nombre;

    public TipoMaquinaDTO() {
    }

    public TipoMaquinaDTO(Integer idTipoMaquina, String nombre) {
        this.idTipoMaquina = idTipoMaquina;
        this.nombre = nombre;
    }

    public Integer getIdTipoMaquina() {
        return idTipoMaquina;
    }

    public void setIdTipoMaquina(Integer idTipoMaquina) {
        this.idTipoMaquina = idTipoMaquina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
