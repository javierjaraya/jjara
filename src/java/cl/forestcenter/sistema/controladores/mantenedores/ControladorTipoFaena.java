package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.TipoFaenaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorTipoFaena extends Controlador {

    public List<TipoFaenaDTO> getAllTipoFaena(String where) {
        ArrayList<TipoFaenaDTO> retorno = new ArrayList<TipoFaenaDTO>();
        try {
            String sql = "SELECT TF.idTipoFaena , TF.nombre "
                    + "FROM tipo_faena TF " + where+ " ORDER BY TF.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    TipoFaenaDTO tipoFaena = new TipoFaenaDTO();
                    tipoFaena.setIdTipoFaena(res.getInt("idTipoFaena"));
                    tipoFaena.setNombre(res.getString("nombre"));

                    retorno.add(tipoFaena);

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

    public TipoFaenaDTO getTipoFaenaByID(Integer idTipoFaena) {
        TipoFaenaDTO tipoFaena = null;
        try {
            String sql = "SELECT TF.idTipoFaena , TF.nombre "
                    + "FROM tipo_faena TF WHERE TF.idTipoFaena = " + idTipoFaena;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    tipoFaena = new TipoFaenaDTO();
                    tipoFaena.setIdTipoFaena(res.getInt("idTipoFaena"));
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
