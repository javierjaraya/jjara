<%-- 
    Document   : sistema
    Created on : 23-04-2015, 09:56:19 PM
    Author     : Javier-PC
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>::FOREST CENTER::</title>
        <link rel="shortcut icon" href="/jjara/files/img/favicon.ico" />
        <link href="/jjara/files/css/estilointerior.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="/jjara/files/Complementos/jQuery/jquery-2.1.3.min.js"></script>
        <!-- DataGridView--> 
        <link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
        <script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>

        <!-- Start WOWSlider.com HEAD section -->
        <link rel="stylesheet" type="text/css" href="/jjara/files/img/banner/engine1//style.css" media="screen" />
        <script type="text/javascript" src="/jjara/files/img/banner/engine1//jquery.js"></script>
        <!-- End WOWSlider.com HEAD section -->
        <script type="text/javascript" src="/jjara/files/Complementos/GraficosChart/Chart.js"></script>

        <script type="text/javascript">
            function obtieneSubMenu(id) {//CARGANDO LISTO
                var menuPrincipal = document.getElementById("menuPrincipal");
                menuPrincipal.style.display = "none";
                var indicador = document.getElementById("indicador");
                indicador.style.display = 'none';
                var subMenu = document.getElementById("subMenu");
                subMenu.style.display = 'block';
                $.ajax({
                    data: 'accion=SUBMENU&id=' + id,
                    url: 'sistema',
                    success: function (data) {

                        $('div#subMenu').html(data);
                    }
                });
            }
            function cargarOpcion(url) {
                $('div#cargando').html("<img src='files/img/carg.gif'/>");
                var subContenido = document.getElementById("subContenido");
                subContenido.style.display = 'block';
                $("div#subContenido").load(url);
            }

            function menuPrincipal() {
                var subMenu = document.getElementById("subMenu");
                subMenu.style.display = 'none';
                var menuPrincipal = document.getElementById("menuPrincipal");
                menuPrincipal.style.display = "none";
                $('div#subMenu').html("");
                var subContenido = document.getElementById("subContenido");
                subContenido.style.display = 'none';
                $('div#subContenido').html("");
                var menuPrincipal = document.getElementById("menuPrincipal");
                menuPrincipal.style.display = 'block';
                var indicador = document.getElementById("indicador");
                indicador.style.display = 'block';
            }

            function mostrarOcultarIndicador() {
                $('#contenido-indicador').toggle("fast");
            }

            $(document).ready(function () {
                obtenerIndicadorHTML();
            });

            function obtenerIndicadorHTML() {
                $.getJSON('/jjara/planificacionMetaProduccionFaena?accion=METAS_ACTIVAS_HOY',
                        function (data) {
                            $('div#indicador').html(data);

                        }
                );
            }
        </script>
    </head>
    <body>
        <div id="wrapperfalso">
            <div id="sesion"><strong class="coloramarillo"><em>Bienvenido,</em></strong> <%= request.getSession().getAttribute("nombreEmpleado")%> /<a href="cerrarSesion">Salir</a></div>
            <div id="wrapper">
                <div id="header">
                    <!-- Start WOWSlider.com BODY section id=wowslider-container1 -->
                    <div id="wowslider-container1">
                        <div class="ws_images"><ul>
                                <li><img src="/jjara/files/img/banner/data1/images/header.jpg" alt="header" title="header" id="wows1_0"/></li>
                                <li><img src="/jjara/files/img/banner/data1/images/header2.jpg" alt="header2" title="header2" id="wows1_1"/></li>
                                <li><img src="/jjara/files/img/banner/data1/images/header3.jpg" alt="header3" title="header3" id="wows1_2"/></li>
                                <li><img src="/jjara/files/img/banner/data1/images/header4.jpg" alt="header4" title="header4" id="wows1_3"/></li>
                            </ul></div>

                        <a href="#" class="ws_frame"></a>
                        <div class="ws_shadow"></div>
                    </div>
                    <script type="text/javascript" src="/jjara/files/img/banner/engine1//wowslider.js"></script>
                    <script type="text/javascript" src="/jjara/files/img/banner/engine1//script.js"></script>
                    <!-- End WOWSlider.com BODY section -->
                </div>
                <div id="indicador">
                    <div id="header-indicador">
                        <img onclick="mostrarOcultarIndicador()" src="/jjara/files/img/indicador/alertaenverde.png" onmouseover="this.src = '/jjara/files/img/indicador/alertaenverde2.png'" onmouseout="this.src = '/jjara/files/img/indicador/alertaenverde.png'">                        
                    </div>
                    <div id="contenido-indicador" style="display: none;">
                        este es el contenido del indicador
                    </div>
                </div>
                <div id="contenido">
                    <div id="menuPrincipal">
                        <% Object menu = request.getSession().getAttribute("MenuPrincipalHTML");
                            String mostrar = "";
                            if (menu != null) {
                                mostrar = (String) menu;
                            }
                        %>
                        <%= mostrar%>                        
                    </div>
                    <div id="subMenu" style="display: 'none';"></div>
                    <div id="subContenido" style="float: left"></div>                   
                </div>
                <div id="footer">Forest Center - Dirección: Av. O’Higgins #3630, Chillan Viejo.. Fono: (56 42) 2228119. Todos los derechos reservados 2015</div>
            </div>
        </div>
    </body>
</html>

