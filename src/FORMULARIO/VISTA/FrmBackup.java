/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmBackup extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    backup bac = new backup();
    BO_backup bBO = new BO_backup();
    DAO_backup bdao = new DAO_backup();
    EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("BACKUP");
        evetbl.centrar_formulario_internalframa(this);
        boton_guardar();
    }

    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtb3direc_dump, "DEBE CARGAR UNA DIRECCION DUMP")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtb4direc_backup, "DEBE CARGAR UNA DIRECCION BACKUP")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtb5basedato, "DEBE CARGAR UNA BASEDATO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtb6localhost, "DEBE CARGAR UN LOCALHOST")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtb7puerto, "DEBE CARGAR UN PUERTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtb8usuario, "DEBE CARGAR UN USUARIO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtb9senha, "DEBE CARGAR UNA SENHA")) {
            return false;
        }
        return true;
    }

    void boton_guardar() {
        if (bdao.getBoolean_backup_existente(conn)) {
            String inicio = "xxxx";
            bac.setB1idbackup(1);
            bac.setB3direc_dump(inicio);
            bac.setB4direc_backup(inicio);
            bac.setB5basedato(inicio);
            bac.setB6localhost(inicio);
            bac.setB7puerto(inicio);
            bac.setB8usuario(inicio);
            bac.setB9senha(inicio);
            bac.setB10cantidad(1);
            bBO.insertar_backup(bac);
            cargar_datos_backup();
        } else {
            cargar_datos_backup();
        }
    }

    void boton_editar() {
        if (validar_guardar()) {
            bac.setB1idbackup(1);
            bac.setB3direc_dump(txtb3direc_dump.getText());
            bac.setB4direc_backup(txtb4direc_backup.getText());
            bac.setB5basedato(txtb5basedato.getText());
            bac.setB6localhost(txtb6localhost.getText());
            bac.setB7puerto(txtb7puerto.getText());
            bac.setB8usuario(txtb8usuario.getText());
            bac.setB9senha(txtb9senha.getText());
            bac.setB10cantidad(Integer.parseInt(txtb10cantidad.getText()+1));
            bBO.update_backup(bac);
        }
    }

    private void cargar_datos_backup() {
        bdao.cargar_backup(bac);
        txtb3direc_dump.setText(bac.getB3direc_dump());
        txtb4direc_backup.setText(bac.getB4direc_backup());
        txtb5basedato.setText(bac.getB5basedato());
        txtb6localhost.setText(bac.getB6localhost());
        txtb7puerto.setText(bac.getB7puerto());
        txtb8usuario.setText(bac.getB8usuario());
        txtb9senha.setText(bac.getB9senha());
        txtb10cantidad.setText(String.valueOf(bac.getB10cantidad()));
        btneditar.setEnabled(true);
    }

    public FrmBackup() {
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
        jLabel2 = new javax.swing.JLabel();
        txtb3direc_dump = new javax.swing.JTextField();
        btneditar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtb4direc_backup = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtb5basedato = new javax.swing.JTextField();
        txtb6localhost = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtb7puerto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtb8usuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtb9senha = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtb10cantidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

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

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("DUMP:");

        txtb3direc_dump.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb3direc_dump.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb3direc_dumpKeyPressed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("BACKUP:");

        txtb4direc_backup.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb4direc_backup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb4direc_backupKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("BASEDATO:");

        txtb5basedato.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb5basedato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb5basedatoKeyPressed(evt);
            }
        });

        txtb6localhost.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb6localhost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb6localhostKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("LOCALHOST:");

        txtb7puerto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb7puerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb7puertoKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("PUERTO:");

        txtb8usuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb8usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb8usuarioKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("USUARIO:");

        txtb9senha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb9senha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb9senhaKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("SENHA:");

        txtb10cantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtb10cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtb10cantidadKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("CANTIDAD:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtb9senha, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtb7puerto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtb5basedato, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtb6localhost, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtb8usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtb10cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtb3direc_dump)
                            .addComponent(txtb4direc_backup))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtb3direc_dump, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtb4direc_backup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtb5basedato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtb6localhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtb7puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtb8usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtb9senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtb10cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btneditar)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void txtb3direc_dumpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb3direc_dumpKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtb3direc_dumpKeyPressed

    private void txtb4direc_backupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb4direc_backupKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb4direc_backupKeyPressed

    private void txtb5basedatoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb5basedatoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb5basedatoKeyPressed

    private void txtb6localhostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb6localhostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb6localhostKeyPressed

    private void txtb7puertoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb7puertoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb7puertoKeyPressed

    private void txtb8usuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb8usuarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb8usuarioKeyPressed

    private void txtb9senhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb9senhaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb9senhaKeyPressed

    private void txtb10cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtb10cantidadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtb10cantidadKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtb10cantidad;
    private javax.swing.JTextField txtb3direc_dump;
    private javax.swing.JTextField txtb4direc_backup;
    private javax.swing.JTextField txtb5basedato;
    private javax.swing.JTextField txtb6localhost;
    private javax.swing.JTextField txtb7puerto;
    private javax.swing.JTextField txtb8usuario;
    private javax.swing.JTextField txtb9senha;
    // End of variables declaration//GEN-END:variables
}