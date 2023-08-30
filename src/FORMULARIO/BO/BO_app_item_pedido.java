package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_app_item_pedido;
import FORMULARIO.ENTIDAD.app_item_pedido;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_app_item_pedido {

    private DAO_app_item_pedido DAOaip = new DAO_app_item_pedido();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_app_item_pedido(app_item_pedido ENTaip) {
        String titulo = "insertar_app_item_pedido";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOaip.update_app_item_pedido(conn, ENTaip);
            DAOaip.insertar_app_item_pedido(conn, ENTaip);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTaip.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTaip.toString(), titulo);
            }
        }
    }

    public void update_app_item_pedido(app_item_pedido aip, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR APP_ITEM_PEDIDO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_app_item_pedido";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOaip.update_app_item_pedido(conn, aip);
                DAOaip.actualizar_tabla_app_item_pedido(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, aip.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, aip.toString(), titulo);
                }
            }
        }
    }
}
