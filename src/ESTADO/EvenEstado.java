/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ESTADO;

/**
 *
 * @author Digno
 */
public class EvenEstado {
    public static String tp_item_alq_combo;
    public static String tp_item_alq_subcombo;
    public static String tp_item_alq_pro;
    public static String est_EMITIDO;
    public static String est_ANULADO;
    public static String est_ALQUILADO;
    public static String est_DEVOLUCION;
    public static String form_pago_EFECTIVO;
    public static String form_pago_TARJETA;
    public static String form_pago_TRANSFERENCIA;

    public EvenEstado() {
        setTp_item_alq_combo("C");
        setTp_item_alq_subcombo("SC");
        setTp_item_alq_pro("P");
        setEst_EMITIDO("EMITIDO");
        setEst_ANULADO("ANULADO");
        setEst_ALQUILADO("ALQUILADO");
        setEst_DEVOLUCION("DEVOLUCION");
        setForm_pago_EFECTIVO("EFECTIVO");
        setForm_pago_TARJETA("TARJETA");
        setForm_pago_TRANSFERENCIA("TRANSFERENCIA");
    }

    public static String getForm_pago_EFECTIVO() {
        return form_pago_EFECTIVO;
    }

    public static void setForm_pago_EFECTIVO(String form_pago_EFECTIVO) {
        EvenEstado.form_pago_EFECTIVO = form_pago_EFECTIVO;
    }

    public static String getForm_pago_TARJETA() {
        return form_pago_TARJETA;
    }

    public static void setForm_pago_TARJETA(String form_pago_TARJETA) {
        EvenEstado.form_pago_TARJETA = form_pago_TARJETA;
    }

    public static String getForm_pago_TRANSFERENCIA() {
        return form_pago_TRANSFERENCIA;
    }

    public static void setForm_pago_TRANSFERENCIA(String form_pago_TRANSFERENCIA) {
        EvenEstado.form_pago_TRANSFERENCIA = form_pago_TRANSFERENCIA;
    }

    public static String getEst_EMITIDO() {
        return est_EMITIDO;
    }

    public static void setEst_EMITIDO(String est_EMITIDO) {
        EvenEstado.est_EMITIDO = est_EMITIDO;
    }

    public static String getEst_ANULADO() {
        return est_ANULADO;
    }

    public static void setEst_ANULADO(String est_ANULADO) {
        EvenEstado.est_ANULADO = est_ANULADO;
    }

    public static String getEst_ALQUILADO() {
        return est_ALQUILADO;
    }

    public static void setEst_ALQUILADO(String est_ALQUILADO) {
        EvenEstado.est_ALQUILADO = est_ALQUILADO;
    }

    public static String getEst_DEVOLUCION() {
        return est_DEVOLUCION;
    }

    public static void setEst_DEVOLUCION(String est_DEVOLUCION) {
        EvenEstado.est_DEVOLUCION = est_DEVOLUCION;
    }
    
    public static String getTp_item_alq_combo() {
        return tp_item_alq_combo;
    }

    public static void setTp_item_alq_combo(String tp_item_alq_combo) {
        EvenEstado.tp_item_alq_combo = tp_item_alq_combo;
    }

    public static String getTp_item_alq_subcombo() {
        return tp_item_alq_subcombo;
    }

    public static void setTp_item_alq_subcombo(String tp_item_alq_subcombo) {
        EvenEstado.tp_item_alq_subcombo = tp_item_alq_subcombo;
    }

    public static String getTp_item_alq_pro() {
        return tp_item_alq_pro;
    }

    public static void setTp_item_alq_pro(String tp_item_alq_pro) {
        EvenEstado.tp_item_alq_pro = tp_item_alq_pro;
    }
    
}
