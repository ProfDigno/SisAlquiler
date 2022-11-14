package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_producto_combo;
import FORMULARIO.ENTIDAD.item_producto_combo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_item_producto_combo {

    private DAO_item_producto_combo itprco_dao = new DAO_item_producto_combo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_item_producto_combo(item_producto_combo itprco, JTable tbltabla) {
        String titulo = "insertar_item_producto_combo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            itprco_dao.insertar_item_producto_combo(conn, itprco);
//			itprco_dao.actualizar_tabla_item_producto_combo(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, itprco.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, itprco.toString(), titulo);
            }
        }
    }

    public void update_item_producto_combo(item_producto_combo itprco, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ITEM_PRODUCTO_COMBO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_item_producto_combo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                itprco_dao.update_item_producto_combo(conn, itprco);
//				itprco_dao.actualizar_tabla_item_producto_combo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, itprco.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, itprco.toString(), titulo);
                }
            }
        }
    }
}
