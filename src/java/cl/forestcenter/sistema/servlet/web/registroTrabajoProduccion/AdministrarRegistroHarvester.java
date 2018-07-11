/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.registroTrabajoProduccion;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMaquina;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroForwarder;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroHarvester;
import cl.forestcenter.sistema.dto.CabezalDTO;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.RegistroForwarderDTO;
import cl.forestcenter.sistema.dto.RegistroHarvesterDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.servlet.web.administrar.*;
import cl.forestcenter.sistema.servlet.web.reporte.*;
import cl.forestcenter.sistema.servlet.web.tableroGestion.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class AdministrarRegistroHarvester extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {
                    listarRegistroHarvester(req, res);
                }
            } else {
                pagina = "/web/registroTrabajoProduccion/harvester/administrarRegistroHarvester.jsp";
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

    private void listarRegistroHarvester(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorRegistroHarvester cRegistroHarvester = new ControladorRegistroHarvester();
        List<RegistroHarvesterDTO> lista = new ArrayList<RegistroHarvesterDTO>();
        String where = "";
        
        int page = fila;
        int rows = cantidad;
        try {
            page = Integer.parseInt(req.getParameter("page"));//1
        } catch (Exception e) {
        }
        try {
            rows = Integer.parseInt(req.getParameter("rows"));//10
        } catch (Exception e) {
        }
        //mas codigo
        lista = cRegistroHarvester.getRegistroHarvester(page, rows, where);
        
        
        //FIN CODIGO VARIABLE
        int size = cRegistroHarvester.cantidadRegistrosHarvester("");
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
}
