/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorUsuario;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jjara
 */
public class PlantillaClaseAdministrar extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";
            String mensaje1 = "";
            String mensaje2 = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {                   
                    int fila = 1;
                    try {
                        fila = Integer.parseInt(req.getParameter("fila").toString());
                    } catch (Exception e) {
                    }

                    String strCant = req.getSession().getAttribute("registros_paginacion").toString();
                    int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

                    ResponseTablaDTO responseJson = new ResponseTablaDTO();
                    //CODIGO VARIABLE
                    ControladorUsuario cUsuario = new ControladorUsuario();
                    List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
                    String where = "";
                    
                    //mas codigo
                    //lista = cUsuario.getUsuarios(fila, cantidad, where);
                    
                    //FIN CODIGO VARIABLE
                    int size = lista.size();
                    responseJson.setTotal(size);
                    res.setCharacterEncoding("UTF-8");
                    responseJson.setRows(lista);
                    PrintWriter out;
                    try {
                        Gson gson = new Gson();
                        String jsonOutput = gson.toJson(responseJson);
                        out = res.getWriter();
                        out.println(jsonOutput);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                pagina = "/web/administracion/usuarios/administrarUsuario.jsp";
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