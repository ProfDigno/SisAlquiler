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
import Evento.Color.cla_color_pelete;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenUtil;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.BO.BO_cliente;
import FORMULARIO.BO.BO_producto_combo;
import FORMULARIO.BO.BO_venta;
import FORMULARIO.BO.BO_venta_alquiler;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.FrmFactura;
import FORMULARIO.VISTA.FrmVuelto;
import FORMULARIO.VISTA.JDpago_combinado;
import static FORMULARIO.VISTA.ALQUILER.FrmCliente.txtdelivery;
import static FORMULARIO.VISTA.ALQUILER.FrmCliente.txtzona;
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
public class FrmProducto_combo_alquiler extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenDatosPc evepc = new EvenDatosPc();
    EvenUtil eveut = new EvenUtil();
    EvenConexion eveconn = new EvenConexion();
    EvenJTextField evejtf = new EvenJTextField();
    EvenCombobox evecmb = new EvenCombobox();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    producto prod = new producto();
    DAO_producto pdao = new DAO_producto();
    cotizacion coti = new cotizacion();
    DAO_cotizacion codao = new DAO_cotizacion();
    usuario usu = new usuario();
    Connection connLocal = ConnPostgres.getConnPosgres();
    private java.util.List<JButton> botones_categoria;
    public static DefaultTableModel model_itemf = new DefaultTableModel();
    cla_color_pelete clacolor = new cla_color_pelete();
    ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private producto_combo ENTpc = new producto_combo();
    private DAO_producto_combo DAOpc = new DAO_producto_combo();
    private BO_producto_combo BOpc = new BO_producto_combo();
    private DAO_item_producto_combo DAOipc = new DAO_item_producto_combo();
    private int cant_boton_cate;
    private String fk_idproducto_categoria;
    private String cantidad_producto = "0";
//    private int fk_idcliente_servi;
    private double monto_total;
    private int idproducto_combo;
    private String tabla_origen = "ALQUILER";
    private double valor_redondeo = 500;
    private int Iidproducto;
    private boolean esCargadoCodBarra;
    private boolean habilitar_editar_precio_venta;
    private double precio_alquiler;
    private double precio_reposicion;
    private double descuento;

    private void abrir_formulario() {
        String servidor = "";
        this.setTitle("COMBO--> USUARIO:" + usu.getGlobal_nombre() + servidor);
        evetbl.centrar_formulario_internalframa(this);
        botones_categoria = new ArrayList<>();
        codao.cargar_cotizacion(coti, 1);
        reestableser_venta();
        cargar_boton_categoria();
        crear_item_producto();
    }

    private void color_formulario(Color colorpanel) {
        panel_insertar_pri_item.setBackground(colorpanel);
        panel_insertar_pri_producto.setBackground(colorpanel);
        panel_referencia_categoria.setBackground(colorpanel);
        panel_tabla_venta.setBackground(colorpanel);
        panel_tabla_item.setBackground(colorpanel);
        panel_referencia_venta.setBackground(colorpanel);
        panel_base_1.setBackground(colorpanel);
    }

    private void crear_item_producto() {
        String dato[] = {"id", "descripcion", "P.Unit", "Cant", "T.alquilado", "OPunit", "OPrepos", "OTalquilado", "OTreposicion",
            "fk_idproducto_combo"};
        evejt.crear_tabla_datos(tblitem_producto, model_itemf, dato);
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 42, 12, 6, 12, 1, 1, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tblitem_producto, Ancho);
        evejt.ocultar_columna(tblitem_producto, 5);
        evejt.ocultar_columna(tblitem_producto, 6);
        evejt.ocultar_columna(tblitem_producto, 7);
        evejt.ocultar_columna(tblitem_producto, 8);
        evejt.ocultar_columna(tblitem_producto, 9);
        evejt.alinear_derecha_columna(tblitem_producto, 2);
        evejt.alinear_derecha_columna(tblitem_producto, 4);
    }

    private boolean validar_cargar_item_producto() {
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
        precio_alquiler = evejt.getDouble_sumar_tabla(tblitem_producto, 7);
        precio_reposicion = evejt.getDouble_sumar_tabla(tblitem_producto, 8);
        if (txtdescuento.getText().trim().length() > 0) {
            descuento = evejtf.getDouble_format_nro_entero(txtdescuento);
        } else {
            descuento = 0;
        }

        monto_total = precio_alquiler;// + total_reservado;
        jFtotal_pagado.setValue(precio_alquiler);
        jFtotal_reposicion.setValue(precio_reposicion);
        jFtotal_combo.setValue(precio_alquiler - descuento);
    }

    private void cargar_item_producto() {
        if (validar_cargar_item_producto()) {
            //String dato[] = {"id", "descripcion", "P.Unit", "Cant", "T.alquilado", "OPunit","OPrepos","OTalquilado","OTreposicion"};
            String Sfk_idproducto_combo = "0";
            pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
            String idproducto = String.valueOf(prod.getC1idproducto());
            String descripcion = prod.getC3nombre();
            int Iprecio_mostrar = 0;
            int IOPrepos = 0;
            Iprecio_mostrar = (int) prod.getC24precio_alquiler();
            IOPrepos = (int) prod.getC25precio_reposicion();
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
            String dato[] = {idproducto, descripcion, P_unit, Scant, T_alquilado, OPunit, OPrepos, SOTalquilado, OTreposicion, Sfk_idproducto_combo};
            evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            txtcantidad_total.setText(null);
            txtdescripcion.setText(getDescripcion_item_venta());
            reestableser_item_venta();
            sumar_item_venta();
        }
    }

    private void calculo_cantidad(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        } else {
            int Icant_cobrado = evejtf.getInt_sumar_restar_cantidad(evt, txtcantidad_total, false, 0);
            if (Icant_cobrado > 0) {
                pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
                int Iprecio_mostrar = 0;
                Iprecio_mostrar = (int) prod.getC24precio_alquiler();
                int Iprecio_mayo = (int) prod.getC5precio_venta_mayorista();
                int Icant_mayo = (int) prod.getC6cantidad_mayorista();
                int Isubtotal = 0;
                String Sprecio_venta = "0";
                if (Icant_cobrado < Icant_mayo) {
                    Isubtotal = Icant_cobrado * Iprecio_mostrar;
                    Sprecio_venta = String.valueOf(Iprecio_mostrar);
                    color_campo_item_venta(Color.white);
                } else {
                    Isubtotal = Icant_cobrado * Iprecio_mayo;
                    Sprecio_venta = String.valueOf(Iprecio_mayo);
                    color_campo_item_venta(Color.yellow);
                }
                String Ssubtotal = String.valueOf(Isubtotal);
                txtprecio_venta.setText(Sprecio_venta);
                txtsubtotal.setText(Ssubtotal);
            }
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
        txtstock.setText(null);
        txtbuscar_producto.setText(null);
        txtcod_barra.setText(null);
        txtcantidad_total.setText(null);
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
                System.out.println("fk_idproducto_categoria:" + fk_idproducto_categoria);
                actualizar_tabla_producto(1);
            }
        });
        panel_referencia_categoria.updateUI();
    }

    private void actualizar_tabla_producto(int tipo) {
        String filtro_categoria = "";
        String filtro_unidad = "";
        String filtro_producto = "";
        String filtro_marca = "";
        if (tipo == 1) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
        }
        if (tipo == 3) {
            if (txtbuscar_producto.getText().trim().length() > 0) {
                String por = "%";
                String temp = por + txtbuscar_producto.getText() + por;
                filtro_producto = " and concat(pm.nombre,'-',u.nombre,'-',p.nombre) ilike'" + temp + "' ";
            }
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
        Iprecio_mino = (int) prod.getC24precio_alquiler();
        int Istock = (int) prod.getC8stock();
        Sprecio_venta = String.valueOf(Iprecio_mino);
        String Sstock = String.valueOf(Istock);
        txtbuscar_producto.setText(prod.getC3nombre());
        txtcod_barra.setText(prod.getC2cod_barra());
        if (prod.getC8stock() <= prod.getC9stock_min()) {
            panel_insertar_pri_producto.setBackground(Color.red);
        } else {
            panel_insertar_pri_producto.setBackground(clacolor.getColor_insertar_primario());
        }
        txtstock.setText(Sstock);
        txtcantidad_total.setText(null);
        txtprecio_venta.setText(Sprecio_venta);
        txtsubtotal.setText(Sprecio_venta);
        txtcantidad_total.grabFocus();
    }

    private void buscar_cod_barra_producto() {
        if (pdao.getBoolean_cargar_producto_por_codbarra(connLocal, prod, txtcod_barra.getText())) {
            actualizar_tabla_producto(5);
            tblproducto.changeSelection(0, 0, false, false);
            color_campo_item_venta(Color.white);
            Iidproducto = prod.getC1idproducto();
            pre_cargar_item_venta();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO NINGUN PRODUCTO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionar_producto() {
        color_campo_item_venta(Color.white);
        Iidproducto = evejt.getInt_select_id(tblproducto);
        pdao.cargar_producto_por_idproducto(connLocal, prod, Iidproducto);
        pre_cargar_item_venta();
    }

    private void ancho_tabla_producto() {
        int Ancho[] = {5, 50, 9, 9, 12, 15};
        evejt.setAnchoColumnaJtable(tblproducto, Ancho);
    }

    private void cargar_cantidad_producto(int cant) {
        txtcantidad_total.setText(String.valueOf(cant));
        cargar_item_producto();
    }

    private void boton_eliminar_item() {
        if (!evejt.getBoolean_validar_select(tblitem_producto)) {
            if (evejt.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf)) {
                sumar_item_venta();
            }
        }
    }

    private String filtro_combo() {
        String filtro = "";
        String filtro_activo = "where pc.activo=" + jCver_oculto.isSelected();
        return filtro + filtro_activo;
    }
    private void buscar_nombre_combo(){
        if(txtbuscar_nombre_combo.getText().trim().length()>0){
            String nombre = txtbuscar_nombre_combo.getText();
            String filtro="where pc.nombre ilike '%"+nombre+"%' and pc.activo=" + jCver_oculto.isSelected();
            DAOpc.actualizar_tabla_producto_combo(connLocal, tblproducto_combo, filtro);
        }
    }
    private void reestableser_venta() {
        color_formulario(clacolor.getColor_insertar_primario());
        idproducto_combo = (eveconn.getInt_ultimoID_mas_uno(connLocal, ENTpc.getTb_producto_combo(), ENTpc.getId_idproducto_combo()));
//        vdao.actualizar_tabla_venta_alquiler(connLocal, tblventa, "");
        jCver_oculto.setSelected(true);
        DAOpc.actualizar_tabla_producto_combo(connLocal, tblproducto_combo, filtro_combo());
        txtidproducto_combo.setText(String.valueOf(idproducto_combo));
        actualizar_tabla_producto(1);
        monto_total = 0;
        txtcantidad_total.setText(null);
        txtdescuento.setText("0");
        txtanombre.setText(null);
        txtdescripcion.setText(null);
        jFtotal_pagado.setValue(monto_total);
        jFtotal_reposicion.setValue(monto_total);
        jFtotal_combo.setValue(monto_total);
        evejt.limpiar_tabla_datos(model_itemf);
        evejt.mostrar_JTabbedPane(jTab_producto_ingrediente, 0);

        reestableser_item_venta();
    }

    private String getDescripcion_item_venta() {
        String suma_descripcion = "";
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String cantidad_pagado = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            suma_descripcion = suma_descripcion + "-(" + cantidad_pagado + ") " + descripcion + "\n ";
        }
        return suma_descripcion;
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

    boolean validar_producto_combo() {
        if (evejtf.getBoo_JTextarea_vacio(txtanombre, "CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdescuento, "CARGAR UN DESCUENTO O CERO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtdescripcion, "CARGAR UNA DESCRIPCION")) {
            return false;
        }
        return true;
    }

    private void cargar_dato_producto_combo() {
        double descuento = evejtf.getDouble_format_nro_entero(txtdescuento);
        ENTpc.setC2nombre(txtanombre.getText());
        ENTpc.setC3descripcion(txtdescripcion.getText());
        ENTpc.setC4precio_alquiler(precio_alquiler);
        ENTpc.setC5precio_reposicion(precio_reposicion);
        ENTpc.setC6descuento(descuento);
        ENTpc.setC7activo(true);
    }

    private void boton_guardar_producto_combo() {
        if (validar_producto_combo()) {
            cargar_dato_producto_combo();
            if (BOpc.getBoo_insertar_producto_combo(ENTpc, tblitem_producto)) {
                reestableser_venta();
            }
        }
    }

    private void select_producto_combo() {
        int idproducto_combo = evejt.getInt_select_id(tblproducto_combo);
        DAOipc.actualizar_tabla_item_producto_combo(connLocal, tblitem_producto_combo, idproducto_combo);
        DAOpc.cargar_producto_combo(connLocal, ENTpc, idproducto_combo);
        txtnombre_editar.setText(ENTpc.getC2nombre());
        jFtotal_pagado_editar.setValue(ENTpc.getC4precio_alquiler());
        txtdescuento_editar.setText(evejtf.getString_format_nro_decimal(ENTpc.getC6descuento()));
        jFtotal_combo_editar.setValue(ENTpc.getC4precio_alquiler() - ENTpc.getC6descuento());
        String estado=evejt.getString_select(tblproducto_combo,6);
        if(estado.equals("true")){
            btneditar.setEnabled(true);
            btnrecargar.setEnabled(false);
            btnocultar.setEnabled(true);
        }
        if(estado.equals("false")){
            btneditar.setEnabled(false);
            btnrecargar.setEnabled(true);
            btnocultar.setEnabled(false);
        }
//        jCver_oculto.setSelected(ENTpc.getC7activo());
    }

    boolean validar_producto_combo_editar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre_editar, "CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdescuento_editar, "CARGAR UN DESCUENTO O CERO")) {
            return false;
        }
        return true;
    }

    private void cargar_dato_producto_combo_editar() {
        double descuento = evejtf.getDouble_format_nro_entero(txtdescuento_editar);
        ENTpc.setC2nombre(txtnombre_editar.getText());
        ENTpc.setC6descuento(descuento);
//        ENTpc.setC7activo(jCver_oculto.isSelected());
    }

    private void boton_editar_producto_combo() {
        if (evejt.getBoolean_validar_select_mensaje(tblproducto_combo, "DEBE SELECCIONAR UN COMBO")) {
            if (validar_producto_combo_editar()) {
                cargar_dato_producto_combo_editar();
                BOpc.update_producto_combo(ENTpc);
                DAOpc.actualizar_tabla_producto_combo(connLocal, tblproducto_combo, filtro_combo());
            }
        }
    }

    private void boton_ocultar_producto_combo() {
        if (evejt.getBoolean_validar_select_mensaje(tblproducto_combo, "DEBE SELECCIONAR UN COMBO")) {
            ENTpc.setC7activo(false);
            BOpc.update_producto_combo(ENTpc);
            DAOpc.actualizar_tabla_producto_combo(connLocal, tblproducto_combo, filtro_combo());
        }
    }

    private void boton_recargar_item_producto_combo() {
        if (evejt.getBoolean_validar_select_mensaje(tblproducto_combo, "DEBE SELECCIONAR UN COMBO")) {
            int idproducto_combo = evejt.getInt_select_id(tblproducto_combo);
            DAOpc.cargar_producto_combo(connLocal, ENTpc, idproducto_combo);
            cargar_item_producto_combo(idproducto_combo);
            txtdescripcion.setText(getDescripcion_item_venta());
            txtanombre.setText(ENTpc.getC2nombre());
            txtdescuento.setText(evejtf.getString_format_nro_decimal(ENTpc.getC6descuento()));
            reestableser_item_venta();
            sumar_item_venta();
            evejt.mostrar_JTabbedPane(jTabbedPane_COMBO, 0);
        }
    }

    private void cargar_item_producto_combo(int fk_idproducto_combo) {
        evejt.limpiar_tabla_datos(model_itemf);
        String titulo = "cargar_item_producto_combo";
        String sql = "select pc.fk_idproducto as id,pc.descripcion,\n"
                + "TRIM(to_char(pc.precio_alquiler,'999G999G999')) as punit,\n"
                + "pc.cantidad as cant,\n"
                + "TRIM(to_char((pc.precio_alquiler*pc.cantidad),'999G999G999')) as talquilado,\n"
                + "pc.precio_alquiler as opunit,\n"
                + "pc.precio_reposicion as oprepos,\n"
                + "(pc.precio_alquiler*pc.cantidad) as otalquilado,\n"
                + "(pc.precio_reposicion*pc.cantidad) as otreposicion,\n"
                + "pc.fk_idproducto_combo \n"
                + "from item_producto_combo pc\n"
                + "where pc.fk_idproducto_combo=" + fk_idproducto_combo;
        try {
            ResultSet rs = eveconn.getResulsetSQL(connLocal, sql, titulo);
            while (rs.next()) {
                String idproducto = rs.getString("id");
                String descripcion = rs.getString("descripcion");
                String P_unit = rs.getString("punit");
                String Scant = rs.getString("cant");
                String T_alquilado = rs.getString("talquilado");
                String OPunit = rs.getString("opunit");
                String OPrepos = rs.getString("oprepos");
                String SOTalquilado = rs.getString("otalquilado");
                String OTreposicion = rs.getString("otreposicion");
                String Sfk_idproducto_combo = rs.getString("fk_idproducto_combo");
                String dato[] = {idproducto, descripcion, P_unit, Scant, T_alquilado, OPunit, OPrepos, SOTalquilado, OTreposicion, Sfk_idproducto_combo};
                evejt.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    public FrmProducto_combo_alquiler() {
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
        jTabbedPane_COMBO = new javax.swing.JTabbedPane();
        panel_base_1 = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        btnagregar_cantidad = new javax.swing.JButton();
        txtcod_barra = new javax.swing.JTextField();
        txtsubtotal = new javax.swing.JTextField();
        txtprecio_venta = new javax.swing.JTextField();
        txtstock = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txtcantidad_total = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        panel_insertar_pri_item = new javax.swing.JPanel();
        btneliminar_item = new javax.swing.JButton();
        jFtotal_pagado = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jFtotal_reposicion = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtdescripcion = new javax.swing.JTextArea();
        txtdescuento = new javax.swing.JTextField();
        btnguardar = new javax.swing.JButton();
        jFtotal_combo = new javax.swing.JFormattedTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtanombre = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        txtidproducto_combo = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        panel_referencia_categoria = new javax.swing.JPanel();
        panel_referencia_venta = new javax.swing.JPanel();
        panel_tabla_venta = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblproducto_combo = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtnombre_editar = new javax.swing.JTextField();
        jFtotal_pagado_editar = new javax.swing.JFormattedTextField();
        txtdescuento_editar = new javax.swing.JTextField();
        jFtotal_combo_editar = new javax.swing.JFormattedTextField();
        jCver_oculto = new javax.swing.JCheckBox();
        btneditar = new javax.swing.JButton();
        btnrecargar = new javax.swing.JButton();
        btnocultar = new javax.swing.JButton();
        txtbuscar_nombre_combo = new javax.swing.JTextField();
        panel_tabla_item = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblitem_producto_combo = new javax.swing.JTable();

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

        panel_insertar_pri_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION PRODUCTO"));
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
        txtcod_barra.setBorder(javax.swing.BorderFactory.createTitledBorder("COD. BARRA"));
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
        txtsubtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsubtotal.setBorder(javax.swing.BorderFactory.createTitledBorder("Sub. ALQUILER"));

        txtprecio_venta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtprecio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("P. ALQUILER"));
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

        txtstock.setEditable(false);
        txtstock.setBackground(new java.awt.Color(204, 204, 255));
        txtstock.setBorder(javax.swing.BorderFactory.createTitledBorder("STOCK "));

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

        jLabel13.setText("TOTAL:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtcantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(2, 2, 2)
                .addComponent(txtcantidad_total, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto))
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                                .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcod_barra, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(txtbuscar_producto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtstock)
                    .addComponent(txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        panel_insertar_pri_item.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_item.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btneliminar_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_itemActionPerformed(evt);
            }
        });

        jFtotal_pagado.setEditable(false);
        jFtotal_pagado.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_pagado.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ALQUILER"));
        jFtotal_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_pagado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jFtotal_reposicion.setEditable(false);
        jFtotal_reposicion.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_reposicion.setBorder(javax.swing.BorderFactory.createTitledBorder("T. REPOSICION"));
        jFtotal_reposicion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_reposicion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_reposicion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

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

        txtdescripcion.setColumns(20);
        txtdescripcion.setRows(5);
        txtdescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        jScrollPane5.setViewportView(txtdescripcion);

        txtdescuento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtdescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtdescuento.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCUENTO"));
        txtdescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdescuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescuentoKeyTyped(evt);
            }
        });

        btnguardar.setText("GUARDAR");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        jFtotal_combo.setEditable(false);
        jFtotal_combo.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_combo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMBO"));
        jFtotal_combo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_combo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_combo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txtanombre.setColumns(20);
        txtanombre.setRows(5);
        txtanombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));
        jScrollPane6.setViewportView(txtanombre);

        javax.swing.GroupLayout panel_insertar_pri_itemLayout = new javax.swing.GroupLayout(panel_insertar_pri_item);
        panel_insertar_pri_item.setLayout(panel_insertar_pri_itemLayout);
        panel_insertar_pri_itemLayout.setHorizontalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6)
                    .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                            .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jFtotal_reposicion, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                .addComponent(jFtotal_pagado))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                                    .addComponent(btneliminar_item, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1))
                                .addComponent(txtdescuento))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnguardar, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addComponent(jFtotal_combo)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panel_insertar_pri_itemLayout.setVerticalGroup(
            panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_itemLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFtotal_pagado)
                    .addComponent(txtdescuento)
                    .addComponent(jFtotal_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panel_insertar_pri_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jFtotal_reposicion)
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnguardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("COMBO");

        txtidproducto_combo.setBackground(new java.awt.Color(0, 0, 255));
        txtidproducto_combo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtidproducto_combo.setForeground(new java.awt.Color(255, 255, 0));
        txtidproducto_combo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane3.setViewportView(panel_referencia_categoria);

        javax.swing.GroupLayout panel_base_1Layout = new javax.swing.GroupLayout(panel_base_1);
        panel_base_1.setLayout(panel_base_1Layout);
        panel_base_1Layout.setHorizontalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidproducto_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_base_1Layout.setVerticalGroup(
            panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_base_1Layout.createSequentialGroup()
                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_base_1Layout.createSequentialGroup()
                        .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel_base_1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_base_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtidproducto_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_insertar_pri_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane_COMBO.addTab("CREAR COMBO", panel_base_1);

        panel_tabla_venta.setBackground(new java.awt.Color(153, 153, 255));
        panel_tabla_venta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        tblproducto_combo.setRowHeight(20);
        tblproducto_combo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproducto_comboMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblproducto_combo);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("EDITAR COMBO"));

        txtnombre_editar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnombre_editar.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE COMBO"));

        jFtotal_pagado_editar.setEditable(false);
        jFtotal_pagado_editar.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_pagado_editar.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ALQUILER"));
        jFtotal_pagado_editar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_pagado_editar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_pagado_editar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txtdescuento_editar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtdescuento_editar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtdescuento_editar.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCUENTO"));
        txtdescuento_editar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdescuento_editarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescuento_editarKeyTyped(evt);
            }
        });

        jFtotal_combo_editar.setEditable(false);
        jFtotal_combo_editar.setBackground(new java.awt.Color(204, 204, 255));
        jFtotal_combo_editar.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL COMBO"));
        jFtotal_combo_editar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_combo_editar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_combo_editar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jCver_oculto.setText("VER OCULTO");
        jCver_oculto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCver_ocultoActionPerformed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        btnrecargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_terminar.png"))); // NOI18N
        btnrecargar.setText("RECARGAR");
        btnrecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrecargarActionPerformed(evt);
            }
        });

        btnocultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btnocultar.setText("OCULTAR");
        btnocultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnocultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtnombre_editar, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btneditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFtotal_pagado_editar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtdescuento_editar, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(btnrecargar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_combo_editar, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(btnocultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCver_oculto)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtnombre_editar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jFtotal_pagado_editar)
                            .addComponent(txtdescuento_editar)
                            .addComponent(jFtotal_combo_editar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btneditar)
                            .addComponent(btnrecargar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnocultar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCver_oculto)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        txtbuscar_nombre_combo.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR COMBO:"));
        txtbuscar_nombre_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_nombre_comboKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_tabla_ventaLayout = new javax.swing.GroupLayout(panel_tabla_venta);
        panel_tabla_venta.setLayout(panel_tabla_ventaLayout);
        panel_tabla_ventaLayout.setHorizontalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_tabla_ventaLayout.createSequentialGroup()
                        .addComponent(txtbuscar_nombre_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_tabla_ventaLayout.setVerticalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tabla_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtbuscar_nombre_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_tabla_item.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM RESERVADO"));

        tblitem_producto_combo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblitem_producto_combo);

        javax.swing.GroupLayout panel_tabla_itemLayout = new javax.swing.GroupLayout(panel_tabla_item);
        panel_tabla_item.setLayout(panel_tabla_itemLayout);
        panel_tabla_itemLayout.setHorizontalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        panel_tabla_itemLayout.setVerticalGroup(
            panel_tabla_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_referencia_ventaLayout = new javax.swing.GroupLayout(panel_referencia_venta);
        panel_referencia_venta.setLayout(panel_referencia_ventaLayout);
        panel_referencia_ventaLayout.setHorizontalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_tabla_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_referencia_ventaLayout.setVerticalGroup(
            panel_referencia_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_referencia_ventaLayout.createSequentialGroup()
                .addComponent(panel_tabla_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane_COMBO.addTab("FILTRO COMBO", panel_referencia_venta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_COMBO, javax.swing.GroupLayout.PREFERRED_SIZE, 1263, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane_COMBO, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        ancho_tabla_producto();
        DAOpc.ancho_tabla_producto_combo(tblproducto_combo);
//        vdao.ancho_tabla_venta_alquiler(tblventa);
//        vdao.ancho_tabla_venta(tblventa);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        actualizar_tabla_producto(3);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void btneliminar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_itemActionPerformed
        // TODO add your handling code here:

        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_itemActionPerformed

    private void tblitem_productoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitem_productoMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tblitem_productoMouseReleased

    private void btnagregar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_cantidadActionPerformed
        // TODO add your handling code here:
        cargar_item_producto();
    }//GEN-LAST:event_btnagregar_cantidadActionPerformed

    private void tblproducto_comboMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproducto_comboMouseReleased
        // TODO add your handling code here:
        select_producto_combo();
//        seleccionar_venta_alquiler();
//        seleccionar_tabla_venta();
    }//GEN-LAST:event_tblproducto_comboMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reestableser_venta();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
//        ven.setVenta_aux(false);
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtcantidad_totalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_totalKeyReleased
        // TODO add your handling code here:
//        calcular_subtotal_itemventa();
//        calculo_cantidad(evt);
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

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_producto_combo();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void txtdescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescuentoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtdescuentoKeyTyped

    private void txtdescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescuentoKeyReleased
        // TODO add your handling code here:
        evejtf.getString_format_nro_entero(txtdescuento);
        if (txtdescuento.getText().trim().length() >= 3) {
            sumar_item_venta();
        }
    }//GEN-LAST:event_txtdescuentoKeyReleased

    private void txtdescuento_editarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescuento_editarKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdescuento_editarKeyReleased

    private void txtdescuento_editarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescuento_editarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdescuento_editarKeyTyped

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_producto_combo();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnrecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrecargarActionPerformed
        // TODO add your handling code here:
        boton_recargar_item_producto_combo();
    }//GEN-LAST:event_btnrecargarActionPerformed

    private void btnocultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnocultarActionPerformed
        // TODO add your handling code here:
        boton_ocultar_producto_combo();
    }//GEN-LAST:event_btnocultarActionPerformed

    private void jCver_ocultoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCver_ocultoActionPerformed
        // TODO add your handling code here:
        DAOpc.actualizar_tabla_producto_combo(connLocal, tblproducto_combo, filtro_combo());
    }//GEN-LAST:event_jCver_ocultoActionPerformed

    private void txtbuscar_nombre_comboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nombre_comboKeyReleased
        // TODO add your handling code here:
        buscar_nombre_combo();
    }//GEN-LAST:event_txtbuscar_nombre_comboKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnocultar;
    private javax.swing.JButton btnrecargar;
    private javax.swing.ButtonGroup gru_campo;
    private javax.swing.ButtonGroup gru_condi;
    private javax.swing.ButtonGroup gru_tipocli;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCver_oculto;
    public static javax.swing.JFormattedTextField jFtotal_combo;
    public static javax.swing.JFormattedTextField jFtotal_combo_editar;
    public static javax.swing.JFormattedTextField jFtotal_pagado;
    public static javax.swing.JFormattedTextField jFtotal_pagado_editar;
    private javax.swing.JFormattedTextField jFtotal_reposicion;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane_COMBO;
    private javax.swing.JPanel panel_base_1;
    private javax.swing.JPanel panel_insertar_pri_item;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_venta;
    private javax.swing.JPanel panel_tabla_item;
    private javax.swing.JPanel panel_tabla_venta;
    public static javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblitem_producto_combo;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTable tblproducto_combo;
    private javax.swing.JTextArea txtanombre;
    private javax.swing.JTextField txtbuscar_nombre_combo;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcantidad_total;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextArea txtdescripcion;
    private javax.swing.JTextField txtdescuento;
    private javax.swing.JTextField txtdescuento_editar;
    private javax.swing.JTextField txtidproducto_combo;
    private javax.swing.JTextField txtnombre_editar;
    private javax.swing.JTextField txtprecio_venta;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txtsubtotal;
    // End of variables declaration//GEN-END:variables
}
