package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.PredioDTO;
import cl.forestcenter.sistema.dto.RegistroForwarderDTO;
import cl.forestcenter.sistema.dto.RegistroHarvesterDTO;
import cl.forestcenter.sistema.dto.TablaResumenMesHarvesterDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorRegistroHarvester extends Controlador {

    public int getFolio() {
        int folio = 0;
        try {
            String sql = "SELECT max(folio) as folio  FROM registro_hv RH  ";

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
        return (folio + 1);
    }

    public int cantidadRegistrosHarvester(String where) {
        int cantidad = 0;
        try {
            String sql = "SELECT count(*) as cantidad  FROM registro_hv RH  " + where;

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

    public List<RegistroHarvesterDTO> getRegistroHarvester(int pagina, int cantidad, String where) {
        ArrayList<RegistroHarvesterDTO> retorno = new ArrayList<RegistroHarvesterDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT RH.idRegistroHV, RH.folio, RH.fecha, F.idFaena, M.idMaquina, E.idEmpleado, RH.turno, J.idJornada, P.idPredio, RH.horometro_inicial, RH.horometro_final, RH.m_arbol, RH.hrs_maq_plan, RH.hrs_maq_real, RH.arb_hr_plan, RH.arb_hr_real, RH.arb_plan, RH.arb_real, RH.m_hr_plan, RH.m_hr_real, RH.m_plan, RH.m_real, "
                    + "concat_ws(' ', TF.nombre, F.numeroTeam) as faena, "
                    + "concat_ws(' ', M.codigoMaquina, M.modelo) as maquina, "
                    + "concat_ws(' ', E.nombres, E.apellidos) as operador, "
                    + "J.nombre as jornada, "
                    + "P.nombre as predio "
                    + "FROM registro_hv RH "
                    + "JOIN faena F ON RH.idFaena = F.idFaena "
                    + "JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + "JOIN maquina M ON RH.idMaquina = M.idMaquina "
                    + "JOIN empleado E ON RH.idOperador = E.idEmpleado "
                    + "JOIN jornada J ON RH.idJornada = J.idJornada "
                    + "JOIN predio P ON RH.idPredio = P.idPredio " + where, pagina, cantidad, " RH.fecha DESC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    RegistroHarvesterDTO registroHarvester = new RegistroHarvesterDTO();
                    registroHarvester.setIdRegistroForwarder(res.getInt("idRegistroHV"));
                    registroHarvester.setFolio(res.getInt("folio"));
                    registroHarvester.setFechaRegistro(res.getDate("fecha"));
                    registroHarvester.setIdFaena(res.getInt("idFaena"));
                    registroHarvester.setFaena(res.getString("faena"));
                    registroHarvester.setIdMaquina(res.getInt("idMaquina"));
                    registroHarvester.setMaquina(res.getString("maquina"));
                    registroHarvester.setIdOperador(res.getInt("idEmpleado"));
                    registroHarvester.setOperador(res.getString("operador"));
                    registroHarvester.setTurno(res.getString("turno"));
                    registroHarvester.setIdJornada(res.getInt("idJornada"));
                    registroHarvester.setJornada(res.getString("jornada"));
                    registroHarvester.setIdPredio(res.getInt("idPredio"));
                    registroHarvester.setPredio(res.getString("predio"));
                    registroHarvester.setHorometroInicial(res.getDouble("horometro_inicial"));
                    registroHarvester.setHorometroFinal(res.getDouble("horometro_final"));
                    registroHarvester.setmArbol(res.getDouble("m_arbol"));
                    registroHarvester.sethMaqPlan(res.getDouble("hrs_maq_plan"));
                    registroHarvester.sethMaqReal(res.getDouble("hrs_maq_real"));
                    registroHarvester.setArbHrPlan(res.getDouble("arb_hr_plan"));
                    registroHarvester.setArbHrReal(res.getDouble("arb_hr_real"));
                    registroHarvester.setArbPlan(res.getDouble("arb_plan"));
                    registroHarvester.setArbReal(res.getDouble("arb_real"));
                    registroHarvester.setmHrPlan(res.getDouble("m_hr_plan"));
                    registroHarvester.setmHrReal(res.getDouble("m_hr_real"));
                    registroHarvester.setmPlan(res.getDouble("m_plan"));
                    registroHarvester.setmReal(res.getDouble("m_real"));

                    retorno.add(registroHarvester);

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

    public List<RegistroHarvesterDTO> getAllRegistroHarvester(String where) {
        ArrayList<RegistroHarvesterDTO> retorno = new ArrayList<RegistroHarvesterDTO>();
        try {
            String sql = "SELECT RH.idRegistroHV, RH.folio, RH.fecha, F.idFaena, M.idMaquina, E.idEmpleado, RH.turno, J.idJornada, P.idPredio, RH.horometro_inicial, RH.horometro_final, RH.m_arbol, RH.hrs_maq_plan, RH.hrs_maq_real, RH.arb_hr_plan, RH.arb_hr_real, RH.arb_plan, RH.arb_real, RH.m_hr_plan, RH.m_hr_real, RH.m_plan, RH.m_real, "
                    + "concat_ws(' ', TF.nombre, F.numeroTeam) as faena, "
                    + "concat_ws(' ', M.codigoMaquina, M.modelo) as maquina, "
                    + "concat_ws(' ', E.nombres, E.apellidos) as operador, "
                    + "J.nombre as jornada, "
                    + "P.nombre as predio "
                    + "FROM registro_hv RH "
                    + "JOIN faena F ON RH.idFaena = F.idFaena "
                    + "JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + "JOIN maquina M ON RH.idMaquina = M.idMaquina "
                    + "JOIN empleado E ON RH.idOperador = E.idEmpleado "
                    + "JOIN jornada J ON RH.idJornada = J.idJornada "
                    + "JOIN predio P ON RH.idPredio = P.idPredio " + where + " ORDER BY RH.fecha DESC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    RegistroHarvesterDTO registroHarvester = new RegistroHarvesterDTO();
                    registroHarvester.setIdRegistroForwarder(res.getInt("idRegistroHV"));
                    registroHarvester.setFolio(res.getInt("folio"));
                    registroHarvester.setFechaRegistro(res.getDate("fecha"));
                    registroHarvester.setIdFaena(res.getInt("idFaena"));
                    registroHarvester.setFaena(res.getString("faena"));
                    registroHarvester.setIdMaquina(res.getInt("idMaquina"));
                    registroHarvester.setMaquina(res.getString("maquina"));
                    registroHarvester.setIdOperador(res.getInt("idEmpleado"));
                    registroHarvester.setOperador(res.getString("operador"));
                    registroHarvester.setTurno(res.getString("turno"));
                    registroHarvester.setIdJornada(res.getInt("idJornada"));
                    registroHarvester.setJornada(res.getString("jornada"));
                    registroHarvester.setIdPredio(res.getInt("idPredio"));
                    registroHarvester.setPredio(res.getString("predio"));
                    registroHarvester.setHorometroInicial(res.getDouble("horometro_inicial"));
                    registroHarvester.setHorometroFinal(res.getDouble("horometro_final"));
                    registroHarvester.setmArbol(res.getDouble("m_arbol"));
                    registroHarvester.sethMaqPlan(res.getDouble("hrs_maq_plan"));
                    registroHarvester.sethMaqReal(res.getDouble("hrs_maq_real"));
                    registroHarvester.setArbHrPlan(res.getDouble("arb_hr_plan"));
                    registroHarvester.setArbHrReal(res.getDouble("arb_hr_real"));
                    registroHarvester.setArbPlan(res.getDouble("arb_plan"));
                    registroHarvester.setArbReal(res.getDouble("arb_real"));
                    registroHarvester.setmHrPlan(res.getDouble("m_hr_plan"));
                    registroHarvester.setmHrReal(res.getDouble("m_hr_real"));
                    registroHarvester.setmPlan(res.getDouble("m_plan"));
                    registroHarvester.setmReal(res.getDouble("m_real"));

                    retorno.add(registroHarvester);

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

    public boolean updatePredio(PredioDTO predio) {
        int res = conector.executeUpdate("UPDATE predio SET nombre= ?,idarea= ?,superficie= ?, estado = ? WHERE idPredio = ? ", predio.getNombre(), predio.getIdArea(), predio.getSuperficie(), predio.getEstado(), predio.getIdPredio());
        return res == 1;
    }

    public boolean removePredio(Integer idPredio) {
        int res = conector.executeUpdate("DELETE FROM predio WHERE idPredio = ?", idPredio);
        return res == 1;
    }

    public int saveRegistroHarvester(RegistroHarvesterDTO registroHarvester) {
        int res = conector.executeInsert("INSERT INTO registro_hv(Folio, Fecha, idMaquina, idOperador, turno, idJornada, idPredio, idFaena, horometro_inicial, horometro_final, m_arbol, hrs_maq_plan, hrs_maq_real, arb_hr_plan, arb_hr_real, arb_plan, arb_real, m_hr_plan, m_hr_real, m_plan, m_real,observacion) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                registroHarvester.getFolio(),
                registroHarvester.getFechaRegistro(),
                registroHarvester.getIdMaquina(),
                registroHarvester.getIdOperador(),
                registroHarvester.getTurno(),
                registroHarvester.getIdJornada(),
                registroHarvester.getIdPredio(),
                registroHarvester.getIdFaena(),
                registroHarvester.getHorometroInicial(),
                registroHarvester.getHorometroFinal(),
                registroHarvester.getmArbol(),
                registroHarvester.gethMaqPlan(),
                registroHarvester.gethMaqReal(),
                registroHarvester.getArbHrPlan(),
                registroHarvester.getArbHrReal(),
                registroHarvester.getArbPlan(),
                registroHarvester.getArbReal(),
                registroHarvester.getmHrPlan(),
                registroHarvester.getmHrReal(),
                registroHarvester.getmPlan(),
                registroHarvester.getmReal(),
                registroHarvester.getObservacion());
        return res;
    }

    public ArrayList<TablaResumenMesHarvesterDTO> getResumenResultadosHarvester(String tipoFaena) {
        ArrayList<TablaResumenMesHarvesterDTO> retorno = new ArrayList<TablaResumenMesHarvesterDTO>();

        try {
            String sql = "SELECT DATE_FORMAT(RH.fecha,'%m') as mes,DATE_FORMAT(RH.fecha,'%y-%m') as fechaR, SUM(m_real) as produccion, SUM(hrs_maq_real) as hora_maquina, AVG(arb_hr_real) as arbol_x_hora, AVG(m_hr_real) as productividad, (SUM(hrs_maq_plan) - SUM(hrs_maq_real)) as tiempo_muerto "
                    + " FROM registro_hv RH "
                    + " JOIN faena F ON RH.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " WHERE TF.nombre = '" + tipoFaena + "' "
                    + " GROUP BY fechaR DESC "
                    + " LIMIT 0,12 ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    TablaResumenMesHarvesterDTO resumenMensualHarvester = new TablaResumenMesHarvesterDTO();
                    resumenMensualHarvester.setMes(res.getInt("mes"));
                    resumenMensualHarvester.setMesAño(res.getString("fechaR"));
                    resumenMensualHarvester.setProduccion(res.getDouble("produccion"));
                    resumenMensualHarvester.setHorasMaquina(res.getDouble("hora_maquina"));
                    resumenMensualHarvester.setArbolesXHora(res.getDouble("arbol_x_hora"));
                    resumenMensualHarvester.setProductividad(res.getDouble("productividad"));
                    resumenMensualHarvester.setTiempoMuerto(res.getDouble("tiempo_muerto"));

                    retorno.add(resumenMensualHarvester);

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

    public ArrayList<TablaResumenMesHarvesterDTO> getResumenPotencialesHarvester(String tipoFaena) {
        ArrayList<TablaResumenMesHarvesterDTO> retorno = new ArrayList<TablaResumenMesHarvesterDTO>();

        try {
            String sql = "SELECT DATE_FORMAT(RH.fecha,'%m') as mes, DATE_FORMAT(RH.fecha,'%y-%m') as fechaR, SUM(m_plan) as produccion, SUM(hrs_maq_plan) as hora_maquina, AVG(arb_hr_plan) as arbol_x_hora, AVG(m_hr_plan) as productividad, (SUM(hrs_maq_plan) - SUM(hrs_maq_real)) as tiempo_muerto "
                    + " FROM registro_hv RH "
                    + " JOIN faena F ON RH.idFaena = F.idFaena "
                    + " JOIN tipo_faena TF ON F.idTipoFaena = TF.idTipoFaena "
                    + " WHERE TF.nombre = '" + tipoFaena + "' "
                    + " GROUP BY fechaR DESC "
                    + " LIMIT 0,12 ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    TablaResumenMesHarvesterDTO resumenMensualHarvester = new TablaResumenMesHarvesterDTO();
                    resumenMensualHarvester.setMes(res.getInt("mes"));
                    resumenMensualHarvester.setMesAño(res.getString("fechaR"));
                    resumenMensualHarvester.setProduccion(res.getDouble("produccion"));
                    resumenMensualHarvester.setHorasMaquina(res.getDouble("hora_maquina"));
                    resumenMensualHarvester.setArbolesXHora(res.getDouble("arbol_x_hora"));
                    resumenMensualHarvester.setProductividad(res.getDouble("productividad"));
                    resumenMensualHarvester.setTiempoMuerto(res.getDouble("tiempo_muerto"));

                    retorno.add(resumenMensualHarvester);

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

    public double metrosCubicosAcumuladosActual(int idFaena, int idPredio, Date fechaInicial) {
        double acumulados = 0;
        try {
            String sql = "SELECT idFaena, SUM(m_real) as acumulados "
                    + " FROM registro_hv RV "
                    + " WHERE RV.idFaena = "+idFaena
                    + " AND RV.idPredio = "+idPredio
                    + " AND RV.fecha <= now() "
                    + " AND RV.Fecha >= "+fechaInicial
                    + " GROUP BY idFaena  ";

            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    acumulados = res.getDouble("acumulados");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return acumulados;
    }
}
