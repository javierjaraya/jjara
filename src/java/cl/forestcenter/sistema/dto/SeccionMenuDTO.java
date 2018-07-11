
package cl.forestcenter.sistema.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quilaco
 */
public class SeccionMenuDTO {
    private Integer idSeccion;
    private String nombre;
    private String url;
    private String imagen1;
    private String imagen2;

    private List<MenuDTO> menu = new ArrayList<MenuDTO>();
            
    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuDTO> menu) {
        this.menu = menu;
    }
    
    public void addMenu(MenuDTO menu){
        this.menu.add(menu);
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
    
    
}
