/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_producto_marca;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_marca;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRepInventarioValorizadoVenta extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRepVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    Connection conn = ConnPostgres.getConnPosgres();
//    DAO_venta vdao = new DAO_venta();
    DAO_producto_categoria pcdao = new DAO_producto_categoria();
    DAO_producto_marca pmdao = new DAO_producto_marca();
    EvenFecha evefec = new EvenFecha();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    EvenJTextField evejtf = new EvenJTextField();
    EvenConexion eveconn = new EvenConexion();
//    DAO_cliente cdao = new DAO_cliente();
    producto_categoria cate = new producto_categoria();
    producto_marca marca = new producto_marca();
    DAO_producto pdao = new DAO_producto();
    cla_color_pelete clacolor = new cla_color_pelete();
    private int fk_idproducto_categoria_local;
    private int fk_idproducto_marca_local;

    void abrir_formulario() {
        this.setTitle("REPORTE INVENTARIO VALORIZADO POR VENTA");
        evetbl.centrar_formulario_internalframa(this);
        color_formulario();
        reestableser();
    }
    void color_formulario() {
        panel_cate_mar.setBackground(clacolor.getColor_insertar_primario());
//        panel_tabla_producto.setBackground(clacolor.getColor_tabla());
//        panel_base_1.setBackground(clacolor.getColor_base());
    }
    String suma_filtro(){
        String suma_filtro = "";
        String filtro_categoria = "";
        String filtro_stock="";
        if (fk_idproducto_categoria_local >= 0) {
            filtro_categoria = " and pc.idproducto_categoria=" + fk_idproducto_categoria_local + "\n";
        }
        String filtro_marca = "";
        if (fk_idproducto_marca_local >= 0) {
            filtro_marca = " and pm.idproducto_marca=" + fk_idproducto_marca_local + "\n";
        }
        if(jCstock_positivo.isSelected() && !jCstock_cero_nega.isSelected()){
            filtro_stock=" and p.stock>0 ";
        }
        if(!jCstock_positivo.isSelected() && jCstock_cero_nega.isSelected()){
            filtro_stock=" and p.stock<=0 ";
        }
        suma_filtro = filtro_categoria + filtro_marca +filtro_stock;
        return suma_filtro;
    }
    void boton_imprimir() {
        boton_calcular();
        pdao.imprimir_rep_inventario_venta(conn, suma_filtro());
    }
    void boton_calcular(){
        double total=pdao.getDouble_suma_inventario_valor_precio(conn, "p.precio_venta_mayorista", suma_filtro());
        jFtotal_venta_mayo.setValue(total);
    }
    void reestableser() {
        jCstock_positivo.setSelected(true);
        jCstock_cero_nega.setSelected(false);
        txtbucar_categoria.setText(null);
        txtbucar_marca.setText(null);
        fk_idproducto_categoria_local = -1;
        fk_idproducto_marca_local = -1;
    }

    void seleccionar_cargar_categoria() {
        fk_idproducto_categoria_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_categoria, "producto_categoria", "nombre", "idproducto_categoria");
        pcdao.cargar_producto_categoria_reporte(cate, fk_idproducto_categoria_local);
        txtbucar_categoria.setText(cate.getNombre());
        txtbucar_marca.grabFocus();
    }

    void seleccionar_cargar_marca() {
        fk_idproducto_marca_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_marca, "producto_marca", "nombre", "idproducto_marca");
        pmdao.cargar_producto_marca_por_id(conn, marca, fk_idproducto_marca_local);
        txtbucar_marca.setText(marca.getC2nombre());
        btnimprimir.grabFocus();
    }

    public FrmRepInventarioValorizadoVenta() {
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

        panel_cate_mar = new javax.swing.JPanel();
        jList_categoria = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        txtbucar_categoria = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucar_marca = new javax.swing.JTextField();
        jList_marca = new javax.swing.JList<>();
        btnimprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jFtotal_venta_mayo = new javax.swing.JFormattedTextField();
        btnreset = new javax.swing.JButton();
        btncalcular = new javax.swing.JButton();
        jCstock_positivo = new javax.swing.JCheckBox();
        jCstock_cero_nega = new javax.swing.JCheckBox();

        setClosable(true);

        panel_cate_mar.setBorder(javax.swing.BorderFactory.createTitledBorder("INVENTARIO VALORIZADO"));

        jList_categoria.setBackground(new java.awt.Color(204, 204, 255));
        jList_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_categoria.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_categoriaMouseReleased(evt);
            }
        });
        jList_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CATEGORIA:");

        txtbucar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("MARCA:");

        txtbucar_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyReleased(evt);
            }
        });

        jList_marca.setBackground(new java.awt.Color(204, 204, 255));
        jList_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_marca.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_marca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_marcaMouseReleased(evt);
            }
        });
        jList_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_marcaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_cate_marLayout = new javax.swing.GroupLayout(panel_cate_mar);
        panel_cate_mar.setLayout(panel_cate_marLayout);
        panel_cate_marLayout.setHorizontalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cate_marLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                    .addComponent(jList_marca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_cate_marLayout.setVerticalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cate_marLayout.createSequentialGroup()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtbucar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnimprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("TOTAL VENTA MAYORISTA:");

        jFtotal_venta_mayo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_venta_mayo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_venta_mayo.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btncalcular.setText("CALCULAR");
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        jCstock_positivo.setText("STOCK POSITIVO");

        jCstock_cero_nega.setText("STOCK CERO Y NEGATIVO");
        jCstock_cero_nega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCstock_cero_negaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_cate_mar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFtotal_venta_mayo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCstock_cero_nega)
                            .addComponent(jCstock_positivo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnreset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_venta_mayo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCstock_cero_nega)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCstock_positivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnreset)
                    .addComponent(btncalcular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_cate_mar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void jList_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_categoria();
    }//GEN-LAST:event_jList_categoriaMouseReleased

    private void jList_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_categoria();
        }
    }//GEN-LAST:event_jList_categoriaKeyPressed

    private void txtbucar_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_categoria);
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtbucarCliente_telefono.grabFocus();
//        }
    }//GEN-LAST:event_txtbucar_categoriaKeyPressed

    private void txtbucar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_categoria, jList_categoria, "producto_categoria", "nombre", "nombre", 4);
    }//GEN-LAST:event_txtbucar_categoriaKeyReleased

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        reestableser();
    }//GEN-LAST:event_btnresetActionPerformed

    private void jList_marcaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_marcaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_marca();
    }//GEN-LAST:event_jList_marcaMouseReleased

    private void jList_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_marcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_marca();
        }
    }//GEN-LAST:event_jList_marcaKeyPressed

    private void txtbucar_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_marca);
    }//GEN-LAST:event_txtbucar_marcaKeyPressed

    private void txtbucar_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_marca, jList_marca, "producto_marca", "nombre", "nombre", 4);
    }//GEN-LAST:event_txtbucar_marcaKeyReleased

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        // TODO add your handling code here:
        boton_calcular();
    }//GEN-LAST:event_btncalcularActionPerformed

    private void jCstock_cero_negaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCstock_cero_negaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCstock_cero_negaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnreset;
    private javax.swing.JCheckBox jCstock_cero_nega;
    private javax.swing.JCheckBox jCstock_positivo;
    private javax.swing.JFormattedTextField jFtotal_venta_mayo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList_categoria;
    private javax.swing.JList<String> jList_marca;
    private javax.swing.JPanel panel_cate_mar;
    private javax.swing.JTextField txtbucar_categoria;
    private javax.swing.JTextField txtbucar_marca;
    // End of variables declaration//GEN-END:variables
}
