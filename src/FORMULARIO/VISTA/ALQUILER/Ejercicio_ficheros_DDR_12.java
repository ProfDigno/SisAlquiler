/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Ejercicio_ficheros_DDR_12 {
    public static void main(String[] args) {
        try {
            //abrimos el XSSFWorkbook
            FileInputStream f = new FileInputStream("C:/Users/Digno/Dropbox/SISTEMA_LORENS/SisAlquiler/AppSheet/EXCEL/dato_pedido.xlsx");
            XSSFWorkbook libro = new XSSFWorkbook(f);
            //seleccionamos la primera hoja
            XSSFSheet hoja = libro.getSheetAt(1);
            //Cogemos todas las filas de esa hoja
            Iterator<Row> filas = hoja.iterator();
            Iterator<Cell> celdas;
            Row fila;
            Cell celda;
            System.out.println("CELL_TYPE_NUMERIC:"+Cell.CELL_TYPE_NUMERIC);
            System.out.println("CELL_TYPE_STRING:"+Cell.CELL_TYPE_STRING);
            System.out.println("CELL_TYPE_BLANK:"+Cell.CELL_TYPE_BLANK);
            System.out.println("CELL_TYPE_BOOLEAN:"+Cell.CELL_TYPE_BOOLEAN);
            System.out.println("CELL_TYPE_ERROR:"+Cell.CELL_TYPE_ERROR);
            System.out.println("CELL_TYPE_FORMULA:"+Cell.CELL_TYPE_FORMULA);
            //recorremos las filas
            while (filas.hasNext()) {
                //Cogemos la siguiente fila
                fila = filas.next();
                //Cogemos todas las celdas de esa fila
                celdas = fila.cellIterator();
                //REcorremos todas las celdas
                int cant_blanco=0;
                int cant_column=0;
                while (celdas.hasNext()) {
                    //Cogemos la siguiente fila
                    celda = celdas.next();
                    //Segun el tipo de celda, usaremos uno u otra funcion
//                    System.out.print(celda.getCellType()+" ,");
                    cant_column++;
                    switch (celda.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(celda.getNumericCellValue()+" ,");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(celda.getStringCellValue()+" ,");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(celda.getBooleanCellValue()+" ,");
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            cant_blanco++;
                            break;
                    }
                }
                System.out.println();
                if(cant_blanco==cant_column){
                    break;
                }
            }
            //cerramos el libro
            f.close();
            libro.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
