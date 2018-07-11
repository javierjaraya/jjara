package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.PerfilDTO;
import cl.forestcenter.sistema.dto.ZonaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorZona extends Controlador {

    public List<ZonaDTO> getAllZonas(String where) {
        ArrayList<ZonaDTO> retorno = new ArrayList<ZonaDTO>();
        try {
            String sql = "SELECT Z.idZona , Z.nombre "
                    + "FROM zona Z " + where+ " ORDER BY Z.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    ZonaDTO zona = new ZonaDTO();
                    zona.setIdZona(res.getInt("idZona"));
                    zona.setNombre(res.getString("nombre"));

                    retorno.add(zona);

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

    public ZonaDTO getZonaByID(Integer idZona) {
        ZonaDTO zona = null;
        try {
            String sql = "SELECT z.idZona , z.nombre "
                    + "FROM zona Z WHERE Z.idZona = " + idZona;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    zona = new ZonaDTO();
                    zona.setIdZona(res.getInt("idZona"));
                    zona.setNombre(res.getString("nombre"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return zona;
    }
}
