package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.app_pedido;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_app_pedido {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    String for_fec_bar = evefec.getFor_fec_barra();
    private String mensaje_insert = "APP_PEDIDO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "APP_PEDIDO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO app_pedido(idventa_alquiler,fecha_creado,"
            + "fec_evento,fk_idcliente,cliente,direccion,telefono,ruc,"
            + "observacion,estado,descuento_gral,pago_parcial,es_eliminado) "
            + "(SELECT ?,?,?,?,?,?,?,?,?,?,?,?,? WHERE NOT EXISTS "
            + "(SELECT 1 FROM app_pedido WHERE idventa_alquiler=? ));";
    private String sql_update = "UPDATE app_pedido SET fecha_creado=?,fec_evento=?,"
            + "fk_idcliente=?,cliente=?,direccion=?,telefono=?,ruc=?,"
            + "observacion=?,descuento_gral=?,pago_parcial=?,es_eliminado=? "
            + "WHERE idventa_alquiler=?  ;";
    private String sql_select = "SELECT idventa_alquiler,fecha_creado,"
            + "fec_evento,fk_idcliente,cliente,direccion,telefono,ruc,"
            + "observacion,estado FROM app_pedido order by 1 desc;";
    private String sql_update_estado = "UPDATE app_pedido SET estado=? WHERE idventa_alquiler=?;";

    public void insertar_app_pedido(Connection conn, app_pedido ap) {
        String titulo = "insertar_app_pedido";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setString(1, ap.getC1idventa_alquiler());
            pst.setTimestamp(2, evefec.getTimestamp_fecha_for_date(ap.getC2fecha_creado()));
            pst.setDate(3, evefec.getDate_fecha_cargado_DATE(ap.getC3fec_evento()));
            pst.setInt(4, ap.getC4fk_idcliente());
            pst.setString(5, ap.getC5cliente());
            pst.setString(6, ap.getC6direccion());
            pst.setString(7, ap.getC7telefono());
            pst.setString(8, ap.getC8ruc());
            pst.setString(9, ap.getC9observacion());
            pst.setString(10, ap.getC10estado());
            pst.setDouble(11, ap.getC11descuento_gral());
            pst.setDouble(12, ap.getC12pago_parcial());
            pst.setBoolean(13, ap.getC13es_eliminado());
            pst.setString(14, ap.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ap.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ap.toString(), titulo);
        }
    }

    public void update_app_pedido(Connection conn, app_pedido ap) {
        String titulo = "update_app_pedido";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_fecha_for_date(ap.getC2fecha_creado()));
            pst.setDate(2, evefec.getDate_fecha_cargado_DATE(ap.getC3fec_evento()));
            pst.setInt(3, ap.getC4fk_idcliente());
            pst.setString(4, ap.getC5cliente());
            pst.setString(5, ap.getC6direccion());
            pst.setString(6, ap.getC7telefono());
            pst.setString(7, ap.getC8ruc());
            pst.setString(8, ap.getC9observacion());
            pst.setDouble(9, ap.getC11descuento_gral());
            pst.setDouble(10, ap.getC12pago_parcial());
            pst.setBoolean(11, ap.getC13es_eliminado());
            pst.setString(12, ap.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ap.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ap.toString(), titulo);
        }
    }

    public void cargar_app_pedido(Connection conn, app_pedido ap, String idventa_alquiler) {
        String sql_cargar = "SELECT idventa_alquiler,\n"
                + "TRIM(to_char(fecha_creado,'" + for_fec_bar + "')) as fecha_creado,\n"
                + "TRIM(to_char(fec_evento,'" + for_fec_bar + "')) as fec_evento,\n"
                + "fk_idcliente,cliente,direccion,telefono,ruc,observacion,estado,descuento_gral,pago_parcial \n"
                + "FROM app_pedido \n"
                + "WHERE idventa_alquiler='" + idventa_alquiler + "';";
        String titulo = "Cargar_app_pedido";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar, titulo);
            if (rs.next()) {
                ap.setC1idventa_alquiler(rs.getString(1));
                ap.setC2fecha_creado(rs.getString(2));
                ap.setC3fec_evento(rs.getString(3));
                ap.setC4fk_idcliente(rs.getInt(4));
                ap.setC5cliente(rs.getString(5));
                ap.setC6direccion(rs.getString(6));
                ap.setC7telefono(rs.getString(7));
                ap.setC8ruc(rs.getString(8));
                ap.setC9observacion(rs.getString(9));
                ap.setC10estado(rs.getString(10));
                ap.setC11descuento_gral(rs.getDouble(11));
                ap.setC12pago_parcial(rs.getDouble(12));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ap.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ap.toString(), titulo);
        }
    }

    public void actualizar_tabla_app_pedido(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_app_pedido(tbltabla);
    }

    public void ancho_tabla_app_pedido(JTable tbltabla) {
        int Ancho[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void update_app_pedido_estado(Connection conn, app_pedido ap) {
        String titulo = "update_app_pedido_estado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_estado);
            pst.setString(1, ap.getC10estado());
            pst.setString(2, ap.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_estado + "\n" + ap.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update_estado + "\n" + ap.toString(), titulo);
        }
    }
    public void update_app_pedido_eliminar_todo(Connection conn){
        String sql="update app_pedido set es_eliminado=true "
                + "where date(fec_evento)>=date(current_date);";
        eveconn.SQL_execute_libre(conn, sql);
    }
}
