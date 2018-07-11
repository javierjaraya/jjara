<%-- 
    Document   : administrarUsuario
    Created on : 24-04-2015, 01:56:55 AM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Usuario");    
%>
<script type="text/javascript"src="/jjara/files/js/validarut.js"></script>
<!-- DataGridView--> 
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>

<div id="contenidoSubMenu">
    <h1>Administrar usuarios</h1>

    <table id="tUsuario" class="easyui-datagrid" style="width:1194px;height:460px"
           url="/jjara/administrarUsuario?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="rut" width="20">Rut</th>
                <th field="nombreEmpleado" width="40">Nombre Empleado</th>
                <th field="nombreUsuario" width="20">Nombre Usuario</th>
                <th field="password" width="10">Password</th>
                <th field="estadoDescripcion" width="10">Estado</th> 
                <th field="nombrePerfil" width="20">Perfil</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarUsuario()">Editar</a>    
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearUsuario()">Crear Usuario</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarUsuario()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarUsuario" id="inputBuscarUsuario" placeholder="Buscar..." onkeyup="buscarUsuario()">
    </div>

    <div id="dlg" class="easyui-dialog" style="width:460px;height:350px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarUsuario' novalidate>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Empleado</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idEmpleado" style="width:200px;" name="idEmpleado" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Nombre Usuario</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="nombreUsuario" style="width:200px;" name="nombreUsuario" maxlength="45" required></div>
            </div>
            <div style="padding: 35px 0 10px 30px;">La contraseña debe tener al menos 8 caracteres.</div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Contraseña</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="password" style="width:200px;" name="password" maxlength="16" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Perfil</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idPerfil" style="width:200px;" name="idPerfil" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="estado" style="width:200px;" name="estado" maxlength="45" required>
                        <option value='-1'>Seleccionar...</option>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                    </select></div>
            </div>
            <input name="idUsuario" id="idUsuario" type="hidden">
            <input name="accion" id="accion" type="hidden">
        </form>
    </div>  

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarUsuario()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>

</div>
<script type="text/javascript">
    $(document).ready(function() {
        cargarPerfiles();
        cargarEmpleados();
    });
    function crearUsuario() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Usuario');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarUsuario() {
        var row = $('#tUsuario').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Usuario');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Usuario.');
        }
    }
    function guardarUsuario() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarUsuario",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    console.log(data);
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tUsuario').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var nombreUsuario = document.getElementById("nombreUsuario").value;
        var password = document.getElementById("password").value;
        var idPerfil = document.getElementById("idPerfil").value;
        var idEmpleado = document.getElementById("idEmpleado").value;
        var estado = document.getElementById("estado").value;
        
        if (nombreUsuario == null || nombreUsuario.length == 0) {
            document.getElementById("nombreUsuario").focus();
            return "Debe ingresar el nombre de usuario";
        } else if (password == null || password.length == 0) {
            document.getElementById("password").focus();
            return "Debe ingresar una contraseña";
        } else if (password.length < 8) {
            document.getElementById("password").focus();
            return "La contraseña debe tener al menos 8 caracteres";
        } else if (idPerfil == 0) {
            document.getElementById("idPerfil").focus();
            return "Debe seleccionar el perfil.";
        } else if (idEmpleado == 0) {
            document.getElementById("idEmpleado").focus();
            return "Debe seleccionar un empleado.";
        } else if (estado == -1) {
            document.getElementById("estado").focus();
            return "Debe seleccionar el estado.";
        }
        return "";
    }
    function eliminarUsuario() {
        var row = $('#tUsuario').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que desea eliminar al usuario?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarUsuario",
                        type: "post",
                        data: "accion=BORRAR&usuario=" + JSON.stringify(row),
                        success: function(data) {
                            var data = eval('(' + data + ')');
                            if (data.success) {
                                $('#tUsuario').datagrid('reload');
                            }
                            $.messager.alert('Aviso', data.statusText)
                        }
                    });
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Usuario.');
        }
    }

    function buscarUsuario() {
        var buscar = document.getElementById("inputBuscarUsuario").value;
        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }
        var url_json = 'administrarUsuario?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tUsuario').datagrid('loadData', datos);
                }
        );
    }
    function cargarPerfiles() {
        $("#idPerfil").empty();
        $.getJSON('/jjara/administrarPerfil?accion=LISTADO',
                function(data) {
                    $("#idPerfil").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idPerfil").append("<option value=\'" + v.idPerfil + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }

    function cargarEmpleados() {
        $("#idEmpleado").empty();
        $.getJSON('/jjara/administrarEmpleado?accion=LISTAR_ALL_EMPLEADOS',
                function(data) {
                    $("#idEmpleado").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idEmpleado").append("<option value=\'" + v.idEmpleado + "\'>" + v.nombres + " " + v.apellidos + "</option>");
                    });
                }
        );
    }
    //COMPLEMENTARIOS
    function getEmpleado() {
        var rut = document.getElementById("rut").value;
        var parm = "";
        if (rut != "") {
            parm = parm + "&q=" + rut;
            var url_json = 'administrarEmpleado?accion=EMPLEADO_BY_RUT&fila=1' + parm;
            $.getJSON(
                    url_json,
                    function(datos) {
                        document.getElementById("nombreCompleto").value = datos.nombres + " " + datos.apellidos;
                        document.getElementById("nombreUsuario").value = datos.nombres.substring(0, 1) + datos.apellidos.substring(0, 5);
                        document.getElementById("password").value = 12345;
                    }
            );
        }
    }
</script>