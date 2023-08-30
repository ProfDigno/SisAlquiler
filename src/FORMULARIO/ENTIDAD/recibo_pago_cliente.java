	package FORMULARIO.ENTIDAD;
public class recibo_pago_cliente {

//---------------DECLARAR VARIABLES---------------
private int C1idrecibo_pago_cliente;
private String C2fecha_emision;
private String C3descripcion;
private double C4monto_recibo_pago;
private String C5monto_letra;
private String C6estado;
private int C7fk_idcliente;
private int C8fk_idusuario;
private String C9forma_pago;
private double C10monto_recibo_efectivo;
private double C11monto_recibo_tarjeta;
private double C12monto_recibo_transferencia;
private int C13fk_idventa_alquiler;
private String C14fecha_recibo;
private static String nom_tabla;
private static String nom_idtabla;
private static int quien_llama;
private static double monto_sena_venta;
//---------------TABLA-ID---------------
	public recibo_pago_cliente() {
		setTb_recibo_pago_cliente("recibo_pago_cliente");
		setId_idrecibo_pago_cliente("idrecibo_pago_cliente");
	}

    public static double getMonto_sena_venta() {
        return monto_sena_venta;
    }

    public static void setMonto_sena_venta(double monto_sena_venta) {
        recibo_pago_cliente.monto_sena_venta = monto_sena_venta;
    }

    public static int getQuien_llama() {
        return quien_llama;
    }

    public static void setQuien_llama(int quien_llama) {
        recibo_pago_cliente.quien_llama = quien_llama;
    }
        
	public static String getTb_recibo_pago_cliente(){
		return nom_tabla;
	}
	public static void setTb_recibo_pago_cliente(String nom_tabla){
		recibo_pago_cliente.nom_tabla = nom_tabla;
	}
	public static String getId_idrecibo_pago_cliente(){
		return nom_idtabla;
	}
	public static void setId_idrecibo_pago_cliente(String nom_idtabla){
		recibo_pago_cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public String getC14fecha_recibo() {
        return C14fecha_recibo;
    }

    public void setC14fecha_recibo(String C14fecha_recibo) {
        this.C14fecha_recibo = C14fecha_recibo;
    }

    public String getC9forma_pago() {
        return C9forma_pago;
    }

    public void setC9forma_pago(String C9forma_pago) {
        this.C9forma_pago = C9forma_pago;
    }

    public double getC10monto_recibo_efectivo() {
        return C10monto_recibo_efectivo;
    }

    public void setC10monto_recibo_efectivo(double C10monto_recibo_efectivo) {
        this.C10monto_recibo_efectivo = C10monto_recibo_efectivo;
    }

    public double getC11monto_recibo_tarjeta() {
        return C11monto_recibo_tarjeta;
    }

    public void setC11monto_recibo_tarjeta(double C11monto_recibo_tarjeta) {
        this.C11monto_recibo_tarjeta = C11monto_recibo_tarjeta;
    }

    public double getC12monto_recibo_transferencia() {
        return C12monto_recibo_transferencia;
    }

    public void setC12monto_recibo_transferencia(double C12monto_recibo_transferencia) {
        this.C12monto_recibo_transferencia = C12monto_recibo_transferencia;
    }
        
	public int getC1idrecibo_pago_cliente(){
		return C1idrecibo_pago_cliente;
	}
	public void setC1idrecibo_pago_cliente(int C1idrecibo_pago_cliente){
		this.C1idrecibo_pago_cliente = C1idrecibo_pago_cliente;
	}
	public String getC2fecha_emision(){
		return C2fecha_emision;
	}
	public void setC2fecha_emision(String C2fecha_emision){
		this.C2fecha_emision = C2fecha_emision;
	}
	public String getC3descripcion(){
		return C3descripcion;
	}
	public void setC3descripcion(String C3descripcion){
		this.C3descripcion = C3descripcion;
	}
	public double getC4monto_recibo_pago(){
		return C4monto_recibo_pago;
	}
	public void setC4monto_recibo_pago(double C4monto_recibo_pago){
		this.C4monto_recibo_pago = C4monto_recibo_pago;
	}
	public String getC5monto_letra(){
		return C5monto_letra;
	}
	public void setC5monto_letra(String C5monto_letra){
		this.C5monto_letra = C5monto_letra;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public int getC7fk_idcliente(){
		return C7fk_idcliente;
	}
	public void setC7fk_idcliente(int C7fk_idcliente){
		this.C7fk_idcliente = C7fk_idcliente;
	}
	public int getC8fk_idusuario(){
		return C8fk_idusuario;
	}
	public void setC8fk_idusuario(int C8fk_idusuario){
		this.C8fk_idusuario = C8fk_idusuario;
	}

    public int getC13fk_idventa_alquiler() {
        return C13fk_idventa_alquiler;
    }

    public void setC13fk_idventa_alquiler(int C13fk_idventa_alquiler) {
        this.C13fk_idventa_alquiler = C13fk_idventa_alquiler;
    }

    @Override
    public String toString() {
        return "recibo_pago_cliente{" + "C1idrecibo_pago_cliente=" + C1idrecibo_pago_cliente + ", C2fecha_emision=" + C2fecha_emision + ", C3descripcion=" + C3descripcion + ", C4monto_recibo_pago=" + C4monto_recibo_pago + ", C5monto_letra=" + C5monto_letra + ", C6estado=" + C6estado + ", C7fk_idcliente=" + C7fk_idcliente + ", C8fk_idusuario=" + C8fk_idusuario + ", C9forma_pago=" + C9forma_pago + ", C10monto_recibo_efectivo=" + C10monto_recibo_efectivo + ", C11monto_recibo_tarjeta=" + C11monto_recibo_tarjeta + ", C12monto_recibo_transferencia=" + C12monto_recibo_transferencia + ", C13fk_idventa_alquiler=" + C13fk_idventa_alquiler + ", C14fecha_recibo=" + C14fecha_recibo + '}';
    }

        
}
