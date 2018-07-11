package cl.forestcenter.sistema.servlet.web;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorParametro;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorUsuario;
import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.SeccionMenuDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
import java.io.*;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.*;

import javax.servlet.http.*;

public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pagina = "/login.jsp";
        String paginaError = "/login_error.jsp";
        String paginaActiva = null;
        
        try {
            HttpSession sesion = req.getSession(false);
            if (sesion == null) {
                sesion = req.getSession(true);
            } else {
                paginaActiva = (String) req.getSession().getAttribute("urlActiva");
            }
            //Obtenemos datos del formulario
            String txtUsuario = (req.getParameter("usuario") != null) ? req.getParameter("usuario") : (String) req.getSession().getAttribute("txtUsuario");
            String txtClave = (req.getParameter("password") != null) ? req.getParameter("password") : (String) req.getSession().getAttribute("password");

            if ((txtUsuario != null) && (txtClave != null)) {
                //Validar Usuario y password, verificar el perfil
                txtUsuario = txtUsuario.toUpperCase();
                System.out.println("Intento inicio sesion , Alias: " + txtUsuario);

                ControladorLogin cLogin = new ControladorLogin();
                ControladorUsuario cUsuario = new ControladorUsuario();
                ControladorParametro cParametro = new ControladorParametro();
                HashMap<String, String> parametros = cParametro.obtenerParametros();

                UsuarioDTO usuario = cUsuario.getUsuarioByNombreUsuario(txtUsuario);//cLogin.getUsuario(txtUsuario);
                int cantidadIntentosParametro = 0;
                int minutosEsperaParametro = 0;

                if (usuario.getIdUsuario() != null) {
                    cantidadIntentosParametro = Integer.parseInt(parametros.get("cantidad_intentos"));
                    minutosEsperaParametro = Integer.parseInt(parametros.get("minutos_espera"));

                    if (!(usuario.getDiferenciaMinutos() > minutosEsperaParametro && usuario.getCantidadIntentos() >= cantidadIntentosParametro)) {
                        if (usuario.getPassword().equalsIgnoreCase(txtClave)) {
                            if (usuario.getIdPerfil() > 0) {

                                //USUARIO VALIDO
                                int minutosInactivo = Integer.parseInt(parametros.get("minutos_inactivo"));
                                //Carga Perfil
                                sesion.setMaxInactiveInterval(minutosInactivo * 60);
                                sesion.setAttribute("usuario", usuario);
                                sesion.setAttribute("idUsuario", usuario.getIdUsuario());
                                sesion.setAttribute("nombreUsuario", usuario.getNombreUsuario());
                                sesion.setAttribute("nombreEmpleado", usuario.getNombreEmpleado());
                                sesion.setAttribute("registros_paginacion", parametros.get("registros_paginacion"));
                                sesion.setAttribute("parametros_globales", parametros);

                                //Carga Menu
                                HashMap<Integer, SeccionMenuDTO> seccionesMenu = cLogin.getOpcionesMenu(usuario.getIdPerfil());
                                Set<Integer> keys = seccionesMenu.keySet();
                                
                                List<SeccionMenuDTO> listaMenuPrincipal = getSeccionesMenu(seccionesMenu);
                                String menuPrincipalHTML = getMenuPrincipalHTML(listaMenuPrincipal);
                                
                                sesion.setAttribute("MenuPrincipal", listaMenuPrincipal);
                                sesion.setAttribute("MenuPrincipalHTML", menuPrincipalHTML);    
                                
                                //PERMISOS
                                HashMap<String,MenuDTO> permisosMenu = cLogin.getPermisos(usuario.getIdPerfil());
                                sesion.setAttribute("permisos", permisosMenu);
                                
                                 pagina = "/web/sistema.jsp";
                                 sesion.setAttribute("urlActiva", pagina);
                                
                                 usuario.setCantidadIntentos(0);
                                 cLogin.actualizarIntentos(usuario);
                                 
                                 //insertamos una nueva sesion
                                 cLogin.insertarSession(usuario, sesion.getId());
                                 
                                sesion.setAttribute("mensajeLogin", "Contraseña valida");
                            } else {
                                pagina = paginaError;
                                //Sumamos un nuevo intento
                                usuario.setCantidadIntentos(usuario.getCantidadIntentos() + 1);
                                cLogin.actualizarIntentos(usuario);
                                sesion.setAttribute("mensajeLogin", "No posee el perfil para ingresar al sistema, favor contactar al Administrador.");

                            }
                        } else {
                            pagina = paginaError;
                            //Sumamos un nuevo intento
                            usuario.setCantidadIntentos(usuario.getCantidadIntentos() + 1);
                            cLogin.actualizarIntentos(usuario);
                            sesion.setAttribute("mensajeLogin", "Contrase�a invalida, intentelo nuevamente.");
                        }
                    } else {
                        pagina = paginaError;
                        sesion.setAttribute("mensajeLogin", "Favor espere: " + (minutosEsperaParametro - usuario.getDiferenciaMinutos()) + " minutos.");
                    }
                } else {
                    pagina = paginaError;
                    sesion.setAttribute("mensajeLogin", "El usuario \"" + req.getParameter("txtUsuario") + "\" no esta registrado.");
                }

            } else {
                if (paginaActiva != null) {
                    pagina = paginaActiva;
                }
            }

        } catch (Exception e) {
            pagina = paginaError;
            req.getSession().setAttribute("mensajeLogin", "Sesion inv&aacute;lida");
            e.printStackTrace();
        }
        System.out.println("Intento inicio sesion , pagina: " + pagina);
        ServletContext sc = getServletConfig().getServletContext();
        RequestDispatcher rdNext = sc.getRequestDispatcher(pagina);
        rdNext.forward(req, res);
    }

    private List<SeccionMenuDTO> getSeccionesMenu(HashMap<Integer, SeccionMenuDTO> seccionesMenu) {
        List<SeccionMenuDTO> secciones = new ArrayList<SeccionMenuDTO>();
        Set<Integer> keys = seccionesMenu.keySet();
        for (Integer key : keys) {
            SeccionMenuDTO seccion = seccionesMenu.get(key);
            secciones.add(seccion);
        }
        return secciones;
    }

    private String getMenuPrincipalHTML(List<SeccionMenuDTO> secciones) {
        String opciones = "";
        for (int i = 0; i < secciones.size(); i++) {
            SeccionMenuDTO seccion = secciones.get(i);            
            opciones +="<div id=\"boton\">"
                       + "<div id=\"c1\"><a onClick=\"obtieneSubMenu("+seccion.getIdSeccion() + ")\" ><img src=\"/jjara/files/img/"+seccion.getImagen1()+"\" onmouseover=\"this.src='/jjara/files/img/"+seccion.getImagen2()+"'\" onmouseout=\"this.src='/jjara/files/img/"+seccion.getImagen1()+"'\" width=\"200\" height=\"200\" id=\""+(i+1)+"\" /></a></div>"
                       + "<div id=\"c2\">"+seccion.getNombre()+"</div>"
                     +"</div>";   
        }
        return opciones;
    }
}
