package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.tipo_evento;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_tipo_evento {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "TIPO_EVENTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "TIPO_EVENTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO tipo_evento(idtipo_evento,nombre,activar) VALUES (?,?,?);";
    private String sql_update = "UPDATE tipo_evento SET nombre=?,activar=? WHERE idtipo_evento=?;";
    private String sql_select = "SELECT idtipo_evento as idte,nombre as tipo_evento,activar FROM tipo_evento order by 1 desc;";
    private String sql_cargar = "SELECT idtipo_evento,nombre,activar FROM tipo_evento WHERE idtipo_evento=";

    public void insertar_tipo_evento(Connection conn, tipo_evento tiev) {
        tiev.setC1idtipo_evento(eveconn.getInt_ultimoID_mas_uno(conn, tiev.getTb_tipo_evento(), tiev.getId_idtipo_evento()));
        String titulo = "insertar_tipo_evento";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, tiev.getC1idtipo_evento());
            pst.setString(2, tiev.getC2nombre());
            pst.setBoolean(3, tiev.getC3activar());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + tiev.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + tiev.toString(), titulo);
        }
    }

    public void update_tipo_evento(Connection conn, tipo_evento tiev) {
        String titulo = "update_tipo_evento";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, tiev.getC2nombre());
            pst.setBoolean(2, tiev.getC3activar());
            pst.setInt(3, tiev.getC1idtipo_evento());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + tiev.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + tiev.toString(), titulo);
        }
    }

    public void cargar_tipo_evento(Connection conn, tipo_evento tiev, int idtipo_evento) {
        String titulo = "Cargar_tipo_evento";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idtipo_evento, titulo);
            if (rs.next()) {
                tiev.setC1idtipo_evento(rs.getInt(1));
                tiev.setC2nombre(rs.getString(2));
                tiev.setC3activar(rs.getBoolean(3));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + tiev.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + tiev.toString(), titulo);
        }
    }

    public void actualizar_tabla_tipo_evento(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_tipo_evento(tbltabla);
    }

    public void ancho_tabla_tipo_evento(JTable tbltabla) {
        int Ancho[] = {10,80,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
