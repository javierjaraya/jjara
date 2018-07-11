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
           url="administrarRegistroHarvester?accion=LISTADO&fila=1"
           title="Registros Trabajo Produccion Harvester"
           toolbar="#toolbar" pagination="true" loadMsg="Procesando...espere..."
           rownumbers="false" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="folio" width="12">Folio</th>
                <th field="fechaRegistro" width="25">Fecha</th>
                <th field="faena" width="20">Faena</th>
                <th field="maquina" width="20">Máquina</th> 
                <th field="operador" width="20">Operador</th>
                <th field="turno" width="13">Turno</th>
                <th field="jornada" width="16">Jornada</th>
                <th field="predio" width="25">Predio</th>
                <th field="horometroInicial" width="15">Horómetro Inicial</th>
                <th field="horometroFinal" width="15">Horometro Final</th>
                <th field="mArbol" width="15">m3/Arb</th>
                <th field="hMaqPlan" width="15">Hrs Maq Plan</th>
                <th field="hMaqReal" width="15">Hrs Maq Real</th>
                <th field="arbHrPlan" width="15">Arb/Hr Plan</th>
                <th field="arbHrReal" width="15">Arb/Hr Real</th>
                <th field="arbPlan" width="15">Arb Plan</th>
                <th field="arbReal" width="15">Arb Real</th>
                <th field="mHrPlan" width="15">m3/Hr Plan</th>
                <th field="mHrReal" width="15">m3/Hr Real</th>
                <th field="mPlan" width="15">m3 Plan</th>
                <th field="mReal" width="15">m3 Real</th>
            </tr>                         
        </thead>
    </table>
    <div id="toolbar">
        <input type="button" value="Exportar" onclick="generarExcel()" class="btn-verde">
    </div>
</div>

<script type="text/javascript">
    function generarExcel() {
        window.open("/jjara/web/registroTrabajoProduccion/harvester/excel.jsp", this.target, 'width=500,height=300');        
    }
</script>