	package FORMULARIO.ENTIDAD;
public class app_pedido {

//---------------DECLARAR VARIABLES---------------
private String C1idventa_alquiler;
private String C2fecha_creado;
private String C3fec_evento;
private int C4fk_idcliente;
private String C5cliente;
private String C6direccion;
private String C7telefono;
private String C8ruc;
private String C9observacion;
private String C10estado;
private double C11descuento_gral;
private double C12pago_parcial;
private boolean C13es_eliminado;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public app_pedido() {
		setTb_app_pedido("app_pedido");
		setId_idventa_alquiler("idventa_alquiler");
	}
	public static String getTb_app_pedido(){
		return nom_tabla;
	}
	public static void setTb_app_pedido(String nom_tabla){
		app_pedido.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_alquiler(){
		return nom_idtabla;
	}
	public static void setId_idventa_alquiler(String nom_idtabla){
		app_pedido.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public boolean getC13es_eliminado() {
        return C13es_eliminado;
    }

    public void setC13es_eliminado(boolean C13es_eliminado) {
        this.C13es_eliminado = C13es_eliminado;
    }
        
	public String getC1idventa_alquiler(){
		return C1idventa_alquiler;
	}
	public void setC1idventa_alquiler(String C1idventa_alquiler){
		this.C1idventa_alquiler = C1idventa_alquiler;
	}
	public String getC2fecha_creado(){
		return C2fecha_creado;
	}
	public void setC2fecha_creado(String C2fecha_creado){
		this.C2fecha_creado = C2fecha_creado;
	}
	public String getC3fec_evento(){
		return C3fec_evento;
	}
	public void setC3fec_evento(String C3fec_evento){
		this.C3fec_evento = C3fec_evento;
	}
	public int getC4fk_idcliente(){
		return C4fk_idcliente;
	}
	public void setC4fk_idcliente(int C4fk_idcliente){
		this.C4fk_idcliente = C4fk_idcliente;
	}
	public String getC5cliente(){
		return C5cliente;
	}
	public void setC5cliente(String C5cliente){
		this.C5cliente = C5cliente;
	}
	public String getC6direccion(){
		return C6direccion;
	}
	public void setC6direccion(String C6direccion){
		this.C6direccion = C6direccion;
	}
	public String getC7telefono(){
		return C7telefono;
	}
	public void setC7telefono(String C7telefono){
		this.C7telefono = C7telefono;
	}
	public String getC8ruc(){
		return C8ruc;
	}
	public void setC8ruc(String C8ruc){
		this.C8ruc = C8ruc;
	}
	public String getC9observacion(){
		return C9observacion;
	}
	public void setC9observacion(String C9observacion){
		this.C9observacion = C9observacion;
	}
	public String getC10estado(){
		return C10estado;
	}
	public void setC10estado(String C10estado){
		this.C10estado = C10estado;
	}

    public double getC11descuento_gral() {
        return C11descuento_gral;
    }

    public void setC11descuento_gral(double C11descuento_gral) {
        this.C11descuento_gral = C11descuento_gral;
    }

    public double getC12pago_parcial() {
        return C12pago_parcial;
    }

    public void setC12pago_parcial(double C12pago_parcial) {
        this.C12pago_parcial = C12pago_parcial;
    }

    @Override
    public String toString() {
        return "app_pedido{" + "C1idventa_alquiler=" + C1idventa_alquiler + ", C2fecha_creado=" + C2fecha_creado + ", C3fec_evento=" + C3fec_evento + ", C4fk_idcliente=" + C4fk_idcliente + ", C5cliente=" + C5cliente + ", C6direccion=" + C6direccion + ", C7telefono=" + C7telefono + ", C8ruc=" + C8ruc + ", C9observacion=" + C9observacion + ", C10estado=" + C10estado + ", C11descuento_gral=" + C11descuento_gral + ", C12pago_parcial=" + C12pago_parcial + '}';
    }
        

}
