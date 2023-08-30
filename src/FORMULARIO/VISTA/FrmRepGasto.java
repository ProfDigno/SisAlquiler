/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import CONFIGURACION.EvenDatosPc;
import ESTADO.EvenEstado;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.PosImprimir_Gasto;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmRepGasto extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private gasto ENTgas = new gasto();
    private BO_gasto BOgas = new BO_gasto();
    private DAO_gasto DAOgas = new DAO_gasto();
    private caja_detalle caja = new caja_detalle();
    private usuario usu = new usuario();
    private EvenUtil eveut = new EvenUtil();
    private EvenDatosPc evepc = new EvenDatosPc();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenJtable evejta = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private EvenFecha evefec = new EvenFecha();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private PosImprimir_Gasto posgas = new PosImprimir_Gasto();
    Connection conn = ConnPostgres.getConnPosgres();
    private cla_color_pelete clacolor= new cla_color_pelete();
     private EvenEstado eveest = new EvenEstado();
    private int fk_idgasto_tipo;
    private int idgasto;
    private double monto_gasto;
    private String indice_gasto;
    String tabla_gasto="GASTO";
//    private String indice;
    private int tipo_filtro;

    /**
     * Creates new form FrmZonaDelivery
     */
    private void abrir_formulario() {
        this.setTitle(tabla_gasto);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        color_formulario();
    }
    private void color_formulario(){
        panel_tabla_gasto.setBackground(clacolor.getColor_tabla());
    }
    private String filtro_gasto(int tipo){
        String ocultarAnulado = "";
        String filtro = "";
        String fecha = "";
        
        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
            String fecdesde = evefec.getString_cambiar_formato(txtfecha_desde.getText());
            String fechasta = evefec.getString_cambiar_formato(txtfecha_hasta.getText());
            fecha = " and date(g.fecha_emision)>=date('" + fecdesde + "') and date(g.fecha_emision)<=date('" + fechasta + "')";
        }
        if (tipo == 2) {
            filtro = fecha + ocultarAnulado;
        }
        if (tipo == 3) {
            if ((txtbuscar_descripcion.getText().trim().length() > 0)) {
                String descrip = txtbuscar_descripcion.getText();
                filtro = " and g.descripcion ilike '%" + descrip + "%' " + fecha + ocultarAnulado;
            }
        }
        if (tipo == 4) {
            if ((txtbuscar_tipo.getText().trim().length() > 0)) {
                String gtipo = txtbuscar_tipo.getText();
                filtro = " and gt.nombre ilike '%" + gtipo + "%' " + fecha + ocultarAnulado;
            }
        }
        return filtro;
    }
    private void actualizar_gasto(int tipo) {
        tipo_filtro=tipo;
        String orden = " order by g.fecha_emision desc";
        DAOgas.actualizar_tabla_gasto(conn, tblgasto, filtro_gasto(tipo_filtro), orden);
        double suma_monto = DAOgas.sumar_monto_gasto(conn, filtro_gasto(tipo_filtro));
        jFsuma_monto.setValue(suma_monto);
        double suma_cantidad = DAOgas.sumar_cantidad_gasto(conn, filtro_gasto(tipo_filtro));
        jFsuma_cantidad.setValue(suma_cantidad);
    }


    private void boton_imprimir() {
        if (!evejta.getBoolean_validar_select(tblgasto)) {
            posgas.boton_imprimir_pos_GASTO(conn, idgasto);
        }
    }

    private void seleccionar_tabla() {
        DAOgas.cargar_gasto(ENTgas, tblgasto);
        idgasto = ENTgas.getIdgasto();
        fk_idgasto_tipo = ENTgas.getFk_idgasto_tipo();
    }

    private void reestableser() {
        idgasto = eveconn.getInt_ultimoID_mas_uno(conn, ENTgas.getTabla(), ENTgas.getIdtabla());
        indice_gasto = eveut.getString_crear_indice();
        fk_idgasto_tipo = 0;
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha_barra());
        actualizar_gasto(2);
    }

    private void boton_nuevo() {
        reestableser();
    }

    private void boton_imprimir_filtro_gasto(){
        DAOgas.imprimir_filtro_gasto_excel(conn, filtro_gasto(tipo_filtro));
    }
    public FrmRepGasto() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_tabla_gasto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblgasto = new javax.swing.JTable();
        jFsuma_monto = new javax.swing.JFormattedTextField();
        jFsuma_cantidad = new javax.swing.JFormattedTextField();
        btnimprimir_filtro_gasto = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtfecha_desde = new javax.swing.JTextField();
        txtfecha_hasta = new javax.swing.JTextField();
        btnbuscar_fecha = new javax.swing.JButton();
        txtbuscar_descripcion = new javax.swing.JTextField();
        txtbuscar_tipo = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        panel_tabla_gasto.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblgasto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblgasto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgasto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgastoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblgasto);

        jFsuma_monto.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA MONTO:"));
        jFsuma_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_monto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFsuma_cantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        jFsuma_cantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFsuma_cantidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnimprimir_filtro_gasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_filtro_gasto.setText("FILTRO GASTO");
        btnimprimir_filtro_gasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_filtro_gastoActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO"));

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.setBorder(javax.swing.BorderFactory.createTitledBorder("FEC. DESDE:"));
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.setBorder(javax.swing.BorderFactory.createTitledBorder("FEC. HASTA:"));
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        btnbuscar_fecha.setText("BUSCAR FECHA ");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        txtbuscar_descripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Descripcion:"));
        txtbuscar_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_descripcionKeyReleased(evt);
            }
        });

        txtbuscar_tipo.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Tipo "));
        txtbuscar_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_tipoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_fecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(284, 284, 284))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtfecha_desde)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtfecha_hasta)
                        .addComponent(txtbuscar_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtbuscar_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_tabla_gastoLayout = new javax.swing.GroupLayout(panel_tabla_gasto);
        panel_tabla_gasto.setLayout(panel_tabla_gastoLayout);
        panel_tabla_gastoLayout.setHorizontalGroup(
            panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnimprimir_filtro_gasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        panel_tabla_gastoLayout.setVerticalGroup(
            panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnimprimir_filtro_gasto)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_gasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_tabla_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOgas.ancho_tabla_gasto(tblgasto);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtbuscar_tipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_tipoKeyReleased
        // TODO add your handling code here:
        actualizar_gasto(4);
    }//GEN-LAST:event_txtbuscar_tipoKeyReleased

    private void txtbuscar_descripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_descripcionKeyReleased
        // TODO add your handling code here:
        actualizar_gasto(3);
    }//GEN-LAST:event_txtbuscar_descripcionKeyReleased

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        actualizar_gasto(2);
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_gasto(2);
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void btnimprimir_filtro_gastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_filtro_gastoActionPerformed
        // TODO add your handling code here:
        boton_imprimir_filtro_gasto();
    }//GEN-LAST:event_btnimprimir_filtro_gastoActionPerformed

    private void tblgastoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgastoMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblgastoMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_filtro_gasto;
    private javax.swing.JFormattedTextField jFsuma_cantidad;
    private javax.swing.JFormattedTextField jFsuma_monto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_tabla_gasto;
    private javax.swing.JTable tblgasto;
    private javax.swing.JTextField txtbuscar_descripcion;
    private javax.swing.JTextField txtbuscar_tipo;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
