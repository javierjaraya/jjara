/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.forestcenter.sistema.controladores.mantenedores.nucleo;

/**
 *
 * @author Javier J
 */
public class DatoConexion {

    private String host;
    private String puerto;
    private String db;
    private String user;
    private String pass;

    public DatoConexion(String host, String puerto, String db, String user, String pass) {
        this.host = host;
        this.puerto = puerto;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    public DatoConexion(String host, String db, String user, String pass) {
        this.host = host;
        this.db = db;
        this.user = user;
        this.pass = pass;
        this.puerto = "";
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getURL() {
        if (puerto.length() == 0) {
            return this.host + "/" + this.db;
        } else {
            return this.host + ":" + this.puerto + "/" + this.db;
        }
    }
}
