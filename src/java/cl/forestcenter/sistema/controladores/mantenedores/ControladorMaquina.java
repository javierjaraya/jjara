package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.MaquinaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorMaquina extends Controlador {

    public List<MaquinaDTO> getMaquinas(int pagina, int cantidad, String where) {
        ArrayList<MaquinaDTO> retorno = new ArrayList<MaquinaDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT M.idMaquina, M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal " + where, pagina, cantidad, " M.codigoMaquina ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    MaquinaDTO maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    if (maquina.getIdCabezal() != 1) {
                        maquina.setCabezal(res.getString("cabezal"));
                    } else {
                        maquina.setCabezal("No Tiene");
                    }
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));

                    retorno.add(maquina);

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
    
    public List<MaquinaDTO> getAllMaquinas(String where) {
        ArrayList<MaquinaDTO> retorno = new ArrayList<MaquinaDTO>();
        try {
            String sql = "SELECT M.idMaquina, M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.estado = 1 " + where +" ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    MaquinaDTO maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    if (maquina.getIdCabezal() != 1) {
                        maquina.setCabezal(res.getString("cabezal"));
                    } else {
                        maquina.setCabezal("No Tiene");
                    }
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));

                    retorno.add(maquina);

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

    public int updateMaquina(MaquinaDTO maquina) {
        int res = conector.executeUpdate("UPDATE maquina SET codigoMaquina = ?, codigoForestal = ?, patente= ?,modelo= ?,numeroChasis= ?, numeroMotor = ?, año= ?, horometro = ?,  idtipoMaquina= ?, idCabezal = ?, estado = ? WHERE idMaquina = ? ", maquina.getCodigoMaquina(), maquina.getCodigoForestal(), maquina.getPatente(), maquina.getModelo(), maquina.getNumeroChasis(), maquina.getNumeroMotor(), maquina.getAño(), maquina.getHorometro(), maquina.getIdTipoMaquina(), maquina.getIdCabezal(), maquina.getEstado(), maquina.getIdMaquina());
        return res;
    }

    public int removeMaquina(Integer idMaquina) {
        int res = conector.executeUpdate("DELETE FROM maquina WHERE idMaquina = ?", idMaquina);
        return res;
    }

    public int agregarMaquina(MaquinaDTO maquina) {
        int res = conector.executeInsert("INSERT INTO maquina(codigoMaquina, codigoForestal, patente, modelo, numeroChasis,numeroMotor, año,horometro, idtipoMaquina,idCabezal,fechaRegistro, estado) VALUES (?,?,?,?,?,?,?,?,?,?,now(),?) ", maquina.getCodigoMaquina(), maquina.getCodigoForestal(), maquina.getPatente(), maquina.getModelo(), maquina.getNumeroChasis(), maquina.getNumeroMotor(), maquina.getAño(), maquina.getHorometro(), maquina.getIdTipoMaquina(), maquina.getIdCabezal(), maquina.getEstado());
        return res;
    }

    public MaquinaDTO getByIdMaquina(Integer idMaquina) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.idMaquina = " + idMaquina + " ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }

    public MaquinaDTO getByCodigoMaquina(String codigoMaquina) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.codigoMaquina = '" + codigoMaquina + "' ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }

    public MaquinaDTO getByCodigoForestal(String codigoForestal) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.codigoForestal = '" + codigoForestal + "' ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }

    public MaquinaDTO getByPatente(String patente) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.patente = '" + patente + "' ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }

    public MaquinaDTO getByNumeroChasis(String numeroChasis) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.numeroChasis = '" + numeroChasis + "' ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }

    public MaquinaDTO getByNumeroMotor(String numeroMotor) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE M.numeroMotor = '" + numeroMotor + "' ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }

    public MaquinaDTO getByIdCabezal(Integer idCabezal) {
        MaquinaDTO maquina = null;
        try {
            String sql = "SELECT M.idMaquina,M.codigoMaquina, M.codigoForestal, M.patente, M.modelo, M.numeroChasis, M.numeroMotor, M.año, M.horometro, TM.idtipoMaquina, TM.nombre AS tipoMaquina, C.idCabezal, concat_ws(' - ', C.patente, C.modelo) AS cabezal, M.fechaRegistro, M.estado "
                    + " FROM maquina M "
                    + " JOIN tipo_maquina TM ON M.idtipoMaquina = TM.idtipoMaquina "
                    + " JOIN cabezal C ON M.idCabezal = C.idCabezal WHERE C.idCabezal = " + idCabezal + " ORDER BY M.codigoMaquina ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    maquina = new MaquinaDTO();
                    maquina.setIdMaquina(res.getInt("idMaquina"));
                    maquina.setCodigoMaquina(res.getString("codigoMaquina"));
                    maquina.setCodigoForestal(res.getString("codigoForestal"));
                    maquina.setPatente(res.getString("patente"));
                    maquina.setModelo(res.getString("modelo"));
                    maquina.setNumeroChasis(res.getString("numeroChasis"));
                    maquina.setNumeroMotor(res.getString("numeroMotor"));
                    maquina.setAño(res.getInt("año"));
                    maquina.setHorometro(res.getDouble("horometro"));
                    maquina.setIdTipoMaquina(res.getInt("idtipoMaquina"));
                    maquina.setTipoMaquina(res.getString("tipoMaquina"));
                    maquina.setIdCabezal(res.getInt("idCabezal"));
                    maquina.setCabezal(res.getString("cabezal"));
                    maquina.setFechaRegistro(res.getDate("fechaRegistro"));
                    maquina.setEstado(res.getInt("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return maquina;
    }
}
