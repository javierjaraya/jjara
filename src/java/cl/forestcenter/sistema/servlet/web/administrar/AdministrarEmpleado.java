/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorArea;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorCargo;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorEmpleado;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorUsuario;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.CargoDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
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
public class AdministrarEmpleado extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";
            String mensaje1 = "";
            String mensaje2 = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {
                    listarEmpleados(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_ALL_EMPLEADOS")) {
                    listarAllEmpleados(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarEmpleado(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarEmpleado(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarEmpleado(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarEmpleado(req, res);
                } else if (req.getParameter("accion").equals("EMPLEADO_BY_RUT")) {
                    getEmpleadoByRut(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_CARGOS")){
                    listarCargos(req,res);
                } else if (req.getParameter("accion").equals("LISTAR_EMPLEADOS_COMBOTREE")){
                    listarEmpleadosComboTree(req,res);
                }
            } else {
                pagina = "/web/administracion/empleados/administrarEmpleado.jsp";
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

    private void listarEmpleados(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();
        String where = "";

        //mas codigo
        lista = cEmpleado.getEmpleados(fila, cantidad, where);

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

    private void listarAllEmpleados(HttpServletRequest req, HttpServletResponse res) {       
        //CODIGO VARIABLE
        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();
        String where = "";
        
        lista = cEmpleado.getAllEmpleados( where);

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

    private void guardarEmpleado(HttpServletRequest req, HttpServletResponse res) {
        String rut = "", nombres = "", apellidos = "", direccion = "", correo = "";
        Integer idEmpleado = 0, idCargo = 0, telefono = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idEmpleado")) {
                idEmpleado = Integer.parseInt(req.getParameter("idEmpleado").toString());
            } else if (parameterNames.equals("rut")) {
                rut = req.getParameter("rut");
            } else if (parameterNames.equals("nombres")) {
                nombres = req.getParameter("nombres");
            } else if (parameterNames.equals("apellidos")) {
                apellidos = req.getParameter("apellidos");
            } else if (parameterNames.equals("direccion")) {
                direccion = req.getParameter("direccion");
            } else if (parameterNames.equals("correo")) {
                correo = req.getParameter("correo");
            } else if (parameterNames.equals("idCargo")) {
                idCargo = Integer.parseInt(req.getParameter("idCargo"));
            } else if (parameterNames.equals("telefono")) {
                telefono = Integer.parseInt(req.getParameter("telefono"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setIdEmpleado(idEmpleado);
        empleado.setRut(rut);
        empleado.setNombres(nombres);
        empleado.setApellidos(apellidos);
        empleado.setDireccion(direccion);
        empleado.setCorreo(correo);
        empleado.setIdCargo(idCargo);
        empleado.setTelefono(telefono);
        empleado.setEstado(estado);

        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        if (cEmpleado.updateEmpleado(empleado)) {
            responseJson.success = true;
            mensaje = "Empleado actualizado correctamente.";//Datos actualizados correctamente.
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = empleado;
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

    private void agregarEmpleado(HttpServletRequest req, HttpServletResponse res) {
        String rut = "", nombres = "", apellidos = "", direccion = "", correo = "";
        Integer idEmpleado = 0, idCargo = 0, telefono = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("rut")) {
                rut = req.getParameter("rut");
            } else if (parameterNames.equals("nombres")) {
                nombres = req.getParameter("nombres");
            } else if (parameterNames.equals("apellidos")) {
                apellidos = req.getParameter("apellidos");
            } else if (parameterNames.equals("direccion")) {
                direccion = req.getParameter("direccion");
            } else if (parameterNames.equals("correo")) {
                correo = req.getParameter("correo");
            } else if (parameterNames.equals("idCargo")) {
                idCargo = Integer.parseInt(req.getParameter("idCargo"));
            } else if (parameterNames.equals("telefono")) {
                telefono = Integer.parseInt(req.getParameter("telefono"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setRut(rut);
        empleado.setNombres(nombres);
        empleado.setApellidos(apellidos);
        empleado.setDireccion(direccion);
        empleado.setCorreo(correo);
        empleado.setIdCargo(idCargo);
        empleado.setTelefono(telefono);
        empleado.setEstado(estado);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        EmpleadoDTO existe = cEmpleado.getByRut(empleado.getRut());
        if (existe == null) {
            if (cEmpleado.saveEmpleado(empleado)) {
                responseJson.success = true;
                mensaje = "Empleado guardado correctamente.";//Datos actualizados correctamente.
            } else {
                responseJson.success = false;
                mensaje = "Se a producido un error inesperado.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El rut ingresado ya esta registrado, intente nuevamente.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = empleado;
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

    private void borrarEmpleado(HttpServletRequest req, HttpServletResponse res) {
        String jsonEmpleado = req.getParameter("empleado");
        Gson gson = new Gson();
        EmpleadoDTO empleado = gson.fromJson(jsonEmpleado, EmpleadoDTO.class);

        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        if (cEmpleado.removeEmpleado(empleado.getIdEmpleado())) {

        }
    }

    private void buscarEmpleado(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();
        String where = " WHERE e.rut LIKE '%" + q + "%' OR e.nombres LIKE '%" + q + "%' OR e.apellidos LIKE '%" + q + "%' OR c.nombre LIKE '%" + q + "%' or e.correo LIKE '%" + q + "%' OR e.direccion LIKE '%" + q + "%' OR e.telefono LIKE '%" + q + "%' ";

        //mas codigo
        lista = cEmpleado.getEmpleados(fila, cantidad, where);

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

    private void getEmpleadoByRut(HttpServletRequest req, HttpServletResponse res) {
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

        //CODIGO VARIABLE
        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();
        String where = " WHERE e.rut = '" + q + "' ";

        //mas codigo
        lista = cEmpleado.getEmpleados(fila, cantidad, where);

        if (lista.size() > 0) {
            //FIN CODIGO VARIABLE
            res.setCharacterEncoding("UTF-8");
            PrintWriter out;
            try {
                Gson gson = new Gson();
                String jsonOutput = gson.toJson(lista.get(0));
                out = res.getWriter();
                out.println(jsonOutput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void listarCargos(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorCargo cCargo = new ControladorCargo();
        List<CargoDTO> lista = new ArrayList<CargoDTO>();

        String where = " where 1=1 ";
        
        //mas codigo
        lista = cCargo.getAllCargos(where);

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

    private void listarEmpleadosComboTree(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorEmpleado cEmpleado = new ControladorEmpleado();
        List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();

        String where = " ";

        //mas codigo
        lista = cEmpleado.getAllEmpleados(where);

        List<ComboTree> comboOpciones = new ArrayList<ComboTree>();
        for (int i = 0; i < lista.size(); i++) {
            ComboTree combo = new ComboTree();
            combo.setId(lista.get(i).getIdEmpleado());
            combo.setText(lista.get(i).getNombreCompleto());
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
