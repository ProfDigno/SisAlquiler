package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_transaccion_banco;
import FORMULARIO.ENTIDAD.transaccion_banco;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_transaccion_banco {

    private DAO_transaccion_banco trba_dao = new DAO_transaccion_banco();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_transaccion_banco(transaccion_banco trba) {
        String titulo = "insertar_transaccion_banco";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            trba_dao.insertar_transaccion_banco(conn, trba);
//            trba_dao.actualizar_tabla_transaccion_banco(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, trba.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, trba.toString(), titulo);
            }
        }
    }

    public void update_transaccion_banco(transaccion_banco trba, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR TRANSACCION_BANCO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_transaccion_banco";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                trba_dao.update_transaccion_banco(conn, trba);
                trba_dao.actualizar_tabla_transaccion_banco(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, trba.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, trba.toString(), titulo);
                }
            }
        }
    }
}
