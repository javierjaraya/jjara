package cl.forestcenter.sistema.controladores.mantenedores.nucleo;

public abstract class Controlador {

    protected MySqlConexion conector;

    public Controlador() {
        conector = new MySqlConexion(1);
    }

    public String obtenerSqlFinalPaginacion(String sql, int pagina, int cantidad, String order) {
        String sqlFinal = "";
        int inicioPag = 0;
        if (pagina <= 0) {
            sqlFinal = sql;
        } else {
            //LIMITAR EL RESULTADO SEGUN FILA Y CANTIDAD
            inicioPag = ((pagina - 1) * cantidad);
            sqlFinal = sql + " ORDER BY " + order + "  LIMIT " + inicioPag + "," + cantidad;
        }
        return sqlFinal;
    }
    
    public String formatoRut(String rut){
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        return rut;
    }

}
