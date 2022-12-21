/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Fecha;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class EvenFecha {

    private static String for_fec_DATE = "yyyy-MM-dd";
    //    private static String formato_fechaHora = "yyyy-MM-dd HH:mm";
        private static String formato_fechaHoraZona = "yyyy-MM-dd HH:mm:ss.00";
    private static String for_fec_barra = "dd/MM/yyyy";
    private static String fecha_dia1 = "01/MM/yyyy";
    private static String for_fecHs_barra = "dd/MM/yyyy HH:mm:ss";
    private static String for_fecZona_barra = "dd/MM/yyyy HH:mm:ss.00";
    private static String formato_hora = "HH:mm:ss";

    public static String getFor_fec_barra() {
        return for_fec_barra;
    }

    public static String getFor_fecZona_barra() {
        return for_fecZona_barra;
    }

    public String getString_validar_fecha(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(for_fec_barra);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: dd/MM/yyyy\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha_barra();
        }
        return Sfecha;
    }

    public String getString_cambiar_formato(String oldDateString) {
        String newDateString = "";
        try {
            final String OLD_FORMAT = "dd/MM/yyyy";
            final String NEW_FORMAT = "yyyy-MM-dd"; // August 12, 2010 
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            java.util.Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: " + for_fec_DATE + "\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            newDateString = getString_formato_fecha_barra();
        }
        return newDateString;
    }
    public String getString_cambiar_formato_add_hora(String oldDateString) {
        String newDateString = "";
        String hora=" 12:00:00.00";
        try {
            final String OLD_FORMAT = "dd/MM/yyyy";
            final String NEW_FORMAT = "yyyy-MM-dd"; // August 12, 2010 
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            java.util.Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: " + for_fec_DATE + "\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            newDateString = getString_formato_fecha_barra();
        }
        return newDateString+hora;
    }
//" 12:00:00.00"
    public String getString_validar_fecha_hora(String fechaStr) {
        String Sfecha = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(for_fecHs_barra);
            java.util.Date fechaDate = formato.parse(fechaStr);
            Sfecha = String.valueOf(formato.format(fechaDate));
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: dd/MM/yyyy HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            Sfecha = getString_formato_fecha_hora();
        }
        return Sfecha;
    }

    public java.sql.Date getDate_fecha_hora_cargado(String fechaStr) {
        java.sql.Date dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(for_fecHs_barra);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Date(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: dd/MM/yyyy HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return dateSql;
    }

    public java.sql.Date getDate_fecha_cargado(String fechaStr) {
        java.sql.Date dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(for_fec_barra);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Date(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: dd/MM/yyyy\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        return dateSql;
    }

    public java.sql.Timestamp getTimestamp_fecha_cargado(String fechaStr) {
        String titulo="getTimestamp_fecha_cargado:";
        java.sql.Timestamp dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(for_fecZona_barra);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Timestamp(dateUtil.getTime());
            System.out.println(titulo+fechaStr);
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: dd/MM/yyyy HORA:MINUTO\n" + e+"\nCARGADO:"+fechaStr;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR:"+titulo, JOptionPane.ERROR_MESSAGE);
            dateSql = getTimestamp_sistema();
        }
        return dateSql;
    }
    public java.sql.Timestamp getTimestamp_fecha_for_date(String fechaStr) {
        java.sql.Timestamp dateSql = null;
        java.util.Date dateUtil = new java.util.Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat(formato_fechaHoraZona);
            dateUtil = formato.parse(fechaStr);
            dateSql = new java.sql.Timestamp(dateUtil.getTime());
        } catch (Exception e) {
            String mensaje = "EL FORMATO DE LA FECHA NO ES CORRECTA\n FORMATO: yyyy-MM-dd HORA:MINUTO\n" + e;
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            dateSql = getTimestamp_sistema();
        }
        return dateSql;
    }
    public String getString_formato_fecha_barra() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(for_fec_barra);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }
    public String getString_formato_fecha_DATE() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(for_fec_DATE);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }
    public String getString_formato_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formato_hora);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public int getInt_segundos_ahora() {
        java.util.Date utilDate = new java.util.Date(); //fecha actual
        SimpleDateFormat sdf_hora = new SimpleDateFormat("HH");
        int hora = Integer.parseInt(sdf_hora.format(utilDate));
        SimpleDateFormat sdf_min = new SimpleDateFormat("mm");
        int min = Integer.parseInt(sdf_min.format(utilDate));
        SimpleDateFormat sdf_seg = new SimpleDateFormat("ss");
        int seg = Integer.parseInt(sdf_seg.format(utilDate));
        int resul = ((hora * 3600) + (min * 60) + seg);
        return resul;
    }

    public int getInt_diferencia_en_segundo(int tiempo_sql) {
        int resul = getInt_segundos_ahora() - tiempo_sql;
        return resul;
    }

    public String getString_convertir_segundo_hora(int num) {
        int hor, min, seg;
        hor = num / 3600;
        min = (num - (3600 * hor)) / 60;
        seg = num - ((hor * 3600) + (min * 60));
        String horaformada = hor + "h " + min + "m " + seg + "s";
        System.out.println(horaformada);
        return horaformada;
    }

    public String getString_fecha_dia1() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha_dia1);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_fecha_hora() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(for_fecHs_barra);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public String getString_formato_fecha_hora_zona() {
        String Sfecha;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(for_fecZona_barra);
        Sfecha = String.valueOf(sdf.format(date));
        return Sfecha;
    }

    public java.util.Date getDate_sistema() {
        java.util.Date fechaDate;
        try {
            fechaDate = new java.util.Date();
            return fechaDate;
        } catch (Exception e) {
            System.out.println("" + e);
            return null;
        }
    }

    public java.sql.Date getDateSQL_sistema() {
        java.sql.Date fechaDate;
        try {
            fechaDate = new java.sql.Date(new java.util.Date().getTime());
            return fechaDate;
        } catch (Exception e) {
            System.out.println("" + e);
            return null;
        }
    }

    public Timestamp getTimestamp_sistema() {
        long timeNow = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
        return ts;
    }

    public void cargar_combobox_directo(JComboBox combo) {
        String fechas[] = {"HOY", "AYER",
            "ESTA SEMANA", "SEMANA  ANTERIOR",
            "ESTE MES", "MES ANTERIOR",
            "PRIMER TRIMESTRE", "SEGUNDO TRIMESTRE", "TERCER TRIMESTRE", "CUARTO TRIMESTRE",
        "TODO AÑO"};
        for (int i = 0; i < fechas.length; i++) {
            String fecha = fechas[i];
            combo.addItem(fecha);
        }
    }

    public String getFechaDirecto_combobox(JComboBox combo, String campofecha) {
        //date_part('year',(current_date - interval '1 year')) un ano menos

        String fecha = "";
        if (combo.getSelectedIndex() == 0) {//HOY
            fecha = "\n and date(" + campofecha + ")=date(current_date) ";
        }
        if (combo.getSelectedIndex() == 1) {//AYER
            fecha = "\n and date(" + campofecha + ")=date(current_date-1) ";
        }
        if (combo.getSelectedIndex() == 2) {//ESTA SEMANA
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('week'," + campofecha + ")=date_part('week',current_date) ";
        }
        if (combo.getSelectedIndex() == 3) {//SEMANA  ANTERIOR
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('week'," + campofecha + ")=date_part('week',(current_date - interval '1 week')) ";
        }
        if (combo.getSelectedIndex() == 4) {//ESTE MES
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=date_part('month',current_date) ";
        }
        if (combo.getSelectedIndex() == 5) {//MES ANTERIOR
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('month'," + campofecha + ")=date_part('month',(current_date - interval '1 month')) ";
        }
        if (combo.getSelectedIndex() == 6) {//PRIMER TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=1 ";
        }
        if (combo.getSelectedIndex() == 7) {//SEGUNDO TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=2 ";
        }
        if (combo.getSelectedIndex() == 8) {//TERCER TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=3 ";
        }
        if (combo.getSelectedIndex() == 9) {//CUARTO TRIMESTRE
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)"
                    + "\n and date_part('quarter'," + campofecha + ")=4 ";
        }
        if (combo.getSelectedIndex() == 10) {//todo ano
            fecha = "\n and date_part('year'," + campofecha + ")=date_part('year',current_date)";
        }
        return fecha;
    }
}
