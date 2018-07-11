package cl.forestcenter.sistema.dto;

public class UsuarioDTO {

    private Integer idUsuario;
    private String rut;
    private String nombreUsuario;
    private String password;
    private String fechaIngreso;
    private int estado;
    private String estadoDescripcion;
    
    private Integer idEmpleado;
    private String nombreEmpleado;
    
    private Integer idPerfil;
    private String nombrePerfil; 

    private Integer cantidadIntentos;
    private Integer diferenciaMinutos;

    public UsuarioDTO() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
     public void setEstado(int estado) {
        this.estado = estado;
        this.estadoDescripcion = estado == 1 ? "Activo" : "Inactivo";
    }

    public int getEstado() {
        return estado;
    }
    public String getEstadoDescripcion() {
        return estadoDescripcion;
    }

    public void setEstadoDescripcion(String estadoDescripcion) {
        this.estadoDescripcion = estadoDescripcion;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Integer getCantidadIntentos() {
        return cantidadIntentos;
    }

    public void setCantidadIntentos(Integer cantidadIntentos) {
        this.cantidadIntentos = cantidadIntentos;
    }

    public Integer getDiferenciaMinutos() {
        return diferenciaMinutos;
    }

    public void setDiferenciaMinutos(Integer diferenciaMinutos) {
        this.diferenciaMinutos = diferenciaMinutos;
    }
    
}
