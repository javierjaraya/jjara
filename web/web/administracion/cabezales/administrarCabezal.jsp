<%-- 
    Document   : administrarMaquina
    Created on : 28-04-2015, 10:40:51 PM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Cabezal");    
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

    <h1>Administrar cabezales</h1>

    <table id="tCabezal" class="easyui-datagrid" style="width:1194px;height:460px"
           url="administrarCabezal?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="idCabezal" width="15">ID Cabezal</th>
                <th field="patente" width="20">Patente</th>
                <th field="modelo" width="20">Modelo</th> 
                <th field="numeroChasis" width="20">Nº Chasis</th>
                <th field="año" width="13">Año</th>
                <th field="horometro" width="20">Horometro</th>
                <th field="fechaRegistro" width="20">Fecha Registro</th>
                <th field="descripcionEstado" width="10">Estado</th>
            </tr>                          
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarCabezal()">Editar</a>  
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearCabezal()">Crear Cabezal</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarCabezal()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarCabezal" id="inputBuscarCabezal" placeholder="Buscar..." onkeyup="buscarCabezal()">
    </div>


    <div id="dlg" class="easyui-dialog" style="width:410px;height:385px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarCabezal' novalidate>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Patente:</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="patente" style="width:200px;" name="patente" maxlength="45" title="Se necesita un nombre" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Modelo:</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="modelo" style="width:200px;" name="modelo" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Numero Chasis</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="numeroChasis" style="width:200px;" name="numeroChasis" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Año</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="año" style="width:200px;" name="año" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Horometro</label></div>
                <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="0" id="horometro" style="width:200px;" name="horometro" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="estado" style="width:200px;" name="estado" maxlength="45" required>
                        <option value='-1'>Seleccionar...</option>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                    </select></div>
            </div>
            <input name="accion" id="accion" type="hidden">
            <input name="idCabezal" id="idCabezal" type="hidden">
        </form>
    </div>  

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarCabezal()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>  

</div>
<script type="text/javascript">
    function crearCabezal() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Cabezal');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarCabezal() {
        var row = $('#tCabezal').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Cabezal');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar un Cabezal.');
        }
    }
    function guardarCabezal() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarCabezal",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tCabezal').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var patente = document.getElementById("patente").value;
        var modelo = document.getElementById("modelo").value;
        var numeroChasis = document.getElementById("numeroChasis").value;
        var año = document.getElementById("año").value;
        var horometro = document.getElementById("horometro").value;
        var estado = document.getElementById("estado").value;

        if (patente == null || patente.length == 0) {
            document.getElementById("patente").focus();
            return "Debe ingresar la patente";
        } else if (modelo == null || modelo.length == 0) {
            document.getElementById("modelo").focus();
            return "Debe ingresar el modelo";
        } else if (numeroChasis == null || numeroChasis.length == 0) {
            document.getElementById("numeroChasis").focus();
            return "Debe ingresar el número de chasis";
        } else if (año == null || año.length != 4) {
            document.getElementById("año").focus();
            return "Debe ingresar el año";
        } else if (isNaN(año)) {
            document.getElementById("año").focus();
            return "El año ingresado no es valido";
        } else if (año < 1800) {
            document.getElementById("año").focus();
            return "El año ingresado no es valido";
        } else if (horometro == null) {
            document.getElementById("horometro").focus();
            return "Debe ingresar el horómetro";
        } else if (isNaN(horometro)) {
            document.getElementById("horometro").focus();
            return "El horómetro ingresado no es valido";
        } else if(horometro < 0){
            document.getElementById("horometro").focus();
            return "El horómetro ingresado no es valido";
        } else if(estado == -1){
            document.getElementById("estado").focus();
            return "Debe seleccionar un estado.";
        }
        return "";
    }
    function eliminarCabezal() {
        var row = $('#tCabezal').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que desea eliminar el cabezal?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarCabezal",
                        type: "post",
                        data: "accion=BORRAR&cabezal=" + JSON.stringify(row),
                        success: function(data) {
                            var data = eval('(' + data + ')');
                            if (data.success) {
                                $('#tCabezal').datagrid('reload');
                            }
                            $.messager.alert('Aviso', data.statusText);
                        }
                    });
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Maquina.');
        }
    }
    function buscarCabezal() {
        var buscar = document.getElementById("inputBuscarCabezal").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'administrarCabezal?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tCabezal').datagrid('loadData', datos);
                }
        );
    }
</script>