/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.dto;

import java.sql.Date;


/**
 *
 * @author Javier
 */
public class EmpleadoDTO {
    public int idEmpleado;
    public String rut;
    public String nombres;
    public String apellidos;
    public String direccion;
    public int telefono;
    public String correo;
    public int idCargo;
    public String cargo;
    public Date fechaRegistro;
    public int estado;
    public String estadoDescripcion;

    public EmpleadoDTO(){
        
    }
    public EmpleadoDTO(int idEmpleado, String rut, String nombres, String apellidos, String direccion, int telefono, String correo, int idCargo, Date fechaRegistro, int estado) {
        this.idEmpleado = idEmpleado;
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.idCargo = idCargo;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.estadoDescripcion = estado == 1 ? "Activo" : "Inactivo";
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
    
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        this.estadoDescripcion = estado == 1 ? "Activo" : "Inactivo";
    }
    
    public String getNombreCompleto(){
        return nombres+" "+apellidos;
    }
    
}
