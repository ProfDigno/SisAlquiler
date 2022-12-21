/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import FORMULARIO.VISTA.ALQUILER.FrmCliente;
import FORMULARIO.VISTA.ALQUILER.FrmCaja_Abrir_alquiler;
import FORMULARIO.VISTA.ALQUILER.FrmCaja_abrir_cerrar_alquiler;
import FORMULARIO.VISTA.ALQUILER.FrmCaja_Cierre_alquiler;
import FORMULARIO.VISTA.ALQUILER.FrmRepVenta_alquiler;
import FORMULARIO.VISTA.ALQUILER.FrmCajaDetalle_alquiler;
import FORMULARIO.VISTA.ALQUILER.FrmVenta_alquiler;
import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import CONFIGURACION.ClaCorteAdmin;
import CONFIGURACION.ComputerInfo;
import Config_JSON.json_array_conexion;
import Config_JSON.json_config;
import Config_JSON.json_imprimir_pos;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.Jframe.EvenJFRAME;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_cotizacion;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_venta;
import FORMULARIO.ENTIDAD.caja_cierre;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import FORMULARIO.ENTIDAD.cotizacion;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.financista;
import FORMULARIO.VISTA.ALQUILER.FrmProducto_combo_alquiler;
import FORMULARIO.VISTA.ALQUILER.FrmRepReciboCliente;
import FORMULARIO.VISTA.ALQUILER.FrmRepTransaccionBanco;
import FORMULARIO.VISTA.ALQUILER.FrmRepVentaAlquiler;
import INSERT_AUTO.Cla_insert_automatico;
import java.awt.Color;
import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Digno
 */
public class FrmMenuAlquiler extends javax.swing.JFrame {

    /**
     * Creates new form FrmMenuQchurron
     */
    Connection conn = null;
    Connection connSER = null;
    ConnPostgres conPs = new ConnPostgres();
    VariablesBD var = new VariablesBD();
    control_vista covi = new control_vista();
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    factura fac = new factura();
    DAO_factura fdao = new DAO_factura();
    DAO_cliente cdao = new DAO_cliente();
    DAO_venta vdao = new DAO_venta();
    DAO_producto pdao = new DAO_producto();
    DAO_producto_categoria pcDAO = new DAO_producto_categoria();
    Cla_insert_automatico ins_auto = new Cla_insert_automatico();
    private financista fina = new financista();
    EvenConexion eveconn = new EvenConexion();
    caja_cierre cjcie = new caja_cierre();
    DAO_caja_cierre cjcie_dao = new DAO_caja_cierre();
    caja_cierre_alquilado cjciea = new caja_cierre_alquilado();
    DAO_caja_cierre_alquilado cjciea_dao = new DAO_caja_cierre_alquilado();
    cla_color_pelete clacolor = new cla_color_pelete();
    json_config jsconfig = new json_config();
    json_imprimir_pos jsprint = new json_imprimir_pos();
    EvenSQLDataSet evedata = new EvenSQLDataSet();
    FunFreeChard chard = new FunFreeChard();
    ClaCorteAdmin corte = new ClaCorteAdmin();
    json_array_conexion jscon=new json_array_conexion();
    private ComputerInfo pcinfo=new ComputerInfo();
    private Timer tiempo;
    boolean vergrafico = true;
    int seg_ver_grafico = 0;
    String estado_CERRADO="CERRADO";
    String version="1.6";
    void abrir_formulario() {
//        conPs.ConnectDBpostgres(conn, false, false);
        conPs.ConnectDBpostgres(conn,false);
        conn = conPs.getConnPosgres();
        covi.setComanda_abierto(true);
        jsconfig.cargar_jsom_configuracion();
        jsprint.cargar_jsom_imprimir_pos();
        this.setExtendedState(MAXIMIZED_BOTH);
        iniciarTiempo();
        habilitar_menu(false);
        codao.cargar_cotizacion(coti, 1);
        txtvercion.setText("V:" + version);
        corte.setFecha_corte("2023-04-24");
        jFdolar.setValue(coti.getDolar_guarani());
        jFreal.setValue(coti.getReal_guarani());
//        grafico_mas_vendidos();
//        grafico_venta_semanal();
//        pdao.actualizar_tabla_producto_stock_minimo(conn, tbl_producto_stock_minimo);
        pcDAO.update_orden_categoria_mas_vendido_mes();
        iniciar_color();
        actualizacion_version_v1();
        
//        primer_finanza();
    }

    void actualizacion_version_v1() {
        /**
         * ALTER TABLE cliente ADD COLUMN delivery boolean; update cliente set
         * delivery=true; update gastos set fk_idusuario=7 where fk_idusuario=1;
         * alter table itemventacomanda alter column preciocompra type
         * numeric(14,0) using preciocompra::numeric;
         */
        String sql = "DO $$ \n"
                + "    BEGIN\n"
                + "        BEGIN\n"
//                + " ALTER TABLE venta_alquiler ADD COLUMN monto_descuento NUMERIC(15,0) DEFAULT 0;\n "
                + " ALTER TABLE venta_alquiler ADD COLUMN monto_pagado NUMERIC(15,0) DEFAULT 0;\n "
                + " ALTER TABLE recibo_pago_cliente ADD COLUMN fk_idventa_alquiler INTEGER DEFAULT 0;\n "
//                + " ALTER TABLE item_venta_alquiler ADD COLUMN orden INTEGER DEFAULT 0;\n "
//                + " ALTER TABLE producto ADD COLUMN stock_fijo NUMERIC(15,0) DEFAULT 0; \n"
//                + " ALTER TABLE credito_cliente ADD COLUMN fecha_vence TIMESTAMP DEFAULT current_date; \n"
//                + " update producto set alquilado=false;\n"
//                + " ALTER TABLE compra ADD COLUMN alquilado boolean; \n"
//                + " update compra set alquilado=false;\n"
                + ""        
                + "        EXCEPTION\n"
                + "            WHEN duplicate_column THEN RAISE NOTICE 'duplicate_column.';\n"
                + "        END;\n"
                + "    END;\n"
                + "$$ ";
        eveconn.SQL_execute_libre(conn, sql);
    }
//    void primer_finanza(){
//        int idfinancista = (eveconn.getInt_ultimoID_mas_uno(conn, fina.getTb_financista(), fina.getId_idfinancista()));
//        if(idfinancista==0){
//            evetbl.abrir_TablaJinternal(new FrmFinancista());
//        }
//    }

//    void ocultar_grafico() {
//        panel_mas_vendidos.setVisible(!jCocultar_grafico.isSelected());
////        panel_stock_minimo.setVisible(!jCocultar_grafico.isSelected());
//        panel_venta_semanal.setVisible(!jCocultar_grafico.isSelected());
//    }

    void iniciar_color() {
        clacolor.setColor_insertar_primario(new Color(137, 201, 184));
        clacolor.setColor_insertar_secundario(new Color(132, 169, 172));
        clacolor.setColor_tabla(new Color(217, 173, 173));
        clacolor.setColor_referencia(new Color(239, 187, 207));
        clacolor.setColor_base(new Color(134, 117, 169));
        clacolor.setColor_shopp(new Color(255, 244, 125));
    }

    void titulo_sistema(String servidor) {
//        String titulo = jsconfig.getNombre_sistema() + " V." + jsconfig.getVersion()
//                + " BD: " + var.getPsLocalhost() + "/" + var.getPsPort() + "/" + var.getPsNomBD()
//                + " Fecha: " + jsconfig.getFecha_sis() + servidor;
String titulo = jscon.getNombre() 
                + " BD: " + jscon.getLocalhost() + " /" + jscon.getPort() + " /" + jscon.getBasedato()+" IP:"+pcinfo.getStringMiIP();
        this.setTitle(titulo);
    }

    void conectar_servidor(boolean mensaje) {
        String servidor = "";
        titulo_sistema(servidor);
    }

    private void habilitar_menu(boolean blo) {
//        FrmMenuAlquiler.btncaja_detalle.setEnabled(blo);
        FrmMenuAlquiler.btncliente.setEnabled(blo);
        FrmMenuAlquiler.btngasto.setEnabled(blo);
        FrmMenuAlquiler.btnproducto.setEnabled(blo);
//        FrmMenuAlquiler.btnvale.setEnabled(blo);
//        FrmMenuAlquiler.btnventa.setEnabled(blo);
        FrmMenuAlquiler.btncombo.setEnabled(blo);
        FrmMenuAlquiler.btnshopp.setEnabled(blo);
        FrmMenuAlquiler.btncambiar_usuario.setEnabled(blo);
//        FrmMenuAlquiler.btncaja_cierre.setEnabled(blo);
//        FrmMenuAlquiler.btndelivery_venta.setEnabled(blo);
//        FrmMenuAlquiler.btncajacerrar.setEnabled(blo);
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
//        FrmMenuAlquiler.btncomprainsumo.setEnabled(blo);
        FrmMenuAlquiler.btncotizacion.setEnabled(blo);
//        FrmMenuAlquiler.btninventario.setEnabled(blo);
    }

    void iniciarTiempo() {
        tiempo = new Timer();
        //le asignamos una tarea al timer
        tiempo.schedule(new FrmMenuAlquiler.clasetimer(), 0, 1000 * 1);
        System.out.println("Timer Iniciado en COMANDA");
    }

    void pararTiempo() {
        tiempo.cancel();
        System.out.println("Timer Parado en COMANDA");
    }

    class clasetimer extends TimerTask {

        private int contador_segundo;

        public void run() {
            contador_segundo++;
            txtfechahora.setText(evefec.getString_formato_hora());
//            if (contador_segundo >= 60) {
//                contador_segundo = 0;
//            }
//            if (contador_segundo >= 10) {
//                if (vergrafico) {
//                    jCocultar_grafico.setSelected(true);
//                    ocultar_grafico();
//                    vergrafico = false;
//                }
//            }
        }
    }

    private void abrir_caja_cierre() {
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
        cjcie.setC1idcaja_cierre(idcaja_cierre);
        cjcie_dao.cargar_caja_cierre(cjcie);
        if (cjcie.getC4estado().equals(estado_CERRADO)) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
        } else {
            evetbl.abrir_TablaJinternal(new FrmCaja_Cierre());
        }
    }
    private void abrir_caja_cierre_alquiler() {
        int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjciea.getTb_caja_cierre_alquilado(), cjciea.getId_idcaja_cierre_alquilado()));
        cjciea.setC1idcaja_cierre_alquilado(idcaja_cierre);
        cjciea_dao.cargar_caja_cierre_alquilado(conn, cjciea, idcaja_cierre);
        if (cjciea.getC4estado().equals(estado_CERRADO)) {
            JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
            evetbl.abrir_TablaJinternal(new FrmCaja_Abrir_alquiler());
        } else {
            evetbl.abrir_TablaJinternal(new FrmCaja_Cierre_alquiler());
        }
    }
//    void grafico_mas_vendidos() {
//        int cant_dias = 7;
//        int top = 20;
//        DefaultCategoryDataset dataset = evedata.getDataset_producto_mas_vendido(conn, cant_dias, top);
//        String titulo = "TOP " + top + " MAS VENDIDOS ultimos " + cant_dias + " dias";
//        String plano_horizontal = "PRODUCTOS";
//        String plano_vertical = "CANTIDAD";
//        chard.crear_graficoBar3D(panel_mas_vendidos, dataset, titulo, plano_horizontal, plano_vertical);
//    }
//
//    void grafico_venta_semanal() {
//        DefaultCategoryDataset dataset = evedata.getDataset_venta_semanal(conn);
//        String titulo = "VENTA SEMANAL 4 SEMANA";
//        String plano_horizontal = "SEMANA";
//        String plano_vertical = "MONTO";
//        chard.crear_graficoBar3D(panel_venta_semanal, dataset, titulo, plano_horizontal, plano_vertical);
//    }

    public FrmMenuAlquiler() {
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

        escritorio = new javax.swing.JDesktopPane();
        btncliente = new javax.swing.JButton();
        btnproducto = new javax.swing.JButton();
        btngasto = new javax.swing.JButton();
        lblusuario = new javax.swing.JLabel();
        btnshopp = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jFdolar = new javax.swing.JFormattedTextField();
        jFreal = new javax.swing.JFormattedTextField();
        btncambiar_usuario = new javax.swing.JButton();
        txtfechahora = new javax.swing.JLabel();
        txtvercion = new javax.swing.JLabel();
        btncotizacion = new javax.swing.JButton();
        btncombo = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_cliente = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu_producto = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenu_delivery = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenu_config = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu_gasto = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btncliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/cliente.png"))); // NOI18N
        btncliente.setText("CLIENTE/FUNCIO");
        btncliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclienteActionPerformed(evt);
            }
        });

        btnproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/producto_beer.png"))); // NOI18N
        btnproducto.setText("PRODUCTO");
        btnproducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnproducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnproductoActionPerformed(evt);
            }
        });

        btngasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/gasto.png"))); // NOI18N
        btngasto.setText("GASTO");
        btngasto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btngasto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btngasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngastoActionPerformed(evt);
            }
        });

        lblusuario.setBackground(new java.awt.Color(51, 51, 51));
        lblusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblusuario.setForeground(new java.awt.Color(51, 51, 51));
        lblusuario.setText("usuario");

        btnshopp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/alquiler.png"))); // NOI18N
        btnshopp.setText("ALQUILER");
        btnshopp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnshopp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnshopp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnshoppActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("COTIZACION"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("DOLAR:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("REAL:");

        jFdolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFdolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdolar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFreal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFreal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFreal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jFreal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFdolar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFdolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jFreal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btncambiar_usuario.setText("CAMBIAR USUARIO");
        btncambiar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncambiar_usuarioActionPerformed(evt);
            }
        });

        txtfechahora.setBackground(new java.awt.Color(102, 102, 102));
        txtfechahora.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtfechahora.setForeground(new java.awt.Color(102, 102, 102));
        txtfechahora.setText("jLabel3");

        txtvercion.setBackground(new java.awt.Color(102, 102, 102));
        txtvercion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtvercion.setForeground(new java.awt.Color(102, 102, 102));
        txtvercion.setText("jLabel3");

        btncotizacion.setText("COTIZACION");
        btncotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncotizacionActionPerformed(evt);
            }
        });

        btncombo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/72_combo.png"))); // NOI18N
        btncombo.setText("COMBO");
        btncombo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncombo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncomboActionPerformed(evt);
            }
        });

        escritorio.setLayer(btncliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnproducto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btngasto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lblusuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnshopp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncambiar_usuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(txtfechahora, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(txtvercion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncotizacion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btncombo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnshopp, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnproducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncombo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btngasto)
                .addGap(105, 105, 105)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createSequentialGroup()
                        .addComponent(txtfechahora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtvercion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblusuario))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btncotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncambiar_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btngasto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(escritorioLayout.createSequentialGroup()
                                    .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtfechahora)
                                        .addComponent(txtvercion)
                                        .addComponent(lblusuario))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btncotizacion)
                                    .addGap(5, 5, 5)
                                    .addComponent(btncambiar_usuario))
                                .addComponent(btncliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnshopp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btncombo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(425, Short.MAX_VALUE))
        );

        jMenu_cliente.setText("CLIENTE");

        jMenuItem2.setText("CREAR CLIENTE");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu_cliente.add(jMenuItem2);

        jMenuItem3.setText("ZONA DELIVERY");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu_cliente.add(jMenuItem3);

        jMenuBar1.add(jMenu_cliente);

        jMenu_producto.setText("PRODUCTO");

        jMenuItem4.setText("CREAR PRODUCTO");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem4);

        jMenuItem32.setText("MARCA");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem32);

        jMenuItem5.setText("CATEGORIA");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem5);

        jMenuItem6.setText("UNIDAD");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem6);

        jMenuItem40.setText("CREAR COMBO ");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu_producto.add(jMenuItem40);

        jMenuBar1.add(jMenu_producto);

        jMenu_delivery.setText("DELIVERY");

        jMenuItem8.setText("ZONA");
        jMenu_delivery.add(jMenuItem8);

        jMenuItem9.setText("ENTREGADOR");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu_delivery.add(jMenuItem9);

        jMenuItem27.setText("DELIVERY VENTA");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu_delivery.add(jMenuItem27);

        jMenuBar1.add(jMenu_delivery);

        jMenu_config.setText("CONFIGURACION");

        jMenuItem10.setText("USUARIO");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu_config.add(jMenuItem10);

        jMenu1.setText("BACKUP");

        jMenuItem15.setText("DATOS BACKUP");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem15);

        jMenuItem16.setText("CREAR BACKUP");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenu_config.add(jMenu1);

        jMenuItem17.setText("COTIZACION");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu_config.add(jMenuItem17);

        jMenuBar1.add(jMenu_config);

        jMenu_gasto.setText("GASTO");

        jMenuItem12.setText("GASTO");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu_gasto.add(jMenuItem12);

        jMenuItem13.setText("GASTO TIPO");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu_gasto.add(jMenuItem13);

        jMenuBar1.add(jMenu_gasto);

        jMenu4.setText("BANCO");

        jMenuItem26.setText("NOMBRE BANCO");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem26);

        jMenuItem31.setText("DATO BANCO");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem31);

        jMenuBar1.add(jMenu4);

        jMenu8.setText("ALQUILER");

        jMenuItem41.setText("TIPO EVENTO");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem41);

        jMenuBar1.add(jMenu8);

        jMenu3.setText("INFORMES");

        jMenuItem1.setText("REP. ALQUILER SIMPLE");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem7.setText("RECIBOS DE PAGO CLIENTE");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem11.setText("TRANSACCION BANCO");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmZonaDelivery());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_categoria());
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_unidad());
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclienteActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_btnclienteActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto());
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnproductoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto());
    }//GEN-LAST:event_btnproductoActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_tipo());
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void btngastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngastoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto());
    }//GEN-LAST:event_btngastoActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario());
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        conectar_servidor(true);
        if (corte.verificar_corte_admin(conn)) {
            JDiaLogin log = new JDiaLogin(this, true);
            log.setVisible(true);
        }
//        pdao.ancho_tabla_producto_stock_minimo(tbl_producto_stock_minimo);
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmBackup());
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCrearBackup());
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void btnshoppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnshoppActionPerformed
        // TODO add your handling code here:
//        evetbl.abrir_TablaJinternal(new FrmBalanceCaja());
        evetbl.abrir_TablaJinternal(new FrmVenta_alquiler());
    }//GEN-LAST:event_btnshoppActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCotizacion());
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void btncambiar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncambiar_usuarioActionPerformed
        // TODO add your handling code here:
        JDiaLogin log = new JDiaLogin(this, true);
        log.setVisible(true);
    }//GEN-LAST:event_btncambiar_usuarioActionPerformed

    private void btncotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncotizacionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCotizacion());
    }//GEN-LAST:event_btncotizacionActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEntregador_venta());
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_marca());
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmBanco());
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmDato_Banco());
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_combo_alquiler());
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTipo_evento());
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void btncomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncomboActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProducto_combo_alquiler());
    }//GEN-LAST:event_btncomboActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepVentaAlquiler());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepReciboCliente());
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRepTransaccionBanco());
    }//GEN-LAST:event_jMenuItem11ActionPerformed

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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMenuAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenuAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenuAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenuAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuAlquiler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btncambiar_usuario;
    public static javax.swing.JButton btncliente;
    public static javax.swing.JButton btncombo;
    public static javax.swing.JButton btncotizacion;
    public static javax.swing.JButton btngasto;
    public static javax.swing.JButton btnproducto;
    public static javax.swing.JButton btnshopp;
    public static javax.swing.JDesktopPane escritorio;
    public static javax.swing.JFormattedTextField jFdolar;
    public static javax.swing.JFormattedTextField jFreal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public static javax.swing.JMenu jMenu_cliente;
    public static javax.swing.JMenu jMenu_config;
    public static javax.swing.JMenu jMenu_delivery;
    public static javax.swing.JMenu jMenu_gasto;
    public static javax.swing.JMenu jMenu_producto;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblusuario;
    private javax.swing.JLabel txtfechahora;
    private javax.swing.JLabel txtvercion;
    // End of variables declaration//GEN-END:variables
}
