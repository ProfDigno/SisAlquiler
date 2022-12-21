/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.JasperReport;

import Evento.Mensaje.EvenMensajeJoptionpane;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
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
}
