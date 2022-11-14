	package FORMULARIO.BO;
	import BASEDATO.LOCAL.ConnPostgres;
	import Evento.Mensaje.EvenMensajeJoptionpane;
	import FORMULARIO.DAO.DAO_item_banco_cliente;
	import FORMULARIO.ENTIDAD.item_banco_cliente;
	import java.sql.Connection;
	import java.sql.SQLException;
	import javax.swing.JTable;
public class BO_item_banco_cliente {
private DAO_item_banco_cliente itbacl_dao = new DAO_item_banco_cliente();
	EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

	public void insertar_item_banco_cliente(item_banco_cliente itbacl, JTable tbltabla) {
		String titulo = "insertar_item_banco_cliente";
		Connection conn = ConnPostgres.getConnPosgres();
		try {
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			itbacl_dao.insertar_item_banco_cliente(conn, itbacl);
			itbacl_dao.actualizar_tabla_item_banco_cliente(conn, tbltabla);
			conn.commit();
		} catch (SQLException e) {
			evmen.mensaje_error(e, itbacl.toString(), titulo);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				evmen.Imprimir_serial_sql_error(e1, itbacl.toString(), titulo);
			}
		}
	}
	public void update_item_banco_cliente(item_banco_cliente itbacl, JTable tbltabla) {
		if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_BANCO_CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
			String titulo = "update_item_banco_cliente";
			Connection conn = ConnPostgres.getConnPosgres();
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
				itbacl_dao.update_item_banco_cliente(conn, itbacl);
				itbacl_dao.actualizar_tabla_item_banco_cliente(conn, tbltabla);
				conn.commit();
			} catch (SQLException e) {
				evmen.mensaje_error(e, itbacl.toString(), titulo);
				try {
					conn.rollback();
				}catch (SQLException e1) {
					evmen.Imprimir_serial_sql_error(e1, itbacl.toString(), titulo);
				}
			}
		}
	}
}
