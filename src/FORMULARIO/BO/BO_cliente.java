/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.BO;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class BO_cliente {

    private DAO_cliente DAOcl = new DAO_cliente();
    private DAO_saldo_credito_cliente DAOscc = new DAO_saldo_credito_cliente();
    private DAO_credito_cliente DAOcc = new DAO_credito_cliente();
    private DAO_grupo_credito_cliente DAOgcc = new DAO_grupo_credito_cliente();
    private DAO_recibo_pago_cliente DAOrpc = new DAO_recibo_pago_cliente();
    private grupo_credito_cliente ENTgcc2 = new grupo_credito_cliente();
    private grupo_credito_cliente ENTgcc3 = new grupo_credito_cliente();
    private DAO_caja_detalle_alquilado DAOcda = new DAO_caja_detalle_alquilado();
    private DAO_transaccion_banco DAOtb = new DAO_transaccion_banco();
    private DAO_venta_alquiler DAOva = new DAO_venta_alquiler();
    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();
    private String estado_EMITIDO = "EMITIDO";
    private String estado_ABIERTO = "ABIERTO";
    private String estado_CERRADO = "CERRADO";
    EvenConexion eveconn = new EvenConexion();

    public void insertar_cliente(Connection conn, cliente ingre, JTable tbltabla) {
        String titulo = "insertar_cliente";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcl.insertar_cliente(conn, ingre);
            DAOcl.actualizar_tabla_cliente(conn, tbltabla);
            conn.commit();
        } catch (SQLException e) {
            evmen.mensaje_error(e, ingre.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, ingre.toString(), titulo);
            }
        }
    }

    public void update_cliente(cliente ingre) {
        if (evmen.MensajeGeneral_warning("ESTAS SEGURO DE MODIFICAR ESTE CLIENTE", "MODIFICAR", "ACEPTAR", "CANCELAR")) {
            String titulo = "update_cliente";
            Connection conn = ConnPostgres.getConnPosgres();
            try {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
                DAOcl.update_cliente(conn, ingre);
                conn.commit();
            } catch (SQLException e) {
                evmen.mensaje_error(e, ingre.toString(), titulo);
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    evmen.Imprimir_serial_sql_error(e1, ingre.toString(), titulo);
                }
            }
        }
    }

    public boolean getBoolean_insertar_cliente_con_credito_inicio1(cliente cli, saldo_credito_cliente sccli, credito_cliente ccli, grupo_credito_cliente gcc) {
        String titulo = "insertar_cliente_con_credito_inicio";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert = false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOcl.insertar_cliente(conn, cli);
            DAOscc.insertar_saldo_credito_cliente(conn, sccli);
            DAOgcc.insertar_grupo_credito_cliente(conn, gcc);
            DAOcc.insertar_credito_cliente1(conn, ccli);
            conn.commit();
            insert = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, cli.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cli.toString(), titulo);
            }
        }
        return insert;
    }

    public boolean getBoolean_insertar_credito_inicio1(saldo_credito_cliente sccli, credito_cliente ccli, grupo_credito_cliente gcc) {
        String titulo = "getBoolean_insertar_credito_inicio";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert = false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOscc.insertar_saldo_credito_cliente(conn, sccli);
            DAOgcc.insertar_grupo_credito_cliente(conn, gcc);
            DAOcc.insertar_credito_cliente1(conn, ccli);
            conn.commit();
            insert = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, sccli.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, sccli.toString(), titulo);
            }
        }
        return insert;
    }

    public boolean getBoolean_insertar_cliente_con_recibo_pago(cliente cli, credito_cliente ccli, credito_cliente ccli2,
            grupo_credito_cliente gcc, recibo_pago_cliente rpcli, 
            saldo_credito_cliente sccli, caja_detalle_alquilado caja, 
            transaccion_banco ENTtb, int tipo_pago,venta_alquiler vealq) {
        String titulo = "getBoolean_insertar_cliente_con_recibo_pago";
        Connection conn = ConnPostgres.getConnPosgres();
        boolean insert = false;
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            DAOrpc.insertar_recibo_pago_cliente1(conn, rpcli);
            DAOcc.insertar_credito_cliente1(conn, ccli);
            DAOgcc.cargar_grupo_credito_cliente_id(conn, ENTgcc2, cli.getC1idcliente());
            ENTgcc2.setC4estado(estado_CERRADO);
            DAOgcc.cerrar_grupo_credito_cliente(conn, ENTgcc2);
            ENTgcc2.setC4estado(estado_ABIERTO);
            ENTgcc2.setC5fk_idcliente(cli.getC1idcliente());
            DAOgcc.insertar_grupo_credito_cliente(conn, ENTgcc2);
            DAOscc.insertar_saldo_credito_cliente(conn, sccli);
            DAOgcc.cargar_grupo_credito_cliente_id(conn, ENTgcc3, cli.getC1idcliente());
            int idsaldo_credito_cliente = (eveconn.getInt_ultimoID_max(conn, sccli.getTb_saldo_credito_cliente(), sccli.getId_idsaldo_credito_cliente()));
            ccli2.setC8fk_idgrupo_credito_cliente(ENTgcc3.getC1idgrupo_credito_cliente());
            ccli2.setC9fk_idsaldo_credito_cliente(idsaldo_credito_cliente);
            DAOcc.insertar_credito_cliente1(conn, ccli2);
            DAOcl.update_cliente_saldo_credito(conn, cli);
            DAOcda.insertar_caja_detalle_alquilado(conn, caja);
            DAOva.update_venta_alquiler_monto_pago(conn, vealq);
            if (tipo_pago == 3) {
                DAOtb.insertar_transaccion_banco(conn, ENTtb);
            }
            conn.commit();
            insert = true;
        } catch (SQLException e) {
            evmen.mensaje_error(e, cli.toString(), titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evmen.Imprimir_serial_sql_error(e1, cli.toString(), titulo);
            }
        }
        return insert;
    }
}
