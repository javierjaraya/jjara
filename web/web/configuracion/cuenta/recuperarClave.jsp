<%-- 
    Document   : recuperarClave
    Created on : 26-05-2015, 13:59:58
    Author     : Javier J
--%>


<div id="cajainicio"><img src="/jjara/files/img/inicio.png" width="267" height="40" /></div>
<div id="caja"></div>
<form id="fmrecuperar" action="administrarRecuperarClave" method="post">
    <div id="caja1-recuperar">Correo Electronico: </div>
    <input type="text" name="email" id="email" placeholder="Correo electronico" class="text" style="margin-left: 80px;background-color:#CCC;" />
    <input type="button" onclick="recuperarClave()" value="Recuperar" class="submit" style="margin-right: 150px; margin-top: 40px;background-color:#06C;"  />
</form>
