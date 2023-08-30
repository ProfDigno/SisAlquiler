/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JasperReport;

import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * 
 * @author Digno
 */
public class EvenJasperReport {
    EvenMensajeJoptionpane evemen=new EvenMensajeJoptionpane();
    EvenConexion eveconn = new EvenConexion();
    public void imprimirjasper(Connection conexion,String sql,String titulonota,String direccion){
        String titulo="imprimirjasper";
        try{
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,conexion);           
            JasperViewer jviewer = new JasperViewer(jasperPrint,false);
            jviewer.setTitle(titulonota);
            jviewer.setVisible(true); 
            evemen.Imprimir_serial_sql(sql, titulo);
        }catch(Exception e){
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        
   } 
    public void imprimirPDF(Connection conexion, String sql, String direccion, String rutatemp) {
        String titulo = "imprimirPDF";
        String carpeta="REPORTE_PDF/";
        String formato=".pdf";
        String ruta=carpeta+rutatemp+formato;
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperExportManager.exportReportToPdfFile(jasperPrint,ruta);// exportacion PDF
            abrirArchivo(ruta);
            evemen.Imprimir_serial_sql(sql + "\n", titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }
    private void abrirArchivo(String ruta) {
        try {
            File file = new File(ruta);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void imprimirExcel_exportar_appsheet_incremental(Connection conn, String sql, String titulonota, 
            String direccion,String rutatemp,int PageHeight) {
        int cant_fila=getInt_contar_cant_fila(conn, sql);
        if(true){
            System.out.println("titulonota:"+titulonota);
            System.out.println("sql:"+sql);
            System.out.println("direccion:"+direccion);
            System.out.println("rutatemp:"+rutatemp);
        }
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);   
            jasperDesign.setPageHeight(jasperDesign.getPageHeight()+(PageHeight*cant_fila));
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JRExporter exp = new JRXlsxExporter();
            exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exp.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutatemp));
            exp.exportReport();
//            abrirArchivo(rutatemp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    private int getInt_contar_cant_fila(Connection conn,String sql){
        int cantidad=0;
        String titulo="getInt_contar_cant_fila: ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
              cantidad++;
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        System.out.println(titulo+cantidad);
        return cantidad;
    }
    public void imprimirExcel(Connection conexion, String sql, String direccion, String rutatemp) {
        String titulo = "imprimirExcel";
        String carpeta="REPORTE_EXCEL/";
        String formato=".xlsx";
        String ruta=carpeta+rutatemp+formato;
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(direccion);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JRExporter exp = new JRXlsxExporter();
            exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exp.setParameter(JRExporterParameter.OUTPUT_FILE, new File(ruta));
            exp.exportReport();
            abrirArchivo(ruta);
            evemen.Imprimir_serial_sql(sql + "\n", titulo);
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }

    }
}
