/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADO.EvenEstado;
import Evento.Color.cla_color_pelete;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmRecibo_pago_cliente extends javax.swing.JInternalFrame {

    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejta = new EvenJtable();
    EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    cla_color_pelete clacolor = new cla_color_pelete();
    EvenFecha evefec = new EvenFecha();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private usuario ENTusu = new usuario();
    private recibo_pago_cliente ENTrpc = new recibo_pago_cliente();
    private grupo_credito_cliente ENTgcc = new grupo_credito_cliente();
    private cliente ENTcli = new cliente();
    private credito_cliente ENTcc1 = new credito_cliente();
    private credito_cliente ENTcc2 = new credito_cliente();
    private grupo_credito_cliente ENTgcc2 = new grupo_credito_cliente();
    private saldo_credito_cliente ENTscc = new saldo_credito_cliente();
    private caja_detalle ENTcd = new caja_detalle();
    private caja_detalle_alquilado ENTcda = new caja_detalle_alquilado();
    private DAO_recibo_pago_cliente DAOrpc = new DAO_recibo_pago_cliente();
    private DAO_grupo_credito_cliente DAOgcc = new DAO_grupo_credito_cliente();
    private DAO_credito_cliente DAOcc = new DAO_credito_cliente();
    private DAO_cliente DAOcli = new DAO_cliente();
    private DAO_grupo_credito_cliente DAOgcc2 = new DAO_grupo_credito_cliente();
    private DAO_caja_detalle_alquilado DAOcda = new DAO_caja_detalle_alquilado();
    private BO_recibo_pago_cliente BOrpc = new BO_recibo_pago_cliente();
    private BO_cliente BOcli = new BO_cliente();
    private transaccion_banco ENTtb = new transaccion_banco();
    private DAO_transaccion_banco DAOtb = new DAO_transaccion_banco();
    private BO_transaccion_banco BOtb = new BO_transaccion_banco();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private EvenEstado eveest = new EvenEstado();
    private boolean hab_guardar;
    private int fk_idcliente;
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
//    private String forma_pago_EFECTIVO = "EFECTIVO";
    private double monto_recibo_pago;
    private double monto_saldo_credito;
    private String monto_letra;
    private String tabla_origen = ENTcd.getTabla_origen_recibo();
    private int fk_idusuario;
    private int fk_idrecibo_pago_cliente;
    private double Lmonto_saldo_credito;
    private int tipo_pago;
    private String forma_pago;
    private double monto_recibo_efectivo;
    private double monto_recibo_tarjeta;
    private double monto_recibo_transferencia;
    private boolean hab_carga_dato_banco = false;
    private int fk_iddato_banco=0;
    private int fk_iddato_banco_cliente=0;
    private boolean hab_carga_dato_banco_cliente = false;

    private void abrir_formulario() {
        this.setTitle("RECIBO PAGO CLIENTE");
        evetbl.centrar_formulario_internalframa(this);
        fk_idusuario = ENTusu.getGlobal_idusuario();
        fk_idcliente = ENTcli.getC1idcliente_global();
        cargar_cliente();
        cargar_dato_banco();
        cargar_dato_banco_cliente();
        reestableser();
        DAOrpc.actualizar_tabla_recibo_pago_cliente(conn, tblpro_categoria);
        color_formulario();

    }

    private void cargar_dato_banco() {
        evecmb.cargarCombobox(conn, cmbdato_banco, "db.iddato_banco", "b.nombre||'-'||db.nro_cuenta", "banco b,dato_banco db",
                "where db.fk_idbanco=b.idbanco and db.activo=true ");
        hab_carga_dato_banco = true;
    }

    private void select_dato_banco() {
        if (hab_carga_dato_banco) {
            fk_iddato_banco = evecmb.getInt_seleccionar_COMBOBOX_where(conn, cmbdato_banco, "db.iddato_banco",
                    "b.nombre,'-',db.nro_cuenta", "banco b,dato_banco db", "and db.fk_idbanco=b.idbanco and db.activo=true ");
            System.out.println("fk_iddato_banco:" + fk_iddato_banco);
        }
    }

    private void cargar_dato_banco_cliente() {
        evecmb.cargarCombobox(conn, cmbdato_banco_cliente, "db.iddato_banco_cliente",
                "b.nombre||'-'||db.nro_cuenta",
                "banco b,dato_banco_cliente db,item_banco_cliente ibc",
                "where db.fk_idbanco=b.idbanco "
                + "and db.iddato_banco_cliente=ibc.fk_iddato_banco_cliente "
                + "and db.activo=true "
                + "and ibc.fk_idcliente=" + fk_idcliente);
        hab_carga_dato_banco_cliente = true;
    }

    private void select_dato_banco_cliente() {
        if (hab_carga_dato_banco_cliente) {
            fk_iddato_banco_cliente = evecmb.getInt_seleccionar_COMBOBOX_where(conn, cmbdato_banco_cliente, "db.iddato_banco_cliente",
                    "b.nombre,'-',db.nro_cuenta",
                    "banco b,dato_banco_cliente db,item_banco_cliente ibc",
                    "and db.fk_idbanco=b.idbanco "
                    + "and db.iddato_banco_cliente=ibc.fk_iddato_banco_cliente "
                    + "and db.activo=true "
                    + "and ibc.fk_idcliente=" + fk_idcliente);
            System.out.println("fk_iddato_banco_cliente:" + fk_iddato_banco_cliente);
        }
    }

    private void color_formulario() {
        panel_tabla_categoria.setBackground(clacolor.getColor_tabla());
        panel_insertar_categoria.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtrec_descripcion, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtrec_monto_recibo_pago, "DEBE CARGAR UN MONTO")) {
            return false;
        }
        if (monto_recibo_pago > (Math.abs(ENTcli.getC13saldo_credito()))) {
            JOptionPane.showMessageDialog(null, "EL MONTO EXEDE EL MONTO MAXIMO DE PAGO", "ERROR", JOptionPane.ERROR_MESSAGE);
            monto_recibo_pago = Math.abs(ENTcli.getC13saldo_credito());
            txtrec_monto_recibo_pago.setText(String.valueOf((int) monto_recibo_pago));
            cargar_monto();
            return false;
        }
        if (tipo_pago == 3) {
            if (evejtf.getBoo_JTextField_vacio(txtnro_transaccion, "DEBE CARGAR UN NRO DE TRANSACCION")) {
                return false;
            }
            if (evejtf.getBoo_JTextField_vacio(txtobservacion, "DEBE CARGAR UNA OBSERVACION")) {
                return false;
            }
            if(cmbdato_banco.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(cmbdato_banco, "SELECCIONE UN BANCO DE MI BANCO", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if(cmbdato_banco_cliente.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(cmbdato_banco_cliente, "SELECCIONE UN BANCO DEL CLIENTE", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void cargar_monto() {
        if (txtrec_monto_recibo_pago.getText().trim().length() > 0) {
            monto_recibo_pago = evejtf.getDouble_format_nro_entero(txtrec_monto_recibo_pago);
            String monto_recibo = evejtf.getString_format_nro_entero(monto_recibo_pago);
            monto_letra = nroletra.Convertir(monto_recibo, true);
            txtrec_monto_letra.setText(monto_letra);
            Lmonto_saldo_credito = ENTcli.getC13saldo_credito() + monto_recibo_pago;
            jFnuevo_saldo.setValue(Lmonto_saldo_credito);
            monto_saldo_credito = Math.abs(Lmonto_saldo_credito);
            if (Lmonto_saldo_credito >= 0) {
                jFnuevo_saldo.setBackground(Color.yellow);
            } else {
                jFnuevo_saldo.setBackground(Color.white);
            }
        }
    }

    private void cargar_recibo_pago_cliente() {
        ENTrpc.setC3descripcion(txtrec_descripcion.getText());
        ENTrpc.setC4monto_recibo_pago(monto_recibo_pago);
        ENTrpc.setC5monto_letra(monto_letra);
        ENTrpc.setC6estado(estado_EMITIDO);
        ENTrpc.setC7fk_idcliente(fk_idcliente);
        ENTrpc.setC8fk_idusuario(fk_idusuario);
        ENTrpc.setC9forma_pago(forma_pago);
        ENTrpc.setC10monto_recibo_efectivo(monto_recibo_efectivo);
        ENTrpc.setC11monto_recibo_tarjeta(monto_recibo_tarjeta);
        ENTrpc.setC12monto_recibo_transferencia(monto_recibo_transferencia);

    }

    private void cargar_credito_cliente_recibo() {
        fk_idrecibo_pago_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTrpc.getTb_recibo_pago_cliente(), ENTrpc.getId_idrecibo_pago_cliente()));
        DAOgcc2.cargar_grupo_credito_cliente_id(conn, ENTgcc2, fk_idcliente);
        ENTcc1.setC3descripcion(txtrec_descripcion.getText());
        ENTcc1.setC4estado(estado_EMITIDO);
        ENTcc1.setC5monto_contado(monto_recibo_pago);
        ENTcc1.setC6monto_credito(0);
        ENTcc1.setC7tabla_origen(tabla_origen);
        ENTcc1.setC8fk_idgrupo_credito_cliente(ENTgcc2.getC1idgrupo_credito_cliente());
        ENTcc1.setC11fk_idventa_alquiler(0);
        ENTcc1.setC10fk_idrecibo_pago_cliente(fk_idrecibo_pago_cliente);
        ENTcc1.setC9fk_idsaldo_credito_cliente(0);
        ENTcc1.setC12vence(false);
        ENTcc1.setC13fecha_vence(evefec.getString_formato_fecha_hora_zona());
    }

    private void cargar_credito_cliente_saldo() {
        ENTcc2.setC3descripcion("SALDO DEL CIERRE ANTERIOR");
        ENTcc2.setC4estado(estado_EMITIDO);
        ENTcc2.setC5monto_contado(0);
        ENTcc2.setC6monto_credito(monto_saldo_credito);
        ENTcc2.setC7tabla_origen(tabla_origen);
        ENTcc2.setC11fk_idventa_alquiler(0);
        ENTcc2.setC10fk_idrecibo_pago_cliente(0);
        ENTcc2.setC12vence(false);
        ENTcc2.setC13fecha_vence(evefec.getString_formato_fecha_hora_zona());
    }

    private void cargar_saldo_credito_cliente() {
        ENTscc.setC3descripcion("SALDO DEL CIERRE ANTERIOR");
        ENTscc.setC4monto_saldo_credito(monto_saldo_credito);
        String Smonto_saldo_credito = String.valueOf(monto_saldo_credito);
        ENTscc.setC5monto_letra(nroletra.Convertir(Smonto_saldo_credito, true));
        ENTscc.setC6estado(estado_EMITIDO);
        ENTscc.setC7fk_idcliente(fk_idcliente);
        ENTscc.setC8fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_caja_alquilado() {
        DAOcda.limpiar_caja_detalle_alquilado(ENTcda);
        ENTcda.setC3descripcion("(RECIBO CLIENTE) ID:" + fk_idrecibo_pago_cliente + " DESCRIP:" + txtrec_descripcion.getText());
        ENTcda.setC4tabla_origen(tabla_origen);
        ENTcda.setC5estado(estado_EMITIDO);
        ENTcda.setC10monto_recibo_pago(monto_recibo_pago);
        ENTcda.setC22fk_idrecibo_pago_cliente(fk_idrecibo_pago_cliente);
        ENTcda.setC25forma_pago(forma_pago);
        ENTcda.setC26monto_recibo_efectivo(monto_recibo_efectivo);
        ENTcda.setC27monto_recibo_tarjeta(monto_recibo_tarjeta);
        ENTcda.setC28monto_recibo_transferencia(monto_recibo_transferencia);

    }

    private void cargar_dato_transccion_banco() {
        if (tipo_pago == 3) {
            ENTtb.setC3nro_transaccion(txtnro_transaccion.getText());
            ENTtb.setC4monto(monto_recibo_pago);
            ENTtb.setC5observacion(txtobservacion.getText());
            ENTtb.setC6concepto(txtrec_descripcion.getText());
            ENTtb.setC7fk_iddato_banco2(fk_iddato_banco);
            ENTtb.setC8fk_iddato_banco_cliente2(fk_iddato_banco_cliente);
            ENTtb.setC9fk_idrecibo_pago_cliente2(fk_idrecibo_pago_cliente);
        }
    }
    private void boton_transaccion(){
        cargar_dato_transccion_banco();
        BOtb.insertar_transaccion_banco(ENTtb);
    }
    private void boton_guardar() {
        if (hab_guardar) {
            if (validar_guardar()) {
                cargar_recibo_pago_cliente();
                cargar_credito_cliente_recibo();
                cargar_saldo_credito_cliente();
                cargar_credito_cliente_saldo();
                cargar_dato_caja_alquilado();
                cargar_dato_transccion_banco();
                ENTcli.setC1idcliente(fk_idcliente);
                if (BOcli.getBoolean_insertar_cliente_con_recibo_pago1(ENTcli, ENTcc1, ENTcc2, ENTgcc2, ENTrpc, ENTscc, ENTcda, ENTtb, tipo_pago)) {
                    reestableser();
                    DAOrpc.actualizar_tabla_recibo_pago_cliente(conn, tblpro_categoria);
                    DAOcli.actualizar_tabla_cliente2(conn, FrmCliente.tblcliente_credito_resumen);
                    DAOgcc.actualizar_tabla_grupo_credito_cliente_idc(conn, FrmCliente.tblgrupo_credito_cliente, fk_idcliente);
                    DAOgcc.cargar_grupo_credito_cliente_id(conn, ENTgcc, fk_idcliente);
                    DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, FrmCliente.tblcredito_cliente, ENTgcc.getC1idgrupo_credito_cliente());
                    if (evemen.MensajeGeneral_question("DESEA CERRAR EL RECIBO", "RECIBO", "CERRAR", "CANCELAR")) {
                        this.dispose();
                    }
                }
            }
        }
    }

    private void seleccionar_tabla() {
        int id = evejta.getInt_select_id(tblpro_categoria);
        DAOrpc.cargar_recibo_pago_cliente(conn, ENTrpc, id);
        txtid.setText(String.valueOf(ENTrpc.getC1idrecibo_pago_cliente()));
        txtrec_descripcion.setText(ENTrpc.getC3descripcion());
//        btnguardar.setEnabled(false);
        hab_guardar = false;
    }

    private void reestableser() {
        txtid.setText(null);
        jPanel_datobanco.setVisible(false);
        txtrec_fecha_emision.setText(evefec.getString_formato_fecha_hora());
        txtrec_descripcion.setText("RECIBO DE PAGO PARA: " + ENTcli.getC3nombre());
        txtrec_monto_recibo_pago.setText(null);
        txtrec_monto_letra.setText(null);
        txtnro_transaccion.setText(null);
        txtobservacion.setText("Ninguna");
//        btnguardar.setEnabled(true);
        select_forma_pago(1);
        hab_guardar = true;
        txtrec_descripcion.grabFocus();
    }

    private void limpiar_cliente_local() {
        fk_idcliente = 0;
        txtcli_nombre.setText(null);
        txtcli_ruc.setText(null);
        txtcli_direccion.setText(null);
        txtcli_telefono.setText(null);
        jFcli_saldo_credito.setValue(null);
        txtcli_fec_limite.setText(null);
        txtcli_nombre.grabFocus();
    }

    private void cargar_cliente() {
        DAOcli.cargar_cliente(conn, ENTcli, fk_idcliente);
        cargar_cliente_local(ENTcli);
    }

    private void cargar_cliente_local(cliente cli) {
        fk_idcliente = cli.getC1idcliente();
        txtcli_nombre.setText(cli.getC3nombre());
        txtcli_ruc.setText(cli.getC6ruc());
        txtcli_direccion.setText(cli.getC4direccion());
        txtcli_telefono.setText(cli.getC5telefono());
        jFcli_saldo_credito.setValue(cli.getC13saldo_credito());
    }

    private void select_forma_pago(int tipo) {
        tipo_pago = tipo;
        forma_pago = "nulo";
        monto_recibo_efectivo = 0;
        monto_recibo_tarjeta = 0;
        monto_recibo_transferencia = 0;
        if (tipo == 1) {
            forma_pago = (eveest.getForm_pago_EFECTIVO());
            monto_recibo_efectivo = (monto_recibo_pago);
            btnpago_efectivo.setBackground(Color.yellow);
            btnpago_taarjeta.setBackground(Color.white);
            btnpago_banco.setBackground(Color.white);
            btnconfirmar_pago.setText("PAGO (EFECTIVO)");
            jPanel_datobanco.setVisible(false);
        }
        if (tipo == 2) {
            forma_pago = (eveest.getForm_pago_TARJETA());
            monto_recibo_tarjeta = (monto_recibo_pago);
            btnpago_efectivo.setBackground(Color.white);
            btnpago_taarjeta.setBackground(Color.yellow);
            btnpago_banco.setBackground(Color.white);
            btnconfirmar_pago.setText("PAGO (TARJETA)");
            jPanel_datobanco.setVisible(false);
        }
        if (tipo == 3) {
            forma_pago = (eveest.getForm_pago_TRANSFERENCIA());
            monto_recibo_transferencia = (monto_recibo_pago);
            btnpago_efectivo.setBackground(Color.white);
            btnpago_taarjeta.setBackground(Color.white);
            btnpago_banco.setBackground(Color.yellow);
            btnconfirmar_pago.setText("PAGO (TRANSFERENCIA)");
            jPanel_datobanco.setVisible(true);
        }
    }

    public FrmRecibo_pago_cliente() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_insertar_categoria = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtrec_descripcion = new javax.swing.JTextField();
        panel_dato_cliente = new javax.swing.JPanel();
        txtcli_nombre = new javax.swing.JTextField();
        txtcli_direccion = new javax.swing.JTextField();
        txtcli_ruc = new javax.swing.JTextField();
        txtcli_telefono = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jFcli_saldo_credito = new javax.swing.JFormattedTextField();
        txtcli_fec_limite = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtrec_fecha_emision = new javax.swing.JTextField();
        txtrec_monto_recibo_pago = new javax.swing.JTextField();
        txtrec_monto_letra = new javax.swing.JTextField();
        jFnuevo_saldo = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        btnpago_efectivo = new javax.swing.JButton();
        btnpago_taarjeta = new javax.swing.JButton();
        btnpago_banco = new javax.swing.JButton();
        jPanel_datobanco = new javax.swing.JPanel();
        txtnro_transaccion = new javax.swing.JTextField();
        txtobservacion = new javax.swing.JTextField();
        cmbdato_banco = new javax.swing.JComboBox<>();
        cmbdato_banco_cliente = new javax.swing.JComboBox<>();
        btnconfirmar_pago = new javax.swing.JButton();
        panel_tabla_categoria = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpro_categoria = new javax.swing.JTable();

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

        panel_insertar_categoria.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_categoria.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtrec_descripcion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrec_descripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        txtrec_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_descripcionKeyPressed(evt);
            }
        });

        panel_dato_cliente.setBackground(new java.awt.Color(204, 204, 255));
        panel_dato_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTE"));

        txtcli_nombre.setEditable(false);
        txtcli_nombre.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));
        txtcli_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_nombreKeyPressed(evt);
            }
        });

        txtcli_direccion.setEditable(false);
        txtcli_direccion.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_direccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_direccion.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCION"));

        txtcli_ruc.setEditable(false);
        txtcli_ruc.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_ruc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_ruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));
        txtcli_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcli_rucKeyPressed(evt);
            }
        });

        txtcli_telefono.setEditable(false);
        txtcli_telefono.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_telefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_telefono.setBorder(javax.swing.BorderFactory.createTitledBorder("TELEFONO"));

        jFcli_saldo_credito.setEditable(false);
        jFcli_saldo_credito.setBackground(new java.awt.Color(204, 204, 204));
        jFcli_saldo_credito.setBorder(javax.swing.BorderFactory.createTitledBorder("SALDO"));
        jFcli_saldo_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFcli_saldo_credito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        txtcli_fec_limite.setEditable(false);
        txtcli_fec_limite.setBackground(new java.awt.Color(204, 204, 204));
        txtcli_fec_limite.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA LIMITE"));

        javax.swing.GroupLayout panel_dato_clienteLayout = new javax.swing.GroupLayout(panel_dato_cliente);
        panel_dato_cliente.setLayout(panel_dato_clienteLayout);
        panel_dato_clienteLayout.setHorizontalGroup(
            panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                        .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                                .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                                .addComponent(jFcli_saldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcli_fec_limite, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                                .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_dato_clienteLayout.setVerticalGroup(
            panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dato_clienteLayout.createSequentialGroup()
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_dato_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFcli_saldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcli_fec_limite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("FECHA:");

        txtrec_fecha_emision.setEditable(false);
        txtrec_fecha_emision.setBackground(new java.awt.Color(204, 204, 255));
        txtrec_fecha_emision.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrec_fecha_emision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_fecha_emisionKeyPressed(evt);
            }
        });

        txtrec_monto_recibo_pago.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        txtrec_monto_recibo_pago.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtrec_monto_recibo_pago.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));
        txtrec_monto_recibo_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_monto_recibo_pagoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrec_monto_recibo_pagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrec_monto_recibo_pagoKeyTyped(evt);
            }
        });

        txtrec_monto_letra.setEditable(false);
        txtrec_monto_letra.setBackground(new java.awt.Color(204, 204, 255));
        txtrec_monto_letra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrec_monto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO LETRA"));
        txtrec_monto_letra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrec_monto_letraKeyPressed(evt);
            }
        });

        jFnuevo_saldo.setEditable(false);
        jFnuevo_saldo.setBorder(javax.swing.BorderFactory.createTitledBorder("NUEVO SALDO"));
        jFnuevo_saldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFnuevo_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFnuevo_saldo.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("FORMA PAGO"));

        btnpago_efectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/efectivo.png"))); // NOI18N
        btnpago_efectivo.setText("EFECTIVO");
        btnpago_efectivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnpago_efectivo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnpago_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpago_efectivoActionPerformed(evt);
            }
        });

        btnpago_taarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/credito.png"))); // NOI18N
        btnpago_taarjeta.setText("TARJETA");
        btnpago_taarjeta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnpago_taarjeta.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnpago_taarjeta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnpago_taarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpago_taarjetaActionPerformed(evt);
            }
        });

        btnpago_banco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/pago_banco.png"))); // NOI18N
        btnpago_banco.setText("BANCO");
        btnpago_banco.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnpago_banco.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnpago_banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpago_bancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnpago_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpago_taarjeta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpago_banco)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnpago_efectivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnpago_taarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnpago_banco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_datobanco.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DE BANCO"));

        txtnro_transaccion.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO TRANSACION o  REFERENCIA"));

        txtobservacion.setBorder(javax.swing.BorderFactory.createTitledBorder("OBSERVACION"));

        cmbdato_banco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbdato_banco.setBorder(javax.swing.BorderFactory.createTitledBorder("MI BANCO"));
        cmbdato_banco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbdato_bancoItemStateChanged(evt);
            }
        });

        cmbdato_banco_cliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbdato_banco_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("BANCO CLIENTE"));
        cmbdato_banco_cliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbdato_banco_clienteItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel_datobancoLayout = new javax.swing.GroupLayout(jPanel_datobanco);
        jPanel_datobanco.setLayout(jPanel_datobancoLayout);
        jPanel_datobancoLayout.setHorizontalGroup(
            jPanel_datobancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_datobancoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_datobancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnro_transaccion)
                    .addComponent(txtobservacion)
                    .addComponent(cmbdato_banco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbdato_banco_cliente, 0, 299, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_datobancoLayout.setVerticalGroup(
            jPanel_datobancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_datobancoLayout.createSequentialGroup()
                .addComponent(txtnro_transaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbdato_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbdato_banco_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        btnconfirmar_pago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnconfirmar_pago.setText("CONFIRMAR PAGO");
        btnconfirmar_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_pagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_categoriaLayout = new javax.swing.GroupLayout(panel_insertar_categoria);
        panel_insertar_categoria.setLayout(panel_insertar_categoriaLayout);
        panel_insertar_categoriaLayout.setHorizontalGroup(
            panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtrec_fecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtrec_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addComponent(panel_dato_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                        .addComponent(txtrec_monto_recibo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFnuevo_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtrec_monto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnconfirmar_pago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_datobanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_insertar_categoriaLayout.setVerticalGroup(
            panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                        .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtrec_fecha_emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(panel_dato_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrec_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtrec_monto_recibo_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFnuevo_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtrec_monto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_categoriaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_datobanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnconfirmar_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CREAR RECIBO", panel_insertar_categoria);

        panel_tabla_categoria.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_categoria.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblpro_categoria.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpro_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpro_categoriaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblpro_categoria);

        javax.swing.GroupLayout panel_tabla_categoriaLayout = new javax.swing.GroupLayout(panel_tabla_categoria);
        panel_tabla_categoria.setLayout(panel_tabla_categoriaLayout);
        panel_tabla_categoriaLayout.setHorizontalGroup(
            panel_tabla_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE)
        );
        panel_tabla_categoriaLayout.setVerticalGroup(
            panel_tabla_categoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_categoriaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TABLAS", panel_tabla_categoria);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOrpc.ancho_tabla_recibo_pago_cliente(tblpro_categoria);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblpro_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpro_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblpro_categoriaMouseReleased

    private void txtrec_descripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_descripcionKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_guardar();
        }
    }//GEN-LAST:event_txtrec_descripcionKeyPressed

    private void txtcli_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_nombreKeyPressed
        // TODO add your handling code here:
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            if (txtcli_nombre.getText().trim().length() > 0) {
//                txtbuscador_cliente_nombre.setText(txtcli_nombre.getText());
//                cdao.actualizar_tabla_cliente_buscar(conn, tblbuscar_cliente, "nombre", txtbuscador_cliente_nombre.getText());
//                eveJtab.mostrar_JTabbedPane(tab_venta, 1);
//            }
//        }
    }//GEN-LAST:event_txtcli_nombreKeyPressed

    private void txtcli_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcli_rucKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            buscar_cargar_cliente_ruc();
        }
    }//GEN-LAST:event_txtcli_rucKeyPressed

    private void txtrec_fecha_emisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_fecha_emisionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrec_fecha_emisionKeyPressed

    private void txtrec_monto_recibo_pagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_recibo_pagoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrec_monto_recibo_pagoKeyPressed

    private void txtrec_monto_letraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_letraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrec_monto_letraKeyPressed

    private void txtrec_monto_recibo_pagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_recibo_pagoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtrec_monto_recibo_pagoKeyTyped

    private void txtrec_monto_recibo_pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrec_monto_recibo_pagoKeyReleased
        // TODO add your handling code here:
        cargar_monto();
    }//GEN-LAST:event_txtrec_monto_recibo_pagoKeyReleased

    private void btnpago_efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpago_efectivoActionPerformed
        // TODO add your handling code here:
        select_forma_pago(1);
    }//GEN-LAST:event_btnpago_efectivoActionPerformed

    private void btnpago_taarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpago_taarjetaActionPerformed
        // TODO add your handling code here:
        select_forma_pago(2);
    }//GEN-LAST:event_btnpago_taarjetaActionPerformed

    private void btnpago_bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpago_bancoActionPerformed
        // TODO add your handling code here:
//        boton_guardar(3);
        select_forma_pago(3);
    }//GEN-LAST:event_btnpago_bancoActionPerformed

    private void btnconfirmar_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_pagoActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnconfirmar_pagoActionPerformed

    private void cmbdato_bancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbdato_bancoItemStateChanged
        // TODO add your handling code here:
        select_dato_banco();
    }//GEN-LAST:event_cmbdato_bancoItemStateChanged

    private void cmbdato_banco_clienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbdato_banco_clienteItemStateChanged
        // TODO add your handling code here:
        select_dato_banco_cliente();
    }//GEN-LAST:event_cmbdato_banco_clienteItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnconfirmar_pago;
    private javax.swing.JButton btnpago_banco;
    private javax.swing.JButton btnpago_efectivo;
    private javax.swing.JButton btnpago_taarjeta;
    private javax.swing.JComboBox<String> cmbdato_banco;
    private javax.swing.JComboBox<String> cmbdato_banco_cliente;
    private javax.swing.JFormattedTextField jFcli_saldo_credito;
    private javax.swing.JFormattedTextField jFnuevo_saldo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_datobanco;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_dato_cliente;
    private javax.swing.JPanel panel_insertar_categoria;
    private javax.swing.JPanel panel_tabla_categoria;
    private javax.swing.JTable tblpro_categoria;
    private javax.swing.JTextField txtcli_direccion;
    private javax.swing.JTextField txtcli_fec_limite;
    private javax.swing.JTextField txtcli_nombre;
    private javax.swing.JTextField txtcli_ruc;
    private javax.swing.JTextField txtcli_telefono;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnro_transaccion;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtrec_descripcion;
    private javax.swing.JTextField txtrec_fecha_emision;
    private javax.swing.JTextField txtrec_monto_letra;
    private javax.swing.JTextField txtrec_monto_recibo_pago;
    // End of variables declaration//GEN-END:variables
}
