package cl.forestcenter.sistema.controladores.mantenedores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
import cl.forestcenter.sistema.dto.PerfilDTO;
import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.SeccionMenuDTO;

public class ControladorLogin extends Controlador {

    ControladorParametro q;

    public ControladorLogin() {
        q = new ControladorParametro();
    }

    /**
     * Metodo que registra una session en la BD
     *
     * @param usuario
     * @param numeroSession
     * @return true si se a registrado exitozamente y false en caso contrario.
     */
    public boolean insertarSession(UsuarioDTO usuario, String numeroSession) {
        boolean resp = false;
        try {
            int respuesta = conector.executeUpdate("INSERT INTO sesion (fechaConexion, idUsuario, numero) values(now(), ?, ?)", usuario.getIdUsuario(), numeroSession);

            if (respuesta == 1) {
                resp = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return resp;
    }

    /**
     * Metodo que valida si un usuario a iniciado una sesion
     *
     * @param idUsuario
     * @param nroSesion
     * @return true si ya ha iniciado y false en caso contrario.
     */
    public boolean validarSesion(String idUsuario, String nroSesion) {
        if (nroSesion != null && !nroSesion.equals("")) {
            try {
                ResultSet rsSesion = conector.getResultSet("select count(*) as cantidad from sesion  where idSesion =( select max(idSesion) from sesion where idUsuario = ? and numero = ?)", idUsuario, nroSesion);
                if (rsSesion.next()) {
                    if (rsSesion.getInt("cantidad") == 1) {
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.println("[Query][validarSesion] error:" + e.getMessage());
            } finally {
                conector.close();
            }
        }
        return false;
    }

    public boolean validarContrasenaActual(int idUsuario, String contrasena) {
        if (contrasena != null && !contrasena.equals("")) {
            try {
                ResultSet rsSesion = conector.getResultSet("validarContrasena", contrasena, "" + idUsuario);
                while (rsSesion.next()) {
                    if (rsSesion.getInt("cantidad") > 0) {
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.println("[Query][validarContrase�aActual] error:" + e.getMessage());
            } finally {
                conector.close();
            }
        }
        return false;
    }

    public boolean modificarContrasena(int idUsuario, String contrasena) {
        try {
            int respuesta = conector.executeUpdate("actualizarPass", "" + idUsuario, contrasena);
            if (respuesta > 0) {
                //conector.close();
                return true;
            }

        } catch (Exception e) {

            System.out.println("[Query][modificarContrasena] error:" + e.getMessage());
        } finally {
            conector.close();
        }
        return false;
    }

    public boolean actualizarIntentos(UsuarioDTO usuario) {
        boolean resp = false;
        try {
            
            conector.executeUpdate(""
                    + "UPDATE intento_usuario "
                    + " SET cantidadIntentos = ? ,"
                    + " fecha_ultimo_intento = now() "
                    + " WHERE idUsuario = ? ",usuario.getCantidadIntentos(),usuario.getIdUsuario());
            
            
            //ResultSet respuesta = conector.getResultSetProcedure("{ CALL actualizar_cantidad_intentos (?,?)}", usuario.getIdUsuario(), usuario.getCantidadIntentos());            
            resp = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return resp;
    }

    public HashMap<Integer, SeccionMenuDTO> getOpcionesMenu(int idPerfil) {
        HashMap<Integer, SeccionMenuDTO> hasMenuSeccionTotal = new HashMap<Integer, SeccionMenuDTO>();
        Integer seccionFinal = 0;
        try {

            String sql = "select p.idPerfil, s.idseccion, s.nombre as nombreSeccion, s.url as urlSeccion, s.imagen1, s.imagen2, m.idMenu, m.nombre, m.url, m.imagen1 as img1, m.imagen2 as img2, p.crear, p.modificar, p.eliminar "
                    + " from perfil_menu p "
                    + " left join menu m on p.idMenu = m.idMenu "
                    + " left join seccion_menu s on m.idSeccion = s.idSeccion "
                    + " where p.idPerfil = "+idPerfil
                    + " ORDER BY m.idseccion, m.idMenu";

            ResultSet res = conector.getResultSet(sql);
            while (res.next()) {
                try {
                    MenuDTO menu = new MenuDTO();
                    menu.setIdMenu(res.getInt("idMenu"));
                    menu.setNombre(res.getString("nombre"));
                    menu.setUrl(res.getString("url"));
                    menu.setImagen1(res.getString("img1"));
                    menu.setImagen2(res.getString("img2"));
                    
                    menu.setEliminar((res.getInt("eliminar") > 0) ? true : false);
                    menu.setCrear((res.getInt("crear") > 0) ? true : false);
                    menu.setModificar((res.getInt("modificar") > 0) ? true : false);

                    seccionFinal = res.getInt("idseccion");

                    SeccionMenuDTO seccion = hasMenuSeccionTotal.get(seccionFinal);
                    if (seccion == null) {
                        seccion = new SeccionMenuDTO();
                        seccion.setIdSeccion(res.getInt("idseccion"));
                        seccion.setNombre(res.getString("nombreSeccion"));
                        seccion.setUrl(res.getString("urlSeccion"));
                        seccion.setImagen1(res.getString("imagen1"));
                        seccion.setImagen2(res.getString("imagen2"));                        
                    }
                    seccion.addMenu(menu);
                    hasMenuSeccionTotal.put(seccionFinal, seccion);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return hasMenuSeccionTotal;
    }

    public HashMap<String, MenuDTO> getPermisos(int perfil) {
        HashMap<String, MenuDTO> permisos = new HashMap<String, MenuDTO>();
        try {            
           
            String sql = "Select * from perfil_menu p "
                    + " INNER JOIN menu m ON p.idmenu = m.idmenu "
                    + " WHERE p.idperfil = ? ";

            ResultSet res = conector.getResultSet(sql, perfil);

            while (res.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setIdMenu(res.getInt("idMenu"));
                menu.setNombre(res.getString("nombre"));
                menu.setUrl(res.getString("url"));
                menu.setEliminar((res.getInt("eliminar") > 0) ? true : false);
                menu.setCrear((res.getInt("crear") > 0) ? true : false);
                menu.setModificar((res.getInt("modificar") > 0) ? true : false);
                
                permisos.put(menu.getNombre(), menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return permisos;
    }
}
