/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.servlet.web.tableroGestion;

import cl.forestcenter.sistema.servlet.web.reporte.*;
import cl.forestcenter.sistema.servlet.web.administrar.*;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorLogin;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroForwarder;
import cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroHarvester;
import cl.forestcenter.sistema.dto.TablaResumenMesForwarderDTO;
import cl.forestcenter.sistema.dto.TablaResumenMesHarvesterDTO;
import cl.forestcenter.sistema.util.DataSetsGraficoLinea;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
public class AdministrarTableroRaleo extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ControladorLogin cLogin = new ControladorLogin();

        if (cLogin.validarSesion(req.getSession().getAttribute("idUsuario").toString(), req.getSession().getId())) {

            String pagina = "";

            if (req.getParameter("accion") != null && !req.getParameter("accion").equals("")) {
                if (req.getParameter("accion").equals("HTML_TABLA_RALEO_FORWARDER")) {
                    htmlTablaRaleoForwarder(req, res);
                } else if (req.getParameter("accion").equals("HTML_TABLA_RALEO_HARVESTER")) {
                    htmlTablaRaleoHarvester(req, res);
                } else if (req.getParameter("accion").equals("DATOS_GRAFICO_RALEO_FORWARDER")) {
                    obtenerDatosGraficoForwarder(req, res);
                } else if (req.getParameter("accion").equals("DATOS_GRAFICO_RALEO_HARVESTER")) {
                    obtenerDatosGraficoHarvester(req, res);
                }
            } else {
                pagina = "/web/tableroGestion/raleo/administrarTableroRaleo.jsp";
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
     
    private void htmlTablaRaleoForwarder(HttpServletRequest req, HttpServletResponse res) {
        ControladorRegistroForwarder cRegistroForwarder = new ControladorRegistroForwarder();
        List<TablaResumenMesForwarderDTO> listaResultado = new ArrayList<TablaResumenMesForwarderDTO>();

        //mas codigo
        listaResultado = cRegistroForwarder.getResumenResultadosForwarder("Raleo");//i x mes


        //CABEZA TABLA FORWARDER COSECHA
        int mesInicial = 0;
        if(listaResultado.size() > 0){
            mesInicial = listaResultado.get(0).getMes();
        }
        String html = "<tr ><td>Estado</td><td>Item</td><td>Unidad</td>";
        List<Object> meses = obtenerNombreMeses(listaResultado.size(), mesInicial);
        String htmlMeses = "";
        for (int i = 0; i < meses.size(); i++) {
            htmlMeses += "<td>" + meses.get(i) + "</td>";
        }
        htmlMeses += "</tr>";

        html += htmlMeses;  

        //CONTENIDO RESULTADOS       
        String produccionResultados = "";
        String horasMaquinasResultados = "";
        String ciclosXHorasResultados = "";
        String productividadResultados = "";
        String tiempoMuertoResultados = "";
        for (int i = 0; i < listaResultado.size(); i++) {
            produccionResultados = "<td>" + listaResultado.get(i).getProduccion() + "</td>" + produccionResultados;
            horasMaquinasResultados = "<td>" + listaResultado.get(i).getHorasMaquina() + "</td>" + horasMaquinasResultados;
            ciclosXHorasResultados = "<td>" + redondear(listaResultado.get(i).getCiclosXHora(),2) + "</td>" + ciclosXHorasResultados;
            productividadResultados = "<td>" + redondear(listaResultado.get(i).getProductividad(),2) + "</td>" + productividadResultados;
            tiempoMuertoResultados = "<td>" + listaResultado.get(i).getTiempoMuerto() + "</td>" + tiempoMuertoResultados;
        }
        produccionResultados = "<tr ><td rowspan=\"5\" bgcolor=\"#F6D8CE\">Resultados</td><td>Producción</td><td>m3</td>" + produccionResultados + "</tr>";
        horasMaquinasResultados = "<tr><td >Horas máquina</td><td>hm</td>" + horasMaquinasResultados + "</tr>";
        ciclosXHorasResultados = "<tr><td >Ciclos por hora</td><td>ciclos/hm</td>" + ciclosXHorasResultados + "</tr>";
        productividadResultados = "<tr><td >Productividad m3</td><td>m3/hm</td>" + productividadResultados + "</tr>";
        tiempoMuertoResultados = "<tr><td >Tiempo muerto</td><td>hm</td>" + tiempoMuertoResultados + "</tr>";
        String htmlResultados = produccionResultados + horasMaquinasResultados + ciclosXHorasResultados + productividadResultados + tiempoMuertoResultados;

        //CONTENIDO POTENCIALES
        List<TablaResumenMesForwarderDTO> listaPotenciales = new ArrayList<TablaResumenMesForwarderDTO>();
        listaPotenciales = cRegistroForwarder.getResumenPotencialesForwarder("Raleo");//i x mes

        String produccionPotenciales = "";
        String horasMaquinasPotenciales = "";
        String ciclosXHorasPotenciales = "";
        String productividadPotenciales = "";
        for (int i = 0; i < listaPotenciales.size(); i++) {
            produccionPotenciales = "<td>" + listaPotenciales.get(i).getProduccion() + "</td>" + produccionPotenciales;
            horasMaquinasPotenciales = "<td>" + listaPotenciales.get(i).getHorasMaquina() + "</td>" + horasMaquinasPotenciales;
            ciclosXHorasPotenciales = "<td>" + redondear(listaPotenciales.get(i).getCiclosXHora(),2) + "</td>" + ciclosXHorasPotenciales;
            productividadPotenciales = "<td>" + redondear(listaPotenciales.get(i).getProductividad(),2) + "</td>" + productividadPotenciales;
        }
        produccionPotenciales = "<tr ><td rowspan=\"4\" bgcolor=\"#D8F6CE\">Potenciales</td><td>Producción</td><td>m3</td>" + produccionPotenciales + "</tr>";
        horasMaquinasPotenciales = "<tr><td >Horas máquina</td><td>hm</td>" + horasMaquinasPotenciales + "</tr>";
        ciclosXHorasPotenciales = "<tr><td >Ciclos por hora</td><td>ciclos/hm</td>" + ciclosXHorasPotenciales + "</tr>";
        productividadPotenciales = "<tr><td >Productividad m3</td><td>m3/hm</td>" + productividadPotenciales + "</tr>";
        String htmlPotenciales = produccionPotenciales + horasMaquinasPotenciales + ciclosXHorasPotenciales + productividadPotenciales;

        //CONTENIDO INDICADORES
        String OEEE = "";
        String disponibilidad = "";
        String eficiencia = "";
        String capacidad = "";
        for (int i = 0; i < listaResultado.size(); i++) {
            double dispo = (listaResultado.get(i).getHorasMaquina() / listaPotenciales.get(i).getHorasMaquina())*100;
            disponibilidad = "<td>" + redondear(dispo,2) + "%</td>" + disponibilidad;
            double efi = (listaResultado.get(i).getCiclosXHora() / listaPotenciales.get(i).getCiclosXHora())*100;
            eficiencia = "<td>" + redondear(efi,2) + "%</td>" + eficiencia;
            double cap = (listaResultado.get(i).getProductividad() / listaPotenciales.get(i).getProductividad())*100;
            capacidad = "<td>" + redondear(cap,2) + "%</td>" + capacidad;
            double oe = (dispo * efi * cap)/10000;
            OEEE = "<td>" + redondear(oe,2) + "%</td>" + OEEE;
        }
        OEEE = "<tr ><td rowspan=\"4\" bgcolor=\"#F2F5A9\">Indicadores</td><td>OEE</td><td>%</td>" + OEEE + "</tr>";
        disponibilidad = "<tr><td >Disponibilidad(hm)</td><td>%</td>" + disponibilidad + "</tr>";
        eficiencia = "<tr><td >Eficiencia (Ciclos/Hora)</td><td>%</td>" + eficiencia + "</tr>";
        capacidad = "<tr><td >Capacidad (m3/hora)</td><td>%</td>" + capacidad + "</tr>";

        String htmlIndicadores = OEEE + disponibilidad + eficiencia + capacidad;

        //CONTENIDO COMPLETO
        html += htmlIndicadores + htmlResultados + htmlPotenciales;

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(html);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void htmlTablaRaleoHarvester(HttpServletRequest req, HttpServletResponse res) {
        ControladorRegistroHarvester cRegistroHarvester = new ControladorRegistroHarvester();
        List<TablaResumenMesHarvesterDTO> listaResultado = new ArrayList<TablaResumenMesHarvesterDTO>();

        //mas codigo
        listaResultado = cRegistroHarvester.getResumenResultadosHarvester("Raleo");//i x mes


        //CABEZA TABLA HARVESTER RALEO
        int mesInicial = 0;
        if(listaResultado.size() > 0){
            mesInicial = listaResultado.get(0).getMes();
        }
        String html = "<tr ><td>Estado</td><td>Item</td><td>Unidad</td>";
        List<Object> meses = obtenerNombreMeses(listaResultado.size(), mesInicial);
        String htmlMeses = "";
        for (int i = 0; i < meses.size(); i++) {
            htmlMeses += "<td>" + meses.get(i) + "</td>";
        }
        htmlMeses += "</tr>";

        html += htmlMeses;

        //CONTENIDO RESULTADOS        
        String produccionResultados = "";
        String horasMaquinasResultados = "";
        String arbolesXHorasResultados = "";
        String productividadResultados = "";
        String tiempoMuertoResultados = "";
        for (int i = 0; i < listaResultado.size(); i++) {
            produccionResultados = "<td>" + listaResultado.get(i).getProduccion() + "</td>" + produccionResultados;
            horasMaquinasResultados = "<td>" + listaResultado.get(i).getHorasMaquina() + "</td>" + horasMaquinasResultados;
            arbolesXHorasResultados = "<td>" + redondear(listaResultado.get(i).getArbolesXHora(),2)+ "</td>" + arbolesXHorasResultados;
            productividadResultados = "<td>" + redondear(listaResultado.get(i).getProductividad(),2) + "</td>" + productividadResultados;
            tiempoMuertoResultados = "<td>" + listaResultado.get(i).getTiempoMuerto() + "</td>" + tiempoMuertoResultados;
        }
        produccionResultados = "<tr ><td rowspan=\"5\" bgcolor=\"#F6D8CE\">Resultados</td><td>Producción</td><td>m3</td>" + produccionResultados + "</tr>";
        horasMaquinasResultados = "<tr><td >Horas máquina</td><td>hm</td>" + horasMaquinasResultados + "</tr>";
        arbolesXHorasResultados = "<tr><td >Arbol por hora</td><td>arbol/hm</td>" + arbolesXHorasResultados + "</tr>";
        productividadResultados = "<tr><td >Productividad m3</td><td>m3/hm</td>" + productividadResultados + "</tr>";
        tiempoMuertoResultados = "<tr><td >Tiempo muerto</td><td>hm</td>" + tiempoMuertoResultados + "</tr>";
        String htmlResultados = produccionResultados + horasMaquinasResultados + arbolesXHorasResultados + productividadResultados + tiempoMuertoResultados;

        //CONTENIDO POTENCIALES
        List<TablaResumenMesHarvesterDTO> listaPotenciales = new ArrayList<TablaResumenMesHarvesterDTO>();
        listaPotenciales = cRegistroHarvester.getResumenPotencialesHarvester("Raleo");//i x mes

        String produccionPotenciales = "";
        String horasMaquinasPotenciales = "";
        String arbolXHorasPotenciales = "";
        String productividadPotenciales = "";
        for (int i = 0; i < listaPotenciales.size(); i++) {
            produccionPotenciales = "<td>" + listaPotenciales.get(i).getProduccion() + "</td>" + produccionPotenciales;
            horasMaquinasPotenciales = "<td>" + listaPotenciales.get(i).getHorasMaquina() + "</td>" + horasMaquinasPotenciales;
            arbolXHorasPotenciales = "<td>" + redondear(listaPotenciales.get(i).getArbolesXHora(),2)+ "</td>" + arbolXHorasPotenciales;
            productividadPotenciales = "<td>" + redondear(listaPotenciales.get(i).getProductividad(),2) + "</td>" + productividadPotenciales;
        }
        produccionPotenciales = "<tr ><td rowspan=\"4\" bgcolor=\"#D8F6CE\">Potenciales</td><td>Producción</td><td>m3</td>" + produccionPotenciales + "</tr>";
        horasMaquinasPotenciales = "<tr><td >Horas máquina</td><td>hm</td>" + horasMaquinasPotenciales + "</tr>";
        arbolXHorasPotenciales = "<tr><td >Arbol por hora</td><td>arbol/hr</td>" + arbolXHorasPotenciales + "</tr>";
        productividadPotenciales = "<tr><td >Productividad m3</td><td>m3/hm</td>" + productividadPotenciales + "</tr>";
        String htmlPotenciales = produccionPotenciales + horasMaquinasPotenciales + arbolXHorasPotenciales + productividadPotenciales;

        //CONTENIDO INDICADORES
        String OEEE = "";
        String disponibilidad = "";
        String eficiencia = "";
        String capacidad = "";
        for (int i = 0; i < listaResultado.size(); i++) {
            double dispo = (listaResultado.get(i).getHorasMaquina() / listaPotenciales.get(i).getHorasMaquina())*100;
            disponibilidad = "<td>" + redondear(dispo,2) + "%</td>" + disponibilidad;
            double efi = (listaResultado.get(i).getArbolesXHora()/ listaPotenciales.get(i).getArbolesXHora())*100;
            eficiencia = "<td>" + redondear(efi,2) + "%</td>" + eficiencia;
            double cap = (listaResultado.get(i).getProductividad() / listaPotenciales.get(i).getProductividad())*100;
            capacidad = "<td>" + redondear(cap,2) + "%</td>" + capacidad;
            double oe = (dispo * efi * cap)/10000;
            OEEE = "<td>" + redondear(oe,2) + "%</td>" + OEEE;
        }
        OEEE = "<tr ><td rowspan=\"4\" bgcolor=\"#F2F5A9\">Indicadores</td><td>OEE</td><td>%</td>" + OEEE + "</tr>";
        disponibilidad = "<tr><td >Disponibilidad(hm)</td><td>%</td>" + disponibilidad + "</tr>";
        eficiencia = "<tr><td >Eficiencia (Ciclos/Hora)</td><td>%</td>" + eficiencia + "</tr>";
        capacidad = "<tr><td >Capacidad (m3/hora)</td><td>%</td>" + capacidad + "</tr>";

        String htmlIndicadores = OEEE + disponibilidad + eficiencia + capacidad;

        //CONTENIDO COMPLETO
        html += htmlIndicadores + htmlResultados + htmlPotenciales;

        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(html);
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
        
    public List<Object> obtenerNombreMeses(int cantidadMeses, int mesInicial){
        String meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        Calendar c1 = Calendar.getInstance();
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        
        int m = 0;
        if(mesInicial > 0){
            m = mesInicial-1;
        }else{
            m = Integer.parseInt(mes);
        }
        
        List<Object> listaMeses = new ArrayList<Object>();
        
        for (int i = 0; i < cantidadMeses; i++) {
            listaMeses.add(0, meses[m]);
            m--;
            if (m == -1) {
                m = 11;
            }
        }
        
        return listaMeses;
    }

    private void obtenerDatosGraficoForwarder(HttpServletRequest req, HttpServletResponse res) {
        //CONTENIDO RESULTADOS
        ControladorRegistroForwarder cRegistroForwarder = new ControladorRegistroForwarder();
        List<TablaResumenMesForwarderDTO> listaResultado = new ArrayList<TablaResumenMesForwarderDTO>();     
        List<TablaResumenMesForwarderDTO> listaPotenciales = new ArrayList<TablaResumenMesForwarderDTO>();
        List<DataSetsGraficoLinea> seriesGraficos = new ArrayList<DataSetsGraficoLinea>();
        
        listaResultado = cRegistroForwarder.getResumenResultadosForwarder("Raleo");//i x mes
        listaPotenciales = cRegistroForwarder.getResumenPotencialesForwarder("Raleo");//i x mes
        
        int mesInicial = 0;
        if(listaResultado.size() > 0){
            mesInicial = listaResultado.get(0).getMes();
        }
        List<Object> meses = obtenerNombreMeses(listaResultado.size(),mesInicial);
        
        List<Object> horasMaquinaReal = new ArrayList<Object>();
        List<Object> horasMaquinaPlan = new ArrayList<Object>();
        List<Object> produccionReal = new ArrayList<Object>();
        List<Object> produccionPlan = new ArrayList<Object>();
        List<Object> productividadReal = new ArrayList<Object>();
        List<Object> oee = new ArrayList<Object>();
        List<Object> disponibilidad = new ArrayList<Object>();
        List<Object> eficiencia = new ArrayList<Object>();
        List<Object> capacidad = new ArrayList<Object>();
        for (int i = 0; i < listaResultado.size(); i++) {
            horasMaquinaReal.add(0,listaResultado.get(i).getHorasMaquina());
            horasMaquinaPlan.add(0,listaPotenciales.get(i).getHorasMaquina());
            produccionReal.add(0,listaResultado.get(i).getProduccion());
            produccionPlan.add(0,listaPotenciales.get(i).getProduccion());
            productividadReal.add(0,redondear(listaResultado.get(i).getProductividad(),2));
            
            double disp = (listaResultado.get(i).getHorasMaquina()/listaPotenciales.get(i).getHorasMaquina())*100;
            disp = redondear(disp, 2);
            disponibilidad.add(0,disp);
            double efi = (listaResultado.get(i).getCiclosXHora()/listaPotenciales.get(i).getCiclosXHora())*100;
            efi = redondear(efi, 2);
            eficiencia.add(0,efi);
            double cap = (listaResultado.get(i).getProductividad()/listaPotenciales.get(i).getProductividad())*100;
            cap = redondear(cap, 2);
            capacidad.add(0,cap);
            double oe = (disp*efi*cap)/10000;
            oe = redondear(oe, 2);
            oee.add(0,oe);
        }
        //GRAFICO HORAS MAQUINAS
        DataSetsGraficoLinea serieHorasReal = new DataSetsGraficoLinea();
        serieHorasReal.label= "Real";
        serieHorasReal.fillColor= "rgba(220,220,220,0.2)";
        serieHorasReal.strokeColor= "rgba(220,220,220,1)";
        serieHorasReal.pointColor= "rgba(220,220,220,1)";
        serieHorasReal.pointStrokeColor= "#fff";
        serieHorasReal.pointHighlightFill= "#fff";
        serieHorasReal.pointHighlightStroke= "rgba(220,220,220,1)";
        serieHorasReal.data = horasMaquinaReal;
        serieHorasReal.listaMeses = meses;
        
        DataSetsGraficoLinea serieHorasPlan = new DataSetsGraficoLinea();
        serieHorasPlan.label= "Potencial";
        serieHorasPlan.fillColor= "rgba(151,187,205,0.2)";
        serieHorasPlan.strokeColor= "rgba(151,187,205,1)";
        serieHorasPlan.pointColor= "rgba(151,187,205,1)";
        serieHorasPlan.pointStrokeColor= "#fff";
        serieHorasPlan.pointHighlightFill= "#fff";
        serieHorasPlan.pointHighlightStroke= "rgba(151,187,205,1)";
        serieHorasPlan.data = horasMaquinaPlan;
        serieHorasPlan.listaMeses = meses;
        //GRAFICO PRODUCCION PRODUCTIVIDAD
        DataSetsGraficoLinea serieProduccionReal = new DataSetsGraficoLinea();
        serieProduccionReal.label= "Resultado";
        serieProduccionReal.fillColor= "rgba(151,187,205,0.2)";
        serieProduccionReal.strokeColor= "rgba(151,187,205,1)";
        serieProduccionReal.pointColor= "rgba(151,187,205,1)";
        serieProduccionReal.pointStrokeColor= "#fff";
        serieProduccionReal.pointHighlightFill= "#fff";
        serieProduccionReal.pointHighlightStroke= "rgba(151,187,205,1)";
        serieProduccionReal.data = produccionReal;
        serieProduccionReal.listaMeses = meses;
        
        DataSetsGraficoLinea serieProduccionPlan = new DataSetsGraficoLinea();
        serieProduccionPlan.label= "Resultado";
        serieProduccionPlan.fillColor= "rgba(151,187,205,0.2)";
        serieProduccionPlan.strokeColor= "rgba(151,187,205,1)";
        serieProduccionPlan.pointColor= "rgba(151,187,205,1)";
        serieProduccionPlan.pointStrokeColor= "#fff";
        serieProduccionPlan.pointHighlightFill= "#fff";
        serieProduccionPlan.pointHighlightStroke= "rgba(151,187,205,1)";
        serieProduccionPlan.data = produccionPlan;
        serieProduccionPlan.listaMeses = meses;
        
        DataSetsGraficoLinea serieProductividadReal = new DataSetsGraficoLinea();
        serieProductividadReal.label= "Resultado";
        serieProductividadReal.fillColor= "rgba(151,187,205,0.2)";
        serieProductividadReal.strokeColor= "rgba(151,187,205,1)";
        serieProductividadReal.pointColor= "rgba(151,187,205,1)";
        serieProductividadReal.pointStrokeColor= "#fff";
        serieProductividadReal.pointHighlightFill= "#fff";
        serieProductividadReal.pointHighlightStroke= "rgba(151,187,205,1)";
        serieProductividadReal.data = productividadReal;
        serieProductividadReal.listaMeses = meses;
        
        //GRAFICO INDICADORES OPERACIONALES
        DataSetsGraficoLinea serieDisponibilidad = new DataSetsGraficoLinea();
        serieDisponibilidad.label= "Resultado";
        serieDisponibilidad.fillColor= "rgba(151,187,205,0.2)";
        serieDisponibilidad.strokeColor= "rgba(151,187,205,1)";
        serieDisponibilidad.pointColor= "rgba(151,187,205,1)";
        serieDisponibilidad.pointStrokeColor= "#fff";
        serieDisponibilidad.pointHighlightFill= "#fff";
        serieDisponibilidad.pointHighlightStroke= "rgba(151,187,205,1)";
        serieDisponibilidad.data = disponibilidad;
        serieDisponibilidad.listaMeses = meses;
        
        DataSetsGraficoLinea serieEficiencia = new DataSetsGraficoLinea();
        serieEficiencia.label= "Resultado";
        serieEficiencia.fillColor= "rgba(151,187,205,0.2)";
        serieEficiencia.strokeColor= "rgba(151,187,205,1)";
        serieEficiencia.pointColor= "rgba(151,187,205,1)";
        serieEficiencia.pointStrokeColor= "#fff";
        serieEficiencia.pointHighlightFill= "#fff";
        serieEficiencia.pointHighlightStroke= "rgba(151,187,205,1)";
        serieEficiencia.data = eficiencia;
        serieEficiencia.listaMeses = meses;
        
        DataSetsGraficoLinea serieCapacidad = new DataSetsGraficoLinea();
        serieCapacidad.label= "Resultado";
        serieCapacidad.fillColor= "rgba(151,187,205,0.2)";
        serieCapacidad.strokeColor= "rgba(151,187,205,1)";
        serieCapacidad.pointColor= "rgba(151,187,205,1)";
        serieCapacidad.pointStrokeColor= "#fff";
        serieCapacidad.pointHighlightFill= "#fff";
        serieCapacidad.pointHighlightStroke= "rgba(151,187,205,1)";
        serieCapacidad.data = capacidad;
        serieCapacidad.listaMeses = meses;
        
        DataSetsGraficoLinea serieOEE = new DataSetsGraficoLinea();
        serieOEE.label= "Resultado";
        serieOEE.fillColor= "rgba(151,187,205,0.2)";
        serieOEE.strokeColor= "rgba(151,187,205,1)";
        serieOEE.pointColor= "rgba(151,187,205,1)";
        serieOEE.pointStrokeColor= "#fff";
        serieOEE.pointHighlightFill= "#fff";
        serieOEE.pointHighlightStroke= "rgba(151,187,205,1)";
        serieOEE.data = oee;
        serieOEE.listaMeses = meses;
        
        //AGREGAMOS LAS SERIES
        seriesGraficos.add(serieHorasReal);//[0]
        seriesGraficos.add(serieHorasPlan);//[1]
        seriesGraficos.add(serieProduccionReal);//[2]
        seriesGraficos.add(serieProduccionPlan);//[3]
        seriesGraficos.add(serieProductividadReal);//[4]
        seriesGraficos.add(serieDisponibilidad);//[5]
        seriesGraficos.add(serieEficiencia);//[6]
        seriesGraficos.add(serieCapacidad);//[7]
        seriesGraficos.add(serieOEE);//[8]
        
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(seriesGraficos);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtenerDatosGraficoHarvester(HttpServletRequest req, HttpServletResponse res) {
        //CONTENIDO RESULTADOS
        ControladorRegistroHarvester cRegistroHarvester = new ControladorRegistroHarvester();
        List<TablaResumenMesHarvesterDTO> listaResultado = new ArrayList<TablaResumenMesHarvesterDTO>();     
        List<TablaResumenMesHarvesterDTO> listaPotenciales = new ArrayList<TablaResumenMesHarvesterDTO>();
        List<DataSetsGraficoLinea> seriesGraficos = new ArrayList<DataSetsGraficoLinea>();
        
        listaResultado = cRegistroHarvester.getResumenResultadosHarvester("Raleo");//i x mes
        listaPotenciales = cRegistroHarvester.getResumenPotencialesHarvester("Raleo");//i x mes
        
        int mesInicial = 0;
        if(listaResultado.size() > 0){
            mesInicial = listaResultado.get(0).getMes();
        }
        List<Object> meses = obtenerNombreMeses(listaResultado.size(),mesInicial);
        
        List<Object> horasMaquinaReal = new ArrayList<Object>();
        List<Object> horasMaquinaPlan = new ArrayList<Object>();
        List<Object> produccionReal = new ArrayList<Object>();
        List<Object> produccionPlan = new ArrayList<Object>();
        List<Object> productividadReal = new ArrayList<Object>();
        List<Object> oee = new ArrayList<Object>();
        List<Object> disponibilidad = new ArrayList<Object>();
        List<Object> eficiencia = new ArrayList<Object>();
        List<Object> capacidad = new ArrayList<Object>();
        for (int i = 0; i < listaResultado.size(); i++) {
            horasMaquinaReal.add(0,listaResultado.get(i).getHorasMaquina());
            horasMaquinaPlan.add(0,listaPotenciales.get(i).getHorasMaquina());
            produccionReal.add(0,listaResultado.get(i).getProduccion());
            produccionPlan.add(0,listaPotenciales.get(i).getProduccion());
            productividadReal.add(0,redondear(listaResultado.get(i).getProductividad(),2));
            
            double disp = (listaResultado.get(i).getHorasMaquina()/listaPotenciales.get(i).getHorasMaquina())*100;
            disp = redondear(disp, 2);
            disponibilidad.add(0,disp);
            double efi = (listaResultado.get(i).getArbolesXHora()/listaPotenciales.get(i).getArbolesXHora())*100;
            efi = redondear(efi, 2);
            eficiencia.add(0,efi);
            double cap = (listaResultado.get(i).getProductividad()/listaPotenciales.get(i).getProductividad())*100;
            cap = redondear(cap, 2);
            capacidad.add(0,cap);
            double oe = (disp*efi*cap)/10000;
            oe = redondear(oe, 2);
            oee.add(0,oe);
        }
        //GRAFICO HORAS MAQUINAS
        DataSetsGraficoLinea serieHorasReal = new DataSetsGraficoLinea();
        serieHorasReal.label= "Real";
        serieHorasReal.fillColor= "rgba(220,220,220,0.2)";
        serieHorasReal.strokeColor= "rgba(220,220,220,1)";
        serieHorasReal.pointColor= "rgba(220,220,220,1)";
        serieHorasReal.pointStrokeColor= "#fff";
        serieHorasReal.pointHighlightFill= "#fff";
        serieHorasReal.pointHighlightStroke= "rgba(220,220,220,1)";
        serieHorasReal.data = horasMaquinaReal;
        serieHorasReal.listaMeses = meses;
        
        DataSetsGraficoLinea serieHorasPlan = new DataSetsGraficoLinea();
        serieHorasPlan.label= "Potencial";
        serieHorasPlan.fillColor= "rgba(151,187,205,0.2)";
        serieHorasPlan.strokeColor= "rgba(151,187,205,1)";
        serieHorasPlan.pointColor= "rgba(151,187,205,1)";
        serieHorasPlan.pointStrokeColor= "#fff";
        serieHorasPlan.pointHighlightFill= "#fff";
        serieHorasPlan.pointHighlightStroke= "rgba(151,187,205,1)";
        serieHorasPlan.data = horasMaquinaPlan;
        serieHorasPlan.listaMeses = meses;
        //GRAFICO PRODUCCION PRODUCTIVIDAD
        DataSetsGraficoLinea serieProduccionReal = new DataSetsGraficoLinea();
        serieProduccionReal.label= "Resultado";
        serieProduccionReal.fillColor= "rgba(151,187,205,0.2)";
        serieProduccionReal.strokeColor= "rgba(151,187,205,1)";
        serieProduccionReal.pointColor= "rgba(151,187,205,1)";
        serieProduccionReal.pointStrokeColor= "#fff";
        serieProduccionReal.pointHighlightFill= "#fff";
        serieProduccionReal.pointHighlightStroke= "rgba(151,187,205,1)";
        serieProduccionReal.data = produccionReal;
        serieProduccionReal.listaMeses = meses;
        
        DataSetsGraficoLinea serieProduccionPlan = new DataSetsGraficoLinea();
        serieProduccionPlan.label= "Resultado";
        serieProduccionPlan.fillColor= "rgba(151,187,205,0.2)";
        serieProduccionPlan.strokeColor= "rgba(151,187,205,1)";
        serieProduccionPlan.pointColor= "rgba(151,187,205,1)";
        serieProduccionPlan.pointStrokeColor= "#fff";
        serieProduccionPlan.pointHighlightFill= "#fff";
        serieProduccionPlan.pointHighlightStroke= "rgba(151,187,205,1)";
        serieProduccionPlan.data = produccionPlan;
        serieProduccionPlan.listaMeses = meses;
        
        DataSetsGraficoLinea serieProductividadReal = new DataSetsGraficoLinea();
        serieProductividadReal.label= "Resultado";
        serieProductividadReal.fillColor= "rgba(151,187,205,0.2)";
        serieProductividadReal.strokeColor= "rgba(151,187,205,1)";
        serieProductividadReal.pointColor= "rgba(151,187,205,1)";
        serieProductividadReal.pointStrokeColor= "#fff";
        serieProductividadReal.pointHighlightFill= "#fff";
        serieProductividadReal.pointHighlightStroke= "rgba(151,187,205,1)";
        serieProductividadReal.data = productividadReal;
        serieProductividadReal.listaMeses = meses;
        
        //GRAFICO INDICADORES OPERACIONALES
        DataSetsGraficoLinea serieDisponibilidad = new DataSetsGraficoLinea();
        serieDisponibilidad.label= "Resultado";
        serieDisponibilidad.fillColor= "rgba(151,187,205,0.2)";
        serieDisponibilidad.strokeColor= "rgba(151,187,205,1)";
        serieDisponibilidad.pointColor= "rgba(151,187,205,1)";
        serieDisponibilidad.pointStrokeColor= "#fff";
        serieDisponibilidad.pointHighlightFill= "#fff";
        serieDisponibilidad.pointHighlightStroke= "rgba(151,187,205,1)";
        serieDisponibilidad.data = disponibilidad;
        serieDisponibilidad.listaMeses = meses;
        
        DataSetsGraficoLinea serieEficiencia = new DataSetsGraficoLinea();
        serieEficiencia.label= "Resultado";
        serieEficiencia.fillColor= "rgba(151,187,205,0.2)";
        serieEficiencia.strokeColor= "rgba(151,187,205,1)";
        serieEficiencia.pointColor= "rgba(151,187,205,1)";
        serieEficiencia.pointStrokeColor= "#fff";
        serieEficiencia.pointHighlightFill= "#fff";
        serieEficiencia.pointHighlightStroke= "rgba(151,187,205,1)";
        serieEficiencia.data = eficiencia;
        serieEficiencia.listaMeses = meses;
        
        DataSetsGraficoLinea serieCapacidad = new DataSetsGraficoLinea();
        serieCapacidad.label= "Resultado";
        serieCapacidad.fillColor= "rgba(151,187,205,0.2)";
        serieCapacidad.strokeColor= "rgba(151,187,205,1)";
        serieCapacidad.pointColor= "rgba(151,187,205,1)";
        serieCapacidad.pointStrokeColor= "#fff";
        serieCapacidad.pointHighlightFill= "#fff";
        serieCapacidad.pointHighlightStroke= "rgba(151,187,205,1)";
        serieCapacidad.data = capacidad;
        serieCapacidad.listaMeses = meses;
        
        DataSetsGraficoLinea serieOEE = new DataSetsGraficoLinea();
        serieOEE.label= "Resultado";
        serieOEE.fillColor= "rgba(151,187,205,0.2)";
        serieOEE.strokeColor= "rgba(151,187,205,1)";
        serieOEE.pointColor= "rgba(151,187,205,1)";
        serieOEE.pointStrokeColor= "#fff";
        serieOEE.pointHighlightFill= "#fff";
        serieOEE.pointHighlightStroke= "rgba(151,187,205,1)";
        serieOEE.data = oee;
        serieOEE.listaMeses = meses;
        
        //AGREGAMOS LAS SERIES
        seriesGraficos.add(serieHorasReal);//[0]
        seriesGraficos.add(serieHorasPlan);//[1]
        seriesGraficos.add(serieProduccionReal);//[2]
        seriesGraficos.add(serieProduccionPlan);//[3]
        seriesGraficos.add(serieProductividadReal);//[4]
        seriesGraficos.add(serieDisponibilidad);//[5]
        seriesGraficos.add(serieEficiencia);//[6]
        seriesGraficos.add(serieCapacidad);//[7]
        seriesGraficos.add(serieOEE);//[8]
        
        res.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(seriesGraficos);
            out = res.getWriter();
            out.println(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
