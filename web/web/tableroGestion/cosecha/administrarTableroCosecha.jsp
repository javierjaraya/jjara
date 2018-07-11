<%-- 
    Document   : administrarTableroCosecha
    Created on : 21-05-2015, 09:42:30 PM
    Author     : Javier-PC
--%>

<!-- Graficos Chart-->
<script type="text/javascript" src="/jjara/files/Complementos/GraficosChart/Chart.js"></script>
<link rel="stylesheet" type="text/css" href="/jjara/files/css/estilointerior.css">

<!-- DataGridView--> 
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/jjara/files/Complementos/jquery-easyui-1.4.2/demo/demo.css">
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jjara/files/Complementos/jquery-easyui-1.4.2/plugins/jquery.datagrid.js"></script>


<div class="easyui-tabs" style="width:1250px;height:500px">
    <div title="Tablero Gestion" style="padding:10px">
        <div class="seccion-tablero">
            <!-- <div class="titulo-tablero"><img src="/jjara/files/img/forwarder.png" width="101px" height="56"></div> -->
            <div class="tabla" >
                <table id="tablaCosechaForwarder">

                </table>
            </div>
        </div>
        <div class="grafico" style="padding-top: 230px">
            <div class="titulo-grafico">Indicadores Operacionales Cosecha (Fw)</div>
            <div class="cuerpo-grafico">
                <canvas id="indicadoresOperacionalesForwarder" height="200" width="750"></canvas>
            </div>
            <div class="leyenda-grafico"><img src="/jjara/files/img/grafico/leyenda-indicadores.png"></div>
        </div>

        <div class="grafico" style="width:100%;">
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
            <table id="tablaCosechaHarvester">

            </table>
        </div>
        <div style="width:100%">
            <div class="grafico">
                <div class="titulo-grafico">Indicadores Operacionales Cosecha (Hv)</div>
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
    $(document).ready(function () {
        cargarCabezaTablaForwarder();
        cargarCabezaTablaHarvester();
        try {
            mostrarGraficosCosechaForwarder();
        } catch (err) {
            cargarOpcion('administrarTableroCosecha');
        }
        try {
            mostrarGraficosCosechaHarvester();
        } catch (err) {
            cargarOpcion('administrarTableroCosecha');
        }
    });

    function cargarCabezaTablaForwarder() {
        $("#tablaCosechaForwarder").empty();
        $.getJSON('/jjara/administrarTableroCosecha?accion=HTML_TABLA_COSECHA_FORWARDER',
                function (data) {
                    $("#tablaCosechaForwarder").append(data);
                }
        );
    }
    function cargarCabezaTablaHarvester() {
        $("#tablaCosechaHarvester").empty();
        $.getJSON('/jjara/administrarTableroCosecha?accion=HTML_TABLA_COSECHA_HARVESTER',
                function (data) {
                    $("#tablaCosechaHarvester").append(data);
                }
        );
    }

    var horasMaquinaRealForwarderCosecha;
    var horasMaquinaPlanForwarderCosecha;
    var nombreMesesCosecha;
    var produccionRealForwarderCosecha;
    var produccionPlanForwarderCosecha;
    var productividadRealForwarderCosecha;
    var dispobibilidadForwarderCosecha;
    var eficienciaForwarderCosecha;
    var capacidadForwarderCosecha;
    var oeeForwarderCosecha;
    function mostrarGraficosCosechaForwarder() {
        $.ajax({
            async: false, /*false = sincronas (El cliente se bloquea)   || true = asincrona (El cliente sigue funcionando)*/
            url: "administrarTableroCosecha",
            type: "post",
            data: "accion=DATOS_GRAFICO_COSECHA_FORWARDER",
            success: function (data) {
                var data = eval('(' + data + ')');
                nombreMesesCosecha = data[0].listaMeses;
                horasMaquinaRealForwarderCosecha = data[0].data;
                horasMaquinaPlanForwarderCosecha = data[1].data;
                produccionRealForwarderCosecha = data[2].data;
                produccionPlanForwarderCosecha = data[3].data;
                productividadRealForwarderCosecha = data[4].data;
                dispobibilidadForwarderCosecha = data[5].data;
                eficienciaForwarderCosecha = data[6].data;
                capacidadForwarderCosecha = data[7].data;
                oeeForwarderCosecha = data[8].data;
                //GRAFICO DE LINEAS 1 = indicadoresOperacionalesForwarder
                var ctx1 = document.getElementById("indicadoresOperacionalesForwarder").getContext("2d");
                window.grafico1ForwarderCosecha = new Chart(ctx1).Line(lineaIndicadoresOperacionalesForwarderCosecha, {
                    responsive: true,
                });
                //GRAFICO DE LINEAS 2 = produccionProductividadForwarder
                var ctx2 = document.getElementById("produccionProductividadForwarder").getContext("2d");
                window.grafico2ForwarderCosecha = new Chart(ctx2).Line(lineaProduccionProductividadForwarderCosecha, {
                    responsive: true
                });

                //GRAFICO DE LINEA 4 = Rendimiento
                var ctx4 = document.getElementById("rendimientoForwarder").getContext("2d");
                window.grafico4ForwarderCosecha = new Chart(ctx4).Line(lineaRendimientoForwarderCosecha);

                //GRAFICO DE LINEAS 3 = HorasTrabajoForwarder
                var ctx3 = document.getElementById("horasMaquinaForwarder").getContext("2d");
                window.grafico3ForwarderCosecha = new Chart(ctx3).Line(lineaHoraTrabajoForwarderCosecha, {
                    responsive: true
                });
            }
        });
    }
    //GRAFICO INDICADORES OPERACIONALES FORWARDER
    var lineaIndicadoresOperacionalesForwarderCosecha = {
        labels: nombreMesesCosecha,
        datasets: [
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: oeeForwarderCosecha
            },
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: dispobibilidadForwarderCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: eficienciaForwarderCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(125,96,160,0)",
                strokeColor: "rgba(125,96,160,1)",
                pointColor: "rgba(125,96,160,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(125,96,160,1)",
                data: capacidadForwarderCosecha
            }
        ]
    }

    //GRAFICO PRODUCCION PRODUCTIVIDAD FORWARDER
    var lineaProduccionProductividadForwarderCosecha = {
        labels: nombreMesesCosecha,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: produccionRealForwarderCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: produccionPlanForwarderCosecha
            }
        ]
    }

    //GRAFICO RENDIMIENTO FORWARDER
    var lineaRendimientoForwarderCosecha = {
        labels: nombreMesesCosecha,
        datasets: [
            {
                label: "My Second dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: productividadRealForwarderCosecha
            }
        ]
    }

    //GRAFICO HORAS MAQUINA FORWARDER
    var lineaHoraTrabajoForwarderCosecha = {
        labels: nombreMesesCosecha,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: horasMaquinaRealForwarderCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: horasMaquinaPlanForwarderCosecha
            }
        ]
    }

    //HARVESTER
    var horasMaquinaRealHarvesterCosecha;
    var horasMaquinaPlanHarvesterCosecha;
    var nombreMesesHarvesterCosecha;
    var produccionRealHarvesterCosecha;
    var produccionPlanHarvesterCosecha;
    var productividadRealHarvesterCosecha;
    var dispobibilidadHarvesterCosecha;
    var eficienciaHarvesterCosecha;
    var capacidadHarvesterCosecha;
    var oeeHarvesterCosecha;
    function mostrarGraficosCosechaHarvester() {
        $.ajax({
            async: false,
            url: "administrarTableroCosecha",
            type: "post",
            data: "accion=DATOS_GRAFICO_COSECHA_HARVESTER",
            success: function (data) {
                var data = eval('(' + data + ')');
                nombreMesesHarvesterCosecha = data[0].listaMeses;
                horasMaquinaRealHarvesterCosecha = data[0].data;
                horasMaquinaPlanHarvesterCosecha = data[1].data;
                produccionRealHarvesterCosecha = data[2].data;
                produccionPlanHarvesterCosecha = data[3].data;
                productividadRealHarvesterCosecha = data[4].data;
                dispobibilidadHarvesterCosecha = data[5].data;
                eficienciaHarvesterCosecha = data[6].data;
                capacidadHarvesterCosecha = data[7].data;
                oeeHarvesterCosecha = data[8].data;

                //GRAFICO DE LINEAS 1 = indicadoresOperacionalesHarvester
                var ctx = document.getElementById("indicadoresOperacionalesHarvester").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineaIndicadoresOperacionalesHarvesterCosecha, {
                    responsive: true
                });
                //GRAFICO DE LINEAS 2 = produccionProductividadHarvester
                var ctx = document.getElementById("produccionProductividadHarvester").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineaProduccionProductividadHarvesterCosecha, {
                    responsive: true
                });
                
                //GRAFICO DE LINEA 4 = Rendimiento
                var ctx8 = document.getElementById("rendimientoHarvester").getContext("2d");
                window.grafico8HarvesterCosecha = new Chart(ctx8).Line(lineaRendimientoHarvesterCosecha);


                //GRAFICO DE LINEAS 3 = HorasTrabajoHarvester
                var ctx = document.getElementById("horasMaquinaHarvester").getContext("2d");
                window.myLine = new Chart(ctx).Line(lineaHoraTrabajoHarvesterCosecha, {
                    responsive: true
                });

            }
        });
    }
    //GRAFICO INDICADORES OPERACIONALES HARVESTER
    var lineaIndicadoresOperacionalesHarvesterCosecha = {
        labels: nombreMesesHarvesterCosecha,
        datasets: [
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: oeeHarvesterCosecha
            },
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: dispobibilidadHarvesterCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: eficienciaHarvesterCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(125,96,160,0)",
                strokeColor: "rgba(125,96,160,1)",
                pointColor: "rgba(125,96,160,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(125,96,160,1)",
                data: capacidadHarvesterCosecha
            }
        ]
    }

    //GRAFICO PRODUCCION PRODUCTIVIDAD HARVESTER
    var lineaProduccionProductividadHarvesterCosecha = {
        labels: nombreMesesHarvesterCosecha,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(152,185,84,0)",
                strokeColor: "rgba(152,185,84,1)",
                pointColor: "rgba(152,185,84,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(152,185,84,1)",
                data: produccionRealHarvesterCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: produccionPlanHarvesterCosecha
            }
        ]
    }

    //GRAFICO Rendimiento HARVESTER
    var lineaRendimientoHarvesterCosecha = {
        labels: nombreMesesHarvesterCosecha,
        datasets: [            
            {
                label: "My Second dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: productividadRealHarvesterCosecha
            }
        ]
    }

    //GRAFICO HORAS MAQUINA HARVESTER
    var lineaHoraTrabajoHarvesterCosecha = {
        labels: nombreMesesHarvesterCosecha,
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(190,75,72,0)",
                strokeColor: "rgba(190,75,72,1)",
                pointColor: "rgba(190,75,72,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(190,75,72,1)",
                data: horasMaquinaRealHarvesterCosecha
            },
            {
                label: "My Second dataset",
                fillColor: "rgba(74,126,187,0)",
                strokeColor: "rgba(74,126,187,1)",
                pointColor: "rgba(74,126,187,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(74,126,187,1)",
                data: horasMaquinaPlanHarvesterCosecha
            }
        ]
    }
</script>