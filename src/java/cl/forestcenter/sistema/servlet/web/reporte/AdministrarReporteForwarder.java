/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.reporte;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorCabezal;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorEmpleado;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorFaena;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorJornada;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMaquina;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroForwarder;
import cl.forestcenter.sistema.dto.CabezalDTO;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.FaenaDTO;
import cl.forestcenter.sistema.dto.JornadaDTO;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.RegistroForwarderDTO;
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
public class AdministrarReporteForwarder extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("REGISTRAR_REPORTE_FORWARDER")) {
                    registrarReporteForwarder(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_JORNADAS_COMBOTREE")){
                    listarJornadasComboTree(req,res);
                }
            } else {
                pagina = "/web/reporte/forwarder/administrarReporteForwarder.jsp";
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

    private void registrarReporteForwarder(HttpServletRequest req, HttpServletResponse res) {
        Date fecha = null;
        String observacion = "";
        Integer foleo = 0,idJornada = 0, idOperador = 0, idFaena = 0, idPredio = 0, idMaquina = 0,turno = 0, ciclos = 0;
        double largo = 0.0, horometroInicial = 0.0, horometroFinal = 0.0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("fecha")) {
                fecha = Date.valueOf(req.getParameter("fecha"));
            } else if (parameterNames.equals("idJornada")) {
                idJornada = Integer.parseInt(req.getParameter("idJornada"));
            } else if (parameterNames.equals("idOperador")) {
                idOperador = Integer.parseInt(req.getParameter("idOperador"));
            } else if (parameterNames.equals("idMaquina")){
                idMaquina = Integer.parseInt(req.getParameter("idMaquina"));
            } else if (parameterNames.equals("idFaena")) {
                idFaena = Integer.parseInt(req.getParameter("idFaena"));
            } else if (parameterNames.equals("idPredio")) {
                idPredio = Integer.parseInt(req.getParameter("idPredio"));
            } else if (parameterNames.equals("turno")) {
                turno = Integer.parseInt(req.getParameter("turno"));
            } else if (parameterNames.equals("largo")) {
                largo = Double.parseDouble(req.getParameter("largo"));
            } else if (parameterNames.equals("horometroInicial")) {
                horometroInicial = Double.parseDouble(req.getParameter("horometroInicial"));
            } else if (parameterNames.equals("horometroFinal")) {
                horometroFinal = Double.parseDouble(req.getParameter("horometroFinal"));
            } else if (parameterNames.equals("ciclos")) {
                ciclos = Integer.parseInt(req.getParameter("ciclos"));
            } else if (parameterNames.equals("observacion")) {
                observacion = req.getParameter("observacion");
            }
        }
        RegistroForwarderDTO registro = new RegistroForwarderDTO();
        registro.setFechaRegistro(fecha);
        registro.setIdFaena(idFaena);
        registro.setIdMaquina(idMaquina);
        registro.setIdOperador(idOperador);        
        registro.setTurno(turno == 5 ? "Medio Turno" : "Turno Completo");
        registro.setIdJornada(idJornada);
        registro.setIdPredio(idPredio);
        registro.setHorometroInicial(horometroInicial);
        registro.setHorometroFinal(horometroFinal);

        registro.sethMaqPlan(turno);//5 o 10
        registro.sethMaqReal(horometroFinal,horometroInicial);
        
        ControladorFaena cFaena = new ControladorFaena();
        FaenaDTO faena = cFaena.getFaenaByID(idFaena);
        registro.setCicloHrPlan(faena.getIdTipoFaena(), largo);//BUSCAR EL TIPO DE FAENA
        
        registro.setCicloHrReal(ciclos, registro.gethMaqReal());
        registro.setCicloPlan(registro.getCicloHrPlan(), registro.gethMaqPlan());
        registro.setCicloReal(ciclos);
                
        //ACTUALIZAR HOROMETRO MAQUINA
        ControladorMaquina cMaquina = new ControladorMaquina();
        MaquinaDTO maquina = cMaquina.getByIdMaquina(idMaquina);
        maquina.setHorometro(horometroFinal);
        cMaquina.updateMaquina(maquina);
        
        registro.setmHrPlan(maquina.getCodigoMaquina(),registro.getCicloHrPlan(),largo);
        
        registro.setmReal(maquina.getCodigoMaquina(),registro.getCicloReal(), largo);
        
        registro.setmHrReal(registro.getmReal(),registro.gethMaqReal());
        registro.setmPlan(registro.gethMaqPlan(),registro.getmHrPlan()); 
        registro.setObservacion(observacion);
        
        
        ControladorRegistroForwarder cRegistroForwarder = new ControladorRegistroForwarder();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        Integer folioAsignado = cRegistroForwarder.getFolio();
        registro.setFolio(folioAsignado);
        responseJson.statusCode = cRegistroForwarder.saveRegistroForwarder(registro);
        if (responseJson.statusCode > 0) {
            responseJson.success = true;
            mensaje = "Reporte enviado correctamente.  (Folio: "+folioAsignado+")";
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
        
    private void listarJornadasComboTree(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorJornada cJornada = new ControladorJornada();
        List<JornadaDTO> lista = new ArrayList<JornadaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cJornada.getAllJornadas(where);
        
        List<ComboTree> comboOpciones = new ArrayList<ComboTree>();
        for (int i = 0; i < lista.size(); i++) {
            ComboTree combo = new ComboTree();
            combo.setId(lista.get(i).getIdJornada());
            combo.setText(lista.get(i).getNombre());
            comboOpciones.add(combo);
        }
        //FIN CODIGO VARIABLE
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(comboOpciones);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
