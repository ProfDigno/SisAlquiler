/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.caja_detalle;
import FORMULARIO.ENTIDAD.venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_caja_detalle {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec=new EvenFecha();
    private String sql_insert = "INSERT INTO public.caja_detalle(\n"
            + "idcaja_detalle, fecha_emision, descripcion, "
            + "monto_venta_efectivo,monto_venta_tarjeta, monto_delivery, \n"
            + "monto_gasto, monto_compra, monto_vale,monto_caja,monto_cierre, id_origen, tabla_origen, \n"
            + "cierre,estado,fk_idusuario,monto_recibo_pago,monto_compra_credito)\n"
            + "    VALUES (?, ?, ?, ?, ?, \n"
            + "            ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?);";
    private String sql_update_gasto="update caja_detalle set monto_gasto=? "
                + "where tabla_origen=? and id_origen=?;";
    private String sql_select="select date(fecha_emision) as fecha,"
                + "TRIM(to_char(sum(monto_venta_efectivo),'999G999G999')) as v_efectivo,"
                + "TRIM(to_char(sum(monto_venta_tarjeta),'999G999G999')) as v_tarjeta,"
                + "TRIM(to_char(sum(monto_delivery),'999G999G999')) as delivery,"
                + "TRIM(to_char(sum(monto_compra),'999G999G999')) as comp_contado,\n" 
                + "TRIM(to_char(sum(monto_compra_credito),'999G999G999')) as comp_credito,\n" 
                + "TRIM(to_char(sum(monto_gasto),'999G999G999')) as gasto,"
                + "TRIM(to_char(sum(monto_vale),'999G999G999')) as vale, "
                + "TRIM(to_char(sum(monto_recibo_pago),'999G999G999')) as rec_pag_compra "
                + "from caja_detalle "
                + "group by 1 order by 1 desc";
    private String sql_anular="update caja_detalle set descripcion=(descripcion||'-(ANULADO)'),"
                + "monto_venta_efectivo=0,monto_venta_tarjeta=0,monto_delivery=0,monto_gasto=0,monto_compra=0,monto_vale=0,\n"
                + "monto_recibo_pago=0,monto_compra_credito=0, "
                + "estado=? "
                + "where tabla_origen=? and id_origen=?;";
    private String sql_forma_pago="update caja_detalle set "
                + "monto_venta_efectivo=?,monto_venta_tarjeta=?,tabla_origen=? "
                + "where tabla_origen=? and id_origen=?;";
    
    private String sql_update_cerrartodo="update caja_detalle set cierre='C' "
                + "where cierre='A' ";
//    private String sql_cargar = "select idcaja_detalle, fecha_emision, descripcion, monto_venta_efectivo,monto_venta_tarjeta, monto_delivery, \n"
//            + "monto_gasto, monto_compra, monto_vale,monto_caja,monto_cierre, id_origen, tabla_origen, \n"
//            + "cierre,estado,fk_idusuario from caja_detalle where idcaja_detalle=";
    public void insertar_caja_detalle1(Connection conn, caja_detalle caja) {
        int idcaja_detalle = (eveconn.getInt_ultimoID_mas_uno(conn, caja.getTabla(), caja.getIdtabla()));
        caja.setC1idcaja_detalle(idcaja_detalle);
        String titulo = "insertar_caja_detalle";
        PreparedStatement pst = null;
        caja.setC14cierre("A");
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, caja.getC1idcaja_detalle());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, caja.getC3descripcion());
            pst.setDouble(4, caja.getC4monto_venta_efectivo());
            pst.setDouble(5, caja.getC5monto_venta_tarjeta());
            pst.setDouble(6, caja.getC6monto_delivery());
            pst.setDouble(7, caja.getC7monto_gasto());
            pst.setDouble(8, caja.getC8monto_compra());
            pst.setDouble(9, caja.getC9monto_vale());
            pst.setDouble(10,caja.getC10monto_caja());
            pst.setDouble(11,caja.getC11monto_cierre());
            pst.setInt(12, caja.getC12id_origen());
            pst.setString(13, caja.getC13tabla_origen());
            pst.setString(14,caja.getC14cierre());
            pst.setString(15,caja.getC15estado());
            pst.setInt(16, caja.getC16fk_idusuario());
            pst.setDouble(17,caja.getC17monto_recibo_pago());
            pst.setDouble(18,caja.getC18monto_compra_credito());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_insert + "\n" + caja.toString(), titulo);
        }
    }
    public void cargar_caja_detalle(caja_detalle caja,String tabla_origen, int id_origen) {
        String titulo = "cargar_caja_detalle";
        String sql_cargar = "select idcaja_detalle, fecha_emision, descripcion, monto_venta_efectivo,monto_venta_tarjeta, monto_delivery, \n"
            + "monto_gasto, monto_compra, monto_vale,monto_caja,monto_cierre, id_origen, tabla_origen, \n"
            + "cierre,estado,fk_idusuario,monto_recibo_pago,monto_compra_credito from caja_detalle where tabla_origen='"+tabla_origen+"' and id_origen='"+id_origen+"';";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar, titulo);
            if (rs.next()) {
                caja.setC1idcaja_detalle(rs.getInt(1));
                caja.setC2fecha_emision(rs.getString(2));
                caja.setC3descripcion1(rs.getString(3));
                caja.setC4monto_venta_efectivo(rs.getDouble(4));
                caja.setC5monto_venta_tarjeta(rs.getDouble(5));
                caja.setC6monto_delivery(rs.getDouble(6));
                caja.setC7monto_gasto(rs.getDouble(7));
                caja.setC8monto_compra(rs.getDouble(8));
                caja.setC9monto_vale(rs.getDouble(9));
                caja.setC10monto_caja(rs.getDouble(10));
                caja.setC11monto_cierre(rs.getDouble(11));
                caja.setC12id_origen(rs.getInt(12));
                caja.setC13tabla_origen(rs.getString(13));
                caja.setC14cierre(rs.getString(14));
                caja.setC15estado(rs.getString(15));
                caja.setC16fk_idusuario(rs.getInt(16));
                caja.setC17monto_recibo_pago(rs.getDouble(17));
                caja.setC18monto_compra_credito(rs.getDouble(18));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + caja.toString(), titulo);
        }
    }
    public void update_caja_detalle_gasto(Connection conn, caja_detalle caja) {
        String titulo = "update_caja_detalle_gasto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_gasto);
            pst.setDouble(1, caja.getC7monto_gasto());
            pst.setString(2, caja.getC13tabla_origen());
            pst.setInt(3, caja.getC12id_origen());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_gasto + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_update_gasto + "\n" + caja.toString(), titulo);
        }
    }
    public void anular_caja_detalle(Connection conn, caja_detalle caja){
        String titulo = "anular_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setString(1, caja.getC15estado());
            pst.setString(2, caja.getC13tabla_origen());
            pst.setInt(3, caja.getC12id_origen());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_anular + "\n" + caja.toString(), titulo);
        }
    }
    public void update_forma_pago_caja_detalle(Connection conn, caja_detalle caja){
        String titulo = "update_forma_pago_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_forma_pago);
            pst.setDouble(1, caja.getC4monto_venta_efectivo());
            pst.setDouble(2, caja.getC5monto_venta_tarjeta());
            pst.setString(3, caja.getC13tabla_origen_update());
            pst.setString(4, caja.getC13tabla_origen());
            pst.setInt(5, caja.getC12id_origen());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_forma_pago + "\n" + caja.toString(), titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_forma_pago + "\n" + caja.toString(), titulo);
        }
    }
    public void update_caja_detalle_CERRARTODO(Connection conn){
        String titulo = "anular_caja_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update_cerrartodo);
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update_cerrartodo + "\n", titulo);
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql_update_cerrartodo + "\n", titulo);
        }
    }
    public void actualizar_tabla_caja_detalle(Connection conn, JTable tblcaja_resumen) {
        eveconn.Select_cargar_jtable(conn, sql_select, tblcaja_resumen);
    }

    public void ancho_tabla_cliente(JTable tbltabla) {
        int Ancho[] = {10,60,15,15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public void actualizar_tabla_grafico_caja_detalle(Connection conn,JTable tblgrafico_lavado,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,\n"
                + "TRIM(to_char(sum(monto_venta_efectivo),'999G999G999')) as ingreso,\n"
                + "TRIM(to_char(sum(monto_gasto+monto_vale+monto_compra),'999G999G999')) as egreso \n"
                + "from caja_detalle \n"
                + "where  "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblgrafico_lavado);
    }
    public void actualizar_tabla_grafico_caja_detalle_venta(Connection conn,JTable tblcaja_venta,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_venta_efectivo),'999G999G999')) as venta\n"
                + "from caja_detalle \n"
                + "where  monto_venta_efectivo>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_venta);
    }
    public void actualizar_tabla_grafico_caja_detalle_gasto(Connection conn,JTable tblcaja_gasto,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_gasto),'999G999G999')) as gasto\n"
                + "from caja_detalle \n"
                + "where  monto_gasto>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_gasto);
    }
    public void actualizar_tabla_grafico_caja_detalle_vale(Connection conn,JTable tblcaja_vale,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_vale),'999G999G999')) as vale\n"
                + "from caja_detalle \n"
                + "where  monto_vale>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_vale);
    }
    public void actualizar_tabla_grafico_caja_detalle_delivery(Connection conn,JTable tblcaja_vale,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_delivery),'999G999G999')) as delivery\n"
                + "from caja_detalle \n"
                + "where  monto_delivery>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_vale);
    }
    public void actualizar_tabla_grafico_caja_detalle_compra_ins(Connection conn,JTable tblcaja_vale,String campo_fecha,String filtro_fecha) {
        String sql = "select "+campo_fecha+" as FECHA,count(*) as cant,\n"
                + "TRIM(to_char(sum(monto_compra),'999G999G999')) as compra\n"
                + "from caja_detalle \n"
                + "where  monto_compra>0 and "+filtro_fecha
                + " group by 1 order by 1 asc";
        eveconn.Select_cargar_jtable(conn, sql, tblcaja_vale);
    }
}
