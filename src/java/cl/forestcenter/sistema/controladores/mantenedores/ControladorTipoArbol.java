package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.TipoArbolDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorTipoArbol extends Controlador {

    public List<TipoArbolDTO> getAllTipoArbol(String where) {
        ArrayList<TipoArbolDTO> retorno = new ArrayList<TipoArbolDTO>();
        try {
            String sql = "SELECT TA.idTipoArbol , TA.nombre "
                    + "FROM tipo_arbol TA " + where+ " ORDER BY TA.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    TipoArbolDTO tipoArbol = new TipoArbolDTO();
                    tipoArbol.setIdTipoArbol(res.getInt("idTipoArbol"));
                    tipoArbol.setNombre(res.getString("nombre"));

                    retorno.add(tipoArbol);

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

    public TipoArbolDTO getTipoArbolByID(Integer idTipoFaena) {
        TipoArbolDTO tipoFaena = null;
        try {
            String sql = "SELECT TA.idTipoArbol , TA.nombre "
                    + "FROM tipo_arbol TA WHERE TA.idTipoArbol = " + idTipoFaena;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    tipoFaena = new TipoArbolDTO();
                    tipoFaena.setIdTipoArbol(res.getInt("idTipoArbol"));
                    tipoFaena.setNombre(res.getString("nombre"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return tipoFaena;
    }
}
