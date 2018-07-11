<%-- 
    Document   : administrarReporteHarvester
    Created on : 21-05-2015, 09:41:40 PM
    Author     : Javier-PC
--%>
<!-- DataGridView--> 
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>

<div style="padding-left: 330px; padding-top: 20px;">
    <div class="easyui-panel" title="Reporte Diario Harvester" style="width:590px;padding:10px;">
        <div id="formulario-reporte">
            <form id="fm" action="administrarReporteHarvester" method="post" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td>Fecha:</td>
                        <td><input type="date" name="fecha" id="fecha" class="f1 easyui-textbox" style="width:156px;"></td>
                        <td>Jornada</td>
                        <td><select name="idJornada" id="idJornada" style="width:156px;"/></td>
                    </tr>
                    <tr>
                        <td colspan="4"><hr></td>
                    </tr>
                    <tr>
                        <td>Operador:</td>
                        <td><select name="idOperador" id="idOperador" style="width:156px;" onChange=""/></td>
                        <td>Maquina:</td>
                        <td><select name="idMaquina" id="idMaquina" style="width:156px;" onChange="cargarHorometroMaquina()"/></td>
                    </tr>
                    <tr>
                        <td>Equipo:</td>
                        <td><select name="idFaena" id="idFaena" style="width:156px;"/></td>                
                        <td>Predio:</td>
                        <td><select name="idPredio" id="idPredio" style="width:156px;"/></td>
                    </tr>
                    <tr style="height: 30px;">
                        <td colspan="4">DATOS GENERALES</td>
                    </tr>
                    <tr>
                        <td>Turno:</td>
                        <td><select name="turno" id="turno" style="width:156px;">
                                <option value="0">Seleccionar...</option>
                                <option value="5">Medio Turno</option>
                                <option value="10">Turno Completo</option>
                            </select></td>
                    </tr>
                    <tr style="height: 30px;">
                        <td colspan="4">HOROMETRO</td>
                    </tr>
                    <tr>
                        <td>Inicial:</td>
                        <td><input name="horometroInicial" id="horometroInicial" readonly="readonly" style="width:156px;"/></td>
                        <td>Final:</td>
                        <td><input name="horometroFinal" id="horometroFinal" style="width:156px;"/></td>
                    </tr>
                    <tr style="height: 30px;">
                        <td  colspan="4">DATOS HARVESTER</td>
                    </tr>
                    <tr>
                        <td>Arboles Operativos:</td>
                        <td><input name="arbolesOperativos" id="arbolesOperativos" style="width:156px;"/></td>
                        <td>M3 Operativos:</td>
                        <td><input name="m3Operativos" id="m3Operativos" style="width:156px;"/></td>
                    </tr>
                    <tr>
                        <td>Observación:</td>                        
                        <td colspan="4"><br><textarea name="observacion" id="observacion" style="width: 430px; height:70px;"></textarea></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="enviarReporte()">Enviar Reporte</a></td>
                    </tr>                
                </table>
            </form>
        </div>
        <div id="espere" style="display: 'none'; text-align: center; width:550px; height: 230px; padding-top: 120px;">

        </div>
    </div>
</div>


<script type="text/javascript">
    $(document).ready(function () {
        document.getElementById("espere").style.display = "none";
        cargarOperadores();
        cargarFaenas();
        cargarPredios();
        cargarJornada();
        cargarMaquinas();
    });

    function enviarReporte() {
        var resp = validarDatos();
        if (resp == "") {
            iniciarCarga();
            $.ajax({
                url: "administrarReporteHarvester",
                type: "post",
                data: $("#fm").serialize() + "&accion=REGISTRAR_REPORTE_HARVESTER",
                success: function (data) {
                    var data = eval('(' + data + ')');
                    finalizarCarga();
                    $.messager.alert('Aviso', data.statusText);
                    document.getElementById("fm").reset();
                }
            });
        } else {
            $.messager.alert('Alerta', resp);
        }
    }
    function validarDatos() {
        var fecha = document.getElementById("fecha").value;
        var idJornada = document.getElementById("idJornada").value;
        var idOperador = document.getElementById("idOperador").value;
        var idMaquina = document.getElementById("idMaquina").value;
        var idFaena = document.getElementById("idFaena").value;
        var idPredio = document.getElementById("idPredio").value;
        var turno = document.getElementById("turno").value;
        var horometroInicial = document.getElementById("horometroInicial").value;
        var horometroFinal = document.getElementById("horometroFinal").value;
        var arbolesOperativos = document.getElementById("arbolesOperativos").value;
        var m3Operativos = document.getElementById("m3Operativos").value;
        var observacion = document.getElementById("observacion").value;

        if (fecha == null || fecha.length == 0) {
            document.getElementById("fecha").focus();
            return "Debe ingresar la fecha";
        } else if (idJornada == null || idJornada.length == 0 || idJornada == 0) {
            document.getElementById("idJornada").focus();
            return "Debe seleccionar una jornada";
        } else if (idOperador == null || idOperador.length == 0 || idOperador == 0) {
            document.getElementById("idOperador").focus();
            return "Debe seleccionar un operador";
        } else if (idMaquina == null || idMaquina.length == 0 || idMaquina == 0) {
            document.getElementById("idMaquina").focus();
            return "Debe seleccionar una máquina";
        } else if (idFaena == null || idFaena.length == 0 || idFaena == 0) {
            document.getElementById("idFaena").focus();
            return "Debe seleccionar un equipo";
        } else if (idPredio == null || idPredio.length == 0 || idPredio == 0) {
            document.getElementById("idPredio").focus();
            return "Debe seleccionar un predio";
        } else if (turno == null || turno.length == 0 || turno == 0) {
            document.getElementById("turno").focus();
            return "Debe seleccionar un turno";
        } else if (horometroInicial == null || horometroInicial.length == 0) {
            document.getElementById("horometroInicial").focus();
            return "Debe ingresar el horometro inicial";
        } else if (isNaN(horometroInicial)) {
            document.getElementById("horometroInicial").focus();
            return "El horometro inicial contiene caracteres no es validos";
        } else if (horometroFinal == null || horometroFinal.length == 0) {
            document.getElementById("horometroFinal").focus();
            return "Debe ingresar el horometro final";
        } else if (isNaN(horometroFinal)) {
            document.getElementById("horometroFinal").focus();
            return "El horometro final contiene caracteres no es validos";
        } else if (horometroFinal <= horometroInicial) {
            document.getElementById("horometroFinal").focus();
            return "El horometro final no puede ser menor o igual al horometro inicial";
        } else if (arbolesOperativos == null || arbolesOperativos.length == 0) {
            document.getElementById("arbolesOperativos").focus();
            return "Debe ingresar el numero de arboles operativos";
        } else if (isNaN(arbolesOperativos)) {
            document.getElementById("arbolesOperativos").focus();
            return "El numero de arboles operativos contiene caracteres no es validos";
        } else if (m3Operativos == null || m3Operativos.length == 0) {
            document.getElementById("m3Operativos").focus();
            return "Debe ingresar los m3 operativos";
        } else if (isNaN(m3Operativos)) {
            document.getElementById("m3Operativos").focus();
            return "Los m3 operativos contiene caracteres no es validos";
        }
        return "";
    }

    function cargarOperadores() {
        $("#idOperador").empty();
        $.getJSON('/jjara/administrarEmpleado?accion=LISTAR_EMPLEADOS_COMBOTREE',
                function (data) {
                    $("#idOperador").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function (k, v) {
                        $("#idOperador").append("<option value=\'" + v.id + "\'>" + v.text + "</option>");
                    });
                }
        );
    }

    function cargarHorometroMaquina() {
        var idMaquina = document.getElementById("idMaquina").value;
        if (idMaquina != 0) {
            $.getJSON('/jjara/administrarMaquina?accion=OBTENER_MAQUINA_BY_ID&idMaquina=' + idMaquina,
                    function (data) {
                        document.getElementById("horometroInicial").value = data.horometro;
                    }
            );
        } else {
            document.getElementById("horometroInicial").value = 0;
        }
    }

    function cargarMaquinas() {
        $("#idMaquina").empty();
        $.getJSON('/jjara/administrarMaquina?accion=LISTAR_MAQUINAS_COMBOTREE',
                function (data) {
                    $("#idMaquina").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function (k, v) {
                        $("#idMaquina").append("<option value=\'" + v.id + "\'>" + v.text + "</option>");
                    });
                }
        );
    }

    function cargarFaenas() {
        $("#idFaena").empty();
        $.getJSON('/jjara/administrarFaena?accion=LISTAR_FAENAS_COMBOTREE',
                function (data) {
                    $("#idFaena").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function (k, v) {
                        $("#idFaena").append("<option value=\'" + v.id + "\'>" + v.text + "</option>");
                    });
                }
        );
    }

    function cargarPredios() {
        $("#idPredio").empty();
        $.getJSON('/jjara/administrarPredio?accion=LISTAR_PREDIOS_COMBOTREE',
                function (data) {
                    $("#idPredio").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function (k, v) {
                        $("#idPredio").append("<option value=\'" + v.id + "\'>" + v.text + "</option>");
                    });
                }
        );
    }

    function cargarJornada() {
        $("#idJornada").empty();
        $.getJSON('/jjara/administrarReporteForwarder?accion=LISTAR_JORNADAS_COMBOTREE',
                function (data) {
                    $("#idJornada").append("<option value=\'0\'>Seleccionar...</option>");
                    $.each(data, function (k, v) {
                        $("#idJornada").append("<option value=\'" + v.id + "\'>" + v.text + "</option>");
                    });
                }
        );
    }
    
    function iniciarCarga() {
        var formulario = document.getElementById("formulario-reporte")
        formulario.style.display = 'none';
        
        document.getElementById("espere").style.display= 'block';
        $('div#espere').html("<img src='files/img/carg.gif'/>");
    }
    function finalizarCarga() {
        var formulario = document.getElementById("formulario-reporte")
        formulario.style.display = 'block';
        document.getElementById("espere").style.display= 'none';
        $('div#espere').html("");
    }
</script>