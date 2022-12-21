/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Jtable;

import ESTADO.EvenEstado;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Digno
 */
public class EvenRender {
    private EvenEstado eveest=new EvenEstado();
    public void rendertabla_item_venta(JTable tblitem_producto) {
        System.out.println("-->rendertabla_item_venta");
        tblitem_producto.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=1;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1="P";
                String campo2="I";
                String campo3="D";
                String campo4="O";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.YELLOW;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.ORANGE;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.PINK;
                    color_text = Color.BLACK;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_estados(JTable tbltabla,final int columna) {
        System.out.println("-->rendertabla_estados");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1="EMITIDO";
                String campo2="TERMINADO";
                String campo3="ANULADO";
                String campo4="CONFIRMADO";
                String campo5="ANULADO_temp";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_tipo_item_inventario(JTable tbltabla,final int columna) {
        System.out.println("-->rendertabla_tipo_item_inventario");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1="0";
                String campo2="P";
                String campo3="N";
                String campo4="CONFIRMADO";
                String campo5="ANULADO_temp";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.GREEN;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_item_alquiler(JTable tblitem_producto) {
        System.out.println("-->rendertabla_item_alquiler");
        tblitem_producto.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=11;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String campo1=eveest.getTp_item_alq_pro();
                String campo2=eveest.getTp_item_alq_combo();
                String campo3=eveest.getTp_item_alq_subcombo();
                String campo4="O";
                if (texto1 != null && campo1.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && campo2.equals(texto1.toString())) {
                    color_fondo = Color.ORANGE;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo3.equals(texto1.toString())) {
                    color_fondo = Color.YELLOW;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo4.equals(texto1.toString())) {
                    color_fondo = Color.PINK;
                    color_text = Color.BLACK;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_estado_alquilado(JTable tbltabla,final int columna) {
        System.out.println("-->rendertabla_estado_alquilado");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String emitido=eveest.getEst_EMITIDO();
                String alquilado=eveest.getEst_ALQUILADO();
                String anulado=eveest.getEst_ANULADO();
                String devolucion=eveest.getEst_DEVOLUCION();
                String campo5="ANULADO_temp";
                if (texto1 != null && emitido.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.RED;
                }
                if (texto1 != null && alquilado.equals(texto1.toString())) {
                    color_fondo = Color.YELLOW;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && anulado.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                if (texto1 != null && devolucion.equals(texto1.toString())) {
                    color_fondo = Color.ORANGE;
                    color_text = Color.BLUE;
                }
                if (texto1 != null && campo5.equals(texto1.toString())) {
                    color_fondo = Color.RED;
                    color_text = Color.YELLOW;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_venta_alquilado_cancelado(JTable tbltabla,final int columna) {
        System.out.println("-->rendertabla_venta_alquilado_cancelado");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String es_cancelado="SI";
                String es_abierto="NO";
                if (texto1 != null && es_cancelado.equals(texto1.toString())) {
                    color_fondo = Color.GRAY;
                    color_text = Color.white;
                }
                if (texto1 != null && es_abierto.equals(texto1.toString())) {
                    color_fondo = Color.yellow;
                    color_text = Color.BLUE;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
    public void rendertabla_producto_combo(JTable tbltabla,final int columna) {
        System.out.println("-->rendertabla_producto_combo");
        tbltabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //************************************************************
                int columnaRender=columna;
                Color color_fondo = Color.WHITE;
                Color color_text = Color.BLACK;
                Object texto1 = table.getValueAt(row, columnaRender);
                String es_true="true";
                String es_false="false";
                if (texto1 != null && es_true.equals(texto1.toString())) {
                    color_fondo = Color.WHITE;
                    color_text = Color.BLACK;
                }
                if (texto1 != null && es_false.equals(texto1.toString())) {
                    color_fondo = Color.GRAY;
                    color_text = Color.white;
                }
                label.setBackground(color_fondo);
                table.setSelectionForeground(color_text);
                
                return label;
            }
        });
    }
}
