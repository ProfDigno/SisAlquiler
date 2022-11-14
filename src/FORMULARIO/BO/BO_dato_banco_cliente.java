package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_dato_banco_cliente;
import FORMULARIO.DAO.DAO_item_banco_cliente;
import FORMULARIO.ENTIDAD.dato_banco_cliente;
import FORMULARIO.ENTIDAD.item_banco_cliente;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_dato_banco_cliente {

    private DAO_dato_banco_cliente DAOdbc = new DAO_dato_banco_cliente();
    private DAO_item_banco_cliente DAOibc = new DAO_item_banco_cliente();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    public void insertar_dato_banco_cliente(dato_banco_cliente dabacl,item_banco_cliente itbacl) {
        String titulo = "insertar_dato_banco_cliente";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOdbc.insertar_dato_banco_cliente(conn, dabacl);
            DAOibc.insertar_item_banco_cliente(conn, itbacl);
//            dabacl_dao.actualizar_tabla_dato_banco_cliente(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, dabacl.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, dabacl.toString(), titulo);
            }
        }
    }

    public void update_dato_banco_cliente(dato_banco_cliente dabacl) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR DATO_BANCO_CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_dato_banco_cliente";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOdbc.update_dato_banco_cliente(conn, dabacl);
//                dabacl_dao.actualizar_tabla_dato_banco_cliente(conn, tbltabla);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, dabacl.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, dabacl.toString(), titulo);
                }
            }
        }
    }
}
