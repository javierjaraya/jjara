/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.reporte;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroNOC;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.RegistroNocDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.servlet.web.administrar.*;
import cl.forestcenter.sistema.util.ComboTree;
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
public class AdministrarReporteNOC extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("REGISTRAR_REPORTE_NOC")) {
                    registrarReporteNOC(req, res);
                }
            } else {
                pagina = "/web/reporte/noc/administrarReporteNOC.jsp";
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

    private void registrarReporteNOC(HttpServletRequest req, HttpServletResponse res) {
        Date fecha = null;
        String observacion = "";
        Integer idFaena = 0, idPredio = 0, idMaquina = 0, idOperador = 0;
        double  m3 = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("fecha")) {
                fecha = Date.valueOf(req.getParameter("fecha"));
            } else if (parameterNames.equals("idMaquina")){
                idMaquina = Integer.parseInt(req.getParameter("idMaquina"));
            } else if (parameterNames.equals("idFaena")) {
                idFaena = Integer.parseInt(req.getParameter("idFaena"));
            } else if (parameterNames.equals("idPredio")) {
                idPredio = Integer.parseInt(req.getParameter("idPredio"));
            } else if (parameterNames.equals("m3")) {
                m3 = Double.parseDouble(req.getParameter("m3"));
            } else if (parameterNames.equals("observacion")) {
                observacion = req.getParameter("observacion");
            } else if (parameterNames.equals("idOperador")) {
                idOperador = Integer.parseInt(req.getParameter("idOperador"));
            }
        }
        RegistroNocDTO registro = new RegistroNocDTO();
        registro.setFechaRegistro(fecha);
        registro.setIdFaena(idFaena);//TIPO FAENA
        registro.setIdMaquina(idMaquina);
        registro.setIdOperador(idOperador);
        registro.setIdPredio(idPredio);
        registro.setM3(m3); 
        registro.setObservacion(observacion);
        
        
        ControladorRegistroNOC cRegistroNOC = new ControladorRegistroNOC();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        responseJson.statusCode = cRegistroNOC.saveRegistroNOC(registro);
        if (responseJson.statusCode > 0) {
            responseJson.success = true;
            mensaje = "Reporte enviado correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = registro;
        responseJson.statusText = mensaje;
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
