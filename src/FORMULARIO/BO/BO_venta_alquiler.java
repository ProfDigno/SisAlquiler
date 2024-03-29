package FORMULARIO.BO;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.DAO_app_pedido;
import FORMULARIO.DAO.DAO_caja_cierre_alquilado;
import FORMULARIO.DAO.DAO_caja_detalle_alquilado;
import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_credito_cliente;
import FORMULARIO.DAO.DAO_item_venta_alquiler;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_recibo_pago_cliente;
import FORMULARIO.DAO.DAO_venta_alquiler;
import FORMULARIO.ENTIDAD.app_pedido;
import FORMULARIO.ENTIDAD.caja_cierre_alquilado;
import FORMULARIO.ENTIDAD.caja_detalle_alquilado;
import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.credito_cliente;
import FORMULARIO.ENTIDAD.item_venta_alquiler;
import FORMULARIO.ENTIDAD.recibo_pago_cliente;
import FORMULARIO.ENTIDAD.venta_alquiler;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

public class BO_venta_alquiler {

    private DAO_venta_alquiler DAOva = new DAO_venta_alquiler();
    private DAO_item_venta_alquiler DAOiva = new DAO_item_venta_alquiler();
    private DAO_caja_detalle_alquilado DAOcda = new DAO_caja_detalle_alquilado();
    private DAO_credito_cliente DAOcc = new DAO_credito_cliente();
    private DAO_cliente DAOc = new DAO_cliente();
    private DAO_producto DAOp = new DAO_producto();
    private DAO_recibo_pago_cliente DAOrpc=new DAO_recibo_pago_cliente();
    private DAO_app_pedido DAOap = new DAO_app_pedido();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    private String campoid = "fk_idventa_alquiler";

    public boolean getBoolean_insertar_venta_alquiler1(venta_alquiler vealq, credito_cliente ccli, cliente clie, app_pedido ap, boolean est_pedido, JTable tbltabla) {
        boolean insertado = false;
        String titulo = "getBoolean_insertar_venta_alquiler";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOva.insertar_venta_alquiler(conn, vealq);
            DAOiva.insertar_item_venta_alquiler_de_tabla(conn, tbltabla, vealq);
            DAOcc.insertar_credito_cliente1(conn, ccli);
            DAOc.update_cliente_saldo_credito(conn, clie);
            if (est_pedido) {
                DAOap.update_app_pedido_estado(conn, ap);
            }
            conn.commit();
            insertado = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, vealq.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
            }
        }
        return insertado;
    }

    public void update_venta_alquiler(venta_alquiler vealq, JTable tbltabla) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR VENTA_ALQUILER", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_venta_alquiler";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOva.update_venta_alquiler(conn, vealq);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
    }

    public boolean getBoolean_update_venta_alquiler_anular(venta_alquiler vealq, credito_cliente ccli,recibo_pago_cliente ENTrpc, cliente clie) {
        boolean anulado = false;
        String titulo = "getBoolean_update_venta_alquiler_anular";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOva.update_venta_alquiler_estado_anular(conn, vealq);
            DAOcc.update_credito_cliente_anular(conn, ccli);
            DAOrpc.update_recibo_pago_cliente_anular_venta(conn, ENTrpc);
            DAOc.update_cliente_saldo_credito(conn, clie);
            conn.commit();
            anulado = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, vealq.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
            }
        }
        return anulado;
    }

    public boolean getBoolean_update_venta_alquiler_alquilado(String lista_producto, venta_alquiler vealq) {
        boolean retirar = false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE ALQUILAR VENTA_ALQUILER\nLISTA DE PRODUCTO A RETIRAR:\n" + lista_producto + "\nSALE DEL STOCK", "ALQUILAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_venta_alquiler_alquilado";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOva.update_venta_alquiler_alquilado(conn, vealq);
                DAOp.update_producto_stock_Alquilado(conn, vealq);
                conn.commit();
                retirar = true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
        return retirar;
    }

    public boolean getBoolean_update_venta_alquiler_Devolucion(String lista_producto, venta_alquiler vealq) {
        boolean devolusion = false;
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE PRECESAR DEVOLUCION VENTA_ALQUILER\nLISTA DE PRODUCTO A FINALIZAR:\n" + lista_producto + "\n(+INGRESA A STOCK+)", "DEVOLUCION", "ACEPTAR", "CANCELAR")) {
            String titulo = "getBoolean_update_venta_alquiler_Devolucion";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOva.update_venta_alquiler_Devolucion(conn, vealq);
                DAOp.update_producto_stock_Devolucion(conn, vealq);
                conn.commit();
                devolusion = true;
            } catch (SQLException e) {
                evmen.mensaje_error(e, vealq.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
                }
            }
        }
        return devolusion;
    }

    public boolean getBoolean_update_venta_alquiler_Finalizar(venta_alquiler vealq, caja_detalle_alquilado cdalq) {
        boolean finalizar = false;
//        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE PRECESAR DEVOLUCION VENTA_ALQUILER\nLISTA DE PRODUCTO A FINALIZAR:\n" + lista_producto + "\n(+INGRESA A STOCK+)", "DEVOLUCION", "ACEPTAR", "CANCELAR")) {
        String titulo = "getBoolean_update_venta_alquiler_Finalizar";
        Connection conn = ConnPostgres.getConnPosgres();
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOva.update_venta_alquiler_estado_finalizar(conn, vealq);
            int datocampoid = cdalq.getC20fk_idventa_alquiler();
            DAOcda.update_caja_detalle_alquilado_estado_todos(conn, cdalq, datocampoid, campoid);
            conn.commit();
            finalizar = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, vealq.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, vealq.toString(), titulo);
            }
        }
//        }
        return finalizar;
    }
}
