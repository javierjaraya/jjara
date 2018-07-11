package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.AreaDTO;
import cl.forestcenter.sistema.dto.MenuDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.PerfilDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorArea extends Controlador {

    public List<AreaDTO> getAllAreas(String where) {
        ArrayList<AreaDTO> retorno = new ArrayList<AreaDTO>();
        try {
            String sql = "SELECT A.idArea , A.nombre "
                    + "FROM area A " + where + " ORDER BY A.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    AreaDTO area = new AreaDTO();
                    area.setIdArea(res.getInt("idArea"));
                    area.setNombre(res.getString("nombre"));

                    retorno.add(area);

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
    
    public AreaDTO getAreaByID(Integer idArea) {
        AreaDTO area = null;
        try {
            String sql = "SELECT a.idArea , A.nombre "
                    + "FROM area A WHERE A.idArea = " + idArea;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    area = new AreaDTO();
                    area.setIdArea(res.getInt("idArea"));
                    area.setNombre(res.getString("nombre"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return area;
    }

}
