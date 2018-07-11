package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.CabezalDTO;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorCabezal extends Controlador {

    public List<CabezalDTO> getCabezales(int pagina, int cantidad, String where) {
        ArrayList<CabezalDTO> retorno = new ArrayList<CabezalDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT C.idCabezal, C.patente, C.modelo, C.n_chasis, C.año, C.horometro, C.fecha_registro, C.estado "
                    + " FROM cabezal C " + where, pagina, cantidad, " C.patente ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    CabezalDTO cabezal = new CabezalDTO();
                    cabezal.setIdCabezal(res.getInt("idCabezal"));
                    cabezal.setPatente(res.getString("patente"));
                    cabezal.setModelo(res.getString("modelo"));
                    cabezal.setNumeroChasis(res.getString("n_chasis"));
                    cabezal.setAño(res.getInt("año"));
                    cabezal.setHorometro(res.getInt("horometro"));
                    cabezal.setFechaRegistro(res.getDate("fecha_registro"));
                    cabezal.setEstado(res.getInt("estado"));

                    retorno.add(cabezal);

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

    public List<CabezalDTO> getAllCabezales(String where) {
        ArrayList<CabezalDTO> retorno = new ArrayList<CabezalDTO>();
        try {
            String sql = "SELECT C.idCabezal, C.patente, C.modelo, C.n_chasis, C.año, C.horometro, C.fecha_registro, C.estado "
                    + " FROM cabezal C WHERE 1 = 1 AND C.idCabezal != 1" + where+ " ORDER BY C.patente ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    CabezalDTO cabezal = new CabezalDTO();
                    cabezal.setIdCabezal(res.getInt("idCabezal"));
                    cabezal.setPatente(res.getString("patente"));
                    cabezal.setModelo(res.getString("modelo"));
                    cabezal.setNumeroChasis(res.getString("n_chasis"));
                    cabezal.setAño(res.getInt("año"));
                    cabezal.setHorometro(res.getInt("horometro"));
                    cabezal.setFechaRegistro(res.getDate("fecha_registro"));
                    cabezal.setEstado(res.getInt("estado"));

                    retorno.add(cabezal);

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
    
    public int updateMaquina(CabezalDTO cabezal) {
        int res = conector.executeUpdate("UPDATE cabezal SET patente= ?,modelo= ?,n_chasis= ?,año= ?, horometro= ?, estado = ? WHERE idCabezal = ? ", cabezal.getPatente(), cabezal.getModelo(), cabezal.getNumeroChasis(), cabezal.getAño(), cabezal.getHorometro(), cabezal.getEstado(), cabezal.getIdCabezal());
        return res;
    }

    public int removeMaquina(CabezalDTO cabezal) {
        int res = conector.executeUpdate("DELETE FROM cabezal WHERE idCabezal = ?", cabezal.getIdCabezal());
        return res;
    }

    public int saveMaquina(CabezalDTO cabezal) {
        int res = conector.executeInsert("INSERT INTO cabezal(patente, modelo, n_chasis, año, horometro,fecha_registro, estado) VALUES (?,?,?,?,?,now(),?) ", cabezal.getPatente(), cabezal.getModelo(), cabezal.getNumeroChasis(), cabezal.getAño(), cabezal.getHorometro(), cabezal.getEstado());
        return res;
    }

    public CabezalDTO getByIdCabezal(Integer idCabezal) {
        CabezalDTO cabezal = null;
        try {
            String sql = "SELECT C.idCabezal, C.patente, C.modelo, C.n_chasis, C.año, C.horometro, C.fecha_registro, C.estado "
                    + " FROM cabezal C WHERE 1 = 1 AND C.idCabezal = " + idCabezal;//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    cabezal = new CabezalDTO();
                    cabezal.setIdCabezal(res.getInt("idCabezal"));
                    cabezal.setPatente(res.getString("patente"));
                    cabezal.setModelo(res.getString("modelo"));
                    cabezal.setNumeroChasis(res.getString("n_chasis"));
                    cabezal.setAño(res.getInt("año"));
                    cabezal.setHorometro(res.getInt("horometro"));
                    cabezal.setFechaRegistro(res.getDate("fecha_registro"));
                    cabezal.setEstado(res.getInt("estado"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return cabezal;
    }

    public CabezalDTO getByPatente(String patente) {
        CabezalDTO cabezal = null;
        try {
            String sql = "SELECT C.idCabezal, C.patente, C.modelo, C.n_chasis, C.año, C.horometro, C.fecha_registro, C.estado "
                    + " FROM cabezal C WHERE 1 = 1 AND C.patente = '" + patente + "'";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    cabezal = new CabezalDTO();
                    cabezal.setIdCabezal(res.getInt("idCabezal"));
                    cabezal.setPatente(res.getString("patente"));
                    cabezal.setModelo(res.getString("modelo"));
                    cabezal.setNumeroChasis(res.getString("n_chasis"));
                    cabezal.setAño(res.getInt("año"));
                    cabezal.setHorometro(res.getInt("horometro"));
                    cabezal.setFechaRegistro(res.getDate("fecha_registro"));
                    cabezal.setEstado(res.getInt("estado"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return cabezal;
    }
    
    public CabezalDTO getByNumeroChasis(String numeroChasis) {
        CabezalDTO cabezal = null;
        try {
            String sql = "SELECT C.idCabezal, C.patente, C.modelo, C.n_chasis, C.año, C.horometro, C.fecha_registro, C.estado "
                    + " FROM cabezal C WHERE 1 = 1 AND C.n_chasis = '" + numeroChasis + "'";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    cabezal = new CabezalDTO();
                    cabezal.setIdCabezal(res.getInt("idCabezal"));
                    cabezal.setPatente(res.getString("patente"));
                    cabezal.setModelo(res.getString("modelo"));
                    cabezal.setNumeroChasis(res.getString("n_chasis"));
                    cabezal.setAño(res.getInt("año"));
                    cabezal.setHorometro(res.getInt("horometro"));
                    cabezal.setFechaRegistro(res.getDate("fecha_registro"));
                    cabezal.setEstado(res.getInt("estado"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return cabezal;
    }
}
