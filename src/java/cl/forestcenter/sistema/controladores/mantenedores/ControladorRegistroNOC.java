package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.PredioDTO;
import cl.forestcenter.sistema.dto.RegistroForwarderDTO;
import cl.forestcenter.sistema.dto.RegistroNocDTO;
import cl.forestcenter.sistema.dto.TablaResumenMesForwarderDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorRegistroNOC extends Controlador {

    public int cantidadRegistrosNOC(String where) {
        int cantidad = 0;
        try {
            String sql = "SELECT count(*) as cantidad  FROM registro_noc RF  " + where;

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    cantidad = res.getInt("cantidad");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return cantidad;
    }

    public List<RegistroNocDTO> getRegistroNOC(int pagina, int cantidad, String where) {
        ArrayList<RegistroNocDTO> retorno = new ArrayList<RegistroNocDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT RN.idRegistroNOC, RN.fecha, TF.idTipoFaena, M.idMaquina, E.idEmpleado, P.idPredio, RN.m3, RN.observacion, "
                    + " TF.nombre as tipoFaena, "
                    + " concat_ws(' ', M.codigoMaquina, M.modelo) as maquina, "
                    + " concat_ws(' ', E.nombres, E.apellidos) as operador, "
                    + " P.nombre as predio "
                    + " FROM registro_noc RN "
                    + " JOIN tipo_faena TF ON RN.idFaena = TF.idTipoFaena "
                    + " JOIN maquina M ON RN.idMaquina = M.idMaquina "
                    + " JOIN empleado E ON RN.idOperador = E.idEmpleado"
                    + " JOIN predio P ON RN.idPredio = P.idPredio "  + where, pagina, cantidad, " RN.fecha DESC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    RegistroNocDTO registroNOC = new RegistroNocDTO();
                    registroNOC.setIdRegistroNoc(res.getInt("idRegistroNOC"));
                    registroNOC.setFechaRegistro(res.getDate("fecha"));
                    registroNOC.setIdFaena(res.getInt("idTipoFaena"));
                    registroNOC.setFaena(res.getString("tipoFaena"));
                    registroNOC.setIdMaquina(res.getInt("idMaquina"));
                    registroNOC.setMaquina(res.getString("maquina"));
                    registroNOC.setIdOperador(res.getInt("idEmpleado"));
                    registroNOC.setOperador(res.getString("operador"));
                    registroNOC.setIdPredio(res.getInt("idPredio"));
                    registroNOC.setPredio(res.getString("predio"));
                    registroNOC.setM3(res.getDouble("m3"));
                    registroNOC.setObservacion(res.getString("observacion"));

                    retorno.add(registroNOC);

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

    public List<RegistroNocDTO> getAllRegistroNOC(String where) {
        ArrayList<RegistroNocDTO> retorno = new ArrayList<RegistroNocDTO>();
        try {
            String sql
                    = "SELECT RN.idRegistroNOC, RN.fecha, TF.idTipoFaena, M.idMaquina, E.idEmpleado, P.idPredio, RN.m3, RN.observacion, "
                    + " TF.nombre as tipoFaena, "
                    + " concat_ws(' ', M.codigoMaquina, M.modelo) as maquina, "
                    + " concat_ws(' ', E.nombres, E.apellidos) as operador, "
                    + " P.nombre as predio "
                    + " FROM registro_noc RN "
                    + " JOIN tipo_faena TF ON RN.idFaena = TF.idTipoFaena "
                    + " JOIN maquina M ON RN.idMaquina = M.idMaquina "
                    + " JOIN empleado E ON RN.idOperador = E.idEmpleado"
                    + " JOIN predio P ON RN.idPredio = P.idPredio "  + where + " ORDER BY RN.fecha DESC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    RegistroNocDTO registroNOC = new RegistroNocDTO();
                    registroNOC.setIdRegistroNoc(res.getInt("idRegistroNOC"));
                    registroNOC.setFechaRegistro(res.getDate("fecha"));
                    registroNOC.setIdFaena(res.getInt("idTipoFaena"));
                    registroNOC.setFaena(res.getString("tipoFaena"));
                    registroNOC.setIdMaquina(res.getInt("idMaquina"));
                    registroNOC.setMaquina(res.getString("maquina"));
                    registroNOC.setIdOperador(res.getInt("idEmpleado"));
                    registroNOC.setOperador(res.getString("operador"));
                    registroNOC.setIdPredio(res.getInt("idPredio"));
                    registroNOC.setPredio(res.getString("predio"));
                    registroNOC.setM3(res.getDouble("m3"));
                    registroNOC.setObservacion(res.getString("observacion"));

                    retorno.add(registroNOC);

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
    
    public int saveRegistroNOC(RegistroNocDTO registroNOC) {
        int res = conector.executeInsert("INSERT INTO registro_noc(fecha, idFaena, idMaquina, idPredio, idOperador, m3, observacion) "
                + "VALUES (?,?,?,?,?,?,?)",
                registroNOC.getFechaRegistro(),
                registroNOC.getIdFaena(),
                registroNOC.getIdMaquina(),
                registroNOC.getIdPredio(),
                registroNOC.getIdOperador(),
                registroNOC.getM3(),
                registroNOC.getObservacion());
        return res;
    }

    public ArrayList<TablaResumenMesForwarderDTO> getResumenResultadosNOC(String tipoFaena) {
        ArrayList<TablaResumenMesForwarderDTO> retorno = new ArrayList<TablaResumenMesForwarderDTO>();

        try {
            String sql = "SELECT DATE_FORMAT(RF.fecha,'%m') as mes,DATE_FORMAT(RF.fecha,'%y-%m') as fechaR, SUM(m_real) as produccion, SUM(hmaq_real) as hora_maquina, AVG(ciclo_real) as ciclo_x_hora, AVG(m_hr_real) as productividad, (SUM(hmaq_plan) - SUM(hmaq_real)) as tiempo_muerto "
                    + " FROM registro_fw RF "
                    + " JOIN tipo_faena TF ON RF.idFaena = TF.idTipoFaena "
                    + " WHERE TF.nombre = '" + tipoFaena + "' "
                    + " GROUP BY fechaR DESC "
                    + " LIMIT 0,12 ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    TablaResumenMesForwarderDTO resumenMensualForwarder = new TablaResumenMesForwarderDTO();
                    resumenMensualForwarder.setMes(res.getInt("mes"));
                    resumenMensualForwarder.setMesAño(res.getString("fechaR"));
                    resumenMensualForwarder.setProduccion(res.getDouble("produccion"));
                    resumenMensualForwarder.setHorasMaquina(res.getDouble("hora_maquina"));
                    resumenMensualForwarder.setCiclosXHora(res.getDouble("ciclo_x_hora"));
                    resumenMensualForwarder.setProductividad(res.getDouble("productividad"));
                    resumenMensualForwarder.setTiempoMuerto(res.getDouble("tiempo_muerto"));

                    retorno.add(resumenMensualForwarder);

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

    public ArrayList<TablaResumenMesForwarderDTO> getResumenPotencialesNOC(String tipoFaena) {
        ArrayList<TablaResumenMesForwarderDTO> retorno = new ArrayList<TablaResumenMesForwarderDTO>();

        try {
            String sql = "SELECT DATE_FORMAT(RF.fecha,'%m') as mes,DATE_FORMAT(RF.fecha,'%y-%m') as fechaR, SUM(m_plan) as produccion, SUM(hmaq_plan) as hora_maquina, AVG(ciclo_plan) as ciclo_x_hora, AVG(m_hr_plan) as productividad, (SUM(hmaq_plan) - SUM(hmaq_real)) as tiempo_muerto "
                    + " FROM registro_fw RF "
                    + " JOIN tipo_faena TF ON RF.idFaena = TF.idTipoFaena "
                    + " WHERE TF.nombre = '" + tipoFaena + "' "
                    + " GROUP BY fechaR DESC "
                    + " LIMIT 0,12 ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    TablaResumenMesForwarderDTO resumenMensualForwarder = new TablaResumenMesForwarderDTO();
                    resumenMensualForwarder.setMes(res.getInt("mes"));
                    resumenMensualForwarder.setMesAño(res.getString("fechaR"));
                    resumenMensualForwarder.setProduccion(res.getDouble("produccion"));
                    resumenMensualForwarder.setHorasMaquina(res.getDouble("hora_maquina"));
                    resumenMensualForwarder.setCiclosXHora(res.getDouble("ciclo_x_hora"));
                    resumenMensualForwarder.setProductividad(res.getDouble("productividad"));
                    resumenMensualForwarder.setTiempoMuerto(res.getDouble("tiempo_muerto"));

                    retorno.add(resumenMensualForwarder);

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
}
