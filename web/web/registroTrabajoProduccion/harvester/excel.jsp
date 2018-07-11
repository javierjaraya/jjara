<%-- 
    Document   : jsp
    Created on : 02-06-2015, 14:09:05
    Author     : Javier J
--%>

<%@page import="cl.forestcenter.sistema.dto.RegistroHarvesterDTO"%>
<%@page import="cl.forestcenter.sistema.controladores.mantenedores.ControladorRegistroHarvester"%>
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
        <% response.setHeader("Content-Disposition", "attachment;filename=\"BD_HV.xls\""); %>        
        <table>
            <tr >
                <td width="35" valign="top">Folio</td>
                <td width="85" valign="top">Fecha</td>
                <td width="72" valign="top">Faena</td>
                <td width="75" valign="top">Maquina</td>
                <td width="210" valign="top">Operador</td>
                <td width="103" valign="top">Turno</td>
                <td width="50" valign="top">Jornada</td>
                <td width="210" valign="top">Predio</td>
                <td width="67" valign="top">Horometro Inicial</td>
                <td width="67" valign="top">Horometro Final</td>
                <td width="50" valign="top">m3/Arbol</td>
                <td width="50" valign="top">H/Maq Plan</td>
                <td width="50" valign="top">H/Maq Real</td>
                <td width="50" valign="top">Arb/Hr Plan</td>
                <td width="50" valign="top">Arb/Hr Real</td>
                <td width="50" valign="top">Arb Plan</td>
                <td width="50" valign="top">Arb Real</td>
                <td width="50" valign="top">m3/Hr Plan</td>
                <td width="50" valign="top">m3/Hr Real</td>
                <td width="50" valign="top">m3 Plan</td>
                <td width="50" valign="top">m3 Real</td>                
            </tr> 
            <%
                ControladorRegistroHarvester cRegistroHarvester = new ControladorRegistroHarvester();
                List<RegistroHarvesterDTO> lista = new ArrayList<RegistroHarvesterDTO>();
                lista = cRegistroHarvester.getAllRegistroHarvester("");
                for (int i = 0; i < lista.size(); i++) {%>
            <tr>
                <td ><%= lista.get(i).getFolio()%></td>
                <td><%= lista.get(i).getFechaRegistro()%></td>
                <td><%= lista.get(i).getFaena()%></td>
                <td><%= lista.get(i).getMaquina()%></td>
                <td><%= lista.get(i).getOperador()%></td>
                <td><%= lista.get(i).getTurno()%></td>
                <td><%= lista.get(i).getJornada()%></td>
                <td><%= lista.get(i).getPredio()%></td>
                <td><%= lista.get(i).getHorometroInicial()%></td>
                <td><%= lista.get(i).getHorometroFinal()%></td>
                <td><%= lista.get(i).getmArbol()%></td>
                <td><%= lista.get(i).gethMaqPlan()%></td>
                <td><%= lista.get(i).gethMaqReal()%></td>
                <td><%= lista.get(i).getArbHrPlan()%></td>
                <td><%= lista.get(i).getArbHrReal()%></td>
                <td><%= lista.get(i).getArbPlan()%></td>
                <td><%= lista.get(i).getArbReal()%></td>
                <td><%= lista.get(i).getmHrPlan()%></td>
                <td><%= lista.get(i).getmHrReal()%></td>
                <td><%= lista.get(i).getmPlan()%></td>
                <td><%= lista.get(i).getmReal()%></td>
            </tr>

            <% }%>
        </table>
    </body>
</html>
