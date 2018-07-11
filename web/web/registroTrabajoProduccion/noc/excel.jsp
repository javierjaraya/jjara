<%-- 
    Document   : jsp
    Created on : 02-06-2015, 14:09:05
    Author     : Javier J
--%>

<%@page import="cl.forestcenter.sistema.dto.RegistroNocDTO"%>
<%@page import="cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroNOC"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.Gson"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>        
        <style type="text/css">
            * {
                background: transparent;
            }
            table, td, th {
                background: transparent;
                border: 1px solid #eee;
            }

        </style>
    </head>
    <body>
        <% response.setHeader("Content-Disposition", "attachment;filename=\"BD_NOC.xls\""); %>        
        <table>
            <tr >
                <td width="35" valign="top">Folio</td>
                <td width="85" valign="top">Fecha</td>
                <td width="72" valign="top">Faena</td>
                <td width="75" valign="top">Maquina</td>
                <td width="210" valign="top">Predio</td>
                <td width="100" valign="top">m3 Notificados</td>
                <td width="200" valign="top">Observacion</td>                
            </tr> 
            <%
                ControladorRegistroNOC cRegistroNOC = new ControladorRegistroNOC();
                List<RegistroNocDTO> lista = new ArrayList<RegistroNocDTO>();
                lista = cRegistroNOC.getAllRegistroNOC("");
                for (int i = 0; i < lista.size(); i++) {%>
            <tr>
                <td ><%= lista.get(i).getIdRegistroNoc()%></td>
                <td><%= lista.get(i).getFechaRegistro()%></td>
                <td><%= lista.get(i).getFaena()%></td>
                <td><%= lista.get(i).getMaquina()%></td>
                <td><%= lista.get(i).getPredio()%></td>
                <td><%= lista.get(i).getM3()%></td>
                <td><%= lista.get(i).getObservacion()%></td>
            </tr>

            <% }%>
        </table>
    </body>
</html>
