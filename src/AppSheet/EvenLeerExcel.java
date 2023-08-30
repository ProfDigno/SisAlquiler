/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppSheet;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Config_JSON.json_appsheet;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.BO_app_item_pedido;
import FORMULARIO.BO.BO_app_pedido;
import FORMULARIO.DAO.DAO_app_item_pedido;
import FORMULARIO.DAO.DAO_app_pedido;
import FORMULARIO.ENTIDAD.app_item_pedido;
import FORMULARIO.ENTIDAD.app_pedido;
import FORMULARIO.VISTA.ALQUILER.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EvenLeerExcel {

    private json_appsheet jsapp = new json_appsheet();
    private EvenJtable evejt = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private Connection conn = ConnPostgres.getConnPosgres();
    private DAO_app_pedido DAOap = new DAO_app_pedido();
    private app_pedido ENTap = new app_pedido();
    private BO_app_pedido BOap = new BO_app_pedido();
    private DAO_app_item_pedido DAOaip = new DAO_app_item_pedido();
    private app_item_pedido ENTaip = new app_item_pedido();
    private BO_app_item_pedido BOaip = new BO_app_item_pedido();
    String divi_fila = "£";//156
    String divi_columna = "¥";//157
    int nro_hoja_pedido = 0;
    int nro_hoja_item_pedido = 1;

    public void insert_excel_en_tabla_pedido() {
        //(0)-bb595895(1)-17-03-2023(2)-45011(3)-26-03-2023(4)-6(5)-cliente(6)-Barrio Santa Ana(7)-telefono(8)-ruc(9)-Jdhdjsdgdjdh
        String tb_pedido = getS_leer_archivo_excel(nro_hoja_pedido);
        String[] total_tb_pedido = tb_pedido.split(divi_fila);
        DAOap.update_app_pedido_eliminar_todo(conn);
        int contar_fila = 0;
        for (int i = 1; i < total_tb_pedido.length; i++) {
            String fila = total_tb_pedido[i];
            String[] arrayfila = fila.split(divi_columna);
            try {
                boolean es_vencido = Boolean.parseBoolean(arrayfila[13]);
//                System.out.println("es_vencido=" + es_vencido);
                if (!es_vencido) {
                    ENTap.setC1idventa_alquiler(arrayfila[0]);
                    ENTap.setC2fecha_creado(arrayfila[1]);
                    ENTap.setC3fec_evento(arrayfila[3]);
                    ENTap.setC4fk_idcliente(Integer.parseInt(arrayfila[4]));
                    ENTap.setC5cliente(arrayfila[5]);
                    ENTap.setC6direccion(arrayfila[6]);
                    ENTap.setC7telefono(arrayfila[7]);
                    ENTap.setC8ruc(arrayfila[8]);
                    ENTap.setC9observacion(arrayfila[9]);
                    ENTap.setC10estado("NUEVO");
                    ENTap.setC11descuento_gral(Double.parseDouble(arrayfila[10]));
                    ENTap.setC12pago_parcial(Double.parseDouble(arrayfila[11]));//Double.parseDouble(arrayfila[11])
                    ENTap.setC13es_eliminado(false);
                    BOap.insertar_o_update_app_pedido(ENTap);
                }
            } catch (Exception e) {
                System.out.println("Error=" + e);
            }

        }
    }

    public void insert_excel_en_tabla_item_pedido() {
        String tb_pedido = getS_leer_archivo_excel(nro_hoja_item_pedido);
        String[] total_tb_pedido = tb_pedido.split(divi_fila);
        DAOaip.update_app_item_pedido_eliminar_todo(conn);
        for (int i = 1; i < total_tb_pedido.length; i++) {
            String fila = total_tb_pedido[i];
            String[] arrayfila = fila.split(divi_columna);
            try {
                boolean es_vencido = Boolean.parseBoolean(arrayfila[12]);
//                System.out.println("es_vencido=" + es_vencido);
                if (!es_vencido) {
                    ENTaip.setC1iditem_venta_alquiler(arrayfila[0]);
                    ENTaip.setC2cantidad_total(Integer.parseInt(arrayfila[1]));
                    ENTaip.setC3descripcion(arrayfila[2]);
                    ENTaip.setC4precio_alquiler(Double.parseDouble(arrayfila[3]));
                    ENTaip.setC5precio_descuento(Double.parseDouble(arrayfila[4]));
                    ENTaip.setC6es_combo(Boolean.parseBoolean(arrayfila[8]));
                    ENTaip.setC7es_producto(Boolean.parseBoolean(arrayfila[9]));
                    ENTaip.setC8fk_idventa_alquiler(arrayfila[5]);
                    ENTaip.setC9fk_idproducto(Integer.parseInt(arrayfila[6]));
                    ENTaip.setC10fk_idproducto_combo(Integer.parseInt(arrayfila[7]));
                    ENTaip.setC11orden(Integer.parseInt(arrayfila[10]));
                    ENTaip.setC12es_vencido(es_vencido);
                    ENTaip.setC13es_eliminado(false);
                    BOaip.insertar_app_item_pedido(ENTaip);
                }
            } catch (Exception e) {
                System.out.println("Error=" + e);
            }

        }
    }

    public void cargar_excel_en_tabla(JTable tbltabla, DefaultTableModel model_pedido, int nro_hoja) {
        String tb_pedido = getS_leer_archivo_excel(nro_hoja);
        String[] total_tb_pedido = tb_pedido.split(divi_fila);
        for (int i = 0; i < total_tb_pedido.length; i++) {
            String fila = total_tb_pedido[i];
            String[] total_fila = fila.split(divi_columna);
            if (i == 0) {
                evejt.crear_tabla_datos(tbltabla, model_pedido, total_fila);
            } else {
                evejt.cargar_tabla_datos(tbltabla, model_pedido, total_fila);
            }
        }
    }

    public String getS_leer_archivo_excel(int nro_hoja) {
        String sum_archivo_excel = "";
        try {
            FileInputStream archivo_excel = new FileInputStream(jsapp.getDato_pedido_xlsx());
            XSSFWorkbook libro_excel = new XSSFWorkbook(archivo_excel);
            XSSFSheet hoja = libro_excel.getSheetAt(nro_hoja);
            Iterator<Row> toda_fila = hoja.iterator();
            Iterator<Cell> celdas;
            Row fila;
            Cell celda;
            while (toda_fila.hasNext()) {
                fila = toda_fila.next();
                celdas = fila.cellIterator();
                int cant_blanco = 0;
                int cant_column = 0;
                while (celdas.hasNext()) {
                    celda = celdas.next();
                    cant_column++;
                    switch (celda.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            int int_cell = (int) celda.getNumericCellValue();
                            String string_cell = String.valueOf(int_cell);
                            sum_archivo_excel = sum_archivo_excel + string_cell + divi_columna;
                            break;
                        case Cell.CELL_TYPE_STRING:
                            sum_archivo_excel = sum_archivo_excel + celda.getStringCellValue() + divi_columna;
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            sum_archivo_excel = sum_archivo_excel + celda.getBooleanCellValue() + divi_columna;
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            cant_blanco++;
                            break;
                    }
                }
                if (cant_blanco == cant_column) {
                    break;
                } else {
                    sum_archivo_excel = sum_archivo_excel + divi_fila;
                }
            }
            archivo_excel.close();
            libro_excel.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return sum_archivo_excel;
    }
}
