<%-- 
    Document   : administrarTableroRaleo
    Created on : 21-05-2015, 09:42:40 PM
    Author     : Javier-PC
--%>
<!-- DataGridView--> 
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>


<div class="easyui-tabs" style="width:1250px;height:500px">
    <div title="Tablero Gestion" style="padding:10px">
        <div class="tabla" >
            <table id="tablaRaleoForwarder">
                
            </table>
        </div>
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Indicadores Operacionales Raleo (Fw)</div>
                <div class="cuerpo-grafico">
                    <canvas id="indicadoresOperacionalesForwarder" height="200" width="750"></canvas>
                </div>
                <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-indicadores.png"></div>
            </div>
        </div>
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Producción y Productividad (Fw)</div>
                <div class="cuerpo-grafico">
                    <canvas id="produccionProductividadForwarder" height="200" width="750"></canvas>
                </div>
                <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-produccion.png"></div>
            </div>
        </div>
        
        <div class="grafico">
            <div class="titulo-grafico">Rendimiento (Fw)</div>
            <div class="cuerpo-grafico">
                <canvas id="rendimientoForwarder" height="250" width="1210"></canvas>
            </div>
            <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-rendimiento.png"></div>
        </div>
        
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Horas Máquinas (Fw)</div>
                <div class="cuerpo-grafico">
                    <canvas id="horasMaquinaForwarder" height="200" width="750"></canvas>
                </div>
                <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-horamaquina.png"></div>
            </div>
        </div>
        
       <div class="tabla" >
            <table id="tablaRaleoHarvester">
                
            </table>
        </div>
        
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Indicadores Operacionales Raleo (Hv)</div>
                <div class="cuerpo-grafico">
                    <canvas id="indicadoresOperacionalesHarvester" height="200" width="750"></canvas>
                </div>
                <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-indicadores.png"></div>
            </div>
        </div>
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Producción y Productividad (Hv)</div>
                <div class="cuerpo-grafico">
                    <canvas id="produccionProductividadHarvester" height="200" width="750"></canvas>
                </div>
                <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-produccion.png"></div>
            </div>
        </div>
        
        <div class="grafico">
            <div class="titulo-grafico">Rendimiento (Hv)</div>
            <div class="cuerpo-grafico">
                <canvas id="rendimientoHarvester" height="250" width="1210"></canvas>
            </div>
            <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-rendimiento.png"></div>
        </div>
        
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Horas Máquinas (Hv)</div>
                <div class="cuerpo-grafico">
                    <canvas id="horasMaquinaHarvester" height="200" width="750"></canvas>
                </div>
                <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-horamaquina.png"></div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(document).ready(function() {
        cargarCabezaTablaForwarder();
        cargarCabezaTablaHarvester();
        try {
            mostrarGraficosRaleoForwarder();
        } catch (err) {
            cargarOpcion('administrarTableroRaleo');
        }
        try {
            mostrarGraficosRaleoHarvester();
        } catch (err) {
            cargarOpcion('administrarTableroRaleo');
        }
    });
    function cargarCabezaTablaForwarder() {
        $("#tablaRaleoForwarder").empty();
        $.getJSON('/jjara/administrarTableroRaleo?accion=HTML_TABLA_RALEO_FORWARDER',
                function(data) {
                    $("#tablaRaleoForwarder").append(data);
                }
        );
    }
    function cargarCabezaTablaHarvester() {
        $("#tablaRaleoHarvester").empty();
        $.getJSON('/jjara/administrarTableroRaleo?accion=HTML_TABLA_RALEO_HARVESTER',
                function(data) {
                    $("#tablaRaleoHarvester").append(data);
                }
        );
    }

    var horasMaquinaRealForwarderRaleo;
    var horasMaquinaPlanForwarderRaleo;
    var nombreMesesRaleo;
    var produccionRealForwarderRaleo;
    var produccionPlanForwarderRaleo;
    var productividadRealForwarderRaleo;
    var dispobibilidadForwarderRaleo;
    var eficienciaForwarderRaleo;
    var capacidadForwarderRaleo;
    var oeeForwarderRaleo;
    function mostrarGraficosRaleoForwarder() {
        $.ajax({
            async: false,
            url: "administrarTableroRaleo",
            type: "post",
            data: "accion=DATOS_GRAFICO_RALEO_FORWARDER",
            success: function (data) {
                var data = eval('(' + data + ')');
                nombreMesesRaleo = data[0].listaMeses;
                horasMaquinaRealForwarderRaleo = data[0].data;
                horasMaquinaPlanForwarderRaleo = data[1].data;
                produccionRealForwarderRaleo = data[2].data;
                produccionPlanForwarderRaleo = data[3].data;
                productividadRealForwarderRaleo = data[4].data;
                dispobibilidadForwarderRaleo = data[5].data;
                eficienciaForwarderRaleo = data[6].data;
                capacidadForwarderRaleo = data[7].data;
                oeeForwarderRaleo = data[8].data;
                
                //GRAFICO DE LINEAS 1 = indicadoresOperacionalesForwarder
                var ctx1 = document.getElementById("indicadoresOperacionalesForwarder").getContext("2d");
                window.myLine = new Chart(ctx1).Line(lineaIndicadoresOperacionalesForwarderRaleo, {
                    responsive: true
                });
                //GRAFICO DE LINEAS 2 = produccionProductividadForwarder
                var ctx2 = document.getElementById("produccionProductividadForwarder").getContext("2d");
                window.myLine = new Chart(ctx2).Line(lineaProduccionProductividadForwarderRaleo, {
                    responsive: true
                });
                
                //GRAFICO DE LINEA 4 = Rendimiento
                var ctx4 = document.getElementById("rendimientoForwarder").getContext("2d");
                window.grafico4ForwarderRaleo = new Chart(ctx4).Line(lineaRendimientoForwarderRaleo);

                //GRAFICO DE LINEAS 3 = HorasTrabajoForwarder
                var ctx3 = document.getElementById("horasMaquinaForwarder").getContext("2d");
                window.myLine = new Chart(ctx3).Line(lineaHoraTrabajoForwarderRaleo, {
                    responsive: true
                });

            }
        });
    }
    //GRAFICO INDICADORES OPERACIONALES FORWARDER
    var lineaIndicadoresOperacionalesForwarderRaleo = {
        labels: nombreMesesRaleo,
        datasets: [
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: oeeForwarderRaleo
            },
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: dispobibilidadForwarderRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: eficienciaForwarderRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(125,96,160,0)",
                strokeColor: "rgba(125,96,160,1)",
                pointColor: "rgba(125,96,160,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(125,96,160,1)",
                data: capacidadForwarderRaleo
            }
        ]
    }

    //GRAFICO PRODUCCION PRODUCTIVIDAD FORWARDER
    var lineaProduccionProductividadForwarderRaleo = {
        labels: nombreMesesRaleo,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: produccionRealForwarderRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: produccionPlanForwarderRaleo
            }
        ]
    }
    
    //GRAFICO RENDIMIENTO FORWARDER
    var lineaRendimientoForwarderRaleo = {
        labels: nombreMesesRaleo,
        datasets: [
            {
                label: "My Second dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: productividadRealForwarderRaleo
            }
        ]
    }

    //GRAFICO HORAS MAQUINA FORWARDER
    var lineaHoraTrabajoForwarderRaleo = {
        labels: nombreMesesRaleo,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: horasMaquinaRealForwarderRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: horasMaquinaPlanForwarderRaleo
            }
        ]
    }

    //HARVESTER
    
    var horasMaquinaRealHarvesterRaleo;
    var horasMaquinaPlanHarvesterRaleo;
    var nombreMesesHarvesterRaleo;
    var produccionRealHarvesterRaleo;
    var produccionPlanHarvesterRaleo;
    var productividadRealHarvesterRaleo;
    var dispobibilidadHarvesterRaleo;
    var eficienciaHarvesterRaleo;
    var capacidadHarvesterRaleo;
    var oeeHarvesterRaleo;
    function mostrarGraficosRaleoHarvester() {
        $.ajax({
            async: false,
            url: "administrarTableroRaleo",
            type: "post",
            data: "accion=DATOS_GRAFICO_RALEO_HARVESTER",
            success: function (data) {                
                var data = eval('(' + data + ')');
                nombreMesesHarvesterRaleo = data[0].listaMeses;
                horasMaquinaRealHarvesterRaleo = data[0].data;
                horasMaquinaPlanHarvesterRaleo = data[1].data;
                produccionRealHarvesterRaleo = data[2].data;
                produccionPlanHarvesterRaleo = data[3].data;
                productividadRealHarvesterRaleo = data[4].data;
                dispobibilidadHarvesterRaleo = data[5].data;
                eficienciaHarvesterRaleo = data[6].data;
                capacidadHarvesterRaleo = data[7].data;
                oeeHarvesterRaleo = data[8].data;
                
                //GRAFICO DE LINEAS 1 = indicadoresOperacionalesHarvester
                var ctx = document.getElementById("indicadoresOperacionalesHarvester").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineaIndicadoresOperacionalesHarvesterRaleo, {
                    responsive: true
                });
                //GRAFICO DE LINEAS 2 = produccionProductividadHarvester
                var ctx = document.getElementById("produccionProductividadHarvester").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineaProduccionProductividadHarvesterRaleo, {
                    responsive: true
                });
                
                //GRAFICO DE LINEA 4 = Rendimiento
                var ctx8 = document.getElementById("rendimientoHarvester").getContext("2d");
                window.grafico8HarvesterRaleo= new Chart(ctx8).Line(lineaRendimientoHarvesterRaleo);
                
                //GRAFICO DE LINEAS 3 = HorasTrabajoHarvester
                var ctx = document.getElementById("horasMaquinaHarvester").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineaHoraTrabajoHarvesterRaleo, {
                    responsive: true
                });

            }
        });
    }
    //GRAFICO INDICADORES OPERACIONALES HARVESTER
    var lineaIndicadoresOperacionalesHarvesterRaleo = {
        labels: nombreMesesHarvesterRaleo,
        datasets: [
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: oeeHarvesterRaleo
            },
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: dispobibilidadHarvesterRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: eficienciaHarvesterRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(125,96,160,0)",
                strokeColor: "rgba(125,96,160,1)",
                pointColor: "rgba(125,96,160,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(125,96,160,1)",
                data: capacidadHarvesterRaleo
            }
        ]
    }

    //GRAFICO PRODUCCION PRODUCTIVIDAD HARVESTER
    var lineaProduccionProductividadHarvesterRaleo = {
        labels: nombreMesesHarvesterRaleo,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: produccionRealHarvesterRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: produccionPlanHarvesterRaleo
            }
        ]
    }
    
    //GRAFICO Rendimiento HARVESTER
    var lineaRendimientoHarvesterRaleo = {
        labels: nombreMesesHarvesterRaleo,
        datasets: [            
            {
                label: "My Second dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: productividadRealHarvesterRaleo
            }
        ]
    }
    
    //GRAFICO HORAS MAQUINA HARVESTER
    var lineaHoraTrabajoHarvesterRaleo = {
        labels: nombreMesesHarvesterRaleo,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: horasMaquinaRealHarvesterRaleo
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: horasMaquinaPlanHarvesterRaleo
            }
        ]
    }
</script>

