package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.TipoMaquinaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorTipoMaquina extends Controlador {

    public List<TipoMaquinaDTO> getAllTipoFaena(String where) {
        ArrayList<TipoMaquinaDTO> retorno = new ArrayList<TipoMaquinaDTO>();
        try {
            String sql = "SELECT TM.idTipoMaquina , TM.nombre "
                    + "FROM tipo_maquina TM " + where+ " ORDER BY TM.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    TipoMaquinaDTO tipoMaquina = new TipoMaquinaDTO();
                    tipoMaquina.setIdTipoMaquina(res.getInt("idTipoMaquina"));
                    tipoMaquina.setNombre(res.getString("nombre"));

                    retorno.add(tipoMaquina);

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

    public TipoMaquinaDTO getTipoMaquinaByID(Integer idTipoMaquina) {
        TipoMaquinaDTO tipoMaquina = null;
        try {
            String sql = "SELECT TM.idTipoMaquina , TM.nombre "
                    + "FROM tipo_maquina TM  WHERE TM.idTipoMaquina = " + idTipoMaquina;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    tipoMaquina = new TipoMaquinaDTO();
                    tipoMaquina.setIdTipoMaquina(res.getInt("idTipoMaquina"));
                    tipoMaquina.setNombre(res.getString("nombre"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return tipoMaquina;
    }
}
