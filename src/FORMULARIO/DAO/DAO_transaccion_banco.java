package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.transaccion_banco;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_transaccion_banco {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    String for_fec_bar=evefec.getFor_fec_barra();
    private String mensaje_insert = "TRANSACCION_BANCO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "TRANSACCION_BANCO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO transaccion_banco(idtransaccion_banco,fecha_creado,"
            + "nro_transaccion,monto,observacion,concepto,"
            + "fk_iddato_banco,fk_idrecibo_pago_cliente,fecha_transaccion) "
            + "VALUES (?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE transaccion_banco SET fecha_creado=?,nro_transaccion=?,monto=?,"
            + "observacion=?,concepto=?,fk_iddato_banco=?,"
            + "fk_idrecibo_pago_cliente=? WHERE idtransaccion_banco=?;";
    private String sql_select = "SELECT idtransaccion_banco,fecha_creado,nro_transaccion,monto,"
            + "observacion,concepto,fk_iddato_banco,"
            + "fk_idrecibo_pago_cliente "
            + "FROM transaccion_banco order by 1 desc;";
    private String sql_cargar = "SELECT idtransaccion_banco,fecha_creado,nro_transaccion,monto,"
            + "observacion,concepto,fk_iddato_banco,"
            + "fk_idrecibo_pago_cliente "
            + "FROM transaccion_banco WHERE idtransaccion_banco=";

    public void insertar_transaccion_banco(Connection conn, transaccion_banco trba) {
        trba.setC1idtransaccion_banco(eveconn.getInt_ultimoID_mas_uno(conn, trba.getTb_transaccion_banco(), trba.getId_idtransaccion_banco()));
        String titulo = "insertar_transaccion_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, trba.getC1idtransaccion_banco());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, trba.getC3nro_transaccion());
            pst.setDouble(4, trba.getC4monto());
            pst.setString(5, trba.getC5observacion());
            pst.setString(6, trba.getC6concepto());
            pst.setInt(7, trba.getC7fk_iddato_banco2());
            pst.setInt(8, trba.getC8fk_idrecibo_pago_cliente2());
            pst.setDate(9, evefec.getDate_fecha_cargado(trba.getC9fecha_transaccion()));
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + trba.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + trba.toString(), titulo);
        }
    }

    public void update_transaccion_banco(Connection conn, transaccion_banco trba) {
        String titulo = "update_transaccion_banco";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, trba.getC3nro_transaccion());
            pst.setDouble(3, trba.getC4monto());
            pst.setString(4, trba.getC5observacion());
            pst.setString(5, trba.getC6concepto());
            pst.setInt(6, trba.getC7fk_iddato_banco2());
            pst.setInt(7, trba.getC8fk_idrecibo_pago_cliente2());
            pst.setInt(8, trba.getC1idtransaccion_banco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + trba.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + trba.toString(), titulo);
        }
    }

    public void cargar_transaccion_banco(Connection conn, transaccion_banco trba, int idtransaccion_banco) {
        String titulo = "Cargar_transaccion_banco";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idtransaccion_banco, titulo);
            if (rs.next()) {
                trba.setC1idtransaccion_banco(rs.getInt(1));
                trba.setC2fecha_creado(rs.getString(2));
                trba.setC3nro_transaccion(rs.getString(3));
                trba.setC4monto(rs.getDouble(4));
                trba.setC5observacion(rs.getString(5));
                trba.setC6concepto(rs.getString(6));
                trba.setC7fk_iddato_banco2(rs.getInt(7));
                trba.setC8fk_idrecibo_pago_cliente2(rs.getInt(8));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + trba.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + trba.toString(), titulo);
        }
    }

    public void actualizar_tabla_transaccion_banco(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_transaccion_banco(tbltabla);
    }

    public void ancho_tabla_transaccion_banco(JTable tbltabla) {
        int Ancho[] = {11, 11, 11, 11, 11, 11, 11, 11};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_transaccion_banco_filtro(Connection conn, JTable tbltabla, String filtro) {
        String sql = "select \n"
                + "rpc.fk_idventa_alquiler as idva,\n"
                + "tb.idtransaccion_banco as idtb,\n"
                + "to_char(tb.fecha_transaccion,'"+for_fec_bar+"') as fecha,\n"
                + "b.nombre as banco,db.nro_cuenta as cuenta,\n"
                + "c.nombre as cliente,te.nombre as evento,\n"
                + "tb.nro_transaccion as referencia,\n"
                + "TRIM(to_char(tb.monto,'999G999G999'))  as monto,\n"
                + "tb.monto as omonto\n"
                + "from banco b,dato_banco db,transaccion_banco tb,\n"
                + "recibo_pago_cliente rpc,cliente c,venta_alquiler va,tipo_evento te  \n"
                + "where b.idbanco=db.fk_idbanco \n"
                + "and db.iddato_banco=tb.fk_iddato_banco \n"
                + "and tb.fk_idrecibo_pago_cliente=rpc.idrecibo_pago_cliente \n"
                + "and rpc.fk_idcliente=c.idcliente \n"
                + "and rpc.fk_idventa_alquiler=va.idventa_alquiler \n"
                + "and va.fk_idtipo_evento=te.idtipo_evento \n" + filtro
                + " order by date(tb.fecha_transaccion) desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_transaccion_banco_filtro(tbltabla);
    }

    public void ancho_tabla_transaccion_banco_filtro(JTable tbltabla) {
        int Ancho[] = {5, 5, 10, 10, 10, 20, 20, 10, 8, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 9);
    }

    public void imprimir_filtro_transaccion_banco(Connection conn, String filtro) {
        String sql = "select \n"
                + "rpc.fk_idventa_alquiler as idva,\n"
                + "tb.idtransaccion_banco as idtb,\n"
                + "to_char(tb.fecha_transaccion,'"+for_fec_bar+"') as fecha,\n"
                + "b.nombre as banco,db.nro_cuenta as cuenta,\n"
                + "c.nombre as cliente,te.nombre as evento,\n"
                + "tb.nro_transaccion as referencia,\n"
                + "tb.monto as monto\n"
                + "from banco b,dato_banco db,transaccion_banco tb,\n"
                + "recibo_pago_cliente rpc,cliente c,venta_alquiler va,tipo_evento te  \n"
                + "where b.idbanco=db.fk_idbanco \n"
                + "and db.iddato_banco=tb.fk_iddato_banco \n"
                + "and tb.fk_idrecibo_pago_cliente=rpc.idrecibo_pago_cliente \n"
                + "and rpc.fk_idcliente=c.idcliente \n"
                + "and rpc.fk_idventa_alquiler=va.idventa_alquiler \n"
                + "and va.fk_idtipo_evento=te.idtipo_evento \n"+filtro
                + " order by b.nombre desc,date(tb.fecha_transaccion) desc;";
        String titulonota = "FILTRO TRANSACCION BANCO";
        String direccion = "src/REPORTE/BANCO/repFiltroTransaccionBanco.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
