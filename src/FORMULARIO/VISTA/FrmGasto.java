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
public class FrmGasto extends javax.swing.JInternalFrame {

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
        panel_insertar_gasto.setBackground(clacolor.getColor_insertar_primario());
    }
    private String filtro_gasto(int tipo){
        String ocultarAnulado = "";
        String filtro = "";
        String fecha = "";
        
        if (jCocultar_anulado.isSelected()) {
            ocultarAnulado = " and g.estado='"+eveest.getEst_EMITIDO()+"' ";
        }
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
//        String ocultarAnulado = "";
//        String filtro = "";
//        String fecha = "";
//        String orden = " order by g.fecha_emision desc";
//        if (jCocultar_anulado.isSelected()) {
//            ocultarAnulado = " and g.estado='"+eveest.getEst_EMITIDO()+"' ";
//        }
//        if ((txtfecha_desde.getText().trim().length() > 0) && (txtfecha_hasta.getText().trim().length() > 0)) {
//            String fecdesde = evefec.getString_cambiar_formato(txtfecha_desde.getText());
//            String fechasta = evefec.getString_cambiar_formato(txtfecha_hasta.getText());
//            fecha = " and date(g.fecha_emision)>=date('" + fecdesde + "') and date(g.fecha_emision)<=date('" + fechasta + "')";
//        }
//        if (tipo == 2) {
//            filtro = fecha + ocultarAnulado;
//        }
//        if (tipo == 3) {
//            if ((txtbuscar_descripcion.getText().trim().length() > 0)) {
//                String descrip = txtbuscar_descripcion.getText();
//                filtro = " and g.descripcion ilike '%" + descrip + "%' " + fecha + ocultarAnulado;
//            }
//        }
//        if (tipo == 4) {
//            if ((txtbuscar_tipo.getText().trim().length() > 0)) {
//                String gtipo = txtbuscar_tipo.getText();
//                filtro = " and gt.nombre ilike '%" + gtipo + "%' " + fecha + ocultarAnulado;
//            }
//        }
        String orden = " order by g.fecha_emision desc";
        DAOgas.actualizar_tabla_gasto(conn, tblgasto, filtro_gasto(tipo_filtro), orden);
        double suma_monto = DAOgas.sumar_monto_gasto(conn, filtro_gasto(tipo_filtro));
        jFsuma_monto.setValue(suma_monto);
        double suma_cantidad = DAOgas.sumar_cantidad_gasto(conn, filtro_gasto(tipo_filtro));
        jFsuma_cantidad.setValue(suma_cantidad);
    }

    boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_gasto_tipo, "DEBE CARGAR UN GASTO TIPO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdescripcion, "SE DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_gasto, "SE DEBE CARGAR UN MONTO")) {
            return false;
        }
//        } else {
//            monto_gasto = Double.parseDouble(txtmonto_gasto.getText());
//        }
        if (fk_idgasto_tipo == 0) {
            JOptionPane.showMessageDialog(this, "NO SE CARGO CORRECTAMENTE", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtbuscar_gasto_tipo.setText(null);
            txtbuscar_gasto_tipo.grabFocus();
            return false;
        }
        return true;
    }

    private void cargar_datos_caja() {
        caja.setC2fecha_emision(txtfecha_emision.getText());
        caja.setC3descripcion1("(GASTO) id:" + idgasto + " Tipo:" + txtbuscar_gasto_tipo.getText());
        caja.setC4monto_venta_efectivo(0);
        caja.setC5monto_venta_tarjeta(0);
        caja.setC6monto_delivery(0);
        caja.setC7monto_gasto(evejtf.getDouble_format_nro_entero(txtmonto_gasto));
        caja.setC8monto_compra(0);
        caja.setC9monto_vale(0);
        caja.setC10monto_caja(0);
        caja.setC11monto_cierre(0);
        caja.setC12id_origen(idgasto);
        caja.setC13tabla_origen(tabla_gasto);
        caja.setC15estado(eveest.getEst_EMITIDO());
        caja.setC16fk_idusuario(usu.getGlobal_idusuario());
        caja.setC17monto_recibo_pago(0);
        caja.setC18monto_compra_credito(0);
    }

    private void cargar_datos_gasto() {
        String fecha = evefec.getString_cambiar_formato_add_hora(txtfecha_emision.getText());
        ENTgas.setFecha_emision(fecha);
        ENTgas.setDescripcion(txtdescripcion.getText());
        ENTgas.setEstado(eveest.getEst_EMITIDO());
        ENTgas.setMonto_gasto(evejtf.getDouble_format_nro_entero(txtmonto_gasto));
        ENTgas.setFk_idgasto_tipo(fk_idgasto_tipo);
        ENTgas.setFk_idusuario(usu.getGlobal_idusuario());
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_datos_gasto();
            cargar_datos_caja();
            if (BOgas.getboolean_insertar_gasto(ENTgas, caja)) {
                posgas.boton_imprimir_pos_GASTO(conn, idgasto);
                reestableser();

            }
        }
    }

    private void cargar_datos_editar_caja() {
        caja.setC7monto_gasto(evejtf.getDouble_format_nro_entero(txtmonto_gasto));
        caja.setC12id_origen(idgasto);
        caja.setC13tabla_origen(tabla_gasto);
    }

    private void cargar_datos_editar_gasto() {
        ENTgas.setIdgasto(idgasto);
        ENTgas.setFecha_emision(txtfecha_emision.getText()+" 12:00:00.00");
        ENTgas.setDescripcion(txtdescripcion.getText());
        ENTgas.setMonto_gasto(evejtf.getDouble_format_nro_entero(txtmonto_gasto));
        ENTgas.setFk_idgasto_tipo(fk_idgasto_tipo);
        ENTgas.setFk_idusuario(usu.getGlobal_idusuario());
    }

    private void boton_editar() {
        if (!evejta.getBoolean_validar_select(tblgasto)) {
            if (validar_guardar()) {
                cargar_datos_editar_gasto();
                cargar_datos_editar_caja();
                BOgas.update_gasto(ENTgas, caja);
                actualizar_gasto(2);
            }
        }
    }

    private void boton_anular() {
        if (!evejta.getBoolean_validar_select(tblgasto)) {
            ENTgas.setEstado(eveest.getEst_ANULADO());
            ENTgas.setIdgasto(idgasto);
            caja.setC12id_origen(idgasto);
            caja.setC13tabla_origen(tabla_gasto);
            caja.setC15estado(eveest.getEst_ANULADO());
            BOgas.update_gasto_anular(ENTgas, caja);
            reestableser();
        }
    }

    private void boton_imprimir() {
        if (!evejta.getBoolean_validar_select(tblgasto)) {
            posgas.boton_imprimir_pos_GASTO(conn, idgasto);
        }
    }

    private void seleccionar_tabla() {
        DAOgas.cargar_gasto(ENTgas, tblgasto);
        txtid.setText(String.valueOf(ENTgas.getIdgasto()));
        idgasto = ENTgas.getIdgasto();
        txtbuscar_gasto_tipo.setText(ENTgas.getGasto_tipo());
        fk_idgasto_tipo = ENTgas.getFk_idgasto_tipo();
        txtfecha_emision.setText(ENTgas.getFecha_emision());
        txtdescripcion.setText(ENTgas.getDescripcion());
        txtmonto_gasto.setText(evejtf.getString_format_nro_decimal(ENTgas.getMonto_gasto()));
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
        btnanular.setEnabled(true);
        btnimprimir.setEnabled(true);
    }

    private void reestableser() {
        idgasto = eveconn.getInt_ultimoID_mas_uno(conn, ENTgas.getTabla(), ENTgas.getIdtabla());
        indice_gasto = eveut.getString_crear_indice();
        fk_idgasto_tipo = 0;
        jList_gasto_tipo.setVisible(false);
        txtfecha_desde.setText(evefec.getString_fecha_dia1());
        txtfecha_hasta.setText(evefec.getString_formato_fecha_barra());
        txtfecha_emision.setText(evefec.getString_formato_fecha_barra());
        actualizar_gasto(2);
        txtid.setText(null);
        txtbuscar_gasto_tipo.setText(null);
        txtdescripcion.setText("NINGUNA");
        txtmonto_gasto.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btnanular.setEnabled(false);
        btnimprimir.setEnabled(false);
        txtbuscar_gasto_tipo.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    private void cargar_idgasto_tipo() {
        fk_idgasto_tipo = eveconn.getInt_seleccionar_JLista(conn, txtbuscar_gasto_tipo, jList_gasto_tipo, "gasto_tipo", "nombre", "idgasto_tipo");
        jList_gasto_tipo.setVisible(false);
        txtdescripcion.grabFocus();
    }
    private void boton_imprimir_filtro_gasto(){
        DAOgas.imprimir_filtro_gasto(conn, filtro_gasto(tipo_filtro));
    }
    public FrmGasto() {
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

        panel_insertar_gasto = new javax.swing.JPanel();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_gasto_tipo = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdescripcion = new javax.swing.JTextArea();
        txtbuscar_gasto_tipo = new javax.swing.JTextField();
        btngasto_tipo = new javax.swing.JButton();
        txtmonto_gasto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtfecha_emision = new javax.swing.JTextField();
        btnimprimir = new javax.swing.JButton();
        panel_tabla_gasto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblgasto = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtfecha_desde = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfecha_hasta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnbuscar_fecha = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtbuscar_descripcion = new javax.swing.JTextField();
        txtbuscar_tipo = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jFsuma_monto = new javax.swing.JFormattedTextField();
        jFsuma_cantidad = new javax.swing.JFormattedTextField();
        jCocultar_anulado = new javax.swing.JCheckBox();
        btnimprimir_filtro_gasto = new javax.swing.JButton();

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

        panel_insertar_gasto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
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

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_gasto_tipo.setBackground(new java.awt.Color(204, 204, 255));
        jList_gasto_tipo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_gasto_tipo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList_gasto_tipo.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_gasto_tipo.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_gasto_tipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_gasto_tipoMouseReleased(evt);
            }
        });
        jList_gasto_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_gasto_tipoKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jList_gasto_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 290, 100));

        txtdescripcion.setColumns(20);
        txtdescripcion.setRows(5);
        txtdescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        txtdescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdescripcionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtdescripcion);

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 320, -1));

        txtbuscar_gasto_tipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_gasto_tipo.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO GASTO"));
        txtbuscar_gasto_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_gasto_tipoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_gasto_tipoKeyReleased(evt);
            }
        });
        jLayeredPane1.add(txtbuscar_gasto_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 40));

        btngasto_tipo.setText("+");
        btngasto_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngasto_tipoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btngasto_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 30));

        txtmonto_gasto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtmonto_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO GASTO"));
        txtmonto_gasto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("FECHA:");

        txtfecha_emision.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        btnimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/imprimir.png"))); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnimprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_gastoLayout = new javax.swing.GroupLayout(panel_insertar_gasto);
        panel_insertar_gasto.setLayout(panel_insertar_gastoLayout);
        panel_insertar_gastoLayout.setHorizontalGroup(
            panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_gastoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_gastoLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_gastoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertar_gastoLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(12, 12, 12)
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimir))
                    .addGroup(panel_insertar_gastoLayout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertar_gastoLayout.createSequentialGroup()
                                .addComponent(btnnuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnguardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneditar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnanular)))))
                .addContainerGap())
        );
        panel_insertar_gastoLayout.setVerticalGroup(
            panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_gastoLayout.createSequentialGroup()
                .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(txtfecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_gastoLayout.createSequentialGroup()
                        .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_insertar_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnnuevo)
                            .addComponent(btnguardar)
                            .addComponent(btneditar)
                            .addComponent(btnanular)))
                    .addComponent(btnimprimir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("fec. Desde:");

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Fec. Hasta:");

        txtfecha_hasta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_hastaKeyPressed(evt);
            }
        });

        jLabel7.setText("año-mes-dia");

        btnbuscar_fecha.setText("BUSCAR FECHA ");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

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

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jFsuma_monto.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA MONTO:"));
        jFsuma_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsuma_monto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFsuma_cantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        jFsuma_cantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFsuma_cantidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jCocultar_anulado.setText("ANULADOS");
        jCocultar_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCocultar_anuladoActionPerformed(evt);
            }
        });

        btnimprimir_filtro_gasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_filtro_gasto.setText("FILTRO GASTO");
        btnimprimir_filtro_gasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_filtro_gastoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_gastoLayout = new javax.swing.GroupLayout(panel_tabla_gasto);
        panel_tabla_gasto.setLayout(panel_tabla_gastoLayout);
        panel_tabla_gastoLayout.setHorizontalGroup(
            panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_gastoLayout.createSequentialGroup()
                .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtfecha_hasta))
                            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtbuscar_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(txtbuscar_tipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCocultar_anulado)
                            .addComponent(btnimprimir_filtro_gasto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFsuma_monto)
                            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                                .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 87, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panel_tabla_gastoLayout.setVerticalGroup(
            panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                        .addComponent(jCocultar_anulado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnimprimir_filtro_gasto)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                                .addComponent(jFsuma_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jFsuma_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_tabla_gastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(txtfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnbuscar_fecha))
                                    .addComponent(jSeparator1)
                                    .addGroup(panel_tabla_gastoLayout.createSequentialGroup()
                                        .addComponent(txtbuscar_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbuscar_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_insertar_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_gasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_tabla_gasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_insertar_gasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOgas.ancho_tabla_gasto(tblgasto);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblgastoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgastoMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblgastoMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtbuscar_gasto_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_gasto_tipoKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        evejtf.seleccionar_lista(evt, jList_gasto_tipo);
    }//GEN-LAST:event_txtbuscar_gasto_tipoKeyPressed

    private void txtbuscar_gasto_tipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_gasto_tipoKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_condicion_Jlista(conn, txtbuscar_gasto_tipo, jList_gasto_tipo, "gasto_tipo", "nombre", "nombre", "and activar=true");
    }//GEN-LAST:event_txtbuscar_gasto_tipoKeyReleased

    private void jList_gasto_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_gasto_tipoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_idgasto_tipo();
        }
    }//GEN-LAST:event_jList_gasto_tipoKeyPressed

    private void jList_gasto_tipoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_gasto_tipoMouseReleased
        // TODO add your handling code here:
        cargar_idgasto_tipo();
    }//GEN-LAST:event_jList_gasto_tipoMouseReleased

    private void txtdescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescripcionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtmonto_gasto.grabFocus();
        }
    }//GEN-LAST:event_txtdescripcionKeyPressed

    private void txtmonto_gastoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_guardar();
        }
    }//GEN-LAST:event_txtmonto_gastoKeyPressed

    private void txtmonto_gastoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_gastoKeyTyped

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        actualizar_gasto(2);
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void txtbuscar_descripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_descripcionKeyReleased
        // TODO add your handling code here:
        actualizar_gasto(3);
    }//GEN-LAST:event_txtbuscar_descripcionKeyReleased

    private void txtbuscar_tipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_tipoKeyReleased
        // TODO add your handling code here:
        actualizar_gasto(4);
    }//GEN-LAST:event_txtbuscar_tipoKeyReleased

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_hasta.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void txtfecha_hastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_hastaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_gasto(2);
            txtfecha_desde.grabFocus();
        }
    }//GEN-LAST:event_txtfecha_hastaKeyPressed

    private void jCocultar_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCocultar_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_gasto(2);
    }//GEN-LAST:event_jCocultar_anuladoActionPerformed

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular();
    }//GEN-LAST:event_btnanularActionPerformed

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void btngasto_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngasto_tipoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_tipo());
    }//GEN-LAST:event_btngasto_tipoActionPerformed

    private void txtmonto_gastoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtmonto_gasto);
    }//GEN-LAST:event_txtmonto_gastoKeyReleased

    private void btnimprimir_filtro_gastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_filtro_gastoActionPerformed
        // TODO add your handling code here:
        boton_imprimir_filtro_gasto();
    }//GEN-LAST:event_btnimprimir_filtro_gastoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btngasto_tipo;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnimprimir_filtro_gasto;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JCheckBox jCocultar_anulado;
    private javax.swing.JFormattedTextField jFsuma_cantidad;
    private javax.swing.JFormattedTextField jFsuma_monto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_gasto_tipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel panel_insertar_gasto;
    private javax.swing.JPanel panel_tabla_gasto;
    private javax.swing.JTable tblgasto;
    private javax.swing.JTextField txtbuscar_descripcion;
    private javax.swing.JTextField txtbuscar_gasto_tipo;
    private javax.swing.JTextField txtbuscar_tipo;
    private javax.swing.JTextArea txtdescripcion;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtfecha_emision;
    private javax.swing.JTextField txtfecha_hasta;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_gasto;
    // End of variables declaration//GEN-END:variables
}
