<%-- 
    Document   : administrarMaquina
    Created on : 28-04-2015, 10:40:51 PM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Perfil");    
%>
<script type="text/javascript"src="/jjara/files/js/validarut.js"></script>
<!-- DataGridView--> 
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>

<div id="contenidoSubMenu">
    <h1>Administrar perfiles</h1>

    <table id="tPerfil" class="easyui-datagrid" style="width:1194px;height:460px"
           url="administrarPerfil?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="idPerfil" width="15">ID Perfil</th>
                <th field="nombre" width="20">Nombre Perfil</th>
            </tr>                          
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarPerfil()">Editar</a>
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearPerfil()">Crear Perfil</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarPerfil()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarPerfil" id="inputBuscarPerfil" placeholder="Buscar..." onkeyup="buscarPerfil()">
    </div>


    <div id="dlg" class="easyui-dialog" style="width:410px;height:385px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarPerfil' novalidate>
            <div class="fitem">
                <label class="label-formualrio">Nombre:</label>
                <input type="text" name="nombre" id="nombre" class="easyui-validatebox input-formulario" style="width:200px;" required>
            </div><hr>
            <div class="fitem">
                <div id="permisosPerfil">
                    <!-- AQUI ESTA EL LISTADO DE PERMISOS-->
                </div>
            </div>
            <input name="accion" id="accion" type="hidden">
            <input name="idPerfil" id="idPerfil" type="hidden">

        </form>
    </div>  

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarPerfil()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>  
</div>
<script type="text/javascript">
    function crearPerfil() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Perfil');
        document.getElementById('accion').value = "AGREGAR";
        obtenerHTMLPerfilMenu();
    }
    function editarPerfil() {
        var row = $('#tPerfil').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Perfil');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
            obtenerHTMLPerfilMenuByIDPerfil();
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Perfil.');
        }
    }
    function guardarPerfil() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarPerfil",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    console.log(data);
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tPerfil').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var nombre = document.getElementById("nombre").value;
        if (nombre.length == 0) {
            document.getElementById("nombre").focus();
            return "Debe ingresar el nombre del perfil";
        }
        return "";
    }

    function eliminarPerfil() {
        var row = $('#tPerfil').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que   desea eliminar el perfil?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarPerfil",
                        type: "post",
                        data: "accion=BORRAR&perfil=" + JSON.stringify(row),
                        success: function(data) {
                            var data = eval('(' + data + ')');
                            if (data.success) {
                                $('#tPerfil').datagrid('reload');
                            }
                            $.messager.alert('Aviso', data.statusText);
                        }
                    });
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Faena.');
        }
    }
    function buscarPerfil() {
        var buscar = document.getElementById("inputBuscarPerfil").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'administrarPerfil?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tPerfil').datagrid('loadData', datos);
                }
        );
    }

    function obtenerHTMLPerfilMenu() {
        var url_json = 'administrarPerfil?accion=HTML_PERMISOS';
        $.getJSON(
                url_json,
                function(data) {
                    $('#permisosPerfil').html(data.statusText);
                }
        );

    }

    function obtenerHTMLPerfilMenuByIDPerfil() {
        var idPerfil = document.getElementById('idPerfil').value;
        var url_json = 'administrarPerfil?accion=HTML_PERMISOS_PERFIL&idPerfil=' + idPerfil;
        $.getJSON(
                url_json,
                function(data) {
                    $('#permisosPerfil').html(data.statusText);
                }
        );

    }



</script>