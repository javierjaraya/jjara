/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorArea;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorPredio;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorZona;
import cl.forestcenter.sistema.dto.AreaDTO;
import cl.forestcenter.sistema.dto.PredioDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.ZonaDTO;
import cl.forestcenter.sistema.util.ComboTree;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AdministrarPredio extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (req != null && req.isRequestedSessionIdValid() && cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";
            String mensaje1 = "";
            String mensaje2 = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {
                    listarPredios(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_ALL_PREDIO")) {
                    listarAllPredios(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarPredio(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarPredio(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarPredio(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarPredio(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_ZONAS")) {
                    listarZonas(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_AREAS")) {
                    listarAreas(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_PREDIOS_COMBOTREE")){
                    listarPrediosComboTree(req,res);
                }
            } else {
                pagina = "/web/administracion/predios/administrarPredio.jsp";
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

    private void listarPredios(HttpServletRequest req, HttpServletResponse res) {
        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);
        String q = req.getParameter("q");

        int page = Integer.parseInt(req.getParameter("page"));//1
        int rows = Integer.parseInt(req.getParameter("rows"));//10
        
        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE

        ControladorPredio cPredios = new ControladorPredio();
        
        List<PredioDTO> lista = new ArrayList<PredioDTO>();

        String where = " WHERE 1=1 ";

        if (q != null) {
            where += " and P.nombre LIKE '%" + q + "%' OR A.nombre LIKE '%" + q + "%' OR Z.nombre LIKE '%" + q + "%' ";
        }
        //SIN FILTRO
        int cantidadRegistros = cPredios.cantidadPredios(where);
        //mas codigo
        lista = cPredios.getPredios(page, rows, where);

        //FIN CODIGO VARIABLE
        responseJson.setTotal(cantidadRegistros);
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

    private void listarAllPredios(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorPredio cPredios = new ControladorPredio();
        
        List<PredioDTO> lista = new ArrayList<PredioDTO>();

        String where = " WHERE 1=1 ";
        //mas codigo
        lista = cPredios.getAllPredios(where);

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
    
    private void guardarPredio(HttpServletRequest req, HttpServletResponse res) {
        String nombre = "";
        Integer idPredio = 0, idArea = 0, idZona = 0, estado = 0;
        Double superficie = 0.0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idPredio")) {
                idPredio = Integer.parseInt(req.getParameter("idPredio"));
            } else if (parameterNames.equals("nombre")) {
                nombre = req.getParameter("nombre");
            } else if (parameterNames.equals("idArea")) {
                idArea = Integer.parseInt(req.getParameter("idArea"));
            } else if (parameterNames.equals("idZona")) {
                idZona = Integer.parseInt(req.getParameter("idZona"));
            } else if (parameterNames.equals("superficie")) {
                superficie = Double.parseDouble(req.getParameter("superficie"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        PredioDTO predio = new PredioDTO();
        predio.setIdPredio(idPredio);
        predio.setNombre(nombre);
        predio.setIdArea(idArea);
        predio.setIdZona(idZona);
        predio.setSuperficie(superficie);
        predio.setEstado(estado);

        ControladorPredio cPredio = new ControladorPredio();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        if (cPredio.updatePredio(predio)) {
            responseJson.success = true;
            mensaje = "Predio actualizado correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = predio;
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

    private void agregarPredio(HttpServletRequest req, HttpServletResponse res) {
        String nombre = "";
        Integer idArea = 0, idZona = 0, estado = 0;
        Double superficie = 0.0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("nombre")) {
                nombre = req.getParameter("nombre");
            } else if (parameterNames.equals("idArea")) {
                idArea = Integer.parseInt(req.getParameter("idArea"));
            } else if (parameterNames.equals("idZona")) {
                idZona = Integer.parseInt(req.getParameter("idZona"));
            } else if (parameterNames.equals("superficie")) {
                superficie = Double.parseDouble(req.getParameter("superficie"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        PredioDTO predio = new PredioDTO();
        predio.setNombre(nombre);
        predio.setIdArea(idArea);
        predio.setIdZona(idZona);
        predio.setSuperficie(superficie);
        predio.setEstado(estado);
        
        ControladorPredio cPredio = new ControladorPredio();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        if (cPredio.savePredio(predio)) {
            responseJson.success = true;
            mensaje = "El predio fue creado correctamente";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = predio;
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

    private void borrarPredio(HttpServletRequest req, HttpServletResponse res) {
        String json = req.getParameter("predio");
        Gson gson = new Gson();
        PredioDTO predio = gson.fromJson(json, PredioDTO.class);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        ControladorPredio cPredio = new ControladorPredio();
        if (cPredio.removePredio(predio.getIdPredio())) {
            responseJson.success = true;
            mensaje = "Predio eliminado correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = predio;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscarPredio(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorPredio cPredio = new ControladorPredio();
        List<PredioDTO> lista = new ArrayList<PredioDTO>();
        String where = " WHERE 1=1 ";

        if (q != null) {
            where += " and P.idPredio LIKE '%" + q + "%' OR P.nombre LIKE '%" + q + "%' OR A.nombre LIKE '%" + q + "%' OR Z.nombre LIKE '%" + q + "%' ";
        }
        //SIN FILTRO
        int cantidadRegistros = cPredio.cantidadPredios(where);
        
        //mas codigo
        lista = cPredio.getPredios(fila, cantidad, where);

        //FIN CODIGO VARIABLE
        int size = lista.size();
        responseJson.setTotal(cantidadRegistros);
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

    private void listarZonas(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorZona cZona = new ControladorZona();
        List<ZonaDTO> lista = new ArrayList<ZonaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cZona.getAllZonas(where);

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

    private void listarAreas(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorArea cArea = new ControladorArea();
        List<AreaDTO> lista = new ArrayList<AreaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cArea.getAllAreas(where);

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
    
    private void listarPrediosComboTree(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorPredio cPredio = new ControladorPredio();
        List<PredioDTO> lista = new ArrayList<PredioDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cPredio.getAllPredios(where);
        
        List<ComboTree> comboOpciones = new ArrayList<ComboTree>();
        for (int i = 0; i < lista.size(); i++) {
            ComboTree combo = new ComboTree();
            combo.setId(lista.get(i).getIdPredio());
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
