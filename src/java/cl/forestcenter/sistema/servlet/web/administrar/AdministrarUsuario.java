/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorEmpleado;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorUsuario;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
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
public class AdministrarUsuario extends HttpServlet {

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
                    listarUsuarios(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarUsuario(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarUsuario(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarUsuario(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarUsuario(req, res);
                } else if (req.getParameter("accion").equals("CAMBIAR_CLAVE")) {
                    cambiarClave(req, res);
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

    private void listarUsuarios(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);
        String nombres = req.getParameter("nombres");

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorUsuario cUsuario = new ControladorUsuario();
        List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();

        String where = " where 1=1 ";

        if (nombres != null) {
            where += " and nombres LIKE '%" + nombres + "%' ";
        }
        //SIN FILTRO

        //mas codigo
        lista = cUsuario.getUsuarios(fila, cantidad, where);

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

    private void guardarUsuario(HttpServletRequest req, HttpServletResponse res) {
        String nombreUsuario = "", password = "";
        Integer idUsuario = 0, idEmpleado = 0, idPerfil = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idUsuario")) {
                idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
            } else if (parameterNames.equals("idEmpleado")) {
                idEmpleado = Integer.parseInt(req.getParameter("idEmpleado"));
            } else if (parameterNames.equals("nombreUsuario")) {
                nombreUsuario = req.getParameter("nombreUsuario");
            } else if (parameterNames.equals("password")) {
                password = req.getParameter("password");
            } else if (parameterNames.equals("idPerfil")) {
                idPerfil = Integer.parseInt(req.getParameter("idPerfil"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setIdUsuario(idUsuario);
        usuario.setNombreUsuario(nombreUsuario.toLowerCase());
        usuario.setPassword(password);
        usuario.setIdEmpleado(idEmpleado);
        usuario.setIdPerfil(idPerfil);
        usuario.setEstado(estado);

        ControladorUsuario cUsuario = new ControladorUsuario();
        
        UsuarioDTO usuarioActual = cUsuario.getUsuarioByIdUsuario(idUsuario);
        boolean validarUsuario = false;
        if(usuarioActual.getNombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario())){
            validarUsuario = true;
        }
        
        UsuarioDTO temp = cUsuario.getUsuarioByIdEmpleado(usuario.getIdEmpleado());//Obtenemos un usuasio si existe
        
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        if (temp == null || temp.getIdUsuario() == usuario.getIdUsuario()) {
            //Validamos que no se repita el nombre de usuario
            temp = cUsuario.getUsuarioByNombreUsuario(usuario.getNombreUsuario());
            if (validarUsuario || temp == null) {
                if (cUsuario.actualizarUsuario(usuario)) {
                    responseJson.success = true;
                    mensaje = "El usuario fue actualizado correctamente";
                } else {
                    responseJson.success = false;
                    mensaje = "Se a producido un error inesperado.";
                }
            } else {
                responseJson.success = false;
                mensaje = "El nombre de usuario \"" + nombreUsuario + "\" ya esta en uso.";
            }
        } else {
            responseJson.success = false;
            mensaje = "EL empleado ya tiene un usuario asociado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = usuario;
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

    private void agregarUsuario(HttpServletRequest req, HttpServletResponse res) {
        String nombreUsuario = "", password = "";
        Integer idUsuario = 0, idEmpleado = 0, idPerfil = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idEmpleado")) {
                idEmpleado = Integer.parseInt(req.getParameter("idEmpleado"));
            } else if (parameterNames.equals("nombreUsuario")) {
                nombreUsuario = req.getParameter("nombreUsuario");
            } else if (parameterNames.equals("password")) {
                password = req.getParameter("password");
            } else if (parameterNames.equals("idPerfil")) {
                idPerfil = Integer.parseInt(req.getParameter("idPerfil"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombreUsuario(nombreUsuario.toLowerCase());
        usuario.setPassword(password);
        usuario.setIdEmpleado(idEmpleado);
        usuario.setIdPerfil(idPerfil);
        usuario.setEstado(estado);

        ControladorUsuario cUsuario = new ControladorUsuario();

        UsuarioDTO temp = cUsuario.getUsuarioByIdEmpleado(usuario.getIdEmpleado());//Obtenemos un usuasio si existe

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        if (temp == null) {
            //Validamos que no se repita el nombre de usuario
            temp = cUsuario.getUsuarioByNombreUsuario(usuario.getNombreUsuario());
            if (temp == null) {
                if (cUsuario.guardarUsuario(usuario)) {
                    responseJson.success = true;
                    mensaje = "El usuario fue creado correctamente";
                } else {
                    responseJson.success = false;
                    mensaje = "Se a producido un error inesperado.";
                }
            } else {
                responseJson.success = false;
                mensaje = "El nombre de usuario \"" + nombreUsuario + "\" ya esta en uso.";
            }
        } else {
            responseJson.success = false;
            mensaje = "EL empleado ya tiene un usuario asociado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = usuario;
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

    private void borrarUsuario(HttpServletRequest req, HttpServletResponse res) {
        String jsonUSuario = req.getParameter("usuario");
        Gson gson = new Gson();
        UsuarioDTO usuario = gson.fromJson(jsonUSuario, UsuarioDTO.class);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        ControladorUsuario cUsuario = new ControladorUsuario();
        if (cUsuario.eliminarUsuario(usuario.getIdUsuario())) {
            responseJson.success = true;
            mensaje = "Usuario eliminado correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = usuario;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscarUsuario(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorUsuario cUsuario = new ControladorUsuario();
        List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
        String where = " WHERE e.rut LIKE '%" + q + "%' OR e.nombres LIKE '%" + q + "%' OR e.apellidos LIKE '%" + q + "%' OR u.nombreUsuario LIKE '%" + q + "%' OR p.nombrePerfil LIKE '%" + q + "%' ";

        //mas codigo
        lista = cUsuario.getUsuarios(fila, cantidad, where);

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

    private void cambiarClave(HttpServletRequest req, HttpServletResponse res) {
        String claveActual = "", nuevaClave = "", repetirClave = "";

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("claveActual")) {
                claveActual = req.getParameter("claveActual");
            } else if (parameterNames.equals("nuevaClave")) {
                nuevaClave = req.getParameter("nuevaClave");
            } else if (parameterNames.equals("repetirClave")) {
                repetirClave = req.getParameter("repetirClave");
            }
        }
        UsuarioDTO usuario = (UsuarioDTO) req.getSession().getAttribute("usuario");

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        if (nuevaClave.equals(repetirClave)) {
            if (usuario.getPassword().equals(claveActual)) {
                ControladorUsuario cUsuario = new ControladorUsuario();
                usuario.setPassword(nuevaClave);
                if (cUsuario.actualizarUsuario(usuario)) {
                    req.getSession().setAttribute("usuario", usuario);
                    responseJson.success = true;
                    mensaje = "Clave cambiada correctamente.";
                } else {
                    responseJson.success = false;
                    mensaje = "Ocurrio un error inesperado, intente mas tarde";
                }
            } else {
                responseJson.success = false;
                mensaje = "La contraseña actual es incorrecta.";
            }
        } else {
            responseJson.success = false;
            mensaje = "Las contraseñas no coinciden.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = usuario;
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
