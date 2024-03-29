/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.DAO;

import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.EvenConexion;
import Evento.Fecha.EvenFecha;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.gasto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class DAO_gasto {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenRender everende = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    EvenJasperReport rep = new EvenJasperReport();
    String for_fec_bar = evefec.getFor_fec_barra();
    String for_feczona_bar = evefec.getFor_fecZona_barra();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private String mensaje_insert = "GASTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "GASTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO public.gasto(\n"
            + "            idgasto, fecha_emision, descripcion, monto_gasto, estado, fk_idgasto_tipo, \n"
            + "            fk_idusuario)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?);";
    private String sql_update = "UPDATE public.gasto\n"
            + "   SET fecha_emision=?,descripcion=?, monto_gasto=?,  \n"
            + "       fk_idgasto_tipo=?, fk_idusuario=?\n"
            + " WHERE idgasto=?;";
    private String sql_anular = "UPDATE public.gasto\n"
            + "   SET estado=?\n"
            + " WHERE idgasto=?;";
    private String sql_select = "select g.idgasto,to_char(g.fecha_emision,'" + for_fec_bar + "') as fecha,\n"
            + "g.descripcion,gt.nombre as tipo,\n"
            + "TRIM(to_char(g.monto_gasto,'999G999G999')) as monto,\n"
            + "g.estado\n"
            + " from gasto g,gasto_tipo gt\n"
            + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n";
    private String sql_cargar = "select g.descripcion,g.monto_gasto,"
            + "to_char(g.fecha_emision,'" + for_fec_bar + "') as fecha,"
            + "g.estado,"
            + "gt.nombre as tipo,g.fk_idgasto_tipo,g.fk_idusuario "
            + " from gasto g,gasto_tipo gt\n"
            + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n"
            + "and g.idgasto=";
    private String sql_suma_monto = "select sum(g.monto_gasto) as suma "
            + " from gasto g,gasto_tipo gt\n"
            + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n"
            + "and g.estado='EMITIDO'\n";
    private String sql_suma_cant = "select count(*) as cant "
            + " from gasto g,gasto_tipo gt\n"
            + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n"
            + "and g.estado='EMITIDO'\n";

    public void cargar_gasto(gasto gas, JTable tabla) {
        String titulo = "cargar_gasto";
        int id = evejt.getInt_select_id(tabla);
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + id, titulo);
            if (rs.next()) {
                gas.setIdgasto(id);
                gas.setFecha_emision(rs.getString("fecha"));
                gas.setDescripcion(rs.getString("descripcion"));
                gas.setMonto_gasto(rs.getDouble("monto_gasto"));
                gas.setEstado(rs.getString("estado"));
                gas.setFk_idgasto_tipo(rs.getInt("fk_idgasto_tipo"));
                gas.setFk_idusuario(rs.getInt("fk_idusuario"));
                gas.setGasto_tipo(rs.getString("tipo"));
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + gas.toString(), titulo);
        }
    }

    public void insertar_gasto(Connection conn, gasto gas) {
        gas.setIdgasto(eveconn.getInt_ultimoID_mas_uno(conn, gas.getTabla(), gas.getIdtabla()));
        String titulo = "insertar_gasto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, gas.getIdgasto());
            pst.setTimestamp(2, evefec.getTimestamp_fecha_for_date(gas.getFecha_emision()));
            pst.setString(3, gas.getDescripcion());
            pst.setDouble(4, gas.getMonto_gasto());
            pst.setString(5, gas.getEstado());
            pst.setInt(6, gas.getFk_idgasto_tipo());
            pst.setInt(7, gas.getFk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + gas.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + gas.toString(), titulo);
        }
    }

    public void update_gasto(Connection conn, gasto gas) {
        String titulo = "update_gasto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_fecha_cargado(gas.getFecha_emision()));
            pst.setString(2, gas.getDescripcion());
            pst.setDouble(3, gas.getMonto_gasto());
            pst.setInt(4, gas.getFk_idgasto_tipo());
            pst.setInt(5, gas.getFk_idusuario());
            pst.setInt(6, gas.getIdgasto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + gas.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + gas.toString(), titulo);
        }
    }

    public void update_gasto_anular(Connection conn, gasto gas) {
        String titulo = "update_gasto_anular";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_anular);
            pst.setString(1, gas.getEstado());
            pst.setInt(2, gas.getIdgasto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_anular + "\n" + gas.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, false);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_anular + "\n" + gas.toString(), titulo);
        }
    }

    public void actualizar_tabla_gasto(Connection conn, JTable tbltabla, String filtro, String orden) {
        eveconn.Select_cargar_jtable(conn, sql_select + filtro + orden, tbltabla);
        everende.rendertabla_estados(tbltabla, 5);
        ancho_tabla_gasto(tbltabla);
    }

    public void ancho_tabla_gasto(JTable tbltabla) {
        int Ancho[] = {3, 8, 24, 25, 7, 8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }

    public double sumar_monto_gasto(Connection conn, String filtro) {
        return eveconn.getdouble_sql_suma(conn, sql_suma_monto + filtro);
    }

    public double sumar_cantidad_gasto(Connection conn, String filtro) {
        return eveconn.getdouble_sql_suma(conn, sql_suma_cant + filtro);
    }

    public void imprimir_filtro_gasto(Connection conn, String filtro) {
        String sql = "select g.idgasto,to_char(g.fecha_emision,'dd/MM/yyyy') as fecha,\n"
                + "g.descripcion,gt.nombre as tipo,\n"
                + "g.monto_gasto as monto,\n"
                + "g.estado\n"
                + " from gasto g,gasto_tipo gt\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n"
                + "and g.estado='EMITIDO'\n" + filtro
                + "\n order by g.fecha_emision desc;";
        String titulonota = "FILTRO GASTO";
        String direccion = "src/REPORTE/GASTO/repFiltroGasto.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_filtro_gasto_excel(Connection conn, String filtro) {
        String sql_select = "select g.idgasto,to_char(g.fecha_emision,'dd/MM/yyyy') as fecha,\n"
                + "g.descripcion,gt.nombre as tipo,\n"
                + "g.monto_gasto as monto\n"
                + "from gasto g,gasto_tipo gt\n"
                + "where g.fk_idgasto_tipo=gt.idgasto_tipo\n"
                + "and g.estado='EMITIDO'\n"+filtro
                + " order by g.fecha_emision desc; ";
        String titulonota = "FILTRO GASTO";
        String direccion = "src/REPORTE/GASTO/repFiltroGastoExcel.jrxml";
        String rutatemp = "Fil_gasto_" + evefec.getS_fec_hs_seg_archivo();
        rep.imprimirExcel(conn, sql_select, direccion, rutatemp);
    }
}
