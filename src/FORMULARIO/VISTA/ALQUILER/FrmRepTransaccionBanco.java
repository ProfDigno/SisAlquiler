/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_recibo_pago_cliente;
import FORMULARIO.DAO.DAO_transaccion_banco;
import FORMULARIO.DAO.DAO_venta_alquiler;
import FORMULARIO.ENTIDAD.cliente;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRepTransaccionBanco extends javax.swing.JInternalFrame {

    private String nombre_formulario = "TRANSACCION BANCO";
    private EvenJFRAME evetbl = new EvenJFRAME();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenJtable evejt = new EvenJtable();
    private DAO_venta_alquiler DAOva = new DAO_venta_alquiler();
    private DAO_transaccion_banco DAOtb=new DAO_transaccion_banco();
//    private DAO_recibo_pago_cliente DAOrpc=new DAO_recibo_pago_cliente();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenCombobox evecmb = new EvenCombobox();
    private DAO_cliente DAOcl = new DAO_cliente();
    private cliente ENTcl = new cliente();
    private boolean esfiltro_por_fecha_direc;
    private boolean esfiltro_por_fecha;
    private boolean hab_carga_dato_banco = false;
    private int fk_idbanco;

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        cargar_dato_banco();
        reestableser();
        actualizar_tabla();
        evefec.cargar_combobox_directo(jCfiltro_direco);
        esfiltro_por_fecha_direc = true;
        esfiltro_por_fecha = false;
    }
    private void cargar_dato_banco() {
        evecmb.cargarCombobox(conn, cmbdato_banco, "db.fk_idbanco", "b.nombre||'-'||db.nro_cuenta", "banco b,dato_banco db",
                "where db.fk_idbanco=b.idbanco and db.activo=true ");
        hab_carga_dato_banco = true;
    }

    private void select_dato_banco() {
        if (hab_carga_dato_banco) {
            fk_idbanco = evecmb.getInt_seleccionar_COMBOBOX_where(conn, cmbdato_banco, "db.fk_idbanco",
                    "b.nombre,'-',db.nro_cuenta", "banco b,dato_banco db", "and db.fk_idbanco=b.idbanco and db.activo=true ");
            System.out.println("fk_idbanco:" + fk_idbanco);
        }
    }
    private String filtro_venta(){
        String campo_fecha="tb.fecha_transaccion";
        String filtro_suma = "";
        String filtro_fecha = "";
        String filtro_fecha_direc = "";
        String filtro_banco="";
        if (esfiltro_por_fecha_direc) {
            filtro_fecha_direc = evefec.getFechaDirecto_combobox(jCfiltro_direco, campo_fecha);
        }
        if (esfiltro_por_fecha) {
            if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
//                String fecdesde = evefec.getString_validar_fecha(txtfecha_desde.getText());
//                String fechasta = evefec.getString_validar_fecha(txtfecha_hasta.getText());
//                txtfecha_desde.setText(fecdesde);
//                txtfecha_hasta.setText(fechasta);
                String fecdesde = evefec.getString_cambiar_formato(txtfecha_desde.getText());
                String fechasta = evefec.getString_cambiar_formato(txtfecha_hasta.getText());
                filtro_fecha = " and date("+campo_fecha+") >= '" + fecdesde + "' and date("+campo_fecha+") <= '" + fechasta + "' \n";
            }
        }
        if(cmbdato_banco.getSelectedIndex()>0){
            filtro_banco=" and b.idbanco="+fk_idbanco;
        }
        filtro_suma = filtro_fecha_direc +filtro_fecha+filtro_banco;
        return filtro_suma;
    }
    private void actualizar_tabla() {
        DAOtb.actualizar_tabla_transaccion_banco_filtro(conn, tblfiltro_transaccion, filtro_venta());
        double monto_total = evejt.getDouble_sumar_tabla(tblfiltro_transaccion, 9);
        jFmonto_total.setValue(monto_total);
    }

    private void reestableser() {
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha_barra());
    }
    private void boton_venta_alquiler_imprimir_orden() {
        DAOva.seleccionar_imprimir_venta_alquiler_tabla(conn, tblfiltro_transaccion);
    }
    private void boton_transaccion_banco_imprimir() {
        DAOtb.imprimir_filtro_transaccion_banco(conn, filtro_venta());
    }
    public FrmRepTransaccionBanco() {
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
        tblfiltro_transaccion = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jCfiltro_direco = new javax.swing.JComboBox<>();
        txtfecha_desde = new javax.swing.JTextField();
        txtfecha_hasta = new javax.swing.JTextField();
        btnbuscar_fecha = new javax.swing.JButton();
        cmbdato_banco = new javax.swing.JComboBox<>();
        btnimprimir_venta_alquiler_1 = new javax.swing.JButton();
        btnimprimir_filtro_transaccion = new javax.swing.JButton();
        jFmonto_total = new javax.swing.JFormattedTextField();

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

        tblfiltro_transaccion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblfiltro_transaccion);

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO VENTA ALQUILER"));

        jCfiltro_direco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfiltro_direcoActionPerformed(evt);
            }
        });

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

        cmbdato_banco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbdato_banco.setBorder(javax.swing.BorderFactory.createTitledBorder("MI BANCO"));
        cmbdato_banco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbdato_bancoItemStateChanged(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbdato_banco, 0, 299, Short.MAX_VALUE)
                .addGap(511, 511, 511))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCfiltro_direco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnbuscar_fecha))
                            .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cmbdato_banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        btnimprimir_venta_alquiler_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_venta_alquiler_1.setText("VENTA ALQUILER");
        btnimprimir_venta_alquiler_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_venta_alquiler_1ActionPerformed(evt);
            }
        });

        btnimprimir_filtro_transaccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_filtro_transaccion.setText("FILTRO TRANSACCION ");
        btnimprimir_filtro_transaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_filtro_transaccionActionPerformed(evt);
            }
        });

        jFmonto_total.setEditable(false);
        jFmonto_total.setBackground(new java.awt.Color(255, 255, 153));
        jFmonto_total.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO TOTAL:"));
        jFmonto_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_venta_alquiler_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_filtro_transaccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnimprimir_venta_alquiler_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimir_filtro_transaccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOrpc.ancho_tabla_recibo_pago_cliente_filtro(tblfiltro_transaccion);
        DAOtb.ancho_tabla_transaccion_banco_filtro(tblfiltro_transaccion);
    }//GEN-LAST:event_formInternalFrameOpened

    private void jCfiltro_direcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfiltro_direcoActionPerformed
        // TODO add your handling code here:
        esfiltro_por_fecha_direc = true;
        esfiltro_por_fecha = false;
        actualizar_tabla();
    }//GEN-LAST:event_jCfiltro_direcoActionPerformed

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

    private void btnimprimir_filtro_transaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_filtro_transaccionActionPerformed
        // TODO add your handling code here:
        boton_transaccion_banco_imprimir();
    }//GEN-LAST:event_btnimprimir_filtro_transaccionActionPerformed

    private void cmbdato_bancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbdato_bancoItemStateChanged
        // TODO add your handling code here:
        select_dato_banco();
        actualizar_tabla();
    }//GEN-LAST:event_cmbdato_bancoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_filtro_transaccion;
    private javax.swing.JButton btnimprimir_venta_alquiler_1;
    private javax.swing.JComboBox<String> cmbdato_banco;
    private javax.swing.JComboBox<String> jCfiltro_direco;
    private javax.swing.JFormattedTextField jFmonto_total;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblfiltro_transaccion;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_hasta;
    // End of variables declaration//GEN-END:variables
}
