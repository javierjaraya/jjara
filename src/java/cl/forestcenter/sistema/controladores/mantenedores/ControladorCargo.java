package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.CargoDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorCargo extends Controlador {

    public List<CargoDTO> getAllCargos(String where) {
        ArrayList<CargoDTO> retorno = new ArrayList<CargoDTO>();
        try {
            String sql = "SELECT C.idCargo , C.nombre "
                    + "FROM cargo C " + where + " ORDER BY C.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    CargoDTO area = new CargoDTO();
                    area.setIdCargo(res.getInt("idCargo"));
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
    
    public CargoDTO getAreaByID(Integer idCargo) {
        CargoDTO cargo = null;
        try {
            String sql = "SELECT C.idCargo , C.nombre "
                    + "FROM cargo C WHERE C.idCargo = " + idCargo;
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    cargo = new CargoDTO();
                    cargo.setIdCargo(res.getInt("idCargo"));
                    cargo.setNombre(res.getString("nombre"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return cargo;
    }

}
