/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.ALQUILER;

import FORMULARIO.ENTIDAD.cotizacion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import CONFIGURACION.EvenDatosPc;
import ESTADO.EvenEstado;
import Evento.Color.cla_color_pelete;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import Evento.Utilitario.EvenUtil;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.BO.BO_cliente;
import FORMULARIO.BO.BO_venta;
import FORMULARIO.BO.BO_venta_alquiler;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.FrmFactura;
import FORMULARIO.VISTA.FrmVuelto;
import FORMULARIO.VISTA.JDpago_combinado;
import static FORMULARIO.VISTA.ALQUILER.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.ALQUILER.FrmCliente.txtzona;
import FORMULARIO.VISTA.FrmTipo_evento;
import IMPRESORA_POS.PosImprimir_Venta;
import IMPRESORA_POS.PosImprimir_venta_alquiler;
import IMPRESORA_POS.PosImprimir_venta_mesa;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmVenta_alquiler extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmVenta
     */
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable evejt = new EvenJtable();
    private EvenRender everende = new EvenRender();
    private EvenFecha evefec = new EvenFecha();
    private EvenDatosPc evepc = new EvenDatosPc();
    private EvenUtil eveut = new EvenUtil();
    private EvenEstado eveest = new EvenEstado();
    private EvenConexion eveconn = new EvenConexion();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private PosImprimir_venta_alquiler posv = new PosImprimir_venta_alquiler();
    private cliente ENTcli = new cliente();
    private item_venta_alquiler ENTiva = new item_venta_alquiler();
    private DAO_item_venta_alquiler DAOiva = new DAO_item_venta_alquiler();
    public static venta_alquiler ENTva = new venta_alquiler();
    private BO_venta_alquiler BOva = new BO_venta_alquiler();
    private DAO_venta_alquiler DAOva = new DAO_venta_alquiler();
    private factura ENTfac = new factura();
    private DAO_cliente DAOcli = new DAO_cliente();
    private producto ENTpro = new producto();
    private DAO_producto DAOpro = new DAO_producto();
    private BO_cliente BOcli = new BO_cliente();
    private zona_delivery ENTzn = new zona_delivery();
    private DAO_zona_delivery DAOzn = new DAO_zona_delivery();
    private cotizacion ENTcot = new cotizacion();
    private DAO_cotizacion DAOcot = new DAO_cotizacion();
    private usuario ENTusu = new usuario();
    private DAO_entregador DAOent = new DAO_entregador();
    private DAO_grupo_credito_cliente DAOgcc = new DAO_grupo_credito_cliente();
    private credito_cliente ENTcc = new credito_cliente();
    private grupo_credito_cliente ENTgcc = new grupo_credito_cliente();
    private producto_combo ENTpc = new producto_combo();
    private DAO_producto_combo DAOpc = new DAO_producto_combo();
    private Connection connLocal = ConnPostgres.getConnPosgres();
    private java.util.List<JButton> botones_categoria;
    private java.util.List<JButton> botones_unidad;
    private java.util.List<JButton> botones_marca;
    public static DefaultTableModel model_itemf = new DefaultTableModel();
    private cla_color_pelete clacolor = new cla_color_pelete();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private int cant_boton_cate;
    private int cant_boton_unid;
    private int cant_boton_marca;
    private String fk_idproducto_unidad;
    private String fk_idproducto_categoria;
    private String fk_idproducto_marca;
    private String cantidad_producto = "0";
    private double monto_total;
    private double monto_delivery;
    private String zona_delivery;
    private String tipo_entrega;
    private String entrega_delivery = "#>DELIVERY<#";
    private String entrega_paqueta = "#>PARA_LLEVAR<#";
    private String entrega_funcio = "#>FUNCIONARIO<#";
    private String estado_ANULADO = "ANULADO";
    private String estado_EMITIDO = "EMITIDO";
    private String estado_RESERVADO = "RESERVADO";
    private String estado_ALQUILADO = "ALQUILADO";
    private String estado_DEVOLUCION = "DEVOLUCION";
    private String estado_USO_RESERVA = "USO_RESERVA";
    private String estado_FINALIZAR = "FINALIZADO";
    private int idventa_alquiler_ultimo;
    private int fk_idcliente_local;
    private double monto_alquilado_efectivo = 0;
    private double monto_alquilado_tarjeta = 0;
    private double monto_alquilado_transferencia = 0;
    private double monto_alquilado_credito = 0;
    private double monto_alquilado_reservado = 0;
    private String forma_pago = "SIN FORMA";
    private String tabla_origen = "ALQUILER";
    private double valor_redondeo = 500;
    private int Iidproducto;
    private boolean esCargadoCodBarra;
    private String forma_pago_EFECTIVO = "EFECTIVO";
    private String forma_pago_TARJETA = "TARJETA";
    private String forma_pago_COMBINADO = "COMBINADO";
    private String forma_pago_TRANSFERENCIA = "TRANSFERENCIA";
    private String forma_pago_PAGOPARCIAL = "PAGO-PARCIAL";
    private String forma_pago_CREDITO = "CREDITO";
    private String condicion_CONTADO = "CONTADO";
    private String condicion_CREDITO = "CREDITO";
    private String condicion_PAGOPARCIAL = "PAGO-PARCIAL";
    private boolean habilitar_editar_precio_venta;
    private boolean hab_venta_combinado;
    private boolean hab_carga_entregador;
    private boolean hab_carga_tipo_evento;
    private int fk_identregador;
    private int fk_idtipo_evento;
    private String hora_estandar = "12";
    private String estado_venta_alquiler;
    private String observacion_inicio = "NINGUNA";
    private double monto_descuento;
    private double monto_pagar;
    private int item_orden = 0;
    private String entre_id = "identregador";
    private String entre_colum = "nombre";
    private String entre_tabla = "entregador";
    private String entre_where = "where activar=true";
    private String te_id = "idtipo_evento";
    private String te_colum = "nombre";
    private String te_tabla = "tipo_evento";
    private String te_where = "where activar=true";
    private double monto_sena;
    private String monto_letra;

    private void abrir_formulario() {
        String servidor = "";
        this.setTitle("VENTA ALQUILER--> USUARIO:" + ENTusu.getGlobal_nombre() + servidor);
        evetbl.centrar_formulario_internalframa(this);
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
        botones_marca = new ArrayList<>();
        jList_cliente.setVisible(false);
//        jCimprimir_ticket.setSelected(true);
        DAOcot.cargar_cotizacion(ENTcot, 1);
        cargar_entregador();
        cargar_tipo_evento();
        reestableser_venta();
        cargar_boton_categoria();
        crear_item_producto();
    }

    private void cargar_entregador() {
        evecmb.cargarCombobox(connLocal, cmbentregador, entre_id, entre_colum, entre_tabla, entre_where);
        hab_carga_entregador = true;
    }

    private void cargar_tipo_evento() {
        evecmb.cargarCombobox(connLocal, cmbtipo_evento, te_id, te_colum, te_tabla, te_where);
        hab_carga_tipo_evento = true;
    }

    private void color_formulario(Color colorpanel) {
        panel_tabla_busca_cli.setBackground(colorpanel);
        panel_insertar_pri_item.setBackground(colorpanel);
        panel_insertar_pri_producto.setBackground(colorpanel);
        panel_referencia_categoria.setBackground(colorpanel);
        panel_referencia_unidad.setBackground(colorpanel);
        panel_tabla_venta.setBackground(colorpanel);
        panel_tabla_item.setBackground(colorpanel);
        panel_referencia_venta.setBackground(colorpanel);
        panel_base_1.setBackground(colorpanel);
        panel_estado.setBackground(colorpanel);
    }

    private void crear_item_producto() {
        String dato[] = {"id", "descripcion", "P.Unit", "Cant", "Total", "OPunit", "OPrepos", "OTalquilado", "OTreposicion",
            "fk_idproducto_combo", "orden", "tipo"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 55, 12, 6, 15, 1, 1, 1, 1, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
        evejt.ocultar_columna(tblitem_producto, 5);
        evejt.ocultar_columna(tblitem_producto, 6);
        evejt.ocultar_columna(tblitem_producto, 7);
        evejt.ocultar_columna(tblitem_producto, 8);
        evejt.ocultar_columna(tblitem_producto, 9);
        evejt.ocultar_columna(tblitem_producto, 10);
        evejt.ocultar_columna(tblitem_producto, 11);
//        evejt.alinear_derecha_columna(tblitem_producto, 2);
//        evejt.alinear_derecha_columna(tblitem_producto, 4);
    }

    boolean validar_cargar_item_producto() {
        if (evejt.getBoolean_validar_select(tblproducto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_total, "CARGAR UNA CANTIDAD TOTAL")) {
            return false;
        }
        if (txtcantidad_total.getText().trim().length() > 4) {
            JOptionPane.showMessageDialog(txtcantidad_total, "EXEDE EL LIMITE DE CANTIDAD PERMITIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtcantidad_total.setText(null);
            txtcantidad_total.grabFocus();
            return false;
        }
        return true;
    }

    private void sumar_item_venta() {
        //String dato[] = {"id", "descripcion", "P.Unit", "Cant", "T.alquilado", "OPunit","OPrepos","OTalquilado","OTreposicion"};
        monto_total = evejt.getDouble_sumar_tabla(tblitem_producto, 7);
        monto_alquilado_reservado = evejt.getDouble_sumar_tabla(tblitem_producto, 8);
        monto_descuento = evejtf.getDouble_format_nro_entero(txtmonto_descuento);
        monto_sena = evejtf.getDouble_format_nro_entero(txtmonto_sena);
        monto_alquilado_efectivo = monto_total;
        monto_alquilado_tarjeta = monto_total;
        monto_alquilado_transferencia = monto_total;
        monto_alquilado_credito = monto_total;
        jFmonto_total.setValue(monto_total);
        jFtotal_reposicion.setValue(monto_alquilado_reservado);
        monto_pagar = monto_total - monto_descuento;
        jFmonto_pagar.setValue(monto_pagar);
        String Smonto_pagar = String.valueOf((int) monto_pagar);
        monto_letra = nroletra.Convertir(Smonto_pagar, true);
        txtmonto_letra.setText(monto_letra);
        everende.rendertabla_item_alquiler(tblitem_producto);
    }

    private void copiar_itemventa(int idventa) {
        String titulo = "duplicar_itemventa";
        String sql = "select fk_idproducto,descripcion,precio_venta,cantidad,tipo,grupo "
                + "from item_venta where fk_idventa=" + idventa
                + " order by iditem_venta asc";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String idproducto = rs.getString("fk_idproducto");
                String descripcion = rs.getString("descripcion");
                String precioUni = rs.getString("precio_venta");
                String cantidad = rs.getString("cantidad");
                String grupo = rs.getString("grupo");
                double Dcantidad = Double.parseDouble(cantidad);
                double DprecioUni = Double.parseDouble(precioUni);
                String total = String.valueOf(DprecioUni * Dcantidad);
                String tipo = rs.getString("tipo");
                String dato[] = {idproducto, tipo, descripcion, precioUni, cantidad, total, grupo};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_item_producto() {
        if (validar_cargar_item_producto()) {
            //String dato[] = {"id", "descripcion", "P.Unit", "Cant", "T.alquilado", "OPunit","OPrepos","OTalquilado","OTreposicion"};
            String Sfk_idproducto_combo = "0";
            DAOpro.cargar_producto_por_idproducto(connLocal, ENTpro, Iidproducto);
            String idproducto = String.valueOf(ENTpro.getC1idproducto());
            String descripcion = ENTpro.getC3nombre();
            int Iprecio_mostrar = 0;
            int IOPrepos = 0;
            Iprecio_mostrar = (int) ENTpro.getC24precio_alquiler();
            IOPrepos = (int) ENTpro.getC25precio_reposicion();
            String Scant = txtcantidad_total.getText();
            int Icant = Integer.parseInt(Scant);
            int IOTalquilado = 0;
            int IOTreposicion = 0;
            String OPunit = "0";
            String P_unit = "0";
            String OPrepos = "0";
            String OTreposicion = "0";
            if (habilitar_editar_precio_venta) {
                OPunit = txtprecio_venta.getText();
            } else {
                IOTalquilado = Icant * Iprecio_mostrar;
                OPunit = String.valueOf(Iprecio_mostrar);
                P_unit = evejtf.getString_format_nro_decimal(Iprecio_mostrar);
            }
            IOTreposicion = Icant * IOPrepos;
            OTreposicion = String.valueOf(IOTreposicion);
            OPrepos = String.valueOf(IOPrepos);
            String SOTalquilado = String.valueOf(IOTalquilado);
            String T_alquilado = evejtf.getString_format_nro_decimal(IOTalquilado);
            item_orden++;
            String Sorden = String.valueOf(item_orden);
            String tipo = eveest.getTp_item_alq_pro();
            String dato[] = {idproducto, descripcion, P_unit, Scant, T_alquilado, OPunit, OPrepos, SOTalquilado, OTreposicion, Sfk_idproducto_combo, Sorden, tipo};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_total.setText(null);
            reestableser_item_venta();
            sumar_item_venta();
        }
    }

    private void color_campo_item_venta(Color color) {
        txtprecio_venta.setBackground(color);
        txtsubtotal.setBackground(color);
        txtcantidad_total.setBackground(color);
    }

    private void reestableser_item_venta() {
        txtprecio_venta.setText(null);
        txtsubtotal.setText(null);
        txtbuscar_producto.setText(null);
        txtcod_barra.setText(null);
        txtcantidad_total.setText(null);
        txtfec_retirado_previsto.setText(evefec.getString_formato_fecha_barra());
        txthora_retirado_previsto.setText(hora_estandar);
        txtminuto_retirado_previsto.setText("00");
        txtfec_devolusion_previsto.setText(evefec.getString_formato_fecha_barra());
        txthora_devolusion_previsto.setText(hora_estandar);
        txtminuto_devolusion_previsto.setText("00");
        panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        color_campo_item_venta(Color.white);
        esCargadoCodBarra = false;
        habilitar_editar_precio_venta = false;
        txtcod_barra.grabFocus();
    }

    private void cargar_boton_categoria() {
        String titulo = "cargar_boton_categoria";
        String sql = "SELECT  c.idproducto_categoria, c.nombre,c.orden \n"
                + "From producto_categoria c,producto p \n"
                + "where c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and c.activar=true \n"
                + "and p.activar=true \n"
                + "and p.alquilado=true \n"
                + "group by 1,2,3\n"
                + "order by c.orden desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            panel_referencia_categoria.removeAll();
            botones_categoria.clear();
            int cant = 0;
            while (rs.next()) {
                cant++;
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_categoria");
                crear_boton_categoria(textboton, nameboton);
                if (cant == 1) {
                    fk_idproducto_categoria = nameboton;
                    actualizar_tabla_producto(1);
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void crear_boton_categoria(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        panel_referencia_categoria.add(boton);
        botones_categoria.add(boton);
        cant_boton_cate++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_cate; p++) {
                    botones_categoria.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_categoria = ((JButton) e.getSource()).getName();
                cargar_boton_unidad(fk_idproducto_categoria);
                cargar_boton_marca(fk_idproducto_categoria);
                System.out.println("fk_idproducto_categoria:" + fk_idproducto_categoria);
                actualizar_tabla_producto(1);
            }
        });
        panel_referencia_categoria.updateUI();
    }

    private void cargar_boton_unidad(String idproducto_categoria) {
        String titulo = "cargar_boton_unidad";
        panel_referencia_unidad.removeAll();
        botones_unidad.clear();
        cant_boton_unid = 0;
        String sql = "select u.idproducto_unidad, u.nombre \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_unidad");
                boton_agregar_unidad(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_boton_marca(String idproducto_categoria) {
        String titulo = "cargar_boton_marca";
        panel_referencia_marca.removeAll();
        botones_marca.clear();
        cant_boton_marca = 0;
        String sql = "select u.idproducto_marca, u.nombre \n"
                + "from producto p,producto_categoria c,producto_marca u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_marca=u.idproducto_marca\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_marca");
                boton_agregar_marca(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_agregar_unidad(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_unidad.add(boton);
        botones_unidad.add(boton);
        cant_boton_unid++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_unid; p++) {
                    botones_unidad.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_unidad = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_unidad: " + fk_idproducto_unidad);
                actualizar_tabla_producto(2);
            }
        });
        panel_referencia_unidad.updateUI();
    }

    private void boton_agregar_marca(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_marca.add(boton);
        botones_marca.add(boton);
        cant_boton_marca++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
                for (int p = 0; p < cant_boton_marca; p++) {
                    botones_marca.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_marca = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_marca: " + fk_idproducto_marca);
                actualizar_tabla_producto(4);
            }
        });
        panel_referencia_marca.updateUI();
    }

    private void actualizar_tabla_producto(int tipo) {
        String filtro_categoria = "";
        String filtro_unidad = "";
        String filtro_producto = "";
        String filtro_marca = "";
        if (tipo == 1) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
        }
        if (tipo == 2) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_unidad = " and p.fk_idproducto_unidad=" + fk_idproducto_unidad;
        }
        if (tipo == 3) {
            if (txtbuscar_producto.getText().trim().length() > 0) {
                String por = "%";
                String temp = por + txtbuscar_producto.getText() + por;
                filtro_producto = " and concat(pm.nombre,'-',u.nombre,'-',p.nombre) ilike'" + temp + "' ";
            }
        }
        if (tipo == 4) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_marca = " and p.fk_idproducto_marca=" + fk_idproducto_marca;
        }
        if (tipo == 5) {
            if (txtcod_barra.getText().trim().length() > 0) {
                String temp = txtcod_barra.getText();
                filtro_producto = " and p.cod_barra='" + temp + "' ";
            }
        }
        String precio_mostrar = "error";
        precio_mostrar = "TRIM(to_char(p.precio_alquiler,'999G999G999')) as p_alqui";
        String sql = "select p.idproducto as idp,\n"
                + "(p.nombre) as nombre,\n"
                + "p.stock_fijo as sfijo,"
                + "p.stock," + precio_mostrar + ", \n"
                + "TRIM(to_char(p.precio_reposicion,'999G999G999')) as p_reposi \n"
                + "from producto p,producto_categoria c,producto_unidad u,producto_marca pm \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and c.activar=true \n"
                + "and p.alquilado=true \n"
                + "and p.activar=true \n" + filtro_categoria + filtro_unidad + filtro_producto + filtro_marca
                + " order by p.idproducto  asc;";
        eveconn.Select_cargar_jtable(connLocal, sql, tblproducto);
        ancho_tabla_producto();
    }

    private void pre_cargar_item_venta() {
        String Sprecio_venta = "0";
        int Iprecio_mino = 0;
        Iprecio_mino = (int) ENTpro.getC24precio_alquiler();
        Sprecio_venta = String.valueOf(Iprecio_mino);
        txtbuscar_producto.setText(ENTpro.getC3nombre());
        txtcod_barra.setText(ENTpro.getC2cod_barra());
        if (ENTpro.getC8stock() <= ENTpro.getC9stock_min()) {
            panel_insertar_pri_producto.setBackground(Color.red);
        } else {
            panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        }
        txtcantidad_total.setText(null);
        txtprecio_venta.setText(Sprecio_venta);
        txtsubtotal.setText(Sprecio_venta);
        txtcantidad_total.grabFocus();
    }

    private void buscar_cod_barra_producto() {
        if (DAOpro.getBoolean_cargar_producto_por_codbarra(connLocal, ENTpro, txtcod_barra.getText())) {
            actualizar_tabla_producto(5);
            tblproducto.changeSelection(0, 0, false, false);
            color_campo_item_venta(Color.white);
            Iidproducto = ENTpro.getC1idproducto();
            pre_cargar_item_venta();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionar_producto() {
        color_campo_item_venta(Color.white);
        Iidproducto = evejt.getInt_select_id(tblproducto);
        DAOpro.cargar_producto_por_idproducto(connLocal, ENTpro, Iidproducto);
        pre_cargar_item_venta();
    }

    private void ancho_tabla_producto() {
        int Ancho[] = {5, 50, 9, 9, 12, 15};
        evejt.setAnchoColumnaJtable(tblproducto, Ancho);
    }

    private void limpiar_cliente() {
        tipo_entrega = entrega_paqueta;
        lblidcliente.setText("id:0");
        fk_idcliente_local = 1;
        txtbucarCliente_nombre.setText(null);
        txtbucarCliente_telefono.setText(null);
        txtbucarCliente_ruc.setText(null);
        txtbucarCliente_direccion.setText(null);
        txtdireccion_alquiler.setText(null);
        txtbucarCliente_telefono.grabFocus();

    }

    private void seleccionar_cargar_cliente(int tipo) {
        if (tipo == 1) {
            fk_idcliente_local = eveconn.getInt_Solo_seleccionar_JLista(connLocal, jList_cliente, "cliente", ENTcli.getCliente_concat(), "idcliente");
        }
        if (tipo == 2) {
            fk_idcliente_local = (eveconn.getInt_ultimoID_mas_uno(connLocal, ENTcli.getTabla(), ENTcli.getIdtabla())) - 1;
        }
        if (tipo == 3) {
            fk_idcliente_local = evejt.getInt_select_id(tblbuscar_cliente);
        }
        if (tipo == 4) {
            fk_idcliente_local = ENTva.getC19fk_idcliente();
        }
        tipo_entrega = entrega_paqueta;
        System.out.println("idclientelocal:" + fk_idcliente_local);
        lblidcliente.setText("id:" + fk_idcliente_local);
        DAOcli.cargar_cliente(connLocal, ENTcli, fk_idcliente_local);
        monto_delivery = ENTcli.getC11deliveryDouble();
        zona_delivery = ENTcli.getC10zona();
        txtbucarCliente_nombre.setText(ENTcli.getC3nombre());
        txtbucarCliente_telefono.setText(ENTcli.getC5telefono());
        txtbucarCliente_ruc.setText(ENTcli.getC6ruc());
        txtbucarCliente_direccion.setText(ENTcli.getC4direccion());
        txtdireccion_alquiler.setText(ENTcli.getC4direccion());
        jFsaldo_credito.setValue(ENTcli.getC13saldo_credito());
    }

    private void boton_eliminar_item() {
        if (evejt.getBoolean_validar_select_mensaje(tblitem_producto, "SELECCIONE UN ITEM PARA ELIMINAR")) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE ELIMINAR ESTE ITEM", "ELIMINAR", "ACEPTAR", "CANCELAR")) {
                String tipo = evejt.getString_select(tblitem_producto, 11);
                if (tipo.equals(eveest.getTp_item_alq_pro()) || tipo.equals(eveest.getTp_item_alq_subcombo())) {
                    if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                        sumar_item_venta();
                    }
                }
                if (tipo.equals(eveest.getTp_item_alq_combo())) {
                    if (evejt.getBoolean_Eliminar_Fila_subfila_alquiler(tblitem_producto, model_itemf, 11)) {
                        sumar_item_venta();
                    }
                }
            }
        }
    }

    private boolean validar_guardar_venta() {

        if (fk_idcliente_local == 1) {
            JOptionPane.showMessageDialog(null, "BUSCAR UN CLIENTE");
            boton_buscar_cliente_venta();
            return false;
        }
        if (evejt.getBoolean_validar_cant_cargado(tblitem_producto)) {
            return false;
        } else {

        }
        if (evejtf.getBoo_JTextField_vacio(txtfec_retirado_previsto, "INGRESAR FECHA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txthora_retirado_previsto, "INGRESAR HORA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_retirado_previsto, "INGRESAR MINUTO ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtfec_devolusion_previsto, "INGRESAR FECHA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txthora_devolusion_previsto, "INGRESAR HORA ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_devolusion_previsto, "INGRESAR MINUTO ")) {
            return false;
        }
        String fecha_retirado_previsto = txtfec_retirado_previsto.getText() + " " + txthora_retirado_previsto.getText() + ":" + txtminuto_retirado_previsto.getText() + ":00.00";
        String fecha_devolusion_previsto = txtfec_devolusion_previsto.getText() + " " + txthora_devolusion_previsto.getText() + ":" + txtminuto_devolusion_previsto.getText() + ":00.00";
        if (evefec.getTimestamp_fecha_cargado(fecha_retirado_previsto).equals(evefec.getTimestamp_fecha_cargado(fecha_devolusion_previsto))) {
            JOptionPane.showMessageDialog(jPanel_fecha, "LA FECHA DE RETIRADO Y DEVOLUSION NO PUEDE SER IGUAL ");
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtobservacion, "INGRESAR UNA OBSERVACION ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdireccion_alquiler, "INGRESAR UNA DIRECCION DONDE ENTREGAR ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_descuento, "INGRESAR UN MONTO DE DESCUENTO ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_sena, "INGRESAR UN MONTO DE SEÑA ")) {
            return false;
        }
        if (cmbentregador.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(cmbentregador, "SELECCIONAR UN ENTREGADOR");
            return false;
        } else {
            fk_identregador = evecmb.getInt_seleccionar_COMBOBOX(connLocal, cmbentregador, entre_id, entre_colum, entre_tabla);
        }
        if (cmbtipo_evento.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(cmbtipo_evento, "SELECCIONAR UN TIPO EVENTO");
            return false;
        } else {
            fk_idtipo_evento = evecmb.getInt_seleccionar_COMBOBOX(connLocal, cmbtipo_evento, te_id, te_colum, te_tabla);
        }
        return true;
    }

    private void boton_cancelar() {
        if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE CANCELAR ESTE ALQUILER", "CANCELAR", "ACEPTAR", "CANCELAR")) {
            reestableser_venta();
        }
    }

    private void reestableser_venta() {
        color_formulario(clacolor.getColor_insertar_primario());
        idventa_alquiler_ultimo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ENTva.getTb_venta_alquiler(), ENTva.getId_idventa_alquiler()));
        DAOva.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
        txtidventa.setText(String.valueOf(idventa_alquiler_ultimo));
        txtbuscar_fecha.setText(evefec.getString_formato_fecha_barra());
        txtfec_vence_credito.setText(evefec.getString_formato_fecha_barra());
        jCestado_emitido.setSelected(true);
        jCestado_finalizado.setSelected(true);
        jCestado_alquilado.setSelected(true);
        jCestado_devolucion.setSelected(true);
        jCestado_usoreserva.setSelected(true);
        jCestado_anulado.setSelected(false);
        actualizar_venta(3);
        actualizar_tabla_producto(1);
        actualizar_producto_combo();
        tipo_entrega = entrega_paqueta;
        estado_venta_alquiler = estado_EMITIDO;
        item_orden = 0;
        monto_total = 0;
        monto_delivery = 0;
        monto_pagar = 0;
        txtcantidad_total.setText(null);
        txtobservacion.setText(observacion_inicio);
        btnconfirmar_venta_efectivo.setBackground(Color.white);
        jFmonto_total.setValue(monto_total);
        jFtotal_reposicion.setValue(monto_total);
        jFsaldo_credito.setValue(monto_total);
        jFmonto_pagar.setValue(monto_total);
        txtmonto_descuento.setText("0");
        txtmonto_sena.setText("0");
        txtmonto_letra.setText("cero");
        select_condicion();
        evejt.limpiar_tabla_datos(model_itemf);
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);
        cmbentregador.setSelectedIndex(1);
        cmbtipo_evento.setSelectedIndex(0);
        limpiar_cliente();
        reestableser_item_venta();
    }

    private void boton_factura() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            ENTfac.setFactura_cargada(true);
            int idventa = (evejt.getInt_select_id(tblventa));
            evetbl.abrir_TablaJinternal(new FrmFactura());
        }
    }

    private void actualizar_producto_combo() {
        DAOpc.actualizar_tabla_producto_combo_alquiler(connLocal, tblproducto_combo, "");
    }

    private void actualizar_venta(int tipo) {
        String filtro = "";
        String campo_filtro = "";
        String filtro_estado = auxvent.filtro_estado_alquiler(jCestado_emitido, jCestado_finalizado, jCestado_usoreserva, jCestado_alquilado, jCestado_devolucion, jCestado_anulado);
        campo_filtro = "(c.idcliente||'-'||c.nombre) as cliente";
        if (tipo == 1) {
            filtro = filtro_estado;
        }
        if (tipo == 2) {
            if (txtbuscar_idventa.getText().trim().length() > 0) {
                filtro = " and v.idventa_alquiler=" + txtbuscar_idventa.getText() + " ";
            }
        }
        if (tipo == 3) {
            if (txtbuscar_fecha.getText().trim().length() > 0) {
                String fec_busca = evefec.getString_cambiar_formato(txtbuscar_fecha.getText());
                filtro = " and date(v.fecha_retirado_real)='" + fec_busca + "' " + filtro_estado;
            }
        }
        DAOva.actualizar_tabla_venta_alquiler(connLocal, tblventa, filtro);
        everende.rendertabla_estado_alquilado(tblventa, 11);
        lblcantidad_filtro.setText("CANT FILTRO: ( " + tblventa.getRowCount() + " )");
    }

    private String gelString_condicion() {
        String condicion = "error";
        if (jRcond_credito.isSelected()) {
            condicion = condicion_CREDITO;
        }
        return condicion;
    }

    private void select_condicion() {
        if (jRcond_credito.isSelected()) {
//            btnconfirmar_venta_efectivo.setText(forma_pago_CREDITO);
            lblvence_credito.setEnabled(true);
            txtfec_vence_credito.setEnabled(true);
        }
    }

    private void cargar_dato_alquiler() {
        String fecha_retirado_previsto = txtfec_retirado_previsto.getText() + " " + txthora_retirado_previsto.getText() + ":" + txtminuto_retirado_previsto.getText() + ":00.00";
        String fecha_devolusion_previsto = txtfec_devolusion_previsto.getText() + " " + txthora_devolusion_previsto.getText() + ":" + txtminuto_devolusion_previsto.getText() + ":30.00";
        monto_delivery = 0;
        ENTva.setC2fecha_creado("now()");
        ENTva.setC3fecha_retirado_previsto(fecha_retirado_previsto);
        ENTva.setC4fecha_retirado_real(fecha_retirado_previsto);
        ENTva.setC5fecha_devolusion_previsto(fecha_devolusion_previsto);
        ENTva.setC6fecha_devolusion_real(fecha_devolusion_previsto);
        ENTva.setC7monto_total(monto_total);
        ENTva.setC8monto_alquilado_efectivo(monto_alquilado_efectivo);
        ENTva.setC9monto_alquilado_tarjeta(monto_alquilado_tarjeta);
        ENTva.setC10monto_alquilado_transferencia(monto_alquilado_transferencia);
        ENTva.setC11monto_delivery(monto_delivery);
        ENTva.setC12forma_pago(forma_pago);
        ENTva.setC13condicion(gelString_condicion());
        ENTva.setC14alquiler_retirado(false);
        ENTva.setC15alquiler_devolusion(false);
        ENTva.setC16direccion_alquiler(txtdireccion_alquiler.getText());
        ENTva.setC17observacion(txtobservacion.getText());
        ENTva.setC18estado(estado_venta_alquiler);
        ENTva.setC19fk_idcliente(fk_idcliente_local);
        ENTva.setC20fk_identregador(fk_identregador);
        ENTva.setC21monto_alquilado_credito(monto_alquilado_credito);
        ENTva.setC28monto_alquilado_reservado(monto_alquilado_reservado);
        ENTva.setC29monto_descuento(monto_descuento);
        ENTva.setC30monto_sena(monto_sena);
        ENTva.setC31monto_letra(monto_letra);
        ENTva.setC32fk_idtipo_evento(fk_idtipo_evento);
        ENTva.setC33monto_pagado(0);
    }

    private String getDescripcion_item_venta() {
        String suma_descripcion = "";
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            suma_descripcion = suma_descripcion + descripcion + ", ";
        }
        return suma_descripcion;
    }

    private void cargar_credito_cliente() {
        DAOgcc.cargar_grupo_credito_cliente_id(connLocal, ENTgcc, fk_idcliente_local);
        ENTcc.setC3descripcion(getDescripcion_item_venta());
        ENTcc.setC4estado(estado_venta_alquiler);
        ENTcc.setC5monto_contado(0);
        ENTcc.setC6monto_credito(monto_pagar);
        ENTcc.setC7tabla_origen(tabla_origen);
        ENTcc.setC8fk_idgrupo_credito_cliente(ENTgcc.getC1idgrupo_credito_cliente());
        ENTcc.setC9fk_idsaldo_credito_cliente(0);
        ENTcc.setC10fk_idrecibo_pago_cliente(0);
        ENTcc.setC11fk_idventa_alquiler(idventa_alquiler_ultimo);
        ENTcc.setC12vence(true);
        ENTcc.setC13fecha_vence(txtfec_vence_credito.getText() + " 12:00:00.00");
        ENTcli.setC1idcliente(fk_idcliente_local);
    }

    ///###################CLIENTE#####################
    private void limpiar_buscardor_cliente() {
        jCfuncionario.setSelected(false);
        txtbuscador_cliente_nombre.setText(null);
        txtbuscador_cliente_telefono.setText(null);
        txtbuscador_cliente_ruc.setText(null);
        txtbuscador_cliente_telefono.grabFocus();
        DAOcli.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }

    private void boton_buscar_cliente_venta() {
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
        limpiar_buscardor_cliente();
    }

    private void boton_venta_efectivo() {
        sumar_item_venta();
        monto_alquilado_tarjeta = 0;
        monto_alquilado_transferencia = 0;
        if (jRcond_credito.isSelected()) {
            monto_alquilado_efectivo = 0;
            forma_pago = forma_pago_CREDITO;
        }
//        hab_venta_combinado = false;
        boton_guardar_venta_alquiler();
    }

    private void calcular_subtotal_itemventa() {
        if ((txtprecio_venta.getText().trim().length() > 0) && (txtcantidad_total.getText().trim().length() > 0)) {
            int precio_venta = Integer.parseInt(txtprecio_venta.getText());
            int Icant_cobrado = 0;
            if (txtcantidad_total.getText().trim().length() == 0) {
                Icant_cobrado = 1;
            } else {
                Icant_cobrado = Integer.parseInt(txtcantidad_total.getText());
            }
            int subtotal = precio_venta * Icant_cobrado;
            txtsubtotal.setText(String.valueOf(subtotal));
        }
    }

    private boolean getBoolean_validar_hora(JTextField txttexto) {
        if (txttexto.getText().trim().length() > 0) {
            int hora = Integer.parseInt(txttexto.getText());
            if (hora >= 0 && hora <= 23) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "FORMATO DE HORA INCORRECTO");
                txttexto.setText(hora_estandar);
                txttexto.requestFocus();
                return false;
            }
        } else {
            txttexto.setText(hora_estandar);
            txttexto.requestFocus();
            return false;
        }
    }

    private boolean getBoolean_validar_minuto(JTextField txttexto) {
        if (txttexto.getText().trim().length() > 0) {
            int minuto = Integer.parseInt(txttexto.getText());
            if (minuto >= 0 && minuto <= 59) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "FORMATO DE MINUTO INCORRECTO");
                txttexto.setText("00");
                txttexto.requestFocus();
                return false;
            }
        } else {
            txttexto.setText("00");
            txttexto.requestFocus();
            return false;
        }
    }

    private void boton_guardar_venta_alquiler() {
        if (validar_guardar_venta()) {
            cargar_dato_alquiler();
            cargar_credito_cliente();
            if (BOva.getBoolean_insertar_venta_alquiler1(ENTva, ENTcc, ENTcli, tblitem_producto)) {
                int idventa_alquiler = idventa_alquiler_ultimo;
                reestableser_venta();
                limpiar_buscardor_cliente();
                DAOva.seleccionar_imprimir_venta_alquiler(connLocal, idventa_alquiler);
                actualizar_venta(1);
            }
        }
    }

    private void boton_anular_venta_alquiler() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            int fk_idcliente = evejt.getInt_select(tblventa, 12);
            ENTva.setC18estado(estado_ANULADO);
            ENTva.setC1idventa_alquiler(idventa_alquiler);
            ENTcc.setC4estado(estado_ANULADO);
            ENTcc.setC11fk_idventa_alquiler(idventa_alquiler);
            ENTcli.setC1idcliente(fk_idcliente);
            if (BOva.getBoolean_update_venta_alquiler_anular(ENTva, ENTcc, ENTcli)) {
                DAOva.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
            }
        }
    }

    private void boton_venta_alquiler_alquilado() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            ENTva.setC18estado(estado_ALQUILADO);
            ENTva.setC1idventa_alquiler(idventa_alquiler);
            String lista_producto = DAOiva.getString_cargar_item_venta_alquiler_cantidad_total(connLocal, idventa_alquiler);
            if (BOva.getBoolean_update_venta_alquiler_alquilado(lista_producto, ENTva)) {
                DAOva.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
            }
        }
    }

    private void boton_venta_alquiler_devolucion() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa_alquiler = evejt.getInt_select_id(tblventa);
            String fecha_retirado_previsto = evejt.getString_select(tblventa, 1) + ":00.00";
            String fecha_devolusion_previsto = evefec.getString_formato_fecha_hora_zona();
            if (evefec.getTimestamp_fecha_cargado(fecha_retirado_previsto).equals(evefec.getTimestamp_fecha_cargado(fecha_devolusion_previsto))) {
                JOptionPane.showMessageDialog(jPanel_fecha, "LA FECHA DE ALQUILADO Y DEVOLUSION NO PUEDE SER IGUAL ", "ERROR DE FECHA", JOptionPane.ERROR_MESSAGE);
            } else {
                ENTva.setC18estado(estado_DEVOLUCION);
                ENTva.setC1idventa_alquiler(idventa_alquiler);
                String lista_producto = DAOiva.getString_cargar_item_venta_alquiler_cantidad_total(connLocal, idventa_alquiler);
                if (BOva.getBoolean_update_venta_alquiler_Devolucion(lista_producto, ENTva)) {
                    DAOva.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
                }
            }
        }
    }

    private void boton_venta_alquiler_imprimir_ticket() {
        DAOva.seleccionar_imprimir_venta_alquiler_tabla(connLocal, tblventa);
    }

    private void seleccionar_venta_alquiler() {
        if (!evejt.getBoolean_validar_select(tblventa)) {
            int idventa = evejt.getInt_select_id(tblventa);
            String estado = evejt.getString_select(tblventa, 10);
            if (estado.equals(estado_EMITIDO)) {
                btnestado_anulado.setEnabled(true);
                btnestado_alquilado.setEnabled(true);
                btnestado_devolucion.setEnabled(false);
            }
            if (estado.equals(estado_ANULADO)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
            }
            if (estado.equals(estado_ALQUILADO)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(true);
            }
            if (estado.equals(estado_USO_RESERVA)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
            }
            if (estado.equals(estado_FINALIZAR)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
            }
            if (estado.equals(estado_DEVOLUCION)) {
                btnestado_anulado.setEnabled(false);
                btnestado_alquilado.setEnabled(false);
                btnestado_devolucion.setEnabled(false);
            }
            DAOiva.actualizar_tabla_item_venta_alquiler_cant_reser(connLocal, tblitem_venta_filtro, idventa);
        }

    }

    boolean validar_cargar_item_producto_combo() {
        if (evejt.getBoolean_validar_select(tblproducto_combo)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_total_combo, "CARGAR UNA CANTIDAD TOTAL")) {
            return false;
        }

        return true;
    }

    private void cargar_producto_combo() {
        if (validar_cargar_item_producto_combo()) {
            String descrip = "#=> ";
            int fk_idproducto_combo = evejt.getInt_select_id(tblproducto_combo);
            String Sfk_idproducto_combo = String.valueOf(fk_idproducto_combo);
            DAOpc.cargar_producto_combo(connLocal, ENTpc, fk_idproducto_combo);
            String idproducto = "0";
            String descripcion = descrip + ENTpc.getC2nombre();
            int Iprecio_mostrar = 0;
            int IOPrepos = 0;
            Iprecio_mostrar = (int) ENTpc.getC4precio_alquiler();
            IOPrepos = (int) ENTpc.getC5precio_reposicion();
            String Scant = txtcantidad_total_combo.getText();
            int Icant = Integer.parseInt(Scant);
            int IOTalquilado = 0;
            int IOTreposicion = 0;
            String OPunit = "0";
            String P_unit = "0";
            String OPrepos = "0";
            String OTreposicion = "0";
            IOTalquilado = Icant * Iprecio_mostrar;
            OPunit = String.valueOf(Iprecio_mostrar);
            P_unit = evejtf.getString_format_nro_decimal(Iprecio_mostrar);
            IOTreposicion = Icant * IOPrepos;
            OTreposicion = String.valueOf(IOTreposicion);
            OPrepos = String.valueOf(IOPrepos);
            String SOTalquilado = String.valueOf(IOTalquilado);
            String T_alquilado = evejtf.getString_format_nro_decimal(IOTalquilado);
            item_orden++;
            String Sorden = String.valueOf(item_orden);
            String tipo = eveest.getTp_item_alq_combo();
            String dato[] = {idproducto, descripcion, P_unit, Scant, T_alquilado, OPunit, OPrepos, SOTalquilado, OTreposicion, Sfk_idproducto_combo, Sorden, tipo};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_total_combo.setText(null);
            cargar_item_producto_combo(fk_idproducto_combo, Icant);
            sumar_item_venta();
        }
    }

    private void cargar_item_producto_combo(int fk_idproducto_combo, int cantidad) {
        String titulo = "cargar_item_producto_combo";
        String sql = "select ipc.fk_idproducto as idproducto,\n"
                + "ipc.descripcion ,(ipc.cantidad*" + cantidad + ") as cant\n"
                + "from item_producto_combo ipc \n"
                + "where ipc.fk_idproducto_combo=" + fk_idproducto_combo
                + " order by 2 desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            String Sfk_idproducto_combo = String.valueOf(fk_idproducto_combo);
            String precio_cero = "0";
            while (rs.next()) {
                String idproducto = rs.getString("idproducto");
                String descripcion = rs.getString("descripcion");
                String Scant = rs.getString("cant");
                item_orden++;
                String Sorden = String.valueOf(item_orden);
                String tipo = eveest.getTp_item_alq_subcombo();
                String dato[] = {idproducto, descripcion, precio_cero, Scant, precio_cero, precio_cero, precio_cero, precio_cero, precio_cero, Sfk_idproducto_combo, Sorden, tipo};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void seleccionar_producto_combo() {
        txtcantidad_total_combo.grabFocus();
    }

    //######################## MESA ##########################
    public FrmVenta_alquiler() {
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

        gru_campo = new javax.swing.ButtonGroup();
        gru_tipocli = new javax.swing.ButtonGroup();
        gru_condi = new javax.swing.ButtonGroup();
        jTabbedPane_VENTA = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        btnagregar_cantidad = new javax.swing.JButton();
        txtcod_barra = new javax.swing.JTextField();
        txtsubtotal = new javax.swing.JTextField();
        txtprecio_venta = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txtcantidad_total = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblproducto_combo = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtcantidad_total_combo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtobservacion = new javax.swing.JTextField();
        panel_insertar_pri_item = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jList_cliente = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        btneliminar_item = new javax.swing.JButton();
        jFmonto_total = new javax.swing.JFormattedTextField();
        btnconfirmar_venta_efectivo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtbucarCliente_ruc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtbucarCliente_nombre = new javax.swing.JTextField();
        txtbucarCliente_direccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucarCliente_telefono = new javax.swing.JTextField();
        btnlimpiar_cliente = new javax.swing.JButton();
        btnnuevo_cliente = new javax.swing.JButton();
        btnbuscar_cliente = new javax.swing.JButton();
        lblidcliente = new javax.swing.JLabel();
        jFtotal_reposicion = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFsaldo_credito = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        txtdireccion_alquiler = new javax.swing.JTextField();
        txtmonto_descuento = new javax.swing.JTextField();
        jFmonto_pagar = new javax.swing.JFormattedTextField();
        txtmonto_sena = new javax.swing.JTextField();
        panel_referencia_marca = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtidventa = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        panel_referencia_categoria = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jRcond_credito = new javax.swing.JRadioButton();
        lblvence_credito = new javax.swing.JLabel();
        txtfec_vence_credito = new javax.swing.JTextField();
        jPanel_fecha = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtfec_devolusion_previsto = new javax.swing.JTextField();
        txtfec_retirado_previsto = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txthora_devolusion_previsto = new javax.swing.JTextField();
        txthora_retirado_previsto = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtminuto_devolusion_previsto = new javax.swing.JTextField();
        txtminuto_retirado_previsto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        cmbentregador = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbtipo_evento = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtmonto_letra = new javax.swing.JTextField();
        btnnuevo_evento = new javax.swing.JButton();
        panel_referencia_venta = new javax.swing.JPanel();
        panel_tabla_venta = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnestado_anulado = new javax.swing.JButton();
        btnfacturar = new javax.swing.JButton();
        btnimprimirNota = new javax.swing.JButton();
        panel_tabla_item = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_venta_filtro = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtbuscar_idventa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbuscar_fecha = new javax.swing.JTextField();
        lblcantidad_filtro = new javax.swing.JLabel();
        btnestado_alquilado = new javax.swing.JButton();
        btnestado_devolucion = new javax.swing.JButton();
        panel_estado = new javax.swing.JPanel();
        jCestado_emitido = new javax.swing.JCheckBox();
        jCestado_finalizado = new javax.swing.JCheckBox();
        jCestado_anulado = new javax.swing.JCheckBox();
        jCestado_devolucion = new javax.swing.JCheckBox();
        jCestado_usoreserva = new javax.swing.JCheckBox();
        jCestado_alquilado = new javax.swing.JCheckBox();
        btnalquiler_todos = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        panel_tabla_busca_cli = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblbuscar_cliente = new javax.swing.JTable();
        txtbuscador_cliente_nombre = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtbuscador_cliente_telefono = new javax.swing.JTextField();
        txtbuscador_cliente_ruc = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jCfuncionario = new javax.swing.JCheckBox();
        btnnuevoCliente = new javax.swing.JButton();
        btnseleccionarCerrar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        panel_referencia_unidad.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_unidad.setLayout(new java.awt.GridLayout(1, 0));

        panel_insertar_pri_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Producto"));
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        tblproducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblproducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto.setRowHeight(20);
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        btnagregar_cantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/flecha_derecha.png"))); // NOI18N
        btnagregar_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_cantidadActionPerformed(evt);
            }
        });

        txtcod_barra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtcod_barra.setBorder(javax.swing.BorderFactory.createTitledBorder("CodBarra"));
        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyReleased(evt);
            }
        });

        txtsubtotal.setEditable(false);
        txtsubtotal.setBackground(new java.awt.Color(204, 204, 255));
        txtsubtotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtsubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsubtotal.setBorder(javax.swing.BorderFactory.createTitledBorder("Subtotal"));

        txtprecio_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprecio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("Precio"));
        txtprecio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyTyped(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD"));

        txtcantidad_total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidad_totalActionPerformed(evt);
            }
        });
        txtcantidad_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_totalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_totalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_totalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtcantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        tblproducto_combo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblproducto_combo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto_combo.setRowHeight(25);
        tblproducto_combo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproducto_comboMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblproducto_combo);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD"));

        txtcantidad_total_combo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_total_combo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_total_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidad_total_comboActionPerformed(evt);
            }
        });
        txtcantidad_total_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_total_comboKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_total_comboKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_total_comboKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtcantidad_total_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcantidad_total_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_producto_ingrediente.addTab("COMBO", jPanel3);

        jLabel2.setText("OBS:");

        txtobservacion.setText("ninguna");

        panel_insertar_pri_item.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_item.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_cliente.setBackground(new java.awt.Color(204, 204, 255));
        jList_cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_cliente.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_clienteMouseReleased(evt);
            }
        });
        jList_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_clienteKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jList_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 430, 140));

        tblitem_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblitem_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblitem_producto.setRowHeight(20);
        tblitem_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblitem_productoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblitem_producto);

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 470, 250));

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/16_eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jFmonto_total.setEditable(false);
        jFmonto_total.setBackground(new java.awt.Color(204, 204, 255));
        jFmonto_total.setBorder(javax.swing.BorderFactory.createTitledBorder("Total Alquiler"));
        jFmonto_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_total.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btnconfirmar_venta_efectivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnconfirmar_venta_efectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/32_guardar.png"))); // NOI18N
        btnconfirmar_venta_efectivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnconfirmar_venta_efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconfirmar_venta_efectivoActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtbucarCliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_rucKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("RUC:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("DIREC:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CLI:");

        txtbucarCliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_nombreKeyReleased(evt);
            }
        });

        txtbucarCliente_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_direccionKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("TEL.:");

        txtbucarCliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarCliente_telefonoKeyReleased(evt);
            }
        });

        btnlimpiar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/escoba.png"))); // NOI18N
        btnlimpiar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiar_clienteActionPerformed(evt);
            }
        });

        btnnuevo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_clienteActionPerformed(evt);
            }
        });

        btnbuscar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_clienteActionPerformed(evt);
            }
        });

        lblidcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblidcliente.setText("id");

        jFtotal_reposicion.setEditable(false);
        jFtotal_reposicion.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_reposicion.setBorder(javax.swing.BorderFactory.createTitledBorder("Tota Reposicion"));
        jFtotal_reposicion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_reposicion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_reposicion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("SALDO:");

        jFsaldo_credito.setEditable(false);
        jFsaldo_credito.setBackground(new java.awt.Color(204, 204, 255));
        jFsaldo_credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFsaldo_credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFsaldo_credito.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("DIREC ENTREGA:");

        txtdireccion_alquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdireccion_alquilerKeyReleased(evt);
            }
        });

        txtmonto_descuento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtmonto_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_descuento.setBorder(javax.swing.BorderFactory.createTitledBorder("Descuento"));
        txtmonto_descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_descuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_descuentoKeyTyped(evt);
            }
        });

        jFmonto_pagar.setEditable(false);
        jFmonto_pagar.setBackground(new java.awt.Color(204, 204, 255));
        jFmonto_pagar.setBorder(javax.swing.BorderFactory.createTitledBorder("Total Pagar"));
        jFmonto_pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_pagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtmonto_sena.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtmonto_sena.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_sena.setBorder(javax.swing.BorderFactory.createTitledBorder("Seña Adelanto:"));
        txtmonto_sena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_senaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_senaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_itemLayout = new javax.swing.GroupLayout(panel_insertar_pri_item);
        panel_insertar_pri_item.setLayout(panel_insertar_pri_itemLayout);
        panel_insertar_pri_itemLayout.setHorizontalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addComponent(jLayeredPane1)
                .addContainerGap())
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFmonto_pagar))
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(btnnuevo_cliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_cliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnlimpiar_cliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFsaldo_credito, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                .addComponent(jFtotal_reposicion, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtmonto_sena, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnconfirmar_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertar_pri_itemLayout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtdireccion_alquiler))
                                .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                            .addComponent(txtbucarCliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtbucarCliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .addComponent(txtbucarCliente_direccion)))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_insertar_pri_itemLayout.setVerticalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtbucarCliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtbucarCliente_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtbucarCliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtbucarCliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(lblidcliente))
                .addGap(5, 5, 5)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel24)
                            .addComponent(txtdireccion_alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlimpiar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFsaldo_credito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btneliminar_item))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFmonto_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jFtotal_reposicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtmonto_sena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnconfirmar_venta_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_referencia_marca.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_marca.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("ID:");

        txtidventa.setBackground(new java.awt.Color(0, 0, 255));
        txtidventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidventa.setForeground(new java.awt.Color(255, 255, 0));
        txtidventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane3.setViewportView(panel_referencia_categoria);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CONDICION"));

        gru_condi.add(jRcond_credito);
        jRcond_credito.setSelected(true);
        jRcond_credito.setText("CREDITO");
        jRcond_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRcond_creditoActionPerformed(evt);
            }
        });

        lblvence_credito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblvence_credito.setText("VENCE:");

        txtfec_vence_credito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfec_vence_credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_vence_creditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRcond_credito)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblvence_credito)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfec_vence_credito)))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jRcond_credito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblvence_credito)
                    .addComponent(txtfec_vence_credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_fecha.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA"));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("RETIRAR:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("EVENTO:");

        txtfec_devolusion_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfec_devolusion_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_devolusion_previstoKeyPressed(evt);
            }
        });

        txtfec_retirado_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfec_retirado_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfec_retirado_previstoKeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("HORA:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("HORA:");

        txthora_devolusion_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txthora_devolusion_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txthora_devolusion_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txthora_devolusion_previstoKeyTyped(evt);
            }
        });

        txthora_retirado_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txthora_retirado_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txthora_retirado_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txthora_retirado_previstoKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText(":");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText(":");

        txtminuto_devolusion_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtminuto_devolusion_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_devolusion_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_devolusion_previstoKeyTyped(evt);
            }
        });

        txtminuto_retirado_previsto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtminuto_retirado_previsto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_retirado_previstoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_retirado_previstoKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Hs");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Hs");

        javax.swing.GroupLayout jPanel_fechaLayout = new javax.swing.GroupLayout(jPanel_fecha);
        jPanel_fecha.setLayout(jPanel_fechaLayout);
        jPanel_fechaLayout.setHorizontalGroup(
            jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_fechaLayout.createSequentialGroup()
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtfec_retirado_previsto, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(txtfec_devolusion_previsto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_fechaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthora_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtminuto_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel_fechaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthora_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtminuto_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_fechaLayout.setVerticalGroup(
            jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_fechaLayout.createSequentialGroup()
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtfec_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txthora_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtminuto_retirado_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_fechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtfec_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txthora_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtminuto_devolusion_previsto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setText("UNI:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setText("MAR:");

        cmbentregador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbentregador.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbentregadorItemStateChanged(evt);
            }
        });
        cmbentregador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbentregadorActionPerformed(evt);
            }
        });

        jLabel1.setText("ENTREGA:");

        jLabel7.setText("EVENTO:");

        cmbtipo_evento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbtipo_evento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbtipo_eventoActionPerformed(evt);
            }
        });

        jLabel13.setText("MONTO:");

        txtmonto_letra.setText("cero");

        btnnuevo_evento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_evento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_eventoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_referencia_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_base_1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_base_1Layout.createSequentialGroup()
                                        .addComponent(jPanel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtmonto_letra))
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbtipo_evento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevo_evento))
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addGap(154, 154, 154)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtobservacion)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addGap(8, 8, 8)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel_referencia_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addGap(7, 7, 7)
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel_fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(cmbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(cmbtipo_evento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnnuevo_evento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("CREAR VENTA", panel_base_1);

        panel_tabla_venta.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla_venta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblventa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblventa.setRowHeight(25);
        tblventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblventaMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblventa);

        javax.swing.GroupLayout panel_tabla_ventaLayout = new javax.swing.GroupLayout(panel_tabla_venta);
        panel_tabla_venta.setLayout(panel_tabla_ventaLayout);
        panel_tabla_ventaLayout.setHorizontalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        panel_tabla_ventaLayout.setVerticalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnestado_anulado.setBackground(new java.awt.Color(255, 51, 51));
        btnestado_anulado.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnestado_anulado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btnestado_anulado.setText("ANULAR");
        btnestado_anulado.setToolTipText("");
        btnestado_anulado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnestado_anulado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_anuladoActionPerformed(evt);
            }
        });

        btnfacturar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnfacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_factura.png"))); // NOI18N
        btnfacturar.setText("FACTURAR ");
        btnfacturar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnfacturar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnfacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfacturarActionPerformed(evt);
            }
        });

        btnimprimirNota.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnimprimirNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimirNota.setText("IMPRIMIR");
        btnimprimirNota.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnimprimirNota.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnimprimirNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirNotaActionPerformed(evt);
            }
        });

        panel_tabla_item.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM RESERVADO"));

        tblitem_venta_filtro.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_venta_filtro);

        javax.swing.GroupLayout panel_tabla_itemLayout = new javax.swing.GroupLayout(panel_tabla_item);
        panel_tabla_item.setLayout(panel_tabla_itemLayout);
        panel_tabla_itemLayout.setHorizontalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
        );
        panel_tabla_itemLayout.setVerticalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("IDVENTA:");

        txtbuscar_idventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_idventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_idventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscar_idventaKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("FECHA:");

        txtbuscar_fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtbuscar_fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_fechaKeyPressed(evt);
            }
        });

        lblcantidad_filtro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblcantidad_filtro.setForeground(new java.awt.Color(0, 0, 204));
        lblcantidad_filtro.setText("jLabel12");

        btnestado_alquilado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnestado_alquilado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/flecha-salir.png"))); // NOI18N
        btnestado_alquilado.setText("ALQUILADO");
        btnestado_alquilado.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnestado_alquilado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnestado_alquilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_alquiladoActionPerformed(evt);
            }
        });

        btnestado_devolucion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnestado_devolucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/flecha-retornar.png"))); // NOI18N
        btnestado_devolucion.setText("DEVOLUCION");
        btnestado_devolucion.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnestado_devolucion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnestado_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnestado_devolucionActionPerformed(evt);
            }
        });

        panel_estado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO VENTA"));

        jCestado_emitido.setText("EMITIDO");
        jCestado_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_emitidoActionPerformed(evt);
            }
        });

        jCestado_finalizado.setText("FINALIZADO");
        jCestado_finalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_finalizadoActionPerformed(evt);
            }
        });

        jCestado_anulado.setText("ANULADO");
        jCestado_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_anuladoActionPerformed(evt);
            }
        });

        jCestado_devolucion.setText("DEVOLUCION");
        jCestado_devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_devolucionActionPerformed(evt);
            }
        });

        jCestado_usoreserva.setText("USO_RESERVA");
        jCestado_usoreserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_usoreservaActionPerformed(evt);
            }
        });

        jCestado_alquilado.setText("ALQUILADO");
        jCestado_alquilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCestado_alquiladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_estadoLayout = new javax.swing.GroupLayout(panel_estado);
        panel_estado.setLayout(panel_estadoLayout);
        panel_estadoLayout.setHorizontalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCestado_devolucion)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_finalizado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCestado_anulado)
                    .addComponent(jCestado_usoreserva)
                    .addComponent(jCestado_alquilado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_estadoLayout.setVerticalGroup(
            panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_estadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_emitido)
                    .addComponent(jCestado_alquilado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_devolucion)
                    .addComponent(jCestado_usoreserva))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_estadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCestado_finalizado)
                    .addComponent(jCestado_anulado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnalquiler_todos.setText("TODOS");
        btnalquiler_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnalquiler_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_referencia_ventaLayout = new javax.swing.GroupLayout(panel_referencia_venta);
        panel_referencia_venta.setLayout(panel_referencia_ventaLayout);
        panel_referencia_ventaLayout.setHorizontalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_tabla_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(btnestado_alquilado, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnestado_devolucion))
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                        .addComponent(btnestado_anulado, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnfacturar))
                                    .addComponent(btnalquiler_todos)))
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblcantidad_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(103, Short.MAX_VALUE))))
        );
        panel_referencia_ventaLayout.setVerticalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addComponent(panel_tabla_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnestado_alquilado, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnestado_devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtbuscar_idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblcantidad_filtro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                                .addComponent(btnestado_anulado, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnalquiler_todos))
                            .addComponent(panel_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnimprimirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnfacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_VENTA.addTab("FILTRO VENTA", panel_referencia_venta);

        panel_tabla_busca_cli.setBackground(new java.awt.Color(51, 102, 255));
        panel_tabla_busca_cli.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA BUSCAR CLIENTE"));

        tblbuscar_cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblbuscar_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbuscar_cliente.setRowHeight(30);
        tblbuscar_cliente.setRowMargin(5);
        tblbuscar_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblbuscar_clienteMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbuscar_clienteMouseReleased(evt);
            }
        });
        tblbuscar_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblbuscar_clienteKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tblbuscar_cliente);

        txtbuscador_cliente_nombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_nombreKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 204));
        jLabel26.setText("Buscar Nombre:");

        jLabel27.setBackground(new java.awt.Color(255, 255, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 204));
        jLabel27.setText("Buscar Telefono:");

        txtbuscador_cliente_telefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_telefonoKeyReleased(evt);
            }
        });

        txtbuscador_cliente_ruc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscador_cliente_ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_rucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscador_cliente_rucKeyReleased(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 204));
        jLabel28.setText("Buscar Ruc:");

        jCfuncionario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jCfuncionario.setForeground(new java.awt.Color(255, 255, 255));
        jCfuncionario.setText("FUNCIONARIO");
        jCfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCfuncionarioActionPerformed(evt);
            }
        });

        btnnuevoCliente.setText("NUEVO CLIENTE");
        btnnuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoClienteActionPerformed(evt);
            }
        });

        btnseleccionarCerrar.setBackground(new java.awt.Color(255, 255, 153));
        btnseleccionarCerrar.setText("SELECCIONAR IR VENTA");
        btnseleccionarCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionarCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_busca_cliLayout = new javax.swing.GroupLayout(panel_tabla_busca_cli);
        panel_tabla_busca_cli.setLayout(panel_tabla_busca_cliLayout);
        panel_tabla_busca_cliLayout.setHorizontalGroup(
            panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txtbuscador_cliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(txtbuscador_cliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                                .addComponent(txtbuscador_cliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevoCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnseleccionarCerrar)
                            .addComponent(jCfuncionario))
                        .addContainerGap(375, Short.MAX_VALUE))))
        );
        panel_tabla_busca_cliLayout.setVerticalGroup(
            panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_busca_cliLayout.createSequentialGroup()
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jCfuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_tabla_busca_cliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtbuscador_cliente_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscador_cliente_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscador_cliente_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevoCliente)
                    .addComponent(btnseleccionarCerrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_busca_cli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_busca_cli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane_VENTA.addTab("BUSCAR CLIENTE", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane_VENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 1289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_VENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        ancho_tabla_producto();
        DAOva.ancho_tabla_venta_alquiler(tblventa);
        DAOpc.ancho_tabla_producto_combo_alquiler(tblproducto_combo);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        actualizar_tabla_producto(3);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void txtbucarCliente_direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_direccionKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtbucarCliente_direccionKeyReleased

    private void txtbucarCliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_nombre, jList_cliente, "cliente", "nombre", ENTcli.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_nombreKeyReleased

    private void txtbucarCliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_telefono, jList_cliente, "cliente", "telefono", ENTcli.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyReleased

    private void txtbucarCliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(connLocal, txtbucarCliente_ruc, jList_cliente, "cliente", "ruc", ENTcli.getCliente_mostrar(), 4);
    }//GEN-LAST:event_txtbucarCliente_rucKeyReleased

    private void txtbucarCliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_nombreKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_telefono.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_nombreKeyPressed

    private void txtbucarCliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_telefonoKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_ruc.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_telefonoKeyPressed

    private void txtbucarCliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarCliente_rucKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_cliente);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtbucarCliente_nombre.grabFocus();
        }
    }//GEN-LAST:event_txtbucarCliente_rucKeyPressed

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:

        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void tblitem_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_productoMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tblitem_productoMouseReleased

    private void btnlimpiar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiar_clienteActionPerformed
        // TODO add your handling code here:
        limpiar_cliente();
        sumar_item_venta();
    }//GEN-LAST:event_btnlimpiar_clienteActionPerformed

    private void btnconfirmar_venta_efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconfirmar_venta_efectivoActionPerformed
        // TODO add your handling code here:
        boton_venta_efectivo();
    }//GEN-LAST:event_btnconfirmar_venta_efectivoActionPerformed

    private void jList_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_cliente(1);
        }
    }//GEN-LAST:event_jList_clienteKeyPressed

    private void jList_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_clienteMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_cliente(1);
    }//GEN-LAST:event_jList_clienteMouseReleased

    private void btnestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_anuladoActionPerformed
        // TODO add your handling code here:
//        boton_estado_venta_anular();
        boton_anular_venta_alquiler();
    }//GEN-LAST:event_btnestado_anuladoActionPerformed

    private void btnfacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfacturarActionPerformed
        // TODO add your handling code here:
        boton_factura();

    }//GEN-LAST:event_btnfacturarActionPerformed

    private void btnimprimirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirNotaActionPerformed
        // TODO add your handling code here:
//        boton_imprimirPos_venta();
        boton_venta_alquiler_imprimir_ticket();
    }//GEN-LAST:event_btnimprimirNotaActionPerformed

    private void txtbuscar_idventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_venta(2);
        }
    }//GEN-LAST:event_txtbuscar_idventaKeyPressed

    private void txtbuscar_idventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_idventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtbuscar_idventaKeyTyped

    private void txtbuscar_fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_fechaKeyPressed
        // TODO add your handling code here:
        evejtf.verificar_fecha(evt, txtbuscar_fecha);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actualizar_venta(3);
        }
    }//GEN-LAST:event_txtbuscar_fechaKeyPressed

    private void btnnuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_clienteActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmCliente());
    }//GEN-LAST:event_btnnuevo_clienteActionPerformed

    private void tblbuscar_clienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscar_clienteMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            seleccionar_cargar_cliente(3);
            limpiar_buscardor_cliente();
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
        }
    }//GEN-LAST:event_tblbuscar_clienteMousePressed

    private void tblbuscar_clienteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscar_clienteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblbuscar_clienteMouseReleased

    private void tblbuscar_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscar_clienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!evejt.getBoolean_validar_select(tblbuscar_cliente)) {
                seleccionar_cargar_cliente(3);
                limpiar_buscardor_cliente();
                evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
            }
        }
    }//GEN-LAST:event_tblbuscar_clienteKeyPressed

    private void txtbuscador_cliente_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_nombreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_nombreKeyPressed

    private void txtbuscador_cliente_nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_nombreKeyReleased
        // TODO add your handling code here:
        DAOcli.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_nombre, jCfuncionario, 1);
    }//GEN-LAST:event_txtbuscador_cliente_nombreKeyReleased

    private void txtbuscador_cliente_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_telefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_telefonoKeyPressed

    private void txtbuscador_cliente_telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_telefonoKeyReleased
        // TODO add your handling code here:
        DAOcli.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_telefono, jCfuncionario, 2);
    }//GEN-LAST:event_txtbuscador_cliente_telefonoKeyReleased

    private void txtbuscador_cliente_rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_rucKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscador_cliente_rucKeyPressed

    private void txtbuscador_cliente_rucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscador_cliente_rucKeyReleased
        // TODO add your handling code here:
        DAOcli.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_ruc, jCfuncionario, 3);
    }//GEN-LAST:event_txtbuscador_cliente_rucKeyReleased

    private void jCfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCfuncionarioActionPerformed
        // TODO add your handling code here:
        if (jCfuncionario.isSelected()) {
            panel_tabla_busca_cli.setBackground(Color.orange);
        } else {
            panel_tabla_busca_cli.setBackground(clacolor.getColor_tabla());//[51,102,255]
        }
        txtbuscador_cliente_nombre.grabFocus();
        DAOcli.buscar_tabla_cliente_zona(connLocal, tblbuscar_cliente, txtbuscador_cliente_nombre, jCfuncionario, 1);
    }//GEN-LAST:event_jCfuncionarioActionPerformed

    private void btnnuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoClienteActionPerformed
        // TODO add your handling code here:
        limpiar_buscardor_cliente();
        evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 2);
    }//GEN-LAST:event_btnnuevoClienteActionPerformed

    private void btnseleccionarCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionarCerrarActionPerformed
        // TODO add your handling code here:
        if (!evejt.getBoolean_validar_select(tblbuscar_cliente)) {
            seleccionar_cargar_cliente(3);
            limpiar_buscardor_cliente();
            evejt.mostrar_JTabbedPane(jTabbedPane_VENTA, 0);
        }
    }//GEN-LAST:event_btnseleccionarCerrarActionPerformed

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
        boton_buscar_cliente_venta();
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void btnagregar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_cantidadActionPerformed
        // TODO add your handling code here:
        cargar_item_producto();
    }//GEN-LAST:event_btnagregar_cantidadActionPerformed

    private void tblventaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblventaMouseReleased
        // TODO add your handling code here:
        seleccionar_venta_alquiler();
    }//GEN-LAST:event_tblventaMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boton_cancelar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtcantidad_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_totalKeyReleased

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void txtcod_barraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyReleased
        // TODO add your handling code here:
//        actualizar_tabla_producto(5);
    }//GEN-LAST:event_txtcod_barraKeyReleased

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_cod_barra_producto();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            boton_venta_efectivo();
        }
    }//GEN-LAST:event_txtcod_barraKeyPressed

    private void txtcantidad_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        }
    }//GEN-LAST:event_txtcantidad_totalKeyPressed

    private void txtcantidad_totalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_totalKeyTyped

    private void txtprecio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_ventaKeyTyped

    private void txtprecio_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyReleased
        // TODO add your handling code here:
        habilitar_editar_precio_venta = true;
        calcular_subtotal_itemventa();
    }//GEN-LAST:event_txtprecio_ventaKeyReleased

    private void txtprecio_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcantidad_total.grabFocus();
        }
    }//GEN-LAST:event_txtprecio_ventaKeyPressed

    private void txtcantidad_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidad_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_totalActionPerformed

    private void txtdireccion_alquilerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccion_alquilerKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccion_alquilerKeyReleased

    private void txtfec_retirado_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_retirado_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfec_retirado_previsto.setText(evefec.getString_validar_fecha(txtfec_retirado_previsto.getText()));
            evejtf.saltar_campo_enter(evt, txtfec_retirado_previsto, txthora_retirado_previsto);
        }
    }//GEN-LAST:event_txtfec_retirado_previstoKeyPressed

    private void txthora_retirado_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_retirado_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_hora(txthora_retirado_previsto)) {
                evejtf.saltar_campo_enter(evt, txthora_retirado_previsto, txtminuto_retirado_previsto);
            }
        }
    }//GEN-LAST:event_txthora_retirado_previstoKeyPressed

    private void txtminuto_retirado_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_retirado_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_minuto(txtminuto_retirado_previsto)) {
                evejtf.saltar_campo_enter(evt, txtminuto_retirado_previsto, txtfec_devolusion_previsto);
            }
        }
    }//GEN-LAST:event_txtminuto_retirado_previstoKeyPressed

    private void txtfec_devolusion_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_devolusion_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtfec_devolusion_previsto.setText(evefec.getString_validar_fecha(txtfec_devolusion_previsto.getText()));
            evejtf.saltar_campo_enter(evt, txtfec_devolusion_previsto, txthora_devolusion_previsto);
        }
    }//GEN-LAST:event_txtfec_devolusion_previstoKeyPressed

    private void txthora_devolusion_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_devolusion_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_hora(txthora_devolusion_previsto)) {
                evejtf.saltar_campo_enter(evt, txthora_devolusion_previsto, txtminuto_devolusion_previsto);
            }
        }
    }//GEN-LAST:event_txthora_devolusion_previstoKeyPressed

    private void txtminuto_devolusion_previstoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_devolusion_previstoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getBoolean_validar_minuto(txtminuto_devolusion_previsto)) {
                evejtf.saltar_campo_enter(evt, txtminuto_devolusion_previsto, txtfec_retirado_previsto);
            }
        }
    }//GEN-LAST:event_txtminuto_devolusion_previstoKeyPressed

    private void txtmonto_descuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_descuentoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_descuentoKeyTyped

    private void txthora_retirado_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_retirado_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txthora_retirado_previstoKeyTyped

    private void txthora_devolusion_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txthora_devolusion_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txthora_devolusion_previstoKeyTyped

    private void txtminuto_retirado_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_retirado_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_retirado_previstoKeyTyped

    private void txtminuto_devolusion_previstoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_devolusion_previstoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_devolusion_previstoKeyTyped

    private void cmbentregadorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbentregadorItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbentregadorItemStateChanged

    private void cmbentregadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbentregadorActionPerformed
        // TODO add your handling code here:
        if (hab_carga_entregador) {
            fk_identregador = evecmb.getInt_seleccionar_COMBOBOX(connLocal, cmbentregador, entre_id, entre_colum, entre_tabla);
        }
    }//GEN-LAST:event_cmbentregadorActionPerformed

    private void jRcond_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRcond_creditoActionPerformed
        // TODO add your handling code here:
        select_condicion();
    }//GEN-LAST:event_jRcond_creditoActionPerformed

    private void btnestado_alquiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_alquiladoActionPerformed
        // TODO add your handling code here:
        boton_venta_alquiler_alquilado();
    }//GEN-LAST:event_btnestado_alquiladoActionPerformed

    private void btnestado_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnestado_devolucionActionPerformed
        // TODO add your handling code here:
        boton_venta_alquiler_devolucion();
    }//GEN-LAST:event_btnestado_devolucionActionPerformed

    private void jCestado_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_emitidoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
    }//GEN-LAST:event_jCestado_emitidoActionPerformed

    private void jCestado_finalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_finalizadoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
    }//GEN-LAST:event_jCestado_finalizadoActionPerformed

    private void jCestado_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
    }//GEN-LAST:event_jCestado_anuladoActionPerformed

    private void jCestado_devolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_devolucionActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
    }//GEN-LAST:event_jCestado_devolucionActionPerformed

    private void jCestado_usoreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_usoreservaActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
    }//GEN-LAST:event_jCestado_usoreservaActionPerformed

    private void jCestado_alquiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCestado_alquiladoActionPerformed
        // TODO add your handling code here:
        actualizar_venta(1);
    }//GEN-LAST:event_jCestado_alquiladoActionPerformed

    private void btnalquiler_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnalquiler_todosActionPerformed
        // TODO add your handling code here:
        actualizar_venta(0);
    }//GEN-LAST:event_btnalquiler_todosActionPerformed

    private void txtfec_vence_creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfec_vence_creditoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfec_vence_creditoKeyPressed

    private void txtmonto_descuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_descuentoKeyReleased
        // TODO add your handling code here:
        sumar_item_venta();
    }//GEN-LAST:event_txtmonto_descuentoKeyReleased

    private void tblproducto_comboMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproducto_comboMouseReleased
        // TODO add your handling code here:
        seleccionar_producto_combo();
    }//GEN-LAST:event_tblproducto_comboMouseReleased

    private void txtcantidad_total_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidad_total_comboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_total_comboActionPerformed

    private void txtcantidad_total_comboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_total_comboKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_producto_combo();
        }
    }//GEN-LAST:event_txtcantidad_total_comboKeyPressed

    private void txtcantidad_total_comboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_total_comboKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidad_total_comboKeyReleased

    private void txtcantidad_total_comboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_total_comboKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_total_comboKeyTyped

    private void txtmonto_senaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_senaKeyReleased
        // TODO add your handling code here:
        monto_sena = evejtf.getDouble_format_nro_entero(txtmonto_sena);
    }//GEN-LAST:event_txtmonto_senaKeyReleased

    private void txtmonto_senaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_senaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_senaKeyTyped

    private void cmbtipo_eventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbtipo_eventoActionPerformed
        // TODO add your handling code here:
        if (hab_carga_tipo_evento) {
            fk_idtipo_evento = evecmb.getInt_seleccionar_COMBOBOX(connLocal, cmbtipo_evento, te_id, te_colum, te_tabla);
        }
    }//GEN-LAST:event_cmbtipo_eventoActionPerformed

    private void btnnuevo_eventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_eventoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTipo_evento());
    }//GEN-LAST:event_btnnuevo_eventoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btnalquiler_todos;
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btnconfirmar_venta_efectivo;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnestado_alquilado;
    private javax.swing.JButton btnestado_anulado;
    private javax.swing.JButton btnestado_devolucion;
    private javax.swing.JButton btnfacturar;
    private javax.swing.JButton btnimprimirNota;
    private javax.swing.JButton btnlimpiar_cliente;
    private javax.swing.JButton btnnuevoCliente;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.JButton btnnuevo_evento;
    private javax.swing.JButton btnseleccionarCerrar;
    private javax.swing.JComboBox<String> cmbentregador;
    private javax.swing.JComboBox<String> cmbtipo_evento;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.ButtonGroup gru_condi;
    private javax.swing.ButtonGroup gru_tipocli;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCestado_alquilado;
    private javax.swing.JCheckBox jCestado_anulado;
    private javax.swing.JCheckBox jCestado_devolucion;
    private javax.swing.JCheckBox jCestado_emitido;
    private javax.swing.JCheckBox jCestado_finalizado;
    private javax.swing.JCheckBox jCestado_usoreserva;
    private javax.swing.JCheckBox jCfuncionario;
    public static javax.swing.JFormattedTextField jFmonto_pagar;
    public static javax.swing.JFormattedTextField jFmonto_total;
    private javax.swing.JFormattedTextField jFsaldo_credito;
    private javax.swing.JFormattedTextField jFtotal_reposicion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList_cliente;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_fecha;
    private javax.swing.JRadioButton jRcond_credito;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_VENTA;
    private javax.swing.JLabel lblcantidad_filtro;
    private javax.swing.JLabel lblidcliente;
    private javax.swing.JLabel lblvence_credito;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_estado;
    private javax.swing.JPanel panel_insertar_pri_item;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_marca;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JPanel panel_referencia_venta;
    private javax.swing.JPanel panel_tabla_busca_cli;
    private javax.swing.JPanel panel_tabla_item;
    private javax.swing.JPanel panel_tabla_venta;
    private javax.swing.JTable tblbuscar_cliente;
    public static javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblitem_venta_filtro;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTable tblproducto_combo;
    private javax.swing.JTable tblventa;
    private javax.swing.JTextField txtbucarCliente_direccion;
    private javax.swing.JTextField txtbucarCliente_nombre;
    private javax.swing.JTextField txtbucarCliente_ruc;
    private javax.swing.JTextField txtbucarCliente_telefono;
    private javax.swing.JTextField txtbuscador_cliente_nombre;
    private javax.swing.JTextField txtbuscador_cliente_ruc;
    private javax.swing.JTextField txtbuscador_cliente_telefono;
    private javax.swing.JTextField txtbuscar_fecha;
    private javax.swing.JTextField txtbuscar_idventa;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcantidad_total;
    private javax.swing.JTextField txtcantidad_total_combo;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtdireccion_alquiler;
    private javax.swing.JTextField txtfec_devolusion_previsto;
    private javax.swing.JTextField txtfec_retirado_previsto;
    private javax.swing.JTextField txtfec_vence_credito;
    private javax.swing.JTextField txthora_devolusion_previsto;
    private javax.swing.JTextField txthora_retirado_previsto;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtminuto_devolusion_previsto;
    private javax.swing.JTextField txtminuto_retirado_previsto;
    private javax.swing.JTextField txtmonto_descuento;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtmonto_sena;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtprecio_venta;
    private javax.swing.JTextField txtsubtotal;
    // End of variables declaration//GEN-END:variables
}
