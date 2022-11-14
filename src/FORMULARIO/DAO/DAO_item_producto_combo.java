package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.item_producto_combo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import FORMULARIO.ENTIDAD.producto_combo;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_item_producto_combo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "ITEM_PRODUCTO_COMBO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ITEM_PRODUCTO_COMBO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO item_producto_combo(iditem_producto_combo,descripcion,cantidad,precio_alquiler,precio_reposicion,fk_idproducto,fk_idproducto_combo) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE item_producto_combo SET descripcion=?,cantidad=?,precio_alquiler=?,precio_reposicion=?,fk_idproducto=?,fk_idproducto_combo=? WHERE iditem_producto_combo=?;";
//    private String sql_select = "SELECT iditem_producto_combo,descripcion,cantidad,precio_alquiler,precio_reposicion,fk_idproducto,fk_idproducto_combo FROM item_producto_combo order by 1 desc;";
    private String sql_cargar = "SELECT iditem_producto_combo,descripcion,cantidad,precio_alquiler,precio_reposicion,fk_idproducto,fk_idproducto_combo FROM item_producto_combo WHERE iditem_producto_combo=";

    public void insertar_item_producto_combo(Connection conn, item_producto_combo itprco) {
        itprco.setC1iditem_producto_combo(eveconn.getInt_ultimoID_mas_uno(conn, itprco.getTb_item_producto_combo(), itprco.getId_iditem_producto_combo()));
        String titulo = "insertar_item_producto_combo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, itprco.getC1iditem_producto_combo());
            pst.setString(2, itprco.getC2descripcion());
            pst.setDouble(3, itprco.getC3cantidad());
            pst.setDouble(4, itprco.getC4precio_alquiler());
            pst.setDouble(5, itprco.getC5precio_reposicion());
            pst.setInt(6, itprco.getC6fk_idproducto());
            pst.setInt(7, itprco.getC7fk_idproducto_combo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + itprco.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + itprco.toString(), titulo);
        }
    }

    public void update_item_producto_combo(Connection conn, item_producto_combo itprco) {
        String titulo = "update_item_producto_combo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, itprco.getC2descripcion());
            pst.setDouble(2, itprco.getC3cantidad());
            pst.setDouble(3, itprco.getC4precio_alquiler());
            pst.setDouble(4, itprco.getC5precio_reposicion());
            pst.setInt(5, itprco.getC6fk_idproducto());
            pst.setInt(6, itprco.getC7fk_idproducto_combo());
            pst.setInt(7, itprco.getC1iditem_producto_combo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + itprco.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + itprco.toString(), titulo);
        }
    }

    public void cargar_item_producto_combo(Connection conn, item_producto_combo itprco, int iditem_producto_combo) {
        String titulo = "Cargar_item_producto_combo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + iditem_producto_combo, titulo);
            if (rs.next()) {
                itprco.setC1iditem_producto_combo(rs.getInt(1));
                itprco.setC2descripcion(rs.getString(2));
                itprco.setC3cantidad(rs.getDouble(3));
                itprco.setC4precio_alquiler(rs.getDouble(4));
                itprco.setC5precio_reposicion(rs.getDouble(5));
                itprco.setC6fk_idproducto(rs.getInt(6));
                itprco.setC7fk_idproducto_combo(rs.getInt(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + itprco.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + itprco.toString(), titulo);
        }
    }

    public void actualizar_tabla_item_producto_combo(Connection conn, JTable tbltabla,int fk_idproducto_combo) {
        String sql_select = "select ipc.iditem_producto_combo as id,ipc.descripcion,\n"
                + "TRIM(to_char(ipc.precio_alquiler,'999G999G999')) as alquiler,\n"
                + "ipc.cantidad as cant,\n"
                + "TRIM(to_char((ipc.precio_alquiler*ipc.cantidad),'999G999G999')) as t_alquiler,\n"
                + "TRIM(to_char((ipc.precio_reposicion*ipc.cantidad),'999G999G999')) as t_reposicion\n"
                + " from item_producto_combo ipc \n"
                + " where ipc.fk_idproducto_combo="+fk_idproducto_combo
                + " order by ipc.iditem_producto_combo desc ";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_item_producto_combo(tbltabla);
    }

    public void ancho_tabla_item_producto_combo(JTable tbltabla) {
        int Ancho[] = {5, 40, 10, 5, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void insertar_item_producto_combo(Connection conn, JTable tblitem_producto, producto_combo pcom) {
        String titulo = "insertar_item_producto_combo";
        //String dato[] = {"id", "descripcion", "P.Unit", "Cant", "T.alquilado", "OPunit","OPrepos","OTalquilado","OTreposicion","fk_idproducto_combo"};
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String idproducto = ((tblitem_producto.getModel().getValueAt(row, 0).toString()));
            String descripcion = ((tblitem_producto.getModel().getValueAt(row, 1).toString()));
            String precio_alquiler = ((tblitem_producto.getModel().getValueAt(row, 5).toString()));
            String cantidad_pagado = ((tblitem_producto.getModel().getValueAt(row, 3).toString()));
            String precio_reposicion = ((tblitem_producto.getModel().getValueAt(row, 6).toString()));
            double Dcant_pagado = Double.parseDouble(cantidad_pagado);
            double Dprecio_reposicion = Double.parseDouble(precio_reposicion);
            double Dprecio_alquiler = Double.parseDouble(precio_alquiler);
            int Ifk_idproducto = Integer.parseInt(idproducto);
            try {
                item_producto_combo item = new item_producto_combo();
                item.setC2descripcion(descripcion);
                item.setC3cantidad(Dcant_pagado);
                item.setC4precio_alquiler(Dprecio_alquiler);
                item.setC5precio_reposicion(Dprecio_reposicion);
                item.setC6fk_idproducto(Ifk_idproducto);
                item.setC7fk_idproducto_combo(pcom.getC1idproducto_combo());
                insertar_item_producto_combo(conn, item);
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
                break;
            }

        }
    }
}
