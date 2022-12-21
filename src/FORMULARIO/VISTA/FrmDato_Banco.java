/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Combobox.EvenCombobox;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmDato_Banco extends javax.swing.JInternalFrame {

    private EvenJFRAME evejfr = new EvenJFRAME();
    private EvenJtable evejt = new EvenJtable();
    private dato_banco ENTdb = new dato_banco();
    private BO_dato_banco BOdb = new BO_dato_banco();
    private DAO_dato_banco DAOdb = new DAO_dato_banco();
    private banco ENTb = new banco();
    private BO_banco BOb = new BO_banco();
    private DAO_banco DAOb = new DAO_banco();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    private cla_color_pelete clacolor= new cla_color_pelete();
    private int fk_idbanco;
    private boolean hab_carga_banco;
    
    private void abrir_formulario() {
        this.setTitle("DATO BANCO");
        evejfr.centrar_formulario_internalframa(this);        
        reestableser_db();
        DAOdb.actualizar_tabla_dato_banco(conn, tbldato_banco);
        color_formulario();
        cargar_banco();
    }
    private void cargar_banco(){
        evecmb.cargarCombobox(conn, cmbbanco,"idbanco","nombre","banco","");
        hab_carga_banco=true;
    }
    private void color_formulario(){
        panel_tabla_unidad.setBackground(clacolor.getColor_tabla());
        panel_insertar_unidad.setBackground(clacolor.getColor_insertar_primario());
    }
    private boolean validar_guardar_db() {
        if (evejtf.getBoo_JTextField_vacio(txttitular, "DEBE CARGAR UN TITULAR")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdocumento, "DEBE CARGAR UN DOCUMENTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnrocuenta, "DEBE CARGAR UN NRO CUENTA")) {
            return false;
        }
        if(evecmb.getBoo_validar_select(cmbbanco, "DEBE SELECCIONAR UN BANCO")){
            return false;
        }
        return true;
    }

    private void boton_guardar_db() {
        if (validar_guardar_db()) {
            ENTdb.setC2titular(txttitular.getText());
            ENTdb.setC3documento(txtdocumento.getText());
            ENTdb.setC4nro_cuenta(txtnrocuenta.getText());
            ENTdb.setC5activo(jCactivo.isSelected());
            ENTdb.setC6fk_idbanco(fk_idbanco);
            BOdb.insertar_dato_banco(ENTdb, tbldato_banco);
            reestableser_db();
        }
    }

    private void boton_editar_db() {
        if (validar_guardar_db()) {
            ENTdb.setC1iddato_banco(Integer.parseInt(txtid.getText()));
            ENTdb.setC2titular(txttitular.getText());
            ENTdb.setC3documento(txtdocumento.getText());
            ENTdb.setC4nro_cuenta(txtnrocuenta.getText());
            ENTdb.setC5activo(jCactivo.isSelected());
            ENTdb.setC6fk_idbanco(fk_idbanco);
            BOdb.update_dato_banco(ENTdb, tbldato_banco);
        }
    }

    private void seleccionar_tabla_db() {
        int iddato_banco=evejt.getInt_select_id(tbldato_banco);
        DAOdb.cargar_dato_banco(conn, ENTdb, iddato_banco);
        txtid.setText(String.valueOf(ENTdb.getC1iddato_banco()));
        txttitular.setText(ENTdb.getC2titular());
        txtdocumento.setText(ENTdb.getC3documento());
        txtnrocuenta.setText(ENTdb.getC4nro_cuenta());
        jCactivo.setSelected(ENTdb.getC5activo());
        DAOb.cargar_banco(conn, ENTb,ENTdb.getC6fk_idbanco());
        cmbbanco.setSelectedItem("("+ENTb.getC1idbanco()+")-"+ENTb.getC2nombre());
        btnguardar_db.setEnabled(false);
        btneditar_db.setEnabled(true);
    }
    private void reestableser_db(){
        txtid.setText(null);
        txttitular.setText(null);
        txtdocumento.setText(null);
        txtnrocuenta.setText(null);
        cmbbanco.setSelectedIndex(0);
        jCactivo.setSelected(false);
        fk_idbanco=0;
        btnguardar_db.setEnabled(true);
        btneditar_db.setEnabled(false);
        btndeletar_db.setEnabled(false);
        txttitular.grabFocus();
    }
    private void boton_nuevo_db(){
        reestableser_db();
    }
    public FrmDato_Banco() {
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

        panel_insertar_unidad = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txttitular = new javax.swing.JTextField();
        btnnuevo_db = new javax.swing.JButton();
        btnguardar_db = new javax.swing.JButton();
        btneditar_db = new javax.swing.JButton();
        btndeletar_db = new javax.swing.JButton();
        txtdocumento = new javax.swing.JTextField();
        txtnrocuenta = new javax.swing.JTextField();
        jCactivo = new javax.swing.JCheckBox();
        cmbbanco = new javax.swing.JComboBox<>();
        panel_tabla_unidad = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldato_banco = new javax.swing.JTable();

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

        panel_insertar_unidad.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_unidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        txttitular.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txttitular.setBorder(javax.swing.BorderFactory.createTitledBorder("TITULAR"));
        txttitular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttitularKeyPressed(evt);
            }
        });

        btnnuevo_db.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo_db.setText("NUEVO");
        btnnuevo_db.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo_db.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo_db.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_dbActionPerformed(evt);
            }
        });

        btnguardar_db.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar_db.setText("GUARDAR");
        btnguardar_db.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar_db.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar_db.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_dbActionPerformed(evt);
            }
        });

        btneditar_db.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar_db.setText("EDITAR");
        btneditar_db.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar_db.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar_db.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditar_dbActionPerformed(evt);
            }
        });

        btndeletar_db.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar_db.setText("DELETAR");
        btndeletar_db.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar_db.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        txtdocumento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("DOCUMENTO"));
        txtdocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdocumentoKeyPressed(evt);
            }
        });

        txtnrocuenta.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnrocuenta.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO CUENTA"));
        txtnrocuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnrocuentaKeyPressed(evt);
            }
        });

        jCactivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCactivo.setText("ACTIVO");

        cmbbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbbanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbbanco.setBorder(javax.swing.BorderFactory.createTitledBorder("BANCO"));
        cmbbanco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbbancoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_unidadLayout = new javax.swing.GroupLayout(panel_insertar_unidad);
        panel_insertar_unidad.setLayout(panel_insertar_unidadLayout);
        panel_insertar_unidadLayout.setHorizontalGroup(
            panel_insertar_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_unidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertar_unidadLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCactivo))
                    .addComponent(txttitular, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnrocuenta, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbbanco, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_unidadLayout.createSequentialGroup()
                        .addGroup(panel_insertar_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_unidadLayout.createSequentialGroup()
                                .addComponent(btnnuevo_db)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnguardar_db)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneditar_db)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndeletar_db))
                            .addComponent(txtdocumento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_insertar_unidadLayout.setVerticalGroup(
            panel_insertar_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_unidadLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCactivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(panel_insertar_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_db)
                    .addComponent(btnguardar_db)
                    .addComponent(btneditar_db)
                    .addComponent(btndeletar_db))
                .addContainerGap())
        );

        panel_tabla_unidad.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_unidad.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbldato_banco.setModel(new javax.swing.table.DefaultTableModel(
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
        tbldato_banco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbldato_bancoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbldato_banco);

        javax.swing.GroupLayout panel_tabla_unidadLayout = new javax.swing.GroupLayout(panel_tabla_unidad);
        panel_tabla_unidad.setLayout(panel_tabla_unidadLayout);
        panel_tabla_unidadLayout.setHorizontalGroup(
            panel_tabla_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );
        panel_tabla_unidadLayout.setVerticalGroup(
            panel_tabla_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_unidadLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_insertar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_tabla_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_insertar_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardar_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_dbActionPerformed
        // TODO add your handling code here:
        boton_guardar_db();
    }//GEN-LAST:event_btnguardar_dbActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOdb.ancho_tabla_dato_banco(tbldato_banco);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbldato_bancoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldato_bancoMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_db();
    }//GEN-LAST:event_tbldato_bancoMouseReleased

    private void btneditar_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar_dbActionPerformed
        // TODO add your handling code here:
        boton_editar_db();
    }//GEN-LAST:event_btneditar_dbActionPerformed

    private void btnnuevo_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_dbActionPerformed
        // TODO add your handling code here:
        boton_nuevo_db();
    }//GEN-LAST:event_btnnuevo_dbActionPerformed

    private void txttitularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttitularKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txttitularKeyPressed

    private void txtdocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdocumentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdocumentoKeyPressed

    private void txtnrocuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnrocuentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnrocuentaKeyPressed

    private void cmbbancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbbancoItemStateChanged
        // TODO add your handling code here:
        if(hab_carga_banco){
        fk_idbanco=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbbanco, "idbanco", "nombre", "banco");
        }
    }//GEN-LAST:event_cmbbancoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndeletar_db;
    private javax.swing.JButton btneditar_db;
    private javax.swing.JButton btnguardar_db;
    private javax.swing.JButton btnnuevo_db;
    private javax.swing.JComboBox<String> cmbbanco;
    private javax.swing.JCheckBox jCactivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_insertar_unidad;
    private javax.swing.JPanel panel_tabla_unidad;
    private javax.swing.JTable tbldato_banco;
    private javax.swing.JTextField txtdocumento;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnrocuenta;
    private javax.swing.JTextField txttitular;
    // End of variables declaration//GEN-END:variables
}
