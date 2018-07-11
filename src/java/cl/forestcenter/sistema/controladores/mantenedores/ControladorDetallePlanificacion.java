package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.DetallePlanificacionDTO;
import cl.forestcenter.sistema.dto.MetaActualDTO;
import cl.forestcenter.sistema.dto.PredioDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorDetallePlanificacion extends Controlador {

    public List<DetallePlanificacionDTO> getDetallePlanificacion(int pagina, int cantidad, String where) {
        ArrayList<DetallePlanificacionDTO> retorno = new ArrayList<DetallePlanificacionDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT dp.idDetallePlanificacion,dp.numeroActa, dp.fechaInicio, dp.fechaTermino, TA.idTipoArbol, TA.nombre AS tipoArbol, dp.metrosCubicos, dp.estado, dp.idPlanificacion, p.idPredio, p.nombre AS nombrePredio "
                    + " FROM detalle_planificacion dp "
                    + " JOIN predio p ON p.idPredio = dp.idPredio "
                    + " JOIN tipo_arbol TA ON dp.idTipoArbol = TA.idTipoArbol " + where, pagina, cantidad, " dp.fechaInicio ASC");//DESC y ASC

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    DetallePlanificacionDTO detalle = new DetallePlanificacionDTO();
                    detalle.setIdDetallePlanificacion(res.getInt("idDetallePlanificacion"));
                    detalle.setNumeroActa(res.getInt("numeroActa"));
                    detalle.setFechaInicioDetalle(res.getDate("fechaInicio"));
                    detalle.setFechaTerminoDetalle(res.getDate("fechaTermino"));
                    detalle.setIdTipoArbol(res.getInt("idTipoArbol"));
                    detalle.setTipoArbol(res.getString("tipoArbol"));
                    detalle.setMetrosCubicos(res.getDouble("metrosCubicos"));
                    detalle.setEstadoDetalle(res.getInt("estado"));
                    detalle.setIdPlanificacionFaena(res.getInt("idPlanificacion"));
                    detalle.setIdPredio(res.getInt("idPredio"));
                    detalle.setNombrePredio(res.getString("nombrePredio"));

                    retorno.add(detalle);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return retorno;
    }

    public int updateDetallePlanificacion(DetallePlanificacionDTO detallePlanificacion) {
        int res = conector.executeUpdate("UPDATE detalle_planificacion SET numeroActa = ?, fechaInicio = ?,fechaTermino = ?,idTipoArbol = ?,metrosCubicos = ?,estado = ?,idPredio = ?,idPlanificacion = ? WHERE idDetallePlanificacion = ?",
                detallePlanificacion.getNumeroActa(),
                detallePlanificacion.getFechaInicioDetalle(),
                detallePlanificacion.getFechaTerminoDetalle(),
                detallePlanificacion.getIdTipoArbol(),
                detallePlanificacion.getMetrosCubicos(),
                detallePlanificacion.getEstadoDetalle(),
                detallePlanificacion.getIdPredio(),
                detallePlanificacion.getIdPlanificacionFaena(),
                detallePlanificacion.getIdDetallePlanificacion());
        return res;
    }

    public int removeDetallePlanificacion(Integer idDetallePlanificacion) {
        int res = conector.executeUpdate("DELETE FROM detalle_planificacion WHERE idDetallePlanificacion = ?", idDetallePlanificacion);
        return res;
    }

    public int agregarDetallePlanificacion(DetallePlanificacionDTO detallePlanificacion) {
        int res = conector.executeInsert("INSERT INTO detalle_planificacion (numeroActa, fechaInicio, fechaTermino, idTipoArbol, metrosCubicos, estado, idPredio, idPlanificacion) VALUES (?,?,?,?,?,?,?,?)",
                detallePlanificacion.getNumeroActa(),
                detallePlanificacion.getFechaInicioDetalle(),
                detallePlanificacion.getFechaTerminoDetalle(),
                detallePlanificacion.getIdTipoArbol(),
                detallePlanificacion.getMetrosCubicos(),
                detallePlanificacion.getEstadoDetalle(),
                detallePlanificacion.getIdPredio(),
                detallePlanificacion.getIdPlanificacionFaena());
        return res;
    }

    public DetallePlanificacionDTO getDetalleByPlanificacionFechas(Integer idPlanificacion, Date fechaInicio, Date fechaTermino) {
        DetallePlanificacionDTO detalle = null;
        try {
            String sql = "SELECT dp.idDetallePlanificacion,dp.numeroActa, dp.fechaInicio, dp.fechaTermino, TA.idTipoArbol, TA.nombre AS tipoArbol, dp.metrosCubicos, dp.estado, dp.idPlanificacion, p.idPredio, p.nombre AS nombrePredio "
                    + " FROM detalle_planificacion dp "
                    + " JOIN predio p ON p.idPredio = dp.idPredio "
                    + " JOIN tipo_arbol TA ON dp.idTipoArbol = TA.idTipoArbol "
                    + " WHERE dp.idPlanificacion = " + idPlanificacion
                    + " AND (dp.fechaInicio <= '" + fechaInicio + "' AND dp.fechaTermino >= '" + fechaInicio + "' "
                    + " OR dp.fechaInicio <= '" + fechaTermino + "' AND dp.fechaTermino >= '" + fechaTermino + "' "
                    + " OR dp.fechaInicio >= '" + fechaInicio + "' AND dp.fechaTermino <= '" + fechaTermino + "') ";

            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    detalle = new DetallePlanificacionDTO();
                    detalle.setIdDetallePlanificacion(res.getInt("idDetallePlanificacion"));
                    detalle.setNumeroActa(res.getInt("numeroActa"));
                    detalle.setFechaInicioDetalle(res.getDate("fechaInicio"));
                    detalle.setFechaTerminoDetalle(res.getDate("fechaTermino"));
                    detalle.setIdTipoArbol(res.getInt("idTipoArbol"));
                    detalle.setTipoArbol(res.getString("tipoArbol"));
                    detalle.setMetrosCubicos(res.getDouble("metrosCubicos"));
                    detalle.setEstadoDetalle(res.getInt("estado"));
                    detalle.setIdPlanificacionFaena(res.getInt("idPlanificacion"));
                    detalle.setIdPredio(res.getInt("idPredio"));
                    detalle.setNombrePredio(res.getString("nombrePredio"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return detalle;
    }

    public DetallePlanificacionDTO getDetalleByPlanificacionFechasDistinDetalle(Integer idPlanificacion, Integer idDetalle, Date fechaInicio, Date fechaTermino) {
        DetallePlanificacionDTO detalle = null;
        try {
            String sql = "SELECT dp.idDetallePlanificacion,dp.numeroActa, dp.fechaInicio, dp.fechaTermino, TA.idTipoArbol, TA.nombre AS tipoArbol, dp.metrosCubicos, dp.estado, dp.idPlanificacion, p.idPredio, p.nombre AS nombrePredio "
                    + " FROM detalle_planificacion dp "
                    + " JOIN predio p ON p.idPredio = dp.idPredio "
                    + " JOIN tipo_arbol TA ON dp.idTipoArbol = TA.idTipoArbol "
                    + " WHERE dp.idPlanificacion = " + idPlanificacion + " AND dp.idDetallePlanificacion != " + idDetalle
                    + " AND (dp.fechaInicio <= '" + fechaInicio + "' AND dp.fechaTermino >= '" + fechaInicio + "' "
                    + " OR dp.fechaInicio <= '" + fechaTermino + "' AND dp.fechaTermino >= '" + fechaTermino + "' "
                    + " OR dp.fechaInicio >= '" + fechaInicio + "' AND dp.fechaTermino <= '" + fechaTermino + "') ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    detalle = new DetallePlanificacionDTO();
                    detalle.setIdDetallePlanificacion(res.getInt("idDetallePlanificacion"));
                    detalle.setNumeroActa(res.getInt("numeroActa"));
                    detalle.setFechaInicioDetalle(res.getDate("fechaInicio"));
                    detalle.setFechaTerminoDetalle(res.getDate("fechaTermino"));
                    detalle.setIdTipoArbol(res.getInt("idTipoArbol"));
                    detalle.setTipoArbol(res.getString("tipoArbol"));
                    detalle.setMetrosCubicos(res.getDouble("metrosCubicos"));
                    detalle.setEstadoDetalle(res.getInt("estado"));
                    detalle.setIdPlanificacionFaena(res.getInt("idPlanificacion"));
                    detalle.setIdPredio(res.getInt("idPredio"));
                    detalle.setNombrePredio(res.getString("nombrePredio"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return detalle;
    }

    public DetallePlanificacionDTO getDetalleByIdDetalle(Integer idDetallePlanificacion) {
        DetallePlanificacionDTO detalle = null;
        try {
            String sql = "SELECT dp.idDetallePlanificacion,dp.numeroActa, dp.fechaInicio, dp.fechaTermino, TA.idTipoArbol, TA.nombre AS tipoArbol, dp.metrosCubicos, dp.estado, dp.idPlanificacion, p.idPredio, p.nombre AS nombrePredio "
                    + " FROM detalle_planificacion dp "
                    + " JOIN predio p ON p.idPredio = dp.idPredio "
                    + " JOIN tipo_arbol TA ON dp.idTipoArbol = TA.idTipoArbol "
                    + " WHERE dp.idDetallePlanificacion = " + idDetallePlanificacion;

            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    detalle = new DetallePlanificacionDTO();
                    detalle.setIdDetallePlanificacion(res.getInt("idDetallePlanificacion"));
                    detalle.setNumeroActa(res.getInt("numeroActa"));
                    detalle.setFechaInicioDetalle(res.getDate("fechaInicio"));
                    detalle.setFechaTerminoDetalle(res.getDate("fechaTermino"));
                    detalle.setIdTipoArbol(res.getInt("idTipoArbol"));
                    detalle.setTipoArbol(res.getString("tipoArbol"));
                    detalle.setMetrosCubicos(res.getDouble("metrosCubicos"));
                    detalle.setEstadoDetalle(res.getInt("estado"));
                    detalle.setIdPlanificacionFaena(res.getInt("idPlanificacion"));
                    detalle.setIdPredio(res.getInt("idPredio"));
                    detalle.setNombrePredio(res.getString("nombrePredio"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return detalle;
    }

    public List<MetaActualDTO> getMetasActivasHoy() {
        List<MetaActualDTO> metasActuales = new ArrayList<MetaActualDTO>();
        try {
            String sql = "SELECT P.idPlanificacion, P.idFaena, CONCAT(TF.Nombre,' ',F.numeroTeam) as descripcionFaena, D.numeroActa, D.fechaInicio, D.fechaTermino, DATEDIFF(D.fechaTermino,D.fechaInicio) as TotalDias, DATEDIFF(now(),D.fechaInicio) as DiasTranscurridos, TA.idTipoArbol,TA.nombre as nombreTipoArbol, D.metrosCubicos,D.idPredio, PR.nombre AS nombrePredio "
                    + " FROM planificacion P "
                    + " JOIN detalle_planificacion D ON P.idPlanificacion = D.idPlanificacion "
                    + " JOIN tipo_arbol TA ON D.idTipoArbol = TA.idTipoArbol "
                    + " JOIN faena F ON P.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " JOIN predio PR ON D.idPredio = PR.idPredio "
                    + " WHERE P.estado = 1 "
                    + " AND D.estado = 1 "
                    + " AND D.fechaInicio <= now() "
                    + " AND D.fechaTermino >= now(); ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    MetaActualDTO metaActual = new MetaActualDTO();
                    metaActual.setIdPlanificacion(res.getInt("idPlanificacion"));
                    metaActual.setIdFaena(res.getInt("idFaena"));
                    metaActual.setDescripcionFaena(res.getString("descripcionFaena"));
                    metaActual.setNumeroActa(res.getInt("numeroActa"));
                    metaActual.setFechaInicio(res.getDate("fechaInicio"));
                    metaActual.setFechaTermino(res.getDate("fechaTermino"));
                    metaActual.setTotalDias(res.getInt("TotalDias"));
                    metaActual.setDiasTranscurridos(res.getInt("DiasTranscurridos"));
                    metaActual.setIdTipoArbol(res.getInt("idTipoArbol"));
                    metaActual.setNombreTipoArbol(res.getString("nombreTipoArbol"));
                    metaActual.setMetrosCubicos(res.getDouble("metrosCubicos"));                    
                    metaActual.setIdPredio(res.getInt("idPredio"));
                    metaActual.setNombrePredio(res.getString("nombrePredio"));

                    metasActuales.add(metaActual);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return metasActuales;
    }
}
