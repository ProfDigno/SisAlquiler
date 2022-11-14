/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class json_config_json {
private EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    public json_config_json() {
        setPassword("4650586");
    }

    public static String password;

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        json_config_json.password = password;
    }

    public void abrirArchivo(String ruta) {
        String pass = JOptionPane.showInputDialog(null, "PARA CONFIGURAR SE NECESITA PASSWORD\n" + ruta, "PASSWORD JSON", JOptionPane.ERROR_MESSAGE);
        if (pass != null) {
            if (pass.equals(getPassword())) {
                try {
                    File file = new File(ruta);
                    Desktop.getDesktop().open(file);
                    JOptionPane.showMessageDialog(null, "EL SISTEMA SE DEBE CERRAR PARA CORREGIR EL JSON ", "CERRAR SISTEMA", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.toString(), ruta, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "PASSWORD INCORRECTO ", "ERROR DE PASSWORD", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    public boolean getboo_ejecutar_por_password() {
        boolean ejecutar=false;
        String pass = JOptionPane.showInputDialog(null, "PARA EJECUTAR SE NECESITA PASSWORD\n", "PASSWORD", JOptionPane.ERROR_MESSAGE);
        if (pass != null) {
            if (pass.equals(getPassword())) {
                ejecutar=true;
            } else {
                JOptionPane.showMessageDialog(null, "PASSWORD INCORRECTO ", "ERROR DE PASSWORD", JOptionPane.WARNING_MESSAGE);
            }
        }
        return ejecutar;
    }
}
