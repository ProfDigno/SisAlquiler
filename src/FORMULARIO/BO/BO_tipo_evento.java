package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_tipo_evento;
import FORMULARIO.ENTIDAD.tipo_evento;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_tipo_evento {

    private DAO_tipo_evento DAOtv = new DAO_tipo_evento();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_tipo_evento(tipo_evento tiev, JTable tbltabla) {
        String titulo = "insertar_tipo_evento";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOtv.insertar_tipo_evento(conn, tiev);
            DAOtv.actualizar_tabla_tipo_evento(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, tiev.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, tiev.toString(), titulo);
            }
        }
    }

    public void update_tipo_evento(tipo_evento tiev, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR TIPO_EVENTO", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_tipo_evento";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOtv.update_tipo_evento(conn, tiev);
                DAOtv.actualizar_tabla_tipo_evento(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, tiev.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, tiev.toString(), titulo);
                }
            }
        }
    }
}
