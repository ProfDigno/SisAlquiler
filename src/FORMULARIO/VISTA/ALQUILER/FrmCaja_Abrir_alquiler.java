/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.EvenDatosPc;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Utilitario.EvenUtil;
import FORMULARIO.BO.BO_caja_cierre;
import FORMULARIO.BO.BO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.DAO.DAO_grupo_credito_cliente;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.usuario;
import IMPRESORA_POS.PosImprimir_Venta;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmCaja_Abrir_alquiler extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmCaja_Cierre
     */
    PosImprimir_Venta posv = new PosImprimir_Venta();
    Connection connLocal = null;
    ConnPostgres cpt = new ConnPostgres();
    EvenJTextField evejtf = new EvenJTextField();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    caja_cierre_alquilado cjcie = new caja_cierre_alquilado();
    usuario usu = new usuario();
    private caja_detalle_alquilado cdalq = new caja_detalle_alquilado();
    EvenJFRAME evetbl = new EvenJFRAME();
    BO_caja_cierre_alquilado bocjcie=new BO_caja_cierre_alquilado();
    cla_color_pelete clacolor= new cla_color_pelete();
    private DAO_caja_detalle_alquilado cdalq_dao = new DAO_caja_detalle_alquilado();
    private DAO_grupo_credito_cliente gccDAO = new DAO_grupo_credito_cliente();
    private String tabla_origen = "CAJA_ABRIR";
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO="Abierto";
    void abrir_formulario(){
        this.setTitle("CAJA ABRIR ALQUILER");
        connLocal = cpt.getConnPosgres();
        color_formulario();
        evetbl.centrar_formulario_internalframa(this);
    }
    void color_formulario(){
        panel_insert.setBackground(clacolor.getColor_shopp());
    }
    void cargar_datos_caja_cierre(){
        cjcie.setC4estado(estado_ABIERTO);
        cjcie.setC5fk_idusuario(usu.getGlobal_idusuario());
    }
    void cargar_datos_caja() {
        cdalq_dao.limpiar_caja_detalle_alquilado(cdalq);
        cdalq.setC3descripcion("(VENTA-ALQUILER) CAJA ABRIR:");
        cdalq.setC4tabla_origen(tabla_origen);
        cdalq.setC5estado(estado_EMITIDO);
        cdalq.setC16monto_apertura_caja(evejtf.getDouble_format_nro_entero(txtmonto_caja_abrir));
    }
    void boton_caja_cierre(){
        if(txtmonto_caja_abrir.getText().trim().length()>0){
            cargar_datos_caja();
            cargar_datos_caja_cierre();
            bocjcie.insertar_caja_cierre_alquilado(cjcie, cdalq);
            this.dispose();
        }
    }
    public FrmCaja_Abrir_alquiler() {
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

        panel_insert = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtmonto_caja_abrir = new javax.swing.JTextField();
        btncaja_abrir = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CAJA ABRIR ALQUILER");

        txtmonto_caja_abrir.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        txtmonto_caja_abrir.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtmonto_caja_abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmonto_caja_abrirActionPerformed(evt);
            }
        });
        txtmonto_caja_abrir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_caja_abrirKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_caja_abrirKeyReleased(evt);
            }
        });

        btncaja_abrir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btncaja_abrir.setText("CAJA ABRIR");
        btncaja_abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaja_abrirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncaja_abrir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtmonto_caja_abrir, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_caja_abrir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncaja_abrir, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtmonto_caja_abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmonto_caja_abrirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmonto_caja_abrirActionPerformed

    private void btncaja_abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaja_abrirActionPerformed
        // TODO add your handling code here:
        boton_caja_cierre();
    }//GEN-LAST:event_btncaja_abrirActionPerformed

    private void txtmonto_caja_abrirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_abrirKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtmonto_caja_abrir);
    }//GEN-LAST:event_txtmonto_caja_abrirKeyReleased

    private void txtmonto_caja_abrirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_caja_abrirKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_caja_cierre();
        }
    }//GEN-LAST:event_txtmonto_caja_abrirKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncaja_abrir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JTextField txtmonto_caja_abrir;
    // End of variables declaration//GEN-END:variables
}