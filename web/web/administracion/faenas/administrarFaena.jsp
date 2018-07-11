<%-- 
    Document   : administrarMaquina
    Created on : 28-04-2015, 10:40:51 PM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Faenas");    
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
    <h1>Administrar faenas</h1>

    <table id="tFaena" class="easyui-datagrid" style="width:1194px;height:460px"
           url="administrarFaena?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="numeroTeam" width="35">Numero Team</th>
                <th field="tipoFaena" width="50">Tipo Faena</th>
                <th field="nombreJefeFaena" width="50">Jefe Faena</th> 
                <th field="nombreCalibrador" width="50">Calibrador</th>
            </tr>                          
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarFaena()">Editar</a>   
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearFaena()">Crear Faena</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarFaena()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarFaena" id="inputBuscarFaena" placeholder="Buscar..." onkeyup="buscarFaena()">
    </div>


    <div id="dlg" class="easyui-dialog" style="width:410px;height:285px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarFaena' novalidate>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Numero Team:</label></div>
                <div class="cajaInput"><input type="text" name="numeroTeam" id="numeroTeam" class="easyui-validatebox input-formulario" style="width:200px;" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Tipo Faena:</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idTipoFaena" style="width:200px;" name="idTipoFaena" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Jefe Faena:</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idJefeFaena" style="width:200px;" name="idJefeFaena" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Calibrador:</label></div>
                <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idCalibrador" style="width:200px;" name="idCalibrador" maxlength="45" required></select></div>
            </div>
            <input name="accion" id="accion" type="hidden">
            <input name="idFaena" id="idFaena" type="hidden">
        </form>
    </div>  

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarFaena()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>  

</div>
<script type="text/javascript">
    $(document).ready(function() {
        cargarTiposFaenas();
        cargarEmpleados();
    });
    function crearFaena() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Faena');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarFaena() {
        var row = $('#tFaena').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Faena');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Faena.');
        }
    }
    function guardarFaena() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarFaena",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tFaena').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var numeroTeam = document.getElementById("numeroTeam").value;
        var idTipoFaena = document.getElementById("idTipoFaena").value;
        var idJefeFaena = document.getElementById("idJefeFaena").value;
        var idCalibrador = document.getElementById("idCalibrador").value;

        if (numeroTeam == null || numeroTeam.length == 0) {
            document.getElementById("numeroTeam").focus();
            return "Debe ingresar el Numero de Team";
        } else if (isNaN(numeroTeam)) {
            document.getElementById("numeroTeam").focus();
            return "El Numero de Team no es valido";
        } else if (idTipoFaena == 0) {
            document.getElementById("idTipoFaena").focus();
            return "Debe seleccionar el tipo de faena.";
        } else if (idJefeFaena == 0) {
            document.getElementById("idJefeFaena").focus();
            return "Debe seleccionar un Jefe de Faena.";
        } else if (idCalibrador == 0) {
            document.getElementById("idCalibrador").focus();
            return "Debe seleccionar un Calibrador.";
        }
        return "";
    }
    function eliminarFaena() {
        var row = $('#tFaena').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que   desea eliminar la faena?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarFaena",
                        type: "post",
                        data: "accion=BORRAR&faena=" + JSON.stringify(row),
                        success: function(data) {
                            var data = eval('(' + data + ')');
                            if (data.success) {
                                $('#tFaena').datagrid('reload');
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
    function buscarFaena() {
        var buscar = document.getElementById("inputBuscarFaena").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'administrarFaena?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tFaena').datagrid('loadData', datos);
                }
        );
    }

    function cargarTiposFaenas() {
        $("#idTipoFaena").empty();
        $.getJSON('/jjara/administrarFaena?accion=LISTADO_TIPO_FAENA',
                function(data) {
                    $("#idTipoFaena").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idTipoFaena").append("<option value=\'" + v.idTipoFaena + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }

    function cargarEmpleados() {
        $("#idJefeFaena").empty();
        $("#idCalibrador").empty();
        $.getJSON('/jjara/administrarEmpleado?accion=LISTAR_ALL_EMPLEADOS',
                function(data) {
                    $("#idJefeFaena").append("<option value=\'0\'>Seleccionar...</option>");
                    $("#idCalibrador").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idJefeFaena").append("<option value=\'" + v.idEmpleado + "\'>" + v.nombres + " " + v.apellidos + "</option>");
                        $("#idCalibrador").append("<option value=\'" + v.idEmpleado + "\'>" + v.nombres + " " + v.apellidos + "</option>");
                    });
                }
        );
    }

    //COMPLEMENTARIOS
    function getEmpleado(nEmpleado) {
        var rut;
        if (nEmpleado == 0) {
            rut = document.getElementById("rutJefeFaena").value;
        } else {
            rut = document.getElementById("rutCalibrador").value;
        }
        if (rut.length > 7) {
            var parm = "";
            if (rut != "") {
                parm = parm + "&q=" + rut;
                var url_json = 'administrarEmpleado?accion=EMPLEADO_BY_RUT&fila=1' + parm;
                $.getJSON(
                        url_json,
                        function(datos) {
                            if (nEmpleado == 0) {
                                document.getElementById("nombreJefeFaena").value = datos.nombres + " " + datos.apellidos;
                            } else {
                                document.getElementById("nombreCalibrador").value = datos.nombres + " " + datos.apellidos;
                            }
                        }
                );
            }
        }
    }
</script>