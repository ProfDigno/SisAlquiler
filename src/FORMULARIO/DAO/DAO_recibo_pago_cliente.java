package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.recibo_pago_cliente;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_recibo_pago_cliente {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    String for_fec_bar=evefec.getFor_fec_barra();
    private String mensaje_insert = "RECIBO_PAGO_CLIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RECIBO_PAGO_CLIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO recibo_pago_cliente(idrecibo_pago_cliente,fecha_emision,descripcion,"
            + "monto_recibo_pago,monto_letra,estado,fk_idcliente,fk_idusuario,"
            + "forma_pago,monto_recibo_efectivo,monto_recibo_tarjeta,monto_recibo_transferencia,fk_idventa_alquiler,"
            + "fecha_recibo) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE recibo_pago_cliente SET fecha_emision=?,descripcion=?,monto_recibo_pago=?,monto_letra=?,estado=?,fk_idcliente=?,fk_idusuario=? WHERE idrecibo_pago_cliente=?;";
    private String sql_cargar = "SELECT idrecibo_pago_cliente,fecha_emision,descripcion,monto_recibo_pago,monto_letra,estado,fk_idcliente,fk_idusuario FROM recibo_pago_cliente WHERE idrecibo_pago_cliente=";
    private String sql_select = "SELECT re.idrecibo_pago_cliente as idrecibo,to_char(re.fecha_emision,'"+for_fec_bar+" HH24:MI') as fec_emision,\n"
            + "re.descripcion,TRIM(to_char(re.monto_recibo_pago,'999G999G999')) as monto,re.estado,fi.nombre\n"
            + "FROM recibo_pago_cliente re,cliente fi \n"
            + "where re.fk_idcliente=fi.idcliente\n"
            + "order by 1 desc;";
    private String sql_anular_venta = "UPDATE recibo_pago_cliente SET estado=?,"
            + "monto_recibo_pago=?,monto_recibo_efectivo=?,"
            + "monto_recibo_tarjeta=?,monto_recibo_transferencia=? "
            + "WHERE fk_idventa_alquiler=?;";
    public void insertar_recibo_pago_cliente1(Connection conn, recibo_pago_cliente rbcl) {
        rbcl.setC1idrecibo_pago_cliente(eveconn.getInt_ultimoID_mas_uno(conn, rbcl.getTb_recibo_pago_cliente(), rbcl.getId_idrecibo_pago_cliente()));
        String titulo = "insertar_recibo_pago_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, rbcl.getC1idrecibo_pago_cliente());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, rbcl.getC3descripcion());
            pst.setDouble(4, rbcl.getC4monto_recibo_pago());
            pst.setString(5, rbcl.getC5monto_letra());
            pst.setString(6, rbcl.getC6estado());
            pst.setInt(7, rbcl.getC7fk_idcliente());
            pst.setInt(8, rbcl.getC8fk_idusuario());
            pst.setString(9, rbcl.getC9forma_pago());
            pst.setDouble(10, rbcl.getC10monto_recibo_efectivo());
            pst.setDouble(11, rbcl.getC11monto_recibo_tarjeta());
            pst.setDouble(12, rbcl.getC12monto_recibo_transferencia());
            pst.setInt(13, rbcl.getC13fk_idventa_alquiler());
            pst.setDate(14, evefec.getDate_fecha_cargado(rbcl.getC14fecha_recibo()));
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + rbcl.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + rbcl.toString(), titulo);
        }
    }

    public void update_recibo_pago_cliente(Connection conn, recibo_pago_cliente rbcl) {
        String titulo = "update_recibo_pago_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, rbcl.getC3descripcion());
            pst.setDouble(3, rbcl.getC4monto_recibo_pago());
            pst.setString(4, rbcl.getC5monto_letra());
            pst.setString(5, rbcl.getC6estado());
            pst.setInt(6, rbcl.getC7fk_idcliente());
            pst.setInt(7, rbcl.getC8fk_idusuario());
            pst.setInt(8, rbcl.getC1idrecibo_pago_cliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + rbcl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + rbcl.toString(), titulo);
        }
    }

    public void cargar_recibo_pago_cliente(Connection conn, recibo_pago_cliente rbcl, int id) {
        String titulo = "Cargar_recibo_pago_cliente";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                rbcl.setC1idrecibo_pago_cliente(rs.getInt(1));
                rbcl.setC2fecha_emision(rs.getString(2));
                rbcl.setC3descripcion(rs.getString(3));
                rbcl.setC4monto_recibo_pago(rs.getDouble(4));
                rbcl.setC5monto_letra(rs.getString(5));
                rbcl.setC6estado(rs.getString(6));
                rbcl.setC7fk_idcliente(rs.getInt(7));
                rbcl.setC8fk_idusuario(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rbcl.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rbcl.toString(), titulo);
        }
    }

    public void actualizar_tabla_recibo_pago_cliente(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_recibo_pago_cliente(tbltabla);
    }

    public void ancho_tabla_recibo_pago_cliente(JTable tbltabla) {
        int Ancho[] = {10, 15, 30, 10, 10, 25};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_recibo_pago_cliente_filtro(Connection conn, JTable tbltabla, String filtro) {
        String sql = "select rc.fk_idventa_alquiler as oidva,rc.idrecibo_pago_cliente as idrc,\n"
                + "to_char(rc.fecha_recibo,'"+for_fec_bar+"') as fecha,\n"
                + "c.nombre as cliente,\n"
                + "te.nombre as evento,\n"
                + "case \n"
                + "when rc.forma_pago='TRANSFERENCIA' \n"
                + "then (select b.nombre from transaccion_banco tb,dato_banco db,banco b  \n"//b.nombre
                + "where tb.fk_iddato_banco=db.iddato_banco and db.fk_idbanco=b.idbanco  \n"
                + "and tb.fk_idrecibo_pago_cliente=rc.idrecibo_pago_cliente limit 1)\n"
                + "else '------' end as banco,\n"
                + "case \n"
                + "when rc.forma_pago='TRANSFERENCIA' \n"
                + "then (select tb.nro_transaccion  from transaccion_banco tb,dato_banco db,banco b  \n" //tb.nro_transaccion
                + "where tb.fk_iddato_banco=db.iddato_banco and db.fk_idbanco=b.idbanco  \n"
                + "and tb.fk_idrecibo_pago_cliente=rc.idrecibo_pago_cliente limit 1)\n"
                + "else '------' end as referencia,\n"
                + "TRIM(to_char(rc.monto_recibo_efectivo,'999G999G999')) as efectivo,\n"
                + "TRIM(to_char(rc.monto_recibo_tarjeta,'999G999G999')) as tarjeta,\n"
                + "TRIM(to_char(rc.monto_recibo_transferencia,'999G999G999')) as transfer,\n"
                + "rc.monto_recibo_efectivo as oefectivo,\n"
                + "rc.monto_recibo_tarjeta as otarjeta,\n"
                + "rc.monto_recibo_transferencia as otransfer\n"
                + "from recibo_pago_cliente rc,cliente c,venta_alquiler va,tipo_evento te  \n"
                + "where rc.fk_idcliente=c.idcliente \n"
                + "and rc.fk_idventa_alquiler=va.idventa_alquiler \n"
                + "and va.fk_idtipo_evento=te.idtipo_evento \n" + filtro
                + " order by rc.idrecibo_pago_cliente desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_recibo_pago_cliente_filtro(tbltabla);
    }

    public void ancho_tabla_recibo_pago_cliente_filtro(JTable tbltabla) {
        int Ancho[] = {3, 3, 10, 15, 15, 10, 10, 8, 8, 8, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
//        evejt.ocultar_columna(tbltabla, 0);
        evejt.ocultar_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 11);
        evejt.ocultar_columna(tbltabla, 12);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.alinear_derecha_columna(tbltabla, 9);
    }

    public void imprimir_filtro_recibo_pago_cliente(Connection conn, String filtro) {
        String sql = "select rc.fk_idventa_alquiler as idva,rc.idrecibo_pago_cliente as idrc,\n"
                + "to_char(rc.fecha_recibo,'"+for_fec_bar+"') as fecha,\n"
                + "c.nombre as cliente,\n"
                + "te.nombre as evento,\n"
                + "case \n"
                + "when rc.forma_pago='TRANSFERENCIA' \n"
                + "then (select b.nombre  from transaccion_banco tb,dato_banco db,banco b  \n" //b.nombre
                + "where tb.fk_iddato_banco=db.iddato_banco and db.fk_idbanco=b.idbanco  \n"
                + "and tb.fk_idrecibo_pago_cliente=rc.idrecibo_pago_cliente limit 1)\n"
                + "else '------' end as banco,\n"
                + "case \n"
                + "when rc.forma_pago='TRANSFERENCIA' \n"
                + "then (select tb.nro_transaccion  from transaccion_banco tb,dato_banco db,banco b  \n"//tb.nro_transaccion
                + "where tb.fk_iddato_banco=db.iddato_banco and db.fk_idbanco=b.idbanco  \n"
                + "and tb.fk_idrecibo_pago_cliente=rc.idrecibo_pago_cliente limit 1)\n"
                + "else '------' end as referencia,\n"
                + "rc.monto_recibo_efectivo as efectivo,\n"
                + "rc.monto_recibo_tarjeta as tarjeta,\n"
                + "rc.monto_recibo_transferencia as transfer,\n"
                + "rc.monto_recibo_pago as pago "
                + "from recibo_pago_cliente rc,cliente c,venta_alquiler va,tipo_evento te  \n"
                + "where rc.fk_idcliente=c.idcliente \n"
                + "and rc.fk_idventa_alquiler=va.idventa_alquiler \n"
                + "and va.fk_idtipo_evento=te.idtipo_evento \n"+filtro
                + " order by rc.idrecibo_pago_cliente desc;";
        String titulonota = "FILTRO RECIBO CLIENTE";
        String direccion = "src/REPORTE/RECIBO/repFiltroReciboCliente.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
    public void update_recibo_pago_cliente_anular_venta(Connection conn, recibo_pago_cliente rbcl) {
        String titulo = "update_recibo_pago_cliente_anular_venta";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular_venta);
            pst.setString(1, rbcl.getC6estado());
            pst.setDouble(2, rbcl.getC4monto_recibo_pago());
            pst.setDouble(3, rbcl.getC10monto_recibo_efectivo());
            pst.setDouble(4, rbcl.getC11monto_recibo_tarjeta());
            pst.setDouble(5, rbcl.getC12monto_recibo_transferencia());
            pst.setInt(6, rbcl.getC13fk_idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular_venta + "\n" + rbcl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_anular_venta + "\n" + rbcl.toString(), titulo);
        }
    }
}
