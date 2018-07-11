package cl.forestcenter.sistema.controladores.mantenedores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;

public class ControladorParametro extends Controlador {

    public ControladorParametro() {
    }

    public HashMap<String, String> obtenerParametros() {
        HashMap<String, String> retorno = new HashMap<String, String>();
        try {
            ResultSet res = conector.getResultSet(
                    "SELECT p.idParametro, p.nombre, p.valor, p.descripcion "
                    + "FROM  parametro p ORDER BY p.nombre");
            while (res.next()) {
                retorno.put(res.getString("nombre"), res.getString("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return retorno;
    }

    public String obtenerParametro(String parametro) {
        String strParametro = "";
        try {

            ResultSet rsOpcion = conector.getResultSet("SELECT * FROM parametro WHERE nombre = ?", parametro);
            if (rsOpcion.next()) {
                strParametro = rsOpcion.getString("valor");
            }

        } catch (Exception e) {
            System.out.println("[Query][obtenerParametro] error:" + e.getMessage());
        } finally {
            conector.close();
        }

        return strParametro;
    }

    public ParametroDTO getParametro(String parametro) {
        ParametroDTO parametroDTO = null;
        try {

            ResultSet rsOpcion = conector.getResultSet("SELECT * FROM parametro WHERE nombre = ?", parametro);
            if (rsOpcion.next()) {
                parametroDTO = new ParametroDTO();
                parametroDTO.setIdParametro(rsOpcion.getInt("idParametro"));
                parametroDTO.setNombre(rsOpcion.getString("nombre"));
                parametroDTO.setValor(rsOpcion.getString("valor"));
                parametroDTO.setDescripcion(rsOpcion.getString("descripcion"));
            }

        } catch (Exception e) {
            System.out.println("[Query][getParametro] error:" + e.getMessage());
        } finally {
            conector.close();
        }

        return parametroDTO;
    }

    public ArrayList<ParametroDTO> getParametros(int pagina, int cantidad, String where) {
        ArrayList<ParametroDTO> retorno = new ArrayList<ParametroDTO>();
        try {
            ResultSet res = conector.getResultSet(obtenerSqlFinalPaginacion(
                    " SELECT	p.idParametro, p.nombre, p.valor, p.descripcion "
                    + " FROM  parametro p WHERE 1 = 1 " + where, pagina, cantidad, "nombre ASC"));
            while (res.next()) {
                try {
                    ParametroDTO parametro = new ParametroDTO();
                    parametro.setIdParametro(res.getInt("idParametro"));
                    parametro.setNombre(res.getString("nombre"));
                    parametro.setValor(res.getString("valor"));
                    parametro.setDescripcion(res.getString("descripcion"));

                    retorno.add(parametro);

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

    public boolean actualizarParametro(Integer idParametro, String nombre, String valor, String descripcion) {
        boolean resp = false;
        try {
            int respuesta = conector.executeUpdate("UPDATE parametro set nombre = ?, valor = ?, descripcion = ? WHERE idParametro = ?", nombre, valor, descripcion, idParametro);

            if (respuesta == 1) {
                resp = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return resp;
    }
}
