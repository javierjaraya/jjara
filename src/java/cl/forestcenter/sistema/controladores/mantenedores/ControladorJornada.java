package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.JornadaDTO;
import cl.forestcenter.sistema.dto.TipoFaenaDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorJornada extends Controlador {

    public List<JornadaDTO> getAllJornadas(String where) {
        ArrayList<JornadaDTO> retorno = new ArrayList<JornadaDTO>();
        try {
            String sql = "SELECT J.idJornada , J.nombre "
                    + "FROM jornada J " + where+ " ORDER BY J.nombre ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    JornadaDTO jornada = new JornadaDTO();
                    jornada.setIdJornada(res.getInt("idJornada"));
                    jornada.setNombre(res.getString("nombre"));

                    retorno.add(jornada);

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
