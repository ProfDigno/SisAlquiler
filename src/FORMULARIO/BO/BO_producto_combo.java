package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_item_producto_combo;
import FORMULARIO.DAO.DAO_producto_combo;
import FORMULARIO.ENTIDAD.producto_combo;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_producto_combo {

    private DAO_producto_combo DAOpc = new DAO_producto_combo();
    private DAO_item_producto_combo DAOipc = new DAO_item_producto_combo();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public boolean getBoo_insertar_producto_combo(producto_combo prco, JTable tbltabla) {
        String titulo = "insertar_producto_combo";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOpc.insertar_producto_combo(conn, prco);
            DAOipc.insertar_item_producto_combo(conn, tbltabla, prco);
//            DAOpc.actualizar_tabla_producto_combo(conn, tbltabla);
            conn.commit();
            return true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, prco.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, prco.toString(), titulo);
            }
            return false;
        }
    }

    public void update_producto_combo(producto_combo prco, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR PRODUCTO_COMBO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_producto_combo";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOpc.update_producto_combo(conn, prco);
                DAOpc.actualizar_tabla_producto_combo(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, prco.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, prco.toString(), titulo);
                }
            }
        }
    }
}
