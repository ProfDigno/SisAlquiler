/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import FORMULARIO.VISTA.ALQUILER.FrmCaja_Abrir_alquiler;
import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Config_JSON.json_array_conexion;
//import BASEDATO.SERVIDOR.ConnPostgres_SER;
import Evento.Color.cla_color_pelete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.BO.BO_usuario;
import FORMULARIO.DAO.DAO_backup;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.financista;
import FORMULARIO.ENTIDAD.usuario;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class JDiaLogin extends javax.swing.JDialog {

    /**
     * Creates new form JDiaLogin
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    usuario usu = new usuario();
    BO_usuario uBO = new BO_usuario();
    DAO_usuario pdao = new DAO_usuario();
    DAO_backup bdao = new DAO_backup();
    DAO_venta vdao = new DAO_venta();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
//    ConnPostgres_SER conPsSER = new ConnPostgres_SER();
//    Connection connser = conPsSER.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    caja_cierre cjcie = new caja_cierre();
    caja_cierre_alquilado ccal = new caja_cierre_alquilado();
    caja_detalle_alquilado cdalq = new caja_detalle_alquilado();
    
    DAO_caja_cierre cjcie_dao = new DAO_caja_cierre();
    private DAO_caja_cierre_alquilado ccal_dao = new DAO_caja_cierre_alquilado();
    cla_color_pelete clacolor= new cla_color_pelete();
    private financista fina = new financista();
    private Object estado_CERRADO="CERRADO";
    private json_array_conexion jscon = new json_array_conexion();
    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("INGRESO");
        habilitar_menu(false, false);
        usuario_de_inicio();
        color_formulario();
    }
    void color_formulario(){
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
    }
    private boolean validar_ingreso() {
        if (evejtf.getBoo_JTextField_vacio(txtusuario, "DEBE CARGAR UN USUARIO")) {
            return false;
        }
        if (evejtf.getBoo_JPasswordField_vacio(jPassword, "DEBE CARGAR UNA SENHA")) {
            return false;
        }
        return true;
    }

    private void habilitar_menu(boolean blo, boolean blo2) {
//        FrmMenuAlquiler.btncaja_detalle.setEnabled(blo);
        FrmMenuAlquiler.btncliente.setEnabled(blo2);
        FrmMenuAlquiler.btngasto.setEnabled(blo2);
        FrmMenuAlquiler.btnproducto.setEnabled(blo);
//        FrmMenuAlquiler.btnvale.setEnabled(blo2);
//        FrmMenuAlquiler.btnventa.setEnabled(blo2);
        FrmMenuAlquiler.btncombo.setEnabled(blo);
        FrmMenuAlquiler.btnshopp.setEnabled(blo);
        FrmMenuAlquiler.btncambiar_usuario.setEnabled(blo2);
//        FrmMenuAlquiler.btncomprainsumo.setEnabled(blo2);
        FrmMenuAlquiler.btncotizacion.setEnabled(blo2);
//        FrmMenuAlquiler.jMenu_caja.setEnabled(blo);
        FrmMenuAlquiler.jMenu_cliente.setEnabled(blo);
        FrmMenuAlquiler.jMenu_config.setEnabled(blo);
        FrmMenuAlquiler.jMenu_delivery.setEnabled(blo);
        FrmMenuAlquiler.jMenu_gasto.setEnabled(blo);
        FrmMenuAlquiler.jMenu_producto.setEnabled(blo);
//        FrmMenuAlquiler.jMenu_venta.setEnabled(blo);
//        FrmMenuAlquiler.jMenu_fatura.setEnabled(blo);
//        FrmMenuAlquiler.jMenu_compra.setEnabled(blo);
//        FrmMenuAlquiler.jMenu_inventario.setEnabled(blo);
//        FrmMenuAlquiler.btninventario.setEnabled(blo);
//        FrmMenuAlquiler.btndelivery_venta.setEnabled(blo);
//        FrmMenuAlquiler.btncaja_cierre.setEnabled(blo);
//        FrmMenuAlquiler.btncajacerrar.setEnabled(blo2);
    }

    void buscar_usuario() {
        if (pdao.getBoolean_buscar_usuario_existente(conn, usu)) {
            JOptionPane.showMessageDialog(this, "BIENVENIDO\n" + usu.getGlobal_nombre());
            FrmMenuAlquiler.lblusuario.setText(usu.getGlobal_nombre());
            if (usu.getGlobal_nivel().equals("ADMIN")) {
                habilitar_menu(true, true);
            }
            if (usu.getGlobal_nivel().equals("VENTA")) {
                habilitar_menu(false, true);
            }
            this.dispose();
                if (jscon.getCrear_backup().equals("SI")) {
                evetbl.abrir_TablaJinternal(new FrmCrearBackupJson());
                }
            int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
            if (idcaja_cierre == 0) {
                JOptionPane.showMessageDialog(null, "NO HAY NINGUNA CAJA");
                evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
            } else {
                cjcie.setC1idcaja_cierre(idcaja_cierre);
                cjcie_dao.cargar_caja_cierre(cjcie);
                if (cjcie.getC4estado().equals(estado_CERRADO)) {
                    JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
                    evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
                }
            }
            int idcaja_cierre_alquiler = (eveconn.getInt_ultimoID_max(conn, ccal.getTb_caja_cierre_alquilado(), ccal.getId_idcaja_cierre_alquilado()));
            if (idcaja_cierre_alquiler == 0) {
                JOptionPane.showMessageDialog(null, "NO HAY NINGUNA CAJA ALQUILER");
                evetbl.abrir_TablaJinternal(new FrmCaja_Abrir_alquiler());
            } else {
                ccal.setC1idcaja_cierre_alquilado(idcaja_cierre_alquiler);
                ccal_dao.cargar_caja_cierre_alquilado(conn, ccal, idcaja_cierre_alquiler);
                if (ccal.getC4estado().equals(estado_CERRADO)) {
                    JOptionPane.showMessageDialog(null, "NO HAY CAJA ALQUILER ABIERTA SE DEBE ABRIR UNO NUEVO");
                    evetbl.abrir_TablaJinternal(new FrmCaja_Abrir_alquiler());
                }
            }
        } else {
            txtusuario.setText(null);
            jPassword.setText(null);
            txtusuario.grabFocus();
        }
    }
//    void primer_finanza(){
//        int idfinancista = (eveconn.getInt_ultimoID_mas_uno(conn, fina.getTb_financista(), fina.getId_idfinancista()));
//        if(idfinancista==0){
//            evetbl.abrir_TablaJinternal(new FrmFinancista());
//        }
//    }
    void boton_entrar() {
        if (validar_ingreso()) {
            usu.setU3usuario(txtusuario.getText());
            String senha = String.valueOf(jPassword.getPassword());
            usu.setU4senha(senha);
            buscar_usuario();
        }
    }

    void boton_venta() {
        usu.setU3usuario("v");
        usu.setU4senha("v");
        buscar_usuario();
    }

    void cargar_datos_usuario(String u2nombre, String u3usuario, String u4senha, String u5nivel) {
        usu.setU2nombre(u2nombre);
        usu.setU3usuario(u3usuario);
        usu.setU4senha(u4senha);
        usu.setU5nivel(u5nivel);
    }

    void usuario_de_inicio() {
        if (pdao.getBoolean_cantidad_cero(conn)) {
            cargar_datos_usuario("ADMINISTRADOR", "ADMIN", "ADMIN", "ADMIN");
            uBO.insertar_usuario(usu);
            cargar_datos_usuario("VENDEDOR", "v", "v", "VENTA");
            uBO.insertar_usuario(usu);
        }
    }

    public JDiaLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        jLabel2 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        jPassword = new javax.swing.JPasswordField();
        btnentrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(102, 204, 255));

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE USUARIO"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/REPORTE/ALQUILER/logo1.jpg"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtusuario.setBorder(javax.swing.BorderFactory.createTitledBorder("USUARIO:"));
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusuarioKeyPressed(evt);
            }
        });

        jPassword.setBorder(javax.swing.BorderFactory.createTitledBorder("PASSWORD:"));
        jPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordKeyPressed(evt);
            }
        });

        btnentrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnentrar.setText("ENTRAR");
        btnentrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnentrarActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_user.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_key.png"))); // NOI18N

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnentrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPassword)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnentrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnentrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnentrarActionPerformed
        // TODO add your handling code here:
        boton_entrar();
    }//GEN-LAST:event_btnentrarActionPerformed

    private void txtusuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jPassword.grabFocus();
        }
    }//GEN-LAST:event_txtusuarioKeyPressed

    private void jPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_entrar();
        }
    }//GEN-LAST:event_jPasswordKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaLogin dialog = new JDiaLogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnentrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
