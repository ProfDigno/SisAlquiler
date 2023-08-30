package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.venta_alquiler;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DAO_venta_alquiler {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    String for_fec_bar = evefec.getFor_fec_barra();
    private String mensaje_insert = "VENTA_ALQUILER GUARDADO CORRECTAMENTE";
    private String mensaje_update = "VENTA_ALQUILER MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO venta_alquiler(idventa_alquiler,fecha_creado,"
            + "fecha_retirado_previsto,fecha_retirado_real,fecha_devolusion_previsto,fecha_devolusion_real,"
            + "monto_total,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_delivery,"
            + "forma_pago,condicion,alquiler_retirado,alquiler_devolusion,direccion_alquiler,observacion,estado,"
            + "fk_idcliente,fk_identregador,monto_alquilado_credito,monto_alquilado_reservado,monto_descuento,"
            + "monto_sena,monto_letra,fk_idtipo_evento,monto_pagado) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE venta_alquiler SET fecha_creado=?,fecha_retirado_previsto=?,fecha_retirado_real=?,fecha_devolusion_previsto=?,fecha_devolusion_real=?,monto_total=?,monto_alquilado_efectivo=?,monto_alquilado_tarjeta=?,monto_alquilado_transferencia=?,monto_delivery=?,forma_pago=?,condicion=?,alquiler_retirado=?,alquiler_devolusion=?,direccion_alquiler=?,observacion=?,estado=?,fk_idcliente=?,fk_identregador=? WHERE idventa_alquiler=?;";
    private String sql_cargar = "SELECT idventa_alquiler,fecha_creado,fecha_retirado_previsto,fecha_retirado_real,fecha_devolusion_previsto,fecha_devolusion_real,"
            + "monto_total,monto_alquilado_efectivo,monto_alquilado_tarjeta,monto_alquilado_transferencia,monto_delivery,"
            + "forma_pago,condicion,alquiler_retirado,alquiler_devolusion,direccion_alquiler,observacion,estado,"
            + "fk_idcliente,fk_identregador,monto_alquilado_credito,"
            + "TRIM(to_char(fecha_retirado_real,'" + for_fec_bar + "')) as fecha_retirado,"
            + "TRIM(to_char(fecha_retirado_real,'HH24')) as hora_retirado,"
            + "TRIM(to_char(fecha_retirado_real,'MI')) as min_retirado,"
            + "TRIM(to_char(fecha_devolusion_real,'" + for_fec_bar + "')) as fecha_devolusion,"
            + "TRIM(to_char(fecha_devolusion_real,'HH24')) as hora_devolusion,"
            + "TRIM(to_char(fecha_devolusion_real,'MI')) as min_devolusion,"
            + "monto_alquilado_reservado,monto_descuento,monto_sena,monto_letra,fk_idtipo_evento,monto_pagado "
            + "FROM venta_alquiler WHERE idventa_alquiler=";
    private String sql_estado_anular = "update venta_alquiler set estado=?,monto_sena=?,monto_pagado=? where idventa_alquiler=?;";
    private String sql_estado_final = "update venta_alquiler set estado=? where idventa_alquiler=?;";
    private String sql_alquilado = "update venta_alquiler set fecha_retirado_real=?,alquiler_retirado=?,estado=? where idventa_alquiler=?;";
    private String sql_devolusion_alq = "update venta_alquiler set fecha_devolusion_real=?,alquiler_devolusion=?,estado=? where idventa_alquiler=?;";

    private String sql_pago = "update venta_alquiler set monto_pagado=(monto_pagado+?) where idventa_alquiler=?;";

    public void insertar_venta_alquiler(Connection conn, venta_alquiler vealq) {
        vealq.setC1idventa_alquiler(eveconn.getInt_ultimoID_mas_uno(conn, vealq.getTb_venta_alquiler(), vealq.getId_idventa_alquiler()));
        String titulo = "insertar_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, vealq.getC1idventa_alquiler());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_fecha_cargado(vealq.getC3fecha_retirado_previsto()));
            pst.setTimestamp(4, evefec.getTimestamp_fecha_cargado(vealq.getC4fecha_retirado_real()));
            pst.setTimestamp(5, evefec.getTimestamp_fecha_cargado(vealq.getC5fecha_devolusion_previsto()));
            pst.setTimestamp(6, evefec.getTimestamp_fecha_cargado(vealq.getC6fecha_devolusion_real()));
            pst.setDouble(7, vealq.getC7monto_total());
            pst.setDouble(8, vealq.getC8monto_alquilado_efectivo());
            pst.setDouble(9, vealq.getC9monto_alquilado_tarjeta());
            pst.setDouble(10, vealq.getC10monto_alquilado_transferencia());
            pst.setDouble(11, vealq.getC11monto_delivery());
            pst.setString(12, vealq.getC12forma_pago());
            pst.setString(13, vealq.getC13condicion());
            pst.setBoolean(14, vealq.getC14alquiler_retirado());
            pst.setBoolean(15, vealq.getC15alquiler_devolusion());
            pst.setString(16, vealq.getC16direccion_alquiler());
            pst.setString(17, vealq.getC17observacion());
            pst.setString(18, vealq.getC18estado());
            pst.setInt(19, vealq.getC19fk_idcliente());
            pst.setInt(20, vealq.getC20fk_identregador());
            pst.setDouble(21, vealq.getC21monto_alquilado_credito());
            pst.setDouble(22, vealq.getC28monto_alquilado_reservado());
            pst.setDouble(23, vealq.getC29monto_descuento());
            pst.setDouble(24, vealq.getC30monto_sena());
            pst.setString(25, vealq.getC31monto_letra());
            pst.setInt(26, vealq.getC32fk_idtipo_evento());
            pst.setDouble(27, vealq.getC33monto_pagado());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + vealq.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setDouble(6, vealq.getC7monto_total());
            pst.setDouble(7, vealq.getC8monto_alquilado_efectivo());
            pst.setDouble(8, vealq.getC9monto_alquilado_tarjeta());
            pst.setDouble(9, vealq.getC10monto_alquilado_transferencia());
            pst.setDouble(10, vealq.getC11monto_delivery());
            pst.setString(11, vealq.getC12forma_pago());
            pst.setString(12, vealq.getC13condicion());
            pst.setBoolean(13, vealq.getC14alquiler_retirado());
            pst.setBoolean(14, vealq.getC15alquiler_devolusion());
            pst.setString(15, vealq.getC16direccion_alquiler());
            pst.setString(16, vealq.getC17observacion());
            pst.setString(17, vealq.getC18estado());
            pst.setInt(18, vealq.getC19fk_idcliente());
            pst.setInt(19, vealq.getC20fk_identregador());
            pst.setInt(20, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + vealq.toString(), titulo);
        }
    }

    public void cargar_venta_alquiler(Connection conn, venta_alquiler vealq, int idventa_alquiler) {
        String titulo = "Cargar_venta_alquiler";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idventa_alquiler, titulo);
            if (rs.next()) {
                vealq.setC1idventa_alquiler(rs.getInt(1));
                vealq.setC2fecha_creado(rs.getString(2));
                vealq.setC3fecha_retirado_previsto(rs.getString(3));
                vealq.setC4fecha_retirado_real(rs.getString(4));
                vealq.setC5fecha_devolusion_previsto(rs.getString(5));
                vealq.setC6fecha_devolusion_real(rs.getString(6));
                vealq.setC7monto_total(rs.getDouble(7));
                vealq.setC8monto_alquilado_efectivo(rs.getDouble(8));
                vealq.setC9monto_alquilado_tarjeta(rs.getDouble(9));
                vealq.setC10monto_alquilado_transferencia(rs.getDouble(10));
                vealq.setC11monto_delivery(rs.getDouble(11));
                vealq.setC12forma_pago(rs.getString(12));
                vealq.setC13condicion(rs.getString(13));
                vealq.setC14alquiler_retirado(rs.getBoolean(14));
                vealq.setC15alquiler_devolusion(rs.getBoolean(15));
                vealq.setC16direccion_alquiler(rs.getString(16));
                vealq.setC17observacion(rs.getString(17));
                vealq.setC18estado(rs.getString(18));
                vealq.setC19fk_idcliente(rs.getInt(19));
                vealq.setC20fk_identregador(rs.getInt(20));
                vealq.setC21monto_alquilado_credito(rs.getDouble(21));
                vealq.setC22fecha_retirado(rs.getString(22));
                vealq.setC23hora_retirado(rs.getString(23));
                vealq.setC24min_retirado(rs.getString(24));
                vealq.setC25fecha_devolusion(rs.getString(25));
                vealq.setC26hora_devolusion(rs.getString(26));
                vealq.setC27min_devolusion(rs.getString(27));
                vealq.setC28monto_alquilado_reservado(rs.getDouble(28));
                vealq.setC29monto_descuento(rs.getDouble(29));
                vealq.setC30monto_sena(rs.getDouble(30));
                vealq.setC31monto_letra(rs.getString(31));
                vealq.setC32fk_idtipo_evento(rs.getInt(32));
                vealq.setC33monto_pagado(rs.getDouble(33));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + vealq.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_estado_anular(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_anular";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_estado_anular);
            pst.setString(1, vealq.getC18estado());
            pst.setDouble(2, vealq.getC30monto_sena());
            pst.setDouble(3, vealq.getC33monto_pagado());
            pst.setInt(4, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_estado_anular + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_estado_anular + "\n" + vealq.toString(), titulo);
        }
    }
    public void update_venta_alquiler_estado_finalizar(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_estado_finalizar";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_estado_final);
            pst.setString(1, vealq.getC18estado());
            pst.setInt(2, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_estado_final + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_estado_final + "\n" + vealq.toString(), titulo);
        }
    }
    public void update_venta_alquiler_monto_pago(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_monto_pago";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_pago);
            pst.setDouble(1, vealq.getC33monto_pagado());
            pst.setInt(2, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_pago + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_pago + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_alquilado(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_alquilado";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_alquilado);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, true);
            pst.setString(3, vealq.getC18estado());
            pst.setInt(4, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_alquilado + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_alquilado + "\n" + vealq.toString(), titulo);
        }
    }

    public void update_venta_alquiler_Devolucion(Connection conn, venta_alquiler vealq) {
        String titulo = "update_venta_alquiler_Devolucion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_devolusion_alq);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, true);
            pst.setString(3, vealq.getC18estado());
            pst.setInt(4, vealq.getC1idventa_alquiler());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_devolusion_alq + "\n" + vealq.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_devolusion_alq + "\n" + vealq.toString(), titulo);
        }
    }

//    public void update_venta_alquiler_Finalizar(Connection conn, venta_alquiler vealq) {
//        String titulo = "update_venta_alquiler_Finalizar";
//        PreparedStatement pst = null;
//        try {
//            pst = conn.prepareStatement(sql_finalizar_alq);
//            pst.setString(1, vealq.getC18estado());
//            pst.setInt(2, vealq.getC1idventa_alquiler());
//            pst.execute();
//            pst.close();
//            evemen.Imprimir_serial_sql(sql_finalizar_alq + "\n" + vealq.toString(), titulo);
//            evemen.modificado_correcto(mensaje_update, false);
//        } catch (Exception e) {
//            evemen.mensaje_error(e, sql_finalizar_alq + "\n" + vealq.toString(), titulo);
//        }
//    }
    public void actualizar_tabla_venta_alquiler(Connection conn, JTable tbltabla, String filtro) {
        String sql_select = "select v.idventa_alquiler as idva,\n"
                + "TRIM(to_char(v.fecha_retirado_real,'" + for_fec_bar + " HH24:MI')) as retirado,\n"
                + "TRIM(to_char(v.fecha_devolusion_real,'" + for_fec_bar + " HH24:MI')) as devolusion,\n"
                + "cl.nombre as cliente,v.direccion_alquiler as direccion, \n"
                + "te.nombre as evento,"
                + "TRIM(to_char((v.monto_total),'999G999G999')) as mon_total,\n"
                + "TRIM(to_char((v.monto_descuento),'999G999G999')) as descuento,\n"
                + "TRIM(to_char((v.monto_total-v.monto_descuento),'999G999G999')) as mon_pagar,\n"
                + "TRIM(to_char((v.monto_sena),'999G999G999')) as sena,\n"
                + "TRIM(to_char((v.monto_pagado),'999G999G999')) as pagado,\n"
                + "v.estado,cl.idcliente\n "
                + "from venta_alquiler v,cliente cl,tipo_evento te \n"
                + "where v.fk_idcliente=cl.idcliente\n"
                + "and v.fk_idtipo_evento=te.idtipo_evento " + filtro
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_alquiler(tbltabla);
    }

    public void ancho_tabla_venta_alquiler(JTable tbltabla) {
        int Ancho[] = {4, 9, 9, 15, 15, 11, 6, 6, 6, 6, 6, 9, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.alinear_derecha_columna(tbltabla, 9);
        evejt.alinear_derecha_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 12);
    }

    public double getDouble_suma_venta(Connection conn, String campo, String filtro) {
        double sumaventa = 0;
        String titulo = "getDouble_suma_venta";
        String sql = "select count(*) as cantidad,"
                + "sum(v.monto_alquilado_efectivo) as sumaventa_efectivo,\n"
                + "sum(v.monto_alquilado_tarjeta) as sumaventa_tarjeta, "
                + "sum(v.monto_alquilado_transferencia) as sumaventa_transferencia, "
                + "sum(v.monto_alquilado_credito) as sumaventa_credito, "
                + "sum(v.monto_alquilado_tarjeta+v.monto_alquilado_efectivo+v.monto_alquilado_transferencia+v.monto_alquilado_credito) as sumaventa_total "
                + "from venta_alquiler v,cliente c\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + " " + filtro + "\n"
                + " ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                sumaventa = rs.getDouble(campo);
            }
        } catch (SQLException e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
        return sumaventa;
    }

    public void imprimir_rep_alquiler_todos(Connection conn, String filtro) {
        String sql = "select v.idventa_alquiler as idva,\n"
                + "to_char(v.fecha_retirado_real,'" + for_fec_bar + " HH24:MI') as retirado,\n"
                + "to_char(v.fecha_devolusion_real,'" + for_fec_bar + " HH24:MI') as devolucion,\n"
                + "('('||c.idcliente||')'||c.nombre) as cliente,\n"
                + "v.monto_alquilado_efectivo as efectivo,\n"
                + "v.monto_alquilado_tarjeta as tarjeta,\n"
                + "v.monto_alquilado_transferencia as transfe,\n"
                + "v.monto_alquilado_credito as credito,v.forma_pago as pago,v.estado as estado\n"
                + "from venta_alquiler v,cliente c\n"
                + "where v.fk_idcliente=c.idcliente\n"
                + " " + filtro + "\n"
                + "order by v.idventa_alquiler desc;";
        String titulonota = "ALQUILER TODOS";
        String direccion = "src/REPORTE/ALQUILER/repAlquilerTodos.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_orden_entrega_alquiler(Connection conn, int idventa_alquiler) {
        String em_telefono = "Telef: 061-578.489 – Cel.0973-503.421";
        String em_email = "Email: mercedes_fretes@hotmail.com";
        String em_direccion = "Bo San José – C.D.E. – Py.";
        String em_recomendacion = "La seña podrá realizarse en mi oficina en horario comercial o por Transferencia Bancaria a una de las\n"
                + "cuentas habilitadas. El saldo hasta el dìa del evento.";
        String em_saludo = "Sin otro particular y atenta ante cualquier consulta, le saludo muy cordialmente.";
        String em_propietaria = "Lic. Mercedes Fretes\n"
                + "Propietaria";
        String sql = "select iva.cantidad_total as iv_cant,iva.descripcion as iv_concepto,\n"
                + "iva.precio_alquiler as iv_unitario,\n"
                + "(iva.cantidad_total*iva.precio_alquiler) as iv_subtotal,\n"
                + "va.idventa_alquiler as idva,va.observacion as v_obs,\n"
                + "to_char(va.fecha_creado,'" + for_fec_bar + " HH24:MI') as v_fec_crea,\n"
                + "to_char(va.fecha_devolusion_previsto ,'" + for_fec_bar + " HH24:MI') as v_fec_evento,\n"
                + "va.monto_total as v_alquiler,va.monto_descuento as v_descuento,\n"
                + "(va.monto_total-va.monto_descuento) as v_pagar,va.monto_letra as v_mletra,\n"
                + "va.monto_sena as v_msena,\n"
                + "((100*va.monto_sena)/(va.monto_total-va.monto_descuento)) as v_psena,\n"
                + "((va.monto_total-va.monto_descuento)-va.monto_sena) as v_msaldo,\n"
                + "(100-((100*va.monto_sena)/(va.monto_total-va.monto_descuento))) as v_psaldo,\n"
                + "c.nombre as cl_nombre,c.direccion as cl_direccion,\n"
                + "te.nombre as te_evento,\n"
                + "('" + em_telefono + "') as em_telefono,('" + em_email + "') as em_email,('" + em_direccion + "') as em_direccion,\n"
                + "('" + em_recomendacion + "') as em_recomendacion,('" + em_saludo + "') as em_saludo,('" + em_propietaria + "') as em_propietaria\n"
                + "from venta_alquiler va,item_venta_alquiler iva,cliente c,tipo_evento te  \n"
                + "where va.idventa_alquiler=iva.fk_idventa_alquiler \n"
                + "and va.fk_idcliente=c.idcliente \n"
                + "and va.fk_idtipo_evento=te.idtipo_evento \n"
                + "and iva.precio_alquiler>0\n"
                + "and va.idventa_alquiler=" + idventa_alquiler
                + " order by iva.orden asc;";
        String titulonota = "ORDEN DE ENTREGA";
        String direccion = "src/REPORTE/ALQUILER/repOrdenEntregaAlquiler.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_orden_entrega_detalle(Connection conn, int idventa_alquiler, String filtro) {
        String sql = "select va.idventa_alquiler as idva,\n"
                + "to_char(va.fecha_creado ,'" + for_fec_bar + "') as fec_creado, \n"
                + "case \n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=1 then 'LUNES'\n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=2 then 'MARTES'\n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=3 then 'MIERCOLES'\n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=4 then 'JUEVES'\n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=5 then 'VIERNES'\n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=6 then 'SABADO'\n"
                + "when date_part('isodow', va.fecha_devolusion_previsto)=7 then 'DOMiNGO' \n"
                + "else 'otro' end as dia_semana,"
                + "to_char(va.fecha_devolusion_previsto,'" + for_fec_bar + "') as fec_evento,\n"
                + "va.monto_total as m_total,\n"
                + "va.monto_descuento as m_descuento,"
                + "va.monto_sena as m_adelanto,\n"
                + "(va.monto_total-(va.monto_descuento+va.monto_sena)) as m_pagar,\n"
                + "va.monto_letra as m_letra,\n"
                + "va.observacion as obs,\n"
                + "te.nombre as evento,\n"
                + "cl.nombre as cliente,cl.direccion as direccion,cl.telefono as telefono,\n"
                + "iva.orden as ord,iva.descripcion as descripcion,iva.precio_alquiler as preciouni,\n"
                + "iva.cantidad_total as cant,(iva.precio_alquiler*iva.cantidad_total) as subtotal\n"
                + "from venta_alquiler va,tipo_evento te,cliente cl,item_venta_alquiler iva  \n"
                + "where va.fk_idtipo_evento=te.idtipo_evento \n"
                + "and va.fk_idcliente=cl.idcliente \n"
                + "and va.idventa_alquiler=iva.fk_idventa_alquiler \n"
                + filtro
                + " and va.idventa_alquiler=" + idventa_alquiler
                + " order by iva.orden asc;";
        String titulonota = "ORDEN ENTREGA DETALLE";
        String direccion = "src/REPORTE/ALQUILER/repOrdenEntregaDetalleTriple.jrxml";
        String rutatemp = "ordentriple_" + idventa_alquiler;
        rep.imprimirjasper(conn, sql, titulonota, direccion);
//        rep.imprimirPDF(conn, sql, direccion, rutatemp);
    }

    public void actualizar_tabla_venta_alquiler_en_cliente(Connection conn, JTable tbltabla, int idcliente) {
        String sql_select = "select v.idventa_alquiler as idva,\n"
                + "TRIM(to_char(v.fecha_retirado_real,'" + for_fec_bar + " HH24:MI')) as retirado,\n"
                + "TRIM(to_char(v.fecha_devolusion_real,'" + for_fec_bar + " HH24:MI')) as devolusion,\n"
                + "v.direccion_alquiler as direccion,\n"
                + "TRIM(to_char((v.monto_total-v.monto_descuento),'999G999G999')) as mon_pagar,\n"
                + "TRIM(to_char((v.monto_sena),'999G999G999')) as sena,\n"
                + "TRIM(to_char((v.monto_pagado),'999G999G999')) as pagado,\n"
                + "TRIM(to_char(v.monto_pagado-(v.monto_total-v.monto_descuento),'999G999G999')) as saldo,\n"
                + "v.estado,\n"
                + "case "
                + "when v.estado!='ANULADO' and ((v.monto_total-v.monto_descuento) = v.monto_pagado) then 'SI' "
                + "when v.estado!='ANULADO' and ((v.monto_total-v.monto_descuento) > v.monto_pagado) then 'NO' "
                + "when v.estado='ANULADO' then 'ANU' "
                + " else 'error' end as canc "
                + "from venta_alquiler v,cliente cl,tipo_evento te \n"
                + "where v.fk_idcliente=cl.idcliente\n"
                + "and v.fk_idtipo_evento=te.idtipo_evento \n"
                + "and cl.idcliente=" + idcliente
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_venta_alquiler_en_cliente(tbltabla);
        everende.rendertabla_venta_alquilado_cancelado(tbltabla, 9);
    }

    public void ancho_tabla_venta_alquiler_en_cliente(JTable tbltabla) {
        int Ancho[] = {5, 12, 12, 24, 8, 8, 8, 8, 10, 5};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_venta_alquiler_rep_1(Connection conn, JTable tbltabla, String filtro) {
        String sql = "select va.idventa_alquiler as idva,\n"
                + "TRIM(to_char(va.fecha_retirado_real,'" + for_fec_bar + " HH24:MI')) as fec_alquiler,\n"
                + "TRIM(to_char(va.fecha_devolusion_previsto,'" + for_fec_bar + " HH24:MI')) as fec_evento,\n"
                + "cl.nombre as cliente,\n"
                + "cl.direccion as direccion,\n"
                + "te.nombre as evento,\n"
                + "TRIM(to_char((va.monto_total-va.monto_descuento),'999G999G999')) as total ,\n"
                + "TRIM(to_char(va.monto_pagado,'999G999G999')) as pagado,\n"
                + "TRIM(to_char((va.monto_pagado-(va.monto_total-va.monto_descuento)),'999G999G999')) as saldo,\n"
                + "va.estado,\n"
                + "(va.monto_total-va.monto_descuento) as ototal,\n"
                + "va.monto_pagado as opagado,\n"
                + "(va.monto_pagado-(va.monto_total-va.monto_descuento)) as osaldo\n"
                + "from venta_alquiler va,tipo_evento te,cliente cl  \n"
                + "where va.fk_idtipo_evento=te.idtipo_evento \n"
                + "and va.fk_idcliente=cl.idcliente \n" + filtro
                + " order by va.idventa_alquiler desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_venta_alquiler_rep_1(tbltabla);
        everende.rendertabla_estado_alquilado(tbltabla, 9);
    }

    public void ancho_tabla_venta_alquiler_rep_1(JTable tbltabla) {
        int Ancho[] = {5, 9, 9, 15, 25, 12, 7, 7, 7, 8, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 11);
        evejt.ocultar_columna(tbltabla, 12);
    }

    public void seleccionar_imprimir_venta_alquiler(Connection conn, int idventa_alquiler) {
        seleccion_venta_alquiler(conn, idventa_alquiler);
    }

    public void seleccionar_imprimir_venta_alquiler_tabla(Connection conn, JTable tabla) {
        if (!evejt.getBoolean_validar_select(tabla)) {
            int idventa_alquiler = evejt.getInt_select_id(tabla);
            seleccion_venta_alquiler(conn, idventa_alquiler);
        }
    }

    private void seleccion_venta_alquiler(Connection conn, int idventa_alquiler) {
        Object[] botones = {"ORDEN ENTREGA", "ORDEN ENTREGA CON DETALLE", "ORDEN ENTREGA CLIENTE", "CANCELAR"};
        int eleccion_comando = JOptionPane.showOptionDialog(null, "SELECCIONA UNA PARA IMPRIMIR ",
                "ORDEN DE ENTREGA",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, botones, "TICKET DETALLE");
        if (eleccion_comando == 0) {
            imprimir_orden_entrega_detalle(conn, idventa_alquiler, "and iva.precio_alquiler>0");
        }
        if (eleccion_comando == 1) {
            imprimir_orden_entrega_detalle(conn, idventa_alquiler, "");
        }
        if (eleccion_comando == 2) {
            imprimir_orden_entrega_alquiler(conn, idventa_alquiler);
        }
    }

    public void imprimir_filtro_venta_alquiler_simple(Connection conn, String filtro) {
        String sql = "select va.idventa_alquiler as idva,\n"
                + "date(va.fecha_retirado_real) as fec_alquiler,\n"
                + "date(va.fecha_devolusion_previsto) as fec_evento,\n"
                + "cl.nombre as cliente,\n"
                + "te.nombre as evento,\n"
                + "(va.monto_total-va.monto_descuento) as total ,\n"
                + "va.monto_pagado as pagado,\n"
                + "(va.monto_pagado-(va.monto_total-va.monto_descuento)) as saldo,\n"
                + "va.estado as estado\n"
                + "from venta_alquiler va,tipo_evento te,cliente cl  \n"
                + "where  va.fk_idtipo_evento=te.idtipo_evento \n"
                + "and va.fk_idcliente=cl.idcliente \n" + filtro
                + " order by va.idventa_alquiler desc";
        String titulonota = "FILTRO VENTA ALQUILER SIMPLE";
        String direccion = "src/REPORTE/ALQUILER/repFiltroVentaAlquiler.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
