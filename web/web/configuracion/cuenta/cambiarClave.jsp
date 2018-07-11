<%-- 
    Document   : cambiarClave
    Created on : 06-05-2015, 02:45:55 AM
    Author     : Javier-PC
--%>
<script type="text/javascript"src="/jjara/files/js/validarut.js"></script>
<!-- DataGridView--> 
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="cambiarClave" style="padding-top: 150px; padding-left: 400px;">
    <form id="fm" method="post" action='administrarUsuario' novalidate>
        <div class="fitem">
            <div class="cajaLabel"><label class="label-formualrio">Clave Actual:</label></div>
            <div class="cajaInput"><input type="password" name="claveActual" id="claveActual" class="easyui-validatebox input-formulario" style="width:200px;"></div>
        </div>
        <div style="padding: 0 0 10px 30px;">La contraseña debe tener al menos 8 caracteres.</div>
        <div class="fitem">            
            <div class="cajaLabel"><label class="label-formualrio">Nueva Clave:</label></div>
            <div class="cajaInput"><input type="password" name="nuevaClave" id="nuevaClave" class="easyui-validatebox input-formulario" style="width:200px;"></div>
        </div>
        <div class="fitem">
            <div class="cajaLabel"><label class="label-formualrio">Repetir Clave:</label></div>
            <div class="cajaInput"><input type="password" name="repetirClave" id="repetirClave" class="easyui-validatebox input-formulario" style="width:200px;"></div>
        </div>
        <input name="accion" id="accion" type="hidden">
        <div class="cajaInput" style="padding-left: 160px; padding-top: 20px;"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="cambiarClave()">Cambiar Clave</a></div>
    </form>

</div>

<script type="text/javascript">
    function cambiarClave() {
        document.getElementById('accion').value = "CAMBIAR_CLAVE";
        var nueva = document.getElementById('nuevaClave').value;
        var repetir = document.getElementById('repetirClave').value;
        if (nueva == repetir) {
            if (nueva.length > 7){
                $.ajax({
                    url: "administrarUsuario",
                    type: "post",
                    data: $("#fm").serialize(),
                    success: function (data) {
                        console.log(data);
                        var data = eval('(' + data + ')');
                        if (data.success) {
                            document.getElementById("fm").reset();
                        }
                        $.messager.alert('Aviso', data.statusText);
                    }
                });
            }else{
                $.messager.alert('Alerta', "La contraseña debe tener al menos 8 caracteres.");
            }
        } else {
            $.messager.alert('Alerta', "Las contraseñas ingresadas son diferentes");
        }
    }
</script>