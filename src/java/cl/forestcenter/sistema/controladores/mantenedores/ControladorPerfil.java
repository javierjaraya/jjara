package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.PerfilDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorPerfil extends Controlador {

    public List<PerfilDTO> getPerfiles(int pagina, int cantidad, String where) {
        ArrayList<PerfilDTO> retorno = new ArrayList<PerfilDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT p.idPerfil , p.nombrePerfil "
                    + "FROM perfil p " + where, pagina, cantidad, "p.nombrePerfil ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    PerfilDTO perfil = new PerfilDTO();
                    perfil.setIdPerfil(res.getInt("idPerfil"));
                    perfil.setNombre(res.getString("nombrePerfil"));

                    retorno.add(perfil);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return retorno;
    }

    public boolean actualizar(PerfilDTO perfil) {
        int res = conector.executeUpdate("UPDATE perfil SET nombrePerfil= ? WHERE idPerfil = ? ", perfil.getNombre(), perfil.getIdPerfil());
        return res == 1;
    }

    public boolean eliminar(Integer idPerfil) {
        int res = conector.executeUpdate("DELETE FROM perfil WHERE idPerfil = ?", idPerfil);
        return res == 1;
    }

    public int guardar(PerfilDTO perfil) {
        int res = conector.executeInsert("INSERT INTO perfil (nombrePerfil) VALUES (?)", perfil.getNombre());                                        
        return res;
    }
    
    public boolean guardarPermisosPerfil(PerfilDTO perfil){        
        List<MenuDTO> listaMenu = perfil.getMenus();
        boolean estado = true;
        for (int i = 0; i < listaMenu.size(); i++) {
            MenuDTO menu = listaMenu.get(i);
            int crear = menu.isCrear() ? 1 : 0;
            int modificar = menu.isModificar()? 1 : 0;
            int eliminar = menu.isEliminar() ? 1 : 0;
            int res = conector.executeInsert("INSERT INTO perfil_menu(idPerfil, idMenu, crear, modificar, eliminar) VALUES (?,?,?,?,?)", perfil.getIdPerfil(),menu.getIdMenu(),crear,modificar,eliminar);
            if(res==1){
                estado = false;
            }
        }
        return estado;
    }

    public PerfilDTO getPerfilByID(Integer idPerfil) {
        PerfilDTO perfil = null;

        try {
            String sql = "SELECT p.idPerfil, p.nombrePerfil, pm.idMenu, m.nombre, pm.crear, pm.modificar, pm.eliminar "
                    + "FROM perfil p "
                    + "INNER JOIN perfil_menu pm ON pm.idPerfil = p.idPerfil "
                    + "INNER JOIN menu m ON m.idMenu = pm.idMenu "
                    + "WHERE p.idPerfil = " + idPerfil;
            ResultSet res = conector.getResultSet(sql);
            List<MenuDTO> menus = new ArrayList<MenuDTO>();
            
            while (res.next()) {
                try {

                    if (perfil == null) {
                        perfil = new PerfilDTO();
                        perfil.setIdPerfil(res.getInt("idPerfil"));
                        perfil.setNombre(res.getString("nombrePerfil"));
                    }
                    MenuDTO menu = new MenuDTO();
                    menu.setIdMenu(res.getInt("idMenu"));
                    menu.setNombre(res.getString("nombre"));
                    
                    boolean crear = res.getInt("crear") == 1 ? true : false;
                    menu.setCrear(crear);
                    boolean modificar = res.getInt("modificar") == 1 ? true : false;
                    menu.setModificar(modificar);
                    boolean eliminar = res.getInt("eliminar") == 1 ? true : false;
                    menu.setEliminar(eliminar);
                    
                    menus.add(menu);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if(perfil != null){
                perfil.setMenus(menus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return perfil;
    }
    
    public List<MenuDTO> getAllMenu(){
        List<MenuDTO> menus = new ArrayList<MenuDTO>();
        
        try {
            String sql = "SELECT m.idMenu, m.nombre, m.url, m.idSeccion "
                    + "FROM menu m  ";                
            ResultSet res = conector.getResultSet(sql);
            
            while (res.next()) {
                try {

                    MenuDTO menu = new MenuDTO();
                    menu.setIdMenu(res.getInt("idMenu"));
                    menu.setNombre(res.getString("nombre"));
                    menu.setUrl(res.getString("url"));
                    menu.setIdSeccion(res.getInt("idSeccion"));
                    
                    menus.add(menu);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        
        return menus;
    }
    
    public boolean eliminarPermisos(Integer idPerfil){
        int res = conector.executeUpdate("DELETE FROM perfil_menu WHERE idPerfil = ? ", idPerfil);
        return res >=0;
    }
}
