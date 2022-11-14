/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Archivos;

import Config_JSON.json_array_conexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Digno
 */
public class EvenArchivo {

    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    json_array_conexion jscon=new json_array_conexion();
//    int dias=Integer.parseInt(jscon.getLimite_dia_eliminar());
    int limite_dia_eliminar=-3;   
    public void eliminar_archivos_de_carpeta_fechalimite(String ruta) {
        String titulo = "eliminar_archivos_de_carpeta_fechalimite";
        try {
            File file = new File(ruta);
            eliminar_archivo_por_fecha(file);
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo + " ->" + ruta);
        }
    }

    private void eliminar_archivo_por_fecha(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { 
            for (File archivo : files) {
                String nombre = archivo.getName().toString();
                long lastModified = archivo.lastModified();
                String formato_fecha = "yyyy-MM-dd hh:mm aa";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato_fecha);
                Date date_archivo = new Date(lastModified);
                String fecha_creado = simpleDateFormat.format(date_archivo);
                Calendar calendat_fecha_limite = Calendar.getInstance();
                calendat_fecha_limite.add(Calendar.DATE, limite_dia_eliminar);
                Date date_fecha_limite = calendat_fecha_limite.getTime();
                if(date_archivo.before(date_fecha_limite)){
                    System.out.println("Eliminar: "+ nombre + " ->" + fecha_creado);
                    archivo.delete();
                }else{
                    System.out.println("Mantener: "+ nombre + " ->" + fecha_creado);
                }
            }
        }
//        folder.delete();
    }

    private void abrirArchivo(String ruta) {
        try {
            File file = new File(ruta);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
