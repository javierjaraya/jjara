<%-- 
    Document   : administrarReporteForwarder
    Created on : 21-05-2015, 09:41:25 PM
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
    <div class="easyui-panel" title="Reporte NOC" style="width:590px;padding:10px;">
        <div id="formulario-reporte">
            <form id="fm" action="administrarReporteNOC" method="post" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td>Fecha:</td>
                        <td><input type="date" name="fecha" id="fecha" class="f1 easyui-textbox" style="width:156px;"></td>
                    </tr>
                    <tr>
                        <td colspan="4"><hr></td>
                    </tr>
                    <tr>
                        <td >Codigo Forestal:</td>
                        <td><select name="idMaquina" id="idMaquina" style="width:156px;" /></td>
                        <td >Operador:</td>
                        <td><select name="idOperador" id="idOperador" style="width:156px;" /></td>
                    </tr>
                    <tr>
                        <td>Faena:</td>
                        <td><select name="idFaena" id="idFaena" style="width:156px;"/></td>                
                        <td>Predio:</td>
                        <td><select name="idPredio" id="idPredio" style="width:156px;"/></td>
                    </tr>
                    <tr>
                        <td colspan="4"><hr></td>
                    </tr>
                    <tr style="height: 30px;">
                        <td  colspan="4">DATOS GENERALES</td>
                    </tr>
                    <tr>
                        <td>m3 Notificados</td>
                        <td><input name="m3" id="m3" style="width:156px;"/></td>
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
        <div id="espere" style="display: 'none'; text-align: center; width:550px; height: 160px; padding-top: 80px;">

        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        document.getElementById("espere").style.display = "none";
        cargarFaenas();
        cargarPredios();
        cargarMaquinas();
        cargarOperadores();
    });

    function enviarReporte() {        
        var resp = validarDatos();
        if (resp == "") {
            iniciarCarga();
            $.ajax({
                url: "administrarReporteNOC",
                type: "post",
                data: $("#fm").serialize() + "&accion=REGISTRAR_REPORTE_NOC",
                success: function (data) {
                    finalizarCarga();
                    var data = eval('(' + data + ')');
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
        var idMaquina = document.getElementById("idMaquina").value;
        var idFaena = document.getElementById("idFaena").value;
        var idPredio = document.getElementById("idPredio").value;
        var idOperador = document.getElementById("idOperador").value;
        var observacion = document.getElementById("observacion").value;
        var m3 = document.getElementById("m3").value;

        if (fecha == null || fecha.length == 0) {
            document.getElementById("fecha").focus();
            return "Debe ingresar la fecha";
        } else if (idMaquina == null || idMaquina.length == 0 || idMaquina == 0) {
            document.getElementById("idMaquina").focus();
            return "Debe seleccionar el codigo forestal";
        } else if (idFaena == null || idFaena.length == 0 || idFaena == 0) {
            document.getElementById("idFaena").focus();
            return "Debe seleccionar un equipo";
        } else if (idPredio == null || idPredio.length == 0 || idPredio == 0) {
            document.getElementById("idPredio").focus();
            return "Debe seleccionar un predio";
        } else if (idOperador == null || idOperador.length == 0 || idOperador == 0) {
            document.getElementById("idOperador").focus();
            return "Debe seleccionar un operador";
        } else if (m3 == null || m3.length == 0) {
            document.getElementById("m3").focus();
            return "Debe ingresar los metros cubicos";
        } else if (isNaN(m3)) {
            document.getElementById("m3").focus();
            return "Los metros cubicos ingresados contiene caracteres no validos";
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

    function cargarMaquinas() {
        $("#idMaquina").empty();
        $.getJSON('/jjara/administrarMaquina?accion=LISTAR_MAQUINAS_COMBOTREE&tipoCodigo=Forestal',
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
        $.getJSON('/jjara/administrarFaena?accion=LISTAR_TIPO_FAENA_COMBOTREE',
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