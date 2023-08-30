/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppSheet;

import BASEDATO.EvenConexion;
import Evento.JasperReport.EvenJasperReport;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class DAO_reporte_appsheet {

    private EvenJasperReport rep = new EvenJasperReport();
    private EvenConexion eveconn = new EvenConexion();
    public void crear_tabla_producto_combo_temp(Connection conn) {
        String sql = "DROP TABLE IF EXISTS producto_combo_temp;"
                + "CREATE TEMP TABLE producto_combo_temp AS\n"
                + "SELECT idproducto,(0) as idproducto_combo,nombre,precio_alquiler,(0) as descuento,"
                + "(true) as es_producto,(false) as es_combo "
                + "FROM producto where activar=true; \n"
                + "\n"
                + " INSERT INTO producto_combo_temp \n"
                + " (SELECT (100000+idproducto_combo) as idproducto,idproducto_combo,nombre,precio_alquiler,descuento,"
                + "(false) as es_producto,(true) as es_combo "
                + "FROM producto_combo where activo=true);  ";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void exportar_excel_lista_producto(Connection conn) {
        int band_Height = 20;
        String rutatemp = "AppSheet/EXCEL/producto_combo.xlsx";
        String sql = "SELECT idproducto,idproducto_combo,nombre,"
                + "precio_alquiler,descuento,es_producto,es_combo "
                + "from producto_combo_temp;";
        String direccion = "src/AppSheet/REPORTE/repProducto_Combo.jrxml";
        String titulo = "LISTA PRODUCTO COMBO";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }
    public void exportar_excel_lista_cliente1(Connection conn) {
        int band_Height = 20;
        String rutatemp = "AppSheet/EXCEL/cliente.xlsx";
        String sql = "select idcliente,nombre,direccion,telefono,ruc,tipo from cliente;";
        String direccion = "src/AppSheet/REPORTE/repCliente.jrxml";
        String titulo = "LISTA CLIENTE";
        rep.imprimirExcel_exportar_appsheet_incremental(conn, sql, titulo, direccion, rutatemp, band_Height);
    }
}
