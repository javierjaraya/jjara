package cl.forestcenter.sistema.controladores.mantenedores.nucleo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * Clase que establece una relacion directa con la base de datos MYSQL otorgando
 * los metodos principales para el uso de los procedimientos almacenados.
 *
 * la relacion es de bajo nivel por lo que no se recomienda editarla con valores
 * asociados a una base de datos especifica.
 *
 * Nota : llamar el metodo close() una ves que no se usara mas esta clase dentro
 * de un contexto.
 *
 * @author Alejandro Venegas
 * @since 22 Agosto 2012
 */
public class ProcedimientosMYSQL {

    private Context ctx;
    private DataSource cn;
    private Connection con;

//MySqlDSGestionPedidos
    public ProcedimientosMYSQL(String XMLDataSource) {
        try {
            ctx = new javax.naming.InitialContext();
            cn = (DataSource) ctx.lookup("java:/" + XMLDataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public ProcedimientosMYSQL(DataSource ds) {
        cn = ds;
    }

    public ProcedimientosMYSQL(Connection ds) {
        con = ds;
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo interno que prepara la llamada del procedimiento. indicando
     * principalmente la cantidad de parametros que llegaran.
     *
     * @param procedimiento
     * @param args
     * @return
     */
    private String prepareProcedure(String procedimiento, String[] args) {
        String proc = "call " + procedimiento;
        String pars = "";
        String query = proc + "";
        if (args != null && args.length > 0) {
            proc += "(";
            query += "(\"";
            for (String parametro : args) {
                proc += "?,";
                pars += parametro + ",";
                query += parametro + "\",\"";
            }
            if (proc.endsWith(",")) {
                proc = proc.substring(0, proc.length() - 1);
            }
            if (pars.endsWith(",")) {
                pars = pars.substring(0, pars.length() - 1);
            }
            if (query.endsWith(",\"")) {
                query = query.substring(0, query.length() - 2);
            }
            proc += ");";
            query += ");";
        } else {
            proc += "();";
            query += "();";
        }
        System.out.println("[MySQL-Procedure]:" + query);
        return proc;
    }

    /**
     * Procedimiento interno que establece los parametros
     *
     * @param pstm
     * @param args
     * @throws SQLException
     */
    private void cargarArgs(PreparedStatement pstm, String[] args) throws SQLException {
        if (args != null && args.length > 0) {
            int i = 1;
            for (String parametro : args) {
                pstm.setString(i, parametro);
                i++;
            }
        }
    }

    /**
     * Metodo que llama y ejecuta un procedimiento almacenado, retornando el
     * cursor/resultset con el procedimiento mysql.
     *
     * principalmente para ser usando en los procedimientos SELECT. el resultset
     * sera retornado con el cursor en la fila -1
     *
     *
     * @param procedimiento nombre del procedimiento
     * @param args parametros opcional.
     * @return tabla de resultados en un resultset.
     * @author Alejandro Venegas
     * @since 22 Agosto 2012
     */
    public ResultSet getResultSet(String procedimiento, String... args) {

        ResultSet rs = null;
        try {

            if (con == null || con.isClosed()) {
                con = cn.getConnection();
            }
            procedimiento = prepareProcedure(procedimiento, args);
            CallableStatement cs = con.prepareCall(procedimiento);
            cargarArgs(cs, args);

            rs = cs.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return rs;

    }

    /**
     * Metodo que llama y ejecuta un procedimiento almacenado, retornando el
     * primer valor de la primera columna.
     *
     * principalmente para ser usando en los procedimientos que requieran
     * capturar solo un parametro. el resultset sera retornado con el cursor en
     * la fila -1
     *
     *
     * @param procedimiento nombre del procedimiento
     * @param args parametros opcional.
     * @return primer valor de la primera columna.
     * @author Alejandro Venegas
     * @since 27 Septiembre 2012
     */
    public String getPrimerValor(String procedimiento, String... args) {
        String valor = "";
        ResultSet rs = null;
        try {

            if (con == null || con.isClosed()) {
                con = cn.getConnection();
            }
            procedimiento = prepareProcedure(procedimiento, args);
            CallableStatement cs = con.prepareCall(procedimiento);
            cargarArgs(cs, args);

            rs = cs.executeQuery();
            if (rs.next()) {
                valor = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return valor;

    }

    /**
     * Metodo que llama y ejecuta un procedimiento almacenado update, retornando
     * la cantidad de filas afectadas en la actualizacion con el procedimiento
     * mysql,0 si ninguna fila fue afectada.
     *
     *
     * @param procedimiento nombre del procedimiento
     * @param args parametros opcional del procedimiento.
     * @return int con cantidad de filas afectadas
     * @author Alejandro Venegas
     * @since 22 Agosto 2012
     */
    public int executeUpdate(String procedimiento, String... args) {

        int affectedRows = 0;
        try {

            if (con == null || con.isClosed()) {
                con = cn.getConnection();
            }
            procedimiento = prepareProcedure(procedimiento, args);
            CallableStatement cs = con.prepareCall(procedimiento);
            cargarArgs(cs, args);
            affectedRows = cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return affectedRows;

    }

    /**
     * Metodo que llama y ejecuta un procedimiento almacenado INSERT, retornando
     * el ID generado de la nueva row.
     *
     * @param procedimiento nombre del procedimiento
     * @param args parametros opcional.
     * @return id generado en un int, 0 si no se inserto con exito.
     * @author Alejandro Venegas
     * @since 22 Agosto 2012
     */
    public int executeInsert(String procedimiento, String... args) {

        int idGenerado = 0;
        try {

            if (con == null || con.isClosed()) {
                con = cn.getConnection();
            }
            procedimiento = prepareProcedure(procedimiento, args);
            PreparedStatement pstm = con.prepareStatement(procedimiento, Statement.RETURN_GENERATED_KEYS);
            cargarArgs(pstm, args);
            pstm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return idGenerado;

    }

    /**
     * Metodo que llama y ejecuta un procedimiento almacenado INSERT, retornando
     * el ID generado de la nueva row. EL PROCEDIMIENTO ALMACENADO DEBE RETORNAR
     * LA ULTIMA ID GENERADA DE LA TABLA, manualmente.
     *
     * select per_id from perfil order by per_id desc limit 0,1;
     *
     * @param procedimiento nombre del procedimiento
     * @param args parametros opcional.
     * @return id generado en un int, 0 si no se inserto con exito.
     * @author Alejandro Venegas
     * @since 22 Agosto 2012
     */
    public int executeInsertSafe(String procedimiento, String... args) {

        int idGenerado = 0;
        try {

            if (con == null || con.isClosed()) {
                con = cn.getConnection();
            }
            procedimiento = prepareProcedure(procedimiento, args);
            PreparedStatement pstm = con.prepareStatement(procedimiento);
            cargarArgs(pstm, args);
            ResultSet rs = pstm.executeQuery();;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;

    }

    public Connection getCon() {
        try {
            if (con == null || con.isClosed()) {
                con = cn.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public DataSource getCn() {
        return cn;
    }

    public void setCn(DataSource cn) {
        this.cn = cn;
    }
}
