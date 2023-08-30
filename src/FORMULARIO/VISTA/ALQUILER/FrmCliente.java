/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import FORMULARIO.VISTA.ALQUILER.FrmRecibo_pago_cliente;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Color.cla_color_pelete;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
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
    private venta_alquiler ENTva = new venta_alquiler();
    private DAO_venta_alquiler DAOva = new DAO_venta_alquiler();
    private banco ENTb = new banco();
    private BO_banco BOb = new BO_banco();
    private DAO_banco DAOb = new DAO_banco();
    private EvenRender everende = new EvenRender();
    private recibo_pago_cliente ENTrpc=new recibo_pago_cliente();
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private String estado_ANULADO = "ANULADO";
    private String estado_RESERVADO = "RESERVADO";
    private String estado_ALQUILADO = "ALQUILADO";
    private String estado_DEVOLUCION = "DEVOLUCION";
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
        color_formulario();
        actualizar_todo(0);
    }


    void color_formulario() {
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_guardar_cliente() {
        txtfecha_inicio.setText(evefec.getString_formato_fecha_barra());
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
//        if (txtfecha_nacimiento.getText().trim().length() == 0) {
//            txtfecha_nacimiento.setText(evefec.getString_formato_fecha());
//            ENTcli.setC7fecha_cumple(evefec.getString_formato_fecha());
//        }
        return true;
    }

    private String tipo_cliente() {
        String tipo = "cliente";
        return tipo;
    }

    private void cargar_cliente(int idcliente,boolean es_insert) {
        if(es_insert){
            ENTcli.setC1idcliente(idcliente);
            ENTcli.setC2fecha_inicio("now");
            ENTcli.setC13saldo_credito(Double.parseDouble("-" + evejtf.getString_format_nro_entero(txtsaldo_credito)));
        }else{
            ENTcli.setC1idcliente(Integer.parseInt(txtidcliente.getText()));
            ENTcli.setC2fecha_inicio(txtfecha_inicio.getText());
            ENTcli.setC13saldo_credito(evejtf.getDouble_format_nro_entero(txtsaldo_credito));
        }
        
        ENTcli.setC3nombre(txtnombre.getText());
        ENTcli.setC4direccion(txtdireccion.getText());
        ENTcli.setC5telefono(txttelefono.getText());
        ENTcli.setC6ruc(txtruc.getText());
        ENTcli.setC7fecha_cumple("now()");
        ENTcli.setC8tipo(tipo_cliente());
        ENTcli.setC12escredito(jCescredito.isSelected());
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
            int idcliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTcli.getTabla(), ENTcli.getIdtabla(),"where idcliente<9999; "));
            JOptionPane.showMessageDialog(this,"IDCLIENTE:"+idcliente);
            int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTscc.getTb_saldo_credito_cliente(), ENTscc.getId_idsaldo_credito_cliente()));
            int idgrupo_credito_cliente = (eveconn.getInt_ultimoID_mas_uno(conn, ENTgcc.getTb_grupo_credito_cliente(), ENTgcc.getId_idgrupo_credito_cliente()));
            cargar_cliente(idcliente,true);
            cargar_saldo_credito_cliente(idcliente);
            cargar_grupo_credito_cliente(idcliente);
            cargar_credito_cliente(idsaldo_credito_cliente, idgrupo_credito_cliente);
            if (BOcli.getBoolean_insertar_cliente_con_credito_inicio1(ENTcli,false, ENTscc, ENTcc, ENTgcc)) {
                reestableser_cliente();
                DAOgcc.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, idcliente);
                DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
                actualizar_todo(0);
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
            cargar_cliente(0,false);
            BOcli.update_cliente(ENTcli);
            actualizar_todo(0);
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
        jCescredito.setSelected(ENTcli.isC12escredito());
//        evejtf.getString_format_nro_entero(ENTcli.getC13saldo_credito());
        txtsaldo_credito.setText(evejtf.getString_format_nro_decimal(ENTcli.getC13saldo_credito()));
        
        txtfec_inicio_credito.setText(ENTcli.getC14fecha_inicio_credito());
        txtdia_limite_credito.setText(String.valueOf(ENTcli.getC15dia_limite_credito()));
        ENTcli.setC1idcliente_global(fk_idcliente);
        DAOgcc.actualizar_tabla_grupo_credito_cliente_idc(conn, tblgrupo_credito_cliente, fk_idcliente);
        DAOgcc.cargar_grupo_credito_cliente_id(conn, ENTgcc, fk_idcliente);
        DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, ENTgcc.getC1idgrupo_credito_cliente());
        DAOva.actualizar_tabla_venta_alquiler_en_cliente(conn, tblventa_alquiler, fk_idcliente);
        txtzona.setText(ENTcli.getC10zona());
        txtdelivery.setText(ENTcli.getC11delivery());
        btnpagar_credito.setEnabled(false);
        btnimprimir_venta_alquiler_1.setEnabled(false);
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
//        txtfecha_nacimiento.setText(null);
        txtzona.setText(null);
        txtdelivery.setText(null);
        jCescredito.setSelected(false);
        txtsaldo_credito.setText("0");
        txtfec_inicio_credito.setText(evefec.getString_formato_fecha_barra());
        txtdia_limite_credito.setText("0");
        btnpagar_credito.setEnabled(false);
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

    private void sumar_monto_credito_cliente() {
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

    private void actualizar_todo(int tipo) {
        String filtro="";
        if(tipo==1){
            if(txtbuscar_nombre.getText().trim().length()>1){
                String busca=txtbuscar_nombre.getText();
                filtro="where nombre ilike'%"+busca+"%' ";
            }
        }
        if(tipo==2){
            if(txtbuscar_ruc.getText().trim().length()>1){
                String busca=txtbuscar_ruc.getText();
                filtro="where ruc ilike'%"+busca+"%' ";
            }
        }
        if(tipo==3){
            if(txtbuscar_telefono.getText().trim().length()>1){
                String busca=txtbuscar_telefono.getText();
                filtro="where telefono ilike'%"+busca+"%' ";
            }
        }
        if(tipo==4){
            if(txtbuscar_direccion.getText().trim().length()>1){
                String busca=txtbuscar_direccion.getText();
                filtro="where direccion ilike'%"+busca+"%' ";
            }
        }
        String orden="";
        if(jRord_nombre.isSelected()){
            orden="order by nombre asc ";
        }
        if(jRord_saldo.isSelected()){
            orden="order by saldo_credito asc ";
        }
        if(jRord_fecha.isSelected()){
            orden="order by fecha_inicio desc ";
        }
        DAOcli.actualizar_tabla_cliente(conn, tblcliente_credito_resumen,filtro,orden);
        sumar_monto_credito_cliente();
    }

    private void cargar_credito_cliente() {
        if (!evejt.getBoolean_validar_select(tblgrupo_credito_cliente)) {
            int idgrupo_credito_cliente = evejt.getInt_select_id(tblgrupo_credito_cliente);
            DAOcc.actualizar_tabla_credito_cliente_por_grupo(conn, tblcredito_cliente, idgrupo_credito_cliente);
        }
    }


    private void seleccionar_venta_alquiler() {
        int idventa_alquiler = evejt.getInt_select_id(tblventa_alquiler);
        ENTva.setC1idventa_alquiler_global(idventa_alquiler);
        String estado = evejt.getString_select(tblventa_alquiler, 8);
        String cancelado = evejt.getString_select(tblventa_alquiler, 9);
        btnimprimir_venta_alquiler_1.setEnabled(true);
        if (estado.equals(estado_ANULADO)) {
            btnpagar_credito.setEnabled(false);
        }
        if (estado.equals(estado_ALQUILADO) || estado.equals(estado_DEVOLUCION) || estado.equals(estado_EMITIDO)) {
            if (cancelado.equals("NO")) {
                btnpagar_credito.setEnabled(true);
            } else {
                btnpagar_credito.setEnabled(false);
            }
        }
    }

    private void boton_venta_alquiler_imprimir_orden() {
        DAOva.seleccionar_imprimir_venta_alquiler_tabla(conn, tblventa_alquiler);
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
        gru_ord = new javax.swing.ButtonGroup();
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
        btncrearcredito_inicio = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla_cliente = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblcliente_credito_resumen = new javax.swing.JTable();
        txtbuscar_nombre = new javax.swing.JTextField();
        txtbuscar_ruc = new javax.swing.JTextField();
        txtbuscar_telefono = new javax.swing.JTextField();
        txtbuscar_direccion = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jRord_saldo = new javax.swing.JRadioButton();
        jRord_nombre = new javax.swing.JRadioButton();
        jRord_fecha = new javax.swing.JRadioButton();
        btnpagar_credito = new javax.swing.JButton();
        btnactualizar_tabla = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jFsaldo_credito_total = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblventa_alquiler = new javax.swing.JTable();
        btnimprimir_venta_alquiler_1 = new javax.swing.JButton();
        btnactualizar_saldo = new javax.swing.JButton();
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
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DE CREDITO"));

        jCescredito.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCescredito.setText("TIENE CREDITO");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("SALDO DE CREDITO:");

        txtsaldo_credito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsaldo_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        btncrearcredito_inicio.setText("CREAR CREDITO DE INICIO");
        btncrearcredito_inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearcredito_inicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncrearcredito_inicio)
                .addGap(53, 53, 53))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btncrearcredito_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        txtbuscar_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTE:"));
        txtbuscar_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombreKeyReleased(evt);
            }
        });

        txtbuscar_ruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC:"));
        txtbuscar_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_rucKeyReleased(evt);
            }
        });

        txtbuscar_telefono.setBorder(javax.swing.BorderFactory.createTitledBorder("TELEFONO:"));
        txtbuscar_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_telefonoKeyReleased(evt);
            }
        });

        txtbuscar_direccion.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCION:"));
        txtbuscar_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_direccionKeyReleased(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("ORDENAR POR:"));

        gru_ord.add(jRord_saldo);
        jRord_saldo.setText("SALDO");
        jRord_saldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_saldoActionPerformed(evt);
            }
        });

        gru_ord.add(jRord_nombre);
        jRord_nombre.setSelected(true);
        jRord_nombre.setText("NOMBRE");
        jRord_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_nombreActionPerformed(evt);
            }
        });

        gru_ord.add(jRord_fecha);
        jRord_fecha.setText("FECHA ");
        jRord_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_fechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRord_fecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRord_nombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRord_saldo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRord_saldo)
                    .addComponent(jRord_nombre)
                    .addComponent(jRord_fecha)))
        );

        javax.swing.GroupLayout panel_tabla_clienteLayout = new javax.swing.GroupLayout(panel_tabla_cliente);
        panel_tabla_cliente.setLayout(panel_tabla_clienteLayout);
        panel_tabla_clienteLayout.setHorizontalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane4)
        );
        panel_tabla_clienteLayout.setVerticalGroup(
            panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_tabla_clienteLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tabla_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtbuscar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnpagar_credito.setBackground(new java.awt.Color(102, 255, 102));
        btnpagar_credito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpagar_credito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/32_pago.png"))); // NOI18N
        btnpagar_credito.setText("PAGAR CREDITO");
        btnpagar_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpagar_creditoActionPerformed(evt);
            }
        });

        btnactualizar_tabla.setText("ACTUALIZAR");
        btnactualizar_tabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_tablaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("SALDO:");

        jFsaldo_credito_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFsaldo_credito_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsaldo_credito_total.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jPanel8.setBackground(new java.awt.Color(102, 102, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("VENTA ALQUILER"));

        tblventa_alquiler.setModel(new javax.swing.table.DefaultTableModel(
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
        tblventa_alquiler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblventa_alquilerMouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tblventa_alquiler);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        btnimprimir_venta_alquiler_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_venta_alquiler_1.setText("VENTA ALQUILER");
        btnimprimir_venta_alquiler_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_venta_alquiler_1ActionPerformed(evt);
            }
        });

        btnactualizar_saldo.setText("ACTUALIZAR SALDO");
        btnactualizar_saldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizar_saldoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnpagar_credito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_venta_alquiler_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                .addComponent(btnactualizar_saldo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnactualizar_tabla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFsaldo_credito_total, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel_tabla_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel6)
                                    .addComponent(jFsaldo_credito_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnpagar_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(btnimprimir_venta_alquiler_1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnactualizar_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizar_saldo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
            .addGap(0, 590, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
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
                .addComponent(panel_tabla_cliente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGap(0, 590, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
//        evejtf.saltar_campo_enter(evt, txttelefono, txtfecha_nacimiento);
    }//GEN-LAST:event_txttelefonoKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
//        pasarCampoEnter(evt, txtnombre, txtruc);
        evejtf.saltar_campo_enter(evt, txtnombre, txtruc);
    }//GEN-LAST:event_txtnombreKeyPressed

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

    private void txtdireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionKeyPressed

    private void tblcliente_credito_resumenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcliente_credito_resumenMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_cliente();
    }//GEN-LAST:event_tblcliente_credito_resumenMouseReleased

    private void btnpagar_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpagar_creditoActionPerformed
        // TODO add your handling code here:
        ENTrpc.setQuien_llama(1);
            evetbl.abrir_TablaJinternal(new FrmRecibo_pago_cliente());
    }//GEN-LAST:event_btnpagar_creditoActionPerformed

    private void btnactualizar_tablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_tablaActionPerformed
        // TODO add your handling code here:
        actualizar_todo(0);
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

    private void tblventa_alquilerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblventa_alquilerMouseReleased
        // TODO add your handling code here:
        seleccionar_venta_alquiler();
    }//GEN-LAST:event_tblventa_alquilerMouseReleased

    private void btnimprimir_venta_alquiler_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_venta_alquiler_1ActionPerformed
        // TODO add your handling code here:
        boton_venta_alquiler_imprimir_orden();
    }//GEN-LAST:event_btnimprimir_venta_alquiler_1ActionPerformed

    private void txtbuscar_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombreKeyReleased
        // TODO add your handling code here:
        actualizar_todo(1);
    }//GEN-LAST:event_txtbuscar_nombreKeyReleased

    private void txtbuscar_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_rucKeyReleased
        // TODO add your handling code here:
        actualizar_todo(2);
    }//GEN-LAST:event_txtbuscar_rucKeyReleased

    private void txtbuscar_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_telefonoKeyReleased
        // TODO add your handling code here:
        actualizar_todo(3);
    }//GEN-LAST:event_txtbuscar_telefonoKeyReleased

    private void txtbuscar_direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_direccionKeyReleased
        // TODO add your handling code here:
        actualizar_todo(4);
    }//GEN-LAST:event_txtbuscar_direccionKeyReleased

    private void btnactualizar_saldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizar_saldoActionPerformed
        // TODO add your handling code here:
        if(fk_idcliente>0){
            ENTcli.setC1idcliente(fk_idcliente);
            DAOcli.update_cliente_saldo_credito(conn, ENTcli);
            actualizar_todo(0);
        }
    }//GEN-LAST:event_btnactualizar_saldoActionPerformed

    private void jRord_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_nombreActionPerformed
        // TODO add your handling code here:
        actualizar_todo(0);
    }//GEN-LAST:event_jRord_nombreActionPerformed

    private void jRord_saldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_saldoActionPerformed
        // TODO add your handling code here:
        actualizar_todo(0);
    }//GEN-LAST:event_jRord_saldoActionPerformed

    private void jRord_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_fechaActionPerformed
        // TODO add your handling code here:
        actualizar_todo(0);
    }//GEN-LAST:event_jRord_fechaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizar_saldo;
    private javax.swing.JButton btnactualizar_tabla;
    private javax.swing.JButton btncrearcredito_inicio;
    private javax.swing.JButton btndeletar;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnimprimir_venta_alquiler_1;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnpagar_credito;
    private javax.swing.ButtonGroup gru_ord;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JCheckBox jCescredito;
    public static javax.swing.JFormattedTextField jFsaldo_credito_total;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRord_fecha;
    private javax.swing.JRadioButton jRord_nombre;
    private javax.swing.JRadioButton jRord_saldo;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_cliente;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JPanel panel_tabla_cliente;
    private javax.swing.JPanel panel_tabla_cliente1;
    private javax.swing.JPanel panel_tabla_cliente2;
    public static javax.swing.JTable tblcliente_credito_resumen;
    public static javax.swing.JTable tblcredito_cliente;
    public static javax.swing.JTable tblgrupo_credito_cliente;
    public static javax.swing.JTable tblventa_alquiler;
    private javax.swing.JTextField txtbuscar_direccion;
    private javax.swing.JTextField txtbuscar_nombre;
    private javax.swing.JTextField txtbuscar_ruc;
    private javax.swing.JTextField txtbuscar_telefono;
    public static javax.swing.JTextField txtdelivery;
    private javax.swing.JTextField txtdia_limite_credito;
    private javax.swing.JTextArea txtdireccion;
    private javax.swing.JTextField txtfec_inicio_credito;
    private javax.swing.JTextField txtfecha_inicio;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txtsaldo_credito;
    private javax.swing.JTextField txttelefono;
    public static javax.swing.JTextField txtzona;
    // End of variables declaration//GEN-END:variables
}
