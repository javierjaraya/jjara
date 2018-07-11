package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.FaenaDTO;
import cl.forestcenter.sistema.dto.PlanificacionDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorMetaProduccionFaena extends Controlador {

    public List<PlanificacionDTO> getPlanificacion(int pagina, int cantidad, String where) {
        ArrayList<PlanificacionDTO> retorno = new ArrayList<PlanificacionDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT p.idPlanificacion, p.fechaInicio, p.fechaTermino, p.estado, "
                    + " f.idFaena, f.numeroTeam, tf.idTipoFaena, tf.nombre as tipoFaena,  f.jefeFaena, f.calibrador "
                    + " FROM planificacion p "
                    + " JOIN faena f ON f.idFaena = p.idFaena "
                    + " JOIN tipo_faena tf ON f.idTipoFaena = tf.idTipoFaena " + where, pagina, cantidad, " p.fechaInicio ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    PlanificacionDTO planificacion = new PlanificacionDTO();
                    planificacion.setIdPlanificacion(res.getInt("idPlanificacion"));
                    planificacion.setFechaInicio(res.getDate("fechaInicio"));
                    planificacion.setFechaTermino(res.getDate("fechaTermino"));
                    planificacion.setEstado(res.getInt("estado"));
                    planificacion.setIdFaena(res.getInt("idFaena"));

                    FaenaDTO faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                    retorno.add(planificacion);

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

    public int updatePlanificacion(PlanificacionDTO planificacion) {
        int res = conector.executeUpdate("UPDATE planificacion SET fechaInicio= ?, fechaTermino= ?, estado= ?, idFaena= ? WHERE idPlanificacion = ? ", planificacion.getFechaInicio(), planificacion.getFechaTermino(), planificacion.getEstado(), planificacion.getIdFaena(), planificacion.getIdPlanificacion());
        return res;
    }

    public int removePlanificacion(Integer idPlanificacion) {
        int res = conector.executeUpdate("DELETE FROM planificacion WHERE idPlanificacion = ?", idPlanificacion);
        return res;
    }

    public int savePlanificacion(PlanificacionDTO planificacion) {
        int res = conector.executeInsert("INSERT INTO planificacion(fechaInicio, fechaTermino, estado, idFaena) VALUES ('" + planificacion.getFechaInicio() + "','" + planificacion.getFechaTermino() + "'," + planificacion.getEstado() + "," + planificacion.getIdFaena() + ")");
        return res;
    }

    public PlanificacionDTO getPlanificacionByID(int idPlanificacion) {
        PlanificacionDTO planificacion = new PlanificacionDTO();
        try {
            String sql = "SELECT p.idPlanificacion, p.fechaInicio, p.fechaTermino, p.estado, "
                    + " f.idFaena, f.numeroTeam, tf.idTipoFaena, tf.nombre as tipoFaena,  f.jefeFaena, f.calibrador "
                    + " FROM planificacion p "
                    + " JOIN faena f ON f.idFaena = p.idFaena "
                    + " JOIN tipo_faena tf ON f.idTipoFaena = tf.idTipoFaena WHERE p.idPlanificacion = " + idPlanificacion;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    planificacion = new PlanificacionDTO();
                    planificacion.setIdPlanificacion(res.getInt("idPlanificacion"));
                    planificacion.setFechaInicio(res.getDate("fechaInicio"));
                    planificacion.setFechaTermino(res.getDate("fechaTermino"));
                    planificacion.setEstado(res.getInt("estado"));
                    planificacion.setIdFaena(res.getInt("idFaena"));

                    FaenaDTO faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return planificacion;
    }

    public PlanificacionDTO getPlanificacionByFaenaFechas(int idFaena, Date fechaInicio, Date fechaTermino) {
        PlanificacionDTO planificacion = null;
        try {
            String sql = "SELECT P.idPlanificacion, P.fechaInicio, P.fechaTermino, P.estado, P.idFaena, "
                    + " f.idFaena, f.numeroTeam, tf.idTipoFaena, tf.nombre as tipoFaena,  f.jefeFaena, f.calibrador "
                    + " FROM planificacion P "
                    + " JOIN faena f ON f.idFaena = P.idFaena "
                    + " JOIN tipo_faena tf ON f.idTipoFaena = tf.idTipoFaena "
                    + " WHERE P.idFaena = " + idFaena
                    + " AND (P.fechaInicio <= '" + fechaInicio + "' AND P.fechaTermino >= '" + fechaInicio + "' "
                    + " OR P.fechaInicio <= '" + fechaTermino + "' AND P.fechaTermino >= '" + fechaTermino + "' "
                    + " OR P.fechaInicio >= '" + fechaInicio + "' AND P.fechaTermino <= '" + fechaTermino + "') ";

            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    planificacion = new PlanificacionDTO();
                    planificacion.setIdPlanificacion(res.getInt("idPlanificacion"));
                    planificacion.setFechaInicio(res.getDate("fechaInicio"));
                    planificacion.setFechaTermino(res.getDate("fechaTermino"));
                    planificacion.setEstado(res.getInt("estado"));
                    planificacion.setIdFaena(res.getInt("idFaena"));

                    FaenaDTO faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return planificacion;
    }

    public PlanificacionDTO getPlanificacionByFaenaFechasDistintaPlanificacion(int idPlanificacion, int idFaena, Date fechaInicio, Date fechaTermino) {
        PlanificacionDTO planificacion = null;
        try {
            String sql = "SELECT P.idPlanificacion, P.fechaInicio, P.fechaTermino, P.estado, P.idFaena, "
                    + " f.idFaena, f.numeroTeam, tf.idTipoFaena, tf.nombre as tipoFaena,  f.jefeFaena, f.calibrador "
                    + " FROM planificacion P "
                    + " JOIN faena f ON f.idFaena = p.idFaena "
                    + " JOIN tipo_faena tf ON f.idTipoFaena = tf.idTipoFaena "
                    + " WHERE P.idFaena = " + idFaena + " AND  P.idPlanificacion != " + idPlanificacion
                    + " AND (P.fechaInicio <= '" + fechaInicio + "' AND P.fechaTermino >= '" + fechaInicio + "' "
                    + " OR P.fechaInicio <= '" + fechaTermino + "' AND P.fechaTermino >= '" + fechaTermino + "' "
                    + " OR P.fechaInicio >= '" + fechaInicio + "' AND P.fechaTermino <= '" + fechaTermino + "') ";

            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    planificacion = new PlanificacionDTO();
                    planificacion.setIdPlanificacion(res.getInt("idPlanificacion"));
                    planificacion.setFechaInicio(res.getDate("fechaInicio"));
                    planificacion.setFechaTermino(res.getDate("fechaTermino"));
                    planificacion.setEstado(res.getInt("estado"));
                    planificacion.setIdFaena(res.getInt("idFaena"));

                    FaenaDTO faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                    planificacion.setDescripcionFaena(faena.getDescripcionFaena());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return planificacion;
    }
}
