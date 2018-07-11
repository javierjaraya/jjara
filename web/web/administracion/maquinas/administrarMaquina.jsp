<%-- 
    Document   : administrarMaquina
    Created on : 28-04-2015, 10:40:51 PM
    Author     : Javier-PC
--%>

<%@page import="java.util.HashMap"%>
<%@page import="cl.forestcenter.sistema.dto.MenuDTO"%>
<%
    HashMap<String,MenuDTO> permisosMenu = (HashMap<String,MenuDTO>) request.getSession().getAttribute ("permisos");
    MenuDTO menu = permisosMenu.get("Maquinas");    
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

    <h1>Administrar maquinas</h1>

    <table id="tMaquina" class="easyui-datagrid" style="width:1194px;height:460px"
           url="administrarMaquina?accion=LISTADO&fila=1"
           toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
           rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="codigoMaquina" width="20">Codigo Máquina</th>
                <th field="codigoForestal" width="20">Codigo Forestal</th>
                <th field="patente" width="15">Patente</th>
                <th field="modelo" width="20">Modelo</th> 
                <th field="numeroChasis" width="20">Nº Chasis</th>
                <th field="numeroMotor" width="20">Nº Motor</th>
                <th field="año" width="10">Año</th>
                <th field="horometro" width="13">Horometro</th>
                <th field="tipoMaquina" width="17">Tipo Máquina</th>
                <th field="cabezal" width="20">Cabezal</th>
                <th field="fechaRegistro" width="20">Fecha Registro</th>
                <th field="descripcionEstado" width="10">Estado</th>
            </tr>                          
        </thead>
    </table>
    <div id="toolbar">
        <% if(menu.isModificar()){ %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarMaquina()">Editar</a>   
        <% }
           if(menu.isCrear()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearMaquina()">Crear Máquina</a>
        <% }
           if(menu.isEliminar()){
        %>
        <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarMaquina()">Eliminar</a>
        <% } %>
        <input type="text" value="" name="inputBuscarMaquina" id="inputBuscarMaquina" placeholder="Buscar..." onkeyup="buscarMaquina()">
    </div>


    <div id="dlg" class="easyui-dialog" style="width:450px;height:455px;padding:10px 20px;"
         closed="true" buttons="#dlg-buttons" modal="true">
        <div class="ftitle titulo-forlumario">Detalle</div><hr>
        <form id="fm" method="post" action='administrarMaquina' novalidate>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Código Maquina:</label></div>
                <div class="cajaInput"><input type="text" name="codigoMaquina" id="codigoMaquina" class="easyui-validatebox input-formulario" style="width:200px;" title="Se necesita el codigo de la maquina" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Código Forestal:</label></div>
                <div class="cajaInput"><input type="text" name="codigoForestal" id="codigoForestal" class="easyui-validatebox input-formulario" style="width:200px;" title="Se necesita el codigo de la maquina" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Patente:</label></div>
                <div class="cajaLabel"><input type="text" class="easyui-validatebox input-formulario" value="" id="patente" style="width:200px;" name="patente" maxlength="45" title="Se necesita un nombre" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Modelo:</label></div>
                <div class="cajaLabel"><input type="text" class="easyui-validatebox input-formulario" value="" id="modelo" style="width:200px;" name="modelo" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Número Chasis</label></div>
                <div class="cajaLabel"><input type="text" class="easyui-validatebox input-formulario" value="" id="numeroChasis" style="width:200px;" name="numeroChasis" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Número Motor</label></div>
                <div class="cajaLabel"><input type="text" class="easyui-validatebox input-formulario" value="" id="numeroMotor" style="width:200px;" name="numeroMotor" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Año</label></div>
                <div class="cajaLabel"><input type="text" class="easyui-validatebox input-formulario" value="" id="año" style="width:200px;" name="año" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Horómetro</label></div>
                <div class="cajaLabel"><input type="text" class="easyui-validatebox input-formulario" value="0" id="horometro" style="width:200px;" name="horometro" maxlength="45" required></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Tipo Máquina</label></div>
                <div class="cajaLabel"><select class="easyui-validatebox input-formulario" value="" id="idTipoMaquina" style="width:200px;" name="idTipoMaquina" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Cabezal</label></div>
                <div class="cajaLabel"><select class="easyui-validatebox input-formulario" value="" id="idCabezal" style="width:200px;" name="idCabezal" maxlength="45" required></select></div>
            </div>
            <div class="fitem">
                <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                <div class="cajaLabel"><select class="easyui-validatebox input-formulario" value="" id="estado" style="width:200px;" name="estado" maxlength="45" required>
                        <option value='-1'>Seleccionar...</option>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                    </select></div>
            </div>
            <input name="accion" id="accion" type="hidden">
            <input name="idMaquina" id="accion" type="hidden">
        </form>
    </div>      

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarMaquina()">Guardar</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
    </div>  
</div>
<script type="text/javascript">
    $(document).ready(function() {
        cargarTipoMaquina();
        cargarCabezales();
    });
    function crearMaquina() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Maquina');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarMaquina() {
        var row = $('#tMaquina').datagrid('getSelected');
        if (row) {
            //document.getElementById("codigoMaquina").disabled = true;// Así desactivamos
            //document.getElementById("codigoForestal").disabled = true;// Así desactivamos
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Maquina');
            $('#fm').form('load', row);
            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Maquina.');
        }
    }
    function guardarMaquina() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "administrarMaquina",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    console.log(data);
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tMaquina').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var codigoMaquina = document.getElementById("codigoMaquina").value;
        var codigoForestal = document.getElementById("codigoForestal").value;
        var patente = document.getElementById("patente").value;
        var modelo = document.getElementById("modelo").value;
        var numeroChasis = document.getElementById("numeroChasis").value;
        var numeroMotor = document.getElementById("numeroMotor").value;
        var horometro = document.getElementById("horometro").value;
        var año = document.getElementById("año").value;
        var idTipoMaquina = document.getElementById("idTipoMaquina").value;
        var idCabezal = document.getElementById("idCabezal").value;
        var estado = document.getElementById("estado").value;

        if (codigoMaquina == null || codigoMaquina.length == 0) {
            document.getElementById("codigoMaquina").focus();
            return "Debe ingresar el código máquina";
        } else if (idTipoMaquina == 2 && (codigoForestal == null || codigoForestal.length == 0)) {
            document.getElementById("codigoForestal").focus();
            return "Debe ingresar el código forestal";
        } else if (patente == null || patente.length == 0) {
            document.getElementById("patente").focus();
            return "Debe ingresar la patente";
        } else if (modelo == null || modelo.length == 0) {
            document.getElementById("modelo").focus();
            return "Debe ingresar el modelo";
        } else if (numeroChasis == null || numeroChasis.length == 0) {
            document.getElementById("numeroChasis").focus();
            return "Debe ingresar el número de chasis";
        } else if (numeroMotor == null || numeroMotor.length == 0) {
            document.getElementById("numeroMotor").focus();
            return "Debe ingresar el número de motor";
        } else if (horometro == null || horometro.length == 0) {
            document.getElementById("horometro").focus();
            return "Debe ingresar el horómetro";
        } else if (isNaN(horometro)) {
            document.getElementById("horometro").focus();
            return "El horómetro ingresado no es valido";
        } else if (horometro < 0) {
            document.getElementById("horometro").focus();
            return "El horómetro ingresado no es valido";
        } else if (año == null || año.length != 4) {
            document.getElementById("año").focus();
            return "Debe ingresar el año";
        } else if(idTipoMaquina)
        if (isNaN(año)) {
            document.getElementById("año").focus();
            return "El año ingresado tiene caracteres no es validos";
        } else if (año < 1800) {
            document.getElementById("año").focus();
            return "El año ingresado no es valido";
        } else if(idTipoMaquina == 0){
            document.getElementById("idTipoMaquina").focus();
            return "Debe seleccionar el tipo de máquina.";
        } else if(idTipoMaquina == 1 && idCabezal == 0){
            document.getElementById("idCabezal").focus();
            return "Debe seleccionar un cabezal.";
        } else if(estado == -1){
            document.getElementById("estado").focus();
            return "Debe seleccionar el estado.";
        }
        return "";
    }
    function eliminarMaquina() {
        var row = $('#tMaquina').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que desea eliminar la maquina?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "administrarMaquina",
                        type: "post",
                        data: "accion=BORRAR&maquina=" + JSON.stringify(row),
                        success: function(data) {
                            var data = eval('(' + data + ')');
                            if (data.success) {
                                $('#tMaquina').datagrid('reload');
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
    function buscarMaquina() {
        var buscar = document.getElementById("inputBuscarMaquina").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'administrarMaquina?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tMaquina').datagrid('loadData', datos);
                }
        );
    }

    function cargarTipoMaquina() {
        $("#idTipoMaquina").empty();
        $.getJSON('/jjara/administrarMaquina?accion=LISTAR_TIPO_MAQUINA',
                function(data) {
                    $("#idTipoMaquina").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idTipoMaquina").append("<option value=\'" + v.idTipoMaquina + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }

    function cargarCabezales() {
        $("#idCabezal").empty();
        $.getJSON('/jjara/administrarCabezal?accion=LISTAR_ALL_CABEZALES',
                function(data) {
                    $("#idCabezal").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function(k, v) {
                        $("#idCabezal").append("<option value=\'" + v.idCabezal + "\'>" + v.patente + " - " + v.modelo + "</option>");
                    });
                }
        );
    }
</script>