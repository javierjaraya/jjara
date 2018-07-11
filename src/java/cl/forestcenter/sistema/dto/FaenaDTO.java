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
public class FaenaDTO {
    public Integer idFaena;
    public Integer numeroTeam;
    public Integer idTipoFaena;
    public Integer idJefeFaena;
    public Integer idCalibrador;
    
    public String tipoFaena;
    
    public String nombreJefeFaena;
    public String nombreCalibrador;
    
    public FaenaDTO(){
        
    }

    public Integer getIdFaena() {
        return idFaena;
    }

    public void setIdFaena(Integer idFaena) {
        this.idFaena = idFaena;
    }

    public Integer getNumeroTeam() {
        return numeroTeam;
    }

    public void setNumeroTeam(Integer numeroTeam) {
        this.numeroTeam = numeroTeam;
    }

    public Integer getIdTipoFaena() {
        return idTipoFaena;
    }

    public void setIdTipoFaena(Integer idTipoFaena) {
        this.idTipoFaena = idTipoFaena;
    }
    
    public String getTipoFaena() {
        return tipoFaena;
    }

    public void setTipoFaena(String tipoFaena) {
        this.tipoFaena = tipoFaena;
    }

    public Integer getIdJefeFaena() {
        return idJefeFaena;
    }

    public void setIdJefeFaena(Integer idJefeFaena) {
        this.idJefeFaena = idJefeFaena;
    }

    public String getNombreJefeFaena() {
        return nombreJefeFaena;
    }

    public void setNombreJefeFaena(String nombreJefeFaena) {
        this.nombreJefeFaena = nombreJefeFaena;
    }

    public Integer getIdCalibrador() {
        return idCalibrador;
    }

    public void setIdCalibrador(Integer idCalibrador) {
        this.idCalibrador = idCalibrador;
    }

    public String getNombreCalibrador() {
        return nombreCalibrador;
    }

    public void setNombreCalibrador(String nombreCalibrador) {
        this.nombreCalibrador = nombreCalibrador;
    }

   public String getDescripcionFaena(){
       return tipoFaena+" "+numeroTeam;
   }
}
