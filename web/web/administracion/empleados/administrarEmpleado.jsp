<%-- 
    Document   : administrarEmpleado
    Created on : 28-04-2015, 10:40:51 PM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Empleados");    
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
    <h1>Administrar empleados</h1>

    <table id="tEmpleado" class="easyui-datagrid" style="width:1194px;height:460px"
           url="administrarEmpleado?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="rut" width="15">Rut</th>
                <th field="nombres" width="20">Nombres</th>
                <th field="apellidos" width="20">Apellidos</th> 
                <th field="direccion" width="20">Direccion</th>
                <th field="telefono" width="13">Telefono</th>
                <th field="correo" width="20">Correo</th>
                <th field="cargo" width="20">Cargo</th>
                <th field="fechaRegistro" width="17">Fecha Registro</th>
                <th field="estadoDescripcion" width="10">Estado</th>
            </tr>                          
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarEmpleado()">Editar</a>   
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearEmpleado()">Crear Empleado</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarEmpleado()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarEmpleado" id="inputBuscarEmpleado" placeholder="Buscar..." onkeyup="buscarEmpleado()">
    </div>


    <div id="dlg" class="easyui-dialog" style="width:410px;height:385px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarEmpleado' novalidate>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Rut:</label></div>
                <div class="cajaInput"><input type="text" name="rut" id="rut" class="easyui-validatebox input-formulario" style="width:200px;" title="Se necesita el rut" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Nombres:</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="nombres" style="width:200px;" name="nombres" maxlength="45" title="Se necesita un nombre" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Apellidos:</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="apellidos" style="width:200px;" name="apellidos" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Direccion:</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="direccion" style="width:200px;" name="direccion" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Telefono</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="telefono" style="width:200px;" name="telefono" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Correo:</label></div>
                <div class="cajaInput"><input type="email" class="easyui-validatebox input-formulario" value="" id="correo" style="width:200px;" name="correo" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Cargo</label></div>                
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idCargo" style="width:200px;" name="idCargo" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="estado" style="width:200px;" name="estado" maxlength="45" required>
                        <option value='-1'>Seleccionar...</option>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                    </select></div>
            </div>
            <input name="idEmpleado" id="idEmpleado" type="hidden">
            <input name="accion" id="accion" type="hidden">
        </form>
    </div>  

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarEmpleado()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>  

</div>
<script type="text/javascript">
    $(document).ready(function() {
        cargarCargos();
    });
    function crearEmpleado() {
        document.getElementById("fm").reset();
        document.getElementById("rut").disabled=false;// Así activamos
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Empleado');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarEmpleado() {
        var row = $('#tEmpleado').datagrid('getSelected');
        if (row) {
            document.getElementById("rut").disabled=true;// Así desactivamos
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Empleado');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Empleado.');
        }
    }
    function guardarEmpleado() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarEmpleado",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    console.log(data);
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tEmpleado').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var run = document.getElementById("rut").value;
        var nombres = document.getElementById("nombres").value;
        var apellidos = document.getElementById("apellidos").value;
        var direccion = document.getElementById("direccion").value;
        var telefono = document.getElementById("telefono").value;
        var correo = document.getElementById("correo").value;
        var idCargo = document.getElementById("idCargo").value;
        var estado = document.getElementById("estado").value;
        
        if (!Rut(run)) {
            document.getElementById("rut").focus();
            return "El rut ingresado no es valido";
        } else if (nombres == null || nombres.length == 0) {
            document.getElementById("nombres").focus();
            return "Debe ingresar los nombres";
        } else if (apellidos == null || apellidos.length == 0) {
            document.getElementById("apellidos").focus();
            return "Debe ingresar los apellidos";
        } else if (direccion == null || direccion.length == 0) {
            document.getElementById("direccion").focus();
            return "Debe ingresar la direccion";
        } else if (telefono == null || telefono.length < 6 || telefono.length > 10) {
            document.getElementById("telefono").focus();
            return "Debe ingresar un telefono valido";
        } else if (isNaN(telefono)){
            document.getElementById("telefono").focus();
            return "El telefono ingresado tiene caracteres no validos";
        }else if (correo == null || correo.length == 0) {
            document.getElementById("correo").focus();
            return "Debe ingresar un correo valido";
        }else if (idCargo == 0) {
            document.getElementById("idCargo").focus();
            return "Debe seleccionar el cargo.";
        }else if (estado == -1) {
            document.getElementById("estado").focus();
            return "Debe seleccionar el estado.";
        }
        return "";
    }
    function eliminarEmpleado() {
        var row = $('#tEmpleado').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que desea eliminar el empleado?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarEmpleado",
                        type: "post",
                        data: "accion=BORRAR&empleado=" + JSON.stringify(row),
                        success: function(data) {
                            $('#tEmpleado').datagrid('reload');// reload the user data
                            $.messager.alert('Exito', 'Empleado eliminado correctamente.');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Empleado.');
        }
    }
    function buscarEmpleado() {
        var buscar = document.getElementById("inputBuscarEmpleado").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'administrarEmpleado?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tEmpleado').datagrid('loadData', datos);
                }
        );
    }
    function cargarCargos() {
        $("#idCargo").empty();
        $.getJSON('/jjara/administrarEmpleado?accion=LISTAR_CARGOS',
                function(data) {
                    $.each(data, function(k, v) {
                        $("#idCargo").append("<option value=\'" + v.idCargo + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }
</script>