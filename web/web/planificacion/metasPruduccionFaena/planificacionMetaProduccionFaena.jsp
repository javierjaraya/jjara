<%-- 
    Document   : administrarMaquina
    Created on : 28-04-2015, 10:40:51 PM
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

<div id="contenidoSubMenu">
    <div id="columna1">
        <h1>Meta Producción Faena</h1>

        <table id="tPlanificacion" class="easyui-datagrid" style="width:590px;height:460px"
               url="planificacionMetaProduccionFaena?accion=LISTADO&fila=1"
               toolbar="#toolbar" pagination="false" loadMsg="Procesando...espere..."
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <th field="idFaena" width="13">ID Faena</th>
                    <th field="descripcionFaena" width="21">Descripcion Faena</th>
                    <th field="fechaInicio" width="20">Fecha Inicio</th> 
                    <th field="fechaTermino" width="20">Fecha Termino</th>
                    <th field="descripcionEstado" width="10">Estado</th>
                </tr>                          
            </thead>
        </table>
        <div id="toolbar">
            <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarPlanificacion()">Editar</a>    
            <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearPlanificacion()">Crear Planificacion</a>
            <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarPlanificacion()">Eliminar</a>
            <input type="text" value="" name="inputBuscarPlanificacion" id="inputBuscarPlanificacion" placeholder="Buscar..." onkeyup="buscarPlanificacion()">
            <a href="#"  class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="verDetalle()">Ver Detalle</a>
        </div>

        <div id="dlg" class="easyui-dialog" style="width:410px;height:305px;padding:10px 20px;"
             closed="true" buttons="#dlg-buttons" modal="true">
            <div class="ftitle titulo-forlumario">Detalle</div><hr>
            <form id="fm" method="post" action='planificacionMetaProduccionFaena' novalidate>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Faena:</label></div>
                    <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idFaena" style=" width:200px;" name="idFaena" maxlength="45" required>
                        </select></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Fecha Inicio:</label></div>
                    <div class="cajaInput"><input type="date" name="fechaInicio" id="fechaInicio" class="easyui-validatebox input-formulario" style="width:200px;" required></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Fecha Termino:</label></div>
                    <div class="cajaInput"><input type="date" class="easyui-validatebox input-formulario" value="" id="fechaTermino" style="width:200px;" name="fechaTermino" maxlength="45" required></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                    <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="estado" style="width:200px;" name="estado" maxlength="45" required>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                        </select></div>
                </div>
                <input name="accion" id="accion" type="hidden">
                <input name="idPlanificacion" id="idPlanificacion" type="hidden">
            </form>
        </div>  

        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarPlanificacion()">Guardar</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
        </div>  
    </div>
    <div id="columna2">
        <h1>Detalle Planificación</h1>

        <table id="tDetallePlanificacion" class="easyui-datagrid" style="width:590px;height:460px;"
               url=""
               toolbar="#toolbarDetalle" pagination="false" loadMsg="Procesando...espere..."
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>           
                    <th field="fechaInicioDetalle" width="20">Fecha Inicio</th> 
                    <th field="fechaTerminoDetalle" width="20">Fecha Termino</th>
                    <th field="nombrePredio" width="21">Predio</th>
                    <th field="tipoArbol" width="21">Tipo Arbol</th>
                    <th field="metrosCubicos" width="21">Metros Cubicos</th>
                    <th field="descripcionEstado" width="15">Estado</th>
                </tr>                          
            </thead>
        </table>
        <div id="toolbarDetalle">
            <a href="#"  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editarDetalle()">Editar</a>    
            <a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="crearDetalle()">Crear detalle</a>
            <a href="#"  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="eliminarDetalle()">Eliminar</a>
        </div>

        <div id="dlgDetalle" class="easyui-dialog" style="width:410px;height:385px;padding:10px 20px;"
             closed="true" buttons="#dlg-buttons-Detalle" modal="true">
            <div class="ftitle titulo-forlumario">Detalle</div><hr>
            <form id="fmDetalle" method="post" action='planificacionMetaProduccionFaena' novalidate> 
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Número Acta</label></div>
                    <div class="cajaInput"><input type="text" name="numeroActa" id="numeroActa" class="easyui-validatebox input-formulario" style="width:200px;" required></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Fecha Inicio</label></div>
                    <div class="cajaInput"><input type="date" name="fechaInicioDetalle" id="fechaInicioDetalle" class="easyui-validatebox input-formulario" style="width:200px;" required></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Fecha Termino</label></div>
                    <div class="cajaInput"><input type="date" name="fechaTerminoDetalle" id="fechaTerminoDetalle" class="easyui-validatebox input-formulario" value="" style="width:200px;"  maxlength="45" required></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Predio</label></div>
                    <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idPredio" style="width:200px;" name="idPredio" maxlength="45" required></select></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Tipo Arbol</label></div>
                    <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="idTipoArbol" style="width:200px;" name="idTipoArbol" maxlength="45" required></select></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Metros Cubicos</label></div>
                    <div class="cajaInput"><input type="text" class="easyui-validatebox input-formulario" value="" id="metrosCubicos" style="width:200px;" name="metrosCubicos" maxlength="45" required></div>
                </div>
                <div class="fitem">
                    <div class="cajaLabel"><label class="label-formualrio">Estado</label></div>
                    <div class="cajaInput"><select class="easyui-validatebox input-formulario" value="" id="estadoDetalle" style="width:200px;" name="estadoDetalle" maxlength="45" required>
                        <option value='0'>Inactivo</option>
                        <option value='1'>Activo</option> 
                    </select></div>
                </div>
                <input name="accionDetalle" id="accionDetalle" type="hidden">
                <input name="idDetallePlanificacion" id="idDetallePlanificacion" type="hidden">
                <input name="idPlanificacionFaena" id="idPlanificacionFaena" type="hidden">
            </form>
        </div>  

        <div id="dlg-buttons-Detalle">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="guardarDetalle()">Guardar</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgDetalle').dialog('close')">Cancelar</a>
        </div>  
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        cargarFaenas();
        cargarPredios();
        cargarTipoArboles();
    });
    function crearPlanificacion() {
        document.getElementById("fm").reset();
        $('#dlg').dialog('open').dialog('setTitle', 'Crear Planificacion');
        document.getElementById('accion').value = "AGREGAR";
    }
    function editarPlanificacion() {
        var row = $('#tPlanificacion').datagrid('getSelected');
        if (row) {
            $('#dlg').dialog('open').dialog('setTitle', 'Editar Planificacion');
            $('#fm').form('load', row);

            var fInicio = new Date(row.fechaInicio);
            var diaI = fInicio.getDate();
            if (diaI < 10)
                diaI = "0" + diaI;
            var mesI = (fInicio.getMonth() + 1);
            if (mesI < 10)
                mesI = "0" + mesI;
            document.getElementById('fechaInicio').value = fInicio.getFullYear() + "-" + mesI + "-" + diaI;

            var fTermino = new Date(row.fechaTermino);
            var diaT = fTermino.getDate();
            if (diaT < 10)
                diaT = "0" + diaT;
            var mesT = (fTermino.getMonth() + 1);
            if (mesT < 10)
                mesT = "0" + mesT;
            document.getElementById('fechaTermino').value = fTermino.getFullYear() + "-" + mesT + "-" + diaT;

            document.getElementById('accion').value = "GUARDAR";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Planificacion.');
        }
    }
    function guardarPlanificacion() {
        var resp = validarDatos();
        if (resp == "") {
            $.ajax({
                url: "planificacionMetaProduccionFaena",
                type: "post",
                data: $("#fm").serialize(),
                success: function(data) {
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlg').dialog('close');
                        $('#tPlanificacion').datagrid('reload');
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var fechaInicio = document.getElementById("fechaInicio").value;
        var fechaTermino = document.getElementById("fechaTermino").value;

        if (fechaInicio.length == 0) {
            document.getElementById("fechaInicio").focus();
            return "Debe ingresar la fecha de inicio";
        } else if (fechaTermino.length == 0) {
            document.getElementById("fechaTermino").focus();
            return "Debe ingresar la fecha de termino";
        } else if ((Date.parse(document.getElementById('fechaInicio').value)) > (Date.parse(document.getElementById('fechaTermino').value))) {
            document.getElementById('fechaTermino').focus();
            return "La fecha de termino no puede ser menor a la fecha de inicio";
        }
        return "";
    }
    function eliminarPlanificacion() {
        var row = $('#tPlanificacion').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirmar', '¿Confirma que desea eliminar la planificacion?', function(r) {
                if (r) {//SI
                    $.ajax({
                        url: "planificacionMetaProduccionFaena",
                        type: "post",
                        data: "accion=BORRAR&idPlanificacion=" + row.idPlanificacion,
                        success: function(data) {
                            var data = eval('(' + data + ')');
                            if (data.success) {
                                $('#tPlanificacion').datagrid('reload');
                            }
                            $.messager.alert('Aviso', data.statusText);
                        }
                    });
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Planificacion.');
        }
    }
    function buscarPlanificacion() {
        var buscar = document.getElementById("inputBuscarPlanificacion").value;

        var parm = "";
        if (buscar != "") {
            parm = parm + "&q=" + buscar;
        }

        var url_json = 'planificacionMetaProduccionFaena?accion=BUSCAR&fila=1' + parm;
        $.getJSON(
                url_json,
                function(datos) {
                    $('#tPlanificacion').datagrid('loadData', datos);
                }
        );
    }
    function cargarFaenas() {
        $("#idFaena").empty();
        $.getJSON('/jjara/administrarFaena?accion=LISTADO',
                function(data) {
                    $.each(data, function(k, v) {
                        $("#idFaena").append("<option value=\'" + v.idFaena + "\'>" + v.tipoFaena + " " + v.numeroTeam + "</option>");
                    });
                }
        );
    }

    //METODOS DETALLE PLANIFICACION    
    function verDetalle() {
        var row = $('#tPlanificacion').datagrid('getSelected');
        if (row) {
            document.getElementById("idPlanificacionFaena").value = row.idPlanificacion;
            $.ajax({
                url: "planificacionMetaProduccionFaena",
                type: "post",
                data: "accion=LISTADO_DETALLE&fila=1&idPlanificacion=" + row.idPlanificacion,
                success: function(data) {
                    var data = eval('(' + data + ')');
                    $('#tDetallePlanificacion').datagrid('loadData', data);
                }
            });
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una Planificacion.');
        }
    }
    function crearDetalle() {
        var rowPlanificacion = $('#tPlanificacion').datagrid('getSelected');
        if (rowPlanificacion) {
            var idPlanificacion = rowPlanificacion.idPlanificacion;
            document.getElementById("fmDetalle").reset();
            document.getElementById("idPlanificacionFaena").value = idPlanificacion;
            $('#dlgDetalle').dialog('open').dialog('setTitle', 'Crear Detalle');
            document.getElementById('accionDetalle').value = "AGREGAR_DETALLE";
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una planificacion.');
        }
    }
    function editarDetalle() {
        var rowPlanificacion = $('#tPlanificacion').datagrid('getSelected');
        if (rowPlanificacion) {
            var row = $('#tDetallePlanificacion').datagrid('getSelected');
            if (row) {
                $('#dlgDetalle').dialog('open').dialog('setTitle', 'Editar Detalle Planificacion');
                $('#fmDetalle').form('load', row);

                var fInicio = new Date(row.fechaInicioDetalle);
                var diaI = fInicio.getDate();
                if (diaI < 10)
                    diaI = "0" + diaI;
                var mesI = (fInicio.getMonth() + 1);
                if (mesI < 10)
                    mesI = "0" + mesI;
                document.getElementById('fechaInicioDetalle').value = fInicio.getFullYear() + "-" + mesI + "-" + diaI;

                var fTermino = new Date(row.fechaTerminoDetalle);
                var diaT = fTermino.getDate();
                if (diaT < 10)
                    diaT = "0" + diaT;
                var mesT = (fTermino.getMonth() + 1);
                if (mesT < 10)
                    mesT = "0" + mesT;
                document.getElementById('fechaTerminoDetalle').value = fTermino.getFullYear() + "-" + mesT + "-" + diaT;

                document.getElementById('accionDetalle').value = "GUARDAR_DETALLE";
            } else {
                $.messager.alert('Alerta', 'Debe seleccionar un detalle de planificacion.');
            }
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una planificacion, y luego el detalle a modificar.');
        }
    }
    function guardarDetalle() {
        var accion = document.getElementById("accionDetalle").value;
        var resp = validarDatosDetalle();
        if (resp == "") {
            $.ajax({
                url: "planificacionMetaProduccionFaena",
                type: "post",
                data: $("#fmDetalle").serialize() + "&accion=" + accion,
                success: function(data) {
                    var data = eval('(' + data + ')');
                    if (data.success) {
                        $('#dlgDetalle').dialog('close');
                        verDetalle();
                    }
                    $.messager.alert('Aviso', data.statusText);
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatosDetalle() {
        var numeroActa = document.getElementById("numeroActa").value;
        var fechaInicioDetalle = document.getElementById("fechaInicioDetalle").value;
        var fechaTerminoDetalle = document.getElementById("fechaTerminoDetalle").value;
        var metrosCubicos = document.getElementById("metrosCubicos").value;

        if (numeroActa == null || numeroActa.length == 0) {
            document.getElementById("numeroActa").focus();
            return "Debe ingresar el número de acta";
        } else if (isNaN(numeroActa) ) {
            document.getElementById("numeroActa").focus();
            return "El número de acta tiene caracteres no validos.";
        } else if (numeroActa <= 0 ) {
            document.getElementById("numeroActa").focus();
            return "El número de acta debe ser mayor a 0.";
        } else if (fechaInicioDetalle == null || fechaInicioDetalle.length == 0) {
            document.getElementById("fechaInicio").focus();
            return "Debe ingresar la fecha de inicio";
        } else if (fechaTerminoDetalle == null || fechaTerminoDetalle.length == 0) {
            document.getElementById("fechaTermino").focus();
            return "Debe ingresar la fecha de termino";
        } else if ((Date.parse(document.getElementById('fechaInicioDetalle').value)) > (Date.parse(document.getElementById('fechaTerminoDetalle').value))) {
            document.getElementById('fechaTermino').focus();
            return "La fecha de termino no puede ser menor a la fecha de inicio";
        } else if (metrosCubicos == null || metrosCubicos.length == 0) {
            document.getElementById("metrosCubicos").focus();
            return "Debe ingresar los metros cubicos";
        } else if (isNaN(metrosCubicos) ) {
            document.getElementById("metrosCubicos").focus();
            return "Los metros cubicos tiene caracteres no validos.";
        } else if (metrosCubicos <= 0 ) {
            document.getElementById("metrosCubicos").focus();
            return "Los metros cubicos tiene que ser mayor a 0.";
        }
        return "";
    }

    function eliminarDetalle() {
        var rowPlanificacion = $('#tPlanificacion').datagrid('getSelected');
        if (rowPlanificacion) {
            var row = $('#tDetallePlanificacion').datagrid('getSelected');
            if (row) {
                $.messager.confirm('Confirmar', '¿Confirma que desea eliminar el detalle de planificacion?', function(r) {
                    if (r) {//SI
                        $.ajax({
                            url: "planificacionMetaProduccionFaena",
                            type: "post",
                            data: "accion=BORRAR_DETALLE&idDetallePlanificacion=" + row.idDetallePlanificacion,
                            success: function(data) {
                                var data = eval('(' + data + ')');
                                if (data.success) {
                                    verDetalle();
                                }
                                $.messager.alert('Aviso', data.statusText);
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('Alerta', 'Debe seleccionar una Planificacion.');
            }
        } else {
            $.messager.alert('Alerta', 'Debe seleccionar una planificacion, y luego el detalle a eliminar.');
        }
    }
    function cargarPredios() {
        $("#idPredio").empty();
        $.getJSON('/jjara/administrarPredio?accion=LISTAR_ALL_PREDIO',
                function(data) {
                    $.each(data, function(k, v) {
                        $("#idPredio").append("<option value=\'" + v.idPredio + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }
    
    function cargarTipoArboles() {
        $("#idTipoArbol").empty();
        $.getJSON('/jjara/planificacionMetaProduccionFaena?accion=LISTAR_TIPO_ARBOLES',
                function(data) {
                    $.each(data, function(k, v) {
                        $("#idTipoArbol").append("<option value=\'" + v.idTipoArbol + "\'>" + v.nombre + "</option>");
                    });
                }
        );
    }


</script>