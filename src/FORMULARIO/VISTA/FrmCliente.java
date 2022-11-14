/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmCliente extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenFecha evefec = new EvenFecha();
    private EvenConexion eveconn = new EvenConexion();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenJtable evejt = new EvenJtable();
    Connection conn = ConnPostgres.getConnPosgres();
    private cliente ENTcli = new cliente();
    private BO_cliente BOcli = new BO_cliente();
    private DAO_cliente DAOcli = new DAO_cliente();
    private zona_delivery ENTzn = new zona_delivery();
    private DAO_zona_delivery DAOzn = new DAO_zona_delivery();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private cla_color_pelete clacolor = new cla_color_pelete();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private usuario ENTusu = new usuario();
    private saldo_credito_cliente ENTscc = new saldo_credito_cliente();
    private credito_cliente ENTcc = new credito_cliente();
    private grupo_credito_cliente ENTgcc = new grupo_credito_cliente();
    private DAO_grupo_credito_cliente DAOgcc = new DAO_grupo_credito_cliente();
    private DAO_credito_cliente DAOcc = new DAO_credito_cliente();
    private dato_banco_cliente ENTdb = new dato_banco_cliente();
    private BO_dato_banco_cliente BOdb = new BO_dato_banco_cliente();
    private DAO_dato_banco_cliente DAOdb = new DAO_dato_banco_cliente();
    private item_banco_cliente ENTibc = new item_banco_cliente();
//    private item_banco_cliente ENTibc=new item_banco_cliente();
    private banco ENTb = new banco();
    private BO_banco BOb = new BO_banco();
    private DAO_banco DAOb = new DAO_banco();
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private int fk_idbanco;
    private boolean hab_carga_banco;
    private int fk_idcliente;

    /**
     * Creates new form FrmZonaDelivery
     */
    private void abrir_formulario_cliente() {
        this.setTitle("CLIENTE");
        evetbl.centrar_formulario_internalframa(this);
        reestableser_cliente();
        reestableser_db();
        color_formulario();
        cargar_banco();
        actualizar_todo();
    }

    private void cargar_banco() {
        evecmb.cargarCombobox(conn, cmbbanco, "idbanco", "nombre", "banco", "");
        hab_carga_banco = true;
    }

    void color_formulario() {
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar_cliente() {
        txtfecha_inicio.setText(evefec.getString_formato_fecha());
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtruc, "DEBE CARGAR UN RUC")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttelefono, "DEBE CARGAR UN TELEFONO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdireccion, "DEBE CARGAR UNA DIRECCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtzona, "DEBE CARGAR UNA ZONA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtsaldo_credito, "DEBE CARGAR UN SALDO DE CREDITO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtfec_inicio_credito, "DEBE CARGAR UNA FECHA INICIO CREDITO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdia_limite_credito, "DEBE CARGAR UN DIA LIMITE CREDITO")) {
            return false;
        }
        if (txtfecha_nacimiento.getText().trim().length() == 0) {
            txtfecha_nacimiento.setText(evefec.getString_formato_fecha());
            ENTcli.setC7fecha_cumple(evefec.getString_formato_fecha());
//            return false;
        }
        return true;
    }

    private String tipo_cliente() {
        String tipo = "cliente";
        return tipo;
    }

    private void cargar_cliente() {
        ENTcli.setC2fecha_inicio("now");
        ENTcli.setC3nombre(txtnombre.getText());
        ENTcli.setC4direccion(txtdireccion.getText());
        ENTcli.setC5telefono(txttelefono.getText());
        ENTcli.setC6ruc(txtruc.getText());
        ENTcli.setC7fecha_cumple(txtfecha_nacimiento.getText());
        ENTcli.setC8tipo(tipo_cliente());
        ENTcli.setC12escredito(jCescredito.isSelected());
        ENTcli.setC13saldo_credito(Double.parseDouble("-" + txtsaldo_credito.getText()));
        ENTcli.setC14fecha_inicio_credito(txtfec_inicio_credito.getText());
        ENTcli.setC15dia_limite_credito(Integer.parseInt(txtdia_limite_credito.getText()));
    }

    private void cargar_saldo_credito_cliente(int idcliente) {
        ENTscc.setC3descripcion("CREDITO DE CLIENTE DE INICIO");
        ENTscc.setC4monto_saldo_credito(ENTcli.getC13saldo_credito());
        ENTscc.setC5monto_letra(nroletra.Convertir(txtsaldo_credito.getText(), true));
        ENTscc.setC6estado(estado_EMITIDO);
        ENTscc.setC7fk_idcliente(idcliente);
        ENTscc.setC8fk_idusuario(ENTusu.getGlobal_idusuario());
    }

    private void cargar_grupo_credito_cliente(int idcliente) {
        ENTgcc.setC4estado(estado_ABIERTO);
        ENTgcc.setC5fk_idcliente(idcliente);
    }

    private void cargar_credito_cliente(int idsaldo_credito_cliente, int idgrupo_credito_cliente) {
        ENTcc.setC3descripcion("CREDITO DE CLIENTE DE INICIO");
        ENTcc.setC4estado(estado_EMITIDO);
        ENTcc.setC5monto_contado(0);
        ENTcc.setC6monto_credito(ENTcli.getC13saldo_credito());
        ENTcc.setC7tabla_origen("CLIENTE");
        ENTcc.setC8fk_idgrupo_credito_cliente(idgrupo_credito_cliente);
        ENTcc.setC11fk_idventa_alquiler(0);
        ENTcc.setC10fk_idrecibo_pago_cliente(0);
        ENTcc.setC9fk_idsaldo_credito_cliente(idsaldo_credito_cliente);
        ENTcc.setC12vence(false);
        ENTcc.setC13fecha_vence(evefec.getString_formato_fecha_hora_zona());
    }

    private void boton_guardar_cliente() {
        if (validar_guardar_cliente()) {
            int idcliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTcli.getTabla(), ENTcli.getIdtabla()));
            int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTscc.getTb_saldo_credito_cliente(), ENTscc.getId_idsaldo_credito_cliente()));
            int idgrupo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTgcc.getTb_grupo_credito_cliente(), ENTgcc.getId_idgrupo_credito_cliente()));
            cargar_cliente();
            cargar_saldo_credito_cliente(idcliente);
            cargar_grupo_credito_cliente(idcliente);
            cargar_credito_cliente(idsaldo_credito_cliente, idgrupo_credito_cliente);
            if (BOcli.getBoolean_insertar_cliente_con_credito_inicio1(ENTcli, ENTscc, ENTcc, ENTgcc)) {
                reestableser_cliente();
                DAOgcc.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
                DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
            }
        }
    }

    private void credito_inicio_para_todos() {
        String titulo = "credito_inicio_para_todos";
        String sql = "select c.idcliente,coalesce((select count(*) as cant from grupo_credito_cliente gcc "
                + "where gcc.fk_idcliente=c.idcliente),0) as grupo  \n"
                + "from cliente c order by 1 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            int contar = 0;
            while (rs.next()) {
                int idcliente = rs.getInt("idcliente");
                int grupo = rs.getInt("grupo");
                if (grupo == 0) {
                    guardar_credito_inicio(idcliente);
                    contar++;
                }
            }
            JOptionPane.showMessageDialog(null, "CANTIDAD DE CREDITO DE INICIO CREADO =" + contar);
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void guardar_credito_inicio(int idcliente) {
        int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTscc.getTb_saldo_credito_cliente(), ENTscc.getId_idsaldo_credito_cliente()));
        int idgrupo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTgcc.getTb_grupo_credito_cliente(), ENTgcc.getId_idgrupo_credito_cliente()));
        cargar_saldo_credito_cliente(idcliente);
        cargar_grupo_credito_cliente(idcliente);
        cargar_credito_cliente(idsaldo_credito_cliente, idgrupo_credito_cliente);
        if (BOcli.getBoolean_insertar_credito_inicio1(ENTscc, ENTcc, ENTgcc)) {
            DAOgcc.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
            DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
        }
    }

    private void boton_editar_cliente() {
        if (validar_guardar_cliente()) {
            ENTcli.setC1idcliente(Integer.parseInt(txtidcliente.getText()));
            ENTcli.setC2fecha_inicio(txtfecha_inicio.getText());
            ENTcli.setC3nombre(txtnombre.getText());
            ENTcli.setC4direccion(txtdireccion.getText());
            ENTcli.setC5telefono(txttelefono.getText());
            ENTcli.setC6ruc(txtruc.getText());
            ENTcli.setC7fecha_cumple(txtfecha_nacimiento.getText());
            ENTcli.setC8tipo(tipo_cliente());
            ENTcli.setC12escredito(jCescredito.isSelected());
            ENTcli.setC13saldo_credito(Double.parseDouble(txtsaldo_credito.getText()));
            ENTcli.setC14fecha_inicio_credito(txtfec_inicio_credito.getText());
            ENTcli.setC15dia_limite_credito(Integer.parseInt(txtdia_limite_credito.getText()));
            BOcli.update_cliente(ENTcli);
        }
    }

    private void seleccionar_tabla_cliente() {
        fk_idcliente = evejt.getInt_select_id(tblcliente_credito_resumen);
        DAOcli.cargar_cliente(conn, ENTcli, fk_idcliente);
        txtidcliente.setText(String.valueOf(ENTcli.getC1idcliente()));
        txtfecha_inicio.setText(ENTcli.getC2fecha_inicio());
        txtnombre.setText(ENTcli.getC3nombre());
        txtdireccion.setText(ENTcli.getC4direccion());
        txttelefono.setText(ENTcli.getC5telefono());
        txtruc.setText(ENTcli.getC6ruc());
        txtfecha_nacimiento.setText(ENTcli.getC7fecha_cumple());
        jCescredito.setSelected(ENTcli.isC12escredito());
        txtsaldo_credito.setText(String.valueOf(ENTcli.getC13saldo_credito()));
        txtfec_inicio_credito.setText(ENTcli.getC14fecha_inicio_credito());
        txtdia_limite_credito.setText(String.valueOf(ENTcli.getC15dia_limite_credito()));
        ENTcli.setC1idcliente_global(fk_idcliente);
        DAOgcc.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, fk_idcliente);
        DAOgcc.cargar_grupo_credito_cliente_id(conn, ENTgcc, fk_idcliente);
        DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, ENTgcc.getC1idgrupo_credito_cliente());
        DAOdb.actualizar_tabla_dato_banco_cliente(conn, tbldato_banco,fk_idcliente);
        reestableser_db();
        txtzona.setText(ENTcli.getC10zona());
        txtdelivery.setText(ENTcli.getC11delivery());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser_cliente() {
        jLzona.setVisible(false);
        txtidcliente.setText(null);
        txtnombre.setText(null);
        txtfecha_inicio.setText(null);
        txtdireccion.setText(null);
        txttelefono.setText(null);
        txtruc.setText(null);
        txtfecha_nacimiento.setText(null);
        txtzona.setText(null);
        txtdelivery.setText(null);
        jCescredito.setSelected(false);
        txtsaldo_credito.setText("0");
        txtfec_inicio_credito.setText(evefec.getString_formato_fecha());
        txtdia_limite_credito.setText("0");
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        btndeletar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void cargar_zona_cliente() {
        int idzona = eveconn.getInt_seleccionar_JLista(conn, txtzona, jLzona, ENTzn.getTabla(), ENTzn.getNombretabla(), ENTzn.getIdtabla());
        ENTcli.setC9fk_idzona_delivery(idzona);
        DAOzn.cargar_zona_delivery(ENTzn, idzona);
        txtdelivery.setText(String.valueOf(ENTzn.getDelivery()));
    }

    private void boton_nuevo_cliente() {
        reestableser_cliente();
    }

    void sumar_monto_credito_cliente() {
        String titulo = "sumar_monto_credito_cliente";
        String sql = "select sum(saldo_credito) as monto from cliente; ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double monto = rs.getDouble("monto");
                jFsaldo_credito_total.setValue(monto);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void actualizar_todo() {
        DAOcli.actualizar_tabla_cliente2(conn, tblcliente_credito_resumen);
        
        sumar_monto_credito_cliente();
    }

    private void cargar_credito_cliente() {
        if (!evejt.getBoolean_validar_select(tblgrupo_credito_cliente)) {
            int idgrupo_credito_cliente = evejt.getInt_select_id(tblgrupo_credito_cliente);
            DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
        }
    }

    private boolean validar_guardar_db() {
        if(!evejt.getBoolean_validar_select_mensaje(tblcliente_credito_resumen, "SELECCIONE UN CLIENTE")){
            evejt.mostrar_JTabbedPane(jTab_cliente,1);
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttitular, "DEBE CARGAR UN TITULAR")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdocumento, "DEBE CARGAR UN DOCUMENTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnrocuenta, "DEBE CARGAR UN NRO CUENTA")) {
            return false;
        }
        return true;
    }

    private void cargar_dato_db() {
        ENTdb.setC2titular(txttitular.getText());
        ENTdb.setC3documento(txtdocumento.getText());
        ENTdb.setC4nro_cuenta(txtnrocuenta.getText());
        ENTdb.setC5activo(jCactivo.isSelected());
        ENTdb.setC6fk_idbanco(fk_idbanco);
    }
    private void cargar_dato_ibc(){
        int iddato_banco_cliente=(eveconn.getInt_ultimoID_mas_uno(conn, ENTdb.getTb_dato_banco_cliente(), ENTdb.getId_iddato_banco_cliente()));
        ENTibc.setC2fk_iddato_banco_cliente(iddato_banco_cliente);
        ENTibc.setC3fk_idcliente(fk_idcliente);
    }
    private void boton_guardar_db() {
        if (validar_guardar_db()) {
            cargar_dato_db();
            cargar_dato_ibc();
            BOdb.insertar_dato_banco_cliente(ENTdb, ENTibc);
            DAOdb.actualizar_tabla_dato_banco_cliente(conn, tbldato_banco,fk_idcliente);
            reestableser_db();
        }
    }

    private void boton_editar_db() {
        if (validar_guardar_db()) {
            ENTdb.setC1iddato_banco_cliente(Integer.parseInt(txtid.getText()));
            cargar_dato_db();
            BOdb.update_dato_banco_cliente(ENTdb);
            DAOdb.actualizar_tabla_dato_banco_cliente(conn, tbldato_banco,fk_idcliente);
        }
    }

    private void seleccionar_tabla_db() {
        int iddato_banco = evejt.getInt_select_id(tbldato_banco);
        DAOdb.cargar_dato_banco_cliente(conn, ENTdb, iddato_banco);
        txtid.setText(String.valueOf(ENTdb.getC1iddato_banco_cliente()));
        txttitular.setText(ENTdb.getC2titular());
        txtdocumento.setText(ENTdb.getC3documento());
        txtnrocuenta.setText(ENTdb.getC4nro_cuenta());
        jCactivo.setSelected(ENTdb.getC5activo());
        DAOb.cargar_banco(conn, ENTb, ENTdb.getC6fk_idbanco());
        cmbbanco.setSelectedItem("(" + ENTb.getC1idbanco() + ")-" + ENTb.getC2nombre());
        btnguardar_db.setEnabled(false);
        btneditar_db.setEnabled(true);
    }

    private void reestableser_db() {
        
        txtid.setText(null);
        txttitular.setText(null);
        txtdocumento.setText(null);
        txtnrocuenta.setText(null);
        cmbbanco.setSelectedIndex(0);
        jCactivo.setSelected(true);
        fk_idbanco = 0;
        btnguardar_db.setEnabled(true);
        btneditar_db.setEnabled(false);
        btndeletar_db.setEnabled(false);
        txttitular.grabFocus();
    }

    private void boton_nuevo_db() {
        reestableser_db();
    }

    public FrmCliente() {
        initComponents();
        abrir_formulario_cliente();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_tipo = new javax.swing.ButtonGroup();
        jTab_cliente = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        panel_insert = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdireccion = new javax.swing.JTextArea();
        txtruc = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtidcliente = new javax.swing.JTextField();
        txtfecha_inicio = new javax.swing.JTextField();
        txtfecha_nacimiento = new javax.swing.JTextField();
        txtzona = new javax.swing.JTextField();
        txtdelivery = new javax.swing.JTextField();
        jLzona = new javax.swing.JList<>();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btndeletar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jCescredito = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        txtsaldo_credito = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtfec_inicio_credito = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtdia_limite_credito = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        cmbbanco = new javax.swing.JComboBox<>();
        jCactivo = new javax.swing.JCheckBox();
        txtnrocuenta = new javax.swing.JTextField();
        txtdocumento = new javax.swing.JTextField();
        txttitular = new javax.swing.JTextField();
        txtid = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldato_banco = new javax.swing.JTable();
        btnnuevo_db = new javax.swing.JButton();
        btnguardar_db = new javax.swing.JButton();
        btneditar_db = new javax.swing.JButton();
        btndeletar_db = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla_cliente = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcliente_credito_resumen = new javax.swing.JTable();
        btnpagar_credito = new javax.swing.JButton();
        btnactualizar_tabla = new javax.swing.JButton();
        btncrearcredito_inicio = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jFsaldo_credito_total = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panel_tabla_cliente1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblgrupo_credito_cliente = new javax.swing.JTable();
        panel_tabla_cliente2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblcredito_cliente = new javax.swing.JTable();

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

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        txtdireccion.setColumns(20);
        txtdireccion.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtdireccion.setRows(5);
        txtdireccion.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCION"));
        txtdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdireccionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtdireccion);

        txtruc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));
        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        txttelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttelefono.setBorder(javax.swing.BorderFactory.createTitledBorder("TELEFONO"));
        txttelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoKeyPressed(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        txtidcliente.setEditable(false);
        txtidcliente.setBackground(new java.awt.Color(204, 204, 204));
        txtidcliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtidcliente.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        txtfecha_inicio.setEditable(false);
        txtfecha_inicio.setBackground(new java.awt.Color(204, 204, 204));
        txtfecha_inicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfecha_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA INICIO"));

        txtfecha_nacimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfecha_nacimiento.setBorder(javax.swing.BorderFactory.createTitledBorder("Fec. Nac.: año-mes-dia"));
        txtfecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfecha_nacimientoKeyTyped(evt);
            }
        });

        txtzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtzona.setBorder(javax.swing.BorderFactory.createTitledBorder("ZONA"));
        txtzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtzonaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtzonaKeyReleased(evt);
            }
        });

        txtdelivery.setEditable(false);
        txtdelivery.setBackground(new java.awt.Color(204, 204, 204));
        txtdelivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtdelivery.setBorder(javax.swing.BorderFactory.createTitledBorder("DELIVERY"));

        jLzona.setBackground(new java.awt.Color(204, 204, 255));
        jLzona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLzona.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jLzona.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jLzona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLzonaMouseReleased(evt);
            }
        });
        jLzona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLzonaKeyReleased(evt);
            }
        });

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

        btndeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/eliminar.png"))); // NOI18N
        btndeletar.setText("DELETAR");
        btndeletar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndeletar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panel_insertLayout.createSequentialGroup()
                            .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtfecha_nacimiento))
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_insertLayout.createSequentialGroup()
                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addComponent(txtzona, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLzona, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeletar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtzona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdelivery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLzona, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(btndeletar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DE CREDITO"));

        jCescredito.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCescredito.setText("TIENE CREDITO");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("SALDO DE CREDITO:");

        txtsaldo_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsaldo_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsaldo_creditoKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("FEC INICIO CREDITO:");

        txtfec_inicio_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtfec_inicio_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_inicio_creditoKeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("DIA LIMITE CREDITO:");

        txtdia_limite_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdia_limite_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdia_limite_creditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtdia_limite_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(83, Short.MAX_VALUE))
                    .addComponent(txtsaldo_credito)
                    .addComponent(txtfec_inicio_credito)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCescredito)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCescredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtsaldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtfec_inicio_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtdia_limite_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("DATO BANCO CLIENTE"));

        cmbbanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbbanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbbanco.setBorder(javax.swing.BorderFactory.createTitledBorder("BANCO"));
        cmbbanco.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbbancoItemStateChanged(evt);
            }
        });

        jCactivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCactivo.setText("ACTIVO");

        txtnrocuenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnrocuenta.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO CUENTA"));
        txtnrocuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnrocuentaKeyPressed(evt);
            }
        });

        txtdocumento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtdocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("DOCUMENTO"));
        txtdocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdocumentoKeyPressed(evt);
            }
        });

        txttitular.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txttitular.setBorder(javax.swing.BorderFactory.createTitledBorder("TITULAR"));
        txttitular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttitularKeyPressed(evt);
            }
        });

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttitular))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(txtdocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtnrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addComponent(cmbbanco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCactivo)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnnuevo_db)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnguardar_db)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneditar_db)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndeletar_db)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCactivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_db)
                    .addComponent(btnguardar_db)
                    .addComponent(btneditar_db)
                    .addComponent(btndeletar_db))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(226, 226, 226))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_cliente.addTab("CREAR CLIENTE", jPanel3);

        panel_tabla_cliente.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblcliente_credito_resumen.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcliente_credito_resumen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcliente_credito_resumenMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblcliente_credito_resumen);

        javax.swing.GroupLayout panel_tabla_clienteLayout = new javax.swing.GroupLayout(panel_tabla_cliente);
        panel_tabla_cliente.setLayout(panel_tabla_clienteLayout);
        panel_tabla_clienteLayout.setHorizontalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        panel_tabla_clienteLayout.setVerticalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        );

        btnpagar_credito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpagar_credito.setText("PAGAR CREDITO");
        btnpagar_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpagar_creditoActionPerformed(evt);
            }
        });

        btnactualizar_tabla.setText("ACTUALIZAR TABLA");
        btnactualizar_tabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_tablaActionPerformed(evt);
            }
        });

        btncrearcredito_inicio.setText("CREAR CREDITO DE INICIO");
        btncrearcredito_inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearcredito_inicioActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("SALDO CREDITO TOTAL:");

        jFsaldo_credito_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFsaldo_credito_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsaldo_credito_total.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnpagar_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncrearcredito_inicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFsaldo_credito_total)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnpagar_credito, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncrearcredito_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jFsaldo_credito_total, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1072, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 543, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 36, Short.MAX_VALUE)))
        );

        jTab_cliente.addTab("TABLA CREDITO", jPanel1);

        panel_tabla_cliente1.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente1.setBorder(javax.swing.BorderFactory.createTitledBorder("GRUPO CREDITO FINANZA"));

        tblgrupo_credito_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgrupo_credito_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblgrupo_credito_clienteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgrupo_credito_clienteMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblgrupo_credito_cliente);

        javax.swing.GroupLayout panel_tabla_cliente1Layout = new javax.swing.GroupLayout(panel_tabla_cliente1);
        panel_tabla_cliente1.setLayout(panel_tabla_cliente1Layout);
        panel_tabla_cliente1Layout.setHorizontalGroup(
            panel_tabla_cliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        panel_tabla_cliente1Layout.setVerticalGroup(
            panel_tabla_cliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_tabla_cliente2.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla_cliente2.setBorder(javax.swing.BorderFactory.createTitledBorder("CREDITO FINANZA"));

        tblcredito_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblcredito_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblcredito_clienteMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblcredito_cliente);

        javax.swing.GroupLayout panel_tabla_cliente2Layout = new javax.swing.GroupLayout(panel_tabla_cliente2);
        panel_tabla_cliente2.setLayout(panel_tabla_cliente2Layout);
        panel_tabla_cliente2Layout.setHorizontalGroup(
            panel_tabla_cliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );
        panel_tabla_cliente2Layout.setVerticalGroup(
            panel_tabla_cliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_cliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1072, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 543, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 36, Short.MAX_VALUE)))
        );

        jTab_cliente.addTab("GRUPO CREDITO", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTab_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_cliente)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_cliente();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        cdao.ancho_tabla_cliente(tblpro_cliente);
        DAOcli.ancho_tabla_cliente2(tblcliente_credito_resumen);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_cliente();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_cliente();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtruc, txttelefono);
        evejtf.saltar_campo_enter(evt, txtruc, txttelefono);
    }//GEN-LAST:event_txtrucKeyPressed

    private void txttelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter2(evt, txttelefono, txtdireccion);
        evejtf.saltar_campo_enter(evt, txttelefono, txtfecha_nacimiento);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtfecha_nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyReleased
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtfecha_nacimiento);
    }//GEN-LAST:event_txtfecha_nacimientoKeyReleased

    private void txtfecha_nacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyTyped
        // TODO add your handling code here:
//        fo.soloFechaText(evt);
    }//GEN-LAST:event_txtfecha_nacimientoKeyTyped

    private void txtzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtzona, jLzona, ENTzn.getTabla(), ENTzn.getNombretabla(), ENTzn.getNombretabla(), 4);
    }//GEN-LAST:event_txtzonaKeyReleased

    private void jLzonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLzonaMouseReleased
        // TODO add your handling code here:
        cargar_zona_cliente();
    }//GEN-LAST:event_jLzonaMouseReleased

    private void txtzonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtzonaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jLzona);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtzona.setBackground(Color.WHITE);
            txtdireccion.setBackground(Color.YELLOW);
            txtdireccion.grabFocus();
        }
    }//GEN-LAST:event_txtzonaKeyPressed

    private void jLzonaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLzonaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_zona_cliente();
        }
    }//GEN-LAST:event_jLzonaKeyReleased

    private void txtfecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_nacimientoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfecha_nacimiento.setText(evefec.getString_validar_fecha(txtfecha_nacimiento.getText()));
            evejtf.saltar_campo_enter(evt, txtfecha_nacimiento, txtzona);
        }

    }//GEN-LAST:event_txtfecha_nacimientoKeyPressed

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void tblcliente_credito_resumenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcliente_credito_resumenMouseReleased
        // TODO add your handling code here:
//        seleccionar_tabla();
        seleccionar_tabla_cliente();
    }//GEN-LAST:event_tblcliente_credito_resumenMouseReleased

    private void btnpagar_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpagar_creditoActionPerformed
        // TODO add your handling code here:
        if (tblcliente_credito_resumen.getSelectedRow() >= 0) {
            evetbl.abrir_TablaJinternal(new FrmRecibo_pago_cliente());
        }
    }//GEN-LAST:event_btnpagar_creditoActionPerformed

    private void btnactualizar_tablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_tablaActionPerformed
        // TODO add your handling code here:
        actualizar_todo();
    }//GEN-LAST:event_btnactualizar_tablaActionPerformed

    private void tblgrupo_credito_clienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_credito_clienteMousePressed
        // TODO add your handling code here:
        cargar_credito_cliente();
    }//GEN-LAST:event_tblgrupo_credito_clienteMousePressed

    private void tblgrupo_credito_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupo_credito_clienteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblgrupo_credito_clienteMouseReleased

    private void tblcredito_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcredito_clienteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblcredito_clienteMouseReleased

    private void btncrearcredito_inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearcredito_inicioActionPerformed
        // TODO add your handling code here:
        credito_inicio_para_todos();
    }//GEN-LAST:event_btncrearcredito_inicioActionPerformed

    private void txtdia_limite_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdia_limite_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdia_limite_creditoKeyPressed

    private void txtfec_inicio_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_inicio_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfec_inicio_creditoKeyPressed

    private void txtsaldo_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsaldo_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsaldo_creditoKeyPressed

    private void cmbbancoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbbancoItemStateChanged
        // TODO add your handling code here:
        if (hab_carga_banco) {
            fk_idbanco = evecmb.getInt_seleccionar_COMBOBOX(conn, cmbbanco, "idbanco", "nombre", "banco");
        }
    }//GEN-LAST:event_cmbbancoItemStateChanged

    private void txtnrocuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnrocuentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnrocuentaKeyPressed

    private void txtdocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdocumentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdocumentoKeyPressed

    private void txttitularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttitularKeyPressed
        // TODO add your handling code here:
        //        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txttitularKeyPressed

    private void tbldato_bancoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldato_bancoMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_db();
    }//GEN-LAST:event_tbldato_bancoMouseReleased

    private void btnnuevo_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_dbActionPerformed
        // TODO add your handling code here:
        boton_nuevo_db();
    }//GEN-LAST:event_btnnuevo_dbActionPerformed

    private void btnguardar_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_dbActionPerformed
        // TODO add your handling code here:
        boton_guardar_db();
    }//GEN-LAST:event_btnguardar_dbActionPerformed

    private void btneditar_dbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar_dbActionPerformed
        // TODO add your handling code here:
        boton_editar_db();
    }//GEN-LAST:event_btneditar_dbActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_tabla;
    private javax.swing.JButton btncrearcredito_inicio;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btndeletar_db;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneditar_db;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnguardar_db;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_db;
    private javax.swing.JButton btnpagar_credito;
    private javax.swing.JComboBox<String> cmbbanco;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JCheckBox jCactivo;
    private javax.swing.JCheckBox jCescredito;
    private javax.swing.JFormattedTextField jFsaldo_credito_total;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jLzona;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTab_cliente;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JPanel panel_tabla_cliente;
    private javax.swing.JPanel panel_tabla_cliente1;
    private javax.swing.JPanel panel_tabla_cliente2;
    public static javax.swing.JTable tblcliente_credito_resumen;
    public static javax.swing.JTable tblcredito_cliente;
    private javax.swing.JTable tbldato_banco;
    public static javax.swing.JTable tblgrupo_credito_cliente;
    public static javax.swing.JTextField txtdelivery;
    private javax.swing.JTextField txtdia_limite_credito;
    private javax.swing.JTextArea txtdireccion;
    private javax.swing.JTextField txtdocumento;
    private javax.swing.JTextField txtfec_inicio_credito;
    private javax.swing.JTextField txtfecha_inicio;
    private javax.swing.JTextField txtfecha_nacimiento;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnrocuenta;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txtsaldo_credito;
    private javax.swing.JTextField txttelefono;
    private javax.swing.JTextField txttitular;
    public static javax.swing.JTextField txtzona;
    // End of variables declaration//GEN-END:variables
}
