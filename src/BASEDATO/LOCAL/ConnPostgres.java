/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BASEDATO.LOCAL;

//import ClaseUTIL.FunMensaje;
//import ClaseUTIL.SQLejecucion;
//import ClaseUTIL.SQLprepar;
import BASEDATO.EvenConexion;
import Config_JSON.json_array_conexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.io.FileReader;
import java.sql.*;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.Driver;

/**
 * Esta clase se conecta directamente y exclusivamente con postgres pero depende
 * de los datos proporcionado por la base de dato SQLite por eso es importante
 * que los datos de conexion en el SQLite esteen corectos
 *
 * @author Pc
 */
public class ConnPostgres {

     private static Connection connPostgres = null;
    public static String PsDriver= "org.postgresql.Driver";
    public static String PsConexion= "jdbc:postgresql";
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    EvenConexion evconn = new EvenConexion();
    json_array_conexion jscon=new json_array_conexion();

    public String getDatos_conexion() {
        String dato = "";
        dato = "Puerto:" + jscon.getPort() + "/ Basedato:" + jscon.getBasedato() + " ";
        return dato;
    }

    public void ConnectDBpostgres(Connection connConfig, boolean msj) {
        jscon.cargar_jsom_array();
        try {
            String connectString = "" + PsConexion + "://" + jscon.getLocalhost() + ":" + jscon.getPort() + "/" + jscon.getBasedato() + "";
            Class.forName(PsDriver);
            Connection connLocal = DriverManager.getConnection(connectString, jscon.getUsuario(), jscon.getPassword());
            if (connLocal != null) {
                System.out.println("++++++++++++++++Conection a posgrest suceso" + "\n" + PsDriver + "\n" + connectString + "\n" + jscon.getUsuario() + "\n" + jscon.getPassword());
                if (msj) {
                    JOptionPane.showMessageDialog(null, "++Conection a posgrest suceso++" + "\n" + PsDriver + "\n" + connectString + "\n" + jscon.getUsuario());
                }
            }
            setConnPostgres(connLocal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrio un error en la conexion con base de datos"
                    + "\nLocal Host: " + jscon.getLocalhost()
                    + "\nPuerto: " + jscon.getPort()
                    + "\nUsuario: " + jscon.getUsuario()
                    + "\nError: " + e.getMessage(), "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "EL SISTEMA SE VA CERRAR POR FALLA EN LA CONEXION", "ERROR CONEXION", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void cerrar_conexion() {
        try {
            getConnPosgres().close();
            System.out.println("CONEXION CERRADA");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CERRAR\n" + e);
        }
    }

    public void cerrar_todas_conexiones() {
        String sql = "select count(*) as cantidad from pg_stat_activity;";
    }

    /**
     * @return the connPostgres
     */
    public static Connection getConnPosgres() {
//        System.out.println("CONECTADO " + jscon.getBasedato());
        return connPostgres;

    }

    /**
     * @param aConnPostgres the connPostgres to set
     */
    public static void setConnPostgres(Connection aConnPostgres) {
        connPostgres = aConnPostgres;
    }

}
