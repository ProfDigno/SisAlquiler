package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_venta_alquiler;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.venta_alquiler;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_venta_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    DAO_producto pdao = new DAO_producto();
    private String mensaje_insert = "ITEM_VENTA_ALQUILER GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_VENTA_ALQUILER MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_venta_alquiler("
            + "iditem_venta_alquiler,descripcion,"
            + "precio_venta,precio_compra,cantidad_total,cantidad_pagado,"
            + "fk_idventa_alquiler,fk_idproducto,"
            + "fk_idproducto_combo,precio_alquiler,precio_reposicion,es_combo,es_producto,orden) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_venta_alquiler SET descripcion=?,"
            + "precio_venta=?,precio_compra=?,cantidad_total=?,cantidad_pagado=?,"
            + "fk_idventa_alquiler=?,fk_idproducto=?,"
            + "fk_idproducto_combo=?,precio_alquiler=?,precio_reposicion=?,es_combo=?,es_producto=? "
            + "WHERE iditem_venta_alquiler=?;";
    private String sql_select = "SELECT iditem_venta_alquiler,descripcion,precio_venta,precio_compra,cantidad_total,cantidad_pagado,fk_idventa_alquiler,fk_idproducto FROM item_venta_alquiler order by 1 desc;";
    private String sql_cargar = "SELECT iditem_venta_alquiler,descripcion,precio_venta,precio_compra,cantidad_total,cantidad_pagado,fk_idventa_alquiler,fk_idproducto FROM item_venta_alquiler WHERE iditem_venta_alquiler=";

    private String sql_cant_reser = "select iditem_venta_alquiler as iditem\n"
            + ",descripcion\n"
            + ",trim(to_char(precio_alquiler,'999G999G999')) as pventa\n"
            + ",(cantidad_total) as cant\n"
            + ",trim(to_char(((cantidad_total)*precio_alquiler),'999G999G999')) as total\n"
            + "from item_venta_alquiler\n"
            + "where  fk_idventa_alquiler="
            ;
    private String sql_cant_reser_int = "select fk_idproducto as idp\n"
            + ",descripcion\n"
            + ",precio_alquiler as pventa\n"
            + ",(cantidad_total) as cant\n"
            + ",((cantidad_total)*precio_alquiler) as total\n"
            + "from item_venta_alquiler\n"
            + "where (cantidad_total-cantidad_pagado)>0 and fk_idventa_alquiler="
            ;
    private String sql_cant_total = "select (descripcion||' ====>  '||cantidad_total) as descrip "
            + "from item_venta_alquiler\n"
            + "where fk_idventa_alquiler="
            ;
    public void insertar_item_venta_alquiler(Connection conn, item_venta_alquiler ivealq) {
        ivealq.setC1iditem_venta_alquiler(eveconn.getInt_ultimoID_mas_uno(conn, ivealq.getTb_item_venta_alquiler(), ivealq.getId_iditem_venta_alquiler()));
        String titulo = "insertar_item_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ivealq.getC1iditem_venta_alquiler());
            pst.setString(2, ivealq.getC2descripcion());
            pst.setDouble(3, ivealq.getC3precio_venta());
            pst.setDouble(4, ivealq.getC4precio_compra());
            pst.setDouble(5, ivealq.getC5cantidad_total());
            pst.setDouble(6, ivealq.getC6cantidad_pagado());
            pst.setInt(7, ivealq.getC7fk_idventa_alquiler());
            pst.setInt(8, ivealq.getC8fk_idproducto());
            pst.setInt(9, ivealq.getC9fk_idproducto_combo());
            pst.setDouble(10, ivealq.getC10precio_alquiler());
            pst.setDouble(11, ivealq.getC11precio_reposicion());
            pst.setBoolean(12, ivealq.getC12es_combo());
            pst.setBoolean(13, ivealq.getC13es_producto());
            pst.setInt(14, ivealq.getC14orden());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ivealq.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ivealq.toString(), titulo);
        }
    }

    public void update_item_venta_alquiler(Connection conn, item_venta_alquiler ivealq) {
        String titulo = "update_item_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, ivealq.getC2descripcion());
            pst.setDouble(2, ivealq.getC3precio_venta());
            pst.setDouble(3, ivealq.getC4precio_compra());
            pst.setDouble(4, ivealq.getC5cantidad_total());
            pst.setDouble(5, ivealq.getC6cantidad_pagado());
            pst.setInt(6, ivealq.getC7fk_idventa_alquiler());
            pst.setInt(7, ivealq.getC8fk_idproducto());
            pst.setInt(8, ivealq.getC1iditem_venta_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ivealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ivealq.toString(), titulo);
        }
    }

    public void cargar_item_venta_alquiler(Connection conn, item_venta_alquiler ivealq, int id) {
        String titulo = "Cargar_item_venta_alquiler";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                ivealq.setC1iditem_venta_alquiler(rs.getInt(1));
                ivealq.setC2descripcion(rs.getString(2));
                ivealq.setC3precio_venta(rs.getDouble(3));
                ivealq.setC4precio_compra(rs.getDouble(4));
                ivealq.setC5cantidad_total(rs.getDouble(5));
                ivealq.setC6cantidad_pagado(rs.getDouble(6));
                ivealq.setC7fk_idventa_alquiler(rs.getInt(7));
                ivealq.setC8fk_idproducto(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ivealq.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ivealq.toString(), titulo);
        }
    }

    public void insertar_item_venta_alquiler_de_tabla(Connection conn, JTable tblitem_producto, venta_alquiler ven) {
        String titulo="insertar_item_venta_alquiler_de_tabla";
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String precio_alquiler = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String cantidad_pagado = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_reposicion = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            String fk_idproducto_combo = ((tblitem_producto.getModel().getValueAt(row, 9).toString()));
            String Sorden = ((tblitem_producto.getModel().getValueAt(row, 10).toString()));
            double Dcant_pagado = Double.parseDouble(cantidad_pagado);
            double Dprecio_venta= 0;
            double Dprecio_compra=0;
            int Iorden=Integer.parseInt(Sorden);
            int Ifk_idproducto_combo=Integer.parseInt(fk_idproducto_combo);
            double Dprecio_reposicion=Double.parseDouble(precio_reposicion);
            double Dprecio_alquiler=Double.parseDouble(precio_alquiler);
            int Ifk_idproducto=Integer.parseInt(idproducto);
            boolean Bes_combo=false;
            boolean Bes_producto=false;
            if(Ifk_idproducto>0){
                Bes_producto=true;
            }
            if(Ifk_idproducto_combo>0){
                Bes_combo=true;
            }
            try {
                item_venta_alquiler item = new item_venta_alquiler();
                item.setC2descripcion(descripcion);
                item.setC3precio_venta(Dprecio_venta);
                item.setC4precio_compra(Dprecio_compra);
                item.setC5cantidad_total(Dcant_pagado);
                item.setC6cantidad_pagado(Dcant_pagado); 
                item.setC7fk_idventa_alquiler(ven.getC1idventa_alquiler());
                item.setC8fk_idproducto(Ifk_idproducto);
                item.setC9fk_idproducto_combo(Ifk_idproducto_combo);
                item.setC10precio_alquiler(Dprecio_alquiler);
                item.setC11precio_reposicion(Dprecio_reposicion);
                item.setC12es_combo(Bes_combo);
                item.setC13es_producto(Bes_producto);
                item.setC14orden(Iorden);
                insertar_item_venta_alquiler(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e,titulo);
                break;
            }

        }
    }
    public String getString_cargar_item_venta_alquiler_cantidad_total(Connection conn, int id) {
        String suma_item="";
        String titulo = "getString_cargar_item_venta_alquiler_cantidad_total";
        String sql=sql_cant_total + id;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn,sql , titulo);
            while (rs.next()) {
                String descripcion=(rs.getString(1));
                suma_item=suma_item+descripcion +"\n";
            }
            evemen.Imprimir_serial_sql(sql , titulo);
        } catch (Exception e) {
            suma_item="error";
            evemen.mensaje_error(e, sql , titulo);
        }
        return suma_item;
    }
    public void actualizar_tabla_item_venta_alquiler(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_venta_alquiler(tbltabla);
    }

    public void ancho_tabla_item_venta_alquiler(JTable tbltabla) {
        int Ancho[] = {12, 12, 12, 12, 12, 12, 12, 12};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_item_venta_alquiler_cant_reser(Connection conn, JTable tbltabla,int idventa_alquiler) {
        eveconn.Select_cargar_jtable(conn, sql_cant_reser+idventa_alquiler+" order by orden asc;", tbltabla);
        ancho_tabla_item_venta_alquiler_cant_reser(tbltabla);
    }
    public void actualizar_tabla_item_venta_alquiler_cant_reser_int(Connection conn, JTable tbltabla,int idventa_alquiler) {
        eveconn.Select_cargar_jtable(conn, sql_cant_reser_int+idventa_alquiler+" order by orden asc;", tbltabla);
        ancho_tabla_item_venta_alquiler_cant_reser(tbltabla);
    }
    public void ancho_tabla_item_venta_alquiler_cant_reser(JTable tbltabla) {
        int Ancho[] = {5,70,10,5,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }
}
