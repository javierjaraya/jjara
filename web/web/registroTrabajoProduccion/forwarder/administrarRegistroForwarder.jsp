<%-- 
    Document   : administrarRegistroForwarder
    Created on : 21-05-2015, 09:42:54 PM
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
    <table id="tRegistroForwarder" class="easyui-datagrid" style="width:1199px;height:490px"
           url="administrarRegistroForwarder?accion=LISTADO&fila=1"
           title="Registros Trabajo Produccion Forwarder"
           toolbar="#toolbar" pagination="true" loadMsg="Procesando...espere..."
           rownumbers="false" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="folio" width="10">Folio</th>
                <th field="fechaRegistro" width="25">Fecha</th>
                <th field="faena" width="20">Faena</th>
                <th field="maquina" width="20">Máquina</th> 
                <th field="operador" width="35">Operador</th>
                <th field="turno" width="13">Turno</th>
                <th field="jornada" width="15">Jornada</th>
                <th field="predio" width="25">Predio</th>
                <th field="horometroInicial" width="15">Horómetro Inicial</th>
                <th field="horometroFinal" width="15">Horometro Final</th>
                <th field="hMaqPlan" width="15">Hmaq Plan</th>
                <th field="hMaqReal" width="15">Hmaq Real</th>
                <th field="cicloHrPlan" width="15">Ciclo/Hr Plan</th>
                <th field="cicloHrReal" width="15">Ciclo/Hr Real</th>
                <th field="cicloPlan" width="15">Ciclos Plan</th>
                <th field="cicloReal" width="15">Ciclos Real</th>
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
        window.open("/jjara/web/registroTrabajoProduccion/forwarder/excel.jsp", this.target, 'width=500,height=300');        
    }
</script>