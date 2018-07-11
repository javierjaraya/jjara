/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.SeccionMenuDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Javier-PC
 */
public class Sistema extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ControladorLogin cLogin = new ControladorLogin();

        if (req != null && req.isRequestedSessionIdValid() && cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {
            String pagina = "";
            String mensaje1 = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("SUBMENU")) {
                    int idSeccion = Integer.parseInt(req.getParameter("id"));

                    
                    HttpSession sesion = req.getSession(false);
                    if (sesion == null) {
                        sesion = req.getSession(true);
                    }
                    List<SeccionMenuDTO> listaMenuPrincipal = (List<SeccionMenuDTO>) sesion.getAttribute("MenuPrincipal");
                                                         
                    //onClick=\"javascript:location.reload()\"
                    String subMenu = "<div id=\"home\"><a onClick=\"menuPrincipal()\"><img src=\"/jjara/files/img/home.png\" onmouseover=\"this.src='/jjara/files/img/home2.png'\"  onmouseout=\"this.src='/jjara/files/img/home.png'\" width=\"55\" height=\"55\" id=\"1\" /></a></div>";
                    for (int i = 0; i < listaMenuPrincipal.size(); i++) {
                        if (listaMenuPrincipal.get(i).getIdSeccion() == idSeccion) {
                            List<MenuDTO> opciones = listaMenuPrincipal.get(i).getMenu();
                            for (int j = 0; j < opciones.size(); j++) {
                                subMenu += "<div id=\"caja\"><a onClick=\"cargarOpcion(\'"+opciones.get(j).getUrl().trim()+"\')\"><img src=\"/jjara/files/img/"+opciones.get(j).getImagen1().trim()+"\" onmouseover=\"this.src='/jjara/files/img/"+opciones.get(j).getImagen2().trim()+"'\" onmouseout=\"this.src='/jjara/files/img/"+opciones.get(j).getImagen1().trim()+"'\"width=\"153\" height=\"55\" id=\"1\" /></a></div>";                                
                            }
                        }
                    }      
                    PrintWriter out;
                    try {
                        Gson gson = new Gson();
                        out = res.getWriter();
                        out.println(subMenu);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                pagina = "/Sistema.jsp";
                ServletContext sc = getServletConfig().getServletContext();
                RequestDispatcher rdNext = sc.getRequestDispatcher(pagina);
                rdNext.forward(req, res);
            }

        } else {
            System.out.println(">>>>> Sesion invalida");
            String pagina = "/error.jsp";
            req.getSession().setAttribute("mensajeLogin", "Sesión invalida.");
            req.getSession().setAttribute("flagResultado", "false");
            ServletContext sc = getServletConfig().getServletContext();
            RequestDispatcher rdNext = sc.getRequestDispatcher(pagina);
            rdNext.forward(req, res);
        }
    }
}
