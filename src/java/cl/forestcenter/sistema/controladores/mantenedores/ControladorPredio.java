package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.PredioDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorPredio  extends Controlador {
    
    public int cantidadPredios(String where){
        int cantidad = 0;
        try {
            String sql = "SELECT count(*) as cantidad, P.idPredio,P.nombre,A.idarea, A.nombre AS area, Z.idzona, Z.nombre AS zona ,P.superficie, P.estado FROM predio P JOIN area A ON P.idArea = A.idArea JOIN zona Z ON A.idZona = Z.idZona " + where;
            
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
    
    public List<PredioDTO> getPredios(int pagina, int cantidad, String where) {
        ArrayList<PredioDTO> retorno = new ArrayList<PredioDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT P.idPredio,P.nombre AS nombrePredio,A.idarea, A.nombre AS nombreArea, Z.idzona, Z.nombre AS nombreZona ,P.superficie, P.estado "
                            + "FROM predio P "
                            + "JOIN area A ON P.idArea = A.idArea "
                            + "JOIN zona Z ON A.idZona = Z.idZona " + where, pagina, cantidad, " P.idPredio ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    PredioDTO predio = new PredioDTO();
                    predio.setIdPredio(res.getInt("idPredio"));
                    predio.setNombre(res.getString("nombrePredio"));
                    predio.setIdArea(res.getInt("idarea"));
                    predio.setArea(res.getString("nombreArea"));
                    predio.setIdZona(res.getInt("idzona"));
                    predio.setZona(res.getString("nombreZona"));
                    predio.setSuperficie(res.getDouble("superficie"));
                    predio.setEstado(res.getInt("estado"));

                    retorno.add(predio);

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
    
    public List<PredioDTO> getAllPredios(String where) {
        ArrayList<PredioDTO> retorno = new ArrayList<PredioDTO>();
        try {
            String sql = "SELECT P.idPredio,P.nombre,A.idarea, A.nombre AS nombreArea, Z.idzona, Z.nombre AS nombreZona ,P.superficie, P.estado "
                            + "FROM predio P "
                            + "JOIN area A ON P.idArea = A.idArea "
                            + "JOIN zona Z ON A.idZona = Z.idZona " + where+" ORDER BY P.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res != null && res.next()) {
                try {
                    PredioDTO predio = new PredioDTO();
                    predio.setIdPredio(res.getInt("idPredio"));
                    predio.setNombre(res.getString("nombre"));
                    predio.setIdArea(res.getInt("idarea"));
                    predio.setArea(res.getString("nombreArea"));
                    predio.setIdZona(res.getInt("idzona"));
                    predio.setZona(res.getString("nombreZona"));
                    predio.setSuperficie(res.getDouble("superficie"));
                    predio.setEstado(res.getInt("estado"));

                    retorno.add(predio);

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
        int res = conector.executeUpdate("UPDATE predio SET nombre= ?,idarea= ?,superficie= ?, estado = ? WHERE idPredio = ? ", predio.getNombre(), predio.getIdArea(), predio.getSuperficie(),predio.getEstado(), predio.getIdPredio());
        return res == 1;
    }

    public boolean removePredio(Integer idPredio) {
        int res = conector.executeUpdate("DELETE FROM predio WHERE idPredio = ?", idPredio);
        return res == 1;
    }

    public boolean savePredio(PredioDTO predio) {
        int res = conector.executeInsert("INSERT INTO predio(nombre, idarea, superficie,estado) VALUES (?,?,?,?) ",predio.getNombre(),predio.getIdArea(),predio.getSuperficie(),predio.getEstado());
        return res > 0;
    }
}
