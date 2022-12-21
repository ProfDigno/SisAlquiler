package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.producto_combo;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_producto_combo {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    EvenRender everende = new EvenRender();
    private String mensaje_insert = "PRODUCTO_COMBO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PRODUCTO_COMBO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO producto_combo(idproducto_combo,nombre,descripcion,precio_alquiler,precio_reposicion,descuento,activo) VALUES (?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE producto_combo SET nombre=?,descripcion=?,precio_alquiler=?,precio_reposicion=?,descuento=?,activo=? WHERE idproducto_combo=?;";
    private String sql_select = "select pc.idproducto_combo as id,pc.nombre ,pc.descripcion,\n"
            + "TRIM(to_char(pc.precio_alquiler,'999G999G999')) as alquiler,\n"
            + "TRIM(to_char(pc.descuento,'999G999G999')) as descuento,\n"
            + "TRIM(to_char((pc.precio_alquiler-pc.descuento),'999G999G999')) as combo,\n"
            + "TRIM(to_char(pc.precio_reposicion,'999G999G999')) as reposicion,"
            + "pc.activo as act\n"
            + " from producto_combo pc order by pc.idproducto_combo desc; ";
    private String sql_cargar = "SELECT idproducto_combo,nombre,descripcion,precio_alquiler,precio_reposicion,descuento,activo FROM producto_combo WHERE idproducto_combo=";

    public void insertar_producto_combo(Connection conn, producto_combo prco) {
        prco.setC1idproducto_combo(eveconn.getInt_ultimoID_mas_uno(conn, prco.getTb_producto_combo(), prco.getId_idproducto_combo()));
        String titulo = "insertar_producto_combo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, prco.getC1idproducto_combo());
            pst.setString(2, prco.getC2nombre());
            pst.setString(3, prco.getC3descripcion());
            pst.setDouble(4, prco.getC4precio_alquiler());
            pst.setDouble(5, prco.getC5precio_reposicion());
            pst.setDouble(6, prco.getC6descuento());
            pst.setBoolean(7, prco.getC7activo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + prco.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + prco.toString(), titulo);
        }
    }

    public void update_producto_combo(Connection conn, producto_combo prco) {
        String titulo = "update_producto_combo";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, prco.getC2nombre());
            pst.setString(2, prco.getC3descripcion());
            pst.setDouble(3, prco.getC4precio_alquiler());
            pst.setDouble(4, prco.getC5precio_reposicion());
            pst.setDouble(5, prco.getC6descuento());
            pst.setBoolean(6, prco.getC7activo());
            pst.setInt(7, prco.getC1idproducto_combo());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + prco.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + prco.toString(), titulo);
        }
    }

    public void cargar_producto_combo(Connection conn, producto_combo prco, int idproducto_combo) {
        String titulo = "Cargar_producto_combo";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idproducto_combo, titulo);
            if (rs.next()) {
                prco.setC1idproducto_combo(rs.getInt(1));
                prco.setC2nombre(rs.getString(2));
                prco.setC3descripcion(rs.getString(3));
                prco.setC4precio_alquiler(rs.getDouble(4));
                prco.setC5precio_reposicion(rs.getDouble(5));
                prco.setC6descuento(rs.getDouble(6));
                prco.setC7activo(rs.getBoolean(7));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + prco.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + prco.toString(), titulo);
        }
    }

    public void actualizar_tabla_producto_combo(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_producto_combo(tbltabla);
        everende.rendertabla_producto_combo(tbltabla, 7);
    }

    public void ancho_tabla_producto_combo(JTable tbltabla) {
        int Ancho[] = {5, 20, 30, 10, 10, 10, 10, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    /**
     * select pc.idproducto_combo as id,pc.nombre ,
     * TRIM(to_char(pc.precio_alquiler,'999G999G999')) as alquiler,
     * TRIM(to_char(pc.descuento,'999G999G999')) as descuento,
     * TRIM(to_char((pc.precio_alquiler-pc.descuento),'999G999G999')) as pagar
     * from producto_combo pc order by pc.idproducto_combo desc;
     */
    public void actualizar_tabla_producto_combo_alquiler(Connection conn, JTable tbltabla,String filtro) {
        String sql = "select pc.idproducto_combo as id,pc.nombre ,\n"
                + "TRIM(to_char(pc.precio_alquiler,'999G999G999')) as alquiler,\n"
                + "TRIM(to_char(pc.descuento,'999G999G999')) as descuento,\n"
                + "TRIM(to_char((pc.precio_alquiler-pc.descuento),'999G999G999')) as pagar\n"
                + " from producto_combo pc \n"
                + " where pc.activo=true "+filtro
                + " order by pc.idproducto_combo desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_producto_combo_alquiler(tbltabla);
    }

    public void ancho_tabla_producto_combo_alquiler(JTable tbltabla) {
        int Ancho[] = {5, 50, 15, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
