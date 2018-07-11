package cl.forestcenter.sistema.controladores.mantenedores;

import cl.forestcenter.sistema.controladores.mantenedores.nucleo.Controlador;
import cl.forestcenter.sistema.dto.EmpleadoDTO;
import cl.forestcenter.sistema.dto.ParametroDTO;
import cl.forestcenter.sistema.dto.PerfilDTO;
import cl.forestcenter.sistema.dto.UsuarioDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario extends Controlador {

    public List<UsuarioDTO> getUsuarios(int pagina, int cantidad, String where) {
        ArrayList<UsuarioDTO> retorno = new ArrayList<UsuarioDTO>();
        try {
            String sql = obtenerSqlFinalPaginacion(
                    "SELECT u.idUsuario, u.nombreUsuario, u.password, u.estado,  "
                    + " p.idPerfil , p.nombrePerfil, "
                    + " e.idEmpleado, e.rut, concat_ws(' ', e.nombres, e.apellidos) as nombreEmpleado, "
                    + " iu.cantidadIntentos, TIMESTAMPDIFF(MINUTE, iu.fecha_ultimo_intento, NOW()) as minutos"
                    + " FROM usuario u "
                    + " INNER JOIN perfil p ON u.idPerfil = p.idPerfil "
                    + " INNER JOIN intento_usuario iu ON u.idUsuario = iu.idUsuario "
                    + " INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado " + where, pagina, cantidad, "u.nombreUsuario ASC");//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    UsuarioDTO usuario = new UsuarioDTO();
                    usuario.setIdUsuario(res.getInt("idUsuario"));
                    usuario.setNombreUsuario(res.getString("nombreUsuario"));
                    usuario.setPassword(res.getString("password"));
                    usuario.setEstado(res.getInt("estado"));

                    usuario.setIdEmpleado(res.getInt("idEmpleado"));
                    usuario.setRut(res.getString("rut"));
                    usuario.setNombreEmpleado(res.getString("nombreEmpleado"));

                    usuario.setIdPerfil(res.getInt("idPerfil"));
                    usuario.setNombrePerfil(res.getString("nombrePerfil"));

                    usuario.setCantidadIntentos(res.getInt("cantidadIntentos"));
                    usuario.setDiferenciaMinutos(res.getInt("minutos"));

                    retorno.add(usuario);

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

    public boolean actualizarUsuario(UsuarioDTO usuario) {
        int res = conector.executeUpdate("UPDATE usuario SET nombreUsuario = ?, password = ?, estado = ?, idEmpleado = ? , idPerfil = ? WHERE idusuario = ? ", usuario.getNombreUsuario(), usuario.getPassword(), usuario.getEstado(), usuario.getIdEmpleado() ,usuario.getIdPerfil(), usuario.getIdUsuario());
        return res == 1;
    }

    public boolean eliminarUsuario(Integer idUsuario) {
        int r = conector.executeUpdate("DELETE FROM intento_usuario WHERE idUsuario = ? ", idUsuario);
        int res = conector.executeUpdate("DELETE FROM usuario WHERE idusuario = ?", idUsuario);
        return res == 1;
    }

    public boolean guardarUsuario(UsuarioDTO usuario) {
        int res = conector.executeInsert("INSERT INTO usuario (nombreUsuario, password, estado, idEmpleado, idPerfil) VALUES (?,?,?,?,?)", usuario.getNombreUsuario(), usuario.getPassword(), usuario.getEstado(), usuario.getIdEmpleado(), usuario.getIdPerfil());
        int r = conector.executeInsert("INSERT INTO intento_usuario(cantidadIntentos, fecha_ultimo_intento, idUsuario) VALUES (0,now(),?)", res);
        return res > 0;
    }

    public UsuarioDTO getUsuarioByIdUsuario(Integer idUsuario) {
        UsuarioDTO usuario = null;
        try {
            String sql = " SELECT u.idUsuario, u.nombreUsuario, u.password, u.estado,  "
                    + " p.idPerfil , p.nombrePerfil, "
                    + " e.idEmpleado, e.rut, concat_ws(' ', e.nombres, e.apellidos) as nombreEmpleado, "
                    + " iu.cantidadIntentos, TIMESTAMPDIFF(MINUTE, iu.fecha_ultimo_intento, NOW()) as minutos"
                    + " FROM usuario u "
                    + " INNER JOIN perfil p ON u.idPerfil = p.idPerfil "
                    + " INNER JOIN intento_usuario iu ON u.idUsuario = iu.idUsuario "
                    + " INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado WHERE 1 = 1 AND u.idUsuario = " + idUsuario + " ORDER BY u.nombreUsuario ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuario(res.getInt("idUsuario"));
                    usuario.setNombreUsuario(res.getString("nombreUsuario"));
                    usuario.setPassword(res.getString("password"));
                    usuario.setEstado(res.getInt("estado"));

                    usuario.setIdEmpleado(res.getInt("idEmpleado"));
                    usuario.setRut(res.getString("rut"));
                    usuario.setNombreEmpleado(res.getString("nombreEmpleado"));

                    usuario.setIdPerfil(res.getInt("idPerfil"));
                    usuario.setNombrePerfil(res.getString("nombrePerfil"));

                    usuario.setCantidadIntentos(res.getInt("cantidadIntentos"));
                    usuario.setDiferenciaMinutos(res.getInt("minutos"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return usuario;
    }
    
    public UsuarioDTO getUsuarioByIdEmpleado(Integer idEmpleado) {
        UsuarioDTO usuario = null;
        try {
            String sql = " SELECT u.idUsuario, u.nombreUsuario, u.password, u.estado,  "
                    + " p.idPerfil , p.nombrePerfil, "
                    + " e.idEmpleado, e.rut, concat_ws(' ', e.nombres, e.apellidos) as nombreEmpleado, "
                    + " iu.cantidadIntentos, TIMESTAMPDIFF(MINUTE, iu.fecha_ultimo_intento, NOW()) as minutos"
                    + " FROM usuario u "
                    + " INNER JOIN perfil p ON u.idPerfil = p.idPerfil "
                    + " INNER JOIN intento_usuario iu ON u.idUsuario = iu.idUsuario "
                    + " INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado WHERE 1 = 1 AND e.idEmpleado = " + idEmpleado + " ORDER BY u.nombreUsuario ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuario(res.getInt("idUsuario"));
                    usuario.setNombreUsuario(res.getString("nombreUsuario"));
                    usuario.setPassword(res.getString("password"));
                    usuario.setEstado(res.getInt("estado"));

                    usuario.setIdEmpleado(res.getInt("idEmpleado"));
                    usuario.setRut(res.getString("rut"));
                    usuario.setNombreEmpleado(res.getString("nombreEmpleado"));

                    usuario.setIdPerfil(res.getInt("idPerfil"));
                    usuario.setNombrePerfil(res.getString("nombrePerfil"));

                    usuario.setCantidadIntentos(res.getInt("cantidadIntentos"));
                    usuario.setDiferenciaMinutos(res.getInt("minutos"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return usuario;
    }

    public UsuarioDTO getUsuarioByNombreUsuario(String nombreUsuario) {
        UsuarioDTO usuario = null;
        try {
            String sql = " SELECT u.idUsuario, u.nombreUsuario, u.password, u.estado,  "
                    + " p.idPerfil , p.nombrePerfil, "
                    + " e.idEmpleado, e.rut, concat_ws(' ', e.nombres, e.apellidos) as nombreEmpleado, "
                    + " iu.cantidadIntentos, TIMESTAMPDIFF(MINUTE, iu.fecha_ultimo_intento, NOW()) as minutos"
                    + " FROM usuario u "
                    + " INNER JOIN perfil p ON u.idPerfil = p.idPerfil "
                    + " INNER JOIN intento_usuario iu ON u.idUsuario = iu.idUsuario "
                    + " INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado WHERE 1 = 1 AND u.nombreUsuario = '" + nombreUsuario + "' ORDER BY u.nombreUsuario ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuario(res.getInt("idUsuario"));
                    usuario.setNombreUsuario(res.getString("nombreUsuario"));
                    usuario.setPassword(res.getString("password"));
                    usuario.setEstado(res.getInt("estado"));

                    usuario.setIdEmpleado(res.getInt("idEmpleado"));
                    usuario.setRut(res.getString("rut"));
                    usuario.setNombreEmpleado(res.getString("nombreEmpleado"));

                    usuario.setIdPerfil(res.getInt("idPerfil"));
                    usuario.setNombrePerfil(res.getString("nombrePerfil"));

                    usuario.setCantidadIntentos(res.getInt("cantidadIntentos"));
                    usuario.setDiferenciaMinutos(res.getInt("minutos"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return usuario;
    }

    public UsuarioDTO getUsuarioByIdPerfil(Integer idPerfil) {
        UsuarioDTO usuario = null;
        try {
            String sql = " SELECT u.idUsuario, u.nombreUsuario, u.password, u.estado,  "
                    + " p.idPerfil , p.nombrePerfil, "
                    + " e.idEmpleado, e.rut, concat_ws(' ', e.nombres, e.apellidos) as nombreEmpleado, "
                    + " iu.cantidadIntentos, TIMESTAMPDIFF(MINUTE, iu.fecha_ultimo_intento, NOW()) as minutos"
                    + " FROM usuario u "
                    + " INNER JOIN perfil p ON u.idPerfil = p.idPerfil "
                    + " INNER JOIN intento_usuario iu ON u.idUsuario = iu.idUsuario "
                    + " INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado WHERE 1 = 1 AND p.idPerfil = " + idPerfil + " ORDER BY u.nombreUsuario ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuario(res.getInt("idUsuario"));
                    usuario.setNombreUsuario(res.getString("nombreUsuario"));
                    usuario.setPassword(res.getString("password"));
                    usuario.setEstado(res.getInt("estado"));

                    usuario.setIdEmpleado(res.getInt("idEmpleado"));
                    usuario.setRut(res.getString("rut"));
                    usuario.setNombreEmpleado(res.getString("nombreEmpleado"));

                    usuario.setIdPerfil(res.getInt("idPerfil"));
                    usuario.setNombrePerfil(res.getString("nombrePerfil"));

                    usuario.setCantidadIntentos(res.getInt("cantidadIntentos"));
                    usuario.setDiferenciaMinutos(res.getInt("minutos"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return usuario;
    }

    public UsuarioDTO getUsuarioByEmail(String correo) {
        UsuarioDTO usuario = null;
        try {
            String sql = " SELECT u.idUsuario, u.nombreUsuario, u.password, u.estado,  "
                    + " p.idPerfil , p.nombrePerfil, "
                    + " e.idEmpleado, e.rut, concat_ws(' ', e.nombres, e.apellidos) as nombreEmpleado, "
                    + " iu.cantidadIntentos, TIMESTAMPDIFF(MINUTE, iu.fecha_ultimo_intento, NOW()) as minutos"
                    + " FROM usuario u "
                    + " INNER JOIN perfil p ON u.idPerfil = p.idPerfil "
                    + " INNER JOIN intento_usuario iu ON u.idUsuario = iu.idUsuario "
                    + " INNER JOIN empleado e ON u.idEmpleado = e.idEmpleado WHERE 1 = 1 AND e.correo = '" + correo + "' ORDER BY u.nombreUsuario ASC";//DESC y ASC
            ResultSet res = conector.getResultSet(sql);

            while (res.next()) {
                try {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuario(res.getInt("idUsuario"));
                    usuario.setNombreUsuario(res.getString("nombreUsuario"));
                    usuario.setPassword(res.getString("password"));
                    usuario.setEstado(res.getInt("estado"));

                    usuario.setIdEmpleado(res.getInt("idEmpleado"));
                    usuario.setRut(res.getString("rut"));
                    usuario.setNombreEmpleado(res.getString("nombreEmpleado"));

                    usuario.setIdPerfil(res.getInt("idPerfil"));
                    usuario.setNombrePerfil(res.getString("nombrePerfil"));

                    usuario.setCantidadIntentos(res.getInt("cantidadIntentos"));
                    usuario.setDiferenciaMinutos(res.getInt("minutos"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conector.close();
        }

        return usuario;
    }

}
