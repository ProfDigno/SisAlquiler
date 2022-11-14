package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.dato_banco_cliente;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_dato_banco_cliente {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "DATO_BANCO_CLIENTE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "DATO_BANCO_CLIENTE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO dato_banco_cliente(iddato_banco_cliente,titular,documento,nro_cuenta,activo,fk_idbanco) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE dato_banco_cliente SET titular=?,documento=?,nro_cuenta=?,activo=?,fk_idbanco=? WHERE iddato_banco_cliente=?;";
    private String sql_select = "SELECT iddato_banco_cliente,titular,documento,nro_cuenta,activo,fk_idbanco FROM dato_banco_cliente order by 1 desc;";
    private String sql_cargar = "SELECT iddato_banco_cliente,titular,documento,nro_cuenta,activo,fk_idbanco FROM dato_banco_cliente WHERE iddato_banco_cliente=";

    public void insertar_dato_banco_cliente(Connection conn, dato_banco_cliente dabacl) {
        dabacl.setC1iddato_banco_cliente(eveconn.getInt_ultimoID_mas_uno(conn, dabacl.getTb_dato_banco_cliente(), dabacl.getId_iddato_banco_cliente()));
        String titulo = "insertar_dato_banco_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, dabacl.getC1iddato_banco_cliente());
            pst.setString(2, dabacl.getC2titular());
            pst.setString(3, dabacl.getC3documento());
            pst.setString(4, dabacl.getC4nro_cuenta());
            pst.setBoolean(5, dabacl.getC5activo());
            pst.setInt(6, dabacl.getC6fk_idbanco());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + dabacl.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + dabacl.toString(), titulo);
        }
    }

    public void update_dato_banco_cliente(Connection conn, dato_banco_cliente dabacl) {
        String titulo = "update_dato_banco_cliente";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, dabacl.getC2titular());
            pst.setString(2, dabacl.getC3documento());
            pst.setString(3, dabacl.getC4nro_cuenta());
            pst.setBoolean(4, dabacl.getC5activo());
            pst.setInt(5, dabacl.getC6fk_idbanco());
            pst.setInt(6, dabacl.getC1iddato_banco_cliente());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + dabacl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + dabacl.toString(), titulo);
        }
    }

    public void cargar_dato_banco_cliente(Connection conn, dato_banco_cliente dabacl, int iddato_banco_cliente) {
        String titulo = "Cargar_dato_banco_cliente";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + iddato_banco_cliente, titulo);
            if (rs.next()) {
                dabacl.setC1iddato_banco_cliente(rs.getInt(1));
                dabacl.setC2titular(rs.getString(2));
                dabacl.setC3documento(rs.getString(3));
                dabacl.setC4nro_cuenta(rs.getString(4));
                dabacl.setC5activo(rs.getBoolean(5));
                dabacl.setC6fk_idbanco(rs.getInt(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + dabacl.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + dabacl.toString(), titulo);
        }
    }

    public void actualizar_tabla_dato_banco_cliente(Connection conn, JTable tbltabla,int fk_idcliente) {
        String sql = "SELECT db.iddato_banco_cliente as id,db.titular,db.nro_cuenta,b.nombre as banco \n"
                + "FROM dato_banco_cliente db,banco b,item_banco_cliente ibc  \n"
                + "where b.idbanco=db.fk_idbanco \n"
                + "and ibc.fk_iddato_banco_cliente=db.iddato_banco_cliente \n"
                + "and ibc.fk_idcliente="+fk_idcliente
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_dato_banco_cliente(tbltabla);
    }

    public void ancho_tabla_dato_banco_cliente(JTable tbltabla) {
        int Ancho[] = {10, 40, 25, 25};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
