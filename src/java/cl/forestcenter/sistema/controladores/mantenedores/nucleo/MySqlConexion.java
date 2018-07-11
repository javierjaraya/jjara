package cl.forestcenter.sistema.controladores.mantenedores.nucleo;

import java.sql.*;

public class MySqlConexion {

    private DatoConexion dato;
    private String driver;
    private Connection con;
    private Boolean autoCommit = true;
    private Boolean roolback = false;

    public MySqlConexion(int nDato) {//host , <puerto,> bd, user, contraseña
        switch (nDato) {
            case 1://Conexion local
                dato = new DatoConexion("localhost", "jjara", "root", "");
                break;
            case 2://Conexion otro servidor laboratorio IP Privada
                dato = new DatoConexion("192.168.3.202", "jjara", "jjara", "jWXbunsm");
                break;
            case 3://Conexion mismo servidor IP Virtual
                dato = new DatoConexion("192.168.122.70", "jjara", "jjara", "jWXbunsm");
                break;
        }
        driver = "com.mysql.jdbc.Driver";
    }

    private Connection conectar() {
        Connection con = null;
        try {
            Class.forName(this.driver);
            con = DriverManager.getConnection("jdbc:mysql://" + this.dato.getURL(), this.dato.getUser(), this.dato.getPass());
        } catch (ClassNotFoundException e) {//Error con los driver            
            e.printStackTrace();
        } catch (SQLException e) {//Error al establecer conexion con la BD            
            e.printStackTrace();
        }
        return con;
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                if (!autoCommit) {
                    if (roolback) {
                        con.rollback();
                    } else {
                        con.commit();
                    }
                }
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que llama y ejecuta un procedimiento almacenado INSERT, retornando
     * el ID generado de la nueva row.
     *
     * @param procedimiento nombre del procedimiento
     * @param args parametros opcional.
     * @return id generado en un int, 0 si no se inserto con exito.
     * @author Javier Jara Y
     * @since 02 Febrero 2015
     */
    public int executeInsert(String sql, Object... args) {
        int idGenerado = 0;
        int affectedRows = 0;
        PreparedStatement pstm = null;
        try {
            System.out.println("SQL : " + sql);

            if (con == null || con.isClosed()) {
                //con = cn.getConnection();
                con = conectar();
                con.setAutoCommit(autoCommit);
            }

            pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Object parametro : args) {
                if (parametro instanceof String) {
                    pstm.setString(i, parametro.toString());
                } else if (parametro instanceof Integer) {
                    pstm.setInt(i, ((Integer) parametro).intValue());
                } else if (parametro instanceof Double) {
                    pstm.setDouble(i, ((Double) parametro));
                } else if (parametro == null) {
                    pstm.setNull(i, java.sql.Types.INTEGER);
                } else if (parametro instanceof Date) {
                    pstm.setDate(i, java.sql.Date.valueOf(parametro.toString()));
                }
                i++;
            }

            affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = null;
                rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return idGenerado;

    }

    public int executeUpdate(String sql, Object... args) {
        int affectedRows = 0;
        PreparedStatement pstm = null;
        try {
            System.out.println("SQL : " + sql);

            if (con == null || con.isClosed()) {
                //con = cn.getConnection();
                con = conectar();
                con.setAutoCommit(autoCommit);
            }

            pstm = con.prepareStatement(sql);
            int i = 1;
            for (Object parametro : args) {
                if (parametro instanceof String) {
                    pstm.setString(i, parametro.toString());
                } else if (parametro instanceof Integer) {
                    pstm.setInt(i, ((Integer) parametro).intValue());
                } else if (parametro instanceof Double) {
                    pstm.setDouble(i, ((Double) parametro));
                } else if (parametro == null) {
                    pstm.setNull(i, java.sql.Types.INTEGER);
                } else if (parametro instanceof Date) {
                    pstm.setDate(i, java.sql.Date.valueOf(parametro.toString()));
                }
                i++;
            }

            affectedRows = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return affectedRows;

    }

    public ResultSet getResultSet(String sql, Object... args) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            System.out.println("SQL : " + sql);

            if (con == null || con.isClosed()) {
                //con = cn.getConnection();
                con = conectar();
                con.setAutoCommit(autoCommit);
            }

            pstm = con.prepareStatement(sql);
            int i = 1;
            for (Object parametro : args) {
                if (parametro instanceof String) {
                    pstm.setString(i, parametro.toString());
                } else if (parametro instanceof Integer) {
                    pstm.setInt(i, ((Integer) parametro).intValue());
                }
                if (parametro instanceof Double) {
                    pstm.setDouble(i, ((Double) parametro));
                }
                if (parametro instanceof Date) {
                    pstm.setDate(i, java.sql.Date.valueOf(parametro.toString()));
                }
                i++;
            }

            rs = pstm.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return rs;
    }

    //METODOS PARA PROCEDIMIENTO ALMACENADO
    public ResultSet getResultSetProcedure(String nombreProcedimiento, Object... args) {
        ResultSet rs = null;
        try {
            System.out.println("SQL : " + nombreProcedimiento);

            if (con == null || con.isClosed()) {
                //con = cn.getConnection();
                con = conectar();
                con.setAutoCommit(autoCommit);
            }

            CallableStatement cst = con.prepareCall(nombreProcedimiento);

            int i = 1;
            for (Object parametro : args) {
                if (parametro instanceof String) {
                    cst.setString(i, parametro.toString());
                } else if (parametro instanceof Integer) {
                    cst.setInt(i, ((Integer) parametro).intValue());
                }
                if (parametro instanceof Double) {
                    cst.setDouble(i, ((Double) parametro));
                }
                if (parametro instanceof Boolean) {
                    cst.setBoolean(i, ((boolean) parametro));
                }
                if (parametro instanceof Date) {
                    cst.setDate(i, ((Date) parametro));
                }
                i++;
            }
            rs = cst.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return rs;
    }
}
