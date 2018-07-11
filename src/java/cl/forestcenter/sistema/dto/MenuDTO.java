package cl.forestcenter.sistema.dto;

public class MenuDTO {

    private Integer idMenu;
    private String nombre;
    private String url;

    private Integer idSeccion;
    private String nombreSeccion;
    
    private String imagen1;
    private String imagen2;

    private boolean eliminar = false;
    private boolean crear = false;
    private boolean modificar = false;
    
    private String eliminarDescripcion;
    private String crearDescripcion;
    private String modificarDescripcion;

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }
    
    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
        if(this.eliminar)
            this.eliminarDescripcion = "Si";
        else
            this.eliminarDescripcion = "No";
    }

    public boolean isCrear() {
        return crear;
    }

    public void setCrear(boolean crear) {
        this.crear = crear;
        if(this.crear)
            this.crearDescripcion = "Si";
        else
            this.crearDescripcion = "No";
    }

    public boolean isModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
        if(this.modificar)
            this.modificarDescripcion = "Si";
        else
            this.modificarDescripcion = "No";
    }

    public String getEliminarDescripcion() {
        return eliminarDescripcion;
    }

    public void setEliminarDescripcion(String eliminarDescripcion) {
        this.eliminarDescripcion = eliminarDescripcion;
    }

    public String getCrearDescripcion() {
        return crearDescripcion;
    }

    public void setCrearDescripcion(String crearDescripcion) {
        this.crearDescripcion = crearDescripcion;
    }

    public String getModificarDescripcion() {
        return modificarDescripcion;
    }

    public void setModificarDescripcion(String modificarDescripcion) {
        this.modificarDescripcion = modificarDescripcion;
    }
    
    
}
