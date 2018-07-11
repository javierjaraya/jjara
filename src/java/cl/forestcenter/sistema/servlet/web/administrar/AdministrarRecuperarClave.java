/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorEmpleado;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorFaena;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorParametro;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorUsuario;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.FaenaDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
import cl.forestcenter.sistema.util.CorreoElectronico;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
public class AdministrarRecuperarClave extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub        
        String pagina = "";

        if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
            if (req.getParameter("accion").equals("RECUPERAR_CLAVE")) {
                recuperarClave(req, res);
            }
        } else {
            pagina = "/web/configuracion/cuenta/recuperarClave.jsp";
            ServletContext sc = getServletConfig().getServletContext();
            RequestDispatcher rdNext = sc.getRequestDispatcher(pagina);
            rdNext.forward(req, res);
        }
    }

    private void recuperarClave(HttpServletRequest req, HttpServletResponse res) {

        String email = req.getParameter("email");

        ResponseDTO responseJson = new ResponseDTO();
        //CODIGO VARIABLE
        ControladorUsuario cUsuario = new ControladorUsuario();
        String where = "";

        ControladorParametro cParametro = new ControladorParametro();
        HashMap<String, String> parametros = cParametro.obtenerParametros();

        UsuarioDTO usuario = cUsuario.getUsuarioByEmail(email);

        if (usuario != null) {

            String asunto = "Recuperación de contraseña ForestCenter";
            String texto = "Estimado(a) " + usuario.getNombreEmpleado() + ".\n"
                    + "\n"
                    + "Tú u otra persona ha solicitado recuperar tu contraseña de usuario. Tu nombre de usuario y contraseña es la siguiente:\n"
                    + "\n"
                    + "Nombre Usuario: " + usuario.getNombreUsuario() + "\n"
                    + "Contraseña: " + usuario.getPassword();
            CorreoElectronico correo = new CorreoElectronico(parametros.get("correo_sistema"), email, asunto, texto, parametros.get("password_correo_sistema"));
            if (correo.enviarCorreo()) {
                responseJson.setSuccess(true);
                responseJson.setStatusCode(1);
                responseJson.setStatusText("Datos de inicio de sesion enviado a : " + email);
                responseJson.data = usuario;
            } else {
                responseJson.setSuccess(false);
                responseJson.setStatusCode(0);
                responseJson.setStatusText("Ocurrio un error al enviar las datos de usuario, intente nuevamente.");
                responseJson.data = usuario;
            }
        } else {
            responseJson.setSuccess(false);
            responseJson.setStatusCode(0);
            responseJson.setStatusText("El correo ingresado no esta registrado en el sistema.");
            responseJson.data = usuario;
        }

        //FIN CODIGO VARIABLE
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
}
