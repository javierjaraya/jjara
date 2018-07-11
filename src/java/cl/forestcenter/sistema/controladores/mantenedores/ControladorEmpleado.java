package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class ControladorEmpleado extends Controlador {

    public List<EmpleadoDTO> getEmpleados(int pagina, int cantidad, String where) {
        ArrayList<EmpleadoDTO> retorno = new ArrayList<EmpleadoDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                      " SELECT e.idEmpleado, e.rut, e.nombres, e.apellidos, e.direccion, e.telefono, e.correo, c.idCargo, c.nombre as cargo, e.fechaRegistro, e.estado "
                    + " FROM empleado e "
                    + " JOIN cargo c ON e.idCargo = c.idCargo " + where, pagina, cantidad, " e.rut ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    EmpleadoDTO empleado = new EmpleadoDTO();
                    empleado.setIdEmpleado(res.getInt("idEmpleado"));
                    empleado.setRut(res.getString("rut"));
                    empleado.setNombres(res.getString("nombres"));
                    empleado.setApellidos(res.getString("apellidos"));
                    empleado.setDireccion(res.getString("direccion"));
                    empleado.setTelefono(res.getInt("telefono"));
                    empleado.setCorreo(res.getString("correo"));
                    empleado.setIdCargo(res.getInt("idcargo"));
                    empleado.setCargo(res.getString("cargo"));
                    empleado.setFechaRegistro(res.getDate("fechaRegistro"));
                    empleado.setEstado(res.getInt("estado"));

                    retorno.add(empleado);

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

    public List<EmpleadoDTO> getAllEmpleados(String where) {
        ArrayList<EmpleadoDTO> retorno = new ArrayList<EmpleadoDTO>();
        try {
            String sql = "SELECT e.idEmpleado, e.rut, e.nombres, e.apellidos, e.direccion, e.telefono, e.correo, c.idCargo, c.nombre as cargo, e.fechaRegistro, e.estado "
                    + " FROM empleado e "
                    + " JOIN cargo c ON e.idCargo = c.idCargo WHERE 1 = 1 " + where + " ORDER BY e.nombres ASC";//DESC y ASC
           
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    EmpleadoDTO empleado = new EmpleadoDTO();
                    empleado.setIdEmpleado(res.getInt("idEmpleado"));
                    empleado.setRut(res.getString("rut"));
                    empleado.setNombres(res.getString("nombres"));
                    empleado.setApellidos(res.getString("apellidos"));
                    empleado.setDireccion(res.getString("direccion"));
                    empleado.setTelefono(res.getInt("telefono"));
                    empleado.setCorreo(res.getString("correo"));
                    empleado.setIdCargo(res.getInt("idCargo"));
                    empleado.setCargo(res.getString("cargo"));
                    empleado.setFechaRegistro(res.getDate("fechaRegistro"));
                    empleado.setEstado(res.getInt("estado"));

                    retorno.add(empleado);

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

    public boolean updateEmpleado(EmpleadoDTO empleado) {
        int res = conector.executeUpdate("UPDATE empleado SET nombres = ?, apellidos = ?, direccion = ?, telefono = ?, correo = ?, idcargo = ?, estado = ? WHERE idEmpleado = ? ", empleado.getNombres(), empleado.getApellidos(), empleado.getDireccion(), empleado.getTelefono(), empleado.getCorreo(), empleado.getIdCargo(), empleado.getEstado(), empleado.getIdEmpleado());
        return res == 1;
    }

    public boolean removeEmpleado(Integer idEmpleado) {
        int res = conector.executeUpdate("DELETE FROM empleado WHERE idEmpleado = ?", idEmpleado);
        return res == 1;
    }

    public boolean saveEmpleado(EmpleadoDTO empleado) {
        int res = conector.executeInsert("INSERT INTO empleado (rut, nombres, apellidos, direccion, telefono, correo, idcargo, fechaRegistro, estado) VALUES (?,?,?,?,?,?,?,now(),?) ", empleado.getRut(), empleado.getNombres(), empleado.getApellidos(), empleado.getDireccion(), empleado.getTelefono(), empleado.getCorreo(), empleado.getIdCargo(), empleado.getEstado());
        return res > 0;
    }

    public EmpleadoDTO getByRut(String rut) {
        //rut = formatoRut(rut);
        EmpleadoDTO empleado = null;
        try {
            String sql = "SELECT e.idEmpleado, e.rut, e.nombres, e.apellidos, e.direccion, e.telefono, e.correo, c.idCargo, c.nombre as cargo, e.fechaRegistro, e.estado "
                    + " FROM empleado e "
                    + " JOIN cargo c ON e.idCargo = c.idCargo WHERE 1 = 1 AND e.rut = '" + rut + "' ";
            
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    empleado = new EmpleadoDTO();
                    empleado.setIdEmpleado(res.getInt("idEmpleado"));
                    empleado.setRut(res.getString("rut"));
                    empleado.setNombres(res.getString("nombres"));
                    empleado.setApellidos(res.getString("apellidos"));
                    empleado.setDireccion(res.getString("direccion"));
                    empleado.setTelefono(res.getInt("telefono"));
                    empleado.setCorreo(res.getString("correo"));
                    empleado.setIdCargo(res.getInt("idCargo"));
                    empleado.setCargo(res.getString("cargo"));
                    empleado.setFechaRegistro(res.getDate("fechaRegistro"));
                    empleado.setEstado(res.getInt("estado"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return empleado;
    }

    public EmpleadoDTO getByIdEmpleado(Integer idEmpleado) {
        EmpleadoDTO empleado = null;
        try {
            String sql = "SELECT e.idEmpleado, e.rut, e.nombres, e.apellidos, e.direccion, e.telefono, e.correo, c.idCargo, c.nombre as cargo, e.fechaRegistro, e.estado "
                    + " FROM empleado e "
                    + " JOIN cargo c ON e.idCargo = c.idCargo WHERE 1 = 1 AND e.idEmpleado = " + idEmpleado;
            
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    empleado = new EmpleadoDTO();
                    empleado.setIdEmpleado(res.getInt("idEmpleado"));
                    empleado.setRut(res.getString("rut"));
                    empleado.setNombres(res.getString("nombres"));
                    empleado.setApellidos(res.getString("apellidos"));
                    empleado.setDireccion(res.getString("direccion"));
                    empleado.setTelefono(res.getInt("telefono"));
                    empleado.setCorreo(res.getString("correo"));
                    empleado.setIdCargo(res.getInt("idCargo"));
                    empleado.setCargo(res.getString("cargo"));
                    empleado.setFechaRegistro(res.getDate("fechaRegistro"));
                    empleado.setEstado(res.getInt("estado"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }
        return empleado;
    }
}
