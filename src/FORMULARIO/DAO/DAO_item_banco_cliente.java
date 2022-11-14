	package FORMULARIO.DAO;
	import BASEDATO.EvenConexion;
	import FORMULARIO.ENTIDAD.item_banco_cliente;
	import Evento.JasperReport.EvenJasperReport;
	import Evento.Jtable.EvenJtable;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import Evento.Fecha.EvenFecha;
	import java.sql.ResultSet;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import javax.swing.JTable;
public class DAO_item_banco_cliente {
	EvenConexion eveconn = new EvenConexion();
	EvenJtable evejt = new EvenJtable();
	EvenJasperReport rep = new EvenJasperReport();
	EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
	EvenFecha evefec = new EvenFecha();
	private String mensaje_insert = "ITEM_BANCO_CLIENTE GUARDADO CORRECTAMENTE";
	private String mensaje_update = "ITEM_BANCO_CLIENTE MODIFICADO CORECTAMENTE";
	private String sql_insert = "INSERT INTO item_banco_cliente(iditem_banco_cliente,fk_iddato_banco_cliente,fk_idcliente) VALUES (?,?,?);";
	private String sql_update = "UPDATE item_banco_cliente SET fk_iddato_banco_cliente=?,fk_idcliente=? WHERE iditem_banco_cliente=?;";
	private String sql_select = "SELECT iditem_banco_cliente,fk_iddato_banco_cliente,fk_idcliente FROM item_banco_cliente order by 1 desc;";
	private String sql_cargar = "SELECT iditem_banco_cliente,fk_iddato_banco_cliente,fk_idcliente FROM item_banco_cliente WHERE iditem_banco_cliente=";
	public void insertar_item_banco_cliente(Connection conn, item_banco_cliente itbacl){
		itbacl.setC1iditem_banco_cliente(eveconn.getInt_ultimoID_mas_uno(conn, itbacl.getTb_item_banco_cliente(), itbacl.getId_iditem_banco_cliente()));
		String titulo = "insertar_item_banco_cliente";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_insert);
			pst.setInt(1,itbacl.getC1iditem_banco_cliente());
			pst.setInt(2,itbacl.getC2fk_iddato_banco_cliente());
			pst.setInt(3,itbacl.getC3fk_idcliente());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_insert + "\n" + itbacl.toString(), titulo);
			evemen.guardado_correcto(mensaje_insert, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_insert + "\n" + itbacl.toString(), titulo);
		}
	}
	public void update_item_banco_cliente(Connection conn, item_banco_cliente itbacl){
		String titulo = "update_item_banco_cliente";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql_update);
			pst.setInt(1,itbacl.getC2fk_iddato_banco_cliente());
			pst.setInt(2,itbacl.getC3fk_idcliente());
			pst.setInt(3,itbacl.getC1iditem_banco_cliente());
			pst.execute();
			pst.close();
			evemen.Imprimir_serial_sql(sql_update + "\n" + itbacl.toString(), titulo);
			evemen.modificado_correcto(mensaje_update, true);
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_update + "\n" + itbacl.toString(), titulo);
		}
	}
	public void cargar_item_banco_cliente(Connection conn, item_banco_cliente itbacl,int iditem_banco_cliente){
		String titulo = "Cargar_item_banco_cliente";
		try {
			ResultSet rs=eveconn.getResulsetSQL(conn,sql_cargar+iditem_banco_cliente, titulo);
			if(rs.next()){
				itbacl.setC1iditem_banco_cliente(rs.getInt(1));
				itbacl.setC2fk_iddato_banco_cliente(rs.getInt(2));
				itbacl.setC3fk_idcliente(rs.getInt(3));
				evemen.Imprimir_serial_sql(sql_cargar + "\n" + itbacl.toString(), titulo);
			}
		} catch (Exception e) {
			evemen.mensaje_error(e, sql_cargar + "\n" + itbacl.toString(), titulo);
		}
	}
	public void actualizar_tabla_item_banco_cliente(Connection conn,JTable tbltabla){
		eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
		ancho_tabla_item_banco_cliente(tbltabla);
	}
	public void ancho_tabla_item_banco_cliente(JTable tbltabla){
		int Ancho[]={33,33,33};
		evejt.setAnchoColumnaJtable(tbltabla, Ancho);
	}
}
