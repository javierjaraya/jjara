/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorPerfil;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorUsuario;
import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.PerfilDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
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
public class AdministrarPerfil extends HttpServlet {

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

        if (req != null && req.isRequestedSessionIdValid() && cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";
            String mensaje1 = "";
            String mensaje2 = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {
                    listar(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardar(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregar(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrar(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscar(req, res);
                } else if (req.getParameter("accion").equals("HTML_PERMISOS")) {
                    htmlPermisos(req, res);
                } else if (req.getParameter("accion").equals("HTML_PERMISOS_PERFIL")) {
                    htmlPermisosPerfil(req, res);
                }
            } else {
                pagina = "/web/administracion/perfiles/administrarPerfil.jsp";
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

    private void listar(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        //CODIGO VARIABLE
        ControladorPerfil cPerfil = new ControladorPerfil();
        List<PerfilDTO> lista = new ArrayList<PerfilDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cPerfil.getPerfiles(fila, cantidad, where);

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

    private void guardar(HttpServletRequest req, HttpServletResponse res) {
        String nombrePerfil = "";
        Integer idPerfil = 0;

        nombrePerfil = req.getParameter("nombre");
        idPerfil = Integer.parseInt(req.getParameter("idPerfil"));
        String[] menus = req.getParameterValues("idMenu");

        List<MenuDTO> listaMenu = new ArrayList<MenuDTO>();

        Map<String, String[]> map = req.getParameterMap();

        if (menus != null) {
            for (int i = 0; i < menus.length; i++) {
                String idMenu = menus[i];
                String crear = "c" + idMenu;
                String modificar = "m" + idMenu;
                String eliminar = "e" + idMenu;

                MenuDTO menu = new MenuDTO();
                menu.setIdMenu(Integer.parseInt(idMenu));
                menu.setCrear(map.containsKey(crear));
                menu.setModificar(map.containsKey(modificar));
                menu.setEliminar(map.containsKey(eliminar));

                listaMenu.add(menu);
            }
        }

        PerfilDTO perfil = new PerfilDTO();
        perfil.setIdPerfil(idPerfil);
        perfil.setNombre(nombrePerfil);
        perfil.setMenus(listaMenu);

        ControladorPerfil cPerfil = new ControladorPerfil();
        ResponseDTO responseJson = new ResponseDTO();

        if (cPerfil.actualizar(perfil)) {
            cPerfil.eliminarPermisos(idPerfil);
            if (menus != null) {
                if (cPerfil.guardarPermisosPerfil(perfil)) {
                    responseJson.setStatusText("Perfil actualizado correctamente.");
                    responseJson.success = true;
                } else {
                    responseJson.setStatusText("Ocurrio un error al actualizar los nuevos permisos.");
                    responseJson.success = false;
                }
            } else {
                responseJson.setStatusText("Perfil actualizado sin permisos asignados.");
                responseJson.success = true;
            }
        } else {
            responseJson.setStatusText("Ocurrio un error al actualizar el perfil.");
            responseJson.success = false;
        }

        responseJson.data = perfil;

        res.setCharacterEncoding(
                "UTF-8");
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

    private void agregar(HttpServletRequest req, HttpServletResponse res) {
        String nombrePerfil = "";

        nombrePerfil = req.getParameter("nombre");
        String[] menus = req.getParameterValues("idMenu");

        List<MenuDTO> listaMenu = new ArrayList<MenuDTO>();

        Map<String, String[]> map = req.getParameterMap();

        if (menus != null) {
            for (int i = 0; i < menus.length; i++) {
                String idMenu = menus[i];
                String crear = "c" + idMenu;
                String modificar = "m" + idMenu;
                String eliminar = "e" + idMenu;

                MenuDTO menu = new MenuDTO();
                menu.setIdMenu(Integer.parseInt(idMenu));
                menu.setCrear(map.containsKey(crear));
                menu.setModificar(map.containsKey(modificar));
                menu.setEliminar(map.containsKey(eliminar));

                listaMenu.add(menu);
            }
        }

        ControladorPerfil cPerfil = new ControladorPerfil();
        PerfilDTO perfil = new PerfilDTO();
        perfil.setNombre(nombrePerfil);
        perfil.setMenus(listaMenu);

        ResponseDTO responseJson = new ResponseDTO();
        int id = cPerfil.guardar(perfil);
        if (id > 0) {
            perfil.setIdPerfil(id);
            if (menus != null) {
                if (cPerfil.guardarPermisosPerfil(perfil)) {
                    responseJson.setStatusText("Perfil creado correctamente.");
                    responseJson.success = true;
                } else {
                    responseJson.setStatusText("Perfil creado, pero algunos permisos no fueron agregados corresctamente");
                    responseJson.success = true;
                }
            } else {
                responseJson.setStatusText("Perfil creado sin permisos asignados.");
                responseJson.success = true;
            }
        } else {
            responseJson.setStatusText("Ocurrio un error inesperado.");
            responseJson.success = false;
        }

        responseJson.data = perfil;
        res.setCharacterEncoding("UTF-8");
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

    private void borrar(HttpServletRequest req, HttpServletResponse res) {
        String jsonUSuario = req.getParameter("perfil");
        Gson gson = new Gson();
        PerfilDTO perfil = gson.fromJson(jsonUSuario, PerfilDTO.class);

        ControladorPerfil cPerfil = new ControladorPerfil();

        //VALIDAR QUE NO EXISTAN USUARIO CON EL PERFIL ASOCIADO
        ControladorUsuario cUsuario = new ControladorUsuario();
        UsuarioDTO usuario = cUsuario.getUsuarioByIdPerfil(perfil.getIdPerfil());

        ResponseDTO responseJson = new ResponseDTO();

        if (usuario == null) {
            if (cPerfil.eliminarPermisos(perfil.getIdPerfil())) {
                if (cPerfil.eliminar(perfil.getIdPerfil())) {
                    responseJson.setStatusText("Perfil eliminado correctamente.");
                    responseJson.success = true;
                } else {
                    responseJson.setStatusText("Se eliminaron los permisos, pero se produjo un error al borrar el perfil.");
                    responseJson.success = false;
                }
            } else {
                responseJson.setStatusText("Ocurrio un error al eliminar los permisos, no se elimino el perfil.");
                responseJson.success = false;
            }
        } else {
            responseJson.setStatusText("No se puede eliminar el perfil porque existen usuario asociados.");
            responseJson.success = false;
        }

        responseJson.data = perfil;

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;

        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void htmlPermisos(HttpServletRequest req, HttpServletResponse res) {
        ControladorPerfil cPermisos = new ControladorPerfil();
        List<MenuDTO> menus = cPermisos.getAllMenu();
        MenuDTO menu = new MenuDTO();
        String html = "<table>"
                + "<tr><td></td><td>Crear</td><td>Modificar</td><td>Eliminar</td></tr>";
        for (int i = 0; i < menus.size(); i++) {
            menu = menus.get(i);
            html += "<tr>"
                    + "<td>"
                    + "  <input type=\"checkbox\" name=\"idMenu\" value=\"" + menu.getIdMenu() + "\">"
                    + "  <label class=\"label-formualrio\">" + menu.getNombre() + "</label>"
                    + "</td>"
                    + "<td><input type=\"checkbox\" name=\"c" + (i + 1) + "\" value=\"0\"></td>"
                    + "<td><input type=\"checkbox\" name=\"m" + (i + 1) + "\" value=\"0\"></td>"
                    + "<td><input type=\"checkbox\" name=\"e" + (i + 1) + "\" value=\"0\"></td>"
                    + "</tr>";
        }
        html += "</table>";

        ResponseDTO responseJson = new ResponseDTO();
        responseJson.setStatusText(html);
        responseJson.success = true;
        responseJson.data = menu;
        res.setCharacterEncoding("UTF-8");
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

    private void htmlPermisosPerfil(HttpServletRequest req, HttpServletResponse res) {

        Integer idPerfil = Integer.parseInt(req.getParameter("idPerfil"));

        ControladorPerfil cPermisos = new ControladorPerfil();
        List<MenuDTO> menus = cPermisos.getAllMenu();
        PerfilDTO perfil = cPermisos.getPerfilByID(idPerfil);

        MenuDTO menu = new MenuDTO();
        String html = "<table>"
                + "<tr><td></td><td>Crear</td><td>Modificar</td><td>Eliminar</td></tr>";
        for (int i = 0; i < menus.size(); i++) {
            menu = menus.get(i);

            String esta = "", crear = "", modificar = "", eliminar = "";
            if (perfil != null) {
                MenuDTO menuPerfil = perfil.contieneMenu(menu.getIdMenu());
                if (menuPerfil != null) {
                    esta = "checked";
                    crear = menuPerfil.isCrear() ? "checked" : "";
                    modificar = menuPerfil.isModificar() ? "checked" : "";
                    eliminar = menuPerfil.isEliminar() ? "checked" : "";
                }
            }

            html += "<tr>"
                    + "<td>"
                    + "  <input type=\"checkbox\" name=\"idMenu\" value=\"" + menu.getIdMenu() + "\" " + esta + ">"
                    + "  <label class=\"label-formualrio\">" + menu.getNombre() + "</label>"
                    + "</td>"
                    + "<td><input type=\"checkbox\" name=\"c" + (i + 1) + "\" value=\"0\" " + crear + "></td>"
                    + "<td><input type=\"checkbox\" name=\"m" + (i + 1) + "\" value=\"0\" " + modificar + "></td>"
                    + "<td><input type=\"checkbox\" name=\"e" + (i + 1) + "\" value=\"0\" " + eliminar + "></td>"
                    + "</tr>";
        }
        html += "</table>";

        ResponseDTO responseJson = new ResponseDTO();
        responseJson.setStatusText(html);
        responseJson.success = true;
        responseJson.data = perfil;
        res.setCharacterEncoding("UTF-8");
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
    
    private void buscar(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorPerfil cPerfil = new ControladorPerfil();
        List<PerfilDTO> lista = new ArrayList<PerfilDTO>();
        String where = " WHERE p.nombrePerfil LIKE '%" + q + "%' ";

        //mas codigo
        lista = cPerfil.getPerfiles(fila, cantidad, where);

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
