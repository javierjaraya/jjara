package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.PredioDTO;
import cl.forestcenter.sistema.dto.RegistroForwarderDTO;
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
public class ControladorRegistroForwarder extends Controlador {
    public int getFolio() {
        int folio = 0;
        try {
            String sql = "SELECT max(folio) as folio  FROM registro_fw FW ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    folio = res.getInt("folio");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return (folio+1);
    }
    
    public int cantidadRegistrosForwarder(String where) {
        int cantidad = 0;
        try {
            String sql = "SELECT count(*) as cantidad  FROM registro_fw RF  " + where;

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

    public List<RegistroForwarderDTO> getRegistroForwarder(int pagina, int cantidad, String where) {
        ArrayList<RegistroForwarderDTO> retorno = new ArrayList<RegistroForwarderDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT RF.idRegistroFW, RF.folio, RF.fecha, F.idFaena, M.idMaquina, E.idEmpleado, RF.turno, J.idJornada, P.idPredio, RF.horometro_inicial, RF.horometro_final, RF.hmaq_plan, RF.hmaq_real, RF.ciclo_hr_plan, RF.ciclo_hr_real, RF.ciclo_plan, RF.ciclo_real, RF.m_hr_plan, RF.m_hr_real, RF.m_plan, RF.m_real, RF.observacion, "
                    + " concat_ws(' ', TF.nombre, F.numeroTeam) as faena, "
                    + " concat_ws(' ', M.codigoMaquina, M.modelo) as maquina, "
                    + " concat_ws(' ', E.nombres, E.apellidos) as operador, "
                    + " J.nombre as jornada, "
                    + " P.nombre as predio "
                    + " FROM registro_fw RF "
                    + " JOIN faena F ON RF.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " JOIN maquina M ON RF.idMaquina = M.idMaquina "
                    + " JOIN empleado E ON RF.idOperador = E.idEmpleado "
                    + " JOIN jornada J ON RF.idJornada = J.idJornada "
                    + " JOIN predio P ON RF.idPredio = P.idPredio " + where, pagina, cantidad, " RF.fecha DESC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    RegistroForwarderDTO registroForwarder = new RegistroForwarderDTO();
                    registroForwarder.setIdRegistroForwarder(res.getInt("idRegistroFW"));
                    registroForwarder.setFolio(res.getInt("folio"));
                    registroForwarder.setFechaRegistro(res.getDate("fecha"));
                    registroForwarder.setIdFaena(res.getInt("idFaena"));
                    registroForwarder.setFaena(res.getString("faena"));
                    registroForwarder.setIdMaquina(res.getInt("idMaquina"));
                    registroForwarder.setMaquina(res.getString("maquina"));
                    registroForwarder.setIdOperador(res.getInt("idEmpleado"));
                    registroForwarder.setOperador(res.getString("operador"));
                    registroForwarder.setTurno(res.getString("turno"));
                    registroForwarder.setIdJornada(res.getInt("idJornada"));
                    registroForwarder.setJornada(res.getString("jornada"));
                    registroForwarder.setIdPredio(res.getInt("idPredio"));
                    registroForwarder.setPredio(res.getString("predio"));
                    registroForwarder.setHorometroInicial(res.getDouble("horometro_inicial"));
                    registroForwarder.setHorometroFinal(res.getDouble("horometro_final"));
                    registroForwarder.sethMaqPlan(res.getInt("hmaq_plan"));
                    registroForwarder.sethMaqReal(res.getDouble("hmaq_real"));
                    registroForwarder.setCicloHrPlan(res.getDouble("ciclo_hr_plan"));
                    registroForwarder.setCicloHrReal(res.getDouble("ciclo_hr_real"));
                    registroForwarder.setCicloPlan(res.getDouble("ciclo_plan"));
                    registroForwarder.setCicloReal(res.getDouble("ciclo_real"));
                    registroForwarder.setmHrPlan(res.getDouble("m_hr_plan"));
                    registroForwarder.setmHrReal(res.getDouble("m_hr_real"));
                    registroForwarder.setmPlan(res.getDouble("m_plan"));
                    registroForwarder.setmReal(res.getDouble("m_real"));
                    registroForwarder.setObservacion(res.getString("observacion"));

                    retorno.add(registroForwarder);

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

    public List<RegistroForwarderDTO> getAllRegistroForwarder(String where) {
        ArrayList<RegistroForwarderDTO> retorno = new ArrayList<RegistroForwarderDTO>();
        try {
            String sql = 
                    "SELECT RF.idRegistroFW, RF.folio, RF.fecha, F.idFaena, M.idMaquina, E.idEmpleado, RF.turno, J.idJornada, P.idPredio, RF.horometro_inicial, RF.horometro_final, RF.hmaq_plan, RF.hmaq_real, RF.ciclo_hr_plan, RF.ciclo_hr_real, RF.ciclo_plan, RF.ciclo_real, RF.m_hr_plan, RF.m_hr_real, RF.m_plan, RF.m_real, RF.observacion, "
                    + " concat_ws(' ', TF.nombre, F.numeroTeam) as faena, "
                    + " concat_ws(' ', M.codigoMaquina, M.modelo) as maquina, "
                    + " concat_ws(' ', E.nombres, E.apellidos) as operador, "
                    + " J.nombre as jornada, "
                    + " P.nombre as predio "
                    + " FROM registro_fw RF "
                    + " JOIN faena F ON RF.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " JOIN maquina M ON RF.idMaquina = M.idMaquina "
                    + " JOIN empleado E ON RF.idOperador = E.idEmpleado "
                    + " JOIN jornada J ON RF.idJornada = J.idJornada "
                    + " JOIN predio P ON RF.idPredio = P.idPredio " + where + "ORDER BY RF.fecha DESC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    RegistroForwarderDTO registroForwarder = new RegistroForwarderDTO();
                    registroForwarder.setIdRegistroForwarder(res.getInt("idRegistroFW"));
                    registroForwarder.setFolio(res.getInt("folio"));
                    registroForwarder.setFechaRegistro(res.getDate("fecha"));
                    registroForwarder.setIdFaena(res.getInt("idFaena"));
                    registroForwarder.setFaena(res.getString("faena"));
                    registroForwarder.setIdMaquina(res.getInt("idMaquina"));
                    registroForwarder.setMaquina(res.getString("maquina"));
                    registroForwarder.setIdOperador(res.getInt("idEmpleado"));
                    registroForwarder.setOperador(res.getString("operador"));
                    registroForwarder.setTurno(res.getString("turno"));
                    registroForwarder.setIdJornada(res.getInt("idJornada"));
                    registroForwarder.setJornada(res.getString("jornada"));
                    registroForwarder.setIdPredio(res.getInt("idPredio"));
                    registroForwarder.setPredio(res.getString("predio"));
                    registroForwarder.setHorometroInicial(res.getDouble("horometro_inicial"));
                    registroForwarder.setHorometroFinal(res.getDouble("horometro_final"));
                    registroForwarder.sethMaqPlan(res.getInt("hmaq_plan"));
                    registroForwarder.sethMaqReal(res.getDouble("hmaq_real"));
                    registroForwarder.setCicloHrPlan(res.getDouble("ciclo_hr_plan"));
                    registroForwarder.setCicloHrReal(res.getDouble("ciclo_hr_real"));
                    registroForwarder.setCicloPlan(res.getDouble("ciclo_plan"));
                    registroForwarder.setCicloReal(res.getDouble("ciclo_real"));
                    registroForwarder.setmHrPlan(res.getDouble("m_hr_plan"));
                    registroForwarder.setmHrReal(res.getDouble("m_hr_real"));
                    registroForwarder.setmPlan(res.getDouble("m_plan"));
                    registroForwarder.setmReal(res.getDouble("m_real"));
                    registroForwarder.setObservacion(res.getString("observacion"));

                    retorno.add(registroForwarder);

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

    public boolean updateRegistroForwarder(PredioDTO predio) {
        int res = conector.executeUpdate("UPDATE predio SET nombre= ?,idarea= ?,superficie= ?, estado = ? WHERE idPredio = ? ", predio.getNombre(), predio.getIdArea(), predio.getSuperficie(), predio.getEstado(), predio.getIdPredio());
        return res == 1;
    }

    public boolean removeRegistroForwarder(Integer idPredio) {
        int res = conector.executeUpdate("DELETE FROM predio WHERE idPredio = ?", idPredio);
        return res == 1;
    }

    public int saveRegistroForwarder(RegistroForwarderDTO registroForwarder) {
        int res = conector.executeInsert("INSERT INTO registro_fw(folio, fecha, idFaena, idMaquina, idOperador, turno, idJornada, idPredio, horometro_inicial, horometro_final, hmaq_plan, hmaq_real, ciclo_hr_plan, ciclo_hr_real, ciclo_plan, ciclo_real, m_hr_plan, m_hr_real, m_plan, m_real,observacion) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                registroForwarder.getFolio(),
                registroForwarder.getFechaRegistro(),
                registroForwarder.getIdFaena(),
                registroForwarder.getIdMaquina(),
                registroForwarder.getIdOperador(),
                registroForwarder.getTurno(),
                registroForwarder.getIdJornada(),
                registroForwarder.getIdPredio(),
                registroForwarder.getHorometroInicial(),
                registroForwarder.getHorometroFinal(),
                registroForwarder.gethMaqPlan(),
                registroForwarder.gethMaqReal(),
                registroForwarder.getCicloHrPlan(),
                registroForwarder.getCicloHrReal(),
                registroForwarder.getCicloPlan(),
                registroForwarder.getCicloReal(),
                registroForwarder.getmHrPlan(),
                registroForwarder.getmHrReal(),
                registroForwarder.getmPlan(),
                registroForwarder.getmReal(),
                registroForwarder.getObservacion());
        return res;
    }

    public ArrayList<TablaResumenMesForwarderDTO> getResumenResultadosForwarder(String tipoFaena) {
        ArrayList<TablaResumenMesForwarderDTO> retorno = new ArrayList<TablaResumenMesForwarderDTO>();

        try {
            String sql = "SELECT DATE_FORMAT(RF.fecha,'%m') as mes,DATE_FORMAT(RF.fecha,'%y-%m') as fechaR, SUM(m_real) as produccion, SUM(hmaq_real) as hora_maquina, AVG(ciclo_hr_real) as ciclo_x_hora, AVG(m_hr_real) as productividad, (SUM(hmaq_plan) - SUM(hmaq_real)) as tiempo_muerto "
                    + " FROM registro_fw RF "
                    + " JOIN faena F ON RF.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
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

    public ArrayList<TablaResumenMesForwarderDTO> getResumenPotencialesForwarder(String tipoFaena) {
        ArrayList<TablaResumenMesForwarderDTO> retorno = new ArrayList<TablaResumenMesForwarderDTO>();

        try {
            String sql = "SELECT DATE_FORMAT(RF.fecha,'%m') as mes,DATE_FORMAT(RF.fecha,'%y-%m') as fechaR, SUM(m_plan) as produccion, SUM(hmaq_plan) as hora_maquina, AVG(ciclo_hr_plan) as ciclo_x_hora, AVG(m_hr_plan) as productividad, (SUM(hmaq_plan) - SUM(hmaq_real)) as tiempo_muerto "
                    + " FROM registro_fw RF "
                    + " JOIN faena F ON RF.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
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
