<%-- 
    Document   : administrarRegistroHarvester
    Created on : 21-05-2015, 09:43:04 PM
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
    
    <table id="tRegistroForwarder" class="easyui-datagrid" style="width:1194px;height:490px"
           url="administrarRegistroNOC?accion=LISTADO&fila=1"
           title="Registros Trabajo Produccion Harvester"
           toolbar="#toolbar" pagination="true" loadMsg="Procesando...espere..."
           rownumbers="false" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="idRegistroNoc" width="5">Folio</th>
                <th field="fechaRegistro" width="11">Fecha</th>
                <th field="faena" width="10">Faena</th>
                <th field="maquina" width="10">Máquina</th> 
                <th field="operador" width="30">Operador</th> 
                <th field="predio" width="25">Predio</th>
                <th field="m3" width="15">m3 Notificados</th>
                <th field="observacion" width="40">Observacion</th>
            </tr>                         
        </thead>
    </table>
    <div id="toolbar">
        <input type="button" value="Exportar" onclick="generarExcel()" class="btn-verde">
    </div>
</div>

<script type="text/javascript">
    function generarExcel() {
        window.open("/jjara/web/registroTrabajoProduccion/noc/excel.jsp", this.target, 'width=500,height=300');
    }
</script>