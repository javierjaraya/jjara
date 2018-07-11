/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.reporte;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorCabezal;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorJornada;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMaquina;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroHarvester;
import cl.forestcenter.sistema.dto.CabezalDTO;
import cl.forestcenter.sistema.dto.JornadaDTO;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import cl.forestcenter.sistema.dto.RegistroHarvesterDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
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
public class AdministrarReporteHarvester extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("REGISTRAR_REPORTE_HARVESTER")) {
                    registrarReporteHarvester(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_JORNADAS_COMBOTREE")) {
                    listarJornadasComboTree(req, res);
                }
            } else {
                pagina = "/web/reporte/harvester/administrarReporteHarvester.jsp";
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

    private void registrarReporteHarvester(HttpServletRequest req, HttpServletResponse res) {
        Date fecha = null;
        String observacion = "";
        Integer idJornada = 0, idOperador = 0, idFaena = 0, idPredio = 0, idMaquina = 0, turno = 0, arbolesOperativos = 0;
        double m3Operativos = 0.0, horometroInicial = 0.0, horometroFinal = 0.0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("fecha")) {
                fecha = Date.valueOf(req.getParameter("fecha"));
            } else if (parameterNames.equals("idJornada")) {
                idJornada = Integer.parseInt(req.getParameter("idJornada"));
            } else if (parameterNames.equals("idOperador")) {
                idOperador = Integer.parseInt(req.getParameter("idOperador"));
            } else if (parameterNames.equals("idMaquina")) {
                idMaquina = Integer.parseInt(req.getParameter("idMaquina"));
            } else if (parameterNames.equals("idFaena")) {
                idFaena = Integer.parseInt(req.getParameter("idFaena"));
            } else if (parameterNames.equals("idPredio")) {
                idPredio = Integer.parseInt(req.getParameter("idPredio"));
            } else if (parameterNames.equals("turno")) {
                turno = Integer.parseInt(req.getParameter("turno"));
            } else if (parameterNames.equals("horometroInicial")) {
                horometroInicial = Double.parseDouble(req.getParameter("horometroInicial"));
            } else if (parameterNames.equals("horometroFinal")) {
                horometroFinal = Double.parseDouble(req.getParameter("horometroFinal"));
            } else if (parameterNames.equals("arbolesOperativos")) {
                arbolesOperativos = Integer.parseInt(req.getParameter("arbolesOperativos"));
            } else if (parameterNames.equals("m3Operativos")) {
                m3Operativos = Double.parseDouble(req.getParameter("m3Operativos"));
            } else if (parameterNames.equals("observacion")) {
                observacion = req.getParameter("observacion");
            }
        }

        RegistroHarvesterDTO registro = new RegistroHarvesterDTO();

        registro.setFechaRegistro(fecha);
        registro.setIdFaena(idFaena);
        registro.setIdMaquina(idMaquina);
        registro.setIdOperador(idOperador);
        registro.setTurno(turno == 5 ? "Medio Turno" : "Turno Completo");
        registro.setIdJornada(idJornada);
        registro.setIdPredio(idPredio);
        registro.setHorometroInicial(horometroInicial);
        registro.setHorometroFinal(horometroFinal);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        try {
            //AUMENTAMOS EL HOROMETRO DE LA MAQUINA
            ControladorMaquina cMaquina = new ControladorMaquina();
            MaquinaDTO maquina = cMaquina.getByIdMaquina(idMaquina);
            maquina.setHorometro(horometroFinal);

            //AUMENTAMOS EL HOROMETRO DEL CABEZAL
            ControladorCabezal cCabezal = new ControladorCabezal();
            CabezalDTO cabezal = cCabezal.getByIdCabezal(maquina.getIdCabezal());
            double horometoIn = cabezal.getHorometro();
            cabezal.setHorometro(horometoIn + (horometroFinal - horometroInicial));

            registro.sethMaqPlan(turno);//5 o 10
            registro.sethMaqReal(horometroFinal, horometroInicial);

            registro.setArbReal(arbolesOperativos);//DEL REPORTE       arboles operativos
            registro.setmReal(m3Operativos);//Del Reporte           m3 operativos

            registro.setmArbol(registro.getmReal(), registro.getArbReal());//MAS ABAJO setArbReal
            registro.setArbHrPlanCalcular(registro.getmArbol());//ABAJO del de arriba
            registro.setArbHrReal(registro.getArbReal(), registro.gethMaqReal());
            registro.setArbPlan(registro.gethMaqPlan(), registro.getArbHrPlan());

            registro.setmPlan(registro.getmArbol(), registro.getArbPlan());
            registro.setmHrPlan(registro.getmPlan(), registro.gethMaqPlan());//BAJO setmPlan()
            registro.setmHrReal(registro.getmReal(), registro.gethMaqReal());//BAJO setmReal()

            registro.setObservacion(observacion);

            ControladorRegistroHarvester cRegistroHarvester = new ControladorRegistroHarvester();

            Integer folioAsignado = cRegistroHarvester.getFolio();
            registro.setFolio(folioAsignado);
            responseJson.statusCode = cRegistroHarvester.saveRegistroHarvester(registro);
            if (responseJson.statusCode > 0) {
                cMaquina.updateMaquina(maquina);
                cCabezal.updateMaquina(cabezal);

                responseJson.success = true;
                mensaje = "Reporte enviado correctamente. (Folio: " + folioAsignado + ")";
            } else {
                responseJson.success = false;
                mensaje = "Se a producido un error inesperado.";
            }
        } catch (NullPointerException ex) {
            responseJson.success = false;
            mensaje = "Los valores ingresados no permiten calcular los Arb/Hora Plan";
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
