package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.FaenaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorFaena extends Controlador {

    public List<FaenaDTO> getFaenas(int pagina, int cantidad, String where) {
        ArrayList<FaenaDTO> retorno = new ArrayList<FaenaDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT F.idFaena, F.numeroTeam, TF.idTipoFaena, TF.nombre AS tipoFaena, E1.idEmpleado AS idJefeFaena, E2.idEmpleado AS idCalibrador, "
                    + " concat_ws(' ', E1.nombres, E1.apellidos) as nombreJefeFaena, "
                    + " concat_ws(' ', E2.nombres, E2.apellidos) as nombreCalibrador "
                    + " FROM faena F "
                    + " JOIN empleado E1 ON F.jefeFaena = E1.idEmpleado "
                    + " JOIN empleado E2 ON F.calibrador = E2.idEmpleado "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena " + where, pagina, cantidad, " F.idFaena ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    FaenaDTO faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));
                    faena.setIdJefeFaena(res.getInt("idJefeFaena"));
                    faena.setIdCalibrador(res.getInt("idCalibrador"));
                    
                    faena.setNombreJefeFaena(res.getString("nombreJefeFaena"));
                    faena.setNombreCalibrador(res.getString("nombreCalibrador"));

                    retorno.add(faena);

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
    
    public List<FaenaDTO> getAllFaenas(String where) {
        ArrayList<FaenaDTO> retorno = new ArrayList<FaenaDTO>();
        try {
            String sql = "SELECT F.idFaena, F.numeroTeam, TF.idTipoFaena, TF.nombre AS tipoFaena, E1.idEmpleado AS idJefeFaena, E2.idEmpleado AS idCalibrador, "
                    + " concat_ws(' ', E1.nombres, E1.apellidos) as nombreJefeFaena, "
                    + " concat_ws(' ', E2.nombres, E2.apellidos) as nombreCalibrador "
                    + " FROM faena F "
                    + " JOIN empleado E1 ON F.jefeFaena = E1.idEmpleado "
                    + " JOIN empleado E2 ON F.calibrador = E2.idEmpleado "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena " + where+ " ORDER BY F.idFaena ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    FaenaDTO faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));
                    faena.setIdJefeFaena(res.getInt("idJefeFaena"));
                    faena.setIdCalibrador(res.getInt("idCalibrador"));
                    
                    faena.setNombreJefeFaena(res.getString("nombreJefeFaena"));
                    faena.setNombreCalibrador(res.getString("nombreCalibrador"));

                    retorno.add(faena);

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

    public int updateFaena(FaenaDTO faena) {
        int res = conector.executeUpdate("UPDATE faena SET numeroTeam = ?,idTipoFaena = ?,jefeFaena = ?,calibrador = ? WHERE idFaena = ? ", faena.getNumeroTeam(), faena.getIdTipoFaena(), faena.getIdJefeFaena(), faena.getIdCalibrador(), faena.getIdFaena());
        return res;
    }

    public int removeFaena(Integer idFaena) {
        int res = conector.executeUpdate("DELETE FROM faena WHERE idFaena = ?", idFaena);
        return res;
    }

    public int saveFaena(FaenaDTO faena) {//Retorna el ID asignado
        int res = conector.executeInsert("INSERT INTO faena (numeroTeam, idTipoFaena, jefeFaena, calibrador) VALUES (?,?,?,?) ", faena.getNumeroTeam(), faena.getIdTipoFaena(), faena.getIdJefeFaena(), faena.getIdCalibrador());
        return res;
    }

    public FaenaDTO getFaenaByIDCalibrador(Integer idCalibrador) {
        FaenaDTO faena = null;
        try {
            String sql = "SELECT F.idFaena, F.numeroTeam, TF.idTipoFaena, TF.nombre AS tipoFaena, E1.idEmpleado AS idJefeFaena, E2.idEmpleado AS idCalibrador, "
                    + " concat_ws(' ', E1.nombres, E1.apellidos) as nombreJefeFaena, "
                    + " concat_ws(' ', E2.nombres, E2.apellidos) as nombreCalibrador "
                    + " FROM faena F "
                    + " JOIN empleado E1 ON F.jefeFaena = E1.idEmpleado "
                    + " JOIN empleado E2 ON F.calibrador = E2.idEmpleado "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " WHERE E2.idEmpleado = " + idCalibrador;
            
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));
                    faena.setIdJefeFaena(res.getInt("idJefeFaena"));
                    faena.setIdCalibrador(res.getInt("idCalibrador"));
                    
                    faena.setNombreJefeFaena(res.getString("nombreJefeFaena"));
                    faena.setNombreCalibrador(res.getString("nombreCalibrador"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return faena;
    }

    public FaenaDTO getFaenaByIDJefeFaena(Integer idJefeFaena) {
        FaenaDTO faena = null;
        try {
            String sql = "SELECT F.idFaena, F.numeroTeam, TF.idTipoFaena, TF.nombre AS tipoFaena, E1.idEmpleado AS idJefeFaena, E2.idEmpleado AS idCalibrador, "
                    + " concat_ws(' ', E1.nombres, E1.apellidos) as nombreJefeFaena, "
                    + " concat_ws(' ', E2.nombres, E2.apellidos) as nombreCalibrador "
                    + " FROM faena F "
                    + " JOIN empleado E1 ON F.jefeFaena = E1.idEmpleado "
                    + " JOIN empleado E2 ON F.calibrador = E2.idEmpleado "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " WHERE E1.idEmpleado = " + idJefeFaena;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));
                    faena.setIdJefeFaena(res.getInt("idJefeFaena"));
                    faena.setIdCalibrador(res.getInt("idCalibrador"));
                    
                    faena.setNombreJefeFaena(res.getString("nombreJefeFaena"));
                    faena.setNombreCalibrador(res.getString("nombreCalibrador"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return faena;
    }

    public FaenaDTO getFaenaByID(Integer idFaena) {
        FaenaDTO faena = null;
        try {
            String sql = "SELECT F.idFaena, F.numeroTeam, TF.idTipoFaena, TF.nombre AS tipoFaena, E1.idEmpleado AS idJefeFaena, E2.idEmpleado AS idCalibrador, "
                    + " concat_ws(' ', E1.nombres, E1.apellidos) as nombreJefeFaena, "
                    + " concat_ws(' ', E2.nombres, E2.apellidos) as nombreCalibrador "
                    + " FROM faena F "
                    + " JOIN empleado E1 ON F.jefeFaena = E1.idEmpleado "
                    + " JOIN empleado E2 ON F.calibrador = E2.idEmpleado "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " WHERE F.idFaena = " + idFaena;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    faena = new FaenaDTO();
                    faena.setIdFaena(res.getInt("idFaena"));
                    faena.setNumeroTeam(res.getInt("numeroTeam"));
                    faena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    faena.setTipoFaena(res.getString("tipoFaena"));
                    faena.setIdJefeFaena(res.getInt("idJefeFaena"));
                    faena.setIdCalibrador(res.getInt("idCalibrador"));
                    
                    faena.setNombreJefeFaena(res.getString("nombreJefeFaena"));
                    faena.setNombreCalibrador(res.getString("nombreCalibrador"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return faena;
    }
}
