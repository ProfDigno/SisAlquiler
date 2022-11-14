/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import CONFIGURACION.ComputerInfo;
import Evento.Fecha.EvenFecha;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_array_conexion {

    private String ruta_json = "JSON\\json_array_conexion.json";
    EvenFecha evefec = new EvenFecha();
    private EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    private json_config_json jsoncf=new json_config_json();
    ComputerInfo pcinf=new ComputerInfo();
    private static String nombre;
    private static String localhost;
    private static String port;
    private static String basedato;
    private static String usuario;
    private static String password;
//    private static String MacAddress_maquina;
    private static String nombre_computador;
    private static String direc_dump;
    private static String direc_backup;
    private static String nombre_backup;
    private static String limite_dia_eliminar;
    private static String crear_backup;
    public void cargar_jsom_array() {
//         MacAddress_maquina=pcinf.getMacAddress();
        nombre_computador=pcinf.getNombrePC();
        JSONParser parser = new JSONParser();
        try {
            Object obj_maquina = parser.parse(new FileReader(ruta_json));
            JSONObject jsonObject_maquina = (JSONObject) obj_maquina;
            JSONArray Array_maquina = (JSONArray) jsonObject_maquina.get(nombre_computador);
            Iterator<String> iterator_maquina = Array_maquina.iterator();
            String datos_conexion = String.valueOf(iterator_maquina.next());
            System.out.println(datos_conexion);
            Object obj_conexion = parser.parse(datos_conexion);
            JSONObject jsonObject_conexion = (JSONObject) obj_conexion;
             nombre = (String) jsonObject_conexion.get("nombre");
             localhost = (String) jsonObject_conexion.get("localhost");
             port = (String) jsonObject_conexion.get("port");
             basedato = (String) jsonObject_conexion.get("basedato");
             usuario = (String) jsonObject_conexion.get("usuario");
             password = (String) jsonObject_conexion.get("password");
             direc_dump = (String) jsonObject_conexion.get("direc_dump");
             direc_backup = (String) jsonObject_conexion.get("direc_backup");
             nombre_backup = (String) jsonObject_conexion.get("nombre_backup");
             limite_dia_eliminar = (String) jsonObject_conexion.get("limite_dia_eliminar");
             crear_backup = (String) jsonObject_conexion.get("crear_backup");
            System.out.println(nombre_computador);
            System.out.println(nombre);
            System.out.println(localhost);
            System.out.println(port);
            System.out.println(basedato);
            System.out.println(usuario);
            System.out.println(password);
            System.out.println(direc_dump);
            System.out.println(direc_backup);
            System.out.println(nombre_backup);
            System.out.println(limite_dia_eliminar);
            System.out.println(crear_backup);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString()+"\nNombre Maquina:"+nombre_computador);
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString()+"\nNombre Maquina:"+nombre_computador);
            if(evemen.MensajeGeneral_warning("DESEA ABRIR EL ARCHIVO PARA CAMBIAR EL NOMBRE PARA ESTE EQUIPO","ABRIR JSON","ABRIR","CANCELAR")){
                abrir_este_json_array_conexion();
            }
        } finally {

        }
    }
    public void abrir_este_json_array_conexion(){
        jsoncf.abrirArchivo(ruta_json);
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        json_array_conexion.nombre = nombre;
    }

    public static String getLocalhost() {
        return localhost;
    }

    public static void setLocalhost(String localhost) {
        json_array_conexion.localhost = localhost;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        json_array_conexion.port = port;
    }

    public static String getBasedato() {
        return basedato;
    }

    public static void setBasedato(String basedato) {
        json_array_conexion.basedato = basedato;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        json_array_conexion.usuario = usuario;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        json_array_conexion.password = password;
    }

//    public static String getMacAddress_maquina() {
//        return MacAddress_maquina;
//    }
//
//    public static void setMacAddress_maquina(String MacAddress_maquina) {
//        json_array_conexion.MacAddress_maquina = MacAddress_maquina;
//    }

    public static String getDirec_dump() {
        return direc_dump;
    }

    public static void setDirec_dump(String direc_dump) {
        json_array_conexion.direc_dump = direc_dump;
    }

    public static String getDirec_backup() {
        return direc_backup;
    }

    public static void setDirec_backup(String direc_backup) {
        json_array_conexion.direc_backup = direc_backup;
    }

    public static String getNombre_backup() {
        return nombre_backup;
    }

    public static void setNombre_backup(String nombre_backup) {
        json_array_conexion.nombre_backup = nombre_backup;
    }

    public static String getCrear_backup() {
        return crear_backup;
    }

    public static void setCrear_backup(String crear_backup) {
        json_array_conexion.crear_backup = crear_backup;
    }

    public static String getLimite_dia_eliminar() {
        return limite_dia_eliminar;
    }

    public static void setLimite_dia_eliminar(String limite_dia_eliminar) {
        json_array_conexion.limite_dia_eliminar = limite_dia_eliminar;
    }

    public static String getNombre_computador() {
        return nombre_computador;
    }

    public static void setNombre_computador(String nombre_computador) {
        json_array_conexion.nombre_computador = nombre_computador;
    }

}
