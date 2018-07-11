/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorCabezal;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorEmpleado;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMaquina;
import cl.forestcenter.sistema.dto.CabezalDTO;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
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
public class AdministrarCabezal extends HttpServlet {

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
                    listarCabezales(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_ALL_CABEZALES")) {
                    listarAllCabezales(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarCabezal(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarCabezal(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarCabezal(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarCabezal(req, res);
                }
            } else {
                pagina = "/web/administracion/cabezales/administrarCabezal.jsp";
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

    private void listarCabezales(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorCabezal cCabezal = new ControladorCabezal();
        List<CabezalDTO> lista = new ArrayList<CabezalDTO>();
        String where = " WHERE 1=1 AND C.idCabezal != 1";

        //mas codigo
        lista = cCabezal.getCabezales(fila, cantidad, where);

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
    
    private void listarAllCabezales(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorCabezal cCabezal = new ControladorCabezal();
        List<CabezalDTO> lista = new ArrayList<CabezalDTO>();
        String where = "";

        //mas codigo
        lista = cCabezal.getAllCabezales(where);

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

    private void guardarCabezal(HttpServletRequest req, HttpServletResponse res) {
        String patente = "", modelo = "", nChasis = "";
        Integer idCabezal = 0, año = 0, horometro = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idCabezal")) {
                idCabezal = Integer.parseInt(req.getParameter("idCabezal"));
            } else if (parameterNames.equals("patente")) {
                patente = req.getParameter("patente");
            } else if (parameterNames.equals("modelo")) {
                modelo = req.getParameter("modelo");
            } else if (parameterNames.equals("numeroChasis")) {
                nChasis = req.getParameter("numeroChasis");
            } else if (parameterNames.equals("año")) {
                año = Integer.parseInt(req.getParameter("año"));
            } else if (parameterNames.equals("horometro")) {
                horometro = Integer.parseInt(req.getParameter("horometro"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        CabezalDTO cabezal = new CabezalDTO();
        cabezal.setIdCabezal(idCabezal);
        cabezal.setPatente(patente);
        cabezal.setModelo(modelo);
        cabezal.setNumeroChasis(nChasis);
        cabezal.setAño(año);
        cabezal.setHorometro(horometro);
        cabezal.setEstado(estado);

        ControladorCabezal cCabezal = new ControladorCabezal();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        CabezalDTO cabezalActual = cCabezal.getByIdCabezal(idCabezal);
        boolean patenteValida = false;
        if (cabezalActual.getPatente().equalsIgnoreCase(patente)) {
            patenteValida = true;
        }
        boolean nChasisValido = false;
        if (cabezalActual.getNumeroChasis().equalsIgnoreCase(nChasis)) {
            nChasisValido = true;
        }

        if (patenteValida || cCabezal.getByPatente(patente) == null) {
            if (nChasisValido || cCabezal.getByNumeroChasis(nChasis) == null) {
                responseJson.statusCode = cCabezal.updateMaquina(cabezal);
                if (responseJson.statusCode > 0) {
                    responseJson.success = true;
                    mensaje = "Cabezal actualizada correctamente.";
                } else {
                    responseJson.success = false;
                    mensaje = "Se a producido un error inesperado.";
                }
            } else {
                responseJson.success = false;
                mensaje = "Ya existe un cabezal registrado con este numero de chasis, intente nuevamente.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El patente ingresada ya esta registrado, intente nuevamente.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = cabezal;
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

    private void agregarCabezal(HttpServletRequest req, HttpServletResponse res) {
        String patente = "", modelo = "", nChasis = "";
        Integer idCabezal = 0, año = 0, horometro = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("patente")) {
                patente = req.getParameter("patente");
            } else if (parameterNames.equals("modelo")) {
                modelo = req.getParameter("modelo");
            } else if (parameterNames.equals("numeroChasis")) {
                nChasis = req.getParameter("numeroChasis");
            } else if (parameterNames.equals("año")) {
                año = Integer.parseInt(req.getParameter("año"));
            } else if (parameterNames.equals("horometro")) {
                horometro = Integer.parseInt(req.getParameter("horometro"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        CabezalDTO cabezal = new CabezalDTO();
        cabezal.setIdCabezal(idCabezal);
        cabezal.setPatente(patente);
        cabezal.setModelo(modelo);
        cabezal.setNumeroChasis(nChasis);
        cabezal.setAño(año);
        cabezal.setHorometro(horometro);
        cabezal.setEstado(estado);

        ControladorCabezal cCabezal = new ControladorCabezal();

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        CabezalDTO existe = cCabezal.getByPatente(cabezal.getPatente());
        if (existe == null) {
            existe = cCabezal.getByNumeroChasis(cabezal.getNumeroChasis());
            if (existe == null) {
                responseJson.statusCode = cCabezal.saveMaquina(cabezal);
                if (responseJson.statusCode > 0) {
                    responseJson.success = true;
                    mensaje = "Cabezal agregado correctamente.";
                } else {
                    responseJson.success = false;
                    mensaje = "Se a producido un error inesperado.";
                }
            } else {
                responseJson.success = false;
                mensaje = "Ya existe un cabezal registrado con este numero de chasis, intente nuevamente.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El patente ingresada ya esta registrado, intente nuevamente.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = cabezal;
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

    private void borrarCabezal(HttpServletRequest req, HttpServletResponse res) {
        String json = req.getParameter("cabezal");
        Gson gson = new Gson();
        CabezalDTO cabezal = gson.fromJson(json, CabezalDTO.class);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        ControladorCabezal cCabezal = new ControladorCabezal();
        responseJson.statusCode = cCabezal.removeMaquina(cabezal);
        if (responseJson.statusCode != 0) {
            responseJson.success = true;
            mensaje = "El cabezal fue eliminado correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = cabezal;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscarCabezal(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorCabezal cCabezal = new ControladorCabezal();
        List<CabezalDTO> lista = new ArrayList<CabezalDTO>();
        String where = " WHERE C.patente LIKE '%" + q + "%' OR C.modelo LIKE '%" + q + "%' OR C.n_chasis LIKE '%" + q + "%' OR C.año LIKE '%" + q + "%' OR C.horometro LIKE '%" + q + "%' OR C.estado LIKE '%" + q + "%' ";
 
        //mas codigo
        lista = cCabezal.getCabezales(fila, cantidad, where);

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

}
