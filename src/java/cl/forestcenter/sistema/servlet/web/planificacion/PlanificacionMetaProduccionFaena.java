/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.planificacion;

import cl.forestcenter.sistema.controladores.mantenedores.ControladorDetallePlanificacion;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMaquina;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorMetaProduccionFaena;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroHarvester;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorTipoArbol;
import cl.forestcenter.sistema.dto.DetallePlanificacionDTO;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import cl.forestcenter.sistema.dto.MetaActualDTO;
import cl.forestcenter.sistema.dto.PlanificacionDTO;
import cl.forestcenter.sistema.dto.ResponseDTO;
import cl.forestcenter.sistema.dto.ResponseTablaDTO;
import cl.forestcenter.sistema.dto.TipoArbolDTO;
import cl.forestcenter.sistema.servlet.web.administrar.*;
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
public class PlanificacionMetaProduccionFaena extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (req.getSession() != null && cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("LISTADO")) {
                    listarPlanificacion(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR")) {
                    guardarPlanificacion(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR")) {
                    agregarPlanificacion(req, res);
                } else if (req.getParameter("accion").equals("BORRAR")) {
                    borrarPlanificacion(req, res);
                } else if (req.getParameter("accion").equals("BUSCAR")) {
                    buscarPlanificacion(req, res);
                } else if (req.getParameter("accion").equals("LISTADO_DETALLE")) {
                    listarDetalle(req, res);
                } else if (req.getParameter("accion").equals("AGREGAR_DETALLE")) {
                    agregrarDetalle(req, res);
                } else if (req.getParameter("accion").equals("GUARDAR_DETALLE")) {
                    guardarDetalle(req, res);
                } else if (req.getParameter("accion").equals("BORRAR_DETALLE")) {
                    borrarDetalle(req, res);
                } else if (req.getParameter("accion").equals("LISTAR_TIPO_ARBOLES")) {
                    listarTipoArboles(req, res);
                } else if (req.getParameter("accion").equals("METAS_ACTIVAS_HOY")) {
                    obtenerMetasActivasHoy(req, res);
                }
            } else {
                pagina = "/web/planificacion/metasPruduccionFaena/planificacionMetaProduccionFaena.jsp";
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

    private void listarPlanificacion(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();
        List<PlanificacionDTO> lista = new ArrayList<PlanificacionDTO>();
        String where = "";

        lista = cPlanificacion.getPlanificacion(fila, cantidad, where);

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

    private void guardarPlanificacion(HttpServletRequest req, HttpServletResponse res) {
        Integer idPlanificacion = 0, idFaena = 0, estado = 0;
        Date fechaInicio = null, fechaTermino = null;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idPlanificacion")) {
                idPlanificacion = Integer.parseInt(req.getParameter("idPlanificacion"));
            } else if (parameterNames.equals("fechaInicio")) {
                fechaInicio = Date.valueOf(req.getParameter("fechaInicio"));
            } else if (parameterNames.equals("fechaTermino")) {
                fechaTermino = Date.valueOf(req.getParameter("fechaTermino"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            } else if (parameterNames.equals("idFaena")) {
                idFaena = Integer.parseInt(req.getParameter("idFaena"));
            }
        }
        PlanificacionDTO planificacion = new PlanificacionDTO();
        planificacion.setIdPlanificacion(idPlanificacion);
        planificacion.setIdFaena(idFaena);
        planificacion.setFechaInicio(fechaInicio);
        planificacion.setFechaTermino(fechaTermino);
        planificacion.setEstado(estado);

        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();
        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        if (cPlanificacion.getPlanificacionByFaenaFechasDistintaPlanificacion(idPlanificacion, idFaena, fechaInicio, fechaTermino) == null) {
            responseJson.statusCode = cPlanificacion.updatePlanificacion(planificacion);
            if (responseJson.statusCode != 0) {
                responseJson.success = true;
                mensaje = "Planificacion actualizada correctamente.";
            } else {
                responseJson.success = false;
                mensaje = "Se a producido un error inesperado.";
            }
        } else {
            responseJson.success = false;
            mensaje = "Las fechas ingresadas coinciden con las de otra planificación de la faena.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = planificacion;
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

    private void agregarPlanificacion(HttpServletRequest req, HttpServletResponse res) {
        Integer idPlanificacion = 0, idFaena = 0, estado = 0;
        Date fechaInicio = null, fechaTermino = null;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("fechaInicio")) {
                fechaInicio = Date.valueOf(req.getParameter("fechaInicio"));
            } else if (parameterNames.equals("fechaTermino")) {
                fechaTermino = Date.valueOf(req.getParameter("fechaTermino"));
            } else if (parameterNames.equals("estado")) {
                estado = Integer.parseInt(req.getParameter("estado"));
            } else if (parameterNames.equals("idFaena")) {
                idFaena = Integer.parseInt(req.getParameter("idFaena"));
            }
        }
        PlanificacionDTO planificacion = new PlanificacionDTO();
        planificacion.setIdFaena(idFaena);
        planificacion.setFechaInicio(fechaInicio);
        planificacion.setFechaTermino(fechaTermino);
        planificacion.setEstado(estado);

        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";
        PlanificacionDTO planificacionExistente = cPlanificacion.getPlanificacionByFaenaFechas(idFaena, fechaInicio, fechaTermino);

        if (planificacionExistente == null) {
            responseJson.statusCode = cPlanificacion.savePlanificacion(planificacion);
            if (responseJson.statusCode > 0) {
                responseJson.success = true;
                mensaje = "Planificación guardado correctamente.";
            } else {
                responseJson.success = false;
                mensaje = "Se ha producido un error inesperado.";
            }
        } else {
            responseJson.success = false;
            mensaje = "Las fechas ingresadas coinciden con las de otra planificación de la faena.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = planificacion;
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

    private void borrarPlanificacion(HttpServletRequest req, HttpServletResponse res) {
        Integer idPlanificacion = Integer.parseInt(req.getParameter("idPlanificacion"));

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();
        responseJson.statusCode = cPlanificacion.removePlanificacion(idPlanificacion);
        if (responseJson.statusCode != 0) {
            responseJson.success = true;
            mensaje = "La planificacion fue eliminada correctamente.";//Datos actualizados correctamente.
        } else {
            responseJson.success = false;
            mensaje = "Se ha producido un error inesperado.";
        }

        Gson gson = new Gson();
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = idPlanificacion;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscarPlanificacion(HttpServletRequest req, HttpServletResponse res) {
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
        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();
        List<PlanificacionDTO> lista = new ArrayList<PlanificacionDTO>();
        String where = " WHERE TF.nombre LIKE '%" + q + "%' ";

        //mas codigo
        lista = cPlanificacion.getPlanificacion(fila, cantidad, where);

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

    private void listarDetalle(HttpServletRequest req, HttpServletResponse res) {
        int fila = 1;
        try {
            fila = Integer.parseInt(req.getParameter("fila").toString());
        } catch (Exception e) {
        }

        String strCant = req.getSession().getAttribute("registros_paginacion").toString();
        int cantidad = (strCant.equals("")) ? 0 : Integer.parseInt(strCant);

        ResponseTablaDTO responseJson = new ResponseTablaDTO();
        //CODIGO VARIABLE
        ControladorDetallePlanificacion cDetallePlanificacion = new ControladorDetallePlanificacion();
        List<DetallePlanificacionDTO> listaDetalle = new ArrayList<DetallePlanificacionDTO>();

        String idPlanificacion = req.getParameter("idPlanificacion");
        String where = " AND idPlanificacion = " + idPlanificacion;

        //mas codigo
        listaDetalle = cDetallePlanificacion.getDetallePlanificacion(fila, cantidad, where);

        //FIN CODIGO VARIABLE
        int size = listaDetalle.size();
        responseJson.setTotal(size);
        res.setCharacterEncoding("UTF-8");
        responseJson.setRows(listaDetalle);
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

    private void agregrarDetalle(HttpServletRequest req, HttpServletResponse res) {
        Integer numeroActa = 0, idPredio = 0, idTipoArbol = 0, estado = 0, idPlanificacion = 0;
        Double metrosCubicos = 0.0;
        Date fechaInicioDetalle = null, fechaTerminoDetalle = null;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("numeroActa")) {
                numeroActa = Integer.parseInt(req.getParameter("numeroActa"));
            } else if (parameterNames.equals("fechaInicioDetalle")) {
                fechaInicioDetalle = Date.valueOf(req.getParameter("fechaInicioDetalle"));
            } else if (parameterNames.equals("fechaTerminoDetalle")) {
                fechaTerminoDetalle = Date.valueOf(req.getParameter("fechaTerminoDetalle"));
            } else if (parameterNames.equals("idPredio")) {
                idPredio = Integer.parseInt(req.getParameter("idPredio"));
            } else if (parameterNames.equals("idTipoArbol")) {
                idTipoArbol = Integer.parseInt(req.getParameter("idTipoArbol"));
            } else if (parameterNames.equals("metrosCubicos")) {
                String metrosCu = req.getParameter("metrosCubicos");
                metrosCubicos = Double.parseDouble(metrosCu);
            } else if (parameterNames.equals("estadoDetalle")) {
                estado = Integer.parseInt(req.getParameter("estadoDetalle"));
            } else if (parameterNames.equals("idPlanificacionFaena")) {
                idPlanificacion = Integer.parseInt(req.getParameter("idPlanificacionFaena"));
            }
        }
        DetallePlanificacionDTO detalle = new DetallePlanificacionDTO();
        detalle.setNumeroActa(numeroActa);
        detalle.setFechaInicioDetalle(fechaInicioDetalle);
        detalle.setFechaTerminoDetalle(fechaTerminoDetalle);
        detalle.setIdTipoArbol(idTipoArbol);
        detalle.setMetrosCubicos(metrosCubicos);
        detalle.setEstadoDetalle(estado);
        detalle.setIdPredio(idPredio);
        detalle.setIdPlanificacionFaena(idPlanificacion);

        ControladorDetallePlanificacion cDetalle = new ControladorDetallePlanificacion();

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        //Validar que las fechas del detalle esten dentro de la planificacion
        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();
        PlanificacionDTO planificacion = cPlanificacion.getPlanificacionByID(idPlanificacion);
        if (detalle.getFechaInicioDetalle().after(planificacion.getFechaInicio()) || detalle.getFechaInicioDetalle().equals(planificacion.getFechaInicio())) {
            if (detalle.getFechaTerminoDetalle().before(planificacion.getFechaTermino()) || detalle.getFechaTerminoDetalle().equals(planificacion.getFechaTermino())) {
                //Validar que los rango de fecha de un detalle no coincida con otro detalle
                DetallePlanificacionDTO existeDetalle = cDetalle.getDetalleByPlanificacionFechas(idPlanificacion, fechaInicioDetalle, fechaTerminoDetalle);
                if (existeDetalle == null) {
                    responseJson.statusCode = cDetalle.agregarDetallePlanificacion(detalle);
                    if (responseJson.statusCode > 0) {
                        responseJson.success = true;
                        mensaje = "Detalle planificacion agregada correctamente.";
                    } else {
                        responseJson.success = false;
                        mensaje = "Se a producido un error inesperado.";
                    }
                } else {
                    responseJson.success = false;
                    mensaje = "Existe una coincidencia en las fechas con otro detalle de la planificacion";
                }
            } else {
                responseJson.success = false;
                mensaje = "La fecha de termino no puede ser superior ha la fecha de termino de la planificacion.";
            }
        } else {
            responseJson.success = false;
            mensaje = "La fecha de inicio es menor ha la fecha de inicio de la planificacion.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = detalle;
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

    private void guardarDetalle(HttpServletRequest req, HttpServletResponse res) {
        Integer idDetallePlanificacion = 0, numeroActa = 0, idPredio = 0, idTipoArbol = 0, estado = 0, idPlanificacion = 0;
        Double metrosCubicos = 0.0;
        Date fechaInicioDetalle = null, fechaTerminoDetalle = null;

        String parameterNames = "";
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            parameterNames = (String) e.nextElement();
            if (parameterNames.equals("idDetallePlanificacion")) {
                idDetallePlanificacion = Integer.parseInt(req.getParameter("idDetallePlanificacion"));
            } else if (parameterNames.equals("numeroActa")) {
                numeroActa = Integer.parseInt(req.getParameter("numeroActa"));
            } else if (parameterNames.equals("fechaInicioDetalle")) {
                fechaInicioDetalle = Date.valueOf(req.getParameter("fechaInicioDetalle"));
            } else if (parameterNames.equals("fechaTerminoDetalle")) {
                fechaTerminoDetalle = Date.valueOf(req.getParameter("fechaTerminoDetalle"));
            } else if (parameterNames.equals("idPredio")) {
                idPredio = Integer.parseInt(req.getParameter("idPredio"));
            } else if (parameterNames.equals("idTipoArbol")) {
                idTipoArbol = Integer.parseInt(req.getParameter("idTipoArbol"));
            } else if (parameterNames.equals("metrosCubicos")) {
                String metrosCu = req.getParameter("metrosCubicos");
                metrosCubicos = Double.parseDouble(metrosCu);
            } else if (parameterNames.equals("estadoDetalle")) {
                estado = Integer.parseInt(req.getParameter("estadoDetalle"));
            } else if (parameterNames.equals("idPlanificacionFaena")) {
                idPlanificacion = Integer.parseInt(req.getParameter("idPlanificacionFaena"));
            }
        }
        DetallePlanificacionDTO detalle = new DetallePlanificacionDTO();
        detalle.setIdDetallePlanificacion(idDetallePlanificacion);
        detalle.setNumeroActa(numeroActa);
        detalle.setFechaInicioDetalle(fechaInicioDetalle);
        detalle.setFechaTerminoDetalle(fechaTerminoDetalle);
        detalle.setIdTipoArbol(idTipoArbol);
        detalle.setMetrosCubicos(metrosCubicos);
        detalle.setEstadoDetalle(estado);
        detalle.setIdPredio(idPredio);
        detalle.setIdPlanificacionFaena(idPlanificacion);

        ControladorDetallePlanificacion cDetalle = new ControladorDetallePlanificacion();

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        //Validar que las fechas del detalle esten dentro de la planificacion
        ControladorMetaProduccionFaena cPlanificacion = new ControladorMetaProduccionFaena();
        PlanificacionDTO planificacion = cPlanificacion.getPlanificacionByID(idPlanificacion);
        if (detalle.getFechaInicioDetalle().after(planificacion.getFechaInicio()) || detalle.getFechaInicioDetalle().equals(planificacion.getFechaInicio())) {
            if (detalle.getFechaTerminoDetalle().before(planificacion.getFechaTermino()) || detalle.getFechaTerminoDetalle().equals(planificacion.getFechaTermino())) {
                //Validar que los rango de fecha de un detalle no coincida con otro detalle
                DetallePlanificacionDTO existeDetalle = cDetalle.getDetalleByPlanificacionFechasDistinDetalle(idPlanificacion, idDetallePlanificacion, fechaInicioDetalle, fechaTerminoDetalle);
                if (existeDetalle == null) {
                    responseJson.statusCode = cDetalle.updateDetallePlanificacion(detalle);
                    if (responseJson.statusCode > 0) {
                        responseJson.success = true;
                        mensaje = "Detalle planificación actualizada correctamente.";
                    } else {
                        responseJson.success = false;
                        mensaje = "Se a producido un error inesperado.";
                    }
                } else {
                    responseJson.success = false;
                    mensaje = "Existe una coincidencia en las fechas con otro detalle de la planificacion";
                }
            } else {
                responseJson.success = false;
                mensaje = "La fecha de termino no puede ser superior ha la fecha de termino de la planificacion.";
            }
        } else {
            responseJson.success = false;
            mensaje = "La fecha de inicio es menor ha la fecha de inicio de la planificacion.";
        }

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = detalle;
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

    private void borrarDetalle(HttpServletRequest req, HttpServletResponse res) {
        Integer idDetallePlanificacion = Integer.parseInt(req.getParameter("idDetallePlanificacion"));

        ResponseDTO responseJson = new ResponseDTO();
        String mensaje = "";

        ControladorDetallePlanificacion cDetalle = new ControladorDetallePlanificacion();
        responseJson.statusCode = cDetalle.removeDetallePlanificacion(idDetallePlanificacion);
        if (responseJson.statusCode != 0) {
            responseJson.success = true;
            mensaje = "El detalle fue eliminada correctamente.";
        } else {
            responseJson.success = false;
            mensaje = "Se ha producido un error inesperado.";
        }

        Gson gson = new Gson();
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        responseJson.data = idDetallePlanificacion;
        responseJson.statusText = mensaje;
        try {
            String jsonOutput = gson.toJson(responseJson);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarTipoArboles(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorTipoArbol cTipoArbol = new ControladorTipoArbol();
        List<TipoArbolDTO> lista = new ArrayList<TipoArbolDTO>();

        String where = " where 1=1 ";

        //mas codigo
        lista = cTipoArbol.getAllTipoArbol(where);

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

    private void obtenerMetasActivasHoy(HttpServletRequest req, HttpServletResponse res) {
        //CODIGO VARIABLE
        ControladorDetallePlanificacion cDetallePlanificacion = new ControladorDetallePlanificacion();
        List<MetaActualDTO> metasActuales = new ArrayList<MetaActualDTO>();
        metasActuales = cDetallePlanificacion.getMetasActivasHoy();

        ControladorRegistroHarvester cRegistroHarvester = new ControladorRegistroHarvester();

        String htmlDetalleIndicador = "<div id=\"contenido-indicador\" style=\"display: none;\">";
        boolean rojo = false, amarillo = false, verde = false;
        for (int i = 0; i < metasActuales.size(); i++) {
            double acumuladosReal = cRegistroHarvester.metrosCubicosAcumuladosActual(metasActuales.get(i).getIdFaena(), metasActuales.get(i).getIdPredio(), metasActuales.get(i).getFechaInicio());

            double metrosCubicosPorDia = metasActuales.get(i).getMetrosCubicos() / metasActuales.get(i).getTotalDias();
            double acumuladoPlanificado = metrosCubicosPorDia * metasActuales.get(i).getDiasTranscurridos();

            double unPorciento = 100 / acumuladoPlanificado;
            double porcentajeCumplido = unPorciento * acumuladosReal;

            //DETALLE INDICADORES
            htmlDetalleIndicador += "<div class=\"descripcionIndicador\">";
            if (porcentajeCumplido < 75) {
                rojo = true;
                htmlDetalleIndicador += "<div class=\"imagenDetalleIndicador\"><img style=\" width: 60px; height: 50px;\" src=\"/jjara/files/img/indicador/alertaroja.png\" ></div>";
            } else if (porcentajeCumplido < 90) {
                amarillo = true;
                htmlDetalleIndicador += "<div class=\"imagenDetalleIndicador\"><img style=\" width: 60px; height: 50px;\" src=\"/jjara/files/img/indicador/alertaamarilla.png\" ></div>";
            } else {
                verde = true;
                htmlDetalleIndicador += "<div class=\"imagenDetalleIndicador\"><img style=\" width: 60px; height: 50px;\" src=\"/jjara/files/img/indicador/alertaverde.png\" ></div>";
            }
            htmlDetalleIndicador += "<div class=\"textoDetalleIndicador\">";
            htmlDetalleIndicador += "  <div>Faena: " + metasActuales.get(i).getDescripcionFaena() + "</div>";
            htmlDetalleIndicador += "  <div>Predio: " + metasActuales.get(i).getNombrePredio() + "</div>";
            htmlDetalleIndicador += "  <div>m3 Planificado: " + redondear(acumuladoPlanificado, 2) + " m3</div>";
            htmlDetalleIndicador += "  <div>m3 Real: " + redondear(acumuladosReal, 2) + " m3</div>";
            htmlDetalleIndicador += "</div>";//FIN textoDetalleIndicador
            htmlDetalleIndicador += "</div>";//FIN descripcionIndicador
        }
        if(metasActuales.size() == 0){//SI NO HAY PLANIFICACIONES
            htmlDetalleIndicador += "<div class=\"descripcionIndicador\">";
            htmlDetalleIndicador += "<div class=\"imagenDetalleIndicador\"><img style=\" width: 60px; height: 50px;\" src=\"/jjara/files/img/indicador/advertencia.png\" ></div>";
            
            htmlDetalleIndicador += "<div class=\"textoDetalleIndicador\">";
            htmlDetalleIndicador += "  <div>Aviso : No existen planificaciones activas para la fecha actual </div>";
            //htmlDetalleIndicador += "  <div>Predio: " + metasActuales.get(i).getNombrePredio() + "</div>";
            htmlDetalleIndicador += "</div>";//FIN textoDetalleIndicador
            htmlDetalleIndicador += "</div>";//FIN descripcionIndicador
        }
        htmlDetalleIndicador += "</div>";//FIN contenido-indicador
        //HEADER INDICADORES
        String nombreIcono = "";
        if(rojo){
            nombreIcono = "alertaroja";
        }else if(amarillo){
            nombreIcono = "alertaamarilla";
        }else if(verde){
            nombreIcono = "alertaenverde";
        }else {
            nombreIcono = "advertencia";
        }
        htmlDetalleIndicador = "<div id=\"header-indicador\">"
                + "<img onclick=\"mostrarOcultarIndicador()\" src=\"/jjara/files/img/indicador/"+nombreIcono+".png\" onmouseover=\"this.src = '/jjara/files/img/indicador/"+nombreIcono+"2.png'\" onmouseout=\"this.src = '/jjara/files/img/indicador/"+nombreIcono+".png'\">"
                + "</div>" + htmlDetalleIndicador;

        //FIN CODIGO VAR
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(htmlDetalleIndicador);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double redondear(double numero, int cantidadDecimales) {
        int cifras = (int) Math.pow(10, cantidadDecimales); //0.00
        return Math.rint(numero * cifras) / cifras;
    }
}
