/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_venta_alquiler;
import FORMULARIO.ENTIDAD.cliente;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRepVentaAlquiler extends javax.swing.JInternalFrame {

    private String nombre_formulario = "REPORTE VENTA ALQUILER";
    private EvenJFRAME evetbl = new EvenJFRAME();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenJtable evejt = new EvenJtable();
    private DAO_venta_alquiler DAOva = new DAO_venta_alquiler();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private DAO_cliente DAOcl = new DAO_cliente();
    private cliente ENTcl = new cliente();
    private EvenRender everende = new EvenRender();
    private boolean esfiltro_por_fecha_direc;
    private boolean esfiltro_por_fecha;

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        actualizar_tabla();
        evefec.cargar_combobox_directo(jCfiltro_direco);
        esfiltro_por_fecha_direc = true;
        esfiltro_por_fecha = false;
    }
    private String filtro_venta(){
        String campo_fecha="va.fecha_retirado_real";
        String filtro_suma = "";
        String filtro_fecha = "";
        String filtro_fecha_direc = "";
        String filtro_estado = auxvent.filtro_estado_venta_alquiler(jCestado_emitido, jCestado_alquilado, jCestado_devolucion, jCestado_anulado);
        if (esfiltro_por_fecha_direc) {
            filtro_fecha_direc = evefec.getFechaDirecto_combobox(jCfiltro_direco, campo_fecha);
        }
        if (esfiltro_por_fecha) {
            if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
                String fecdesde = evefec.getString_cambiar_formato(txtfecha_desde.getText());
                String fechasta = evefec.getString_cambiar_formato(txtfecha_hasta.getText());
                filtro_fecha = " and date("+campo_fecha+") >= '" + fecdesde + "' and date("+campo_fecha+") <= '" + fechasta + "' \n";
            }
        }
        filtro_suma = filtro_fecha_direc +filtro_fecha+ filtro_estado;
        return filtro_suma;
    }
    private void actualizar_tabla() {
        
        DAOva.actualizar_tabla_venta_alquiler_rep_1(conn, tblfiltro_venta_alquiler, filtro_venta());
        double monto_total = evejt.getDouble_sumar_tabla(tblfiltro_venta_alquiler, 10);
        double monto_pagado = evejt.getDouble_sumar_tabla(tblfiltro_venta_alquiler, 11);
        double monto_saldo = evejt.getDouble_sumar_tabla(tblfiltro_venta_alquiler, 12);
        jFmonto_total.setValue(monto_total);
        jFmonto_pagado.setValue(monto_pagado);
        jFmonto_saldo.setValue(monto_saldo);
    }

    private void reestableser() {
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha_barra());
        jCestado_emitido.setSelected(true);
        jCestado_alquilado.setSelected(true);
        jCestado_devolucion.setSelected(true);
        jCestado_anulado.setSelected(false);
    }
    private void boton_venta_alquiler_imprimir_orden() {
        DAOva.seleccionar_imprimir_venta_alquiler_tabla(conn, tblfiltro_venta_alquiler);
    }
    private void boton_filtro_venta_alquiler_imprimir() {
        DAOva.imprimir_filtro_venta_alquiler_simple(conn, filtro_venta());
    }
    public FrmRepVentaAlquiler() {
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblfiltro_venta_alquiler = new javax.swing.JTable();
        jFmonto_total = new javax.swing.JFormattedTextField();
        jFmonto_pagado = new javax.swing.JFormattedTextField();
        jFmonto_saldo = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jCfiltro_direco = new javax.swing.JComboBox<>();
        panel_estado = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jCestado_devolucion = new javax.swing.JCheckBox();
        jCestado_alquilado = new javax.swing.JCheckBox();
        txtfecha_desde = new javax.swing.JTextField();
        txtfecha_hasta = new javax.swing.JTextField();
        btnbuscar_fecha = new javax.swing.JButton();
        btnimprimir_venta_alquiler_1 = new javax.swing.JButton();
        btnimprimir_filtro_venta_alquiler = new javax.swing.JButton();

        setClosable(true);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA VENTA ALQUILER"));

        tblfiltro_venta_alquiler.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblfiltro_venta_alquiler);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
        );

        jFmonto_total.setEditable(false);
        jFmonto_total.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO TOTAL:"));
        jFmonto_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFmonto_pagado.setEditable(false);
        jFmonto_pagado.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO PAGADO:"));
        jFmonto_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_pagado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFmonto_saldo.setEditable(false);
        jFmonto_saldo.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO SALDO:"));
        jFmonto_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_saldo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO VENTA ALQUILER"));

        jCfiltro_direco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfiltro_direcoActionPerformed(evt);
            }
        });

        panel_estado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO VENTA"));

        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        jCestado_devolucion.setText("DEVOLUCION");
        jCestado_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_devolucionActionPerformed(evt);
            }
        });

        jCestado_alquilado.setText("ALQUILADO");
        jCestado_alquilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_alquiladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_estadoLayout = new javax.swing.GroupLayout(panel_estado);
        panel_estado.setLayout(panel_estadoLayout);
        panel_estadoLayout.setHorizontalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCestado_emitido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_alquilado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCestado_devolucion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCestado_anulado))
        );
        panel_estadoLayout.setVerticalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_alquilado)
                    .addComponent(jCestado_devolucion)
                    .addComponent(jCestado_anulado))
                .addGap(26, 26, 26))
        );

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.setBorder(javax.swing.BorderFactory.createTitledBorder("Desde"));
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasta"));
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        btnbuscar_fecha.setText("BUSCAR");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCfiltro_direco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(478, 478, 478))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCfiltro_direco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnbuscar_fecha))
                            .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        btnimprimir_venta_alquiler_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_venta_alquiler_1.setText("VENTA ALQUILER");
        btnimprimir_venta_alquiler_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_venta_alquiler_1ActionPerformed(evt);
            }
        });

        btnimprimir_filtro_venta_alquiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_filtro_venta_alquiler.setText("FILTRO VENTA ALQUILER");
        btnimprimir_filtro_venta_alquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_filtro_venta_alquilerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_venta_alquiler_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_filtro_venta_alquiler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnimprimir_venta_alquiler_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimir_filtro_venta_alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOva.ancho_tabla_venta_alquiler_rep_1(tblfiltro_venta_alquiler);
    }//GEN-LAST:event_formInternalFrameOpened

    private void jCfiltro_direcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfiltro_direcoActionPerformed
        // TODO add your handling code here:
        esfiltro_por_fecha_direc = true;
        esfiltro_por_fecha = false;
        actualizar_tabla();
    }//GEN-LAST:event_jCfiltro_direcoActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla();
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla();
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void jCestado_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_devolucionActionPerformed
        // TODO add your handling code here:
        actualizar_tabla();
    }//GEN-LAST:event_jCestado_devolucionActionPerformed

    private void jCestado_alquiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_alquiladoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla();
    }//GEN-LAST:event_jCestado_alquiladoActionPerformed

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        esfiltro_por_fecha_direc = false;
        esfiltro_por_fecha = true;
        actualizar_tabla();
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void btnimprimir_venta_alquiler_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_venta_alquiler_1ActionPerformed
        // TODO add your handling code here:
        boton_venta_alquiler_imprimir_orden();
    }//GEN-LAST:event_btnimprimir_venta_alquiler_1ActionPerformed

    private void btnimprimir_filtro_venta_alquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_filtro_venta_alquilerActionPerformed
        // TODO add your handling code here:
        boton_filtro_venta_alquiler_imprimir();
    }//GEN-LAST:event_btnimprimir_filtro_venta_alquilerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_filtro_venta_alquiler;
    private javax.swing.JButton btnimprimir_venta_alquiler_1;
    private javax.swing.JCheckBox jCestado_alquilado;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_devolucion;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JComboBox<String> jCfiltro_direco;
    private javax.swing.JFormattedTextField jFmonto_pagado;
    private javax.swing.JFormattedTextField jFmonto_saldo;
    private javax.swing.JFormattedTextField jFmonto_total;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_estado;
    private javax.swing.JTable tblfiltro_venta_alquiler;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}