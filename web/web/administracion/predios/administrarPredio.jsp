<%-- 
    Document   : administrarEmpleado
    Created on : 28-04-2015, 10:40:51 PM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Predio");    
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

    <h1>Administrar predios</h1>

    <table id="tPredio" class="easyui-datagrid" style="width:1194px;height:460px"
           url="administrarPredio?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="true" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="idPredio" width="20">ID Predio</th>
                <th field="nombre" width="15">Nombre</th>
                <th field="area" width="20">Area</th>
                <th field="zona" width="20">Zona</th> 
                <th field="superficie" width="20">Superficie</th>
                <th field="descripcionEstado" width="20">Estado</th>
            </tr>                          
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarPredio()">Editar</a>  
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearPredio()">Crear Predio</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarPredio()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarPredio" id="inputBuscarPredio" placeholder="Buscar..." onkeyup="buscarPredio()">
    </div>


    <div id="dlg" class="easyui-dialog" style="width:410px;height:300px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarPredio' novalidate>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Nombre</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="nombre" style="width:200px;" name="nombre" maxlength="45" title="Se necesita un nombre" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Area</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idArea" style="width:200px;" name="idArea" maxlength="45" required>                
                    </select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Zona</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idZona" style="width:200px;" name="idZona" maxlength="45" required>                
                    </select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Superficie</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="superficie" style="width:200px;" name="superficie" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="estado" style="width:200px;" name="estado" maxlength="45" required>
                        <option value='-1'>Seleccionar...</option>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                    </select></div>
            </div>
            <input name="idPredio" id="idPredio" type="hidden">
            <input name="accion" id="accion" type="hidden">
        </form>
    </div>  

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarPredio()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>  

</div>

<script type="text/javascript">
    $(document).ready(function() {
        cargarZonas();
        cargarAreas();
    });
    function crearPredio() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Predio');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarPredio() {
        var row = $('#tPredio').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Predio');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Predio.');
        }
    }
    function guardarPredio() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarPredio",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    console.log(data);
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tPredio').datagrid('reload');
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
        var superficie = document.getElementById("superficie").value;
        var idArea = document.getElementById("idArea").value;
        var idZona = document.getElementById("idZona").value;
        var estado = document.getElementById("estado").value;
        
        if (nombre == null || nombre.length == 0) {
            document.getElementById("nombre").focus();
            return "Debe ingresar el nombre del predio";
        } else if (superficie == null || superficie.length == 0) {
            document.getElementById("superficie").focus();
            return "Debe ingresar la superficie";
        } else if (isNaN(superficie)) {
            document.getElementById("superficie").focus();
            return "La superficie ingresada no es valida.";
        }else if (idArea == 0) {
            document.getElementById("idArea").focus();
            return "Debe seleccionar el area.";
        }else if (idZona == 0) {
            document.getElementById("idZona").focus();
            return "Debe seleccionar la zona.";
        }else if (estado == -1) {
            document.getElementById("estado").focus();
            return "Deve seleccionar un estado.";
        }
        return "";
    }
    function eliminarPredio() {
        var row = $('#tPredio').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que desea eliminar el predio?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarPredio",
                        type: "post",
                        data: "accion=BORRAR&predio=" + JSON.stringify(row),
                        success: function(data) {
                            $('#tPredio').datagrid('reload');// reload the user data
                            $.messager.alert('Exito', 'Predio eliminado correctamente.');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Predio.');
        }
    }
    function buscarPredio() {
        var buscar = document.getElementById("inputBuscarPredio").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'administrarPredio?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tPredio').datagrid('loadData', datos);
                }
        );
    }

    function cargarAreas() {
        $("#idArea").empty();
        $.getJSON('/jjara/administrarPredio?accion=LISTAR_AREAS',
                function(data) {
                    $("#idArea").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idArea").append("<option value=\'" + v.idArea + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }

    function cargarZonas() {
        $("#idZona").empty();
        $.getJSON('/jjara/administrarPredio?accion=LISTAR_ZONAS',
                function(data) {
                    $("#idZona").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idZona").append("<option value=\'" + v.idZona + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }
</script>