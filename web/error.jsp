<%-- 
    Document   : login
    Created on : 22-04-2015, 18:31:23
    Author     : Javier
--%>
<link href="/jjara/files/css/estiloLogin.css" rel="stylesheet" type="text/css" />
<div id="formbody">
    <div id="cajainicio"><img src="/jjara/files/img/inicio.png" width="267" height="40" /></div>
    <div id="caja"></div>
    <label><font size="3" color="red">Usuario o contraseña incorrecto</font></label><br>
    <form action="login" method="post">
        <div id="caja1">Nombre: </div>
        <input type="text" name="usuario" placeholder="Nombre de usuario " class="text" style="background-color:#CCC;" />
        <div id="caja1">Contraseña</div> 
        <input name="password" type="password" class="text" placeholder="*********" style="background-color:#CCC;" size="5" /><div class="texto" id="caja2"><a href="#"  ><strong>¿Olvidó su contraseña?</strong></a></div>
        <input name="passwordEnc" type="hidden" id="passwordEnc">
        <input type="submit" value="Entrar" class="submit" style="background-color:#06C;"  />
    </form>
</div>