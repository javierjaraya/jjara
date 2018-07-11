<%-- 
    Document   : login
    Created on : 22-04-2015, 18:31:23
    Author     : Javier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>::FOREST CENTER::</title>
        <link rel="shortcut icon" href="/jjara/files/img/favicon.ico" />
        <link href="/jjara/files/css/estiloLogin.css" rel="stylesheet" type="text/css" />

        <!-- Start WOWSlider.com HEAD section -->
        <link rel="stylesheet" type="text/css" href="/jjara/files/img/banner/engine1//style.css" media="screen" />
        <script type="text/javascript" src="/jjara/files/img/banner/engine1//jquery.js"></script>
        <!-- End WOWSlider.com HEAD section -->

        <script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
        <script>
            function cargarOpcion(url) {//CARGANDO LISTO                
                var formbody = document.getElementById("formbody");
                formbody.style.display = 'none';
                var formRecuperar = document.getElementById("formRecuperar");
                formRecuperar.style.display = 'block';
                $("div#formRecuperar").load(url);
            }
        </script>
    </head>
    <body>
        <div id="wrapperfalso">
            <div id="sesion"><strong class="coloramarillo"><em>Bienvenido,</em></strong></div>
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
                        <span class="wsl"><a href="http://wowslider.com">Photo Slider jQuery</a> by WOWSlider.com v4.8</span>
                        <a href="#" class="ws_frame"></a>
                        <div class="ws_shadow"></div>
                    </div>
                    <script type="text/javascript" src="/jjara/files/img/banner/engine1//wowslider.js"></script>
                    <script type="text/javascript" src="/jjara/files/img/banner/engine1//script.js"></script>
                    <!-- End WOWSlider.com BODY section -->

                </div>
                <div id="contenido">                    
                    <div id="formbody">
                        <div id="cajainicio"><img src="/jjara/files/img/inicio.png" width="267" height="40" /></div>
                        <div id="caja"></div>
                        <form action="login" method="post">
                            <div id="caja1">Nombre: </div>
                            <input type="text" name="usuario" placeholder="Nombre de usuario " class="text" style="background-color:#CCC;" />
                            <div id="caja1">Contraseña</div> 
                            <input name="password" type="password" class="text" placeholder="••••••••••••" style="background-color:#CCC;" size="5" />
                            <div class="texto" id="caja2"><a onClick="cargarOpcion('administrarRecuperarClave')"  ><strong>¿Olvidó su contraseña?</strong></a></div>
                            <input name="passwordEnc" type="hidden" id="passwordEnc">
                            <input type="submit" value="Entrar" class="submit" style="background-color:#06C;"  />
                        </form>
                    </div>
                    <div id="formRecuperar" style="display: none;">

                    </div>
                </div>
                <div id="footer">Forest Center - Dirección: Av. O’Higgins #3630, Chillan Viejo.. Fono: (56 42) 2228119. Todos los derechos reservados 2015</div>
            </div>

            <script type="text/javascript">
                function recuperarClave() {
                    var email = document.getElementById("email").value;
                    if (email != null && email.length > 0) {
                        $.ajax({
                            url: "administrarRecuperarClave",
                            type: "post",
                            data: $("#fmrecuperar").serialize()+"&accion=RECUPERAR_CLAVE",
                            success: function (data) {
                                console.log(data);
                                var data = eval('(' + data + ')');
                                if (data.success) {
                                    document.getElementById("fmrecuperar").reset();
                                    var formbody = document.getElementById("formbody");
                                    formbody.style.display = 'block';
                                    var formRecuperar = document.getElementById("formRecuperar");
                                    formRecuperar.style.display = 'none';
                                }
                                $.messager.alert('Aviso', data.statusText);
                            }
                        });
                    } else {
                        $.messager.alert('Alerta', "Debe ingresar su direccion de correo electronico.");
                    }
                }
            </script>
    </body>
</html>