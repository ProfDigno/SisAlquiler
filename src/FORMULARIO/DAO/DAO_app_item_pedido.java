package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.app_item_pedido;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_app_item_pedido {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "APP_ITEM_PEDIDO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "APP_ITEM_PEDIDO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO app_item_pedido(iditem_venta_alquiler,"
            + "cantidad_total,descripcion,precio_alquiler,precio_descuento,"
            + "es_combo,es_producto,fk_idventa_alquiler,fk_idproducto,fk_idproducto_combo,orden,"
            + "es_vencido,es_eliminado) "
            + "(SELECT ?,?,?,?,?,?,?,?,?,?,?,?,? WHERE NOT EXISTS (SELECT 1 FROM app_item_pedido WHERE iditem_venta_alquiler=?));";
    private String sql_update = "UPDATE app_item_pedido SET "
            + "cantidad_total=?,descripcion=?,precio_alquiler=?,precio_descuento=?,"
            + "es_combo=?,es_producto=?,fk_idventa_alquiler=?,fk_idproducto=?,fk_idproducto_combo=?,orden=?,"
            + "es_vencido=?,es_eliminado=? "
            + "WHERE iditem_venta_alquiler=?;";
    private String sql_select = "SELECT iditem_venta_alquiler,cantidad_total,descripcion,precio_alquiler,precio_descuento,es_combo,es_producto,fk_idventa_alquiler,fk_idproducto,fk_idproducto_combo FROM app_item_pedido order by 1 desc;";
    private String sql_cargar = "SELECT iditem_venta_alquiler,cantidad_total,descripcion,precio_alquiler,precio_descuento,es_combo,es_producto,fk_idventa_alquiler,fk_idproducto,fk_idproducto_combo FROM app_item_pedido WHERE iditem_venta_alquiler=";

    public void insertar_app_item_pedido(Connection conn, app_item_pedido aip) {
//		aip.setC1iditem_venta_alquiler(eveconn.getInt_ultimoID_mas_uno(conn, aip.getTb_app_item_pedido(), aip.getId_iditem_venta_alquiler()));
        String titulo = "insertar_app_item_pedido";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setString(1, aip.getC1iditem_venta_alquiler());
            pst.setInt(2, aip.getC2cantidad_total());
            pst.setString(3, aip.getC3descripcion());
            pst.setDouble(4, aip.getC4precio_alquiler());
            pst.setDouble(5, aip.getC5precio_descuento());
            pst.setBoolean(6, aip.getC6es_combo());
            pst.setBoolean(7, aip.getC7es_producto());
            pst.setString(8, aip.getC8fk_idventa_alquiler());
            pst.setInt(9, aip.getC9fk_idproducto());
            pst.setInt(10, aip.getC10fk_idproducto_combo());
            pst.setInt(11, aip.getC11orden());
            pst.setBoolean(12, aip.getC12es_vencido());
            pst.setBoolean(13, aip.getC13es_eliminado());
            pst.setString(14, aip.getC1iditem_venta_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + aip.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + aip.toString(), titulo);
        }
    }

    public void update_app_item_pedido(Connection conn, app_item_pedido aip) {
        String titulo = "update_app_item_pedido";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, aip.getC2cantidad_total());
            pst.setString(2, aip.getC3descripcion());
            pst.setDouble(3, aip.getC4precio_alquiler());
            pst.setDouble(4, aip.getC5precio_descuento());
            pst.setBoolean(5, aip.getC6es_combo());
            pst.setBoolean(6, aip.getC7es_producto());
            pst.setString(7, aip.getC8fk_idventa_alquiler());
            pst.setInt(8, aip.getC9fk_idproducto());
            pst.setInt(9, aip.getC10fk_idproducto_combo());
            pst.setInt(10, aip.getC11orden());
            pst.setBoolean(11, aip.getC12es_vencido());
            pst.setBoolean(12, aip.getC13es_eliminado());
            pst.setString(13, aip.getC1iditem_venta_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + aip.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + aip.toString(), titulo);
        }
    }

    public void cargar_app_item_pedido(Connection conn, app_item_pedido aip, int iditem_venta_alquiler) {
        String titulo = "Cargar_app_item_pedido";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + iditem_venta_alquiler, titulo);
            if (rs.next()) {
                aip.setC1iditem_venta_alquiler(rs.getString(1));
                aip.setC2cantidad_total(rs.getInt(2));
                aip.setC3descripcion(rs.getString(3));
                aip.setC4precio_alquiler(rs.getDouble(4));
                aip.setC5precio_descuento(rs.getDouble(5));
                aip.setC6es_combo(rs.getBoolean(6));
                aip.setC7es_producto(rs.getBoolean(7));
                aip.setC8fk_idventa_alquiler(rs.getString(8));
                aip.setC9fk_idproducto(rs.getInt(9));
                aip.setC10fk_idproducto_combo(rs.getInt(10));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + aip.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + aip.toString(), titulo);
        }
    }

    public void actualizar_tabla_app_item_pedido(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_app_item_pedido(tbltabla);
    }

    public void ancho_tabla_app_item_pedido(JTable tbltabla) {
        int Ancho[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void update_app_item_pedido_eliminar_todo(Connection conn){
        String sql="update app_item_pedido set es_eliminado=true; ";
//        String sql="update app_item_pedido set es_eliminado=true where es_vencido=false;";
        eveconn.SQL_execute_libre(conn, sql);
    }
}
