/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
//import BASEDATO.SERVIDOR.ConnPostgres_SER;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_caja_detalle;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.claNombreEstatico;
import IMPRESORA_POS.PosImprimir_Compra;
import IMPRESORA_POS.PosImprimir_Gasto;
import IMPRESORA_POS.PosImprimir_Vale;
import IMPRESORA_POS.PosImprimir_Venta;
import IMPRESORA_POS.PosImprimir_venta_alquiler;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.print.PrintException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class FrmCajaDetalle_alquiler extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmCajaDetalle
     */
    Connection conn = null;
    
    private static String fecha_emision;
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
//    ConnPostgres_SER conPsSER = new ConnPostgres_SER();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    DAO_caja_detalle_alquilado cdao = new DAO_caja_detalle_alquilado();
    PosImprimir_venta_alquiler posven = new PosImprimir_venta_alquiler();
//    PosImprimir_Gasto posgas = new PosImprimir_Gasto();
//    PosImprimir_Vale posval = new PosImprimir_Vale();
//    PosImprimir_Compra poscomp = new PosImprimir_Compra();
    claNombreEstatico nom_sta=new claNombreEstatico();
    ConnPostgres cpt = new ConnPostgres();
    cla_color_pelete clacolor = new cla_color_pelete();
    public static caja_detalle_alquilado caja = new caja_detalle_alquilado();
    private String tabla_origen_ALQUILER="ALQUILER";
    void abrir() {
        conn = cpt.getConnPosgres();
        this.setTitle("CAJA DETALLE ALQUILER");
        cdao.actualizar_tabla_caja_detalle_alquilado(conn, tblcaja_resumen);
        evetbl.centrar_formulario_internalframa(this);
        color_formulario();
    }

    void color_formulario() {
        panel_principal.setBackground(clacolor.getColor_insertar_primario());
        panel_venta.setBackground(clacolor.getColor_insertar_secundario());
        panel_compra.setBackground(clacolor.getColor_insertar_secundario());
        panel_gasto.setBackground(clacolor.getColor_insertar_secundario());
        panel_vale.setBackground(clacolor.getColor_insertar_secundario());
    }

    void seleccionar_caja_detalle() {
        if (!evejt.getBoolean_validar_select(tblcaja_resumen)) {
            fecha_emision = evejt.getString_select(tblcaja_resumen, 0);
            actualizar_caja_detalle_venta(fecha_emision, tabla_origen_ALQUILER,"c.fk_idventa_alquiler",tblcaja_venta, txtcantidad_venta_efectivo, jFtotal_venta_contado);
//            actualizar_caja_detalle_otros(fecha_emision, "VENTA_EFECTIVO", "c.monto_alquilado_efectivo", tblcaja_venta_efectivo,txtcantidad_venta_efectivo,jFtotal_venta_efectivo);
//            actualizar_caja_detalle_otros(fecha_emision, "VENTA_TARJETA", "c.monto_alquilado_tarjeta", tblcaja_venta_tarjeta,txtcantidad_venta_tarjeta,jFtotal_venta_tarjeta);
            actualizar_caja_detalle_otros(fecha_emision, nom_sta.getTabla_origen_vale(),"c.fk_idvale" ,"c.monto_vale", tblcaja_vale, txtcantidad_vale, jFtotal_vale);
            actualizar_caja_detalle_otros(fecha_emision, nom_sta.getTabla_origen_gasto(),"c.fk_idgasto", "c.monto_gasto", tblcaja_gasto, txtcantidad_gasto, jFtotal_gasto);
            actualizar_caja_detalle_otros(fecha_emision, nom_sta.getTabla_origen_compra_contado(),"c.fk_idcompra", "c.monto_compra_contado", tblcaja_compra, txtcantidad_compra, jFtotal_compra);
            actualizar_caja_detalle_otros(fecha_emision, nom_sta.getTabla_origen_compra_credito(),"c.fk_idcompra", "c.monto_compra_credito", tblcaja_compra_credito, txtcantidad_compra_credito, jFtotal_compra_credito);
            actualizar_caja_detalle_otros(fecha_emision, nom_sta.getTabla_origen_recibo(),"c.fk_idrecibo_pago_cliente", "c.monto_recibo_pago", tblcaja_recibo, txtcantidad_recibo_comp, jFtotal_recibo_comp);
            caja_detalle_saldo(fecha_emision);
        }        
    }
    
    void actualizar_caja_detalle_otros(String fecha_emision, String origen_tabla,String id_origen, String campo_total, JTable tabla, JTextField txtcantidad, JFormattedTextField jftotal) {
        String sql = "select "+id_origen+",to_char(c.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha_emision,c.descripcion,"
                + "TRIM(to_char(" + campo_total + ",'999G999G999')) as monto,estado \n"
                + " from caja_detalle_alquilado c \n"
                + "where date(c.fecha_emision)='" + fecha_emision + "'\n"
                + "and c.tabla_origen='" + origen_tabla + "'\n"
                + "order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tabla);
        caja_detalle_cantidad_total(fecha_emision, origen_tabla, campo_total, txtcantidad, jftotal);
        anchotabla_caja_detalle_otros(tabla);
        
    }

    void actualizar_caja_detalle_venta(String fecha_emision, String origen_tabla, String id_origen, JTable tabla, JTextField txtcantidad, JFormattedTextField jftotal) {
        String sql = "select "+id_origen+",to_char(c.fecha_emision,'yyyy-MM-dd HH24:MI') as fecha_emision,c.descripcion,"
                + "TRIM(to_char(c.monto_alquilado_efectivo,'999G999G999')) as v_efectivo,"
                + "TRIM(to_char(c.monto_alquilado_tarjeta,'999G999G999')) as v_tarjeta,"
                + "TRIM(to_char(c.monto_alquilado_transferencia,'999G999G999')) as v_transfe,"
                + "TRIM(to_char(c.monto_alquilado_credio,'999G999G999')) as v_credito,"
                + "c.tabla_origen as origen,"
                + "estado \n"
                + " from caja_detalle_alquilado c \n"
                + "where date(c.fecha_emision)='" + fecha_emision + "'\n"
                + "and c.tabla_origen='" + origen_tabla + "'\n"
                + "order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tabla);
        String campo_total="c.monto_alquilado_efectivo+c.monto_alquilado_tarjeta+c.monto_alquilado_transferencia";
        caja_detalle_cantidad_total(fecha_emision, origen_tabla, campo_total, txtcantidad, jftotal);
        caja_detalle_cantidad_total(fecha_emision, origen_tabla, "c.monto_alquilado_credio", txtcantidad, jFtotal_venta_credito);
//        anchotabla_caja_detalle_otros(tabla);
        anchotabla_caja_detalle_venta(tabla);
        
    }

    void anchotabla_caja_detalle_otros(JTable tabla) {
        int Ancho[] = {8, 17, 52, 10, 13};
        evejt.setAnchoColumnaJtable(tabla, Ancho);
    }

    void anchotabla_caja_detalle_venta(JTable tabla) {
        int Ancho[] = {5, 12, 30, 8,8,8,8,9,12};
        evejt.setAnchoColumnaJtable(tabla, Ancho);
    }

    void caja_detalle_saldo(String fecha_emision) {
        String titulo = "caja_detalle_saldo";
        String sql = "select sum(monto_alquilado_efectivo+monto_alquilado_tarjeta+monto_alquilado_transferencia+monto_apertura_caja+monto_recibo_pago) as ingreso,"
                + "sum(monto_compra_contado+monto_gasto+monto_vale) as egreso, \n"
                + "         sum((monto_alquilado_efectivo+monto_alquilado_tarjeta+monto_alquilado_transferencia+monto_apertura_caja+monto_recibo_pago)-"
                + "   (monto_compra_contado+monto_gasto+monto_vale)) as saldo\n"
                + "from caja_detalle_alquilado "
                + "where date(fecha_emision)='" + fecha_emision + "'\n"
                + "and estado!='ANULADO'\n";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int ingreso = rs.getInt("ingreso");
                int egreso = rs.getInt("egreso");
                int saldo = rs.getInt("saldo");
                jFcaja_ingreso.setValue(ingreso);
                jFcaja_egreso.setValue(egreso);
                jFcaja_saldo.setValue(saldo);
                if (saldo < 0) {
                    jFcaja_saldo.setBackground(Color.YELLOW);
                } else {
                    jFcaja_saldo.setBackground(Color.WHITE);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    void caja_detalle_cantidad_total(String fecha_emision, String origen_tabla, String campo_total, JTextField txtcantidad, JFormattedTextField jftotal) {
        String titulo = "caja_detalle_cantidad_total";
        String sql = "select count(*) as cantidad,sum(" + campo_total + ") as total\n"
                + " from caja_detalle_alquilado c \n"
                + "where date(c.fecha_emision)='" + fecha_emision + "'\n"
                + "and c.estado!='ANULADO'\n"
                + "and c.tabla_origen='" + origen_tabla + "'";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                String cantidad = rs.getString("cantidad");
                txtcantidad.setText(cantidad);
                int total = rs.getInt("total");
                jftotal.setValue(total);
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public FrmCajaDetalle_alquiler() {
        initComponents();
        abrir();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_principal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcaja_resumen = new javax.swing.JTable();
        btnactualizar_tabla = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jFcaja_ingreso = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFcaja_egreso = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFcaja_saldo = new javax.swing.JFormattedTextField();
        panel_venta = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblcaja_venta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtcantidad_venta_efectivo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jFtotal_venta_contado = new javax.swing.JFormattedTextField();
        btnimprimir_venta = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jFtotal_venta_credito = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        panel_compra = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblcaja_compra = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtcantidad_compra = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jFtotal_compra = new javax.swing.JFormattedTextField();
        btnimprimir_compra = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        panel_gasto = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblcaja_gasto = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtcantidad_gasto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jFtotal_gasto = new javax.swing.JFormattedTextField();
        btnimprimir_GASTO = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panel_vale = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcaja_vale = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtcantidad_vale = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jFtotal_vale = new javax.swing.JFormattedTextField();
        btnimprimir_vale = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        panel_compra1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblcaja_compra_credito = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtcantidad_compra_credito = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jFtotal_compra_credito = new javax.swing.JFormattedTextField();
        btnimprimir_compra_credito = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        panel_compra2 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblcaja_recibo = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txtcantidad_recibo_comp = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jFtotal_recibo_comp = new javax.swing.JFormattedTextField();
        btnimprimir_compra_credito1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("CAJA DETALLE");

        panel_principal.setBackground(new java.awt.Color(204, 204, 255));
        panel_principal.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA CAJA"));

        tblcaja_resumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblcaja_resumen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcaja_resumenMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblcaja_resumen);

        btnactualizar_tabla.setText("ACTUALIZAR TABLA");
        btnactualizar_tabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_tablaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("INGRESO:");

        jFcaja_ingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFcaja_ingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_ingreso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("EGRESO:");

        jFcaja_egreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFcaja_egreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_egreso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("SALDO:");

        jFcaja_saldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFcaja_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcaja_saldo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel_principalLayout = new javax.swing.GroupLayout(panel_principal);
        panel_principal.setLayout(panel_principalLayout);
        panel_principalLayout.setHorizontalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnactualizar_tabla)
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFcaja_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFcaja_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFcaja_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_principalLayout.setVerticalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar_tabla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jFcaja_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jFcaja_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jFcaja_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("PRINCIPAL", panel_principal);

        panel_venta.setBackground(new java.awt.Color(204, 204, 255));
        panel_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA VENTA"));

        tblcaja_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblcaja_venta);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("CANT:");

        txtcantidad_venta_efectivo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("TOTAL CONTADO:");

        jFtotal_venta_contado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_venta_contado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_contado.setText("0");
        jFtotal_venta_contado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnimprimir_venta.setText("IMPRIMIR TICKET VENTA");
        btnimprimir_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ventaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("TOTAL CREDITO:");

        jFtotal_venta_credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_venta_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_credito.setText("0");
        jFtotal_venta_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout panel_ventaLayout = new javax.swing.GroupLayout(panel_venta);
        panel_venta.setLayout(panel_ventaLayout);
        panel_ventaLayout.setHorizontalGroup(
            panel_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(panel_ventaLayout.createSequentialGroup()
                .addComponent(btnimprimir_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_venta_contado, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_venta_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );
        panel_ventaLayout.setVerticalGroup(
            panel_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ventaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtcantidad_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jFtotal_venta_contado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir_venta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFtotal_venta_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))))
        );

        jTabbedPane1.addTab("VENTA", panel_venta);

        panel_compra.setBackground(new java.awt.Color(204, 204, 255));
        panel_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA_COMPRA"));

        tblcaja_compra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tblcaja_compra);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("CANTIDAD:");

        txtcantidad_compra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("TOTAL:");

        jFtotal_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra.setText("0");
        jFtotal_compra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnimprimir_compra.setText("IMPRIMIR TICKET COMPRA");
        btnimprimir_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_compraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_compraLayout = new javax.swing.GroupLayout(panel_compra);
        panel_compra.setLayout(panel_compraLayout);
        panel_compraLayout.setHorizontalGroup(
            panel_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
            .addGroup(panel_compraLayout.createSequentialGroup()
                .addComponent(btnimprimir_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
                .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_compraLayout.setVerticalGroup(
            panel_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_compraLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(btnimprimir_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcantidad_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1134, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_compra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_compra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("COMPRA", jPanel5);

        panel_gasto.setBackground(new java.awt.Color(204, 204, 255));
        panel_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA_GASTO"));

        tblcaja_gasto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tblcaja_gasto);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("CANTIDAD:");

        txtcantidad_gasto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("TOTAL:");

        jFtotal_gasto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_gasto.setText("0");
        jFtotal_gasto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnimprimir_GASTO.setText("IMPRIMIR TICKET GASTO");
        btnimprimir_GASTO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_GASTOActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_gastoLayout = new javax.swing.GroupLayout(panel_gasto);
        panel_gasto.setLayout(panel_gastoLayout);
        panel_gastoLayout.setHorizontalGroup(
            panel_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
            .addGroup(panel_gastoLayout.createSequentialGroup()
                .addComponent(btnimprimir_GASTO, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
                .addComponent(jFtotal_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_gastoLayout.setVerticalGroup(
            panel_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_gastoLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(btnimprimir_GASTO, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcantidad_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jFtotal_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1134, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_gasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_gasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("GASTOS", jPanel9);

        panel_vale.setBackground(new java.awt.Color(204, 204, 255));
        panel_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA_VALE"));

        tblcaja_vale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblcaja_vale);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("CANTIDAD:");

        txtcantidad_vale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("TOTAL:");

        jFtotal_vale.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_vale.setText("0");
        jFtotal_vale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnimprimir_vale.setText("IMPRIMIR TICKET VALE");
        btnimprimir_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_valeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_valeLayout = new javax.swing.GroupLayout(panel_vale);
        panel_vale.setLayout(panel_valeLayout);
        panel_valeLayout.setHorizontalGroup(
            panel_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(panel_valeLayout.createSequentialGroup()
                .addComponent(btnimprimir_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
                .addComponent(jFtotal_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_valeLayout.setVerticalGroup(
            panel_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_valeLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_valeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtcantidad_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jFtotal_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir_vale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1134, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_vale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("VALE", jPanel3);

        panel_compra1.setBackground(new java.awt.Color(204, 204, 255));
        panel_compra1.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA_COMPRA"));

        tblcaja_compra_credito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tblcaja_compra_credito);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("CANTIDAD:");

        txtcantidad_compra_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("TOTAL:");

        jFtotal_compra_credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_compra_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra_credito.setText("0");
        jFtotal_compra_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnimprimir_compra_credito.setText("IMPRIMIR TICKET COMPRA");
        btnimprimir_compra_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_compra_creditoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_compra1Layout = new javax.swing.GroupLayout(panel_compra1);
        panel_compra1.setLayout(panel_compra1Layout);
        panel_compra1Layout.setHorizontalGroup(
            panel_compra1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
            .addGroup(panel_compra1Layout.createSequentialGroup()
                .addComponent(btnimprimir_compra_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_compra_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
                .addComponent(jFtotal_compra_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_compra1Layout.setVerticalGroup(
            panel_compra1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_compra1Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_compra1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel16)
                    .addComponent(btnimprimir_compra_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcantidad_compra_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jFtotal_compra_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1134, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_compra1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_compra1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("COMPRA-CREDITO", jPanel1);

        panel_compra2.setBackground(new java.awt.Color(204, 204, 255));
        panel_compra2.setBorder(javax.swing.BorderFactory.createTitledBorder("RECIBO PAGO COMPRA"));

        tblcaja_recibo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tblcaja_recibo);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("CANTIDAD:");

        txtcantidad_recibo_comp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("TOTAL:");

        jFtotal_recibo_comp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_recibo_comp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_recibo_comp.setText("0");
        jFtotal_recibo_comp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnimprimir_compra_credito1.setText("IMPRIMIR TICKET RECIBO");
        btnimprimir_compra_credito1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_compra_credito1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_compra2Layout = new javax.swing.GroupLayout(panel_compra2);
        panel_compra2.setLayout(panel_compra2Layout);
        panel_compra2Layout.setHorizontalGroup(
            panel_compra2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
            .addGroup(panel_compra2Layout.createSequentialGroup()
                .addComponent(btnimprimir_compra_credito1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcantidad_recibo_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
                .addComponent(jFtotal_recibo_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_compra2Layout.setVerticalGroup(
            panel_compra2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_compra2Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_compra2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(btnimprimir_compra_credito1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcantidad_recibo_comp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jFtotal_recibo_comp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1134, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_compra2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_compra2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RECIBO-PAG-COMPRA", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnactualizar_tablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_tablaActionPerformed
        // TODO add your handling code here:
        cdao.actualizar_tabla_caja_detalle_alquilado(conn, tblcaja_resumen);
    }//GEN-LAST:event_btnactualizar_tablaActionPerformed

    private void tblcaja_resumenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcaja_resumenMouseReleased
        // TODO add your handling code here:
        seleccionar_caja_detalle();
    }//GEN-LAST:event_tblcaja_resumenMouseReleased

    private void btnimprimir_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ventaActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblcaja_venta)) {
            int idventa = evejt.getInt_select_id(tblcaja_venta);
            posven.boton_imprimir_pos_VENTA(conn, idventa);
        }
    }//GEN-LAST:event_btnimprimir_ventaActionPerformed

    private void btnimprimir_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_valeActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblcaja_vale)) {
            int idvale = evejt.getInt_select_id(tblcaja_vale);
//            posval.boton_imprimir_pos_VALE(conn, idvale);
        }
    }//GEN-LAST:event_btnimprimir_valeActionPerformed

    private void btnimprimir_GASTOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_GASTOActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblcaja_gasto)) {
            int idgasto = evejt.getInt_select_id(tblcaja_gasto);
//            posgas.boton_imprimir_pos_GASTO(conn, idgasto);
        }
    }//GEN-LAST:event_btnimprimir_GASTOActionPerformed

    private void btnimprimir_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_compraActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblcaja_compra)) {
            int idcompra = evejt.getInt_select_id(tblcaja_compra);
//            poscomp.boton_imprimir_pos_compra(conn, idcompra);
        }
    }//GEN-LAST:event_btnimprimir_compraActionPerformed

    private void btnimprimir_compra_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_compra_creditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnimprimir_compra_creditoActionPerformed

    private void btnimprimir_compra_credito1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_compra_credito1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnimprimir_compra_credito1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_tabla;
    private javax.swing.JButton btnimprimir_GASTO;
    private javax.swing.JButton btnimprimir_compra;
    private javax.swing.JButton btnimprimir_compra_credito;
    private javax.swing.JButton btnimprimir_compra_credito1;
    private javax.swing.JButton btnimprimir_vale;
    private javax.swing.JButton btnimprimir_venta;
    private javax.swing.JFormattedTextField jFcaja_egreso;
    private javax.swing.JFormattedTextField jFcaja_ingreso;
    private javax.swing.JFormattedTextField jFcaja_saldo;
    private javax.swing.JFormattedTextField jFtotal_compra;
    private javax.swing.JFormattedTextField jFtotal_compra_credito;
    private javax.swing.JFormattedTextField jFtotal_gasto;
    private javax.swing.JFormattedTextField jFtotal_recibo_comp;
    private javax.swing.JFormattedTextField jFtotal_vale;
    private javax.swing.JFormattedTextField jFtotal_venta_contado;
    private javax.swing.JFormattedTextField jFtotal_venta_credito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_compra;
    private javax.swing.JPanel panel_compra1;
    private javax.swing.JPanel panel_compra2;
    private javax.swing.JPanel panel_gasto;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JPanel panel_vale;
    private javax.swing.JPanel panel_venta;
    private javax.swing.JTable tblcaja_compra;
    private javax.swing.JTable tblcaja_compra_credito;
    private javax.swing.JTable tblcaja_gasto;
    private javax.swing.JTable tblcaja_recibo;
    private javax.swing.JTable tblcaja_resumen;
    private javax.swing.JTable tblcaja_vale;
    private javax.swing.JTable tblcaja_venta;
    private javax.swing.JTextField txtcantidad_compra;
    private javax.swing.JTextField txtcantidad_compra_credito;
    private javax.swing.JTextField txtcantidad_gasto;
    private javax.swing.JTextField txtcantidad_recibo_comp;
    private javax.swing.JTextField txtcantidad_vale;
    private javax.swing.JTextField txtcantidad_venta_efectivo;
    // End of variables declaration//GEN-END:variables
}
