/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorEmpleado;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorFaena;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorTipoFaena;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.FaenaDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.TipoFaenaDTO;
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
public class AdministrarFaena extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (req != null && cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {
                    listarFaenas(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarFaena(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarFaena(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarFaena(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarFaena(req, res);
                } else if (req.getParameter("accion").equals("LISTADO_TIPO_FAENA")) {
                    listarTipoFaenas(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_FAENAS_COMBOTREE")) {
                    listarFaenasComboTree(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_TIPO_FAENA_COMBOTREE")) {
                    listarTipoFaenasComboTree(req, res);
                }
            } else {
                pagina = "/web/administracion/faenas/administrarFaena.jsp";
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

    private void listarFaenas(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        //CODIGO VARIABLE
        ControladorFaena cFaena = new ControladorFaena();
        List<FaenaDTO> lista = new ArrayList<FaenaDTO>();
        String where = "";

        //mas codigo
        lista = cFaena.getFaenas(fila, cantidad, where);

        //FIN CODIGO VARIABLE
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(lista);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarFaena(HttpServletRequest req, HttpServletResponse res) {
        Integer idFaena = 0, numeroTeam = 0, idTipoFaena = 0, idJefeFaena = 0, idCalibrador = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idFaena")) {
                idFaena = Integer.parseInt(req.getParameter("idFaena"));
            } else if (parameterNames.equals("numeroTeam")) {
                numeroTeam = Integer.parseInt(req.getParameter("numeroTeam"));
            } else if (parameterNames.equals("idTipoFaena")) {
                idTipoFaena = Integer.parseInt(req.getParameter("idTipoFaena"));
            } else if (parameterNames.equals("idJefeFaena")) {
                idJefeFaena = Integer.parseInt(req.getParameter("idJefeFaena"));
            } else if (parameterNames.equals("idCalibrador")) {
                idCalibrador = Integer.parseInt(req.getParameter("idCalibrador"));
            }
        }
        FaenaDTO faena = new FaenaDTO();
        faena.setIdFaena(idFaena);
        faena.setNumeroTeam(numeroTeam);
        faena.setIdTipoFaena(idTipoFaena);
        faena.setIdJefeFaena(idJefeFaena);
        faena.setIdCalibrador(idCalibrador);

        ControladorFaena cFaena = new ControladorFaena();
        FaenaDTO faenaTmp = cFaena.getFaenaByID(idFaena);//FAENA SIN ACTUALIZAR

        boolean jefeFaenaValido = false;
        boolean calibradorValido = false;
        if (faenaTmp.getIdJefeFaena() == idJefeFaena) {
            jefeFaenaValido = true;//No cambio el jefe de faena
        }
        if (faenaTmp.getIdCalibrador() == idCalibrador) {
            calibradorValido = true;//No cambio el calibrador
        }

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        if (idJefeFaena != idCalibrador) {
            //Validamos que el jefe de faena no tenga otra faena asignada
            FaenaDTO tempCalib = cFaena.getFaenaByIDCalibrador(idJefeFaena);
            FaenaDTO tempJefeF = cFaena.getFaenaByIDJefeFaena(idJefeFaena);
            if (jefeFaenaValido || tempCalib == null && tempJefeF == null) {
                //Validamos que el calibrador no tenga otra faena asignada
                tempCalib = cFaena.getFaenaByIDCalibrador(idCalibrador);
                tempJefeF = cFaena.getFaenaByIDJefeFaena(idCalibrador);
                if (calibradorValido || tempCalib == null && tempJefeF == null) {
                    responseJson.statusCode = cFaena.updateFaena(faena);
                    if (responseJson.statusCode == 1) {
                        responseJson.success = true;
                        mensaje = "Faena actualizada correctamente.";
                    } else {
                        responseJson.success = false;
                        mensaje = "Se a producido un error inesperado.";
                    }
                } else {
                    responseJson.success = false;
                    mensaje = "El Calibrador ya tiene una faena asociada.";
                }
            } else {
                responseJson.success = false;
                mensaje = "El Jefe de Faena ya tiene una faena asociada.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El Jefe de faena no puede ser Calibrador.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = faena;
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

    private void agregarFaena(HttpServletRequest req, HttpServletResponse res) {
        Integer idFaena = 0, numeroTeam = 0, idTipoFaena = 0, idJefeFaena = 0, idCalibrador = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("numeroTeam")) {
                numeroTeam = Integer.parseInt(req.getParameter("numeroTeam"));
            } else if (parameterNames.equals("idTipoFaena")) {
                idTipoFaena = Integer.parseInt(req.getParameter("idTipoFaena"));
            } else if (parameterNames.equals("idJefeFaena")) {
                idJefeFaena = Integer.parseInt(req.getParameter("idJefeFaena"));
            } else if (parameterNames.equals("idCalibrador")) {
                idCalibrador = Integer.parseInt(req.getParameter("idCalibrador"));
            }
        }
        FaenaDTO faena = new FaenaDTO();
        faena.setNumeroTeam(numeroTeam);
        faena.setIdTipoFaena(idTipoFaena);
        faena.setIdJefeFaena(idJefeFaena);
        faena.setIdCalibrador(idCalibrador);

        ControladorFaena cFaena = new ControladorFaena();

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        if (idJefeFaena != idCalibrador) {
            //VALIDAMOS LA DISPONIBILIDAD DEL JEFE DE FAENA - SIN NINGUNA FAENA A CARGO
            FaenaDTO tempCalib = cFaena.getFaenaByIDCalibrador(idJefeFaena);
            FaenaDTO tempJefeF = cFaena.getFaenaByIDJefeFaena(idJefeFaena);
            if (tempCalib == null && tempJefeF == null) {
                //VALIDAMOS LA DISPONIBILIDAD DEL CALIBRADOR - SIN NINGUNA FAENA A CARGO
                tempCalib = cFaena.getFaenaByIDCalibrador(idCalibrador);
                tempJefeF = cFaena.getFaenaByIDJefeFaena(idCalibrador);
                if (tempCalib == null && tempJefeF == null) {

                    responseJson.statusCode = cFaena.saveFaena(faena);
                    if (responseJson.statusCode > 0) {
                        responseJson.success = true;
                        mensaje = "Faena guardado correctamente.";
                    } else {
                        responseJson.success = false;
                        mensaje = "Se a producido un error inesperado.";
                    }
                } else {
                    responseJson.success = false;
                    mensaje = "El Calibrador ya tiene una faena asociada.";
                }
            } else {
                responseJson.success = false;
                mensaje = "El Jefe de Faena ya tiene una faena asociada.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El Jefe de faena no puede ser Calibrador.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = faena;
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

    private void borrarFaena(HttpServletRequest req, HttpServletResponse res) {
        String json = req.getParameter("faena");
        Gson gson = new Gson();
        FaenaDTO faena = gson.fromJson(json, FaenaDTO.class);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        ControladorFaena cFaena = new ControladorFaena();
        responseJson.statusCode = cFaena.removeFaena(faena.getIdFaena());
        if (responseJson.statusCode != 0) {
            responseJson.success = true;
            mensaje = "La faena fue eliminada correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = faena;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscarFaena(HttpServletRequest req, HttpServletResponse res) {
        String q = "";
        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("q")) {
                q = req.getParameter("q");
            }
        }

        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorFaena cFaena = new ControladorFaena();
        List<FaenaDTO> lista = new ArrayList<FaenaDTO>();
        String where = " WHERE f.numeroTeam LIKE '%" + q + "%' OR f.tipoFaena LIKE '%" + q + "%' ";

        //mas codigo
        lista = cFaena.getFaenas(fila, cantidad, where);

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

    private void listarTipoFaenas(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorTipoFaena cTipoFaena = new ControladorTipoFaena();
        List<TipoFaenaDTO> lista = new ArrayList<TipoFaenaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cTipoFaena.getAllTipoFaena(where);

        //FIN CODIGO VARIABLE
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(lista);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void listarTipoFaenasComboTree(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorTipoFaena cTipoFaena = new ControladorTipoFaena();
        List<TipoFaenaDTO> lista = new ArrayList<TipoFaenaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cTipoFaena.getAllTipoFaena(where);

        List<ComboTree> comboOpciones = new ArrayList<ComboTree>();
        for (int i = 0; i < lista.size(); i++) {
            ComboTree combo = new ComboTree();
            combo.setId(lista.get(i).getIdTipoFaena());
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

    private void listarFaenasComboTree(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorFaena cFaena = new ControladorFaena();
        List<FaenaDTO> lista = new ArrayList<FaenaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cFaena.getAllFaenas(where);

        List<ComboTree> comboOpciones = new ArrayList<ComboTree>();
        for (int i = 0; i < lista.size(); i++) {
            ComboTree combo = new ComboTree();
            combo.setId(lista.get(i).getIdFaena());
            combo.setText(lista.get(i).getDescripcionFaena());
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
