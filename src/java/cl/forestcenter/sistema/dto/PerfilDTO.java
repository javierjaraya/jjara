package cl.forestcenter.sistema.dto;

import java.util.ArrayList;
import java.util.List;

public class PerfilDTO {

    private Integer idPerfil;
    private String nombre;

    List<MenuDTO> menus = new ArrayList<MenuDTO>();

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDTO> menus) {
        this.menus = menus;
    }

    public MenuDTO contieneMenu(Integer idMenu) {
        MenuDTO menu = null;
        if (!menus.isEmpty()) {
            int cont = 0;
            while(cont < menus.size()){
                if(menus.get(cont).getIdMenu() == idMenu){
                    return menus.get(cont);
                }
                cont++;
            }
        }
        return menu;
    }
}
