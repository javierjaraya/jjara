/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.administrar;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMaquina;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorTipoMaquina;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.TipoMaquinaDTO;
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
public class AdministrarMaquina extends HttpServlet {

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
                    listarMaquinas(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarMaquina(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarMaquina(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarMaquina(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarMaquina(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_TIPO_MAQUINA")) {
                    listarTipoMaquinas(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_MAQUINAS_COMBOTREE")) {
                    listarMaquinasComboTree(req, res);
                } else if (req.getParameter("accion").equals("OBTENER_MAQUINA_BY_ID")) {
                    getMaquinaByID(req, res);
                }
            } else {
                pagina = "/web/administracion/maquinas/administrarMaquina.jsp";
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

    private void listarMaquinas(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorMaquina cMaquina = new ControladorMaquina();
        List<MaquinaDTO> lista = new ArrayList<MaquinaDTO>();
        String where = "";

        //mas codigo
        lista = cMaquina.getMaquinas(fila, cantidad, where);

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

    private void guardarMaquina(HttpServletRequest req, HttpServletResponse res) {
        String codigoMaquina = "", codigoForestal = "", patente = "", modelo = "", numeroChasis = "", numeroMotor = "";
        Integer idMaquina = 0, año = 0, horometro = 0, idTipoMaquina = 0, idCabezal = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idMaquina")) {
                idMaquina = Integer.parseInt(req.getParameter("idMaquina"));
            } else if (parameterNames.equals("codigoMaquina")) {
                codigoMaquina = req.getParameter("codigoMaquina");
            } else if (parameterNames.equals("codigoForestal")) {
                codigoForestal = req.getParameter("codigoForestal");
            } else if (parameterNames.equals("patente")) {
                patente = req.getParameter("patente");
            } else if (parameterNames.equals("modelo")) {
                modelo = req.getParameter("modelo");
            } else if (parameterNames.equals("numeroChasis")) {
                numeroChasis = req.getParameter("numeroChasis");
            } else if (parameterNames.equals("numeroMotor")) {
                numeroMotor = req.getParameter("numeroMotor");
            } else if (parameterNames.equals("año")) {
                año = Integer.parseInt(req.getParameter("año"));
            } else if (parameterNames.equals("horometro")) {
                horometro = Integer.parseInt(req.getParameter("horometro"));
            } else if (parameterNames.equals("idTipoMaquina")) {
                idTipoMaquina = Integer.parseInt(req.getParameter("idTipoMaquina"));
            } else if (parameterNames.equals("idCabezal")) {
                idCabezal = Integer.parseInt(req.getParameter("idCabezal"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        MaquinaDTO maquina = new MaquinaDTO();
        maquina.setIdMaquina(idMaquina);
        maquina.setCodigoMaquina(codigoMaquina);
        maquina.setCodigoForestal(codigoForestal);
        maquina.setPatente(patente);
        maquina.setModelo(modelo);
        maquina.setNumeroChasis(numeroChasis);
        maquina.setNumeroMotor(numeroMotor);
        maquina.setAño(año);
        maquina.setHorometro(horometro);
        maquina.setIdTipoMaquina(idTipoMaquina);
        if (idTipoMaquina == 1) {
            maquina.setIdCabezal(idCabezal);
        } else {
            maquina.setIdCabezal(1);
        }
        maquina.setEstado(estado);

        ControladorMaquina cMaquina = new ControladorMaquina();

        boolean codMaqValido = false, codForValido = false, patValido = false, numChaValido = false, numMoValido = false, idCabValido = false;
        MaquinaDTO maquinaActual = cMaquina.getByIdMaquina(idMaquina);

        if (maquina.getCodigoMaquina().equalsIgnoreCase(maquinaActual.getCodigoMaquina())) {
            codMaqValido = true;
        }
        if (maquina.getCodigoForestal().equalsIgnoreCase(maquinaActual.getCodigoForestal())) {
            codForValido = true;
        }
        if (maquina.getPatente().equalsIgnoreCase(maquinaActual.getPatente())) {
            patValido = true;
        }
        if (maquina.getNumeroChasis().equalsIgnoreCase(maquinaActual.getNumeroChasis())) {
            numChaValido = true;
        }
        if (maquina.getNumeroMotor().equalsIgnoreCase(maquinaActual.getNumeroMotor())) {
            numMoValido = true;
        }
        if (maquina.getIdCabezal() == maquinaActual.getIdCabezal()) {
            idCabValido = true;
        }

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        MaquinaDTO existe = cMaquina.getByCodigoMaquina(maquina.getCodigoMaquina());
        if (codMaqValido || existe == null) {
            existe = cMaquina.getByCodigoForestal(maquina.getCodigoForestal());
            if (codForValido || existe == null) {
                existe = cMaquina.getByPatente(maquina.getPatente());
                if (patValido || existe == null) {
                    existe = cMaquina.getByNumeroChasis(maquina.getNumeroChasis());
                    if (numChaValido || existe == null) {
                        existe = cMaquina.getByNumeroMotor(maquina.getNumeroMotor());
                        if (numMoValido || existe == null) {
                            existe = cMaquina.getByIdCabezal(maquina.getIdCabezal());
                            if (idCabValido || existe == null) {
                                responseJson.statusCode = cMaquina.updateMaquina(maquina);
                                if (responseJson.statusCode > 0) {
                                    responseJson.success = true;
                                    mensaje = "Máquina actualizada correctamente.";//Datos actualizados correctamente.
                                } else {
                                    responseJson.success = false;
                                    mensaje = "Se a producido un error inesperado.";
                                }
                            } else {
                                responseJson.success = false;
                                mensaje = "El cabezal ya esta asociado ha otra maquina, intente nuevamente.";
                            }
                        } else {
                            responseJson.success = false;
                            mensaje = "El número de motor ya esta registrado, intente nuevamente.";
                        }
                    } else {
                        responseJson.success = false;
                        mensaje = "El número de chasis ya esta registrado, intente nuevamente.";
                    }
                } else {
                    responseJson.success = false;
                    mensaje = "La patente ingresada ya esta registrada, intente nuevamente.";
                }
            } else {
                responseJson.success = false;
                mensaje = "El codigo forestal ya esta registrado, intente nuevamente.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El codigo maquina ya esta registrado, intente nuevamente.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = maquina;
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

    private void agregarMaquina(HttpServletRequest req, HttpServletResponse res) {
        String codigoMaquina = "", codigoForestal = "", patente = "", modelo = "", numeroChasis = "", numeroMotor = "";
        Integer año = 0, horometro = 0, idTipoMaquina = 0, idCabezal = 0, estado = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("codigoMaquina")) {
                codigoMaquina = req.getParameter("codigoMaquina");
            } else if (parameterNames.equals("codigoForestal")) {
                codigoForestal = req.getParameter("codigoForestal");
            } else if (parameterNames.equals("patente")) {
                patente = req.getParameter("patente");
            } else if (parameterNames.equals("modelo")) {
                modelo = req.getParameter("modelo");
            } else if (parameterNames.equals("numeroChasis")) {
                numeroChasis = req.getParameter("numeroChasis");
            } else if (parameterNames.equals("numeroMotor")) {
                numeroMotor = req.getParameter("numeroMotor");
            } else if (parameterNames.equals("año")) {
                año = Integer.parseInt(req.getParameter("año"));
            } else if (parameterNames.equals("horometro")) {
                horometro = Integer.parseInt(req.getParameter("horometro"));
            } else if (parameterNames.equals("idTipoMaquina")) {
                idTipoMaquina = Integer.parseInt(req.getParameter("idTipoMaquina"));
            } else if (parameterNames.equals("idCabezal")) {
                idCabezal = Integer.parseInt(req.getParameter("idCabezal"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            }
        }
        MaquinaDTO maquina = new MaquinaDTO();
        maquina.setCodigoMaquina(codigoMaquina);
        maquina.setCodigoForestal(codigoForestal);
        maquina.setPatente(patente);
        maquina.setModelo(modelo);
        maquina.setNumeroChasis(numeroChasis);
        maquina.setNumeroMotor(numeroMotor);
        maquina.setAño(año);
        maquina.setHorometro(horometro);
        maquina.setIdTipoMaquina(idTipoMaquina);
        if (idTipoMaquina == 1) {
            maquina.setIdCabezal(idCabezal);
        } else {
            maquina.setIdCabezal(1);
        }
        maquina.setEstado(estado);

        ControladorMaquina cMaquina = new ControladorMaquina();

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        MaquinaDTO existe = cMaquina.getByCodigoMaquina(maquina.getCodigoMaquina());
        if (existe == null) {
            existe = cMaquina.getByCodigoForestal(maquina.getCodigoForestal());
            if (existe == null) {
                existe = cMaquina.getByPatente(maquina.getPatente());
                if (existe == null) {
                    existe = cMaquina.getByNumeroChasis(maquina.getNumeroChasis());
                    if (existe == null) {
                        existe = cMaquina.getByNumeroMotor(maquina.getNumeroMotor());
                        if (existe == null) {
                            existe = cMaquina.getByIdCabezal(maquina.getIdCabezal());
                            if (maquina.getIdTipoMaquina() == 2 || existe == null) {
                                responseJson.statusCode = cMaquina.agregarMaquina(maquina);
                                if (responseJson.statusCode > 0) {
                                    responseJson.success = true;
                                    mensaje = "Maquina agregada correctamente.";//Datos actualizados correctamente.
                                } else {
                                    responseJson.success = false;
                                    mensaje = "Se a producido un error inesperado.";
                                }
                            } else {
                                responseJson.success = false;
                                mensaje = "El cabezal ya esta asociado ha otra maquina, intente nuevamente.";
                            }
                        } else {
                            responseJson.success = false;
                            mensaje = "El numero de motor ya esta registrado, intente nuevamente.";
                        }
                    } else {
                        responseJson.success = false;
                        mensaje = "El numero de chasis ya esta registrado, intente nuevamente.";
                    }
                } else {
                    responseJson.success = false;
                    mensaje = "La patente ingresada ya esta registrada, intente nuevamente.";
                }
            } else {
                responseJson.success = false;
                mensaje = "El codigo forestal ya esta registrado, intente nuevamente.";
            }
        } else {
            responseJson.success = false;
            mensaje = "El codigo maquina ya esta registrado, intente nuevamente.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = maquina;
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

    private void borrarMaquina(HttpServletRequest req, HttpServletResponse res) {
        String json = req.getParameter("maquina");
        Gson gson = new Gson();
        MaquinaDTO maquina = gson.fromJson(json, MaquinaDTO.class);

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        ControladorMaquina cMaquina = new ControladorMaquina();
        responseJson.statusCode = cMaquina.removeMaquina(maquina.getIdMaquina());
        if (responseJson.statusCode > 0) {
            responseJson.success = true;
            mensaje = "La maquina fue eliminada correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se a producido un error inesperado.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = maquina;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscarMaquina(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorMaquina cMaquina = new ControladorMaquina();
        List<MaquinaDTO> lista = new ArrayList<MaquinaDTO>();
        String where = " WHERE m.codigoMaquina LIKE '%" + q + "%' OR m.patente LIKE '%" + q + "%' OR m.modelo LIKE '%" + q + "%' OR m.numeroChasis LIKE '%" + q + "%' OR m.tipoMaquina LIKE '%" + q + "%' ";

        //mas codigo
        lista = cMaquina.getMaquinas(fila, cantidad, where);

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

    private void listarTipoMaquinas(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorTipoMaquina cTipoMaquina = new ControladorTipoMaquina();
        List<TipoMaquinaDTO> lista = new ArrayList<TipoMaquinaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cTipoMaquina.getAllTipoFaena(where);

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

    private void listarMaquinasComboTree(HttpServletRequest req, HttpServletResponse res) {
        String tipoCodigo = "";

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("tipoCodigo")) {
                tipoCodigo = req.getParameter("tipoCodigo");
            }
        }

        //CODIGO VARIABLE
        ControladorMaquina cMaquina = new ControladorMaquina();
        List<MaquinaDTO> lista = new ArrayList<MaquinaDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cMaquina.getAllMaquinas("");

        List<ComboTree> comboOpciones = new ArrayList<ComboTree>();
        for (int i = 0; i < lista.size(); i++) {
            ComboTree combo = new ComboTree();
            if (tipoCodigo.equalsIgnoreCase("Forestal")) {
                if (lista.get(i).getCodigoForestal().length() > 0 && !lista.get(i).getCodigoForestal().equalsIgnoreCase("AUN NO TIENE")) {
                    combo.setText(lista.get(i).getCodigoForestal());
                    combo.setId(lista.get(i).getIdMaquina());
                    comboOpciones.add(combo);
                }
            } else {
                combo.setText(lista.get(i).getCodigoMaquina());
                combo.setId(lista.get(i).getIdMaquina());
                comboOpciones.add(combo);
            }
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

    private void getMaquinaByID(HttpServletRequest req, HttpServletResponse res) {
        Integer idMaquina = 0;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idMaquina")) {
                idMaquina = Integer.parseInt(req.getParameter("idMaquina"));
            }
        }

        ControladorMaquina cMaquina = new ControladorMaquina();
        MaquinaDTO maquina = cMaquina.getByIdMaquina(idMaquina);

        //FIN CODIGO VARIABLE
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(maquina);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
