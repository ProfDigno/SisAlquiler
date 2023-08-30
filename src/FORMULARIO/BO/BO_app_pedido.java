package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_app_pedido;
import FORMULARIO.ENTIDAD.app_pedido;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_app_pedido {

    private DAO_app_pedido DAOap = new DAO_app_pedido();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_o_update_app_pedido(app_pedido ENTap) {
        String titulo = "insertar_o_update_app_pedido";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOap.update_app_pedido(conn, ENTap);
            DAOap.insertar_app_pedido(conn, ENTap);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ENTap.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ENTap.toString(), titulo);
            }
        }
    }

    public void update_app_pedido(app_pedido ap, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR APP_PEDIDO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_app_pedido";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOap.update_app_pedido(conn, ap);
                DAOap.actualizar_tabla_app_pedido(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ap.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ap.toString(), titulo);
                }
            }
        }
    }
}
